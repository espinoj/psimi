package psidev.psi.mi.xml.xmlindex;

import psidev.psi.mi.xml.PsimiXmlReaderException;
import psidev.psi.mi.xml.model.*;

import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Utility class allowing to retreive an element by id as well as resolving its references.
 *
 * @author Samuel Kerrien
 * @version $Id$
 * @since 1.0
 */
public class PsimiXmlExtractor {

    /**
     * PSI-MI XML pull parser.
     */
    PsimiXmlPullParser ppp;

    /**
     * Index indicating the location of referenceable PSI-MI element.
     */
    private PsimiXmlFileIndex index;

    //////////////////
    // Constructors

    public PsimiXmlExtractor( PsimiXmlFileIndex index, PsimiXmlPullParser psimiXmlPullParser ) {
        if ( index == null ) {
            throw new IllegalArgumentException( "You must give a non null index." );
        }
        this.index = index;
        ppp = psimiXmlPullParser;
    }

    //////////////////////
    // Object extractor

    /**
     * Experiment cache.
     */
    private Map<Integer, ExperimentDescription> experimentCache = new HashMap<Integer, ExperimentDescription>();

    /**
     * Convenience method to retreive an experiment. Experiments are cached as long as no-one clears the map.
     *
     * @param fis the data stream.
     * @param id  the identifier of the experiment we want.
     * @return an experiment.
     * @throws PsimiXmlReaderException
     */
    public ExperimentDescription getExperimentById( FileInputStream fis, int id ) throws PsimiXmlReaderException {

        ExperimentDescription ed = experimentCache.get( id );
        if ( ed == null ) {
            // load it from file
            InputStreamRange range = index.getExperimentPosition( id );
            if ( range == null ) {
                throw new PsimiXmlReaderException( "Could not find a range in the index for experiment id:" + id );
            }
            InputStream eis = extractXmlSnippet( fis, range );
            ed = ppp.parseExperiment( eis );

            // store in cache
            experimentCache.put( id, ed );
        }

        return ed;
    }

    public void clearExperimentCache() {
        experimentCache.clear();
    }

    /**
     * Convenience method to retreive an interactor.
     *
     * @param fis the data stream.
     * @param id  the identifier of the interactor we want.
     * @return an interactor.
     * @throws PsimiXmlReaderException
     */
    public Interactor getInteractorById( FileInputStream fis, int id ) throws PsimiXmlReaderException {
        // load it from file
        InputStreamRange range = index.getInteractorPosition( id );
        if ( range == null ) {
            throw new PsimiXmlReaderException( "Could not find a range in the index for interactor id:" + id );
        }
        InputStream is = extractXmlSnippet( fis, range );
        return ppp.parseInteractor( is );
    }

    /**
     * Convenience method to retreive an feature.
     *
     * @param fis the data stream.
     * @param id  the identifier of the feature we want.
     * @return an feature.
     * @throws PsimiXmlReaderException
     */
    public Feature getFeatureById( FileInputStream fis, int id ) throws PsimiXmlReaderException {
        // load it from file
        InputStreamRange range = index.getFeaturePosition( id );
        if ( range == null ) {
            throw new PsimiXmlReaderException( "Could not find a range in the index for feature id:" + id );
        }
        InputStream is = extractXmlSnippet( fis, range );
        return ppp.parseFeature( is );
    }

    /**
     * Convenience method to retreive an participant.
     *
     * @param fis the data stream.
     * @param id  the identifier of the participant we want.
     * @return an participant.
     * @throws PsimiXmlReaderException
     */
    public Participant getParticipantById( FileInputStream fis, int id ) throws PsimiXmlReaderException {
        // load it from file
        InputStreamRange range = index.getParticipantPosition( id );
        if ( range == null ) {
            throw new PsimiXmlReaderException( "Could not find a range in the index for participant id:" + id );
        }
        InputStream is = extractXmlSnippet( fis, range );
        return ppp.parseParticipant( is );
    }

    /**
     * Convenience method to retreive an interaction.
     *
     * @param fis the data stream.
     * @param id  the identifier of the interaction we want.
     * @return an interaction.
     * @throws PsimiXmlReaderException
     */
    public Interaction getInteractionById( FileInputStream fis, int id ) throws PsimiXmlReaderException {
        // load it from file
        InputStreamRange range = index.getInteractionPosition( id );
        if ( range == null ) {
            throw new PsimiXmlReaderException( "Could not find a range in the index for interaction id:" + id );
        }
        InputStream is = extractXmlSnippet( fis, range );
        return ppp.parseInteraction( is );
    }

    ////////////////////////
    // Reference resolver

    /**
     * Browse the given interaction and replaces all reference by the respective object.
     *
     * @param fis         the file to read objects from.
     * @param interaction the interaction to update.
     * @throws PsimiXmlReaderException
     */
    public void resolveReferences( FileInputStream fis, Interaction interaction ) throws PsimiXmlReaderException {

        // TODO in order to resolve the references of an interaction we have to take into account the scope that is the current entry

        // experiments
        if ( interaction.hasExperimentRefs() ) {
            for ( Iterator<ExperimentRef> itex = interaction.getExperimentRefs().iterator(); itex.hasNext(); ) {
                ExperimentRef eref = itex.next();
                // retreive
                ExperimentDescription ed = getExperimentById( fis, eref.getRef() );
                itex.remove();
                interaction.getExperiments().add( ed );
            }
        }

        for ( Participant p : interaction.getParticipants() ) {
            resolveReferences( fis, p );
        }

        // inferred interaction's experiments
        if ( interaction.hasInferredInteractions() ) {
            for ( InferredInteraction ii : interaction.getInferredInteractions() ) {
                if ( ii.hasExperimentRefs() ) {
                    for ( Iterator<ExperimentRef> itex = ii.getExperimentRefs().iterator(); itex.hasNext(); ) {
                        ExperimentRef eref = itex.next();
                        ExperimentDescription ed = getExperimentById( fis, eref.getRef() );
                        itex.remove();
                        ii.getExperiments().add( ed );
                    }
                }

                for ( InferredInteractionParticipant iip : ii.getParticipant() ) {
                    if ( iip.hasFeatureRef() ) {
                        InputStreamRange r = index.getFeaturePosition( iip.getFeatureRef().getRef() );
                        InputStream pfis = extractXmlSnippet( fis, r );
                        Feature ft = ppp.parseFeature( pfis );
                        iip.setFeatureRef( null );
                        iip.setFeature( ft );
                    }

                    if ( iip.hasParticipantRef() ) {
                        InputStreamRange r = index.getParticipantPosition( iip.getParticipantRef().getRef() );
                        InputStream pis = extractXmlSnippet( fis, r );
                        Participant p = ppp.parseParticipant( pis );
                        iip.setParticipantRef( null );
                        iip.setParticipant( p );
                    }
                }
            }
        } // inferred interaction

        // parameters
        if ( interaction.hasParameters() ) {
            for ( Parameter pm : interaction.getParameters() ) {
                if ( pm.hasExperimentRef() ) {
                    ExperimentRef eref = pm.getExperimentRef();
                    ExperimentDescription ed = getExperimentById( fis, eref.getRef() );
                    pm.setExperimentRef( null );
                    pm.setExperiment( ed );
                }
            }
        }
    }

    /**
     * Browse the given participant and replaces all reference by the respective object.
     *
     * @param fis         fis the file to read objects from.
     * @param participant the participant to update.
     * @throws PsimiXmlReaderException
     */
    public void resolveReferences( FileInputStream fis, Participant participant ) throws PsimiXmlReaderException {

        // interactors
        if ( participant.hasInteractionRef() ) {
            InteractionRef ref = participant.getInteractionRef();
            participant.setInteractionRef( null );
            // TODO block if the ref is the same as the current interaction's id
            participant.setInteraction( getInteractionById( fis, ref.getRef() ) ); // recursive call !!
        }

        if ( participant.hasInteractorRef() ) {
            InteractorRef ref = participant.getInteractorRef();
            InputStreamRange irange = index.getInteractorPosition( ref.getRef() );
            InputStream iis = extractXmlSnippet( fis, irange );
            Interactor interactor = ppp.parseInteractor( iis );
            participant.setInteractorRef( null );
            participant.setInteractor( interactor );
        }

        if ( participant.hasParticipantIdentificationMethods() ) {
            for ( ParticipantIdentificationMethod pim : participant.getParticipantIdentificationMethods() ) {
                if ( pim.hasExperimentRefs() ) {
                    for ( Iterator<ExperimentRef> itex = pim.getExperimentRefs().iterator(); itex.hasNext(); ) {
                        ExperimentRef eref = itex.next();
                        ExperimentDescription ed = getExperimentById( fis, eref.getRef() );
                        itex.remove();
                        pim.getExperiments().add( ed );
                    }
                }
            }
        }

        if ( participant.hasExperimentalRoles() ) {
            for ( ExperimentalRole er : participant.getExperimentalRoles() ) {
                if ( er.hasExperimentRefs() ) {
                    for ( Iterator<ExperimentRef> itex = er.getExperimentRefs().iterator(); itex.hasNext(); ) {
                        ExperimentRef eref = itex.next();
                        ExperimentDescription ed = getExperimentById( fis, eref.getRef() );
                        itex.remove();
                        er.getExperiments().add( ed );
                    }
                }
            }
        }

        if ( participant.hasExperimentalPreparations() ) {
            for ( ExperimentalPreparation ep : participant.getExperimentalPreparations() ) {
                if ( ep.hasExperimentRefs() ) {
                    for ( Iterator<ExperimentRef> itex = ep.getExperimentRefs().iterator(); itex.hasNext(); ) {
                        ExperimentRef eref = itex.next();
                        ExperimentDescription ed = getExperimentById( fis, eref.getRef() );
                        itex.remove();
                        ep.getExperiments().add( ed );
                    }
                }
            }
        }

        if ( participant.hasExperimentalInteractors() ) {
            for ( ExperimentalInteractor ei : participant.getExperimentalInteractors() ) {
                if ( ei.hasExperimentRefs() ) {
                    for ( Iterator<ExperimentRef> itex = ei.getExperimentRefs().iterator(); itex.hasNext(); ) {
                        ExperimentRef eref = itex.next();
                        ExperimentDescription ed = getExperimentById( fis, eref.getRef() );
                        itex.remove();
                        ei.getExperiments().add( ed );
                    }
                }

                if ( ei.hasInteractorRef() ) {
                    InteractorRef ref = ei.getInteractorRef();
                    InputStreamRange irange = index.getInteractorPosition( ref.getRef() );
                    InputStream iis = extractXmlSnippet( fis, irange );
                    Interactor interactor = ppp.parseInteractor( iis );
                    ei.setInteractorRef( null );
                    ei.setInteractor( interactor );
                }
            }
        }

        if ( participant.hasHostOrganisms() ) {
            for ( HostOrganism ho : participant.getHostOrganisms() ) {
                if ( ho.hasExperimentRefs() ) {
                    for ( Iterator<ExperimentRef> itex = ho.getExperimentRefs().iterator(); itex.hasNext(); ) {
                        ExperimentRef eref = itex.next();
                        ExperimentDescription ed = getExperimentById( fis, eref.getRef() );
                        itex.remove();
                        ho.getExperiments().add( ed );
                    }
                }
            }
        }

        if ( participant.hasConfidences() ) {
            for ( Confidence c : participant.getConfidenceList() ) {
                if ( c.hasExperimentRefs() ) {
                    for ( Iterator<ExperimentRef> itex = c.getExperimentRefs().iterator(); itex.hasNext(); ) {
                        ExperimentRef eref = itex.next();
                        ExperimentDescription ed = getExperimentById( fis, eref.getRef() );
                        itex.remove();
                        c.getExperiments().add( ed );
                    }
                }
            }
        }

        if ( participant.hasParameters() ) {
            for ( Parameter pm : participant.getParameters() ) {
                if ( pm.hasExperimentRef() ) {
                    ExperimentRef eref = pm.getExperimentRef();
                    ExperimentDescription ed = getExperimentById( fis, eref.getRef() );
                    pm.setExperimentRef( null );
                    pm.setExperiment( ed );
                }
            }
        }
    }

    /////////////////////
    // Stream extractor

    /**
     * Extract the range of chars describe by the given range on the stream and build an other one solely returning
     * that content.
     *
     * @param fis   the file input stream we are reading the snippet from
     * @param range the byte range to extract using random access
     * @return an input stream representing the requested byte range on fis.
     * @throws psidev.psi.mi.xml.PsimiXmlReaderException
     *
     */
    public static InputStream extractXmlSnippet( FileInputStream fis,
                                                 InputStreamRange range ) throws PsimiXmlReaderException {

        if( range == null ) {
            throw new IllegalArgumentException( "You must give a non null InputStreamRange." );
        }

        try {
            // Calculate how many char do we need to read.
            int charCount = ( int ) ( range.getToPosition() - range.getFromPosition() );

            // Position the stream
            fis.getChannel().position( range.getFromPosition() );
            InputStreamReader isr = new InputStreamReader( fis );
            char[] buf = new char[charCount];
            int read = isr.read( buf, 0, charCount );

            // Build an InputStream with this
            StringBuilder sb = new StringBuilder( charCount );
            for ( int i = 0; i < buf.length; i++ ) {
                char c = buf[i];
                sb.append( c );
            }
            return new ByteArrayInputStream( sb.toString().getBytes() );
        } catch ( IOException e ) {
            throw new PsimiXmlReaderException( "An error occured while extracting XML element in " + range, e );
        }
    }
}
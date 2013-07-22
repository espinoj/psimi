package psidev.psi.mi.jami.enricher.impl.participant;


import psidev.psi.mi.jami.enricher.FeatureEnricher;
import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.impl.feature.FeatureEvidenceUpdaterMinimum;
import psidev.psi.mi.jami.enricher.impl.feature.ModelledFeatureUpdaterMaximum;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.FeatureEvidence;
import psidev.psi.mi.jami.model.ModelledFeature;
import psidev.psi.mi.jami.model.ParticipantEvidence;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 28/06/13
 */
public class ParticipantEvidenceUpdaterMinimum
        extends ParticipantUpdaterMinimum<ParticipantEvidence , FeatureEvidence> {


    @Override
    protected void processParticipant(ParticipantEvidence participantEvidenceToEnrich)
            throws EnricherException {

        super.processParticipant(participantEvidenceToEnrich);

        if(getCvTermEnricher() != null){
            getCvTermEnricher().enrichCvTerm(
                    participantEvidenceToEnrich.getExperimentalRole());

            for(CvTerm cvTerm : participantEvidenceToEnrich.getIdentificationMethods()){
                getCvTermEnricher().enrichCvTerm(cvTerm);
            }

            for(CvTerm cvTerm : participantEvidenceToEnrich.getExperimentalPreparations()){
                getCvTermEnricher().enrichCvTerm(cvTerm);
            }
        }
    }

    @Override
    public FeatureEnricher<FeatureEvidence> getFeatureEnricher(){
        if(featureEnricher == null) featureEnricher = new FeatureEvidenceUpdaterMinimum();
        return featureEnricher;
    }
}
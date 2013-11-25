package psidev.psi.mi.jami.xml.io.writer.elements.impl.expanded;

import psidev.psi.mi.jami.binary.ModelledBinaryInteraction;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.xml.PsiXml25ObjectCache;
import psidev.psi.mi.jami.xml.io.writer.elements.*;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.abstracts.AbstractXml25ModelledInteractionWriter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.util.Set;

/**
 * Expanded XML 2.5 writer for a modelled binary interaction (ignore experimental details).
 * It will write cooperative effects as attributes
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/11/13</pre>
 */

public class ExpandedXml25ModelledBinaryInteractionWriter extends AbstractXml25ModelledInteractionWriter<ModelledBinaryInteraction, ModelledParticipant> implements ExpandedPsiXml25ElementWriter<ModelledBinaryInteraction> {

    public ExpandedXml25ModelledBinaryInteractionWriter(XMLStreamWriter writer, PsiXml25ObjectCache objectIndex) {
        super(writer, objectIndex, new ExpandedXml25ModelledParticipantWriter(writer, objectIndex));
    }

    public ExpandedXml25ModelledBinaryInteractionWriter(XMLStreamWriter writer, PsiXml25ObjectCache objectIndex,
                                                        PsiXml25XrefWriter primaryRefWriter, PsiXml25XrefWriter secondaryRefWriter,
                                                        PsiXml25ExperimentWriter experimentWriter, PsiXml25ParticipantWriter<ModelledParticipant> participantWriter,
                                                        PsiXml25ElementWriter<Set<Feature>> inferredInteractionWriter, PsiXml25ElementWriter<CvTerm> interactionTypeWriter,
                                                        PsiXml25ElementWriter<Confidence> confidenceWriter, PsiXml25ParameterWriter parameterWriter,
                                                        PsiXml25ElementWriter<Annotation> attributeWriter, PsiXml25ElementWriter<Checksum> checksumWriter) {
        super(writer, objectIndex, primaryRefWriter, secondaryRefWriter, experimentWriter,
                participantWriter != null ? participantWriter : new ExpandedXml25ModelledParticipantWriter(writer, objectIndex), inferredInteractionWriter, interactionTypeWriter, confidenceWriter, parameterWriter, attributeWriter, checksumWriter);
    }

    @Override
    protected void writeExperiments(ModelledBinaryInteraction object) throws XMLStreamException {
        super.writeExperiments(object);
        writeExperimentDescription();
    }
}

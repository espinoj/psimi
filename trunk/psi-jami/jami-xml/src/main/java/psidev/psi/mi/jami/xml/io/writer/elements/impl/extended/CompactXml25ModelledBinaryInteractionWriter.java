package psidev.psi.mi.jami.xml.io.writer.elements.impl.extended;

import org.codehaus.stax2.XMLStreamWriter2;
import psidev.psi.mi.jami.binary.ModelledBinaryInteraction;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.xml.PsiXml25ObjectCache;
import psidev.psi.mi.jami.xml.extension.InferredInteraction;
import psidev.psi.mi.jami.xml.io.writer.elements.CompactPsiXml25ElementWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXml25ElementWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXml25ParameterWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXml25XrefWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.compact.CompactXml25ModelledParticipantWriter;

import javax.xml.stream.XMLStreamException;

/**
 * Compact XML 2.5 writer for an extended modelled binary interaction (ignore experimental details).
 * It will write cooperative effects as attributes.
 * It will write intra-molecular property, names, interaction types and experiments
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>15/11/13</pre>
 */

public class CompactXml25ModelledBinaryInteractionWriter extends AbstractXml25ModelledInteractionWriter<ModelledBinaryInteraction, ModelledParticipant> implements CompactPsiXml25ElementWriter<ModelledBinaryInteraction> {

    public CompactXml25ModelledBinaryInteractionWriter(XMLStreamWriter2 writer, PsiXml25ObjectCache objectIndex) {
        super(writer, objectIndex, new CompactXml25ModelledParticipantWriter(writer, objectIndex));
    }

    public CompactXml25ModelledBinaryInteractionWriter(XMLStreamWriter2 writer, PsiXml25ObjectCache objectIndex, PsiXml25XrefWriter primaryRefWriter, PsiXml25XrefWriter secondaryRefWriter, CompactPsiXml25ElementWriter<ModelledParticipant> participantWriter, PsiXml25ElementWriter<CvTerm> interactionTypeWriter, PsiXml25ElementWriter<Annotation> attributeWriter, PsiXml25ElementWriter<Alias> aliasWriter, PsiXml25ElementWriter<InferredInteraction> inferredInteractionWriter, PsiXml25ElementWriter<Experiment> experimentWriter,
                                                       PsiXml25ElementWriter<Confidence> confidenceWriter, PsiXml25ParameterWriter parameterWriter
    ) {
        super(writer, objectIndex, primaryRefWriter, secondaryRefWriter,
                participantWriter != null ? participantWriter : new CompactXml25ModelledParticipantWriter(writer, objectIndex), interactionTypeWriter, attributeWriter, aliasWriter, inferredInteractionWriter, experimentWriter,
                confidenceWriter, parameterWriter);
    }

    @Override
    protected void writeExperiments(ModelledBinaryInteraction object) throws XMLStreamException {
        writeExperimentRef(object);
    }
}
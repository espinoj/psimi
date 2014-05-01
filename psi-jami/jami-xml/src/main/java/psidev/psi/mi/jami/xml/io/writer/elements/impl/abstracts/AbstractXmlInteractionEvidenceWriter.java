package psidev.psi.mi.jami.xml.io.writer.elements.impl.abstracts;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.xml.cache.PsiXmlObjectCache;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXmlElementWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXmlParameterWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.XmlAvailabilityWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.XmlConfidenceWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.Xml25ParameterWriter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 * Abstract class for interaction evidence writers
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/11/13</pre>
 */

public abstract class AbstractXmlInteractionEvidenceWriter<I extends InteractionEvidence, P extends ParticipantEvidence> extends AbstractXmlInteractionWriter<I,P> {
    private PsiXmlElementWriter<String> availabilityWriter;
    private PsiXmlElementWriter<Confidence> confidenceWriter;
    private PsiXmlParameterWriter parameterWriter;

    public AbstractXmlInteractionEvidenceWriter(XMLStreamWriter writer, PsiXmlObjectCache objectIndex) {
        super(writer, objectIndex);
    }

    public PsiXmlElementWriter<String> getAvailabilityWriter() {
        if (this.availabilityWriter == null){
            this.availabilityWriter =  new XmlAvailabilityWriter(getStreamWriter(), getObjectIndex());
        }
        return availabilityWriter;
    }

    public void setAvailabilityWriter(PsiXmlElementWriter<String> availabilityWriter) {
        this.availabilityWriter = availabilityWriter;
    }

    public PsiXmlElementWriter<Confidence> getConfidenceWriter() {
        if (this.confidenceWriter == null){
            this.confidenceWriter = new XmlConfidenceWriter(getStreamWriter());
        }
        return confidenceWriter;
    }

    public void setConfidenceWriter(PsiXmlElementWriter<Confidence> confidenceWriter) {
        this.confidenceWriter = confidenceWriter;
    }

    public PsiXmlParameterWriter getParameterWriter() {
        if (this.parameterWriter == null){
            this.parameterWriter = new Xml25ParameterWriter(getStreamWriter(), getObjectIndex());
        }
        return parameterWriter;
    }

    public void setParameterWriter(PsiXmlParameterWriter parameterWriter) {
        this.parameterWriter = parameterWriter;
    }

    @Override
    protected void initialiseDefaultExperiment() {
        super.initialiseDefaultExperiment();
        getParameterWriter().setDefaultExperiment(getDefaultExperiment());
    }

    @Override
    public void setDefaultExperiment(Experiment defaultExperiment) {
        super.setDefaultExperiment(defaultExperiment);
        getParameterWriter().setDefaultExperiment(defaultExperiment);
    }

    @Override
    public Experiment extractDefaultExperimentFrom(I interaction) {
        Experiment exp = interaction.getExperiment();
        return exp != null ? exp : getDefaultExperiment() ;
    }

    @Override
    protected void writeExperiments(I object) throws XMLStreamException {
        if (object.getExperiment() != null){
            setDefaultExperiment(object.getExperiment());
        }
    }

    @Override
    protected void writeOtherAttributes(I object) throws XMLStreamException {
        // write IMEx id
        if (object.getImexId() != null){
            getStreamWriter().writeAttribute("imexId", object.getImexId());
        }
    }

    @Override
    protected void writeIntraMolecular(I object) throws XMLStreamException {
        // do nothing
    }

    @Override
    protected void writeModelled(I object) throws XMLStreamException {
        // we say we have a modelled = true if the interaction is inferred
        if (object.isInferred()){
            getStreamWriter().writeStartElement("modelled");
            getStreamWriter().writeCharacters(Boolean.toString(object.isInferred()));
            // write end intra molecular
            getStreamWriter().writeEndElement();
        }
    }

    @Override
    protected void writeParameters(I object) throws XMLStreamException {
        // write parameters
        if (!object.getParameters().isEmpty()){
            // write start parameter list
            getStreamWriter().writeStartElement("parameterList");
            for (Object ann : object.getParameters()){
                getParameterWriter().write((Parameter)ann);
            }
            // write end parameterList
            getStreamWriter().writeEndElement();
        }
    }

    @Override
    protected void writeConfidences(I object) throws XMLStreamException {
        // write confidences
        if (!object.getConfidences().isEmpty()){
            // write start confidence list
            getStreamWriter().writeStartElement("confidenceList");
            for (Object ann : object.getConfidences()){
                getConfidenceWriter().write((Confidence)ann);
            }
            // write end confidenceList
            getStreamWriter().writeEndElement();
        }
    }

    @Override
    protected void writeNegative(I object) throws XMLStreamException {
        if (object.isNegative()){
            getStreamWriter().writeStartElement("negative");
            getStreamWriter().writeCharacters(Boolean.toString(object.isNegative()));
            // write end negative
            getStreamWriter().writeEndElement();
        }
    }

    protected void writeAvailabilityRef(String availability) throws XMLStreamException {
        getStreamWriter().writeStartElement("availabilityRef");
        getStreamWriter().writeCharacters(Integer.toString(getObjectIndex().extractIdForAvailability(availability)));
        getStreamWriter().writeEndElement();
    }

    protected void writeAvailabilityDescription(String availability) throws XMLStreamException {
        getAvailabilityWriter().write(availability);
    }
}
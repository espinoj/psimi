package psidev.psi.mi.jami.xml.extension;

import com.sun.xml.internal.bind.annotation.XmlLocation;
import org.xml.sax.Locator;
import psidev.psi.mi.jami.model.*;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Xml implementation of InteractionEvidence
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>08/10/13</pre>
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = "interactionEvidence", propOrder = {
        "JAXBNames",
        "JAXBXref",
        "JAXBAvailability",
        "JAXBAvailabilityRef",
        "JAXBExperimentDescriptions",
        "JAXBExperimentRefs",
        "JAXBParticipants",
        "JAXBInferredInteractions",
        "JAXBInteractionTypes",
        "JAXBModelled",
        "JAXBIntraMolecular",
        "JAXBNegative",
        "JAXBConfidences",
        "JAXBParameters",
        "JAXBAttributes"
})
public class XmlInteractionEvidence extends AbstractXmlInteraction<ParticipantEvidence> implements InteractionEvidence{

    private Availability availability;
    private Integer availabilityRef;
    private Collection<Parameter> parameters;
    private boolean isInferred = false;
    private Collection<Confidence> confidences;
    private boolean isNegative;

    private Collection<VariableParameterValueSet> variableParameterValueSets;
    private List<Experiment> experiments;
    private ArrayList<XmlExperiment> experimentDescriptions;
    private ArrayList<Integer> experimentRefs;
    private Boolean modelled;

    public XmlInteractionEvidence() {
        super();
    }

    public XmlInteractionEvidence(String shortName) {
        super(shortName);
    }

    public XmlInteractionEvidence(String shortName, CvTerm type) {
        super(shortName, type);
    }

    protected void initialiseExperimentalConfidences(){
        this.confidences = new ArrayList<Confidence>();
    }

    protected void initialiseExperimentalConfidencesWith(Collection<Confidence> confidences){
        if (confidences == null){
            this.confidences = Collections.EMPTY_LIST;
        }
        else {
            this.confidences = confidences;
        }
    }

    protected void initialiseVariableParameterValueSets(){
        this.variableParameterValueSets = new ArrayList<VariableParameterValueSet>();
    }

    protected void initialiseVariableParameterValueSetsWith(Collection<VariableParameterValueSet> variableValues){
        if (variableValues == null){
            this.variableParameterValueSets = Collections.EMPTY_LIST;
        }
        else {
            this.variableParameterValueSets = variableValues;
        }
    }

    protected void initialiseExperimentalParameters(){
        this.parameters = new ArrayList<Parameter>();
    }

    protected void initialiseExperimentalParametersWith(Collection<Parameter> parameters){
        if (parameters == null){
            this.parameters = Collections.EMPTY_LIST;
        }
        else {
            this.parameters = parameters;
        }
    }

    public String getImexId() {
        return getJAXBXref() != null ? this.getJAXBXref().getImexId() : null;
    }

    public void assignImexId(String identifier) {
        if (getJAXBXref() == null && identifier != null){
            setJAXBXref(new InteractionXrefContainer());
        }
        getJAXBXref().assignImexId(identifier);
    }

    public Experiment getExperiment() {
        return (this.getExperiments() != null && !this.experiments.isEmpty())? this.experiments.iterator().next() : null;
    }

    public void setExperiment(Experiment experiment) {
        if (getExperiments() == null && experiment != null){
            this.experiments = new ArrayList<Experiment>();
            this.experiments.add(experiment);
        }
        else if (this.experiments != null){
            if (!this.experiments.isEmpty() && experiment == null){
                this.experiments.remove(0);
            }
            else if (experiment != null){
                this.experiments.remove(0);
                this.experiments.add(0, experiment);
            }
        }
    }

    public List<Experiment> getExperiments() {
        if (experiments == null){
            experiments = new ArrayList<Experiment>();
        }
        if (experiments.isEmpty() && this.experimentRefs != null && !this.experimentRefs.isEmpty()){
            resolveExperimentReferences();
        }
        return experiments;
    }

    public void setExperimentAndAddInteractionEvidence(Experiment experiment) {
        Experiment current = getExperiment();
        if (current != null){
            current.removeInteractionEvidence(this);
        }

        if (experiment != null){
            experiment.addInteractionEvidence(this);
        }
    }

    public Collection<VariableParameterValueSet> getVariableParameterValues() {

        if (variableParameterValueSets == null){
            initialiseVariableParameterValueSets();
        }
        return this.variableParameterValueSets;
    }

    public Collection<Confidence> getConfidences() {
        if (confidences == null){
            initialiseExperimentalConfidences();
        }
        return this.confidences;
    }

    public String getAvailability() {
        return this.availability!= null ? this.availability.getJAXBValue() : null;
    }

    public void setAvailability(String availability) {
        if (this.availability == null && this.availability != null){
            this.availability = new Availability();
        }
        this.availability.setJAXBValue(availability);
    }

    public boolean isNegative() {
        return this.isNegative;
    }

    public void setNegative(boolean negative) {
        this.isNegative = negative;
    }

    public Collection<Parameter> getParameters() {
        if (parameters == null){
            initialiseExperimentalParameters();
        }
        return this.parameters;
    }

    public boolean isInferred() {
        return this.isInferred;
    }

    public void setInferred(boolean inferred) {
        this.isInferred = inferred;
    }

    @Override
    public String toString() {
        return getImexId() != null ? getImexId() : super.toString();
    }

    @Override
    @XmlAttribute(name = "names")
    public NamesContainer getJAXBNames() {
        return super.getJAXBNames();
    }

    @Override
    @XmlAttribute(name = "xref")
    public InteractionXrefContainer getJAXBXref() {
        return super.getJAXBXref();
    }

    @Override
    @XmlAttribute(name = "id", required = true)
    public int getJAXBId() {
        return super.getJAXBId();
    }

    @Override
    @XmlElementWrapper(name="attributeList")
    @XmlElement(name="attribute", required = true)
    @XmlElementRefs({ @XmlElementRef(type=XmlAnnotation.class)})
    public ArrayList<Annotation> getJAXBAttributes() {
        return super.getJAXBAttributes();
    }

    @Override
    @XmlElement(name = "intraMolecular", defaultValue = "false")
    public Boolean getJAXBIntraMolecular() {
        return super.getJAXBIntraMolecular();
    }

    @Override
    @XmlElementWrapper(name="participantList")
    @XmlElement(name="participant", required = true)
    @XmlElementRefs({ @XmlElementRef(type=XmlParticipantEvidence.class)})
    public ArrayList<ParticipantEvidence> getJAXBParticipants() {
        return super.getJAXBParticipants();
    }

    @Override
    @XmlElementWrapper(name="inferredInteractionList")
    @XmlElement(name="inferredInteraction", required = true)
    public ArrayList<InferredInteraction> getJAXBInferredInteractions() {
        return super.getJAXBInferredInteractions();
    }

    @Override
    @XmlElement(name="interactionType")
    public ArrayList<CvTerm> getJAXBInteractionTypes() {
        return super.getJAXBInteractionTypes();
    }

    @Override
    @XmlLocation
    @XmlTransient
    public Locator getSaxLocator() {
        return super.getSaxLocator();
    }

    /**
     * Gets the value of the confidenceList property.
     *
     * @return
     *     possible object is
     *     {@link ArrayList<Confidence> }
     *
     */
    @XmlElementWrapper(name="confidenceList")
    @XmlElement(name="participant", required = true)
    @XmlElementRefs({ @XmlElementRef(type=XmlConfidence.class)})
    public ArrayList<Confidence> getJAXBConfidences() {
        if (this.confidences == null || this.confidences.isEmpty()){
            return null;
        }
        return new ArrayList<Confidence>(getConfidences());
    }

    /**
     * Sets the value of the confidenceList property.
     *
     * @param value
     *     allowed object is
     *     {@link ArrayList<Confidence> }
     *
     */
    public void setJAXBConfidences(ArrayList<XmlConfidence> value) {
        getConfidences().clear();
        if (value != null && !value.isEmpty()){
            getConfidences().addAll(value);
        }
    }

    /**
     * Gets the value of the parameterList property.
     *
     * @return
     *     possible object is
     *     {@link ArrayList<ModelledParameter> }
     *
     */
    @XmlElementWrapper(name="parameterList")
    @XmlElement(name="participant", required = true)
    @XmlElementRefs({ @XmlElementRef(type=XmlParameter.class)})
    public ArrayList<Parameter> getJAXBParameters() {
        if (this.parameters == null || this.parameters.isEmpty()){
            return null;
        }
        return new ArrayList<Parameter>(getParameters());
    }

    /**
     * Sets the value of the parameterList property.
     *
     * @param value
     *     allowed object is
     *     {@link ArrayList<ModelledParameter> }
     *
     */
    public void setJAXBParameters(ArrayList<XmlParameter> value) {
        getParameters().clear();
        if (value != null && !value.isEmpty()){
            getParameters().addAll(value);
        }
    }

    /**
     * Gets the value of the availability property.
     *
     * @return
     *     possible object is
     *     {@link Availability }
     *
     */
    @XmlElement(name = "availability")
    public Availability getJAXBAvailability() {
        return availability;
    }

    /**
     * Sets the value of the availability property.
     *
     * @param value
     *     allowed object is
     *     {@link Availability }
     *
     */
    public void setJAXBAvailability(Availability value) {
        this.availability = value;
    }

    /**
     * Gets the value of the availabilityRef property.
     *
     * @return
     *     possible object is
     *     {@link Integer }
     *
     */
    @XmlElement(name = "availabilityRef")
    public Integer getJAXBAvailabilityRef() {
        return availabilityRef;
    }

    /**
     * Sets the value of the availabilityRef property.
     *
     * @param value
     *     allowed object is
     *     {@link Integer }
     *
     */
    public void setJAXBAvailabilityRef(Integer value) {
        this.availabilityRef = value;
    }

    private void resolveAvailabilityReferences() {
        if (getMapOfReferencedObjects().containsKey(availabilityRef)){
            Object o = getMapOfReferencedObjects().get(availabilityRef);
            if (o instanceof Availability){
                this.availability = (Availability)o;
            }
        }
    }

    /**
     * Gets the value of the experimentList property.
     *
     * @return
     *     possible object is
     *     {@link ArrayList<XmlExperiment> }
     *
     */
    @XmlElementWrapper(name="experimentList")
    @XmlElement(name="experimentDescription", required = true)
    public ArrayList<XmlExperiment> getJAXBExperimentDescriptions() {
        return experimentDescriptions;
    }

    /**
     * Sets the value of the experimentList property.
     *
     * @param value
     *     allowed object is
     *     {@link ArrayList<XmlExperiment> }
     *
     */
    public void setJAXBExperimentDescriptions(ArrayList<XmlExperiment> value) {
        this.experimentDescriptions = value;
        if (this.experimentDescriptions != null){
           this.experiments.addAll(experimentDescriptions);
        }
    }

    /**
     * Gets the value of the experimentList property.
     *
     * @return
     *     possible object is
     *     {@link ArrayList<Integer> }
     *
     */
    @XmlElementWrapper(name="experimentList")
    @XmlElement(name="experimentRef", required = true)
    public ArrayList<Integer> getJAXBExperimentRefs() {
        return experimentRefs;
    }

    /**
     * Sets the value of the experimentList property.
     *
     * @param value
     *     allowed object is
     *     {@link ArrayList<Integer> }
     *
     */
    public void setJAXBExperimentRefs(ArrayList<Integer> value) {
        this.experimentRefs = value;
    }

    /**
     * Gets the value of the modelled property.
     *
     * @return
     *     possible object is
     *     {@link Boolean }
     *
     */
    @XmlElement(name = "modelled", defaultValue = "false")
    public Boolean getJAXBModelled() {
        return modelled;
    }

    /**
     * Sets the value of the modelled property.
     *
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *
     */
    public void setJAXBModelled(Boolean value) {
        this.modelled = value;
    }

    /**
     * Gets the value of the negative property.
     *
     * @return
     *     possible object is
     *     {@link Boolean }
     *
     */
    @XmlElement(name = "negative")
    public Boolean getJAXBNegative() {
        return isNegative;
    }

    /**
     * Sets the value of the negative property.
     *
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *
     */
    public void setJAXBNegative(Boolean value) {
        if (value == null){
            this.isNegative = false;
        }
        else{
            this.isNegative = value;
        }
    }

    private void resolveExperimentReferences() {
        for (Integer id : this.experimentRefs){
            if (getMapOfReferencedObjects().containsKey(id)){
                Object o = getMapOfReferencedObjects().get(id);
                if (o instanceof Experiment){
                    this.experiments.add((Experiment)o);
                }
            }
        }
    }
}

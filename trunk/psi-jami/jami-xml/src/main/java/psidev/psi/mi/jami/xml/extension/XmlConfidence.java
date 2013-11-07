package psidev.psi.mi.jami.xml.extension;

import com.sun.xml.bind.Locatable;
import com.sun.xml.bind.annotation.XmlLocation;
import org.xml.sax.Locator;
import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.model.Confidence;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.utils.comparator.confidence.UnambiguousConfidenceComparator;
import psidev.psi.mi.jami.xml.XmlEntryContext;
import psidev.psi.mi.jami.xml.listener.PsiXmlParserListener;
import psidev.psi.mi.jami.xml.utils.PsiXmlUtils;

import javax.xml.bind.annotation.*;

/**
 * Xml implementation of confidence
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>19/07/13</pre>
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlSeeAlso({
        XmlModelledConfidence.class
})
public class XmlConfidence implements Confidence, FileSourceContext, Locatable{

    private CvTerm type;
    private String value;
    private PsiXmLocator sourceLocator;
    @XmlLocation
    @XmlTransient
    private Locator locator;

    public XmlConfidence() {
    }

    public XmlConfidence(XmlOpenCvTerm type, String value) {
        if (type == null){
            throw new IllegalArgumentException("The confidence type is required and cannot be null");
        }
        this.type = type;
        if (value == null){
            throw new IllegalArgumentException("The confidence value is required and cannot be null");
        }
        this.value = value;
    }

    /**
     * Gets the value of the type property.
     *
     * @return
     *     possible object is
     *     {@link XmlOpenCvTerm }
     *
     */
    public CvTerm getType() {
        if (this.type == null){
            this.type = new XmlOpenCvTerm();
        }
        return type;
    }

    public String getValue() {
        return value;
    }

    /**
     * Sets the value of the value property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    @XmlElement(name = "value", required = true)
    public void setJAXBValue(String value) {
        this.value = value != null ? value : PsiXmlUtils.UNSPECIFIED;
        if (value == null){
            PsiXmlParserListener listener = XmlEntryContext.getInstance().getListener();
            if (listener != null){
                listener.onMissingConfidenceValue(this);
            }
        }
    }

    /**
     * Sets the value of the type property.
     *
     * @param value
     *     allowed object is
     *     {@link XmlOpenCvTerm }
     *
     */
    @XmlElement(name = "unit", required = true, type = XmlOpenCvTerm.class)
    public void setJAXBType(CvTerm value) {
        this.type = value;
        if (value == null){
            PsiXmlParserListener listener = XmlEntryContext.getInstance().getListener();
            if (listener != null){
                listener.onMissingConfidenceType(this);
            }
        }
    }

    @Override
    public Locator sourceLocation() {
        return (Locator)getSourceLocator();
    }

    public FileSourceLocator getSourceLocator() {
        if (sourceLocator == null && locator != null){
            sourceLocator = new PsiXmLocator(locator.getLineNumber(), locator.getColumnNumber(), null);
        }
        return sourceLocator;
    }

    public void setSourceLocator(FileSourceLocator sourceLocator) {
        if (sourceLocator == null){
            this.sourceLocator = null;
        }
        else{
            this.sourceLocator = new PsiXmLocator(sourceLocator.getLineNumber(), sourceLocator.getCharNumber(), null);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }

        if (!(o instanceof Confidence)){
            return false;
        }

        return UnambiguousConfidenceComparator.areEquals(this, (Confidence) o);
    }

    @Override
    public String toString() {
        return "Xml Confidence: "+sourceLocator != null ? sourceLocator.toString():super.toString();
    }

    @Override
    public int hashCode() {
        return UnambiguousConfidenceComparator.hashCode(this);
    }
}

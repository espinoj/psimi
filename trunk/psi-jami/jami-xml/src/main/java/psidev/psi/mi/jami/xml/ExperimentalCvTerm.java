//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.05.20 at 10:58:57 AM BST 
//


package psidev.psi.mi.jami.xml;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.Collection;


/**
 * This element is controlled by the PSI-MI controlled vocabulary
 *                 "experimentalPreparation", root term id MI:0346.
 *             
 * 
 * <p>Java class for experimentalPreparation complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="experimentalPreparation">
 *   &lt;complexContent>
 *     &lt;extension base="{http://psi.hupo.org/mi/mif}cvType">
 *       &lt;sequence minOccurs="0">
 *         &lt;element name="experimentRefList" type="{http://psi.hupo.org/mi/mif}experimentRefList" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
@XmlType(name = "experimentalPreparation", propOrder = {
    "experimentRefList"
})
public class ExperimentalCvTerm
    extends XmlCvTerm
    implements Serializable
{

    private Collection<Integer> experimentRefList;

    /**
     * Gets the value of the experimentRefList property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    @XmlElementWrapper(name="experimentRefList")
    @XmlElement(name="experimentRef")
    public Collection<Integer> getExperimentRefList() {
        return experimentRefList;
    }

    /**
     * Sets the value of the experimentRefList property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setExperimentRefList(Collection<Integer> value) {
        this.experimentRefList = value;
    }

}
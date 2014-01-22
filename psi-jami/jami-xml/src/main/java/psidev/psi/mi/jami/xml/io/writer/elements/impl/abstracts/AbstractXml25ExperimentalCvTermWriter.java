package psidev.psi.mi.jami.xml.io.writer.elements.impl.abstracts;

import psidev.psi.mi.jami.model.Alias;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Experiment;
import psidev.psi.mi.jami.xml.cache.PsiXml25ObjectCache;
import psidev.psi.mi.jami.xml.extension.ExperimentalCvTerm;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXml25ElementWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXml25XrefWriter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 * XML 2.5 writer for experimental cv terms
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>12/11/13</pre>
 */

public abstract class AbstractXml25ExperimentalCvTermWriter extends AbstractXml25CvTermWriter {
    private PsiXml25ObjectCache objectIndex;

    public AbstractXml25ExperimentalCvTermWriter(XMLStreamWriter writer, PsiXml25ObjectCache objectIndex) {
        super(writer);
        if (objectIndex == null){
            throw new IllegalArgumentException("The PsiXml 2.5 object index is mandatory for the AbstractXml25ExperimentalCvTermWriter. It is necessary for generating an id to an experimentDescription");
        }
        this.objectIndex = objectIndex;
    }

    public AbstractXml25ExperimentalCvTermWriter(XMLStreamWriter writer,PsiXml25ObjectCache objectIndex,
                                                 PsiXml25ElementWriter<Alias> aliasWriter, PsiXml25XrefWriter primaryRefWriter,
                                                 PsiXml25XrefWriter secondaryRefWriter) {
        super(writer, aliasWriter, primaryRefWriter, secondaryRefWriter);
        if (objectIndex == null){
            throw new IllegalArgumentException("The PsiXml 2.5 object index is mandatory for the AbstractXml25ExperimentalCvTermWriter. It is necessary for generating an id to an experimentDescription");
        }
        this.objectIndex = objectIndex;
    }

    @Override
    protected void writeOtherProperties(CvTerm object) throws XMLStreamException {
        ExperimentalCvTerm term = (ExperimentalCvTerm) object;
        if (!term.getExperiments().isEmpty()){
            getStreamWriter().writeStartElement("experimentRefList");
            for (Experiment exp : term.getExperiments()){
                getStreamWriter().writeStartElement("experimentRef");
                getStreamWriter().writeCharacters(Integer.toString(objectIndex.extractIdForExperiment(exp)));
                getStreamWriter().writeEndElement();
            }
            getStreamWriter().writeEndElement();
        }
    }
}
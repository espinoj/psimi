package psidev.psi.mi.jami.xml.io.writer.elements.impl.expanded.xml25;

import psidev.psi.mi.jami.model.NamedParticipant;
import psidev.psi.mi.jami.model.ParticipantEvidence;
import psidev.psi.mi.jami.xml.cache.PsiXmlObjectCache;
import psidev.psi.mi.jami.xml.utils.PsiXmlUtils;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 * Expanded Xml 2.5 writer for a named participant evidence with a shortname and a fullname.
 * It writes full experimental details
 *
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>14/11/13</pre>
 */

public class XmlNamedParticipantEvidenceWriter extends XmlParticipantEvidenceWriter {
    public XmlNamedParticipantEvidenceWriter(XMLStreamWriter writer, PsiXmlObjectCache objectIndex) {
        super(writer, objectIndex);
    }

    @Override
    protected void writeNames(ParticipantEvidence object) throws XMLStreamException {
        if (object instanceof NamedParticipant){
            NamedParticipant xmlParticipant = (NamedParticipant) object;
            // write names
            PsiXmlUtils.writeCompleteNamesElement(xmlParticipant.getShortName(),
                    xmlParticipant.getFullName(), xmlParticipant.getAliases(), getStreamWriter(),
                    getAliasWriter());
        }
        else{
            super.writeNames(object);
        }
    }
}

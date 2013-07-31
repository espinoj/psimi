package psidev.psi.mi.xml.events;

import psidev.psi.mi.jami.datasource.DefaultFileSourceContext;
import psidev.psi.mi.jami.datasource.FileParsingErrorType;

import java.io.Serializable;

/**
 * Event which is fired when a CvTerm is missing
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>21/03/13</pre>
 */

public class MissingElementEvent extends DefaultFileSourceContext implements Serializable {
    private String message;
    private FileParsingErrorType errorType;

    public MissingElementEvent(String message, FileParsingErrorType errorType){
        this.message = message;
        this.errorType = errorType;
    }

    public String getMessage() {
        return message;
    }

    public FileParsingErrorType getErrorType() {
        return errorType;
    }
}
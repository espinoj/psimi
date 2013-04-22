package psidev.psi.mi.query;

import psidev.psi.mi.exception.BridgeFailedException;
import psidev.psi.mi.exception.UnrecognizedCriteriaException;
import psidev.psi.mi.exception.UnrecognizedDatabaseException;
import psidev.psi.mi.exception.UnrecognizedTermException;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 22/04/13
 * Time: 10:10
 */
public interface QueryInterface {
    public QueryObject passQuery(QueryObject queryObject)
            throws UnrecognizedTermException, BridgeFailedException,
            UnrecognizedDatabaseException, UnrecognizedCriteriaException;
}

package psidev.psi.mi.jami.datasource;

import psidev.psi.mi.jami.exception.MIIOException;

import java.util.Map;

/**
 * A data Source in JAMI.
 *
 * A MIDataSource can initialise itself with a map of options. Some dataSources (file dataSources mainly)
 * need to be closed.
 *
 * Some MIDataSource can be reset and re-initialised
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>01/03/13</pre>
 */

public interface MIDataSource {

    /**
     * Initialise the context of the MIDataSource given a map of options
     * @param options : the options provided by the user
     */
    public void initialiseContext(Map<String, Object> options);

    /**
     * This method close the file data source and all opened streams and readers
     * @throws MIIOException: if the dataSource cannot be closed
     */
    public void close() throws MIIOException;

    /**
     * This method will reset the data sources from all loaded options.
     * The data source will be back to what is was before the initialiseContext was called.
     * To re-use the data source after calling the reset() method, the data source needs to be re-initialised with
     * initialiseContext(Map<String, Object> options).
     * Any provided inputStream or reader will not be closed and will have to be closed separately.
     * @throws MIIOException : if the dataSource cannot be reset
     */
    public void reset() throws MIIOException;
}

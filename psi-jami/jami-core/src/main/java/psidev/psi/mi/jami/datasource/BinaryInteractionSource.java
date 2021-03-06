package psidev.psi.mi.jami.datasource;

import psidev.psi.mi.jami.binary.BinaryInteraction;
import psidev.psi.mi.jami.exception.MIIOException;

import java.util.Collection;
import java.util.Iterator;

/**
 * A Data source of binary interactions.
 * It gives full access to all the interactions using Iterator or the full collection.
 * It can also give information about the size of the datasource
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>08/11/13</pre>
 */

public interface BinaryInteractionSource<T extends BinaryInteraction> extends BinaryInteractionStream<T>, InteractionSource<T> {
}

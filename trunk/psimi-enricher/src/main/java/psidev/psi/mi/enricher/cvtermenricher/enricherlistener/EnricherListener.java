package psidev.psi.mi.enricher.cvtermenricher.enricherlistener;

import psidev.psi.mi.enricher.cvtermenricher.enricherlistener.event.AdditionEvent;
import psidev.psi.mi.enricher.cvtermenricher.enricherlistener.event.OverwriteEvent;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 13/05/13
 * Time: 11:11
 */
public interface EnricherListener {

    public void overwriteEvent(OverwriteEvent e);

    public void additionEvent(AdditionEvent e);

}

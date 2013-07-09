package psidev.psi.mi.jami.enricher.impl.feature.listener;

import psidev.psi.mi.jami.enricher.listener.EnricherListener;
import psidev.psi.mi.jami.enricher.listener.EnrichmentStatus;
import psidev.psi.mi.jami.model.Feature;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 13/06/13
 */
public interface FeatureEnricherListener extends  EnricherListener {

    public void onFeatureEnriched(Feature feature , EnrichmentStatus status , String message);
}

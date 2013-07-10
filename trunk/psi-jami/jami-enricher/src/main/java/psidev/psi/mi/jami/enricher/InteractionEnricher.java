package psidev.psi.mi.jami.enricher;

import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.impl.interaction.listener.InteractionEnricherListener;
import psidev.psi.mi.jami.model.Feature;
import psidev.psi.mi.jami.model.Interaction;
import psidev.psi.mi.jami.model.Participant;

import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 28/06/13
 */
public interface InteractionEnricher <I extends Interaction ,  P extends Participant , F extends Feature> {

    public void enrichInteraction(I interactionToEnrich) throws EnricherException;
    public void enrichInteractions(Collection<I> interactionsToEnrich) throws EnricherException;


    public ParticipantEnricher<P , F> getParticipantEnricher();
    public void setParticipantEnricher(ParticipantEnricher<P , F> participantEnricher);

    public void setCvTermEnricher(CvTermEnricher cvTermEnricher);
    public CvTermEnricher getCvTermEnricher();


    public InteractionEnricherListener getInteractionEnricherListener();
    public void setInteractionEnricherListener(InteractionEnricherListener listener);

}

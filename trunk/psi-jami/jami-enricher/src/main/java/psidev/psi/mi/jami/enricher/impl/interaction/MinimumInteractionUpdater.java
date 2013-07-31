package psidev.psi.mi.jami.enricher.impl.interaction;


import psidev.psi.mi.jami.model.*;

/**
 * An implementation of the InteractionEnricher which updates to the minimum level
 * It provides default minimum updaters.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 09/07/13
 */
public class MinimumInteractionUpdater<I extends Interaction, P extends Participant, F extends Feature>
        extends AbstractInteractionEnricher <I , P , F>{
    /*
    @Override
    public void processInteraction(I interactionToEnrich) throws EnricherException {
        super.processInteraction(interactionToEnrich);
    }  */

    /*
    public ParticipantEnricher<P, F> getParticipantEnricher(){
        if(participantEnricher == null) participantEnricher = new MinimumParticipantUpdater<P,F>();
        return participantEnricher;
    }


    public CvTermEnricher getCvTermEnricher(){
        if(cvTermEnricher == null) cvTermEnricher = new MinimumCvTermUpdater();
        return cvTermEnricher;
    }  */
}
package psidev.psi.mi.jami.enricher.listener.impl;


import psidev.psi.mi.jami.enricher.listener.EntityEnricherListener;
import psidev.psi.mi.jami.enricher.listener.ParticipantEnricherListener;
import psidev.psi.mi.jami.enricher.listener.ParticipantPoolEnricherListener;
import psidev.psi.mi.jami.model.*;

/**
 * A manager for listeners which holds a list of listeners.
 * Listener manager allows enrichers to send events to multiple listeners.
 * A listener itself, it implements all methods
 * which will then fire the corresponding method in each entry of the listener list.
 * No promise can be given to the order in which the listeners are fired.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 10/07/13
 */
public class ParticipantPoolEnricherListenerManager<P extends ParticipantPool>
        extends ParticipantEnricherListenerManager<P>
        implements ParticipantPoolEnricherListener<P> {

    /**
     * A constructor to create a listener manager with no listeners.
     */
    public ParticipantPoolEnricherListenerManager(){ }

    /**
     * A constructor to initiate a listener manager with as many listeners as required.
     * @param listeners     The listeners to add.
     */
    public ParticipantPoolEnricherListenerManager(ParticipantEnricherListener<P>... listeners){
        super(listeners);
    }

    public void onTypeUpdate(P participant, CvTerm oldType) {
        for(EntityEnricherListener listener : getListenersList()){
            if (listener instanceof ParticipantPoolEnricherListener){
                ((ParticipantPoolEnricherListener)listener).onTypeUpdate(participant, oldType);
            }
        }
    }

    public void onAddedCandidate(P participant, ParticipantCandidate added) {
        for(EntityEnricherListener listener : getListenersList()){
            if (listener instanceof ParticipantPoolEnricherListener){
                ((ParticipantPoolEnricherListener)listener).onAddedCandidate(participant, added);
            }
        }
    }

    public void onRemovedCandidate(P participant, ParticipantCandidate removed) {
        for(EntityEnricherListener listener : getListenersList()){
            if (listener instanceof ParticipantPoolEnricherListener){
                ((ParticipantPoolEnricherListener)listener).onRemovedCandidate(participant, removed);
            }
        }
    }


    //============================================================================================
}

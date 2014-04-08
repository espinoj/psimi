package psidev.psi.mi.jami.model.impl;

import psidev.psi.mi.jami.listener.ParticipantInteractorChangeListener;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.utils.CvTermUtils;

import java.util.*;

/**
 * Default implementation of ParticipantPool
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>04/10/13</pre>
 */

public abstract class AbstractParticipantPool<I extends Interaction, F extends Feature, C extends Participant> extends AbstractParticipant<I,F> implements ParticipantPool<I,F,C>, ParticipantInteractorChangeListener {

    private Collection<C> components;
    private CvTerm type;

    public AbstractParticipantPool(InteractorPool interactor) {
        super(interactor);
        initialiseComponentCandidatesSet();
    }

    public AbstractParticipantPool(InteractorPool interactor, CvTerm bioRole) {
        super(interactor, bioRole);
        initialiseComponentCandidatesSet();
    }

    public AbstractParticipantPool(InteractorPool interactor, Stoichiometry stoichiometry) {
        super(interactor, stoichiometry);
        initialiseComponentCandidatesSet();
    }

    public AbstractParticipantPool(InteractorPool interactor, CvTerm bioRole, Stoichiometry stoichiometry) {
        super(interactor, bioRole, stoichiometry);
        initialiseComponentCandidatesSet();
    }

    protected void initialiseComponentCandidatesSet(){
        this.components = new ArrayList<C>();
    }

    protected void initialiseInteractorCandidatesSetWith(Set<C> interactorCandidates){
        if (interactorCandidates == null){
            this.components = Collections.EMPTY_LIST;
        }
        else {
            this.components = interactorCandidates;
        }
    }

    @Override
    public InteractorPool getInteractor() {
        return (InteractorPool) super.getInteractor();
    }

    @Override
    public void setInteractor(Interactor interactor) {
        throw new UnsupportedOperationException("Cannot set the interactor of an ParticipantPool as it is an interactorSet that is related to the interactors in the set of entities");
    }

    @Override
    public void setInteractionAndAddParticipant(I interaction) {
        super.setInteractionAndAddParticipant(interaction);

        for (C subEntity : this){
             subEntity.setInteraction(interaction);
        }
    }

    @Override
    public void setInteraction(I interaction) {
        super.setInteraction(interaction);

        for (C subEntity : this){
            subEntity.setInteraction(interaction);
        }
    }

    public CvTerm getType() {
        return type;
    }

    /**
     * Sets the component set type.
     * Sets the type to molecule set (MI:1304) if the given type is null
     */
    public void setType(CvTerm type) {
        if (type == null){
            this.type = CvTermUtils.createMoleculeSetType();
        }
        else {
            this.type = type;
        }
        getInteractor().setInteractorType(this.type);
    }

    public int size() {
        return components.size();
    }

    public boolean isEmpty() {
        return components.isEmpty();
    }

    public boolean contains(Object o) {
        return components.contains(o);
    }

    public Iterator<C> iterator() {
        return components.iterator();
    }

    public Object[] toArray() {
        return components.toArray();
    }

    public <T> T[] toArray(T[] ts) {
        return components.toArray(ts);
    }

    public boolean add(C interactor) {
        if (components.add(interactor)){
            interactor.setChangeListener(this);
            getInteractor().add(interactor.getInteractor());
            interactor.setInteraction(getInteraction());
            return true;
        }
        return false;
    }

    public boolean remove(Object o) {
        if (components.remove(o)){
            Participant entity = (Participant)o;
            entity.setChangeListener(null);
            getInteractor().remove(entity.getInteractor());
            entity.setInteraction(null);
            return true;
        }
        return false;
    }

    public boolean containsAll(Collection<?> objects) {
        return components.containsAll(objects);
    }

    public boolean addAll(Collection<? extends C> participants) {
        boolean added = false;
        for (C entity : participants){
            if (add(entity)){
                added = true;
            }
        }
        return added;
    }

    public boolean retainAll(Collection<?> objects) {
        for (C entity : this){
            if (!objects.contains(entity)){
                entity.setChangeListener(null);
                getInteractor().remove(entity.getInteractor());
                entity.setInteraction(null);
            }
        }
        return components.retainAll(objects);
    }

    public boolean removeAll(Collection<?> objects) {
        boolean removed = false;
        for (Object entity : objects){
            if (remove(entity)){
                removed = true;
            }
        }
        return removed;
    }

    public void clear() {
        for (C entity : this){
            entity.setChangeListener(null);
            entity.setInteraction(null);
        }
        components.clear();
        getInteractor().clear();
    }

    public void onInteractorUpdate(Participant entity, Interactor oldInteractor) {
        // check that the listener still makes sensr
        if (contains(entity)){
            boolean needsToRemoveOldInteractor = true;
            // check if an interactor is not in another entity that is kept.
            // remove any interactors that are kept with other entities
            for (C e : this){
                // we want to check if an interactor is the same as old interactor in another entry
                if (e != entity){
                    if (oldInteractor.equals(e.getInteractor())){
                        needsToRemoveOldInteractor = false;
                    }
                }
            }
            if (!needsToRemoveOldInteractor){
                getInteractor().remove(oldInteractor);
            }
            getInteractor().add(entity.getInteractor());
        }
    }
}
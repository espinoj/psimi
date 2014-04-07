package psidev.psi.mi.jami.xml.extension;

import psidev.psi.mi.jami.listener.ParticipantInteractorChangeListener;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.utils.CvTermUtils;
import psidev.psi.mi.jami.xml.utils.PsiXml25Utils;

import javax.xml.bind.annotation.XmlTransient;
import java.util.*;

/**
 * Abstract implementation of Xml participantPool
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>04/10/13</pre>
 */
@XmlTransient
public abstract class AbstractXmlParticipantPool<I extends Interaction, F extends Feature, C extends Participant> extends AbstractXmlParticipant<I,F> implements ParticipantPool<I,F,C>, ParticipantInteractorChangeListener {

    private Set<C> components;
    private CvTerm type;

    protected AbstractXmlParticipantPool() {
        super();
        initialiseComponentCandidatesSet();
    }

    public AbstractXmlParticipantPool(InteractorPool interactor) {
        super(interactor);
        initialiseComponentCandidatesSet();
    }

    public AbstractXmlParticipantPool(InteractorPool interactor, CvTerm bioRole) {
        super(interactor, bioRole);
        initialiseComponentCandidatesSet();
    }

    public AbstractXmlParticipantPool(InteractorPool interactor, Stoichiometry stoichiometry) {
        super(interactor, stoichiometry);
        initialiseComponentCandidatesSet();
    }

    public AbstractXmlParticipantPool(InteractorPool interactor, CvTerm bioRole, Stoichiometry stoichiometry) {
        super(interactor, bioRole, stoichiometry);
        initialiseComponentCandidatesSet();
    }

    protected void initialiseComponentCandidatesSet(){
        this.components = new HashSet<C>();
    }

    @Override
    public InteractorPool getInteractor() {
        return (InteractorPool) super.getInteractor();
    }

    @Override
    public void setInteractor(Interactor interactor) {
        throw new UnsupportedOperationException("Cannot set the interactor of an EntitySet as it is an interactorSet that is related to the interactors in the set of entities");
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

    public boolean addAll(Collection<? extends C> interactors) {
        boolean added = this.components.addAll(interactors);
        if (added){
            for (C entity : this){
                entity.setChangeListener(this);
                getInteractor().add(entity.getInteractor());
                entity.setInteraction(getInteraction());
            }
        }
        return added;
    }

    public boolean retainAll(Collection<?> objects) {
        boolean retain = components.retainAll(objects);
        if (retain){
            Collection<Interactor> interactors = new ArrayList<Interactor>(objects.size());
            for (Object o : objects){
                interactors.add(((Participant)o).getInteractor());
            }
            getInteractor().retainAll(interactors);
        }
        return retain;
    }

    public boolean removeAll(Collection<?> objects) {
        boolean remove = components.removeAll(objects);
        if (remove){
            Collection<Interactor> interactors = new ArrayList<Interactor>(objects.size());
            for (Object o : objects){
                Participant entity = (Participant)o;
                entity.setChangeListener(null);
                interactors.add(entity.getInteractor());
                entity.setInteraction(null);
            }
            // check if an interactor is not in another entity that is kept.
            // remove any interactors that are kept with other entities
            for (C entity : this){
                interactors.remove(entity.getInteractor());
            }
            getInteractor().removeAll(interactors);
        }
        return remove;
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

    @Override
    protected void initialiseUnspecifiedInteractor() {
        super.setInteractor(new XmlInteractorPool(PsiXml25Utils.UNSPECIFIED));
    }

    @Override
    public String toString() {
        return "Entity set: "+(getSourceLocator() != null ? getSourceLocator().toString():super.toString());
    }
}
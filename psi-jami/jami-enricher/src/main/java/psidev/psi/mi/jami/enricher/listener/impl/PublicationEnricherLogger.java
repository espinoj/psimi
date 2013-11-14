package psidev.psi.mi.jami.enricher.listener.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import psidev.psi.mi.jami.enricher.listener.EnrichmentStatus;
import psidev.psi.mi.jami.enricher.listener.PublicationEnricherListener;
import psidev.psi.mi.jami.model.Annotation;
import psidev.psi.mi.jami.model.Publication;
import psidev.psi.mi.jami.model.Xref;

import java.util.Date;

/**
 * A logging listener. It will display a message when each event if fired.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 31/07/13
 */
public class PublicationEnricherLogger implements PublicationEnricherListener {

    private static final Logger log = LoggerFactory.getLogger(PublicationEnricherLogger.class.getName());

    public void onEnrichmentComplete(Publication publication, EnrichmentStatus status, String message) {
        log.info(publication.toString()+" enrichment complete with status ["+status+"], message: "+message);
    }

    public void onEnrichmentError(Publication object, String message, Exception e) {
        log.error(object.toString() + " enrichment error, message: " + message, e);    }

    public void onPubmedIdUpdate(Publication publication, String oldPubmedId) {
        log.info(publication.toString()+" updated pubmedId to "+publication.getPubmedId()+" from "+oldPubmedId);
    }

    public void onDoiUpdate(Publication publication, String oldDoi) {
        log.info(publication.toString()+" updated Doi to "+publication.getDoi()+" from "+oldDoi);
    }

    public void onAddedIdentifier(Publication publication, Xref addedXref) {
        log.info(publication.toString()+" added identifier "+addedXref);
    }

    public void onRemovedIdentifier(Publication publication, Xref removedXref) {
        log.info(publication.toString()+" removed identifier "+removedXref);
    }

    public void onImexIdentifierAdded(Publication publication, Xref addedXref) {
        log.info(publication.toString()+" added Imex identifier "+addedXref);
    }

    public void onTitleUpdated(Publication publication, String oldTitle) {
        log.info(publication.toString()+" updated title to "+publication.getTitle()+" from "+oldTitle);
    }

    public void onJournalUpdated(Publication publication, String oldJournal) {
        log.info(publication.toString()+" updated journal to "+publication.getJournal()+" from "+oldJournal);
    }

    public void onPublicationDateUpdated(Publication publication, Date oldDate) {
        log.info(publication.toString()+" updated publication date to "+publication.getPublicationDate()+" from "+oldDate);
    }

    public void onAuthorAdded(Publication publication, String addedAuthor) {
        log.info(publication.toString()+" added author "+addedAuthor);
    }

    public void onAuthorRemoved(Publication publication, String removedAuthor) {
        log.info(publication.toString()+" removed author "+removedAuthor);
    }

    public void onAddedXref(Publication publication, Xref addedXref) {
        log.info(publication.toString()+" added Xref "+addedXref);
    }

    public void onRemovedXref(Publication publication, Xref removedXref) {
        log.info(publication.toString()+" removed Xref "+removedXref);
    }

    public void onAddedAnnotation(Publication publication, Annotation annotationAdded) {
        log.info(publication.toString()+" added annotation "+annotationAdded);
    }

    public void onRemovedAnnotation(Publication publication, Annotation annotationRemoved) {
        log.info(publication.toString()+" removed annotation "+annotationRemoved);
    }

    public void onReleaseDateUpdated(Publication publication, Date oldDate) {
        log.info(publication.toString()+" updated release date to "+publication.getReleasedDate()+" from "+oldDate);
    }
}
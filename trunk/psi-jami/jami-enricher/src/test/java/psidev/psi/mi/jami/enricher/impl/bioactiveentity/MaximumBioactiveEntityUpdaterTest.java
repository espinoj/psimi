package psidev.psi.mi.jami.enricher.impl.bioactiveentity;

import org.junit.Before;
import org.junit.Test;
import psidev.psi.mi.jami.bridges.fetcher.mock.FailingBioactiveEntityFetcher;
import psidev.psi.mi.jami.bridges.fetcher.mock.MockBioactiveEntityFetcher;
import psidev.psi.mi.jami.enricher.BioactiveEntityEnricher;
import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.listener.EnrichmentStatus;
import psidev.psi.mi.jami.enricher.listener.bioactiveentity.BioactiveEntityEnricherListener;
import psidev.psi.mi.jami.enricher.listener.bioactiveentity.BioactiveEntityEnricherListenerManager;
import psidev.psi.mi.jami.enricher.listener.bioactiveentity.BioactiveEntityEnricherLogger;
import psidev.psi.mi.jami.model.Alias;
import psidev.psi.mi.jami.model.BioactiveEntity;
import psidev.psi.mi.jami.model.Checksum;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.model.impl.DefaultBioactiveEntity;

import static junit.framework.Assert.*;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.fail;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 09/08/13
 */
public class MaximumBioactiveEntityUpdaterTest {


    String CHEBI_ID = "TEST_ID";
    String TEST_FULLNAME = "fullName";
    String TEST_SHORTNAME = "shortName";

    BioactiveEntityEnricher enricher;
    MockBioactiveEntityFetcher fetcher;
    BioactiveEntity persistentBioactiveEntity;
    int persistentInt = 0;

    @Before
    public void setUp(){
        fetcher = new MockBioactiveEntityFetcher();
        enricher = new MaximumBioactiveEntityUpdater(fetcher);

        persistentBioactiveEntity = null;
        persistentInt = 0;
    }



    // == RETRY ON FAILING FETCHER ============================================================

    /**
     * Creates a scenario where the fetcher always throws a bridge failure exception.
     * Shows that the query does not repeat infinitely.
     * @throws psidev.psi.mi.jami.enricher.exception.EnricherException
     */
    @Test(expected = EnricherException.class)
    public void test_bridgeFailure_throws_exception_when_persistent() throws EnricherException {

        persistentBioactiveEntity = new DefaultBioactiveEntity(TEST_SHORTNAME , TEST_FULLNAME);
        persistentBioactiveEntity.setChebi(CHEBI_ID);

        int timesToTry = -1;

        FailingBioactiveEntityFetcher fetcher = new FailingBioactiveEntityFetcher(timesToTry);
        fetcher.addEntry(CHEBI_ID , persistentBioactiveEntity);
        enricher.setBioactiveEntityFetcher(fetcher);

        enricher.enrichBioactiveEntity(persistentBioactiveEntity);

        fail("Exception should be thrown before this point");
    }

    /**
     * Creates a scenario where the fetcher does not retrieve an entry on its first attempt.
     * If the enricher re-queries the fetcher, it will eventually receive the entry.
     *
     * @throws EnricherException
     */
    @Test
    public void test_bridgeFailure_does_not_throw_exception_when_not_persistent() throws EnricherException {

        persistentBioactiveEntity = new DefaultBioactiveEntity(TEST_SHORTNAME , TEST_FULLNAME);
        persistentBioactiveEntity.setChebi(CHEBI_ID);

        int timesToTry = 3;
        assertTrue("The test can not be applied as the conditions do not invoke the required response. " +
                "Change the timesToTry." ,
                timesToTry < MaximumBioactiveEntityUpdater.RETRY_COUNT);

        FailingBioactiveEntityFetcher fetcher = new FailingBioactiveEntityFetcher(timesToTry);
        fetcher.addEntry(CHEBI_ID , persistentBioactiveEntity);
        enricher.setBioactiveEntityFetcher(fetcher);

        enricher.enrichBioactiveEntity(persistentBioactiveEntity);

        assertEquals(TEST_FULLNAME, persistentBioactiveEntity.getFullName() );
    }


    // == FAILURE ON NULL ======================================================================

    /**
     * Attempts to enrich a null CvTerm.
     * This should always cause an illegal argument exception
     * @throws EnricherException
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_enriching_with_null_CvTerm() throws EnricherException {
        BioactiveEntity nullBioactiveEntity = null;
        enricher.enrichBioactiveEntity(nullBioactiveEntity);
        fail("Exception should be thrown before this point");
    }

    /**
     * Attempts to enrich a legal cvTerm but with a null fetcher.
     * This should throw an illegal state exception.
     * @throws EnricherException
     */
    @Test(expected = IllegalStateException.class)
    public void test_enriching_with_null_CvTermFetcher() throws EnricherException {
        BioactiveEntity bioactiveEntity = new DefaultBioactiveEntity(TEST_SHORTNAME, TEST_FULLNAME);
        bioactiveEntity.setChebi(CHEBI_ID);

        enricher.setBioactiveEntityFetcher(null);
        assertNull(enricher.getBioactiveEntityFetcher());
        enricher.enrichBioactiveEntity(bioactiveEntity);
        fail("Exception should be thrown before this point");
    }

    @Test
    public void test_enrichment_completes_as_failed_when_no_entry_fetched() throws EnricherException {

        persistentBioactiveEntity = new DefaultBioactiveEntity(TEST_SHORTNAME , TEST_FULLNAME);
        fetcher.clearEntries();

        enricher.setBioactiveEntityEnricherListener( new BioactiveEntityEnricherListenerManager(
                new BioactiveEntityEnricherLogger() ,
                new BioactiveEntityEnricherListener() {
                    public void onEnrichmentComplete(BioactiveEntity object, EnrichmentStatus status, String message) {
                        assertTrue(object == persistentBioactiveEntity);
                        assertEquals(EnrichmentStatus.FAILED , status);
                        persistentInt ++;
                    }
                    public void onChebiUpdate(BioactiveEntity bioactiveEntity, String oldId) { fail("failed"); }
                    public void onSmileUpdate(BioactiveEntity bioactiveEntity, String oldSmile) { fail("failed"); }
                    public void onStandardInchiKeyUpdate(BioactiveEntity bioactiveEntity, String oldKey) { fail("failed"); }
                    public void onStandardInchiUpdate(BioactiveEntity bioactiveEntity, String oldInchi){ fail("failed"); }
                    public void onShortNameUpdate(BioactiveEntity interactor, String oldShortName){ fail("failed"); }
                    public void onFullNameUpdate(BioactiveEntity interactor, String oldFullName) { fail("failed"); }
                    public void onAddedOrganism(BioactiveEntity interactor){ fail("failed"); }
                    public void onAddedInteractorType(BioactiveEntity interactor) { fail("failed"); }
                    public void onAddedIdentifier(BioactiveEntity interactor, Xref added) { fail("failed"); }
                    public void onRemovedIdentifier(BioactiveEntity interactor, Xref removed) { fail("failed"); }
                    public void onAddedXref(BioactiveEntity interactor, Xref added){ fail("failed"); }
                    public void onRemovedXref(BioactiveEntity interactor, Xref removed) { fail("failed"); }
                    public void onAddedAlias(BioactiveEntity interactor, Alias added){ fail("failed"); }
                    public void onRemovedAlias(BioactiveEntity interactor, Alias removed) { fail("failed"); }
                    public void onAddedChecksum(BioactiveEntity interactor, Checksum added) { fail("failed"); }
                    public void onRemovedChecksum(BioactiveEntity interactor, Checksum removed) { fail("failed"); }
                }
        ));

        enricher.enrichBioactiveEntity(persistentBioactiveEntity);

        assertEquals(1, persistentInt);

    }


}

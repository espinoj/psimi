package psidev.psi.mi.jami.enricher.impl.organism;

import org.junit.Before;
import org.junit.Test;
import psidev.psi.mi.jami.bridges.fetcher.mockfetcher.organism.MockOrganismFetcher;
import psidev.psi.mi.jami.enricher.OrganismEnricher;
import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.impl.organism.listener.OrganismEnricherListener;
import psidev.psi.mi.jami.enricher.impl.organism.listener.OrganismEnricherListenerManager;
import psidev.psi.mi.jami.enricher.listener.EnrichmentStatus;
import psidev.psi.mi.jami.model.Alias;
import psidev.psi.mi.jami.model.Organism;
import psidev.psi.mi.jami.model.impl.DefaultAlias;
import psidev.psi.mi.jami.model.impl.DefaultOrganism;

import static junit.framework.Assert.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 24/05/13
 * Time: 14:04
 */
public class OrganismUpdaterMinimumTest {

    private OrganismEnricher organismEnricher;
    private MockOrganismFetcher fetcher;

    public Organism persistentOrganism;

    private static final String TEST_SCIENTIFICNAME = "test scientificName";
    private static final String TEST_COMMONNAME = "test commonName";
    private static final String TEST_OLD_SCIENTIFICNAME = "test old scientificName";
    private static final String TEST_OLD_COMMONNAME = "test old commonName";
    private static final int TEST_AC_FULL_ORG = 11111;
    private static final int TEST_AC_HALF_ORG = 55555;
    private static final int TEST_AC_CUSTOM_ORG = 99999;

    @Before
    public void initialiseFetcherAndEnricher() {
        persistentOrganism = null;
        this.fetcher = new MockOrganismFetcher();
        this.organismEnricher = new OrganismUpdaterMinimum();
        organismEnricher.setFetcher(fetcher);

        Organism fullOrganism = new DefaultOrganism(TEST_AC_FULL_ORG, TEST_COMMONNAME, TEST_SCIENTIFICNAME);
        fullOrganism.getAliases().add(new DefaultAlias("TestAlias"));
        fetcher.addNewOrganism(Integer.toString(TEST_AC_FULL_ORG), fullOrganism);

        Organism halfOrganism = new DefaultOrganism(TEST_AC_HALF_ORG);
        fetcher.addNewOrganism(Integer.toString(TEST_AC_HALF_ORG), halfOrganism);

    }

    // == SCIENTIFIC NAME =====================================================================================

    @Test
    public void test_set_scientificName_if_null() throws EnricherException {
        Organism fetchOrganism = new DefaultOrganism(TEST_AC_CUSTOM_ORG);
        fetchOrganism.setScientificName(TEST_SCIENTIFICNAME);
        fetcher.addNewOrganism(Integer.toString(TEST_AC_CUSTOM_ORG) , fetchOrganism);

        persistentOrganism = new DefaultOrganism(TEST_AC_CUSTOM_ORG);

        assertNull(persistentOrganism.getScientificName());

        organismEnricher.setOrganismEnricherListener(new OrganismEnricherListenerManager(
                // new OrganismEnricherLogger() ,
                new OrganismEnricherListener() {
                    public void onOrganismEnriched(Organism organism, EnrichmentStatus status, String message) {
                        assertTrue(persistentOrganism == organism);
                        assertEquals(EnrichmentStatus.SUCCESS , status);
                    }

                    public void onCommonNameUpdate(Organism organism, String oldCommonName){fail("Should not reach this point");}

                    public void onScientificNameUpdate(Organism organism, String oldScientificName) {
                        assertTrue(persistentOrganism == organism);
                        assertNull(oldScientificName);
                        assertEquals(TEST_SCIENTIFICNAME , organism.getScientificName());
                    }

                    public void onTaxidUpdate(Organism organism, String oldTaxid) {fail("Should not reach this point");}
                    public void onAddedAlias(Organism organism, Alias added)  {fail("Should not reach this point");}
                    public void onRemovedAlias(Organism organism, Alias removed)  {fail("Should not reach this point");}
                }
        ));

        this.organismEnricher.enrichOrganism(persistentOrganism);

        assertNotNull(persistentOrganism.getScientificName());
        assertEquals(TEST_SCIENTIFICNAME, persistentOrganism.getScientificName());
    }

    @Test
    public void test_set_scientificName_if_different() throws EnricherException {
        Organism fetchOrganism = new DefaultOrganism(TEST_AC_CUSTOM_ORG);
        fetchOrganism.setScientificName(TEST_SCIENTIFICNAME);
        fetcher.addNewOrganism(Integer.toString(TEST_AC_CUSTOM_ORG) , fetchOrganism);

        persistentOrganism = new DefaultOrganism(TEST_AC_CUSTOM_ORG);
        persistentOrganism.setScientificName(TEST_OLD_SCIENTIFICNAME);

        organismEnricher.setOrganismEnricherListener(new OrganismEnricherListenerManager(
                // new OrganismEnricherLogger() ,
                new OrganismEnricherListener() {
                    public void onOrganismEnriched(Organism organism, EnrichmentStatus status, String message) {
                        assertTrue(persistentOrganism == organism);
                        assertEquals(EnrichmentStatus.SUCCESS , status);
                    }

                    public void onCommonNameUpdate(Organism organism, String oldCommonName){fail("Should not reach this point");}

                    public void onScientificNameUpdate(Organism organism, String oldScientificName) {
                        assertTrue(persistentOrganism == organism);
                        assertEquals(TEST_OLD_SCIENTIFICNAME, oldScientificName);
                        assertEquals(TEST_SCIENTIFICNAME , organism.getScientificName());
                    }

                    public void onTaxidUpdate(Organism organism, String oldTaxid) {fail("Should not reach this point");}
                    public void onAddedAlias(Organism organism, Alias added)  {fail("Should not reach this point");}
                    public void onRemovedAlias(Organism organism, Alias removed)  {fail("Should not reach this point");}

                }
        ));

        this.organismEnricher.enrichOrganism(persistentOrganism);

        assertNotNull(persistentOrganism.getScientificName());
        assertEquals(TEST_SCIENTIFICNAME, persistentOrganism.getScientificName());
    }

    @Test
    public void test_do_not_set_scientificName_if_same() throws EnricherException {
        Organism fetchOrganism = new DefaultOrganism(TEST_AC_CUSTOM_ORG);
        fetchOrganism.setScientificName(TEST_SCIENTIFICNAME);
        fetcher.addNewOrganism(Integer.toString(TEST_AC_CUSTOM_ORG) , fetchOrganism);

        persistentOrganism = new DefaultOrganism(TEST_AC_CUSTOM_ORG);
        persistentOrganism.setScientificName(TEST_SCIENTIFICNAME);

        organismEnricher.setOrganismEnricherListener(new OrganismEnricherListenerManager(
                // new OrganismEnricherLogger() ,
                new OrganismEnricherListener() {
                    public void onOrganismEnriched(Organism organism, EnrichmentStatus status, String message) {
                        assertTrue(persistentOrganism == organism);
                        assertEquals(EnrichmentStatus.SUCCESS , status);
                    }
                    public void onCommonNameUpdate(Organism organism, String oldCommonName){fail("Should not reach this point");}
                    public void onScientificNameUpdate(Organism organism, String oldScientificName) {fail();}
                    public void onTaxidUpdate(Organism organism, String oldTaxid) {fail("Should not reach this point");}
                    public void onAddedAlias(Organism organism, Alias added)  {fail("Should not reach this point");}
                    public void onRemovedAlias(Organism organism, Alias removed)  {fail("Should not reach this point");}

                }
        ));

        this.organismEnricher.enrichOrganism(persistentOrganism);

        assertNotNull(persistentOrganism.getScientificName());
        assertEquals(TEST_SCIENTIFICNAME, persistentOrganism.getScientificName());
    }


    // == COMMON NAME ========================================================================================

    @Test
    public void test_set_commonName_if_null() throws EnricherException {
        Organism fetchOrganism = new DefaultOrganism(TEST_AC_CUSTOM_ORG);
        fetchOrganism.setCommonName(TEST_COMMONNAME);
        fetcher.addNewOrganism(Integer.toString(TEST_AC_CUSTOM_ORG) , fetchOrganism);

        persistentOrganism = new DefaultOrganism(TEST_AC_CUSTOM_ORG);

        assertNull(persistentOrganism.getCommonName());

        organismEnricher.setOrganismEnricherListener(new OrganismEnricherListenerManager(
                // new OrganismEnricherLogger() ,
                new OrganismEnricherListener() {
                    public void onOrganismEnriched(Organism organism, EnrichmentStatus status, String message) {
                        assertTrue(persistentOrganism == organism);
                        assertEquals(EnrichmentStatus.SUCCESS , status);
                    }

                    public void onCommonNameUpdate(Organism organism, String oldCommonName){
                        assertTrue(persistentOrganism == organism);
                        assertNull(oldCommonName);
                        assertEquals(TEST_COMMONNAME , organism.getCommonName());
                    }

                    public void onScientificNameUpdate(Organism organism, String oldScientificName){fail("Should not reach this point");}

                    public void onTaxidUpdate(Organism organism, String oldTaxid) {fail("Should not reach this point");}
                    public void onAddedAlias(Organism organism, Alias added)  {fail("Should not reach this point");}
                    public void onRemovedAlias(Organism organism, Alias removed)  {fail("Should not reach this point");}

                }
        ));

        this.organismEnricher.enrichOrganism(persistentOrganism);


        assertEquals(TEST_COMMONNAME, persistentOrganism.getCommonName());
    }

    @Test
    public void test_set_commonName_if_different() throws EnricherException {
        Organism fetchOrganism = new DefaultOrganism(TEST_AC_CUSTOM_ORG);
        fetchOrganism.setCommonName(TEST_COMMONNAME);
        fetcher.addNewOrganism(Integer.toString(TEST_AC_CUSTOM_ORG) , fetchOrganism);

        persistentOrganism = new DefaultOrganism(TEST_AC_CUSTOM_ORG);
        persistentOrganism.setCommonName(TEST_OLD_COMMONNAME);

        organismEnricher.setOrganismEnricherListener(new OrganismEnricherListenerManager(
                // new OrganismEnricherLogger() ,
                new OrganismEnricherListener() {
                    public void onOrganismEnriched(Organism organism, EnrichmentStatus status, String message) {
                        assertTrue(persistentOrganism == organism);
                        assertEquals(EnrichmentStatus.SUCCESS , status);
                    }

                    public void onCommonNameUpdate(Organism organism, String oldCommonName){
                        assertTrue(persistentOrganism == organism);
                        assertEquals(TEST_OLD_COMMONNAME, oldCommonName);
                        assertEquals(TEST_COMMONNAME , organism.getCommonName());
                    }
                    public void onScientificNameUpdate(Organism organism, String oldScientificName) {fail("Should not reach this point");}
                    public void onTaxidUpdate(Organism organism, String oldTaxid) {fail("Should not reach this point");}
                    public void onAddedAlias(Organism organism, Alias added)  {fail("Should not reach this point");}
                    public void onRemovedAlias(Organism organism, Alias removed)  {fail("Should not reach this point");}

                }
        ));

        this.organismEnricher.enrichOrganism(persistentOrganism);

        assertNotNull(persistentOrganism.getCommonName());
        assertEquals(TEST_COMMONNAME, persistentOrganism.getCommonName());
    }

    @Test
    public void test_do_not_set_commonName_if_same() throws EnricherException {
        Organism fetchOrganism = new DefaultOrganism(TEST_AC_CUSTOM_ORG);
        fetchOrganism.setCommonName(TEST_COMMONNAME);
        fetcher.addNewOrganism(Integer.toString(TEST_AC_CUSTOM_ORG) , fetchOrganism);

        persistentOrganism = new DefaultOrganism(TEST_AC_CUSTOM_ORG);
        persistentOrganism.setCommonName(TEST_COMMONNAME);

        assertNotNull(persistentOrganism.getCommonName());

        organismEnricher.setOrganismEnricherListener(new OrganismEnricherListenerManager(
                // new OrganismEnricherLogger() ,
                new OrganismEnricherListener() {
                    public void onOrganismEnriched(Organism organism, EnrichmentStatus status, String message) {
                        assertTrue(persistentOrganism == organism);
                        assertEquals(EnrichmentStatus.SUCCESS , status);
                    }
                    public void onCommonNameUpdate(Organism organism, String oldCommonName){fail("Should not reach this point");}
                    public void onScientificNameUpdate(Organism organism, String oldScientificName) {fail();}
                    public void onTaxidUpdate(Organism organism, String oldTaxid) {fail("Should not reach this point");}
                    public void onAddedAlias(Organism organism, Alias added)  {fail("Should not reach this point");}
                    public void onRemovedAlias(Organism organism, Alias removed)  {fail("Should not reach this point");}

                }
        ));

        this.organismEnricher.enrichOrganism(persistentOrganism);

        assertNotNull(persistentOrganism.getCommonName());
        assertEquals(TEST_COMMONNAME, persistentOrganism.getCommonName());
    }

}
package psidev.psi.mi.jami.bridges.uniprot;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.bridges.fetcher.GeneFetcher;
import psidev.psi.mi.jami.model.Alias;
import psidev.psi.mi.jami.model.Annotation;
import psidev.psi.mi.jami.model.Gene;
import psidev.psi.mi.jami.model.Xref;

import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 21/08/13
 */
public class UniprotGeneFetcherTest {

    protected final Logger log = LoggerFactory.getLogger(UniprotGeneFetcherTest.class.getName());

    @Test
    public void test() throws BridgeFailedException {
        GeneFetcher fetcher = new UniprotGeneFetcher();

        String[] refseqIds = {
                //"NP_001734.1",
                //"NM_001743.4",
                //"NP_005175.2",
                //"NM_005184.2",
                //"NP_008819.1",
                "NM_006888.4"};

        String[] ensemblIds = {
                //"ENST00000272298",
                //"ENSP00000272298",
                //"ENSG00000143933",
                //"ENST00000291295",
                //"ENSP00000291295",
                //"ENSG00000160014",
                //"ENST00000356978",
                //"ENSP00000349467",
                //"ENSG00000198668",
                //"ENST00000596362",
                //"ENSP00000472141",
                //"ENSG00000160014",
                "ENSG00000087245",
                "ENSG00000126001"};

        //We expect only one entrance                   "ENSG00000126001"
        for(String id : ensemblIds){
            Collection<Gene> candidatesList = fetcher.fetchByIdentifier(id);
            log.info("----- Entry "+id+" has "+candidatesList.size()+" ----------") ;
            readall(id , candidatesList);
        }


        log.info("______________________ HUMAN TESTS __________________");
        for(String id : ensemblIds){
            Collection<Gene> candidatesList = fetcher.fetchByIdentifier(id , 9606);
            log.info("----- Entry "+id+" has "+candidatesList.size()+" ----------") ;
            readall(id , candidatesList);
        }

    }

    private void readall(String id , Collection<Gene> candidatesList ){
        log.info("----- Entry "+id+" has "+candidatesList.size()+" ----------") ;
        for(Gene inta : candidatesList)  {
            log.info("******");
            log.info("---- full: " + inta.getFullName());
            log.info("---- short: " + inta.getShortName());
            log.info("---- organism: " + inta.getOrganism().toString());
            log.info("---- ensembl: " + inta.getEnsembl());
            log.info("---- ensemblGenomes: " + inta.getEnsemblGenome());
            log.info("---- refseq: " + inta.getRefseq());
            log.info("---- entrez: " + inta.getEntrezGeneId());
            //log.info(inta.)
            for(Alias alias : inta.getAliases()){
                log.info("   +--- Alias: "+alias.toString());
            }
            for(Annotation alias : inta.getAnnotations()){
                log.info("   +--- Anno: "+alias.toString());
            }
            for(Xref xref : inta.getXrefs())  {
                log.info("   +--- Xref: "+xref.toString());
            }
            log.info("********");
        }
    }

}

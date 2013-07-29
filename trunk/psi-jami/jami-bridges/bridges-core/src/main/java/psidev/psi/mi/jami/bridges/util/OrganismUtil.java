package psidev.psi.mi.jami.bridges.util;

import psidev.psi.mi.jami.model.Organism;
import psidev.psi.mi.jami.model.impl.DefaultOrganism;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 29/07/13
 */
public class OrganismUtil {

    public static Organism createSpecialistOrganism(int taxID){
        Organism organism;
        if ( taxID == -1 ) {
            organism = new DefaultOrganism( -1 );
            organism.setScientificName( "In vitro" );
            organism.setCommonName( "In vitro" );
            return organism;
        } else if ( taxID == -2 ) {
            organism = new DefaultOrganism( -2 );
            organism.setScientificName( "Chemical synthesis" );
            organism.setCommonName( "Chemical synthesis" );
            return organism;
        } else if ( taxID == -3 ) {
            organism = new DefaultOrganism( -3 );
            organism.setScientificName( "Unknown" );
            organism.setCommonName( "Unknown" );
            return organism;
        } else if ( taxID == -4 ) {
            organism = new DefaultOrganism( -4 );
            organism.setScientificName( "In vivo" );
            organism.setCommonName( "In vivo" );
            return organism;
        } else if ( taxID == -5 ) {
            organism = new DefaultOrganism( -5 );
            organism.setScientificName( "In Silico" );
            organism.setCommonName( "In Silico" );
            return organism;
        }
        else {
            return null;
        }
    }
}

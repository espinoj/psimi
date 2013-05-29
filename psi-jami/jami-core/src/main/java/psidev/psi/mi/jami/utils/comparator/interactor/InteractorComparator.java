package psidev.psi.mi.jami.utils.comparator.interactor;

import psidev.psi.mi.jami.model.*;

import java.util.Comparator;

/**
 * Generic Interactor Comparator.
 *
 * Bioactive entities come first, then proteins, then genes, then nucleic acids, then complexes and finally InteractorSet.
 * If two interactors are from the same Interactor interface, it will use a more specific Comparator :
 * - Uses AbstractBioactiveEntityComparator for comparing BioactiveEntity objects.
 * - Uses AbstractProteinComparator for comparing Protein objects.
 * - Uses AbstractGeneComparator for comparing Gene objects.
 * - Uses AbstractNucleicAcidComparator for comparing NucleicAcids objects.
 * - Uses ComplexComparator for comparing complexes
 * - Uses InteractorCandidatesComparator for comparing interactor candidates
 * - Uses AbstractPolymerComparator for comparing polymers
 * - use AbstractInteractorBaseComparator for comparing basic interactors that are not one of the above.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>16/01/13</pre>
 */

public class InteractorComparator implements Comparator<Interactor> {

    protected AbstractBioactiveEntityComparator bioactiveEntityComparator;
    protected AbstractGeneComparator geneComparator;
    protected AbstractProteinComparator proteinComparator;
    protected AbstractNucleicAcidComparator nucleicAcidComparator;
    protected Comparator<Interactor> interactorBaseComparator;
    protected ComplexComparator complexComparator;
    protected InteractorCandidatesComparator interactorCandaidatesComparator;
    protected AbstractPolymerComparator polymerComparator;

    /**
     * Creates a new InteractorComparator.
     * @param interactorBaseComparator : required to create more specific comparators and to compare basic interactor objects
     * @param complexComparator : required to compare complex objects
     */
    public InteractorComparator(Comparator<Interactor> interactorBaseComparator, ComplexComparator complexComparator, AbstractPolymerComparator polymerComparator,
                                AbstractBioactiveEntityComparator bioactiveEntityComparator, AbstractGeneComparator geneComparator,
                                AbstractNucleicAcidComparator nucleicAcidComparator, AbstractProteinComparator proteinComparator){
        if (interactorBaseComparator == null){
            throw new IllegalArgumentException("The interactorBaseComparator is required to create more specific interactor comparators and compares basic interactor properties. It cannot be null");
        }
        this.interactorBaseComparator = interactorBaseComparator;
        if (bioactiveEntityComparator == null){
            throw new IllegalArgumentException("The BioactiveEntityComparator is required to compare bioactive entities. It cannot be null");
        }
        this.bioactiveEntityComparator = bioactiveEntityComparator;
        if (geneComparator == null){
            throw new IllegalArgumentException("The GeneComparator is required to compare genes. It cannot be null");
        }
        this.geneComparator = geneComparator;
        if (proteinComparator == null){
            throw new IllegalArgumentException("The ProteinComparator is required to compare proteins. It cannot be null");
        }
        this.proteinComparator = proteinComparator;
        if (nucleicAcidComparator == null){
            throw new IllegalArgumentException("The NucleicAcidComparator is required to compare nucleicAcids. It cannot be null");
        }
        this.nucleicAcidComparator = nucleicAcidComparator;
        if (polymerComparator == null){
            throw new IllegalArgumentException("The PolymerComparator is required to compare polymers. It cannot be null");
        }
        this.polymerComparator = polymerComparator;

        if (complexComparator == null){
            throw new IllegalArgumentException("The ComplexComparator is required to compare complexes. It cannot be null");
        }
        this.complexComparator = complexComparator;
        this.interactorCandaidatesComparator = new InteractorCandidatesComparator(this);
    }

    public AbstractBioactiveEntityComparator getBioactiveEntityComparator() {
        return bioactiveEntityComparator;
    }

    public AbstractGeneComparator getGeneComparator() {
        return geneComparator;
    }

    public AbstractProteinComparator getProteinComparator() {
        return proteinComparator;
    }

    public AbstractNucleicAcidComparator getNucleicAcidComparator() {
        return nucleicAcidComparator;
    }

    public Comparator<Interactor> getInteractorBaseComparator() {
        return interactorBaseComparator;
    }

    public ComplexComparator getComplexComparator() {
        return complexComparator;
    }

    public AbstractPolymerComparator getPolymerComparator() {
        return polymerComparator;
    }

    public InteractorCandidatesComparator getInteractorCandidatesComparator() {
        return interactorCandaidatesComparator;
    }

    /**
     *
     * Bioactive entities come first, then proteins, then genes, then nucleic acids, then complexes and finally InteractorSet.
     * If two interactors are from the same Interactor interface, it will use a more specific Comparator :
     * - Uses AbstractBioactiveEntityComparator for comparing BioactiveEntity objects.
     * - Uses AbstractProteinComparator for comparing Protein objects.
     * - Uses AbstractGeneComparator for comparing Gene objects.
     * - Uses AbstractNucleicAcidComparator for comparing NucleicAcids objects.
     * - Uses InteractorCandidatesComparator for comparing interactor candidates
     * - Uses polymerComparator for comparing Polymer objects.
     * - use AbstractInteractorBaseComparator for comparing basic interactors that are not one of the above.

     * @param interactor1
     * @param interactor2
     * @return
     */
    public int compare(Interactor interactor1, Interactor interactor2) {
        int EQUAL = 0;
        int BEFORE = -1;
        int AFTER = 1;

        if (interactor1 == null && interactor2 == null){
            return EQUAL;
        }
        else if (interactor1 == null){
            return AFTER;
        }
        else if (interactor2 == null){
            return BEFORE;
        }
        else {
            // first check if both interactors are from the same interface

            // both are small molecules
            boolean isBioactiveEntity1 = interactor1 instanceof BioactiveEntity;
            boolean isBioactiveEntity2 = interactor2 instanceof BioactiveEntity;
            if (isBioactiveEntity1 && isBioactiveEntity2){
                return bioactiveEntityComparator.compare((BioactiveEntity) interactor1, (BioactiveEntity) interactor2);
            }
            // the small molecule is before
            else if (isBioactiveEntity1){
                return BEFORE;
            }
            else if (isBioactiveEntity2){
                return AFTER;
            }
            else {
                // both are proteins
                boolean isProtein1 = interactor1 instanceof Protein;
                boolean isProtein2 = interactor2 instanceof Protein;
                if (isProtein1 && isProtein2){
                    return proteinComparator.compare((Protein) interactor1, (Protein) interactor2);
                }
                // the protein is before
                else if (isProtein1){
                    return BEFORE;
                }
                else if (isProtein2){
                    return AFTER;
                }
                else {
                    // both are genes
                    boolean isGene1 = interactor1 instanceof Gene;
                    boolean isGene2 = interactor2 instanceof Gene;
                    if (isGene1 && isGene2){
                        return geneComparator.compare((Gene) interactor1, (Gene) interactor2);
                    }
                    // the gene is before
                    else if (isGene1){
                        return BEFORE;
                    }
                    else if (isGene2){
                        return AFTER;
                    }
                    else {
                        // both are nucleic acids
                        boolean isNucleicAcid1 = interactor1 instanceof NucleicAcid;
                        boolean isNucleicAcid2 = interactor2 instanceof NucleicAcid;
                        if (isNucleicAcid1 && isNucleicAcid2){
                            return nucleicAcidComparator.compare((NucleicAcid) interactor1, (NucleicAcid) interactor2);
                        }
                        // the nucleic acid is before
                        else if (isNucleicAcid1){
                            return BEFORE;
                        }
                        else if (isNucleicAcid2){
                            return AFTER;
                        }
                        else {
                            boolean isPolymer1 = interactor1 instanceof Polymer;
                            boolean isPolymer2 = interactor2 instanceof Polymer;
                            // both are polymers
                            if (isPolymer1 && isPolymer2){
                                return polymerComparator.compare((Polymer) interactor1, (Polymer) interactor2);
                            }
                            // the polymer is before
                            else if (isPolymer1){
                                return BEFORE;
                            }
                            else if (isPolymer2){
                                return AFTER;
                            }
                            else {
                                boolean isComplex1 = interactor1 instanceof Complex;
                                boolean isComplex2 = interactor2 instanceof Complex;
                                // both are complexes
                                if (isComplex1 && isComplex2){
                                    return complexComparator.compare((Complex) interactor1, (Complex) interactor2);
                                }
                                // the complex is before
                                else if (isComplex1){
                                    return BEFORE;
                                }
                                else if (isComplex2){
                                    return AFTER;
                                }
                                else {
                                    // both are interactor candidates
                                    boolean isCandidates1 = interactor1 instanceof InteractorSet;
                                    boolean isCandidates2 = interactor2 instanceof InteractorSet;
                                    if (isCandidates1 && isCandidates2){
                                        return complexComparator.compare((Complex) interactor1, (Complex) interactor2);
                                    }
                                    // the complex is before
                                    else if (isCandidates1){
                                        return BEFORE;
                                    }
                                    else if (isCandidates2){
                                        return AFTER;
                                    }
                                    else {
                                        return interactorBaseComparator.compare(interactor1, interactor2);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

package psidev.psi.mi.validator.extension.rules.dependencies;

import junit.framework.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.Experiment;
import psidev.psi.mi.jami.model.InteractionEvidence;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.model.impl.DefaultExperiment;
import psidev.psi.mi.jami.model.impl.DefaultInteractionEvidence;
import psidev.psi.mi.jami.model.impl.DefaultPublication;
import psidev.psi.mi.jami.utils.XrefUtils;
import psidev.psi.mi.validator.extension.rules.AbstractRuleTest;
import psidev.psi.tools.ontology_manager.impl.local.OntologyLoaderException;
import psidev.psi.tools.validator.ValidatorMessage;

import java.util.Collection;

/**
 * CrossReference2CrossReeferenceType Tester.
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id: CrossReference2CrossReferenceTypeDependencyRuleTest.java 56 2010-01-22 15:37:09Z marine.dumousseau@wanadoo.fr $
 * @since 2.0
 */
public class CrossReference2CrossReferenceTypeDependencyRuleTest extends AbstractRuleTest {

    /**
     *
     * @throws psidev.psi.tools.ontology_manager.impl.local.OntologyLoaderException
     */
    public CrossReference2CrossReferenceTypeDependencyRuleTest() throws OntologyLoaderException {
        super( CrossReference2CrossReferenceTypeDependencyRuleTest.class.getResourceAsStream( "/config/ontologies.xml" ) );
    }

    /**
     * Checks if the pubmed reference type of an interaction is ok. (for instance see-also)
     * @throws Exception
     */
    @Test
    public void check_Pubmed_Interaction_ok() throws Exception {
        InteractionEvidence interaction = new DefaultInteractionEvidence();
        Xref xRef = XrefUtils.createXrefWithQualifier("go", "MI:0448", "test", "process", "MI:0359");
        interaction.getXrefs().add(xRef);

        InteractionCrossReference2CrossReferenceTypeDependencyRule rule =
                new InteractionCrossReference2CrossReferenceTypeDependencyRule( ontologyMaganer );
        final Collection<ValidatorMessage> messages = rule.check( interaction );
        Assert.assertNotNull( messages );
        Assert.assertEquals( 0, messages.size() );
    }

    /**
     * Checks if there is a warning message when the pubmed reference type of an interaction is not a valid one. (for instance primary-reference)
     * @throws Exception
     */
    @Test
    public void check_Pubmed_Interaction_error() throws Exception {
        InteractionEvidence interaction = new DefaultInteractionEvidence();
        Xref xRef = XrefUtils.createXrefWithQualifier("pubmed", "MI:0446", "test", "primary-reference", "MI:0358");
        interaction.getXrefs().add(xRef);

        InteractionCrossReference2CrossReferenceTypeDependencyRule rule =
                new InteractionCrossReference2CrossReferenceTypeDependencyRule( ontologyMaganer );
        final Collection<ValidatorMessage> messages = rule.check( interaction );
        Assert.assertNotNull( messages );
        System.out.println(messages);
        Assert.assertEquals( 1, messages.size() );
    }

    /**
     * Checks if the pubmed cross reference type of an experiment is ok. (for instance primary-reference)
     * @throws Exception
     */
    @Test
    public void check_Pubmed_Experiment_Xref_ok() throws Exception {
        Experiment exp = new DefaultExperiment(new DefaultPublication());
        Xref xRef = XrefUtils.createXrefWithQualifier("pubmed", "MI:0446", "test", "primary-reference", "MI:0358");
        exp.getXrefs().add(xRef);

        ExperimentCrossReference2CrossReferenceTypeDependencyRule rule =
                new ExperimentCrossReference2CrossReferenceTypeDependencyRule( ontologyMaganer );
        final Collection<ValidatorMessage> messages = rule.check( exp );
        Assert.assertNotNull( messages );
        System.out.println(messages);
        Assert.assertEquals( 0, messages.size() );
    }

    /**
     * Checks if the pubmed in bibRef of an experiment has a valid reference type. (for instance primary-reference)
     * @throws Exception
     */
    @Test
    public void check_Pubmed_Experiment_BibRef_ok() throws Exception {
        Experiment exp = new DefaultExperiment(new DefaultPublication());
        Xref xRef = XrefUtils.createXrefWithQualifier("pubmed", "MI:0446", "test", "primary-reference", "MI:0358");
        exp.getPublication().getXrefs().add(xRef);

        ExperimentCrossReference2CrossReferenceTypeDependencyRule rule =
                new ExperimentCrossReference2CrossReferenceTypeDependencyRule( ontologyMaganer );
        final Collection<ValidatorMessage> messages = rule.check( exp );
        Assert.assertNotNull( messages );
        System.out.println(messages);
        Assert.assertEquals( 0, messages.size() );
    }

    /**
     * Checks if there is an error message when the pubmed in bibRef of an experiment hasn't a valid reference type. (for instance identical object)
     * @throws Exception
     */
    @Test
    public void check_Pubmed_Experiment_BibRef_error() throws Exception {
        Experiment exp = new DefaultExperiment(new DefaultPublication());
        Xref xRef = XrefUtils.createXrefWithQualifier("pubmed", "MI:0446", "test", "identical object", "MI:0356");
        exp.getXrefs().add(xRef);

        ExperimentCrossReference2CrossReferenceTypeDependencyRule rule =
                new ExperimentCrossReference2CrossReferenceTypeDependencyRule( ontologyMaganer );
        final Collection<ValidatorMessage> messages = rule.check( exp );
        Assert.assertNotNull( messages );
        System.out.println(messages);
        Assert.assertEquals( 1, messages.size() );
    }
}

package psidev.psi.mi.jami.utils.comparator.experiment;

import org.junit.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.CurationDepth;
import psidev.psi.mi.jami.model.Experiment;
import psidev.psi.mi.jami.model.impl.*;
import psidev.psi.mi.jami.utils.CvTermUtils;

/**
 * Unit tester for UnambiguousCuratedExperimentComparator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>31/05/13</pre>
 */

public class UnambiguousCuratedExperimentComparatorTest {

    private UnambiguousCuratedExperimentComparator comparator = new UnambiguousCuratedExperimentComparator();

    @Test
    public void test_experiment_null_after() throws Exception {
        Experiment exp1 = null;
        Experiment exp2 = new DefaultExperiment(null);

        Assert.assertTrue(comparator.compare(exp1, exp2) > 0);
        Assert.assertTrue(comparator.compare(exp2, exp1) < 0);

        Assert.assertFalse(UnambiguousCuratedExperimentComparator.areEquals(exp1, exp2));
        Assert.assertTrue(UnambiguousCuratedExperimentComparator.hashCode(exp1) != UnambiguousCuratedExperimentComparator.hashCode(exp2));
    }

    @Test
    public void test_different_publications() throws Exception {
        Experiment exp1 = new DefaultExperiment(new DefaultPublication("12345", CurationDepth.IMEx, new DefaultSource("intact")));
        Experiment exp2 = new DefaultExperiment(new DefaultPublication("12345", CurationDepth.IMEx, new DefaultSource("mint")));

        Assert.assertTrue(comparator.compare(exp1, exp2) < 0);
        Assert.assertTrue(comparator.compare(exp2, exp1) > 0);

        Assert.assertFalse(UnambiguousCuratedExperimentComparator.areEquals(exp1, exp2));
        Assert.assertTrue(UnambiguousCuratedExperimentComparator.hashCode(exp1) != UnambiguousCuratedExperimentComparator.hashCode(exp2));
    }

    @Test
    public void test_same_publications() throws Exception {
        Experiment exp1 = new DefaultExperiment(new DefaultPublication("12345"));
        Experiment exp2 = new DefaultExperiment(new DefaultPublication("12345"));

        Assert.assertTrue(comparator.compare(exp1, exp2) == 0);
        Assert.assertTrue(comparator.compare(exp2, exp1) == 0);

        Assert.assertTrue(UnambiguousCuratedExperimentComparator.areEquals(exp1, exp2));
        Assert.assertTrue(UnambiguousCuratedExperimentComparator.hashCode(exp1) == UnambiguousCuratedExperimentComparator.hashCode(exp2));
    }

    @Test
    public void test_same_publication_different_detection_methods() throws Exception {
        Experiment exp1 = new DefaultExperiment(new DefaultPublication("12345"));
        exp1.setInteractionDetectionMethod(CvTermUtils.createMICvTerm("test1", "MI:xxx1"));
        Experiment exp2 = new DefaultExperiment(new DefaultPublication("12345"));
        exp2.setInteractionDetectionMethod(CvTermUtils.createMICvTerm("test2", "MI:xxx2"));

        Assert.assertTrue(comparator.compare(exp1, exp2) < 0);
        Assert.assertTrue(comparator.compare(exp2, exp1) > 0);

        Assert.assertFalse(UnambiguousCuratedExperimentComparator.areEquals(exp1, exp2));
        Assert.assertTrue(UnambiguousCuratedExperimentComparator.hashCode(exp1) != UnambiguousCuratedExperimentComparator.hashCode(exp2));
    }

    @Test
    public void test_same_detection_methods() throws Exception {
        Experiment exp1 = new DefaultExperiment(new DefaultPublication("12345"));
        exp1.setInteractionDetectionMethod(CvTermUtils.createMICvTerm("test1", "MI:xxx1"));
        Experiment exp2 = new DefaultExperiment(new DefaultPublication("12345"));
        exp2.setInteractionDetectionMethod(CvTermUtils.createMICvTerm("test1", "MI:xxx1"));

        Assert.assertTrue(comparator.compare(exp1, exp2) == 0);
        Assert.assertTrue(comparator.compare(exp2, exp1) == 0);

        Assert.assertTrue(UnambiguousCuratedExperimentComparator.areEquals(exp1, exp2));
        Assert.assertTrue(UnambiguousCuratedExperimentComparator.hashCode(exp1) == UnambiguousCuratedExperimentComparator.hashCode(exp2));
    }

    @Test
    public void test_same_detection_method_different_host() throws Exception {
        Experiment exp1 = new DefaultExperiment(new DefaultPublication("12345"));
        exp1.setInteractionDetectionMethod(CvTermUtils.createMICvTerm("test1", "MI:xxx1"));
        exp1.setHostOrganism(new DefaultOrganism(-1));
        Experiment exp2 = new DefaultExperiment(new DefaultPublication("12345"));
        exp2.setInteractionDetectionMethod(CvTermUtils.createMICvTerm("test1", "MI:xxx1"));
        exp1.setHostOrganism(new DefaultOrganism(9606));

        Assert.assertTrue(comparator.compare(exp1, exp2) < 0);
        Assert.assertTrue(comparator.compare(exp2, exp1) > 0);

        Assert.assertFalse(UnambiguousCuratedExperimentComparator.areEquals(exp1, exp2));
        Assert.assertTrue(UnambiguousCuratedExperimentComparator.hashCode(exp1) != UnambiguousCuratedExperimentComparator.hashCode(exp2));
    }

    @Test
    public void test_same_host_organism() throws Exception {
        Experiment exp1 = new DefaultExperiment(new DefaultPublication("12345"));
        exp1.setInteractionDetectionMethod(CvTermUtils.createMICvTerm("test1", "MI:xxx1"));
        exp1.setHostOrganism(new DefaultOrganism(9606));
        Experiment exp2 = new DefaultExperiment(new DefaultPublication("12345"));
        exp2.setInteractionDetectionMethod(CvTermUtils.createMICvTerm("test1", "MI:xxx1"));
        exp2.setHostOrganism(new DefaultOrganism(9606));

        Assert.assertTrue(comparator.compare(exp1, exp2) == 0);
        Assert.assertTrue(comparator.compare(exp2, exp1) == 0);

        Assert.assertTrue(UnambiguousCuratedExperimentComparator.areEquals(exp1, exp2));
        Assert.assertTrue(UnambiguousCuratedExperimentComparator.hashCode(exp1) == UnambiguousCuratedExperimentComparator.hashCode(exp2));
    }

    @Test
    public void test_same_host_different_variable_parameters() throws Exception {
        Experiment exp1 = new DefaultExperiment(new DefaultPublication("12345"));
        exp1.setInteractionDetectionMethod(CvTermUtils.createMICvTerm("test1", "MI:xxx1"));
        exp1.setHostOrganism(new DefaultOrganism(-1));
        exp1.addVariableParameter(new DefaultVariableParameter("cell cycle"));
        Experiment exp2 = new DefaultExperiment(new DefaultPublication("12345"));
        exp2.setInteractionDetectionMethod(CvTermUtils.createMICvTerm("test1", "MI:xxx1"));
        exp2.setHostOrganism(new DefaultOrganism(-1));
        exp2.addVariableParameter(new DefaultVariableParameter("cell cycle"));
        exp2.addVariableParameter(new DefaultVariableParameter("PMA treatment"));

        Assert.assertTrue(comparator.compare(exp1, exp2) < 0);
        Assert.assertTrue(comparator.compare(exp2, exp1) > 0);

        Assert.assertFalse(UnambiguousCuratedExperimentComparator.areEquals(exp1, exp2));
        Assert.assertTrue(UnambiguousCuratedExperimentComparator.hashCode(exp1) != UnambiguousCuratedExperimentComparator.hashCode(exp2));
    }

    @Test
    public void test_same_variable_parameters() throws Exception {
        Experiment exp1 = new DefaultExperiment(new DefaultPublication("12345"));
        exp1.setInteractionDetectionMethod(CvTermUtils.createMICvTerm("test1", "MI:xxx1"));
        exp1.setHostOrganism(new DefaultOrganism(-1));
        exp1.addVariableParameter(new DefaultVariableParameter("PMA treatment"));
        exp1.addVariableParameter(new DefaultVariableParameter("cell cycle"));
        Experiment exp2 = new DefaultExperiment(new DefaultPublication("12345"));
        exp2.setInteractionDetectionMethod(CvTermUtils.createMICvTerm("test1", "MI:xxx1"));
        exp2.setHostOrganism(new DefaultOrganism(-1));
        exp2.addVariableParameter(new DefaultVariableParameter("cell cycle"));
        exp2.addVariableParameter(new DefaultVariableParameter("PMA treatment"));

        Assert.assertTrue(comparator.compare(exp1, exp2) == 0);
        Assert.assertTrue(comparator.compare(exp2, exp1) == 0);

        Assert.assertTrue(UnambiguousCuratedExperimentComparator.areEquals(exp1, exp2));
        Assert.assertTrue(UnambiguousCuratedExperimentComparator.hashCode(exp1) == UnambiguousCuratedExperimentComparator.hashCode(exp2));
    }
}

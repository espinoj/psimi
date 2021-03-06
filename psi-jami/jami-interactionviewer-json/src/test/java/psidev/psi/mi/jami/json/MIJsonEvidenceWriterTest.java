package psidev.psi.mi.jami.json;

import junit.framework.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.exception.IllegalParameterException;
import psidev.psi.mi.jami.factory.options.InteractionWriterOptions;
import psidev.psi.mi.jami.json.binary.MIJsonEvidenceWriter;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.model.impl.*;
import psidev.psi.mi.jami.utils.*;

import java.io.StringWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Unit tester for MIJsonEvidenceWriter
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>08/07/13</pre>
 */

public class MIJsonEvidenceWriterTest {
    private FeatureEvidence testFeature = null;

    @Test(expected = IllegalStateException.class)
    public void test_not_initialised_writer() {
        MIJsonEvidenceWriter binaryWriter = new MIJsonEvidenceWriter();
        binaryWriter.write(new DefaultInteractionEvidence());
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_not_initialised_no_options() {
        MIJsonEvidenceWriter binaryWriter = new MIJsonEvidenceWriter();
        binaryWriter.initialiseContext(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_create_writer_no_ontology_fetcher() {
        MIJsonEvidenceWriter binaryWriter = new MIJsonEvidenceWriter();
        binaryWriter.initialiseContext(null);
    }

    @Test
    public void test_write_binary() throws ParseException, IllegalParameterException {
        StringWriter writer = new StringWriter();
        MIJsonEvidenceWriter binaryWriter = new MIJsonEvidenceWriter(writer, null);

        InteractionEvidence binary = createInteractionEvidence();

        binaryWriter.start();

        binaryWriter.write(binary);
        binaryWriter.end();
        binaryWriter.close();

        String expected_json = getExpectedJson();

        Assert.assertEquals(expected_json, writer.toString());
    }

    @Test
    public void test_write_binary_list() throws ParseException, IllegalParameterException {
        StringWriter writer = new StringWriter();
        MIJsonEvidenceWriter binaryWriter = new MIJsonEvidenceWriter(writer, null);

        InteractionEvidence binary = createInteractionEvidence();

        binaryWriter.start();
        binaryWriter.write(Arrays.asList(binary, binary));
        binaryWriter.end();
        binaryWriter.close();

        String expected_line = getExpectedJson2();
        Assert.assertEquals(expected_line, writer.toString());
    }

    @Test
    public void test_write_binary2() throws ParseException, IllegalParameterException {
        StringWriter writer = new StringWriter();
        MIJsonEvidenceWriter binaryWriter = new MIJsonEvidenceWriter();
        Map<String, Object> options = new HashMap<String, Object>();
        options.put(InteractionWriterOptions.OUTPUT_OPTION_KEY, writer);
        binaryWriter.initialiseContext(options);

        InteractionEvidence binary = createInteractionEvidence();

        String expected_line = getExpectedJson();

        binaryWriter.start();

        binaryWriter.write(binary);
        binaryWriter.end();
        binaryWriter.close();

        Assert.assertEquals(expected_line, writer.toString());
    }

    private String getExpectedJson() {
        return "{\"data\":[{\"object\":\"interactor\",\"id\":\"uniprotkb_P12349\",\"type\":{\"id\":\"MI:0326\",\"name\":\"protein\"},\"organism\":{\"taxid\":\"9606\",\"common\":\"human\",\"scientific\":\"Homo Sapiens\"},\"identifier\":{\"db\":\"uniprotkb\",\"id\":\"P12349\"},\"label\":\"protein3\"},{\"object\":\"interactor\",\"id\":\"uniprotkb_P12345\",\"type\":{\"id\":\"MI:0326\",\"name\":\"protein\"},\"organism\":{\"taxid\":\"9606\",\"common\":\"human\",\"scientific\":\"Homo Sapiens\"},\"identifier\":{\"db\":\"uniprotkb\",\"id\":\"P12345\"},\"label\":\"protein1\"},{\"object\":\"interaction\",\"id\":\"intact_EBI-xxxx_0\",\"interactionType\":{\"id\":\"MI:xxxx\",\"name\":\"association\"},\"experiment\":{\"detmethod\":{\"id\":\"MI:xxx2\",\"name\":\"pull down\"},\"host\":{\"taxid\":\"-1\",\"common\":\"in vitro\"},\"pubid\":[{\"db\":\"pubmed\",\"id\":\"12345\"},\"imex\":\"IM-1\"],\"sourceDatabase\":{\"id\":\"MI:xxx1\",\"name\":\"intact\"}},\"confidences\":[{\"type\":\"author-score\",\"value\":\"high\"}],\"parameters\":[{\"type\":\"ic50\",\"value\":\"5x10^(-1)\",\"unit\":\"molar\"}],\"identifiers\":[{\"db\":\"intact\",\"id\":\"EBI-xxxx\"},{\"db\":\"imex\",\"id\":\"IM-1-1\"}],\"expansion\":{\"name\":\"spoke expansion\",\"id\":\"0\"},\"participants\":[{\"id\":\"1\",\"interactorRef\":\"uniprotkb_P12349\",\"stoichiometry\":\"1\",\"bioRole\":{\"id\":\"MI:xxx5\",\"name\":\"enzyme target\"},\"expRole\":{\"id\":\"MI:0496\",\"name\":\"bait\"},\"identificationMethods\":[{\"id\":\"MI:xxxx1\",\"name\":\"western blot\"}]},{\"id\":\"2\",\"interactorRef\":\"uniprotkb_P12345\",\"stoichiometry\":\"2\",\"bioRole\":{\"id\":\"MI:xxx5\",\"name\":\"enzyme target\"},\"expRole\":{\"id\":\"MI:xxx6\",\"name\":\"prey\"},\"identificationMethods\":[{\"id\":\"MI:xxxx1\",\"name\":\"western blot\"}],\"features\":[{\"id\":\"3\",\"category\":\"otherFeatures\",\"type\":{\"name\":\"binding site region\"},\"sequenceData\":[{\"pos\":\"1..3-6..7\",\"interactorRef\":\"uniprotkb_P12345\",\"participantRef\":\"2\"},{\"pos\":\">9->9\",\"interactorRef\":\"uniprotkb_P12345\",\"participantRef\":\"2\"}],\"InterPro\":\"interpro:xxxx\"}]}]},{\"object\":\"interactor\",\"id\":\"generated_1555920074\",\"type\":{\"id\":\"MI:0326\",\"name\":\"protein\"},\"label\":\"protein2\"},{\"object\":\"interaction\",\"id\":\"intact_EBI-xxxx_1\",\"interactionType\":{\"id\":\"MI:xxxx\",\"name\":\"association\"},\"experiment\":{\"detmethod\":{\"id\":\"MI:xxx2\",\"name\":\"pull down\"},\"host\":{\"taxid\":\"-1\",\"common\":\"in vitro\"},\"pubid\":[{\"db\":\"pubmed\",\"id\":\"12345\"},\"imex\":\"IM-1\"],\"sourceDatabase\":{\"id\":\"MI:xxx1\",\"name\":\"intact\"}},\"confidences\":[{\"type\":\"author-score\",\"value\":\"high\"}],\"parameters\":[{\"type\":\"ic50\",\"value\":\"5x10^(-1)\",\"unit\":\"molar\"}],\"identifiers\":[{\"db\":\"intact\",\"id\":\"EBI-xxxx\"},{\"db\":\"imex\",\"id\":\"IM-1-1\"}],\"expansion\":{\"name\":\"spoke expansion\",\"id\":\"0\"},\"participants\":[{\"id\":\"1\",\"interactorRef\":\"uniprotkb_P12349\",\"stoichiometry\":\"1\",\"bioRole\":{\"id\":\"MI:xxx5\",\"name\":\"enzyme target\"},\"expRole\":{\"id\":\"MI:0496\",\"name\":\"bait\"},\"identificationMethods\":[{\"id\":\"MI:xxxx1\",\"name\":\"western blot\"}]},{\"id\":\"4\",\"interactorRef\":\"generated_1555920074\",\"stoichiometry\":\"5\",\"bioRole\":{\"id\":\"MI:xxx5\",\"name\":\"enzyme target\"},\"expRole\":{\"id\":\"MI:xxx6\",\"name\":\"prey\"},\"identificationMethods\":[{\"id\":\"MI:xxxx2\",\"name\":\"predetermined\"}]}]}]}";
    }

    private String getExpectedJson2(){
        return "{\"data\":[{\"object\":\"interactor\",\"id\":\"uniprotkb_P12349\",\"type\":{\"id\":\"MI:0326\",\"name\":\"protein\"},\"organism\":{\"taxid\":\"9606\",\"common\":\"human\",\"scientific\":\"Homo Sapiens\"},\"identifier\":{\"db\":\"uniprotkb\",\"id\":\"P12349\"},\"label\":\"protein3\"},{\"object\":\"interactor\",\"id\":\"uniprotkb_P12345\",\"type\":{\"id\":\"MI:0326\",\"name\":\"protein\"},\"organism\":{\"taxid\":\"9606\",\"common\":\"human\",\"scientific\":\"Homo Sapiens\"},\"identifier\":{\"db\":\"uniprotkb\",\"id\":\"P12345\"},\"label\":\"protein1\"},{\"object\":\"interaction\",\"id\":\"intact_EBI-xxxx_0\",\"interactionType\":{\"id\":\"MI:xxxx\",\"name\":\"association\"},\"experiment\":{\"detmethod\":{\"id\":\"MI:xxx2\",\"name\":\"pull down\"},\"host\":{\"taxid\":\"-1\",\"common\":\"in vitro\"},\"pubid\":[{\"db\":\"pubmed\",\"id\":\"12345\"},\"imex\":\"IM-1\"],\"sourceDatabase\":{\"id\":\"MI:xxx1\",\"name\":\"intact\"}},\"confidences\":[{\"type\":\"author-score\",\"value\":\"high\"}],\"parameters\":[{\"type\":\"ic50\",\"value\":\"5x10^(-1)\",\"unit\":\"molar\"}],\"identifiers\":[{\"db\":\"intact\",\"id\":\"EBI-xxxx\"},{\"db\":\"imex\",\"id\":\"IM-1-1\"}],\"expansion\":{\"name\":\"spoke expansion\",\"id\":\"0\"},\"participants\":[{\"id\":\"1\",\"interactorRef\":\"uniprotkb_P12349\",\"stoichiometry\":\"1\",\"bioRole\":{\"id\":\"MI:xxx5\",\"name\":\"enzyme target\"},\"expRole\":{\"id\":\"MI:0496\",\"name\":\"bait\"},\"identificationMethods\":[{\"id\":\"MI:xxxx1\",\"name\":\"western blot\"}]},{\"id\":\"2\",\"interactorRef\":\"uniprotkb_P12345\",\"stoichiometry\":\"2\",\"bioRole\":{\"id\":\"MI:xxx5\",\"name\":\"enzyme target\"},\"expRole\":{\"id\":\"MI:xxx6\",\"name\":\"prey\"},\"identificationMethods\":[{\"id\":\"MI:xxxx1\",\"name\":\"western blot\"}],\"features\":[{\"id\":\"3\",\"category\":\"otherFeatures\",\"type\":{\"name\":\"binding site region\"},\"sequenceData\":[{\"pos\":\"1..3-6..7\",\"interactorRef\":\"uniprotkb_P12345\",\"participantRef\":\"2\"},{\"pos\":\">9->9\",\"interactorRef\":\"uniprotkb_P12345\",\"participantRef\":\"2\"}],\"InterPro\":\"interpro:xxxx\"}]}]},{\"object\":\"interactor\",\"id\":\"generated_1555920074\",\"type\":{\"id\":\"MI:0326\",\"name\":\"protein\"},\"label\":\"protein2\"},{\"object\":\"interaction\",\"id\":\"intact_EBI-xxxx_1\",\"interactionType\":{\"id\":\"MI:xxxx\",\"name\":\"association\"},\"experiment\":{\"detmethod\":{\"id\":\"MI:xxx2\",\"name\":\"pull down\"},\"host\":{\"taxid\":\"-1\",\"common\":\"in vitro\"},\"pubid\":[{\"db\":\"pubmed\",\"id\":\"12345\"},\"imex\":\"IM-1\"],\"sourceDatabase\":{\"id\":\"MI:xxx1\",\"name\":\"intact\"}},\"confidences\":[{\"type\":\"author-score\",\"value\":\"high\"}],\"parameters\":[{\"type\":\"ic50\",\"value\":\"5x10^(-1)\",\"unit\":\"molar\"}],\"identifiers\":[{\"db\":\"intact\",\"id\":\"EBI-xxxx\"},{\"db\":\"imex\",\"id\":\"IM-1-1\"}],\"expansion\":{\"name\":\"spoke expansion\",\"id\":\"0\"},\"participants\":[{\"id\":\"1\",\"interactorRef\":\"uniprotkb_P12349\",\"stoichiometry\":\"1\",\"bioRole\":{\"id\":\"MI:xxx5\",\"name\":\"enzyme target\"},\"expRole\":{\"id\":\"MI:0496\",\"name\":\"bait\"},\"identificationMethods\":[{\"id\":\"MI:xxxx1\",\"name\":\"western blot\"}]},{\"id\":\"4\",\"interactorRef\":\"generated_1555920074\",\"stoichiometry\":\"5\",\"bioRole\":{\"id\":\"MI:xxx5\",\"name\":\"enzyme target\"},\"expRole\":{\"id\":\"MI:xxx6\",\"name\":\"prey\"},\"identificationMethods\":[{\"id\":\"MI:xxxx2\",\"name\":\"predetermined\"}]}]}]}";
    }

    private InteractionEvidence createInteractionEvidence() throws ParseException, IllegalParameterException {
        ParticipantEvidence participantA = new DefaultParticipantEvidence(new DefaultProtein("protein1", "full name protein1"));
        // add identifiers
        participantA.getInteractor().getIdentifiers().add(XrefUtils.createUniprotIdentity("P12345"));
        participantA.getInteractor().getIdentifiers().add(XrefUtils.createUniprotSecondary("P12346"));
        participantA.getInteractor().getIdentifiers().add(XrefUtils.createXref("intact", "EBI-12345"));
        // add aliases
        participantA.getInteractor().getAliases().add(AliasUtils.createGeneName("brca2"));
        participantA.getInteractor().getAliases().add(AliasUtils.createGeneNameSynonym("brca2 synonym"));
        participantA.getAliases().add(AliasUtils.createAuthorAssignedName("\"bla\" author assigned name"));
        // species
        participantA.getInteractor().setOrganism(new DefaultOrganism(9606, "human", "Homo Sapiens"));
        //participantA.getAliases()
        ParticipantEvidence participantB = new DefaultParticipantEvidence(new DefaultProtein("protein2", "full name protein2"));
        // add identifiers
        ParticipantEvidence participantC = new DefaultParticipantEvidence(new DefaultProtein("protein3", "full name protein3"));
        participantC.getInteractor().getIdentifiers().add(XrefUtils.createUniprotIdentity("P12349"));
        participantC.getInteractor().getIdentifiers().add(XrefUtils.createUniprotSecondary("P12350"));
        participantC.getInteractor().getIdentifiers().add(XrefUtils.createXref("intact", "EBI-12347"));
        // species
        participantC.getInteractor().setOrganism(new DefaultOrganism(9606, "human", "Homo Sapiens"));
        // participant C is the spoke expansion bait
        participantC.setExperimentalRole(new DefaultCvTerm("bait", Participant.BAIT_ROLE_MI));
        // biological roles
        participantA.setBiologicalRole(CvTermUtils.createMICvTerm("enzyme target", "MI:xxx5"));
        participantB.setBiologicalRole(CvTermUtils.createMICvTerm("enzyme target", "MI:xxx5"));
        participantC.setBiologicalRole(CvTermUtils.createMICvTerm("enzyme target", "MI:xxx5"));
        // experimental roles
        participantA.setExperimentalRole(CvTermUtils.createMICvTerm("prey", "MI:xxx6"));
        participantB.setExperimentalRole(CvTermUtils.createMICvTerm("prey", "MI:xxx6"));
        // xrefs
        participantA.getXrefs().add(new DefaultXref(new DefaultCvTerm("go"), "GO:xxxxx", new DefaultCvTerm("component")));
        participantB.getInteractor().getXrefs().add(new DefaultXref(new DefaultCvTerm("interpro"), "INTERPRO:xxxxx"));
        participantC.getXrefs().add(new DefaultXref(new DefaultCvTerm("intact"),"EBI-x",new DefaultCvTerm("see-also")));
        // annotations
        participantA.getAnnotations().add(new DefaultAnnotation(new DefaultCvTerm("figure-legend"), "Fig 1."));
        participantB.getInteractor().getAnnotations().add(new DefaultAnnotation(new DefaultCvTerm("caution"), "sequence withdrawn from uniprot"));
        participantC.getAnnotations().add(new DefaultAnnotation(new DefaultCvTerm("isoform-comment"), "test comment"));
        // checksum
        participantA.getInteractor().getChecksums().add(ChecksumUtils.createRogid("xxxx1"));
        participantB.getInteractor().getChecksums().add(ChecksumUtils.createRogid("xxxx2"));
        participantC.getInteractor().getChecksums().add(ChecksumUtils.createRogid("xxxx4"));
        // features
        testFeature = new DefaultFeatureEvidence(new DefaultCvTerm("binding site", "binding site region", (String)null));
        testFeature.getRanges().add(RangeUtils.createFuzzyRange(1, 3, 6, 7));
        testFeature.getRanges().add(RangeUtils.createGreaterThanRange(9));
        testFeature.setInterpro("interpro:xxxx");
        participantA.addFeature(testFeature);
        // stc
        participantA.setStoichiometry(2);
        participantB.setStoichiometry(5);
        participantC.setStoichiometry(1);
        // participant identification method
        participantA.getIdentificationMethods().add(new DefaultCvTerm("western blot", "MI:xxxx1"));
        participantB.getIdentificationMethods().add(new DefaultCvTerm("predetermined", "MI:xxxx2"));
        participantC.getIdentificationMethods().add(new DefaultCvTerm("western blot", "MI:xxxx1"));

        InteractionEvidence interaction = new DefaultInteractionEvidence();
        interaction.addParticipant(participantA);
        interaction.addParticipant(participantB);
        interaction.addParticipant(participantC);

        // detection method
        interaction.setExperiment(new DefaultExperiment(new DefaultPublication()));
        interaction.getExperiment().setInteractionDetectionMethod(new DefaultCvTerm("pull down", "MI:xxx2"));
        // first author
        interaction.getExperiment().getPublication().setPublicationDate(new SimpleDateFormat("yyyy").parse("2006"));
        interaction.getExperiment().getPublication().getAuthors().add("author1");
        interaction.getExperiment().getPublication().getAuthors().add("author2");
        // publication identifiers
        interaction.getExperiment().getPublication().setPubmedId("12345");
        interaction.getExperiment().getPublication().assignImexId("IM-1");
        // interaction type
        interaction.setInteractionType(CvTermUtils.createMICvTerm("association", "MI:xxxx"));
        // source database
        Source source = new DefaultSource("intact");
        source.setMIIdentifier("MI:xxx1");
        interaction.getExperiment().getPublication().setSource(source);
        // interaction identifiers
        interaction.getIdentifiers().add(XrefUtils.createIdentityXref("intact", "EBI-xxxx"));
        interaction.getIdentifiers().add(XrefUtils.createXrefWithQualifier("imex", "IM-1-1", "imex-primary"));
        // confidences
        interaction.getConfidences().add(new DefaultConfidence(new DefaultCvTerm("author-score"), "high"));
        // xrefs
        interaction.getXrefs().add(new DefaultXref(new DefaultCvTerm("go"), "GO:xxxx2", new DefaultCvTerm("process")));
        // annotations
        interaction.getAnnotations().add(new DefaultAnnotation(new DefaultCvTerm("figure-legend"), "Fig 1."));
        // parameters
        interaction.getParameters().add(new DefaultParameter(new DefaultCvTerm("ic50"), "5x10^(-1)", new DefaultCvTerm("molar")));
        // creation date
        interaction.setCreatedDate(new SimpleDateFormat("yyyy/MM/dd").parse("2006/06/06"));
        // update date
        interaction.setUpdatedDate(new SimpleDateFormat("yyyy/MM/dd").parse("2007/01/01"));
        // checksum
        interaction.getChecksums().add(ChecksumUtils.createRigid("xxxx3"));
        // host organism
        interaction.getExperiment().setHostOrganism(new DefaultOrganism(-1, "in vitro"));
        // negative
        interaction.setNegative(true);
        return interaction;
    }
}

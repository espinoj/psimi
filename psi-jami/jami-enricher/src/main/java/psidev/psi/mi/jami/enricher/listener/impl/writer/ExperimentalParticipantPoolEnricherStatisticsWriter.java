package psidev.psi.mi.jami.enricher.listener.impl.writer;


import psidev.psi.mi.jami.enricher.listener.ExperimentalParticipantPoolEnricherListener;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.ExperimentalParticipantPool;
import psidev.psi.mi.jami.model.ParticipantCandidate;

import java.io.File;
import java.io.IOException;

/**
 * A statistics logger which records changes made by the enricher.
 * Each addition, removal or update is counted and, upon the completion of the enrichment of the object,
 * is logged in either a file of successes or failures depending on the enrichmentStatus.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 18/07/13
 */
public class ExperimentalParticipantPoolEnricherStatisticsWriter
        extends ParticipantEvidenceEnricherStatisticsWriter<ExperimentalParticipantPool>
        implements ExperimentalParticipantPoolEnricherListener {


    private static final String FILE_NAME = "Participant_evidencePool";

    /**
     * Uses the known name of the JamiObject type as the seed to generate names for the success an failure log files.
     * @throws java.io.IOException      Thrown if a problem is encountered with file location.
     */
    public ExperimentalParticipantPoolEnricherStatisticsWriter() throws IOException {
        super(FILE_NAME);
    }

    /**
     * Creates the files from the provided seed file name with 'success' and 'failure' appended.
     * @param fileName          The seed to base the names of the files on.
     * @throws java.io.IOException      Thrown if a problem is encountered with file location.
     */
    public ExperimentalParticipantPoolEnricherStatisticsWriter(String fileName) throws IOException {
        super(fileName);
    }

    /**
     * Uses the provided names to create the files for successful and failed enrichment logging.
     * @param successFileName   The exact name for the file to log successful enrichments in
     * @param failureFileName   The exact name for the file to log failed enrichments in
     * @throws java.io.IOException      Thrown if a problem is encountered with file location.
     */
    public ExperimentalParticipantPoolEnricherStatisticsWriter(String successFileName, String failureFileName) throws IOException {
        super(successFileName, failureFileName);
    }

    /**
     * Uses the exact files provided to log successful and failed enrichments.
     * @param successFile       The file to log successful enrichments in
     * @param failureFile       The file to log failed enrichments in.
     * @throws java.io.IOException      Thrown if a problem is encountered with file location.
     */
    public ExperimentalParticipantPoolEnricherStatisticsWriter(File successFile, File failureFile) throws IOException {
        super(successFile, failureFile);
    }

    public void onTypeUpdate(ExperimentalParticipantPool participant, CvTerm oldType) {
        checkObject(participant);
        incrementUpdateCount();
    }

    public void onAddedCandidate(ExperimentalParticipantPool participant, ParticipantCandidate added) {
        checkObject(participant);
        incrementAdditionCount();
    }

    public void onRemovedCandidate(ExperimentalParticipantPool participant, ParticipantCandidate removed) {
        checkObject(participant);
        incrementRemovedCount();
    }


    // ================================================================
}

package psidev.psi.mi.validator.extension.rules.dependencies;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Experiment;
import psidev.psi.mi.jami.model.InteractionEvidence;
import psidev.psi.mi.jami.model.ParticipantEvidence;
import psidev.psi.mi.validator.extension.MiContext;
import psidev.psi.mi.validator.extension.MiValidatorContext;
import psidev.psi.mi.validator.extension.rules.AbstractMIRule;
import psidev.psi.mi.validator.extension.rules.RuleUtils;
import psidev.psi.tools.ontology_manager.OntologyManager;
import psidev.psi.tools.ontology_manager.interfaces.OntologyAccess;
import psidev.psi.tools.validator.ValidatorException;
import psidev.psi.tools.validator.ValidatorMessage;

import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.Collections;

/**
 * Rule that allows to check whether the interaction detection method specified matches the biological rule.
 *
 * Rule Id = 12. See http://docs.google.com/Doc?docid=0AXS9Q1JQ2DygZGdzbnZ0Ym5fMHAyNnM3NnRj&hl=en_GB&pli=1
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id: InteractionDetectionMethod2BiologicalRoleDependencyRule.java 56 2010-01-22 15:37:09Z marine.dumousseau@wanadoo.fr $
 * @since 2.0
 */
public class InteractionDetectionMethod2BiologicalRoleDependencyRule extends AbstractMIRule<ParticipantEvidence> {

    private static final Log log = LogFactory.getLog( InteractionDetectionMethod2BiologicalRoleDependencyRule.class );

    private DependencyMapping mapping;

    public InteractionDetectionMethod2BiologicalRoleDependencyRule( OntologyManager ontologyManager ) {
        super( ontologyManager, ParticipantEvidence.class );
        MiValidatorContext validatorContext = MiValidatorContext.getCurrentInstance();

        OntologyAccess mi = ontologyManager.getOntologyAccess( "MI" );
        String fileName = validatorContext.getValidatorConfig().getInteractionDetectionMethod2BiologicalRole();
        
        try {

            URL resource = InteractionDetectionMethod2BiologicalRoleDependencyRule.class
                    .getResource( fileName );
            mapping = new DependencyMapping();

            mapping.buildMappingFromFile( mi, resource );

        } catch (IOException e) {
            throw new ValidatorRuleException("We can't build the map containing the dependencies from the file " + fileName, e);
        } catch (ValidatorException e) {
            throw new ValidatorRuleException("We can't build the map containing the dependencies from the file " + fileName, e);
        }
        // describe the rule.
        setName( "Dependency Check : Interaction detection method and participant's biological role" );
        setDescription( "Checks that each association interaction detection method - participant's biological role is valid and respects IMEx curation rules");
        addTip( "Search the possible terms for interaction detection method and biological role on http://www.ebi.ac.uk/ontology-lookup/browse.do?ontName=MI" );
        addTip( "Look at the file http://psimi.googlecode.com/svn/trunk/validator/psimi-schema-validator/src/main/resources/InteractionDetectionMethod2BiologicalRole.tsv for the possible dependencies interaction detection method - biological role" );        
    }

    /**
     * For each experiment associated with this interaction, collect all respective participants and their biological roles and
     * check if the dependencies are correct.
     *
     * @param participant a participant to check on.
     * @return a collection of validator messages.
     *         if we fail to retreive the MI Ontology.
     */
    public Collection<ValidatorMessage> check( ParticipantEvidence participant ) throws ValidatorException {

        Collection<ValidatorMessage> messages = Collections.EMPTY_LIST;

        CvTerm biolRole = participant.getBiologicalRole();

        if (participant.getInteraction() != null){
            InteractionEvidence interaction = participant.getInteraction();
            Experiment exp = interaction.getExperiment();

            if (exp != null){
                CvTerm method = exp.getInteractionDetectionMethod();
                // build a context in case of error
                MiContext context = RuleUtils.buildContext(participant, "participant");
                context.addAssociatedContext(RuleUtils.buildContext(exp, "experiment"));

                messages=mapping.check( method, biolRole, context, this );
            }
        }

        return messages;
    }

    public String getId() {
        return "R50";
    }
}

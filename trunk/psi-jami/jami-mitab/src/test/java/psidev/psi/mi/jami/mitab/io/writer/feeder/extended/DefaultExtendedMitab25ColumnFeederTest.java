package psidev.psi.mi.jami.mitab.io.writer.feeder.extended;

import junit.framework.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.Alias;
import psidev.psi.mi.jami.model.Participant;
import psidev.psi.mi.jami.tab.extension.MitabAlias;
import psidev.psi.mi.jami.tab.extension.MitabParticipant;
import psidev.psi.mi.jami.tab.extension.MitabProtein;
import psidev.psi.mi.jami.tab.io.writer.feeder.extended.DefaultExtendedMitab25ColumnFeeder;
import psidev.psi.mi.jami.utils.AliasUtils;

import java.io.IOException;
import java.io.StringWriter;

/**
 * Unit tester for DefaultExtendedMitab25ColumnFeeder
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>25/06/13</pre>
 */

public class DefaultExtendedMitab25ColumnFeederTest {

    @Test(expected = ClassCastException.class)
    public void write_aliases_no_mitab() throws IOException {
        StringWriter writer = new StringWriter();
        DefaultExtendedMitab25ColumnFeeder feeder = new DefaultExtendedMitab25ColumnFeeder(writer);

        Participant participant_null = null;
        Participant participant_with_aliases = new MitabParticipant(new MitabProtein("p12345_human", "protein:test"));
        participant_with_aliases.getInteractor().getAliases().add(AliasUtils.createGeneName("test gene"));
        participant_with_aliases.getInteractor().getAliases().add(AliasUtils.createAlias("synonym", "test synonym"));

        feeder.writeAliases(participant_null);
        Assert.assertEquals("-", writer.toString());

        writer = new StringWriter();
        feeder = new DefaultExtendedMitab25ColumnFeeder(writer);
        feeder.writeAliases(participant_with_aliases);
    }

    @Test
    public void write_aliases() throws IOException {
        StringWriter writer = new StringWriter();
        DefaultExtendedMitab25ColumnFeeder feeder = new DefaultExtendedMitab25ColumnFeeder(writer);

        Participant participant_null = null;
        Participant participant_no_aliases = new MitabParticipant(new MitabProtein("p12345_human", "protein test"));
        Participant participant_with_aliases = new MitabParticipant(new MitabProtein("p12345_human", "protein:test"));
        participant_with_aliases.getInteractor().getAliases().add(new MitabAlias("mint", "test gene", "gene name"));
        participant_with_aliases.getInteractor().getAliases().add(new MitabAlias("intact", "test name"));

        feeder.writeAliases(participant_null);
        Assert.assertEquals("-", writer.toString());

        writer = new StringWriter();
        feeder = new DefaultExtendedMitab25ColumnFeeder(writer);
        feeder.writeAliases(participant_no_aliases);
        Assert.assertEquals("psi-mi:p12345_human(display_short)|psi-mi:protein test(display_long)", writer.toString());

        writer = new StringWriter();
        feeder = new DefaultExtendedMitab25ColumnFeeder(writer);
        feeder.writeAliases(participant_with_aliases);
        Assert.assertEquals("psi-mi:p12345_human(display_short)|psi-mi:\"protein:test\"(display_long)|mint:test gene(gene name)|intact:test name", writer.toString());
    }

    @Test(expected = ClassCastException.class)
    public void write_alias_not_mitab() throws IOException {
        StringWriter writer = new StringWriter();
        DefaultExtendedMitab25ColumnFeeder feeder = new DefaultExtendedMitab25ColumnFeeder(writer);

        Alias alias_null = null;
        Alias alias = AliasUtils.createGeneName("test gene");

        feeder.writeAlias(alias_null);
        Assert.assertEquals("", writer.toString());

        writer = new StringWriter();
        feeder = new DefaultExtendedMitab25ColumnFeeder(writer);
        feeder.writeAlias(alias);
    }

    @Test
    public void write_alias() throws IOException {
        StringWriter writer = new StringWriter();
        DefaultExtendedMitab25ColumnFeeder feeder = new DefaultExtendedMitab25ColumnFeeder(writer);

        Alias alias_null = null;
        Alias alias = new MitabAlias("mint", "test gene", "gene name");
        Alias alias_no_type = new MitabAlias("intact", "test:name");

        feeder.writeAlias(alias_null);
        Assert.assertEquals("", writer.toString());

        writer = new StringWriter();
        feeder = new DefaultExtendedMitab25ColumnFeeder(writer);
        feeder.writeAlias(alias);
        Assert.assertEquals("mint:test gene(gene name)", writer.toString());

        writer = new StringWriter();
        feeder = new DefaultExtendedMitab25ColumnFeeder(writer);
        feeder.writeAlias(alias_no_type);
        Assert.assertEquals("intact:\"test:name\"", writer.toString());
    }
}

package org.pf1etools.roll20npc.parser;

import static org.fest.assertions.api.Assertions.assertThat; // main one

import java.text.ParseException;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.pf1etools.creature.Creature;
import org.pf1etools.creature.utils.Sense;
import org.pf1etools.roll20npc.parser.TextParser;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("Dev")
public class BabParserTest
{
	@Test
	public void testStatBabCmbCmdParser1() throws ParseException
	{
		Creature character = new Creature();
		TextParser.parseBabCmbCmd(character, "Base Atk +0; CMB –2; CMD 3");
		assertThat(character.stats.getBab()).isEqualTo(0);
		assertThat(character.stats.getCmb()).isEqualTo(-2);
		assertThat(character.stats.getCmd()).isEqualTo(3);
	}

	@Test
	public void testStatBabCmbCmdParser2() throws ParseException
	{
		Creature character = new Creature();
		TextParser.parseBabCmbCmd(character, "Base Atk +1; CMB +0; CMD 12");
		assertThat(character.stats.getBab()).isEqualTo(1);
		assertThat(character.stats.getCmb()).isEqualTo(0);
		assertThat(character.stats.getCmd()).isEqualTo(12);
	}

	@Test
	public void testStatBabCmbCmdParser3() throws ParseException
	{
		Creature character = new Creature();
		TextParser.parseBabCmbCmd(character, "Base Atk +5; CMB +9; CMD 21 (25 vs. trip)");
		assertThat(character.stats.getBab()).isEqualTo(5);
		assertThat(character.stats.getCmb()).isEqualTo(9);
		assertThat(character.stats.getCmd()).isEqualTo(21);
		assertThat(character.stats.getCmdNotes()).isEqualTo("25 vs. trip");
	}

	@Test
	public void testStatBabCmbCmdParser4() throws ParseException
	{
		Creature character = new Creature();
		TextParser.parseBabCmbCmd(character, "Base Atk +5; CMB +10 (+14 grapple); CMD 20 (28 vs. trip)");
		assertThat(character.stats.getBab()).isEqualTo(5);
		assertThat(character.stats.getCmb()).isEqualTo(10);
		assertThat(character.stats.getCmbNotes()).isEqualTo("+14 grapple");
		assertThat(character.stats.getCmd()).isEqualTo(20);
		assertThat(character.stats.getCmdNotes()).isEqualTo("28 vs. trip");
	}
}

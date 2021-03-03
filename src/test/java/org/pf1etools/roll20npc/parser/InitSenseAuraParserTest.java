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
public class InitSenseAuraParserTest
{
	
	@Test
	public void testSicnavierInit() throws ParseException
	{
		Creature character = new Creature();
		TextParser.parseInitAndSensesAndAura(character, "Init +2; Sense dragon sense; Perception +40");
		TextParser.parseAura(character, "Aura frightful aura (360 ft., DC 32)");
		assertThat(character.getInitiativeMod()).isEqualTo(2);
		assertThat(character.getSenses()).isEqualTo("dragon sense");
		assertThat(character.getAura()).isEqualTo("frightful aura (360 ft., DC 32)");
	}

	
	@Test
	public void testPositiveInit() throws ParseException
	{
		Creature character = new Creature();
		TextParser.parseInitAndSensesAndAura(character, "Init +2; Senses darkvision 60 ft., low-light vision, scent; Perception +12");
		assertThat(character.getInitiativeMod()).isEqualTo(2);
	}

	@Test
	public void testNegativeInit() throws ParseException
	{
		Creature character = new Creature();
		TextParser.parseInitAndSensesAndAura(character, "Init -1; Senses darkvision 60 ft., low-light vision, scent; Perception +12");
		assertThat(character.getInitiativeMod()).isEqualTo(-1);
	}

	@Test
	public void testZeroInit() throws ParseException
	{
		Creature character = new Creature();
		TextParser.parseInitAndSensesAndAura(character, "Init +0; Senses darkvision 60 ft., Perception +5");
		assertThat(character.getInitiativeMod()).isEqualTo(0);
	}

	@Test
	public void testNoInitSemicolonInit() throws ParseException
	{
		Creature character = new Creature();
		TextParser.parseInitAndSensesAndAura(character, "Init -1 Senses darkvision 60 ft., low-light vision; Perception -5");
		assertThat(character.getInitiativeMod()).isEqualTo(-1);
	}

	@Test
	public void testSenses() throws ParseException
	{
		Creature character = new Creature();
		TextParser.parseInitAndSensesAndAura(character, "Init -1 Senses darkvision 60 ft., low-light vision; Perception -5");
		assertThat(character.getInitiativeMod()).isEqualTo(-1);
		assertThat(character.getSenses()).contains("darkvision");
		assertThat(character.getSenses()).contains("low-light vision");
	}

	@Test
	public void testSensesBadDistance() throws ParseException
	{
		Creature character = new Creature();
		TextParser.parseInitAndSensesAndAura(character, "Init -1 Senses darkvision 60a ft., low-light vision; Perception -5");
		assertThat(character.getInitiativeMod()).isEqualTo(-1);
		assertThat(character.getSenses()).contains("darkvision");
		assertThat(character.getSenses()).contains("low-light vision");
	}

	@Disabled
	public void testSensesBadDistanceBeforeNum() throws ParseException
	{
		Creature character = new Creature();
		TextParser.parseInitAndSensesAndAura(character, "Init -1 Senses darkvision a60 ft., low-light vision; Perception -5");
		assertThat(character.getInitiativeMod()).isEqualTo(-1);
		assertThat(character.getSenses()).contains("darkvision");
		assertThat(character.getSenses()).contains("low-light vision");
	}

	@Test
	public void testParsingPerceptionNegative() throws ParseException
	{
		Creature character = new Creature();
		TextParser.parseInitAndSensesAndAura(character, "Init -1 Senses darkvision 60 ft, low-light vision; Perception -5");
		assertThat(character.skills.getPerceptionMod()).isEqualTo(-5);
	}

	@Test
	public void testParsingPerceptionZero() throws ParseException
	{
		Creature character = new Creature();
		TextParser.parseInitAndSensesAndAura(character, "Init -1 Senses darkvision 60 ft, low-light vision; Perception +0");
		assertThat(character.skills.getPerceptionMod()).isEqualTo(0);
	}

	@Test
	public void testParsingPerceptionPositive() throws ParseException
	{
		Creature character = new Creature();
		TextParser.parseInitAndSensesAndAura(character, "Init -1 Senses darkvision 60 ft, low-light vision; Perception +10");
		assertThat(character.skills.getPerceptionMod()).isEqualTo(10);
	}

	@Disabled // perception needed
	public void testParsingNoPerceptionNoSemicolon() throws ParseException
	{
		Creature character = new Creature();
		TextParser.parseInitAndSensesAndAura(character, "Init -1 Senses darkvision 60 ft, low-light vision");
		assertThat(character.skills.getPerceptionMod()).isNull();
	}

	@Test
	public void testParsingBalorLord() throws ParseException
	{
		Creature character = new Creature();
		TextParser.parseInitAndSensesAndAura(character, "Init +10; Senses darkvision 60 ft., low-light vision, true seeing; Perception +44; Aura flaming body, unholy aura (DC 27)");
		assertThat(character.getInitiativeMod()).isEqualTo(10);
		assertThat(character.getSenses()).contains("darkvision");
		assertThat(character.getSenses()).contains("low-light vision");
		assertThat(character.skills.getPerceptionMod()).isEqualTo(44);
	}

	@Test
	public void testParsingUnknownSense() throws ParseException
	{
		Creature character = new Creature();
		TextParser.parseInitAndSensesAndAura(character, "Init +10; Senses darkvision 60 ft., low-light vision, true seeing, unknown sense; Perception +44; Aura flaming body, unholy aura (DC 27)");
		assertThat(character.getInitiativeMod()).isEqualTo(10);
		assertThat(character.skills.getPerceptionMod()).isEqualTo(44);
	}

	@Test
	public void testParsingEmdashAsNegativeSense() throws ParseException
	{
		Creature character = new Creature();
		TextParser.parseInitAndSensesAndAura(character, "Init +0; Senses Perception –1");
		assertThat(character.getInitiativeMod()).isEqualTo(0);
		assertThat(character.getSenses()).isNull();
		assertThat(character.skills.getPerceptionMod()).isEqualTo(-1);
	}

	@Test
	public void testOldLegacySpotSense() throws ParseException
	{
		Creature character = new Creature();
		TextParser.parseInitAndSensesAndAura(character, "Init +2; Senses low-light vision; Listen +3, Spot +3");
		assertThat(character.getInitiativeMod()).isEqualTo(2);
		assertThat(character.getSenses()).contains("Listen");
		assertThat(character.getSenses()).contains("low-light vision");
		assertThat(character.skills.getPerceptionMod()).isEqualTo(3);
	}
}

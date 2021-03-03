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
public class XpParserTest
{
	@Test
	public void testXpParser() throws ParseException
	{
		Creature creature = new Creature();
		TextParser.parseXp(creature, "XP 12,800");
		assertThat(creature.getXp()).isEqualTo(12800);
	}
	
	@Test
	public void testXpParserHasPlusOk() throws ParseException
	{
		Creature creature = new Creature();
		TextParser.parseXp(creature, "XP +12,800");
		assertThat(creature.getXp()).isEqualTo(12800);
	}
	
	@Test
	public void testXpParserHasNegativeOk() throws ParseException
	{
		Creature creature = new Creature();
		TextParser.parseXp(creature, "XP -12,800");
		assertThat(creature.getXp()).isEqualTo(-12800);
	}
	
}

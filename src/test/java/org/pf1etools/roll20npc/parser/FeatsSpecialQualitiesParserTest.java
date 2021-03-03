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
public class FeatsSpecialQualitiesParserTest
{
	@Test
	public void testFeatsParser1() throws ParseException
	{
		Creature character = new Creature();
		TextParser.parseFeats(character, "Feats Awesome Blow, Blind-Fight, Bleeding Critical, Cleave, Combat Reflexes, Critical Focus, Great Cleave, Great Fortitude, Improved Bull Rush, Improved Critical (bite), Improved Initiative, Lightning Reflexes, Power Attack, Run, Staggering Critical");
		assertThat(character.getFeats()).isEqualTo("Awesome Blow, Blind-Fight, Bleeding Critical, Cleave, Combat Reflexes, Critical Focus, Great Cleave, Great Fortitude, Improved Bull Rush, Improved Critical (bite), Improved Initiative, Lightning Reflexes, Power Attack, Run, Staggering Critical");
	}
	
	@Test
	public void testSpecialQualitiesParser() throws ParseException
	{
		Creature character = new Creature();
		TextParser.parseSpecialQualities(character, "SQ urban defender");
		assertThat(character.getSpecialQualities()).isEqualTo("urban defender");
	}

}

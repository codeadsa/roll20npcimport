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
public class SpellLikeKnownPreparedParserTest
{
	@Test
	public void testSpellLikeParser() throws ParseException
	{
		Creature creature = new Creature();
//		TextParser.parseSpellLike(creature, "Spell-Like Abilities (CL 24th; concentration +30)\r\n" + 
//				"Constant—speak with animals\r\n" + 
//				"At will—beast shape II, command (DC 17), detect thoughts, elemental body III (air or water elementals only), greater teleport (self plus 50 lbs. of objects only), gust of wind, hold monster (DC 20), identify, light, lightning bolt (DC 19), mage hand, message\r\n" + 
//				"7/day—break enchantment, cure serious wounds, neutralize poison, remove disease\r\n" + 
//				"3/day—control water, control weather, control winds, heal, plane shift (DC 23)");
//		
	}
}

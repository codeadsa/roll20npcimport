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
public class DefenseAbilitiesParserTest
{
	@Test
	public void testStatAlignSizeTypeParser1() throws ParseException
	{
		Creature character = new Creature();
		TextParser.parseAlignSizeType(character, "LE Large outsider (devil, evil, extraplanar, lawful)");
		assertThat(character.getAlignment()).isEqualTo("LE");
		assertThat(character.getSize()).isEqualTo("Large");
		assertThat(character.getType()).isEqualTo("Outsider (devil, Evil, Extraplanar, Lawful)");
	}

}

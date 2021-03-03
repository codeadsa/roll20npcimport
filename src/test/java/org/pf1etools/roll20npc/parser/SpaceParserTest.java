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
public class SpaceParserTest
{
	@Test
	public void testSpaceParser() throws ParseException
	{
		Creature creature = new Creature();
		TextParser.parseSpaceReach(creature, "Space 10 ft.; Reach 10 ft.");
		assertThat(creature.offenses.getSpace()).isEqualTo(10);
		assertThat(creature.offenses.getReach()).isEqualTo(10);
	}
	
	@Test
	public void testSpaceParserWithReachNotes() throws ParseException
	{
		Creature creature = new Creature();
		TextParser.parseSpaceReach(creature, "Space 30 ft.; Reach 30 ft. (60 ft. with tail slap)");
		assertThat(creature.offenses.getSpace()).isEqualTo(30);
		assertThat(creature.offenses.getReach()).isEqualTo(30);
		assertThat(creature.offenses.getReachNotes()).isEqualTo("60 ft. with tail slap");
	}
	
}

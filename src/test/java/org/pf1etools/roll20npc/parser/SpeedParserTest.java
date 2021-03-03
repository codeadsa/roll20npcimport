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
public class SpeedParserTest
{
	@Test
	public void testSpeedpParser() throws ParseException
	{
		Creature creature = new Creature();
		TextParser.parseSpeed(creature, "Speed 40 ft., burrow 20 ft.; earth glide");
		assertThat(creature.offenses.getSpeedLand()).isEqualTo(40);
		assertThat(creature.offenses.getSpeedBurrow()).isEqualTo(20);
		assertThat(creature.offenses.getSpeedNotes()).isEqualTo("earth glide");
	}
	
	@Test
	public void testSpeedFlyParser() throws ParseException
	{
		Creature creature = new Creature();
		TextParser.parseSpeed(creature, "Speed 40 ft., fly 20 ft. (perfect)  earth glide");
		assertThat(creature.offenses.getSpeedLand()).isEqualTo(40);
		assertThat(creature.offenses.getSpeedFly()).isEqualTo(20);
		assertThat(creature.offenses.getSpeedFlyNotes()).isEqualTo("perfect");
	}
	
	@Test
	public void testSpeedFly2Parser() throws ParseException
	{
		Creature creature = new Creature();
		TextParser.parseSpeed(creature, "Spd fly 30 ft. (perfect)");
		assertThat(creature.offenses.getSpeedFly()).isEqualTo(30);
		assertThat(creature.offenses.getSpeedFlyNotes()).isEqualTo("perfect");
	}
	
	

}

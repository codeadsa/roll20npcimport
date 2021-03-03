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
public class DefenseArmorParserTest
{
	@Test
	public void testPositiveDefenseAcHasEmdashParse() throws ParseException
	{
		Creature character = new Creature();
		TextParser.parseDefenseArmorClass(character, "AC 17, touch 11, flat-footed 15 (+2 Dex, +6 natural, –1 size)");
		assertThat(character.defenses.getAc()).isEqualTo(17);
		assertThat(character.defenses.getAcTouch()).isEqualTo(11);
		assertThat(character.defenses.getAcFlatFooted()).isEqualTo(15);
		assertThat(character.defenses.getAcNotes()).isEqualTo("+2 Dex, +6 natural, -1 size");
	}
	@Test
	public void testCloudDragonParse() throws ParseException
	{
		Creature character = new Creature();
		TextParser.parseDefenseArmorClass(character, "AC* 29, touch 7, flat-footed* 29 (–1 Dex, +22* natural, –2 size)");
		assertThat(character.defenses.getAc()).isEqualTo(29);
		assertThat(character.defenses.getAcTouch()).isEqualTo(7);
		assertThat(character.defenses.getAcFlatFooted()).isEqualTo(29);
		assertThat(character.defenses.getAcNotes()).isEqualTo("-1 Dex, +22* natural, -2 size");
	}
	
	@Test
	public void testDefenseAc() throws ParseException
	{
		Creature character = new Creature();
		TextParser.parseDefenseArmorClass(character, "AC 18, touch 12, flat-footed 16 (+2 Dex, +6 natural)");
		assertThat(character.defenses.getAc()).isEqualTo(18);
		assertThat(character.defenses.getAcTouch()).isEqualTo(12);
		assertThat(character.defenses.getAcFlatFooted()).isEqualTo(16);
		assertThat(character.defenses.getAcNotes()).isEqualTo("+2 Dex, +6 natural");
	}

	@Test
	public void testDefenseHighAc() throws ParseException
	{
		Creature character = new Creature();
		TextParser.parseDefenseArmorClass(character, "AC 33, touch 17, flat-footed 27 (+4 deflection, +6 Dex, +16 natural, –1 size, -2 rage)");
		assertThat(character.defenses.getAc()).isEqualTo(33);
		assertThat(character.defenses.getAcTouch()).isEqualTo(17);
		assertThat(character.defenses.getAcFlatFooted()).isEqualTo(27);
		assertThat(character.defenses.getAcNotes()).isEqualTo("+4 deflection, +6 Dex, +16 natural, -1 size, -2 rage");
	}

}

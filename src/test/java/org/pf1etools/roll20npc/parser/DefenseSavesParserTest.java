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
public class DefenseSavesParserTest
{
	@Test
	public void testDefenseSave1() throws ParseException
	{
		Creature character = new Creature();
		TextParser.parseDefenseSavingThrows(character, "Fort +0, Ref +4, Will +2");
		assertThat(character.defenses.getSaveFort()).isEqualTo(0);
		assertThat(character.defenses.getSaveReflex()).isEqualTo(4);
		assertThat(character.defenses.getSaveWill()).isEqualTo(2);
		assertThat(character.defenses.getSaveNotes()).isNull();
	}

	@Test
	public void testDefenseSave2Endash() throws ParseException
	{
		Creature character = new Creature();
		TextParser.parseDefenseSavingThrows(character, "Fort +3, Ref +2, Will –1");
		assertThat(character.defenses.getSaveFort()).isEqualTo(3);
		assertThat(character.defenses.getSaveReflex()).isEqualTo(2);
		assertThat(character.defenses.getSaveWill()).isEqualTo(-1);
		assertThat(character.defenses.getSaveNotes()).isNull();
	}

	@Test
	public void testDefenseSave3() throws ParseException
	{
		Creature character = new Creature();
		TextParser.parseDefenseSavingThrows(character, "Fort 0, Ref 0, Will 0");
		assertThat(character.defenses.getSaveFort()).isEqualTo(0);
		assertThat(character.defenses.getSaveReflex()).isEqualTo(0);
		assertThat(character.defenses.getSaveWill()).isEqualTo(0);
		assertThat(character.defenses.getSaveNotes()).isNull();
	}

	@Test
	public void testDefenseSave4() throws ParseException
	{
		Creature character = new Creature();
		TextParser.parseDefenseSavingThrows(character, "Fort +1, Ref +0, Will +2");
		assertThat(character.defenses.getSaveFort()).isEqualTo(1);
		assertThat(character.defenses.getSaveReflex()).isEqualTo(0);
		assertThat(character.defenses.getSaveWill()).isEqualTo(2);
		assertThat(character.defenses.getSaveNotes()).isNull();
	}

	@Test
	public void testDefenseSave5() throws ParseException
	{
		Creature character = new Creature();
		TextParser.parseDefenseSavingThrows(character, "Fort 0, Ref +1, Will +2");
		assertThat(character.defenses.getSaveFort()).isEqualTo(0);
		assertThat(character.defenses.getSaveReflex()).isEqualTo(1);
		assertThat(character.defenses.getSaveWill()).isEqualTo(2);
		assertThat(character.defenses.getSaveNotes()).isNull();
	}

	@Test
	public void testDefenseSave6() throws ParseException
	{
		Creature character = new Creature();
		TextParser.parseDefenseSavingThrows(character, "Fort +2, Ref +1, Will 0");
		assertThat(character.defenses.getSaveFort()).isEqualTo(2);
		assertThat(character.defenses.getSaveReflex()).isEqualTo(1);
		assertThat(character.defenses.getSaveWill()).isEqualTo(0);
		assertThat(character.defenses.getSaveNotes()).isNull();
	}

	@Test
	public void testDefenseSave7() throws ParseException
	{
		Creature character = new Creature();
		TextParser.parseDefenseSavingThrows(character, "Fort +11; Ref +19, Will +11; +3 vs. poison");
		assertThat(character.defenses.getSaveFort()).isEqualTo(11);
		assertThat(character.defenses.getSaveReflex()).isEqualTo(19);
		assertThat(character.defenses.getSaveWill()).isEqualTo(11);
		assertThat(character.defenses.getSaveNotes()).isEqualTo("+3 vs. poison");
	}

	@Test
	public void testDefenseSave8() throws ParseException
	{
		Creature character = new Creature();
		TextParser.parseDefenseSavingThrows(character, "Fort +13, Ref +12, Will +10; +1 vs. fear, +2 vs. enchantment");
		assertThat(character.defenses.getSaveFort()).isEqualTo(13);
		assertThat(character.defenses.getSaveReflex()).isEqualTo(12);
		assertThat(character.defenses.getSaveWill()).isEqualTo(10);
		assertThat(character.defenses.getSaveNotes()).isEqualTo("+1 vs. fear, +2 vs. enchantment");
	}
}

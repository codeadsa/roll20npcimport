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
public class DefenseHitPointsParserTest
{
	@Test
	public void testHpCaps() throws ParseException
	{
		Creature character = new Creature();
		TextParser.parseDefenseHitPoints(character, "HP 42 (5d10+15)");
		assertThat(character.defenses.getHp()).isEqualTo(42);
		assertThat(character.defenses.getHd()).isEqualTo(5);
		assertThat(character.defenses.getHitDie()).isEqualTo(10);
		assertThat(character.defenses.getHpMod()).isEqualTo("+15");
		assertThat(character.defenses.getHpNotes()).isNull();
	}
	
	@Test
	public void testPositiveDefenseHp1() throws ParseException
	{
		Creature character = new Creature();
		TextParser.parseDefenseHitPoints(character, "hp 42 (5d10+15)");
		assertThat(character.defenses.getHp()).isEqualTo(42);
		assertThat(character.defenses.getHd()).isEqualTo(5);
		assertThat(character.defenses.getHitDie()).isEqualTo(10);
		assertThat(character.defenses.getHpMod()).isEqualTo("+15");
		assertThat(character.defenses.getHpNotes()).isNull();
	}

	@Test
	public void testPositiveDefenseHp2() throws ParseException
	{
		Creature character = new Creature();
		TextParser.parseDefenseHitPoints(character, "hp 52 (4d10+30 size)");
		assertThat(character.defenses.getHp()).isEqualTo(52);
		assertThat(character.defenses.getHd()).isEqualTo(4);
		assertThat(character.defenses.getHitDie()).isEqualTo(10);
		assertThat(character.defenses.getHpMod()).isEqualTo("+30 size");
		assertThat(character.defenses.getHpNotes()).isNull();
	}

	@Test
	public void testPositiveDefenseHp3() throws ParseException
	{
		Creature character = new Creature();
		TextParser.parseDefenseHitPoints(character, "hp 152 (16d10+64)");
		assertThat(character.defenses.getHp()).isEqualTo(152);
		assertThat(character.defenses.getHd()).isEqualTo(16);
		assertThat(character.defenses.getHitDie()).isEqualTo(10);
		assertThat(character.defenses.getHpMod()).isEqualTo("+64");
		assertThat(character.defenses.getHpNotes()).isNull();
	}

	@Test
	public void testPositiveDefenseHp4() throws ParseException
	{
		Creature character = new Creature();
		TextParser.parseDefenseHitPoints(character, "hp 525 (30d10+360); regeneration 40");
		assertThat(character.defenses.getHp()).isEqualTo(525);
		assertThat(character.defenses.getHd()).isEqualTo(30);
		assertThat(character.defenses.getHitDie()).isEqualTo(10);
		assertThat(character.defenses.getHpMod()).isEqualTo("+360");
		assertThat(character.defenses.getHpNotes()).isEqualTo("regeneration 40");
	}

	@Test
	public void testPositiveDefenseHp5() throws ParseException
	{
		Creature character = new Creature();
		TextParser.parseDefenseHitPoints(character, "hp 195 (17 HD; 5d10+12d8+114); regeneration 5 (acid or fire)");
		assertThat(character.defenses.getHp()).isEqualTo(195);
		assertThat(character.defenses.getHd()).isEqualTo(17);
		assertThat(character.defenses.getHitDie()).isEqualTo(10);
		assertThat(character.defenses.getHpMod()).isEqualTo("+12d8+114");
		assertThat(character.defenses.getHpNotes()).isEqualTo("regeneration 5 (acid or fire)");
	}

	@Test
	public void testPositiveDefenseHp6() throws ParseException
	{
		Creature character = new Creature();
		TextParser.parseDefenseHitPoints(character, "hp 16 (3d10)");
		assertThat(character.defenses.getHp()).isEqualTo(16);
		assertThat(character.defenses.getHd()).isEqualTo(3);
		assertThat(character.defenses.getHitDie()).isEqualTo(10);
		assertThat(character.defenses.getHpMod()).isNull();
		assertThat(character.defenses.getHpNotes()).isNull();
	}

	@Test
	public void testPositiveDefenseHp7() throws ParseException
	{
		Creature character = new Creature();
		TextParser.parseDefenseHitPoints(character, "hp 205 (19 HD; 13d10+6d8+110)");
		assertThat(character.defenses.getHp()).isEqualTo(205);
		assertThat(character.defenses.getHd()).isEqualTo(19);
		assertThat(character.defenses.getHitDie()).isEqualTo(10);
		assertThat(character.defenses.getHpMod()).isEqualTo("+6d8+110");
		assertThat(character.defenses.getHpNotes()).isNull();
	}

	@Test
	public void testPositiveDefenseHp8Endash() throws ParseException
	{
		Creature character = new Creature();
		TextParser.parseDefenseHitPoints(character, "hp 2 (1d8–2)");
		assertThat(character.defenses.getHp()).isEqualTo(2);
		assertThat(character.defenses.getHd()).isEqualTo(1);
		assertThat(character.defenses.getHitDie()).isEqualTo(8);
		assertThat(character.defenses.getHpMod()).isEqualTo("-2");
		assertThat(character.defenses.getHpNotes()).isNull();
	}

}

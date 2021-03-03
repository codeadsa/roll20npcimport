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
public class MeleeRangeSpecialAttackParserTest
{
	@Test
	public void testMeleeParser() throws ParseException
	{
		Creature creature = new Creature();
		TextParser.parseMeleeRangedSpecialAttacks(creature, "Melee 2 claws +7 (1d6+4), bite +7 (1d8+4), and 6 snakes +2 (1d4 + poison )");
		assertThat(creature.offenses.getMelee()).isEqualTo("2 claws +7 (1d6+4), bite +7 (1d8+4), and 6 snakes +2 (1d4 + poison )");
	}
	
	@Test
	public void testMeleeRemoveWeaponBonusParser() throws ParseException
	{
		Creature creature = new Creature();
		TextParser.parseMeleeRangedSpecialAttacks(creature, "Melee +5 holy starknife +29/+24/+19/+14 (1d6+12x3)");
		assertThat(creature.offenses.getMelee()).isEqualTo("holy starknife +29/+24/+19/+14 (1d6+12x3)");
	}
	
	@Test
	public void testMeleeRemoveWeaponBonusHugeParser() throws ParseException
	{
		Creature creature = new Creature();
		TextParser.parseMeleeRangedSpecialAttacks(creature, "Melee Huge +4 unholy halberd of speed +48/+48/+43/+38/+33 (3d8+25/19-20x3), bite +38 (1d8+7) or gore +43 (1d8+21)");
		assertThat(creature.offenses.getMelee()).isEqualTo("Huge unholy halberd of speed +48/+48/+43/+38/+33 (3d8+25/19-20x3), bite +38 (1d8+7) or gore +43 (1d8+21)");
	}
	
	
	@Test
	public void testRangeParser() throws ParseException
	{
		Creature creature = new Creature();
		TextParser.parseMeleeRangedSpecialAttacks(creature, "Ranged void missile +23 touch (6d6 plus energy drain)");
		assertThat(creature.offenses.getRange()).isEqualTo("void missile +23 touch (6d6 plus energy drain)");
	}
	
	@Test
	public void testSpecialAttackParser() throws ParseException
	{
		Creature creature = new Creature();
		TextParser.parseMeleeRangedSpecialAttacks(creature, "Special Attacks commanding voice, energy drain (2 levels, DC 28), void trap");
		assertThat(creature.offenses.getSpecialAttacks()).isEqualTo("commanding voice, energy drain (2 levels, DC 28), void trap");
	}
	
}

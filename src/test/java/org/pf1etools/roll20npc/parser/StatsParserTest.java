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
public class StatsParserTest
{
	@Disabled
	public void testStatParser1MissingValue() throws ParseException
	{
		Creature character = new Creature();
		TextParser.parseStatisticsAbilityScores(character, "Str 1, Dex 15, Con 6, Int , Wis 14, Cha 5");
		assertThat(character.stats.getAbilityStr()).isEqualTo(1);
		assertThat(character.stats.getAbilityDex()).isEqualTo(15);
		assertThat(character.stats.getAbilityCon()).isEqualTo(6);
		assertThat(character.stats.getAbilityInt()).isNull();
		assertThat(character.stats.getAbilityWis()).isEqualTo(14);
		assertThat(character.stats.getAbilityCha()).isEqualTo(5);
	}
	
	@Test
	public void testStatParser2() throws ParseException
	{
		Creature character = new Creature();
		TextParser.parseStatisticsAbilityScores(character, "Str -, Dex 15, Con 12, Int 10, Wis 9, Cha 6");
		assertThat(character.stats.getAbilityStr()).isNull();
		assertThat(character.stats.getAbilityDex()).isEqualTo(15);
		assertThat(character.stats.getAbilityCon()).isEqualTo(12);
		assertThat(character.stats.getAbilityInt()).isEqualTo(10);
		assertThat(character.stats.getAbilityWis()).isEqualTo(9);
		assertThat(character.stats.getAbilityCha()).isEqualTo(6);
	}
	
	@Test
	public void testStatParser3() throws ParseException
	{
		Creature character = new Creature();
		TextParser.parseStatisticsAbilityScores(character, "Str 22, Dex 8, Con -, Int -, Wis 1, Cha 1");
		assertThat(character.stats.getAbilityStr()).isEqualTo(22);
		assertThat(character.stats.getAbilityDex()).isEqualTo(8);
		assertThat(character.stats.getAbilityCon()).isNull();
		assertThat(character.stats.getAbilityInt()).isNull();
		assertThat(character.stats.getAbilityWis()).isEqualTo(1);
		assertThat(character.stats.getAbilityCha()).isEqualTo(1);
	}
	
	@Test
	public void testStatParser4EmDash() throws ParseException
	{
		Creature character = new Creature();
		TextParser.parseStatisticsAbilityScores(character, "Str 20, Dex 13, Con 16, Int —, Wis 11, Cha 1");
		assertThat(character.stats.getAbilityStr()).isEqualTo(20);
		assertThat(character.stats.getAbilityDex()).isEqualTo(13);
		assertThat(character.stats.getAbilityCon()).isEqualTo(16);
		assertThat(character.stats.getAbilityInt()).isNull();
		assertThat(character.stats.getAbilityWis()).isEqualTo(11);
		assertThat(character.stats.getAbilityCha()).isEqualTo(1);
	}
	
	@Test
	public void testStatParser5() throws ParseException
	{
		Creature character = new Creature();
		TextParser.parseStatisticsAbilityScores(character, "Str 15, Dex 18, Con —, Int 10, Wis 13, Cha 16");
		assertThat(character.stats.getAbilityStr()).isEqualTo(15);
		assertThat(character.stats.getAbilityDex()).isEqualTo(18);
		assertThat(character.stats.getAbilityCon()).isNull();
		assertThat(character.stats.getAbilityInt()).isEqualTo(10);
		assertThat(character.stats.getAbilityWis()).isEqualTo(13);
		assertThat(character.stats.getAbilityCha()).isEqualTo(16);
	}
	
	@Test
	public void testStatParser6() throws ParseException
	{
		Creature character = new Creature();
		TextParser.parseStatisticsAbilityScores(character, "Str 43, Dex 23, Con 44, Int 24, Wis 26, Cha 29");
		assertThat(character.stats.getAbilityStr()).isEqualTo(43);
		assertThat(character.stats.getAbilityDex()).isEqualTo(23);
		assertThat(character.stats.getAbilityCon()).isEqualTo(44);
		assertThat(character.stats.getAbilityInt()).isEqualTo(24);
		assertThat(character.stats.getAbilityWis()).isEqualTo(26);
		assertThat(character.stats.getAbilityCha()).isEqualTo(29);
	}
	
	@Test
	public void testStatParser7() throws ParseException
	{
		Creature character = new Creature();
		TextParser.parseStatisticsAbilityScores(character, "Str 25, Dex 18, Con —, Int 14, Wis 14, Cha 18");
		assertThat(character.stats.getAbilityStr()).isEqualTo(25);
		assertThat(character.stats.getAbilityDex()).isEqualTo(18);
		assertThat(character.stats.getAbilityCon()).isNull();
		assertThat(character.stats.getAbilityInt()).isEqualTo(14);
		assertThat(character.stats.getAbilityWis()).isEqualTo(14);
		assertThat(character.stats.getAbilityCha()).isEqualTo(18);
	}
	
	@Test
	public void testStatParser8() throws ParseException
	{
		Creature character = new Creature();
		TextParser.parseStatisticsAbilityScores(character, "Str 32, Dex 9, Con —, Int —, Wis 11, Cha 1");
		assertThat(character.stats.getAbilityStr()).isEqualTo(32);
		assertThat(character.stats.getAbilityDex()).isEqualTo(9);
		assertThat(character.stats.getAbilityCon()).isNull();
		assertThat(character.stats.getAbilityInt()).isNull();
		assertThat(character.stats.getAbilityWis()).isEqualTo(11);
		assertThat(character.stats.getAbilityCha()).isEqualTo(1);
	}	
}

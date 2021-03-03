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
public class SkillsParserTest
{
	@Test
	public void testStatParser1() throws ParseException
	{
		Creature character = new Creature();
		TextParser.parseSkills(character, "Skills Bluff +14, Disguise +11, Fly +11, Intimidate +14, Knowledge (arcana) +15, Knowledge (planes) +15, Knowledge (religion) +12, Perception +12, Sense Motive +12, Spellcraft +17, Stealth +11, Use Magic Device +16");
		assertThat(character.skills.getBluffMod()).isEqualTo(14);
		assertThat(character.skills.getDisguiseMod()).isEqualTo(11);
		assertThat(character.skills.getFlyMod()).isEqualTo(11);
		assertThat(character.skills.getIntimidateMod()).isEqualTo(14);
		assertThat(character.skills.getKnowArcanaMod()).isEqualTo(15);
		assertThat(character.skills.getKnowPlanesMod()).isEqualTo(15);
		assertThat(character.skills.getKnowReligionMod()).isEqualTo(12);
		assertThat(character.skills.getPerceptionMod()).isEqualTo(12);
		assertThat(character.skills.getSenseMotiveMod()).isEqualTo(12);
		assertThat(character.skills.getSpellcraftMod()).isEqualTo(17);
		assertThat(character.skills.getStealthMod()).isEqualTo(11);
		assertThat(character.skills.getUseMagicDeviceMod()).isEqualTo(16);
	}

	@Test
	public void testStatParser2() throws ParseException
	{
		Creature character = new Creature();
		TextParser.parseSkills(character, "Skills Acrobatics +25, Escape Artist +25, Knowledge (planes) +19, Perception +19, Stealth +17, Swim +37");
		assertThat(character.skills.getAcrobaticsMod()).isEqualTo(25);
		assertThat(character.skills.getEscapeArtistMod()).isEqualTo(25);
		assertThat(character.skills.getKnowPlanesMod()).isEqualTo(19);
		assertThat(character.skills.getPerceptionMod()).isEqualTo(19);
		assertThat(character.skills.getStealthMod()).isEqualTo(17);
		assertThat(character.skills.getSwimMod()).isEqualTo(37);
	}

	@Disabled
	public void testStatParser3HasPerceptionNotesAndStarOnSkillLabel() throws ParseException
	{
		Creature character = new Creature();
		TextParser.parseSkills(character, "Skills* Climb +8, Perception +5 (+9 in water), Stealth -1 (+12 in water), Swim +7; Racial Modifiers +4 Perception (+8 in water), +8 Stealth in water");
		assertThat(character.skills.getClimbMod()).isEqualTo(8);
		assertThat(character.skills.getPerceptionMod()).isEqualTo(5);
		assertThat(character.skills.getStealthMod()).isEqualTo(-1);
		assertThat(character.skills.getSwimMod()).isEqualTo(7);
		assertThat(character.skills.getPerceptionNotes()).isEqualTo("+9 in water");
		assertThat(character.skills.getStealthNotes()).isEqualTo("+12 in water");
	}

	@Test
	public void testStatParser4() throws ParseException
	{
		Creature character = new Creature();
		TextParser.parseSkills(character, "Skills Perception +14, Stealth +17");
		assertThat(character.skills.getPerceptionMod()).isEqualTo(14);
		assertThat(character.skills.getStealthMod()).isEqualTo(17);
	}

	@Test
	public void testStatParser5() throws ParseException
	{
		Creature character = new Creature();
		TextParser.parseSkills(character, "Skills Acrobatics +6, Bluff +16, Diplomacy +16, Escape Artist +6, Fly +13, Intimidate +16, Knowledge (planes) +14, Perception +13, Sense Motive +13, Spellcraft +14, Stealth +11; Racial Modifiers +8 Intimidate, +8 Perception");
		assertThat(character.skills.getAcrobaticsMod()).isEqualTo(6);
		assertThat(character.skills.getBluffMod()).isEqualTo(16);
		assertThat(character.skills.getDiplomacyMod()).isEqualTo(16);
		assertThat(character.skills.getEscapeArtistMod()).isEqualTo(6);
		assertThat(character.skills.getFlyMod()).isEqualTo(13);
		assertThat(character.skills.getIntimidateMod()).isEqualTo(16);
		assertThat(character.skills.getKnowPlanesMod()).isEqualTo(14);
		assertThat(character.skills.getPerceptionMod()).isEqualTo(13);
		assertThat(character.skills.getSenseMotiveMod()).isEqualTo(13);
		assertThat(character.skills.getSpellcraftMod()).isEqualTo(14);
		assertThat(character.skills.getStealthMod()).isEqualTo(11);
		assertThat(character.skills.getNotes()).isEqualTo("+8 Intimidate, +8 Perception");
	}

	@Test
	public void testStatParser6() throws ParseException
	{
		Creature character = new Creature();
		TextParser.parseSkills(character, "Skills Acrobatics +3 (+43 when jumping), Perception +43");
		assertThat(character.skills.getAcrobaticsMod()).isEqualTo(3);
		assertThat(character.skills.getPerceptionMod()).isEqualTo(43);
		assertThat(character.skills.getAcrobaticsNotes()).isEqualTo("+43 when jumping");
	}

	@Test
	public void testStatParser7Everything() throws ParseException
	{
		Creature character = new Creature();
		TextParser.parseSkills(character, "Skills Acrobatics +1 (a), Appraise +2 (b), Bluff +3 (c), Climb +4 (d), Craft +5 (e), Diplomacy +6 (f), Disable Device +7 (g), Disguise +8 (h), Escape Artist +9 (i), Fly +10 (j), Handle Animal +11 (k), Heal +12 (l), Intimidate +13 (m), Knowledge (arcana) +14 (n), Knowledge (dungeoneering) +15 (o), Knowledge (engineering) +16 (p), Knowledge (geography) +17 (q), Knowledge (history) +18 (r), Knowledge (local) +19 (s), Knowledge (nature) +20 (t), Knowledge (nobility) +21 (u), Knowledge (planes) +22 (v), Knowledge (religion) +23 (w), Linguistics +24 (x), Perception +25 (y), Perform +26 (z), Profession +27 (aa), Ride +28 (ab), Sense Motive +29 (ac), Sleight of Hand +30 (ad), Spellcraft +31 (ae), Stealth +32 (af), Survival +33 (ag), Swim +34 (ah), Use Magic Device +35 (ai); whatever else");
		int i = 1;
		assertThat(character.skills.getAcrobaticsMod()).isEqualTo(i++);
		assertThat(character.skills.getAppraiseMod()).isEqualTo(i++);
		assertThat(character.skills.getBluffMod()).isEqualTo(i++);
		assertThat(character.skills.getClimbMod()).isEqualTo(i++);
		assertThat(character.skills.getCraftMod()).isEqualTo(i++);
		assertThat(character.skills.getDiplomacyMod()).isEqualTo(i++);
		assertThat(character.skills.getDisableDeviceMod()).isEqualTo(i++);
		assertThat(character.skills.getDisguiseMod()).isEqualTo(i++);
		assertThat(character.skills.getEscapeArtistMod()).isEqualTo(i++);
		assertThat(character.skills.getFlyMod()).isEqualTo(i++);
		assertThat(character.skills.getHandleAnimalMod()).isEqualTo(i++);
		assertThat(character.skills.getHealMod()).isEqualTo(i++);
		assertThat(character.skills.getIntimidateMod()).isEqualTo(i++);
		assertThat(character.skills.getKnowArcanaMod()).isEqualTo(i++);
		assertThat(character.skills.getKnowDungeoneeringMod()).isEqualTo(i++);
		assertThat(character.skills.getKnowEngineeringMod()).isEqualTo(i++);
		assertThat(character.skills.getKnowGeographyMod()).isEqualTo(i++);
		assertThat(character.skills.getKnowHistoryMod()).isEqualTo(i++);
		assertThat(character.skills.getKnowLocalMod()).isEqualTo(i++);
		assertThat(character.skills.getKnowNatureMod()).isEqualTo(i++);
		assertThat(character.skills.getKnowNobilityMod()).isEqualTo(i++);
		assertThat(character.skills.getKnowPlanesMod()).isEqualTo(i++);
		assertThat(character.skills.getKnowReligionMod()).isEqualTo(i++);
		assertThat(character.skills.getLinguisticsMod()).isEqualTo(i++);
		assertThat(character.skills.getPerceptionMod()).isEqualTo(i++);
		assertThat(character.skills.getPerformMod()).isEqualTo(i++);
		assertThat(character.skills.getProfessionMod()).isEqualTo(i++);
		assertThat(character.skills.getRideMod()).isEqualTo(i++);
		assertThat(character.skills.getSenseMotiveMod()).isEqualTo(i++);
		assertThat(character.skills.getSleightOfHandMod()).isEqualTo(i++);
		assertThat(character.skills.getSpellcraftMod()).isEqualTo(i++);
		assertThat(character.skills.getStealthMod()).isEqualTo(i++);
		assertThat(character.skills.getSurvivalMod()).isEqualTo(i++);
		assertThat(character.skills.getSwimMod()).isEqualTo(i++);
		assertThat(character.skills.getUseMagicDeviceMod()).isEqualTo(i++);

		assertThat(character.skills.getAcrobaticsNotes()).isEqualTo("a");
		assertThat(character.skills.getAppraiseNotes()).isEqualTo("b");
		assertThat(character.skills.getBluffNotes()).isEqualTo("c");
		assertThat(character.skills.getClimbNotes()).isEqualTo("d");
		assertThat(character.skills.getCraftNotes()).isEqualTo("e");
		assertThat(character.skills.getDiplomacyNotes()).isEqualTo("f");
		assertThat(character.skills.getDisableDeviceNotes()).isEqualTo("g");
		assertThat(character.skills.getDisguiseNotes()).isEqualTo("h");
		assertThat(character.skills.getEscapeArtistNotes()).isEqualTo("i");
		assertThat(character.skills.getFlyNotes()).isEqualTo("j");
		assertThat(character.skills.getHandleAnimalNotes()).isEqualTo("k");
		assertThat(character.skills.getHealNotes()).isEqualTo("l");
		assertThat(character.skills.getIntimidateNotes()).isEqualTo("m");
		assertThat(character.skills.getKnowArcanaNotes()).isEqualTo("n");
		assertThat(character.skills.getKnowDungeoneeringNotes()).isEqualTo("o");
		assertThat(character.skills.getKnowEngineeringNotes()).isEqualTo("p");
		assertThat(character.skills.getKnowGeographyNotes()).isEqualTo("q");
		assertThat(character.skills.getKnowHistoryNotes()).isEqualTo("r");
		assertThat(character.skills.getKnowLocalNotes()).isEqualTo("s");
		assertThat(character.skills.getKnowNatureNotes()).isEqualTo("t");
		assertThat(character.skills.getKnowNobilityNotes()).isEqualTo("u");
		assertThat(character.skills.getKnowPlanesNotes()).isEqualTo("v");
		assertThat(character.skills.getKnowReligionNotes()).isEqualTo("w");
		assertThat(character.skills.getLinguisticsNotes()).isEqualTo("x");
		assertThat(character.skills.getPerceptionNotes()).isEqualTo("y");
		assertThat(character.skills.getPerformNotes()).isEqualTo("z");
		assertThat(character.skills.getProfessionNotes()).isEqualTo("aa");
		assertThat(character.skills.getRideNotes()).isEqualTo("ab");
		assertThat(character.skills.getSenseMotiveNotes()).isEqualTo("ac");
		assertThat(character.skills.getSleightOfHandNotes()).isEqualTo("ad");
		assertThat(character.skills.getSpellcraftNotes()).isEqualTo("ae");
		assertThat(character.skills.getStealthNotes()).isEqualTo("af");
		assertThat(character.skills.getSurvivalNotes()).isEqualTo("ag");
		assertThat(character.skills.getSwimNotes()).isEqualTo("ah");
		assertThat(character.skills.getUseMagicDeviceNotes()).isEqualTo("ai");

		assertThat(character.skills.getNotes()).isEqualTo("whatever else");
	}

}

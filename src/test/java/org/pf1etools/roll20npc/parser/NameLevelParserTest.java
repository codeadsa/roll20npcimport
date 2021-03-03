package org.pf1etools.roll20npc.parser;

import java.io.File;
import java.text.ParseException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.pf1etools.creature.Creature;
import org.pf1etools.creature.utils.LevelFraction;
import org.pf1etools.roll20npc.parser.ParserUtils;
import org.pf1etools.roll20npc.parser.TextParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.ActiveProfiles;

import static org.fest.assertions.api.Assertions.assertThat; // main one
import static org.fest.assertions.api.Assertions.assertThat; // main one

@SpringBootTest
@ActiveProfiles("Dev")
public class NameLevelParserTest
{
	// ------------------Happy Path Testing------------------//
	@Test
	public void testParsingName_Ok() throws ParseException
	{
		Creature creature = new Creature();
		TextParser.parseNameAndLevel(creature, "GriffonCR 4");
		assertThat(creature.getName()).isEqualTo("Griffon");
	}

	@Test
	public void testParsingLevel_Ok() throws ParseException
	{
		Creature creature = new Creature();
		TextParser.parseNameAndLevel(creature, "GriffonCR 4");
		assertThat(creature.getLevel()).isEqualTo(4);
	}

	@Test
	public void testParsingTerotricusName_Ok() throws ParseException
	{
		Creature creature = new Creature();
		TextParser.parseNameAndLevel(creature, "TerotricusCR 19");
		assertThat(creature.getName()).isEqualTo("Terotricus");
	}

	@Test
	public void testParsingTerotricusLevel_Ok() throws ParseException
	{
		Creature creature = new Creature();
		TextParser.parseNameAndLevel(creature, "TerotricusCR 19");
		assertThat(creature.getLevel()).isEqualTo(19);
	}

	// -------------------Null Testing-----------------//
	@Disabled
	public void testParsingNameNull_ThrowsException() throws ParseException
	{
		Assertions.assertThrows(ParseException.class, () -> {
			Creature creature = new Creature();
			TextParser.parseNameAndLevel(creature, null);
		});
	}

	@Disabled
	public void testParsingLevelNull_ThrowsException() throws ParseException
	{
		Assertions.assertThrows(ParseException.class, () -> {
			Creature creature = new Creature();
			TextParser.parseNameAndLevel(creature, null);
		});

	}

	// -------------------Blank Testing-----------------//
	@Disabled
	public void testParsingNameBlank_ThrowsException() throws ParseException
	{
		Assertions.assertThrows(ParseException.class, () -> {
			Creature creature = new Creature();
			TextParser.parseNameAndLevel(creature, "");
			assertThat(creature.getName()).isNull();
		});
	}

	@Disabled
	public void testParsingLevelBlank_IsNull() throws ParseException
	{
		Assertions.assertThrows(ParseException.class, () -> {
			Creature creature = new Creature();
			TextParser.parseNameAndLevel(creature, "");
			assertThat(creature.getLevel()).isNull();
		});
	}

	@Test
	public void testParsingNameMissing_IsNull() throws ParseException
	{
		Assertions.assertThrows(ParseException.class, () -> {
			Creature creature = new Creature();
			TextParser.parseNameAndLevel(creature, "CR 4");
		});
	}

	@Test
	public void testParsingLevelMissing_ThrowsError() throws ParseException
	{
		Assertions.assertThrows(ParseException.class, () -> {
			Creature creature = new Creature();
			TextParser.parseNameAndLevel(creature, "GriffonCR");
		});
	}

	// -------------------Space Testing-----------------//
	@Test
	public void testParsingStartSpace_IsOK() throws ParseException
	{
		Creature creature = new Creature();
		TextParser.parseNameAndLevel(creature, " GriffonCR 4");
		assertThat(creature.getName()).isEqualTo("Griffon");
		assertThat(creature.getLevel()).isEqualTo(4);
	}

	@Test
	public void testParsingEndSpace_IsOK() throws ParseException
	{
		Creature creature = new Creature();
		TextParser.parseNameAndLevel(creature, "GriffonCR 4 ");
		assertThat(creature.getName()).isEqualTo("Griffon");
		assertThat(creature.getLevel()).isEqualTo(4);
	}

	@Test
	public void testParsingMultiWordName_IsOK() throws ParseException
	{
		Creature creature = new Creature();
		TextParser.parseNameAndLevel(creature, "Golden Griffon MountCR 4");
		assertThat(creature.getName()).isEqualTo("Golden Griffon Mount");
		assertThat(creature.getLevel()).isEqualTo(4);
	}

	// -------------Valid creature Name Testing--------------------//
	@Test
	public void testParsingNameParentheses_IsOk() throws Exception
	{
		Creature creature = new Creature();
		TextParser.parseNameAndLevel(creature, "Alghollthu Master (Aboleth)CR 7");
		assertThat(creature.getName()).isEqualTo("Alghollthu Master (Aboleth)");
		assertThat(creature.getLevel()).isEqualTo(7);
	}

	@Test
	public void testParsingNameHyphens_IsOk() throws Exception
	{
		Creature creature = new Creature();
		TextParser.parseNameAndLevel(creature, "Will-o-WispCR 6");
		assertThat(creature.getName()).isEqualTo("Will-o-Wisp");
		assertThat(creature.getLevel()).isEqualTo(6);
	}

	@Test
	public void testParsingNameComma_IsOk() throws Exception
	{
		Creature creature = new Creature();
		TextParser.parseNameAndLevel(creature, "Barghest, GreaterCR 7");
		assertThat(creature.getName()).isEqualTo("Barghest, Greater");
		assertThat(creature.getLevel()).isEqualTo(7);
	}

	@Test
	public void testParsingNameAmpersand_IsOk() throws Exception
	{
		Creature creature = new Creature();
		TextParser.parseNameAndLevel(creature, "Gold & Black DragonCR 7");
		assertThat(creature.getName()).isEqualTo("Gold & Black Dragon");
		assertThat(creature.getLevel()).isEqualTo(7);
	}

	@Test
	public void testParsingNameColon_IsOk() throws Exception
	{
		Creature creature = new Creature();
		TextParser.parseNameAndLevel(creature, "Gold: DragonCR 7");
		assertThat(creature.getName()).isEqualTo("Gold: Dragon");
		assertThat(creature.getLevel()).isEqualTo(7);
	}

	@Test
	public void testParsingNameApostrophe_IsOk() throws Exception
	{
		Creature creature = new Creature();
		TextParser.parseNameAndLevel(creature, "Wizard's ShackleCR 2");
		assertThat(creature.getName()).isEqualTo("Wizard's Shackle");
	}

	@Test
	public void testParsingNameRightApostrophe_IsOk() throws Exception
	{
		Creature creature = new Creature();
		TextParser.parseNameAndLevel(creature, "Wizard’s ShackleCR 2");
		assertThat(creature.getName()).isEqualTo("Wizard's Shackle");
	}

	@Test
	public void testParsingNameHasCreatureEnd_IsOk() throws Exception
	{
		Creature creature = new Creature();
		TextParser.parseNameAndLevel(creature, "Slime like CRacCR 7");
		assertThat(creature.getName()).isEqualTo("Slime like CRac");
		assertThat(creature.getLevel()).isEqualTo(7);
	}

	// ------------ Invalid Level Testing ----------------//
	@Test
	public void testParsingLevelEndingWithChar_IsInvalid() throws Exception
	{
		Creature creature = new Creature();
		TextParser.parseNameAndLevel(creature, "Mega GoblinCR 7+");
		assertThat(creature.getLevel()).isEqualTo(7);
	}

	// --------------Valid Level Testing ---------------//

	@Test
	public void testParsingFractionLevelGoblin_IsValid() throws Exception
	{
		Creature creature = new Creature();
		TextParser.parseNameAndLevel(creature, "GoblinCR 1/3");
		assertThat(creature.getName()).isEqualTo("Goblin");
		assertThat(creature.getLevel()).isEqualTo(0);
		assertThat(creature.getLevelFraction()).isEqualTo(LevelFraction.ONE_THIRD);
	}

	@Test
	public void testParsingFractionLevelBat_IsValid() throws Exception
	{
		Creature creature = new Creature();
		TextParser.parseNameAndLevel(creature, "Common BatCR 1/8");
		assertThat(creature.getName()).isEqualTo("Common Bat");
		assertThat(creature.getLevel()).isEqualTo(0);
		assertThat(creature.getLevelFraction()).isEqualTo(LevelFraction.ONE_EIGHTH);
	}

}

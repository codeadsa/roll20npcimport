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
public class NameLevelPdfParserTest
{
	// ------------------Happy Path Testing------------------//
	@Test
	public void testParsingName_Ok() throws ParseException
	{
		Creature character = new Creature();
		TextParser.parseNameAndLevel(character, "Griffon CR 4");
		assertThat(character.getName()).isEqualTo("Griffon");
	}

	@Test
	public void testParsingLevel_Ok() throws ParseException
	{
		Creature character = new Creature();
		TextParser.parseNameAndLevel(character, "Griffon CR 4");
		assertThat(character.getLevel()).isEqualTo(4);
	}

	@Test
	public void testParsingTerotricusName_Ok() throws ParseException
	{
		Creature character = new Creature();
		TextParser.parseNameAndLevel(character, "Terotricus CR 19");
		assertThat(character.getName()).isEqualTo("Terotricus");
	}

	@Test
	public void testParsingTerotricusLevel_Ok() throws ParseException
	{
		Creature character = new Creature();
		TextParser.parseNameAndLevel(character, "Terotricus CR 19");
		assertThat(character.getLevel()).isEqualTo(19);
	}

	// -------------------Null Testing-----------------//
	@Disabled //null checking done in the main parser for each line, not in each parse method
	public void testParsingNameNull_ThrowsException() throws ParseException
	{
		Assertions.assertThrows(ParseException.class, () -> {
			Creature character = new Creature();
			TextParser.parseNameAndLevel(character, null);
		});
	}

	@Disabled
	public void testParsingLevelNull_ThrowsException() throws ParseException
	{
		Assertions.assertThrows(ParseException.class, () -> {
			Creature character = new Creature();
			TextParser.parseNameAndLevel(character, null);
		});

	}

	// -------------------Blank Testing-----------------//
	@Disabled //blank check done on each line, not in parsers now.
	public void testParsingNameBlank_ThrowsException() throws ParseException
	{
		Assertions.assertThrows(ParseException.class, () -> {
			Creature character = new Creature();
			TextParser.parseNameAndLevel(character, "");
			assertThat(character.getName()).isNull();
		});
	}

	@Disabled
	public void testParsingLevelBlank_IsNull() throws ParseException
	{
		Assertions.assertThrows(ParseException.class, () -> {
			Creature character = new Creature();
			TextParser.parseNameAndLevel(character, "");
			assertThat(character.getLevel()).isNull();
		});
	}

	@Test
	public void testParsingNameMissing_IsNull() throws ParseException
	{
		Assertions.assertThrows(ParseException.class, () -> {
			Creature character = new Creature();
			TextParser.parseNameAndLevel(character, "CR 4");
		});
	}

	@Test
	public void testParsingLevelMissing_ThrowsError() throws ParseException
	{
		Assertions.assertThrows(ParseException.class, () -> {
			Creature character = new Creature();
			TextParser.parseNameAndLevel(character, "Griffon CR");
		});
	}

	// -------------------Space Testing-----------------//
	@Test
	public void testParsingStartSpace_IsOK() throws ParseException
	{
		Creature character = new Creature();
		TextParser.parseNameAndLevel(character, " Griffon CR 4");
		assertThat(character.getName()).isEqualTo("Griffon");
		assertThat(character.getLevel()).isEqualTo(4);
	}

	@Test
	public void testParsingEndSpace_IsOK() throws ParseException
	{
		Creature character = new Creature();
		TextParser.parseNameAndLevel(character, "Griffon CR 4 ");
		assertThat(character.getName()).isEqualTo("Griffon");
		assertThat(character.getLevel()).isEqualTo(4);
	}

	@Test
	public void testParsingMultiWordName_IsOK() throws ParseException
	{
		Creature character = new Creature();
		TextParser.parseNameAndLevel(character, "Golden Griffon Mount CR 4");
		assertThat(character.getName()).isEqualTo("Golden Griffon Mount");
		assertThat(character.getLevel()).isEqualTo(4);
	}

	// -------------Valid Character Name Testing--------------------//
	@Test
	public void testParsingNameParentheses_IsOk() throws Exception
	{
		Creature character = new Creature();
		TextParser.parseNameAndLevel(character, "Alghollthu Master (Aboleth) CR 7");
		assertThat(character.getName()).isEqualTo("Alghollthu Master (Aboleth)");
		assertThat(character.getLevel()).isEqualTo(7);
	}

	@Test
	public void testParsingNameHyphens_IsOk() throws Exception
	{
		Creature character = new Creature();
		TextParser.parseNameAndLevel(character, "Will-o-Wisp CR 6");
		assertThat(character.getName()).isEqualTo("Will-o-Wisp");
		assertThat(character.getLevel()).isEqualTo(6);
	}

	@Test
	public void testParsingNameComma_IsOk() throws Exception
	{
		Creature character = new Creature();
		TextParser.parseNameAndLevel(character, "Barghest, Greater CR 7");
		assertThat(character.getName()).isEqualTo("Barghest, Greater");
		assertThat(character.getLevel()).isEqualTo(7);
	}

	@Test
	public void testParsingNameAmpersand_IsOk() throws Exception
	{
		Creature character = new Creature();
		TextParser.parseNameAndLevel(character, "Gold & Black Dragon CR 7");
		assertThat(character.getName()).isEqualTo("Gold & Black Dragon");
		assertThat(character.getLevel()).isEqualTo(7);
	}

	@Test
	public void testParsingNameColon_IsOk() throws Exception
	{
		Creature character = new Creature();
		TextParser.parseNameAndLevel(character, "Gold: Dragon CR 7");
		assertThat(character.getName()).isEqualTo("Gold: Dragon");
		assertThat(character.getLevel()).isEqualTo(7);
	}

	@Test
	public void testParsingNameApostrophe_IsOk() throws Exception
	{
		Creature character = new Creature();
		TextParser.parseNameAndLevel(character, "Wizard's Shackle CR 2");
		assertThat(character.getName()).isEqualTo("Wizard's Shackle");
	}

	@Test
	public void testParsingNameRightApostrophe_IsOk() throws Exception
	{
		Creature character = new Creature();
		TextParser.parseNameAndLevel(character, "Wizard’s Shackle CR 2");
		assertThat(character.getName()).isEqualTo("Wizard's Shackle");
	}

	@Test
	public void testParsingNameHasCreatureEnd_IsOk() throws Exception
	{
		Creature character = new Creature();
		TextParser.parseNameAndLevel(character, "Slime like Creature CR 7");
		assertThat(character.getName()).isEqualTo("Slime like Creature");
		assertThat(character.getLevel()).isEqualTo(7);
	}

	@Test
	public void testParsingNameHasCreature_IsOk() throws Exception
	{
		Creature character = new Creature();
		TextParser.parseNameAndLevel(character, "Some Creature From Deep CR 7");
		assertThat(character.getName()).isEqualTo("Some Creature From Deep");
		assertThat(character.getLevel()).isEqualTo(7);
	}

	// ------------ Invalid Level Testing ----------------//
	@Test
	public void testParsingLevelEndingWithChar_IsInvalid() throws Exception
	{
		Creature character = new Creature();
		TextParser.parseNameAndLevel(character, "Mega Goblin CR 7+");
		assertThat(character.getLevel()).isEqualTo(7);
	}

	// --------------Valid Level Testing ---------------//

	@Test
	public void testParsingFractionLevelGoblin_IsValid() throws Exception
	{
		Creature character = new Creature();
		TextParser.parseNameAndLevel(character, "Goblin CR 1/3");
		assertThat(character.getName()).isEqualTo("Goblin");
		assertThat(character.getLevel()).isEqualTo(0);
		assertThat(character.getLevelFraction()).isEqualTo(LevelFraction.ONE_THIRD);
	}

	@Test
	public void testParsingFractionLevelBat_IsValid() throws Exception
	{
		Creature character = new Creature();
		TextParser.parseNameAndLevel(character, "Common Bat CR 1/8");
		assertThat(character.getName()).isEqualTo("Common Bat");
		assertThat(character.getLevel()).isEqualTo(0);
		assertThat(character.getLevelFraction()).isEqualTo(LevelFraction.ONE_EIGHTH);
	}

}

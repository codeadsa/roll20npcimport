package org.pf1etools.roll20npc.scraper;

import static org.fest.assertions.api.Assertions.assertThat; // main one

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.stream.Collectors;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.pf1etools.creature.Creature;
import org.pf1etools.creature.utils.Sense;
import org.pf1etools.roll20npc.parser.TextParser;
import org.pf1etools.roll20npc.scraper.D20PfsrdScraper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("Dev")
public class FileNameTester
{
	@Test
	public void fileNameGriffon()
	{
		assertThat(D20PfsrdScraper.getFileNameFromUrl("https://www.d20pfsrd.com/bestiary/monster-listings/magical-beasts/griffon")).isEqualTo("griffon_magical-beasts.txt");
	}
	
	@Test
	public void fileName3pp()
	{
		assertThat(D20PfsrdScraper.getFileNameFromUrl("https://www.d20pfsrd.com/bestiary/monster-listings/outsiders/elemental/aerial-servant-3pp/")).isEqualTo("aerial-servant-3pp_outsiders-elemental.txt");
	}
	
//	@Test
//	public void fileNameGriffon()
//	{
//		assertThat(D20PfsrdScraper.getFileNameFromUrl("Griffon")).isEqualTo("Griffon");
//	}
//	
//	@Test
//	public void fileNameSpace()
//	{
//		assertThat(D20PfsrdScraper.getFileNameFromUrl("Griffon Black")).isEqualTo("Griffon_Black");
//	}
//	
//	@Test
//	public void fileNameComma()
//	{
//		assertThat(D20PfsrdScraper.getFileNameFromUrl("Griffon,Black")).isEqualTo("GriffonBlack");
//	}
//	
//	@Test
//	public void fileNameParen()
//	{
//		assertThat(D20PfsrdScraper.getFileNameFromUrl("Griffon (Black)")).isEqualTo("Griffon_Black");
//	}
}

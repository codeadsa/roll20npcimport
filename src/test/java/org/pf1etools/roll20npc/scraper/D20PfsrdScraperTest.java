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
public class D20PfsrdScraperTest
{
	
	public void scrapeGriffon() throws Exception
	{
		String url = "https://www.d20pfsrd.com/bestiary/monster-listings/magical-beasts/griffon";
		D20PfsrdScraper.downloadMonster(url);
	}
	
	
	public void scrapeMissingCreature() throws Exception
	{
		String url = "https://www.d20pfsrd.com/bestiary/monster-listings/animals/axe-beak";
		D20PfsrdScraper.downloadMonster(url);
	}
	
	
	public void downloadAasamar() throws Exception
	{
		String url = "https://www.d20pfsrd.com/bestiary/monster-listings/outsiders/aasimar";
		D20PfsrdScraper.downloadMonster(url);
	}
	
	
	public void downloadPig() throws Exception
	{
		String url = "https://www.d20pfsrd.com/bestiary/monster-listings/animals/pig";
		D20PfsrdScraper.downloadMonster(url);
	}
	
	
	public void downloadIssue() throws Exception
	{
		String url = "https://www.d20pfsrd.com/bestiary/monster-listings/vermin/botfly-giant";
		D20PfsrdScraper.deleteMonsterFile(url);
		D20PfsrdScraper.downloadMonster(url);
	}	
	
	
	public void parseOnlineMonster() throws Exception
	{
		String url = "https://www.d20pfsrd.com/bestiary/monster-listings/vermin/botfly-giant";
		String text = D20PfsrdScraper.getMonsterTextFromUrl(url);
		TextParser.parse(text);
	}
	
	
	public void downloadDifferentStylePages() throws Exception
	{
		String url = "https://www.d20pfsrd.com/bestiary/monster-listings/animals/lizard/giant-frilled-lizard"; //broken div so getting the div with statblock is just a fragment
		D20PfsrdScraper.deleteMonsterFile(url);
		D20PfsrdScraper.downloadMonster(url);
		
		url = "https://www.d20pfsrd.com/bestiary/monster-listings/animals/shark/hammerhead-shark"; //good div.statblock
		D20PfsrdScraper.deleteMonsterFile(url);
		D20PfsrdScraper.downloadMonster(url);
		
		url = "https://www.d20pfsrd.com/bestiary/monster-listings/outsiders/aasimar"; //uses table for CR {level}
		D20PfsrdScraper.deleteMonsterFile(url);
		D20PfsrdScraper.downloadMonster(url);
		
		url = "https://www.d20pfsrd.com/bestiary/monster-listings/magical-beasts/Afanc-smg"; //coudn't parse monster
		D20PfsrdScraper.deleteMonsterFile(url);
		D20PfsrdScraper.downloadMonster(url);
		
		url = "https://www.d20pfsrd.com/bestiary/monster-listings/constructs/shrine-stone-animated"; //ldn't find starti
		D20PfsrdScraper.deleteMonsterFile(url);
		D20PfsrdScraper.downloadMonster(url);
		
		url = "https://www.d20pfsrd.com/bestiary/monster-listings/outsiders/asakku-cr7"; //ldn't find starti
		D20PfsrdScraper.deleteMonsterFile(url);
		D20PfsrdScraper.downloadMonster(url);
			
	}
}

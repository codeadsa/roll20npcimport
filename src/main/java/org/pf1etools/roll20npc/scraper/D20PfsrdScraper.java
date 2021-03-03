package org.pf1etools.roll20npc.scraper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.fest.util.Collections;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class D20PfsrdScraper
{
	public static final String CREATURE_OUTPUT_FOLDER= "./src/test/resources/creatures/d20pfsrd/";
	
	static ArrayList<String> urlMasterListAtoZ = new ArrayList<>();
	
	static
	{
		//starting url list taken from https://www.d20pfsrd.com/bestiary/bestiary-alphabetical/
		
//		urlMasterListAtoZ.add("https://www.d20pfsrd.com/bestiary/bestiary-by-challenge-rating/bestiary-cr-under-1/");
//		urlMasterListAtoZ.add("https://www.d20pfsrd.com/bestiary/bestiary-by-challenge-rating/bestiary-cr-1-2");
//		urlMasterListAtoZ.add("https://www.d20pfsrd.com/bestiary/bestiary-by-challenge-rating/bestiary-cr-3-4");
//		urlMasterListAtoZ.add("https://www.d20pfsrd.com/bestiary/bestiary-by-challenge-rating/bestiary-cr-5-6");
//		urlMasterListAtoZ.add("https://www.d20pfsrd.com/bestiary/bestiary-by-challenge-rating/bestiary-cr-7-8");
//		urlMasterListAtoZ.add("https://www.d20pfsrd.com/bestiary/bestiary-by-challenge-rating/bestiary-cr-9-10");
//		urlMasterListAtoZ.add("https://www.d20pfsrd.com/bestiary/bestiary-by-challenge-rating/bestiary-cr-11-12");
//		urlMasterListAtoZ.add("https://www.d20pfsrd.com/bestiary/bestiary-by-challenge-rating/bestiary-cr-13-14");
//		urlMasterListAtoZ.add("https://www.d20pfsrd.com/bestiary/bestiary-by-challenge-rating/bestiary-cr-15-16");
//		urlMasterListAtoZ.add("https://www.d20pfsrd.com/bestiary/bestiary-by-challenge-rating/bestiary-cr-17-18");
//		urlMasterListAtoZ.add("https://www.d20pfsrd.com/bestiary/bestiary-by-challenge-rating/bestiary-cr-19-20");
//		urlMasterListAtoZ.add("https://www.d20pfsrd.com/bestiary/bestiary-by-challenge-rating/bestiary-cr-20-plus");		
		
		urlMasterListAtoZ.add("https://www.d20pfsrd.com/bestiary/bestiary-alphabetical/bestiary-a-b/");
		urlMasterListAtoZ.add("https://www.d20pfsrd.com/bestiary/bestiary-alphabetical/bestiary-c-d/");
		urlMasterListAtoZ.add("https://www.d20pfsrd.com/bestiary/bestiary-alphabetical/bestiary-e-f/");
		urlMasterListAtoZ.add("https://www.d20pfsrd.com/bestiary/bestiary-alphabetical/bestiary-g-h/");
		urlMasterListAtoZ.add("https://www.d20pfsrd.com/bestiary/bestiary-alphabetical/bestiary-i-j/");
		urlMasterListAtoZ.add("https://www.d20pfsrd.com/bestiary/bestiary-alphabetical/bestiary-k-l/");
		urlMasterListAtoZ.add("https://www.d20pfsrd.com/bestiary/bestiary-alphabetical/bestiary-m-n/");
		urlMasterListAtoZ.add("https://www.d20pfsrd.com/bestiary/bestiary-alphabetical/bestiary-o-p/");
		urlMasterListAtoZ.add("https://www.d20pfsrd.com/bestiary/bestiary-alphabetical/bestiary-q-r/");
		urlMasterListAtoZ.add("https://www.d20pfsrd.com/bestiary/bestiary-alphabetical/bestiary-s-t/");
		urlMasterListAtoZ.add("https://www.d20pfsrd.com/bestiary/bestiary-alphabetical/bestiary-u-v/");
		urlMasterListAtoZ.add("https://www.d20pfsrd.com/bestiary/bestiary-alphabetical/bestiary-w-x/");
		urlMasterListAtoZ.add("https://www.d20pfsrd.com/bestiary/bestiary-alphabetical/bestiary-y-z/");
		
	}
	
	@SneakyThrows
	protected static void downloadMonstersFromUrl(String urlMonsterListing)
	{		
		log.debug("START to download monsters from list {}", urlMonsterListing);

		Document doc = Jsoup.connect(urlMonsterListing).get();
		Elements links = doc.select("a");
		
		for(int i=0; i<links.size(); i++)
		{
			Element link = links.get(i);
			String url = link.absUrl("href");
			if (url.contains("bestiary/monster-listings"))
			{
				log.debug("Got a beast url of '{}'", url);
				try
				{
					downloadMonster(url);
					log.debug("Finished downloading {}", url);
				}
				catch(Exception err)
				{
					log.error(err.getMessage());
				}
			}
		};
	}
	
	protected static void downloadMonster(String url) throws Exception
	{
		//check if monster already exists
		String fileName = getFileNameFromUrl(url);
		File creatureFile = new File(CREATURE_OUTPUT_FOLDER +fileName);
		if (creatureFile.exists())
		{
			log.info("{} already existed, url was '{}'", creatureFile.getAbsoluteFile(), url);
		}
		else
		{
			String monsterText = getMonsterTextFromUrl(url);
			if (StringUtils.isNotBlank(monsterText))
			{
				monsterText = url +"\r\n" +monsterText;
				writeMonsterTextToFile(creatureFile.getAbsolutePath(), monsterText);
			}
			else
			{
				log.warn("Couldn't parse monster from URL='{}'", url);
			}
		}
	}
	
	protected static String getMonsterTextFromUrl(String urlOfMonster) throws Exception
	{
		Document doc = Jsoup.connect(urlOfMonster)
				.userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
			    .referrer("http://www.google.com")
				.get();
		String html = doc.html();
		if (StringUtils.isNotBlank(html)) {
			int statblockIndex = -1;
			int titleIndex = -1;
			
			int startIndex = -1;
			int endIndex = -1;
			statblockIndex = html.indexOf("<div class=\"statblock\">");
			titleIndex = html.indexOf("<p class=\"title\">");

			if (statblockIndex != -1 && titleIndex == -1)
				startIndex = statblockIndex;
			else if (statblockIndex == -1 && titleIndex != -1)
				startIndex = titleIndex;
			else
			{
				if (statblockIndex < titleIndex)
					startIndex = statblockIndex;
				else
					startIndex = titleIndex;
			}
			
			if (startIndex == -1)
			{
				startIndex = html.indexOf("class=\"text\"");
				startIndex = html.substring(0, startIndex).lastIndexOf("<table");
			}
			
			endIndex = html.indexOf("ECOLOGY", startIndex);
			if (endIndex == -1)
				endIndex = html.toLowerCase().indexOf("ecology</p>", startIndex);
			if (endIndex == -1)
				endIndex = html.indexOf("BACKGROUND", startIndex);
			if (endIndex == -1)
				endIndex = html.indexOf("background</p>", startIndex);
			if (endIndex == -1)
				endIndex = html.indexOf("SPECIAL ABILITIES", startIndex);
			
			if (startIndex != -1 && endIndex != -1)
			{
				html = html.substring(startIndex, endIndex);
				html = html.replace("<br>", "|<br>").replace("</p>", "|</p>").replace("</tr>", "|</tr>");		
				Element statBlock = Jsoup.parse(html);
				String text = statBlock.text().replace("|", "\r\n");
				StringBuilder sbNewText = new StringBuilder();
				List<String> lines = Arrays.asList(text.split("\r\n"));
				lines.forEach(line -> {
						sbNewText.append(line.trim()).append("\r\n");
				});
				return sbNewText.toString();
			}
			else
			{
				log.warn("Couldn't find starting {} or ending {} point from URL='{}'",startIndex, endIndex, urlOfMonster);
			}
		}
		return null;
	}
	
	protected static String getMonsterTextFromUrl2(String urlOfMonster) throws Exception
	{
		try
		{
			Document doc = Jsoup.connect(urlOfMonster)
					.userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
				    .referrer("http://www.google.com")
					.get();
			Element statBlock = doc.select("div.statblock").first();
			String html = null;
			if (statBlock != null)
				html = statBlock.html();
			
			if (StringUtils.isBlank(html) || html.length() < 1000)
			{
				//getting the div statblock failed, try something else.
				html = doc.html();
				int startIndex = html.indexOf("<p class=\"title\">");
				int endIndex = html.indexOf("ECOLOGY") +"ECOLOGY".length();
				if (startIndex != -1)
					html = html.substring(startIndex, endIndex);
				else
					html = null;
				
			}
			
			if (html != null)
			{
				html = html.replace("<br>", "|<br>").replace("</p>", "|</p>");
				//remove ECOLOGY and anything after that.
				if (html.contains("ECOLOGY"))
				{
					html = html.substring(0, html.indexOf("ECOLOGY"));
				}
				statBlock = Jsoup.parse(html);
				String text = statBlock.text().replace("|", "\r\n");
				StringBuilder sbNewText = new StringBuilder();
				List<String> lines = Arrays.asList(text.split("\r\n"));
				lines.forEach(line -> {
					//capture all but the ending "ecology" section.
					if ( ! line.contains("ECOLOGY"))
						sbNewText.append(line.trim()).append("\r\n");
				});
				html = sbNewText.toString();
			}
			return html;
		}
		catch(HttpStatusException hse)
		{
			if (hse.getStatusCode() == 404)
				log.error("404 from {}", urlOfMonster);
			else
				log.error(hse.getMessage());
			throw hse;
		}
	}
	
	protected static void deleteMonsterFile(String url) throws IOException
	{
		String fileName = getFileNameFromUrl(url);
		File file = new File(CREATURE_OUTPUT_FOLDER +fileName);
		if (file.exists())
			file.delete();
	}
	
	protected static void writeMonsterTextToFile(String fileName, String text) throws IOException
	{
//		Files.writeString(Paths.get(fileName), text);
	}
	
	protected static String getFileNameFromUrl(String url)
	{
		if (url.endsWith("/"))
			url = url.substring(0, url.lastIndexOf("/"));
		String fileName = url.substring(url.lastIndexOf("/")+1, url.length());
		String category = url.substring(url.indexOf("monster-listings/") +"monster-listings/".length(), url.lastIndexOf("/"));
		fileName = fileName +"_" +category.replaceAll("/", "-");
		return fileName.replaceAll(" ", "_").replace("/[^a-zA-Z0-9_\\-.]/g", "") +".txt";
//		return creatureName.replaceAll(" ", "_").replace("/[^a-zA-Z0-9 ]/g", "");
	}
	
	public static void main(String args[]) throws Exception
	{
		log.debug("Starting Scraper");
		if (Collections.isNullOrEmpty(urlMasterListAtoZ))
		{
			log.error("urlMasterListAtoZ had no URLs");
			return;
		}
		else
		{
			for (int i=0; i<urlMasterListAtoZ.size(); i++)
			{
				downloadMonstersFromUrl(urlMasterListAtoZ.get(i));
			}
		}
	}
}

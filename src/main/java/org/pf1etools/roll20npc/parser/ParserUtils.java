package org.pf1etools.roll20npc.parser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.commons.lang3.StringUtils;
import org.pf1etools.creature.utils.Sense;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class ParserUtils
{

	public static String fileToString(File file) throws IOException
	{
//		String content = Files.readString(Paths.get(file.getAbsolutePath()));
		String content = new String(Files.readAllBytes(Paths.get(file.getAbsolutePath()))); 
		return content;
	}

	public static String replaceLast(String text, String regex, String replacement)
	{
		return text.replaceFirst("(?s)(.*)"+regex, "$1"+replacement);
	}

	public static String sanitize(String text)
	{
		if (StringUtils.isBlank(text))
			return null;

		return text.replaceAll("×","x").replaceAll("’", "'").replaceAll("′", "'").replaceAll("`", "'").replaceAll("‘", "'").replaceAll("“", "\"").replaceAll("”", "\"").replaceAll("–", "-").replaceAll("—", "-").replaceAll("‒", "-").replaceAll("−", "-").trim();
	}

	public static Integer getModifyer(String name, String nameValueText)
	{
		log.trace("name = '{}', nameValueText = '{}'", name, nameValueText);
		if (StringUtils.isAnyBlank(name, nameValueText))
			return null;

		String stValue = null;
		try
		{
			stValue = nameValueText.replace(name, "").replace("+", "").replace(";", "").replace(",", "").trim();
			return Integer.parseInt(stValue);
		}
		catch (NumberFormatException nfe)
		{
			log.error("Could not getModifyer from name = '{}', value = '{}'", name, stValue, nfe);
			return null;
		}
	}

	public static String trimNullSafe(String txt)
	{
		if (StringUtils.isBlank(txt))
			return null;
		else
			return txt.trim();
	}

	public static String capitalizeWord(String str)
	{
		String words[] = str.split("\\s");
		String capitalizeWord = "";
		for (String w : words)
		{
			String first = w.substring(0, 1);
			String afterfirst = w.substring(1);
			capitalizeWord += first.toUpperCase()+afterfirst+" ";
		}
		return capitalizeWord.trim();
	}
}

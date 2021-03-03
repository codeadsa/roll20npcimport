package org.pf1etools.creature.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.pf1etools.roll20npc.parser.TextParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

@Deprecated //treating senses as a string for now.
@Log4j2
public enum Sense
{
	ARCANE_SIGHT ("arcane sight"),
	DARKVISION ("darkvision"),
	LOW_LIGHT_VISION ("low-light vision"),
	SCENT ("scent"),
	TREMORSENSE ("tremorsense"),
	THOUGHSENSE ("thoughtsense"),
	BLINDSENSE ("blindsense"),
	BLINDSIGHT ("blindsight"),
	DRAGON_SENSES ("dragon senses"),
	GREENSIGHT ("greensight"),
	KEEN_SCENT ("keen scent"),
	LIFESENSE("lifesense"),
	MISTSIGHT("mistsight"),
	TRUE_SEEING("true seeing"),
	SEE_IN_DARKNESS("see in darkness");

	private static final Pattern SENSE_PATTERN = Pattern.compile("(^[a-zA-Z\\- ]+[^ 0-9$])( (\\d+)( ?)ft)?");
	
	@Getter
	@Setter
	private String name;
	
	@Getter
	@Setter
	private Integer distanceInFeet;
	
	Sense(String name)
	{
		this.name = name;
	}
	
	public static Sense parseSense(String text)
	{
		if (StringUtils.isBlank(text))
			return null;
		
		log.trace("Sense.getSense text = '{}'", text);
		text = text.replace(";", "").trim();
		String txtSense = null;
		String txtDistance = null;
		
		if ( ! text.contains(" ft"))
		{
			txtSense = text;
		}
		else
		{
			Matcher senseMatcher = SENSE_PATTERN.matcher(text);
			if (senseMatcher.find())
			{
				txtSense = senseMatcher.group(1);
				txtDistance = senseMatcher.group(3);
			}
		}
		
		log.trace("parsed sense = '{}', distance = '{}'", txtSense, txtDistance);
		
		String name = txtSense;
		Integer distanceInFeet = null;
		if (StringUtils.isNotBlank(txtDistance))
		{
			distanceInFeet = Integer.parseInt(txtDistance);
		}
		
		Sense sense = null;
		switch(txtSense)
		{
			case "arcane sight" : sense = ARCANE_SIGHT; break;
			case "darkvision" : sense = DARKVISION; break;
			case "low-light vision" : sense = LOW_LIGHT_VISION; break;
			case "scent" : sense = SCENT; break;
			case "tremorsense" : sense = TREMORSENSE; break;
			case "thoughtsense" : sense = THOUGHSENSE; break;
			case "blindsense" : sense = BLINDSENSE; break;
			case "blindsight" : sense = BLINDSIGHT; break;
			case "dragon senses" : sense = DRAGON_SENSES; break;
			case "greensight" : sense = GREENSIGHT; break;
			case "keen scent" : sense = KEEN_SCENT; break;
			case "lifesense" : sense = LIFESENSE; break;
			case "mistsight" : sense = MISTSIGHT; break;
			case "see in darkness" : sense = SEE_IN_DARKNESS; break;
			case "true seeing" : sense = TRUE_SEEING; break;
			default:
			{
				log.error("Could not parse sense of '{}'", text);
				return null;
			}
		}
		
		if (sense != null)
			sense.setDistanceInFeet(distanceInFeet);
		
		return sense;
	}
	
}

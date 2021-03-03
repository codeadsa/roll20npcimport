package org.pf1etools.roll20npc.parser;

import java.text.ParseException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.pf1etools.creature.Creature;
import org.pf1etools.creature.utils.LevelFraction;
import org.pf1etools.creature.utils.Sense;
import org.pf1etools.creature.utils.Skill;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class TextParser
{
	private static final Pattern PATTERN_NAME_LEVEL = Pattern.compile("^(?<name>.*) ?CR.*?(?<level>[0-9\\-\\/]+).*");
	private static final Pattern PATTERN_XP = Pattern.compile("^XP.*?(?<xp>[0-9,\\-]+)");
	private static final Pattern PATTERN_INIT_SENSE_AURA = Pattern.compile("^Init.*?(?<init>[0-9-]+).*?(Senses.*? (?<senses>.*?);?,? ?)?(Perception.*?(?<percept>[0-9\\-]+)(?<perceptNotes>.*?))?((;? Aura )(?<aura>.+))?$");
	private static final Pattern PATTERN_AURA = Pattern.compile("^Aura (?<aura>.*)");
	private static final Pattern PATTERN_DEFENSE_ARMOR_CLASS = Pattern.compile("^AC.*?(?<ac>[0-9]+).*touch.*?(?<touch>[0-9\\-]+).*flat-footed.*?(?<ffoot>[0-9\\-]+) ?\\(?(?<notes>.*?)\\)?$");
	private static final Pattern PATTERN_DEFENSE_HEALTH = Pattern.compile("^((hp)|(HP)) (?<hp>[0-9]+) \\(((?<level>[0-9]*) HD; )?(?<level2>[0-9]+)d(?<hitdie>[0-9]+)(?<hpMod>.*?)\\)(; (?<notes>.*))?");
	private static final Pattern PATTERN_DEFENSE_SAVING_THROWS = Pattern.compile("^Fort.*?(?<fort>[0-9\\-]+) ?\\(?(?<fortNoes>.*?)\\)?,?;? ?Ref.*?(?<ref>[0-9\\-]+) ?\\(?(?<refNoes>.*?)\\)?,?;? ?Will.*?(?<will>[0-9\\-]+)( ?\\(?(?<willNoes>.*?)\\),?;?)?;? ?(?<notes>.*)");
	private static final Pattern PATTERN_DEFENSE_DR_SR_IMMUNE_RESIST_WEAK = Pattern.compile("(Defensive Abilities (?<da>.*?)(; )?)?(DR (?<dr>.*?)(; )?)?(Immune (?<imm>.*?)(; )?)?(Resist (?<res>.*?)(; )?)?(SR (?<sr>[0-9]+)+.*?)?(Weaknesses (?<weak>.*?)(; )?)?$");
	private static final Pattern PATTERN_STATISTICS_ABILITY_SCORES = Pattern.compile("Str.*?(?<str>[0-9\\-]+).*Dex.*?(?<dex>[0-9\\-]+).*Con.*?(?<con>[0-9\\-]+).*Int.*?(?<int>[0-9\\-]+).*Wis.*?(?<wis>[0-9\\-]+).*Cha.*?(?<cha>[0-9\\-]+).*");
	private static final Pattern PATTERN_BAB_CMB_CMD = Pattern.compile("Base Atk.*?(?<bab>[0-9\\-]+).*CMB.*?(?<cmb>[0-9\\-]+)( ?\\((?<cmbNotes>.*)\\))?.*CMD.*?(?<cmd>[0-9\\-]*)( ?\\((?<cmdNotes>.*)\\))?$");
	private static final Pattern PATTERN_ALIGN_SIZE_TYPE = Pattern.compile("(?<align>LG |LN |LE |NG |N |NE |CG |CN |CE )(?<size>Fine|Diminutive|Tiny|Small|Medium|Large|Huge|Gargantuan|Colossal)( (?<type>.*))?");
	private static final Pattern PATTERN_SPEED = Pattern.compile("^((Speed)|(Spd)) ((?<land>[0-9]+).*?)?((b|B)urrow.*?(?<burrow>[0-9]+).*?)?((c|C)limb (?<climb>[0-9]+).*?)?((f|F)ly (?<fly>[0-9]+).*?\\((?<flyNotes>.*)\\).*?)?((s|S)wim (?<swim>[0-9]+).*?)?(; (?<notes>.*))?$");
	private static final Pattern PATTERN_SPACE_REACH = Pattern.compile("^Space.*?(?<space>[0-9]+).*?Reach.*?(?<reach>[0-9]+).*?(\\((?<notes>.*)\\))?$");
	private static final Pattern PATTERN_MELEE_RANGED_SPECIAL_ATTACKS = Pattern.compile("^(Melee (?<melee>.*))?(Ranged (?<range>.*))?(Special Attacks (?<spclatk>.*))?");
	private static final Pattern PATTERN_FEATS = Pattern.compile("^Feats (?<feats>.*)");
	private static final Pattern PATTERN_SQ = Pattern.compile("^SQ (?<sq>.*)");
	
	// URL_REGEX_NAME_LEVEL = "https://regex101.com/r/vdNAcI/2";
	// URL_REGEX_XP = "https://regex101.com/r/3qjOxN/5/";
	// URL_REGEX_INIT_SENSE_AURA = "https://regex101.com/r/YVqdNi/8";
	// URL_REGEX_DEFENSE_ARMOR_CLASS = "https://regex101.com/r/PVueRA/10";
	// URL_REGEX_DEFENSE_HEALTH = "https://regex101.com/r/M5KQtK/4/";
	// URL_REGEX_DEFENSE_SAVING_THROWS = "https://regex101.com/r/JXrKhW/5";
	// URL_REGEX_DEFENSE_DR_SR_IMMUNE_RESIST_WEAK = "https://regex101.com/r/iQcrdg/5";
	// URL_REGEX_STATISTICS_ABILITY_SCORES = "https://regex101.com/r/AIqzzd/8";
	// URL_REGEX_BAB_CMB_CMD = "https://regex101.com/r/SNU55U/7";
	// URL_REGEX_SKILLS = "https://regex101.com/r/twktKt/8/";
	// URL_ALIGN_SIZE_TYPE = "https://regex101.com/r/AJsKC0/3";
	// URL_SPEED = "https://regex101.com/r/95ah2w/4"; 
	// URL_SPACE_REACH = "https://regex101.com/r/rh3bf5/2/";
	// URL_MELEE_RANGED_SPECIAL_ATTACKS = "https://regex101.com/r/uyenUC/3";
	// URL_MELEE_PARSER = "";
	// URL_FEATS = "https://regex101.com/r/UrHowk/1";
	
	
	private static Pattern PATTERN_SKILLS = null;
	static
	{
		// get all the skills and build the Matching Pattern
		StringBuilder sbPatternSkills = new StringBuilder("^Skills.*?");
		Arrays.asList(Skill.values()).forEach(skill -> {
			sbPatternSkills.append(String.format("(%s.*?(?<%s>[0-9]+)( \\((?<%s>.*?)\\))?.*?)?", skill.getParseName(), skill.getAbbr(), skill.getAbbr()+"Notes"));
		});
		sbPatternSkills.append("(; (?<notes>.*))?$");
		log.trace("PATTERN_SKILLS = '{}'",sbPatternSkills.toString());
		PATTERN_SKILLS = Pattern.compile(sbPatternSkills.toString());
	}

	public static boolean isValidCreatureText(String creatureText)
	{
		if (StringUtils.isBlank(creatureText))
			return false;
		else if (creatureText.contains("CR "))
			return true;
		else
			return false;
	}
	
	public static String preFormatTextForPdfFixes(String creatureText)
	{

		//PDF's somtimes end in comma for format/linebreak reasons.
		creatureText = creatureText.replaceAll(",\r\n", ", ");
		creatureText = creatureText.replaceAll(",\n", ", ");
		creatureText = creatureText.replaceAll("Knowledge\n", "Knowledge ");
		creatureText = creatureText.replaceAll("Resist\n", "Resist ");
		creatureText = creatureText.replaceAll("Racial\n", "Racial ");
		
		//pdf stuff
		creatureText = creatureText.replace("I nit ", "Init ");
		creatureText = creatureText.replaceAll("\\+i", "\\+1");
		return creatureText;
	}
	
	public static Creature parse(String creatureText) throws ParseException
	{
		if (StringUtils.isBlank(creatureText))
			return null;
		
		//fix formatting issues
		creatureText = creatureText.replace("STATISTICS", "\r\nSTATISTICS\r\n");
		creatureText = creatureText.replace("DEFENSES", "\r\nDEFENSES\r\n");
		creatureText = creatureText.replace("DEFENSE", "\r\nDEFENSE\r\n");
		creatureText = creatureText.replace("OFFENSE", "\r\nOFFENSE\r\n");
		creatureText = creatureText.replace("flatfooted ", "flat-footed ");
		creatureText = creatureText.replace("Weakness ", "Weaknesses ");
		creatureText = creatureText.replace("Melee* ", "Melee ");
		
		creatureText = creatureText.replace("BaseAtk ", "Base Atk ");
		creatureText = creatureText.replace("BAB ", "Base Atk ");
		
		
		Creature creature = null;
		String[] lines = creatureText.split("\n");
		if (lines.length == 1) //if reading from windows file it's \r\n, if from java swing textarea it's \n
			lines = creatureText.split("\r\n");
		
		boolean readCrLine = false;
		for (int i=0; i<lines.length; i++)
		{
			String text = lines[i];
			
			text = ParserUtils.sanitize(text);
			
			if (StringUtils.isBlank(text))
				continue; //ignore blank lines
			
			//some text fixes
			if (text.startsWith("Vulnerable"))
				text = "Weaknesses " +text;
				
			//ignore all lines until you get to "CR ", then that line and the rest is important to parse.
			if (text.contains("CR ") && readCrLine == false)
			{
				if (text.startsWith("CR "))
					text = lines[i-1].replace("\r","") +" " +text; //fixes Ice Gnome (Barbegazi)\r\nCR 1
				readCrLine = true;
				creature = new Creature();
				parseNameAndLevel(creature, text);
			}
			
			if (text.startsWith("Spell-Like Abilities (CL ") || text.contains("Spells Prepared (CL") || text.contains("Spells Known (CL") || text.contains("Psychic Magic (") || text.contains("Kineticist Wild Talents Known"))
			{
				text = text +"\r\n";
				//accumulate until finding an start of a new block.
				boolean isNewStart = false;

				while ( ! isNewStart)
				{
					if (i+1 >= lines.length)
					{
						isNewStart = true;
					}
					else
					{
						String nextLine = lines[i+1];
						if (nextLine.startsWith("Spell-Like Abilities (CL ") || nextLine.contains("Spells Prepared (CL") || nextLine.contains("Spells Known (CL") || nextLine.startsWith("STATISTICS") || nextLine.startsWith("Psychic Magic (") || nextLine.startsWith("Kineticist Wild Talents Known"))
						{
							isNewStart = true;
						}
						else
						{
							text = text.trim() +"\r\n" +lines[++i];	
						}
					}
				}
			}
			
			if (text.startsWith("SPECIAL ABILITIES"))
			{
				text = text +"\r\n";
				//accumulate until finding an start of a new block.
				boolean isNewStart = false;

				while ( ! isNewStart)
				{
					if (i+1 >= lines.length)
					{
						isNewStart = true;
					}
					else
					{
						String nextLine = lines[i+1];
						if (nextLine.contains("ECOLOGY") || nextLine.contains("ecology") || nextLine.contains("BACKGROUND") || nextLine.contains("background"))
						{
							isNewStart = true;
						}
						else
						{
							text = text.trim() +"\r\n" +lines[++i];	
						}
					}
				}
			}
			
			if (text.startsWith("Senses ")) //for marilith_outsiders-demon.txt where senses is on a line after init.
				text = lines[i-1].trim() +text;
			
			if (readCrLine && StringUtils.isNotBlank(text))
			{
				if (text.startsWith("Init "))
					parseInitAndSensesAndAura(creature, text);
				if (text.startsWith("Aura "))
					parseAura(creature, text);
				if (text.startsWith("AC "))
					parseDefenseArmorClass(creature, text);
				if (text.startsWith("hp "))
					parseDefenseHitPoints(creature, text);
				if (text.startsWith("Fort "))
					parseDefenseSavingThrows(creature, text);
				if (text.startsWith("Str "))
					parseStatisticsAbilityScores(creature, text);
				if (text.startsWith("Base Atk"))
					parseBabCmbCmd(creature, text);
				if (text.startsWith("Skills"))
					parseSkills(creature, text);
				if (text.startsWith("Defensive Abilities") || text.contains("DR ") || text.contains("Immune ") || text.contains("Resist ") || text.contains("SR "))
					parseDefensiveAbilities(creature, text);
				if (text.startsWith("Weaknesses "))
					parseWeaknesses(creature, text);
				if (text.startsWith("LG ") ||text.startsWith("LN ") ||text.startsWith("LE ") ||text.startsWith("NG ") ||text.startsWith("N ") ||text.startsWith("NE ") ||text.startsWith("CG ") ||text.startsWith("CN ") ||text.startsWith("CE "))
					parseAlignSizeType(creature, text);
				if (text.startsWith("XP "))
					parseXp(creature, text);
				if (text.startsWith("Speed ") || text.startsWith("Spd "))
					parseSpeed(creature, text);
				if (text.startsWith("Space "))
					parseSpaceReach(creature, text);
				if (text.startsWith("Melee ") || text.startsWith("Ranged ") || text.startsWith("Special Attacks "))
					parseMeleeRangedSpecialAttacks(creature, text);
				if (text.startsWith("Feats "))
					parseFeats(creature, text);
				if (text.startsWith("SQ "))
					parseSpecialQualities(creature, text);
				if (text.startsWith("Spell-Like Abilities (CL ") || text.contains("Spells Prepared (CL") || text.contains("Spells Known (CL") || text.startsWith("SPECIAL ABILITIES") || text.contains("Psychic Magic (") || text.startsWith("Kineticist Wild Talents Known") )
					parseSpellLikePrepKnownSpecialAbilitiesPsychic(creature, text);			
			}
		}
		log.debug(creature.toString());
		return creature;
	}

	protected static void parseNameAndLevel(Creature creature, String text) throws ParseException
	{
		log.trace("TextParser.parseNameAndLevel got line of '{}'", text);
		// massage the data to fit our parser better.
		text = ParserUtils.sanitize(text);

		// parse the values
		Matcher matcher = PATTERN_NAME_LEVEL.matcher(text);
		if (matcher.find())
		{
			String txtName = matcher.group("name").trim();
			String txtLevel = matcher.group("level").trim();

			log.trace("parsed name='{}', level='{}'", txtName, txtLevel);

			if (StringUtils.isBlank(txtName))
			{
				String stError = String.format("Coudn't parse name, it was blank. input = '%s'", text);
				log.error(stError);
				throw new ParseException(stError, -1);
			}
			
			// set the creature
			creature.setName(txtName);
			setLevel(creature, txtLevel);
		}
		else
		{
			String stError = String.format("parseNameAndLevel invalid format '%s'", text);
			log.error(stError);
			throw new ParseException(stError, -1);
		}

		log.debug("Creature values name = '{}', level = '{}', levelfraction = '{}'", creature.getName(), creature.getLevel(), creature.getLevelFraction());
	}

	private static void setLevel(Creature creature, String txtLevel) throws ParseException
	{
		try
		{
			if (!txtLevel.contains("/")) // is NOT a fraction
			{
				creature.setLevel(Integer.parseInt(txtLevel));
			}
			else
			{
				creature.setLevel(0);
				creature.setLevelFraction(LevelFraction.parseFraction(txtLevel));
			}
		}
		catch (NumberFormatException nfe)
		{
			log.trace("Could not parse level from input of '{}'", txtLevel, nfe);
			throw new ParseException(nfe.getMessage(), -1);
		}
	}

	protected static void parseInitAndSensesAndAura(Creature creature, String text)
	{
		log.trace("TextParser.parseInitAndSensesAndAura got line of '{}'", text);
		// massage the data to fit our parser better.
		text = ParserUtils.sanitize(text);

		// change the legacy spot to Perception
		text = text.replace("Spot", "Perception");
		text = text.replace("Sense ", "Senses ");
		// parse the values
		Matcher matcher = PATTERN_INIT_SENSE_AURA.matcher(text);
		if (matcher.find())
		{
			String txtInit = matcher.group("init");
			String txtSense = matcher.group("senses");
			String txtPerception = matcher.group("percept");
			String txtAura = matcher.group("aura");
			
			txtInit = ParserUtils.trimNullSafe(txtInit);
			txtSense = ParserUtils.trimNullSafe(txtSense);
			txtPerception = ParserUtils.trimNullSafe(txtPerception);
			txtAura = ParserUtils.trimNullSafe(txtAura);
			
			log.trace("parsed txtInit='{}', txtSense='{}', txtPerception='{}', txtAura='{}'", txtInit, txtSense, txtPerception, txtAura);

			// set the creature from the txt values
			creature.setInitiativeMod(ParserUtils.getModifyer("Init ", txtInit));
			if (StringUtils.isNotBlank(txtSense))
				creature.setSenses(txtSense);
			creature.setAura(txtAura);
			creature.skills.setPerceptionMod(ParserUtils.getModifyer("Perception ", txtPerception));
		}
		else
		{
			String stError = String.format("parseInitAndSensesAndAura invalid format '%s'", text);
			log.error(stError);
			//throw new ParseException(stError, -1);
		}

		log.debug("Creature values init='{}', senses='{}', perception='{}', aura='{}'", creature.getInitiativeMod(), creature.getSenses(), creature.skills.getPerceptionMod(), creature.getAura());
	}

	@Deprecated
	private static HashSet<Sense> parseSenses(String txtSenses)
	{
		if (StringUtils.isBlank(txtSenses))
			return null;

		HashSet<Sense> senseList = new HashSet();
		Arrays.asList(txtSenses.split(",")).forEach(txtSense -> {
			Sense sense = Sense.parseSense(txtSense);
			if (sense!=null)
				senseList.add(sense);
		});

		return senseList;
	}

	protected static void parseDefenseArmorClass(Creature creature, String text)
	{
		log.trace("TextParser.parseDefenseArmorClass got line of '{}'", text);
		// massage the data to fit our parser better.
		text = ParserUtils.sanitize(text);

		// parse the values
		Matcher matcher = PATTERN_DEFENSE_ARMOR_CLASS.matcher(text);
		if (matcher.find())
		{
			String txtAc = matcher.group("ac").replace("+", "");
			String txtTouch = matcher.group("touch").replace("+", "");
			String txtFlatFoot = matcher.group("ffoot").replace("+", "").replace("-", ""); //fixing AC 15, touch 13, flat-footed - (+2 Dex, +2 natural, +1 size)
			String txtNotes = matcher.group("notes");
			
			txtAc = ParserUtils.trimNullSafe(txtAc);
			txtTouch = ParserUtils.trimNullSafe(txtTouch);
			txtFlatFoot = ParserUtils.trimNullSafe(txtFlatFoot);
			txtNotes = ParserUtils.trimNullSafe(txtNotes);
			
			log.trace("parsed txtAc='{}', txtTouch='{}', txtFlatFoot='{}', txtNotes='{}'", txtAc, txtTouch, txtFlatFoot, txtNotes);

			// set the creature from the txt values
			creature.defenses.setAc(parseInt(txtAc, "AC"));
			creature.defenses.setAcTouch(parseInt(txtTouch, "Touch"));
			creature.defenses.setAcFlatFooted(parseInt(txtFlatFoot, "Flat Foot"));
			creature.defenses.setAcNotes(txtNotes);
		}
		else
		{
			String stError = String.format("parseDefenseArmorClass invalid format '%s'", text);
			log.error(stError);
//			throw new ParseException(stError, -1);
		}

		log.debug("Creature values ac='{}', touch='{}', flatfoot='{}', notes='{}'", creature.defenses.getAc(), creature.defenses.getAcTouch(), creature.defenses.getAcFlatFooted(), creature.defenses.getAcNotes());
	}

	private static Integer parseInt(String txt, String name)
	{
		if (StringUtils.isBlank(txt))
			return null;

		try
		{
			return Integer.parseInt(txt);
		}
		catch (NumberFormatException nfe)
		{
			log.error(nfe.getMessage());
//			throw new ParseException(String.format("Coudn't get a number for %s from %s", name, txt), -1);
			return null;
		}
	}
	
	protected static void parseDefenseHitPoints(Creature creature, String text)
	{
		log.trace("TextParser.parseDefenseHitPoints got line of '{}'", text);

		// massage the data to fit our parser better.
		text = ParserUtils.sanitize(text);
		text = text.replace("each ", "");
		
		// parse the values
		Matcher matcher = PATTERN_DEFENSE_HEALTH.matcher(text);
		if (matcher.find())
		{
			String txtHp = matcher.group("hp");
			String txtHd1 = matcher.group("level");
			txtHd1 = txtHd1==null ? "0" : txtHd1;
			String txtHd2 = matcher.group("level2");
			String txtHdie = matcher.group("hitdie");
			String txtHpMod = matcher.group("hpMod");
			txtHpMod = txtHpMod==null ? null : txtHpMod;
			String txtHpNotes = matcher.group("notes");
			
			txtHp = ParserUtils.trimNullSafe(txtHp);
			txtHd1 = ParserUtils.trimNullSafe(txtHd1);
			txtHd2 = ParserUtils.trimNullSafe(txtHd2);
			txtHdie = ParserUtils.trimNullSafe(txtHdie);
			txtHpMod = ParserUtils.trimNullSafe(txtHpMod);
			txtHpNotes = ParserUtils.trimNullSafe(txtHpNotes);
			
			log.trace("parsed txtHp='{}', txtHd1='{}', txtHd2='{}', txtHdie='{}', txtHpMod='{}', txtHpNotes='{}'", txtHp, txtHd1, txtHd2, txtHdie, txtHpMod, txtHpNotes);

			// set the creature from the txt values
			creature.defenses.setHp(parseInt(txtHp, "AC"));
			Integer hd = parseInt(txtHd1, "HD");
			Integer hd2 = parseInt(txtHd2, "HD");
			creature.defenses.setHd(hd>hd2 ? hd : hd2);
			creature.defenses.setHitDie(parseInt(txtHdie, "hit die"));
			if (StringUtils.isNotBlank(txtHpMod))
				creature.defenses.setHpMod(txtHpMod);
			if (StringUtils.isNotBlank(txtHpNotes))
				creature.defenses.setHpNotes(txtHpNotes);
		}
		else
		{
			String stError = String.format("parseDefenseHitPoints invalid format '%s'", text);
			log.error(stError);
//			throw new ParseException(stError, -1);
		}

		log.debug("Creature values hp='{}', hd='{}', hdie='{}', hpMod='{}', notes='{}'", creature.defenses.getHd(), creature.defenses.getHitDie(), creature.defenses.getHpMod(), creature.defenses.getHpNotes());
	}

	protected static void parseDefenseSavingThrows(Creature creature, String text)
	{
		log.trace("TextParser.parseDefenseSavingThrows got line of '{}'", text);

		// massage the data to fit our parser better.
		text = ParserUtils.sanitize(text);
		
		// parse the values
		Matcher matcher = PATTERN_DEFENSE_SAVING_THROWS.matcher(text);
		if (matcher.find())
		{
			String txtFort = matcher.group("fort");
			String txtRef = matcher.group("ref");
			String txtWill = matcher.group("will");
			String txtNotes = matcher.group("notes");
			
			txtFort = ParserUtils.trimNullSafe(txtFort);
			txtRef = ParserUtils.trimNullSafe(txtRef);
			txtWill = ParserUtils.trimNullSafe(txtWill);
			txtNotes = ParserUtils.trimNullSafe(txtNotes);
			
			log.trace("parsed txtFort='{}', txtRef='{}', txtWill='{}', txtNotes='{}'", txtFort, txtRef, txtWill, txtNotes);

			// set the creature from the txt values
			creature.defenses.setSaveFort(parseInt(txtFort, "Fort"));
			creature.defenses.setSaveReflex(parseInt(txtRef, "Reflex"));
			creature.defenses.setSaveWill(parseInt(txtWill, "Will"));
			if (StringUtils.isNotBlank(txtNotes))
				creature.defenses.setSaveNotes(txtNotes);
		}
		else
		{
			String stError = String.format("parseDefenseSavingThrows invalid format '%s'", text);
			log.error(stError);
//			throw new ParseException(stError, -1);
		}

		log.debug("Creature values fort='{}', reflex='{}', will='{}', notes='{}'", creature.defenses.getSaveFort(), creature.defenses.getSaveReflex(), creature.defenses.getSaveWill(), creature.defenses.getSaveNotes());
	}

	protected static void parseStatisticsAbilityScores(Creature creature, String text)
	{
		log.trace("TextParser.parseStatisticsAbilityScores got line of '{}'", text);

		// massage the data to fit our parser better.
		text = ParserUtils.sanitize(text);

		// parse the values
		Matcher matcher = PATTERN_STATISTICS_ABILITY_SCORES.matcher(text);
		if (matcher.find())
		{
			String txtStr = matcher.group("str");
			String txtDex = matcher.group("dex");
			String txtCon = matcher.group("con");
			String txtInt = matcher.group("int");
			String txtWis = matcher.group("wis");
			String txtCha = matcher.group("cha");
			
			txtStr = ParserUtils.trimNullSafe(txtStr);
			txtDex = ParserUtils.trimNullSafe(txtDex);
			txtCon = ParserUtils.trimNullSafe(txtCon);
			txtInt = ParserUtils.trimNullSafe(txtInt);
			txtWis = ParserUtils.trimNullSafe(txtWis);
			txtCha = ParserUtils.trimNullSafe(txtCha);
			
			log.trace("parsed txtStr='{}', txtDex='{}', txtCon='{}', txtInt='{}', txtWis='{}', txtCha='{}'", txtStr, txtDex, txtCon, txtInt, txtWis, txtCha);

			// set the creature from the txt values
			creature.stats.setAbilityStr(parseInt(txtStr, "Str"));
			creature.stats.setAbilityDex(parseInt(txtDex, "Dex"));
			creature.stats.setAbilityCon(parseInt(txtCon, "Con"));
			creature.stats.setAbilityInt(parseInt(txtInt, "Int"));
			creature.stats.setAbilityWis(parseInt(txtWis, "Wis"));
			creature.stats.setAbilityCha(parseInt(txtCha, "Cha"));
		}
		else
		{
			String stError = String.format("parseStatisticsAbilityScores invalid format '%s'", text);
			log.error(stError);
//			throw new ParseException(stError, -1);
		}

		log.debug("Creature stats='{}'", creature.stats.toString());
	}

	protected static void parseSkills(Creature creature, String text)
	{
		log.trace("TextParser.parseSkills got line of '{}'", text);

		// massage the data to fit our parser better.
		text = ParserUtils.sanitize(text);
		text = text.replace("Racial Modifiers ", "");
		// parse the values
		Matcher matcher = PATTERN_SKILLS.matcher(text);
		if (matcher.find())
		{
			String txtAcrobatics = matcher.group(Skill.ACROBATICS.getAbbr());
			String txtAppraise = matcher.group(Skill.APPRAISE.getAbbr());
			String txtBluff = matcher.group(Skill.BLUFF.getAbbr());
			String txtClimb = matcher.group(Skill.CLIMB.getAbbr());
			String txtCraft = matcher.group(Skill.CRAFT.getAbbr());
			String txtDiplomacy = matcher.group(Skill.DIPLOMACY.getAbbr());
			String txtDisableDevice = matcher.group(Skill.DISABLE_DEVICE.getAbbr());
			String txtDisguise = matcher.group(Skill.DISGUISE.getAbbr());
			String txtEscapeArtist = matcher.group(Skill.ESCAPE_ARTIST.getAbbr());
			String txtFly = matcher.group(Skill.FLY.getAbbr());
			String txtHandleAnimal = matcher.group(Skill.HANDLE_ANIMAL.getAbbr());
			String txtHeal = matcher.group(Skill.HEAL.getAbbr());
			String txtIntimidate = matcher.group(Skill.INTIMIDATE.getAbbr());
			String txtKnowAll = matcher.group(Skill.KNOWLEDGE_ALL.getAbbr());
			String txtKnowArcana = matcher.group(Skill.KNOWLEDGE_ARCANA.getAbbr());
			String txtKnowDung = matcher.group(Skill.KNOWLEDGE_DUNGEONEERING.getAbbr());
			String txtKnowEng = matcher.group(Skill.KNOWLEDGE_ENGINEERING.getAbbr());
			String txtKnowGeo = matcher.group(Skill.KNOWLEDGE_GEOGRAPHY.getAbbr());
			String txtKnowHist = matcher.group(Skill.KNOWLEDGE_HISTORY.getAbbr());
			String txtKnowLocal = matcher.group(Skill.KNOWLEDGE_LOCAL.getAbbr());
			String txtKnowNat = matcher.group(Skill.KNOWLEDGE_NATURE.getAbbr());
			String txtKnowNobl = matcher.group(Skill.KNOWLEDGE_NOBILITY.getAbbr());
			String txtKnowPlanes = matcher.group(Skill.KNOWLEDGE_PLANES.getAbbr());
			String txtKnowRelig = matcher.group(Skill.KNOWLEDGE_RELIGION.getAbbr());
			String txtLinguistics = matcher.group(Skill.LINGUISTICS.getAbbr());
			String txtPerception = matcher.group(Skill.PERCEPTION.getAbbr());
			String txtPerform = matcher.group(Skill.PERFORM.getAbbr());
			String txtProfession = matcher.group(Skill.PROFESSION.getAbbr());
			String txtRide = matcher.group(Skill.RIDE.getAbbr());
			String txtSenseMotive = matcher.group(Skill.SENSE_MOTIVE.getAbbr());
			String txtSlightOfHand = matcher.group(Skill.SLEIGHT_OF_HAND.getAbbr());
			String txtSpellcraft = matcher.group(Skill.SPELLCRAFT.getAbbr());
			String txtStealth = matcher.group(Skill.STEALTH.getAbbr());
			String txtSurvival = matcher.group(Skill.SURVIVAL.getAbbr());
			String txtSwim = matcher.group(Skill.SWIM.getAbbr());
			String txtUmd = matcher.group(Skill.USE_MAGIC_DEVICE.getAbbr());
			String txtNotes = matcher.group("notes");
			String txtAcrobaticsNotes = matcher.group(Skill.ACROBATICS.getAbbr()+"Notes");
			String txtAppraiseNotes = matcher.group(Skill.APPRAISE.getAbbr()+"Notes");
			String txtBluffNotes = matcher.group(Skill.BLUFF.getAbbr()+"Notes");
			String txtClimbNotes = matcher.group(Skill.CLIMB.getAbbr()+"Notes");
			String txtCraftNotes = matcher.group(Skill.CRAFT.getAbbr()+"Notes");
			String txtDiplomacyNotes = matcher.group(Skill.DIPLOMACY.getAbbr()+"Notes");
			String txtDisableDeviceNotes = matcher.group(Skill.DISABLE_DEVICE.getAbbr()+"Notes");
			String txtDisguiseNotes = matcher.group(Skill.DISGUISE.getAbbr()+"Notes");
			String txtEscapeArtistNotes = matcher.group(Skill.ESCAPE_ARTIST.getAbbr()+"Notes");
			String txtFlyNotes = matcher.group(Skill.FLY.getAbbr()+"Notes");
			String txtHandleAnimalNotes = matcher.group(Skill.HANDLE_ANIMAL.getAbbr()+"Notes");
			String txtHealNotes = matcher.group(Skill.HEAL.getAbbr()+"Notes");
			String txtIntimidateNotes = matcher.group(Skill.INTIMIDATE.getAbbr()+"Notes");
			String txtKnowAllNotes = matcher.group(Skill.KNOWLEDGE_ALL.getAbbr()+"Notes");
			String txtKnowArcanaNotes = matcher.group(Skill.KNOWLEDGE_ARCANA.getAbbr()+"Notes");
			String txtKnowDungNotes = matcher.group(Skill.KNOWLEDGE_DUNGEONEERING.getAbbr()+"Notes");
			String txtKnowEngNotes = matcher.group(Skill.KNOWLEDGE_ENGINEERING.getAbbr()+"Notes");
			String txtKnowGeoNotes = matcher.group(Skill.KNOWLEDGE_GEOGRAPHY.getAbbr()+"Notes");
			String txtKnowHistNotes = matcher.group(Skill.KNOWLEDGE_HISTORY.getAbbr()+"Notes");
			String txtKnowLocalNotes = matcher.group(Skill.KNOWLEDGE_LOCAL.getAbbr()+"Notes");
			String txtKnowNatNotes = matcher.group(Skill.KNOWLEDGE_NATURE.getAbbr()+"Notes");
			String txtKnowNoblNotes = matcher.group(Skill.KNOWLEDGE_NOBILITY.getAbbr()+"Notes");
			String txtKnowPlanesNotes = matcher.group(Skill.KNOWLEDGE_PLANES.getAbbr()+"Notes");
			String txtKnowReligNotes = matcher.group(Skill.KNOWLEDGE_RELIGION.getAbbr()+"Notes");
			String txtLinguisticsNotes = matcher.group(Skill.LINGUISTICS.getAbbr()+"Notes");
			String txtPerceptionNotes = matcher.group(Skill.PERCEPTION.getAbbr()+"Notes");
			String txtPerformNotes = matcher.group(Skill.PERFORM.getAbbr()+"Notes");
			String txtProfessionNotes = matcher.group(Skill.PROFESSION.getAbbr()+"Notes");
			String txtRideNotes = matcher.group(Skill.RIDE.getAbbr()+"Notes");
			String txtSenseMotiveNotes = matcher.group(Skill.SENSE_MOTIVE.getAbbr()+"Notes");
			String txtSlightOfHandNotes = matcher.group(Skill.SLEIGHT_OF_HAND.getAbbr()+"Notes");
			String txtSpellcraftNotes = matcher.group(Skill.SPELLCRAFT.getAbbr()+"Notes");
			String txtStealthNotes = matcher.group(Skill.STEALTH.getAbbr()+"Notes");
			String txtSurvivalNotes = matcher.group(Skill.SURVIVAL.getAbbr()+"Notes");
			String txtSwimNotes = matcher.group(Skill.SWIM.getAbbr()+"Notes");
			String txtUmdNotes = matcher.group(Skill.USE_MAGIC_DEVICE.getAbbr()+"Notes");
			txtAcrobatics = ParserUtils.trimNullSafe(txtAcrobatics);
			txtAppraise = ParserUtils.trimNullSafe(txtAppraise);
			txtBluff = ParserUtils.trimNullSafe(txtBluff);
			txtClimb = ParserUtils.trimNullSafe(txtClimb);
			txtCraft = ParserUtils.trimNullSafe(txtCraft);
			txtDiplomacy = ParserUtils.trimNullSafe(txtDiplomacy);
			txtDisableDevice = ParserUtils.trimNullSafe(txtDisableDevice);
			txtDisguise = ParserUtils.trimNullSafe(txtDisguise);
			txtEscapeArtist = ParserUtils.trimNullSafe(txtEscapeArtist);
			txtFly = ParserUtils.trimNullSafe(txtFly);
			txtHandleAnimal = ParserUtils.trimNullSafe(txtHandleAnimal);
			txtHeal = ParserUtils.trimNullSafe(txtHeal);
			txtIntimidate = ParserUtils.trimNullSafe(txtIntimidate);
			txtKnowAll = ParserUtils.trimNullSafe(txtKnowAll);
			txtKnowArcana = ParserUtils.trimNullSafe(txtKnowArcana);
			txtKnowDung = ParserUtils.trimNullSafe(txtKnowDung);
			txtKnowEng = ParserUtils.trimNullSafe(txtKnowEng);
			txtKnowGeo = ParserUtils.trimNullSafe(txtKnowGeo);
			txtKnowHist = ParserUtils.trimNullSafe(txtKnowHist);
			txtKnowLocal = ParserUtils.trimNullSafe(txtKnowLocal);
			txtKnowNat = ParserUtils.trimNullSafe(txtKnowNat);
			txtKnowNobl = ParserUtils.trimNullSafe(txtKnowNobl);
			txtKnowPlanes = ParserUtils.trimNullSafe(txtKnowPlanes);
			txtKnowRelig = ParserUtils.trimNullSafe(txtKnowRelig);
			txtLinguistics = ParserUtils.trimNullSafe(txtLinguistics);
			txtPerception = ParserUtils.trimNullSafe(txtPerception);
			txtPerform = ParserUtils.trimNullSafe(txtPerform);
			txtProfession = ParserUtils.trimNullSafe(txtProfession);
			txtRide = ParserUtils.trimNullSafe(txtRide);
			txtSenseMotive = ParserUtils.trimNullSafe(txtSenseMotive);
			txtSlightOfHand = ParserUtils.trimNullSafe(txtSlightOfHand);
			txtSpellcraft = ParserUtils.trimNullSafe(txtSpellcraft);
			txtStealth = ParserUtils.trimNullSafe(txtStealth);
			txtSurvival = ParserUtils.trimNullSafe(txtSurvival);
			txtSwim = ParserUtils.trimNullSafe(txtSwim);
			txtUmd = ParserUtils.trimNullSafe(txtUmd);
			txtNotes = ParserUtils.trimNullSafe(txtNotes);

			txtAcrobaticsNotes = ParserUtils.trimNullSafe(txtAcrobaticsNotes);
			txtAppraiseNotes = ParserUtils.trimNullSafe(txtAppraiseNotes);
			txtBluffNotes = ParserUtils.trimNullSafe(txtBluffNotes);
			txtClimbNotes = ParserUtils.trimNullSafe(txtClimbNotes);
			txtCraftNotes = ParserUtils.trimNullSafe(txtCraftNotes);
			txtDiplomacyNotes = ParserUtils.trimNullSafe(txtDiplomacyNotes);
			txtDisableDeviceNotes = ParserUtils.trimNullSafe(txtDisableDeviceNotes);
			txtDisguiseNotes = ParserUtils.trimNullSafe(txtDisguiseNotes);
			txtEscapeArtistNotes = ParserUtils.trimNullSafe(txtEscapeArtistNotes);
			txtFlyNotes = ParserUtils.trimNullSafe(txtFlyNotes);
			txtHandleAnimalNotes = ParserUtils.trimNullSafe(txtHandleAnimalNotes);
			txtHealNotes = ParserUtils.trimNullSafe(txtHealNotes);
			txtIntimidateNotes = ParserUtils.trimNullSafe(txtIntimidateNotes);
			txtKnowAllNotes = ParserUtils.trimNullSafe(txtKnowAllNotes);
			txtKnowArcanaNotes = ParserUtils.trimNullSafe(txtKnowArcanaNotes);
			txtKnowDungNotes = ParserUtils.trimNullSafe(txtKnowDungNotes);
			txtKnowEngNotes = ParserUtils.trimNullSafe(txtKnowEngNotes);
			txtKnowGeoNotes = ParserUtils.trimNullSafe(txtKnowGeoNotes);
			txtKnowHistNotes = ParserUtils.trimNullSafe(txtKnowHistNotes);
			txtKnowLocalNotes = ParserUtils.trimNullSafe(txtKnowLocalNotes);
			txtKnowNatNotes = ParserUtils.trimNullSafe(txtKnowNatNotes);
			txtKnowNoblNotes = ParserUtils.trimNullSafe(txtKnowNoblNotes);
			txtKnowPlanesNotes = ParserUtils.trimNullSafe(txtKnowPlanesNotes);
			txtKnowReligNotes = ParserUtils.trimNullSafe(txtKnowReligNotes);
			txtLinguisticsNotes = ParserUtils.trimNullSafe(txtLinguisticsNotes);
			txtPerceptionNotes = ParserUtils.trimNullSafe(txtPerceptionNotes);
			txtPerformNotes = ParserUtils.trimNullSafe(txtPerformNotes);
			txtProfessionNotes = ParserUtils.trimNullSafe(txtProfessionNotes);
			txtRideNotes = ParserUtils.trimNullSafe(txtRideNotes);
			txtSenseMotiveNotes = ParserUtils.trimNullSafe(txtSenseMotiveNotes);
			txtSlightOfHandNotes = ParserUtils.trimNullSafe(txtSlightOfHandNotes);
			txtSpellcraftNotes = ParserUtils.trimNullSafe(txtSpellcraftNotes);
			txtStealthNotes = ParserUtils.trimNullSafe(txtStealthNotes);
			txtSurvivalNotes = ParserUtils.trimNullSafe(txtSurvivalNotes);
			txtSwimNotes = ParserUtils.trimNullSafe(txtSwimNotes);
			txtUmdNotes = ParserUtils.trimNullSafe(txtUmdNotes);

			// set the creature from the txt values
			creature.skills.setAcrobaticsMod(parseInt(txtAcrobatics, "Acrobatics"));
			creature.skills.setAppraiseMod(parseInt(txtAppraise, "Appraise"));
			creature.skills.setBluffMod(parseInt(txtBluff, "Bluff"));
			creature.skills.setClimbMod(parseInt(txtClimb, "Climb"));
			creature.skills.setCraftMod(parseInt(txtCraft, "Craft"));
			creature.skills.setDiplomacyMod(parseInt(txtDiplomacy, "Diplomacy"));
			creature.skills.setDisableDeviceMod(parseInt(txtDisableDevice, "Disable Device"));
			creature.skills.setDisguiseMod(parseInt(txtDisguise, "Disguise"));
			creature.skills.setEscapeArtistMod(parseInt(txtEscapeArtist, "Escape Artist"));
			creature.skills.setFlyMod(parseInt(txtFly, "Fly"));
			creature.skills.setHandleAnimalMod(parseInt(txtHandleAnimal, "Handle Animal"));
			creature.skills.setHealMod(parseInt(txtHeal, "Heal"));
			creature.skills.setIntimidateMod(parseInt(txtIntimidate, "Intimidate"));
			creature.skills.setKnowAllMod(parseInt(txtKnowAll, "Knowledge all"));
			creature.skills.setKnowArcanaMod(parseInt(txtKnowArcana, "Knowledge arcana"));
			creature.skills.setKnowDungeoneeringMod(parseInt(txtKnowDung, "Knowledge dungeoneering"));
			creature.skills.setKnowEngineeringMod(parseInt(txtKnowEng, "Knowledge engineering"));
			creature.skills.setKnowGeographyMod(parseInt(txtKnowGeo, "Knowledge geography"));
			creature.skills.setKnowHistoryMod(parseInt(txtKnowHist, "Knowledge history"));
			creature.skills.setKnowLocalMod(parseInt(txtKnowLocal, "Knowledge local"));
			creature.skills.setKnowNatureMod(parseInt(txtKnowNat, "Knowledge nature"));
			creature.skills.setKnowNobilityMod(parseInt(txtKnowNobl, "Knowledge nobility"));
			creature.skills.setKnowPlanesMod(parseInt(txtKnowPlanes, "Knowledge planes"));
			creature.skills.setKnowReligionMod(parseInt(txtKnowRelig, "Knowledge religion"));
			creature.skills.setLinguisticsMod(parseInt(txtLinguistics, "Linguistics"));
			creature.skills.setPerceptionMod(parseInt(txtPerception, "Perception"));
			creature.skills.setPerformMod(parseInt(txtPerform, "Perform"));
			creature.skills.setProfessionMod(parseInt(txtProfession, "Profession"));
			creature.skills.setRideMod(parseInt(txtRide, "Ride"));
			creature.skills.setSenseMotiveMod(parseInt(txtSenseMotive, "Sense Motive"));
			creature.skills.setSleightOfHandMod(parseInt(txtSlightOfHand, "Sleight of Hand"));
			creature.skills.setSpellcraftMod(parseInt(txtSpellcraft, "Spellcraft"));
			creature.skills.setStealthMod(parseInt(txtStealth, "Stealth"));
			creature.skills.setSurvivalMod(parseInt(txtSurvival, "Survival"));
			creature.skills.setSwimMod(parseInt(txtSwim, "Swim"));
			creature.skills.setUseMagicDeviceMod(parseInt(txtUmd, "Use Magic Device"));
			creature.skills.setNotes(ParserUtils.trimNullSafe(txtNotes));

			creature.skills.setAcrobaticsNotes(ParserUtils.trimNullSafe(txtAcrobaticsNotes));
			creature.skills.setAppraiseNotes(ParserUtils.trimNullSafe(txtAppraiseNotes));
			creature.skills.setBluffNotes(ParserUtils.trimNullSafe(txtBluffNotes));
			creature.skills.setClimbNotes(ParserUtils.trimNullSafe(txtClimbNotes));
			creature.skills.setCraftNotes(ParserUtils.trimNullSafe(txtCraftNotes));
			creature.skills.setDiplomacyNotes(ParserUtils.trimNullSafe(txtDiplomacyNotes));
			creature.skills.setDisableDeviceNotes(ParserUtils.trimNullSafe(txtDisableDeviceNotes));
			creature.skills.setDisguiseNotes(ParserUtils.trimNullSafe(txtDisguiseNotes));
			creature.skills.setEscapeArtistNotes(ParserUtils.trimNullSafe(txtEscapeArtistNotes));
			creature.skills.setFlyNotes(ParserUtils.trimNullSafe(txtFlyNotes));
			creature.skills.setHandleAnimalNotes(ParserUtils.trimNullSafe(txtHandleAnimalNotes));
			creature.skills.setHealNotes(ParserUtils.trimNullSafe(txtHealNotes));
			creature.skills.setIntimidateNotes(ParserUtils.trimNullSafe(txtIntimidateNotes));
			creature.skills.setKnowAllNotes(ParserUtils.trimNullSafe(txtKnowAllNotes));
			creature.skills.setKnowArcanaNotes(ParserUtils.trimNullSafe(txtKnowArcanaNotes));
			creature.skills.setKnowDungeoneeringNotes(ParserUtils.trimNullSafe(txtKnowDungNotes));
			creature.skills.setKnowEngineeringNotes(ParserUtils.trimNullSafe(txtKnowEngNotes));
			creature.skills.setKnowGeographyNotes(ParserUtils.trimNullSafe(txtKnowGeoNotes));
			creature.skills.setKnowHistoryNotes(ParserUtils.trimNullSafe(txtKnowHistNotes));
			creature.skills.setKnowLocalNotes(ParserUtils.trimNullSafe(txtKnowLocalNotes));
			creature.skills.setKnowNatureNotes(ParserUtils.trimNullSafe(txtKnowNatNotes));
			creature.skills.setKnowNobilityNotes(ParserUtils.trimNullSafe(txtKnowNoblNotes));
			creature.skills.setKnowPlanesNotes(ParserUtils.trimNullSafe(txtKnowPlanesNotes));
			creature.skills.setKnowReligionNotes(ParserUtils.trimNullSafe(txtKnowReligNotes));
			creature.skills.setLinguisticsNotes(ParserUtils.trimNullSafe(txtLinguisticsNotes));
			creature.skills.setPerceptionNotes(ParserUtils.trimNullSafe(txtPerceptionNotes));
			creature.skills.setPerformNotes(ParserUtils.trimNullSafe(txtPerformNotes));
			creature.skills.setProfessionNotes(ParserUtils.trimNullSafe(txtProfessionNotes));
			creature.skills.setRideNotes(ParserUtils.trimNullSafe(txtRideNotes));
			creature.skills.setSenseMotiveNotes(ParserUtils.trimNullSafe(txtSenseMotiveNotes));
			creature.skills.setSleightOfHandNotes(ParserUtils.trimNullSafe(txtSlightOfHandNotes));
			creature.skills.setSpellcraftNotes(ParserUtils.trimNullSafe(txtSpellcraftNotes));
			creature.skills.setStealthNotes(ParserUtils.trimNullSafe(txtStealthNotes));
			creature.skills.setSurvivalNotes(ParserUtils.trimNullSafe(txtSurvivalNotes));
			creature.skills.setSwimNotes(ParserUtils.trimNullSafe(txtSwimNotes));
			creature.skills.setUseMagicDeviceNotes(ParserUtils.trimNullSafe(txtUmdNotes));
			
		}
		else
		{
			String stError = String.format("parseSkills invalid format '%s'", text);
			log.error(stError);
//			throw new ParseException(stError, -1);
		}

		log.trace("*********Skills Print********\r\n" +creature.skills.getSkillsPrint());
		log.debug(creature.skills.toString());
	}

	protected static void parseBabCmbCmd(Creature creature, String text)
	{
		log.trace("TextParser.parseBabCmbCmd got line of '{}'", text);

		// massage the data to fit our parser better.
		text = ParserUtils.sanitize(text);
		// parse the values
		Matcher matcher = PATTERN_BAB_CMB_CMD.matcher(text);
		if (matcher.find())
		{
			String txtBab = matcher.group("bab");
			String txtCmb = matcher.group("cmb");
			String txtCmbNotes = matcher.group("cmbNotes");
			String txtCmd = matcher.group("cmd");
			String txtCmdNotes = matcher.group("cmdNotes");

			txtBab = ParserUtils.trimNullSafe(txtBab);
			txtCmb = ParserUtils.trimNullSafe(txtCmb);
			txtCmbNotes = ParserUtils.trimNullSafe(txtCmbNotes);
			txtCmd = ParserUtils.trimNullSafe(txtCmd);
			txtCmdNotes = ParserUtils.trimNullSafe(txtCmdNotes);

			// set the creature from the txt values
			creature.stats.setBab(parseInt(txtBab, "Bab"));
			if (StringUtils.isNotBlank(txtCmb) && ! txtCmb.equals("-")) //swarms have CMB -, CMD -
				creature.stats.setCmb(parseInt(txtCmb, "Cmb"));
			creature.stats.setCmbNotes(txtCmbNotes);
			if (StringUtils.isNotBlank(txtCmd) && ! txtCmd.equals("-")) //swarms have CMB -, CMD -
				creature.stats.setCmd(parseInt(txtCmd, "Cmd"));
			creature.stats.setCmdNotes(txtCmdNotes);
		}
		else
		{
			String stError = String.format("parseBabCmbCmd invalid format '%s'", text);
			log.error(stError);
//			throw new ParseException(stError, -1);
		}
		log.debug(creature.stats.toString());
	}

	protected static void parseDefensiveAbilities(Creature creature, String text)
	{
		log.trace("TextParser.parseDefensiveAbilities got line of '{}'", text);
		// massage the data to fit our parser better.
		text = ParserUtils.sanitize(text);

		// parse the values
		Matcher matcher = PATTERN_DEFENSE_DR_SR_IMMUNE_RESIST_WEAK.matcher(text);
		if (matcher.find())
		{
			String txtDefAbilities = matcher.group("da");
			String txtDr = matcher.group("dr");
			String txtImmune = matcher.group("imm");
			String txtResist = matcher.group("res");
			String txtSr = matcher.group("sr");
			String txtWeak = matcher.group("weak");
			
			txtDefAbilities = ParserUtils.trimNullSafe(txtDefAbilities);
			txtDr = ParserUtils.trimNullSafe(txtDr);
			txtImmune = ParserUtils.trimNullSafe(txtImmune);
			txtResist = ParserUtils.trimNullSafe(txtResist);
			txtSr = ParserUtils.trimNullSafe(txtSr);
			txtWeak = ParserUtils.trimNullSafe(txtWeak);
			
			// set the creature from the txt values
			if (StringUtils.isNotBlank(txtDefAbilities))
				creature.defenses.setDefensiveAbilities(txtDefAbilities);
			if (StringUtils.isNotBlank(txtDr))
				creature.defenses.setDamageReduction(txtDr);
			if (StringUtils.isNotBlank(txtImmune))
				creature.defenses.setImmune(txtImmune);
			if (StringUtils.isNotBlank(txtResist))
				creature.defenses.setResist(txtResist);
			if (StringUtils.isNotBlank(txtSr))
				creature.defenses.setSpellResist(parseInt(txtSr, "Spell Resist"));
			if (StringUtils.isNotBlank(txtWeak))
				creature.defenses.setWeakness(txtWeak);

		}
		else
		{
			String stError = String.format("parseDefensiveAbilities invalid format '%s'", text);
			log.error(stError);
//			throw new ParseException(stError, -1);
		}
		log.debug(creature.defenses.toString());
	}
	
	protected static void parseWeaknesses(Creature creature, String text)
	{
		log.trace("TextParser.parseWeaknesses got line of '{}'", text);
		// massage the data to fit our parser better.
		text = ParserUtils.sanitize(text);

		// parse the values
		Matcher matcher = PATTERN_DEFENSE_DR_SR_IMMUNE_RESIST_WEAK.matcher(text);
		if (matcher.find())
		{
			String txtWeak = matcher.group("weak");
			
			txtWeak = ParserUtils.trimNullSafe(txtWeak);
			// set the creature from the txt values
			creature.defenses.setWeakness(txtWeak);
		}
		else
		{
			String stError = String.format("parseWeaknesses invalid format '%s'", text);
			log.error(stError);
//			throw new ParseException(stError, -1);
		}
		log.debug(creature.defenses.toString());
	}
	
	protected static void parseAlignSizeType(Creature creature, String text)
	{
		log.trace("TextParser.parseAlignSizeType got line of '{}'", text);
		// massage the data to fit our parser better.
		text = ParserUtils.sanitize(text);
		text = ParserUtils.capitalizeWord(text);
		
		// parse the values
		Matcher matcher = PATTERN_ALIGN_SIZE_TYPE.matcher(text);
		if (matcher.find())
		{
			String txtAlign = matcher.group("align");
			String txtSize = matcher.group("size");
			String txtType = matcher.group("type");
			
			txtAlign = ParserUtils.trimNullSafe(txtAlign);
			txtSize = ParserUtils.trimNullSafe(txtSize);
			txtType = ParserUtils.trimNullSafe(txtType);
			
			// set the creature from the txt values
			creature.setAlignment(txtAlign);
			creature.setSize(txtSize);
			creature.setType(txtType);
		}
		else
		{
			String stError = String.format("parseAlignSizeType invalid format '%s'", text);
			log.error(stError);
//			throw new ParseException(stError, -1);
		}
		log.debug(creature.toString());
	}
	
	protected static void parseXp(Creature creature, String text)
	{
		log.trace("TextParser.parseXp got line of '{}'", text);
		// massage the data to fit our parser better.
		text = ParserUtils.sanitize(text);
		
		// parse the values
		Matcher matcher = PATTERN_XP.matcher(text);
		if (matcher.find())
		{
			String txtXp= matcher.group("xp");
			
			txtXp = ParserUtils.trimNullSafe(txtXp);
			txtXp = txtXp.replaceAll(",", "");
			// set the creature from the txt values
			creature.setXp(parseInt(txtXp, "XP"));
		}
		else
		{
			String stError = String.format("parseXp invalid format '%s'", text);
			log.error(stError);
		}
		log.debug(creature.toString());
	}
	
	protected static void parseSpeed(Creature creature, String text)
	{
		log.trace("TextParser.parseSpeed got line of '{}'", text);
		// massage the data to fit our parser better.
		text = ParserUtils.sanitize(text);
		
		// parse the values
		Matcher matcher = PATTERN_SPEED.matcher(text);
		if (matcher.find())
		{
			String txtLand = matcher.group("land");
			String txtBurrow = matcher.group("burrow");
			String txtClimb = matcher.group("climb");
			String txtFly = matcher.group("fly");
			String txtFlyNotes = matcher.group("flyNotes");
			String txtSwim = matcher.group("swim");
			String txtNotes = matcher.group("notes");
			
			txtLand = ParserUtils.trimNullSafe(txtLand);
			txtBurrow = ParserUtils.trimNullSafe(txtBurrow);
			txtClimb = ParserUtils.trimNullSafe(txtClimb);
			txtFly = ParserUtils.trimNullSafe(txtFly);
			txtFlyNotes = ParserUtils.trimNullSafe(txtFlyNotes);
			txtSwim = ParserUtils.trimNullSafe(txtSwim);
			txtNotes = ParserUtils.trimNullSafe(txtNotes);
			
			// set the creature from the txt values
			creature.offenses.setSpeedLand(parseInt(txtLand, "Speed"));
			creature.offenses.setSpeedBurrow(parseInt(txtBurrow, "Burrow"));
			creature.offenses.setSpeedClimb(parseInt(txtClimb, "Climb"));
			creature.offenses.setSpeedFly(parseInt(txtFly, "Fly"));
			creature.offenses.setSpeedFlyNotes(txtFlyNotes);
			creature.offenses.setSpeedSwim(parseInt(txtSwim, "Swim"));
			creature.offenses.setSpeedNotes(txtNotes);
		}
		else
		{
			String stError = String.format("parseSpeed invalid format '%s'", text);
			log.error(stError);
		}
		log.debug(creature.toString());
	}
	
	protected static void parseSpaceReach(Creature creature, String text)
	{
		log.trace("TextParser.parseSpaceReach got line of '{}'", text);
		// massage the data to fit our parser better.
		text = ParserUtils.sanitize(text);
		
		// parse the values
		Matcher matcher = PATTERN_SPACE_REACH.matcher(text);
		if (matcher.find())
		{
			String txtSpace = matcher.group("space");
			String txtReach = matcher.group("reach");
			String txtReachNotes = matcher.group("notes");
			
			txtSpace = ParserUtils.trimNullSafe(txtSpace);
			txtReach = ParserUtils.trimNullSafe(txtReach);
			txtReachNotes = ParserUtils.trimNullSafe(txtReachNotes);
			
			// set the creature from the txt values
			creature.offenses.setSpace(parseInt(txtSpace, "Speed"));
			creature.offenses.setReach(parseInt(txtReach, "Burrow"));
			creature.offenses.setReachNotes(txtReachNotes);
		}
		else
		{
			String stError = String.format("parseSpaceReach invalid format '%s'", text);
			log.error(stError);
		}
		log.debug(creature.toString());
	}
		
	protected static void parseMeleeRangedSpecialAttacks(Creature creature, String text)
	{
		log.trace("TextParser.parseMeleeRangedSpecialAttacks got line of '{}'", text);
		// massage the data to fit our parser better.
		text = ParserUtils.sanitize(text);
		
		//fix parsing issues
		text = text.replaceAll("/x3", "x3");
		text = text.replaceAll("/x4", "x4");
		text = text.replaceAll("- 20", "-20");
		
		//+3 starknife +27/+22/+17/+12 (1d6+10/19-20x3), the +3 would throw off the community sheet.
		if (text.startsWith("Melee +"))
			text = "Melee " +text.substring(text.indexOf(" ", 7), text.length());
		//Huge +4 unholy halberd of speed +48/+48/+43/+38/+33 (3d8+25/19-20x3), bite +38 (1d8+7) or gore +43 (1d8+21), the +4 would throw off the community sheet.
		if (text.startsWith("Melee Huge +"))
			text = "Melee Huge " +text.substring(text.indexOf(" ", 12), text.length()).trim();
		
		// parse the values
		Matcher matcher = PATTERN_MELEE_RANGED_SPECIAL_ATTACKS.matcher(text);
		if (matcher.find())
		{
			String txtMelee = matcher.group("melee");
			String txtRanged = matcher.group("range");
			String txtSpecialAttacks = matcher.group("spclatk");
			
			txtMelee = ParserUtils.trimNullSafe(txtMelee);
			txtRanged = ParserUtils.trimNullSafe(txtRanged);
			txtSpecialAttacks = ParserUtils.trimNullSafe(txtSpecialAttacks);
			
			// set the creature from the txt values
			if (StringUtils.isNotBlank(txtMelee))
				creature.offenses.setMelee(txtMelee);
			if (StringUtils.isNotBlank(txtRanged))
				creature.offenses.setRange(txtRanged);
			if (StringUtils.isNotBlank(txtSpecialAttacks))
				creature.offenses.setSpecialAttacks(txtSpecialAttacks);
		}
		else
		{
			String stError = String.format("parseMeleeRangedSpecialAttacks invalid format '%s'", text);
			log.error(stError);
		}
		log.debug(creature.toString());
	}
	
	protected static void parseAura(Creature creature, String text)
	{
		log.trace("TextParser.parseAura got line of '{}'", text);
		// massage the data to fit our parser better.
		text = ParserUtils.sanitize(text);
		
		// parse the values
		Matcher matcher = PATTERN_AURA.matcher(text);
		if (matcher.find())
		{
			String txtAura = matcher.group("aura");
			
			txtAura = ParserUtils.trimNullSafe(txtAura);
			
			// set the creature from the txt values			
			creature.setAura(txtAura);
		}
		else
		{
			String stError = String.format("parseAura invalid format '%s'", text);
			log.error(stError);
		}
		log.debug(creature.toString());
	}
	
	protected static void parseFeats(Creature creature, String text)
	{
		log.trace("TextParser.parseFeats got line of '{}'", text);
		// massage the data to fit our parser better.
		text = ParserUtils.sanitize(text);
		
		// parse the values
		Matcher matcher = PATTERN_FEATS.matcher(text);
		if (matcher.find())
		{
			String txtFeats = matcher.group("feats");
			
			txtFeats = ParserUtils.trimNullSafe(txtFeats);
			
			// set the creature from the txt values			
			creature.setFeats(txtFeats);
		}
		else
		{
			String stError = String.format("parseFeats invalid format '%s'", text);
			log.error(stError);
		}
		log.debug(creature.toString());
	}
	
	protected static void parseSpecialQualities(Creature creature, String text)
	{
		log.trace("TextParser.parseSpecialQualities got line of '{}'", text);
		// massage the data to fit our parser better.
		text = ParserUtils.sanitize(text);
		
		// parse the values
		Matcher matcher = PATTERN_SQ.matcher(text);
		if (matcher.find())
		{
			String txtSq= matcher.group("sq");
			
			txtSq = ParserUtils.trimNullSafe(txtSq);
			
			// set the creature from the txt values			
			creature.setSpecialQualities(txtSq);
		}
		else
		{
			String stError = String.format("parseSpecialQualities invalid format '%s'", text);
			log.error(stError);
		}
		log.debug(creature.toString());
	}
	
	protected static void parseSpellLikePrepKnownSpecialAbilitiesPsychic(Creature creature, String text)
	{
		log.trace("TextParser.parseSpellLikePrepKnown got line of '{}'", text);
		// massage the data to fit our parser better.
		text = ParserUtils.sanitize(text);
		
		if (text.startsWith("Spell-Like Abilities "))
		{
			creature.setSpellLike(text.substring(text.indexOf("(")));
		}
		else if (text.contains("Spells Prepared "))
		{
			creature.setSpellsPrepared(text);
		}
		else if (text.contains("Spells Known "))
		{
			creature.setSpellsKnown(text);
		}
		else if (text.startsWith("SPECIAL ABILITIES"))
		{
			creature.setSpecialAbilities(text.substring("SPECIAL ABILITIES ".length()).trim());
		}
		else if (text.startsWith("Psychic Magic "))
		{
			creature.setSpellLike(text);
		}
		else if (text.contains("Kineticist Wild Talents Known"))
		{
			creature.setSpellsKnown(text);
		}
		
		log.debug(creature.toString());
	}
	
	
}

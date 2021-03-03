package org.pf1etools.creature;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.pf1etools.creature.utils.Skill;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Skills
{
	// int i=1;
	// used to generate java code due to numerous skills
	// static
	// {
	// Arrays.asList(Skill.values()).forEach(skill -> {
	// System.out.println("Integer " +skill.getParseName().toLowerCase().replace(" ", "")+"Mod;");
	// System.out.println(String.format("creature.skills.set%sMod(parseInt(txt%s, \"%s\"));", skill.getParseName(),
	// skill.getParseName(), skill.getParseName()));
	// System.out.print(" " +skill.getParseName() +" +"+i++ +",");
	// });
	// }

	Integer acrobaticsMod;
	Integer appraiseMod;
	Integer bluffMod;
	Integer climbMod;
	Integer craftMod;
	Integer diplomacyMod;
	Integer disableDeviceMod;
	Integer disguiseMod;
	Integer escapeArtistMod;
	Integer flyMod;
	Integer handleAnimalMod;
	Integer healMod;
	Integer intimidateMod;
	Integer knowAllMod;
	Integer knowArcanaMod;
	Integer knowDungeoneeringMod;
	Integer knowEngineeringMod;
	Integer knowGeographyMod;
	Integer knowHistoryMod;
	Integer knowLocalMod;
	Integer knowNatureMod;
	Integer knowNobilityMod;
	Integer knowPlanesMod;
	Integer knowReligionMod;
	Integer linguisticsMod;
	Integer perceptionMod;
	Integer performMod;
	Integer professionMod;
	Integer rideMod;
	Integer senseMotiveMod;
	Integer sleightOfHandMod;
	Integer spellcraftMod;
	Integer stealthMod;
	Integer survivalMod;
	Integer swimMod;
	Integer useMagicDeviceMod;

	String acrobaticsNotes;
	String appraiseNotes;
	String bluffNotes;
	String climbNotes;
	String craftNotes;
	String diplomacyNotes;
	String disableDeviceNotes;
	String disguiseNotes;
	String escapeArtistNotes;
	String flyNotes;
	String handleAnimalNotes;
	String healNotes;
	String intimidateNotes;
	String knowAllNotes;
	String knowArcanaNotes;
	String knowDungeoneeringNotes;
	String knowEngineeringNotes;
	String knowGeographyNotes;
	String knowHistoryNotes;
	String knowLocalNotes;
	String knowNatureNotes;
	String knowNobilityNotes;
	String knowPlanesNotes;
	String knowReligionNotes;
	String linguisticsNotes;
	String perceptionNotes;
	String performNotes;
	String professionNotes;
	String rideNotes;
	String senseMotiveNotes;
	String sleightOfHandNotes;
	String spellcraftNotes;
	String stealthNotes;
	String survivalNotes;
	String swimNotes;
	String useMagicDeviceNotes;

	String notes;

	public String getSkillsPrint()
	{
		StringBuilder sbSkills = new StringBuilder();
		if (acrobaticsMod!=null)
		{
			sbSkills.append("Acrobatics ");
			if (this.acrobaticsMod>=0)
				sbSkills.append("+").append(acrobaticsMod);
			else
				sbSkills.append(acrobaticsMod);
			if (StringUtils.isNotBlank(acrobaticsNotes))
				sbSkills.append(" (").append(acrobaticsNotes).append(")");
			sbSkills.append(", ");
		}
		if (appraiseMod!=null)
		{
			sbSkills.append("Appraise ");
			if (this.appraiseMod>=0)
				sbSkills.append("+").append(appraiseMod);
			else
				sbSkills.append(appraiseMod);
			if (StringUtils.isNotBlank(appraiseNotes))
				sbSkills.append(" (").append(appraiseNotes).append(")");
			sbSkills.append(", ");
		}
		if (bluffMod!=null)
		{
			sbSkills.append("Bluff ");
			if (this.bluffMod>=0)
				sbSkills.append("+").append(bluffMod);
			else
				sbSkills.append(bluffMod);
			if (StringUtils.isNotBlank(bluffNotes))
				sbSkills.append(" (").append(bluffNotes).append(")");
			sbSkills.append(", ");
		}
		if (climbMod!=null)
		{
			sbSkills.append("Climb ");
			if (this.climbMod>=0)
				sbSkills.append("+").append(climbMod);
			else
				sbSkills.append(climbMod);
			if (StringUtils.isNotBlank(climbNotes))
				sbSkills.append(" (").append(climbNotes).append(")");
			sbSkills.append(", ");
		}
		if (craftMod!=null)
		{
			sbSkills.append("Craft ");
			if (this.craftMod>=0)
				sbSkills.append("+").append(craftMod);
			else
				sbSkills.append(craftMod);
			if (StringUtils.isNotBlank(craftNotes))
				sbSkills.append(" (").append(craftNotes).append(")");
			sbSkills.append(", ");
		}
		if (diplomacyMod!=null)
		{
			sbSkills.append("Diplomacy ");
			if (this.diplomacyMod>=0)
				sbSkills.append("+").append(diplomacyMod);
			else
				sbSkills.append(diplomacyMod);
			if (StringUtils.isNotBlank(diplomacyNotes))
				sbSkills.append(" (").append(diplomacyNotes).append(")");
			sbSkills.append(", ");
		}
		if (disableDeviceMod!=null)
		{
			sbSkills.append("Disable Device ");
			if (this.disableDeviceMod>=0)
				sbSkills.append("+").append(disableDeviceMod);
			else
				sbSkills.append(disableDeviceMod);
			if (StringUtils.isNotBlank(disableDeviceNotes))
				sbSkills.append(" (").append(disableDeviceNotes).append(")");
			sbSkills.append(", ");
		}
		if (disguiseMod!=null)
		{
			sbSkills.append("Disguise ");
			if (this.disguiseMod>=0)
				sbSkills.append("+").append(disguiseMod);
			else
				sbSkills.append(disguiseMod);
			if (StringUtils.isNotBlank(disguiseNotes))
				sbSkills.append(" (").append(disguiseNotes).append(")");
			sbSkills.append(", ");
		}
		if (escapeArtistMod!=null)
		{
			sbSkills.append("Escape Artist ");
			if (this.escapeArtistMod>=0)
				sbSkills.append("+").append(escapeArtistMod);
			else
				sbSkills.append(escapeArtistMod);
			if (StringUtils.isNotBlank(escapeArtistNotes))
				sbSkills.append(" (").append(escapeArtistNotes).append(")");
			sbSkills.append(", ");
		}
		if (flyMod!=null)
		{
			sbSkills.append("Fly ");
			if (this.flyMod>=0)
				sbSkills.append("+").append(flyMod);
			else
				sbSkills.append(flyMod);
			if (StringUtils.isNotBlank(flyNotes))
				sbSkills.append(" (").append(flyNotes).append(")");
			sbSkills.append(", ");
		}
		if (handleAnimalMod!=null)
		{
			sbSkills.append("Handle Animal ");
			if (this.handleAnimalMod>=0)
				sbSkills.append("+").append(handleAnimalMod);
			else
				sbSkills.append(handleAnimalMod);
			if (StringUtils.isNotBlank(handleAnimalNotes))
				sbSkills.append(" (").append(handleAnimalNotes).append(")");
			sbSkills.append(", ");
		}
		if (healMod!=null)
		{
			sbSkills.append("Heal ");
			if (this.healMod>=0)
				sbSkills.append("+").append(healMod);
			else
				sbSkills.append(healMod);
			if (StringUtils.isNotBlank(healNotes))
				sbSkills.append(" (").append(healNotes).append(")");
			sbSkills.append(", ");
		}
		if (intimidateMod!=null)
		{
			sbSkills.append("Intimidate ");
			if (this.intimidateMod>=0)
				sbSkills.append("+").append(intimidateMod);
			else
				sbSkills.append(intimidateMod);
			if (StringUtils.isNotBlank(intimidateNotes))
				sbSkills.append(" (").append(intimidateNotes).append(")");
			sbSkills.append(", ");
		}		
		if (knowArcanaMod!=null || knowAllMod != null)
		{
			sbSkills.append("Knowledge (arcana) ");
			if (this.knowAllMod != null)
				sbSkills.append("+").append(knowAllMod);
			else if (this.knowArcanaMod>=0)
				sbSkills.append("+").append(knowArcanaMod);
			else
				sbSkills.append(knowArcanaMod);
			if (StringUtils.isNotBlank(knowArcanaNotes))
				sbSkills.append(" (").append(knowArcanaNotes).append(")");
			sbSkills.append(", ");
		}
		if (knowDungeoneeringMod!=null || knowAllMod != null)
		{
			sbSkills.append("Knowledge (dungeoneering) ");
			if (this.knowAllMod != null)
				sbSkills.append("+").append(knowAllMod);
			else if (this.knowDungeoneeringMod>=0)
				sbSkills.append("+").append(knowDungeoneeringMod);
			else
				sbSkills.append(knowDungeoneeringMod);
			if (StringUtils.isNotBlank(knowDungeoneeringNotes))
				sbSkills.append(" (").append(knowDungeoneeringNotes).append(")");
			sbSkills.append(", ");
		}
		if (knowEngineeringMod!=null || knowAllMod != null)
		{
			sbSkills.append("Knowledge (engineering) ");
			if (this.knowAllMod != null)
				sbSkills.append("+").append(knowAllMod);
			else if (this.knowEngineeringMod>=0)
				sbSkills.append("+").append(knowEngineeringMod);
			else
				sbSkills.append(knowEngineeringMod);
			if (StringUtils.isNotBlank(knowEngineeringNotes))
				sbSkills.append(" (").append(knowEngineeringNotes).append(")");
			sbSkills.append(", ");
		}
		if (knowGeographyMod!=null || knowAllMod != null)
		{
			sbSkills.append("Knowledge (geography) ");
			if (this.knowAllMod != null)
				sbSkills.append("+").append(knowAllMod);
			else if (this.knowGeographyMod>=0)
				sbSkills.append("+").append(knowGeographyMod);
			else
				sbSkills.append(knowGeographyMod);
			if (StringUtils.isNotBlank(knowGeographyNotes))
				sbSkills.append(" (").append(knowGeographyNotes).append(")");
			sbSkills.append(", ");
		}
		if (knowHistoryMod!=null || knowAllMod != null)
		{
			sbSkills.append("Knowledge (history) ");
			if (this.knowAllMod != null)
				sbSkills.append("+").append(knowAllMod);
			else if (this.knowHistoryMod>=0)
				sbSkills.append("+").append(knowHistoryMod);
			else
				sbSkills.append(knowHistoryMod);
			if (StringUtils.isNotBlank(knowHistoryNotes))
				sbSkills.append(" (").append(knowHistoryNotes).append(")");
			sbSkills.append(", ");
		}
		if (knowLocalMod!=null || knowAllMod != null)
		{
			sbSkills.append("Knowledge (local) ");
			if (this.knowAllMod != null)
				sbSkills.append("+").append(knowAllMod);
			else if (this.knowLocalMod>=0)
				sbSkills.append("+").append(knowLocalMod);
			else
				sbSkills.append(knowLocalMod);
			if (StringUtils.isNotBlank(knowLocalNotes))
				sbSkills.append(" (").append(knowLocalNotes).append(")");
			sbSkills.append(", ");
		}
		if (knowNatureMod!=null || knowAllMod != null)
		{
			sbSkills.append("Knowledge (nature) ");
			if (this.knowAllMod != null)
				sbSkills.append("+").append(knowAllMod);
			else if (this.knowNatureMod>=0)
				sbSkills.append("+").append(knowNatureMod);
			else
				sbSkills.append(knowNatureMod);
			if (StringUtils.isNotBlank(knowNatureNotes))
				sbSkills.append(" (").append(knowNatureNotes).append(")");
			sbSkills.append(", ");
		}
		if (knowNobilityMod!=null || knowAllMod != null)
		{
			sbSkills.append("Knowledge (nobility) ");
			if (this.knowAllMod != null)
				sbSkills.append("+").append(knowAllMod);
			else if (this.knowNobilityMod>=0)
				sbSkills.append("+").append(knowNobilityMod);
			else
				sbSkills.append(knowNobilityMod);
			if (StringUtils.isNotBlank(knowNobilityNotes))
				sbSkills.append(" (").append(knowNobilityNotes).append(")");
			sbSkills.append(", ");
		}
		if (knowPlanesMod!=null || knowAllMod != null)
		{
			sbSkills.append("Knowledge (planes) ");
			if (this.knowAllMod != null)
				sbSkills.append("+").append(knowAllMod);
			else if (this.knowPlanesMod>=0)
				sbSkills.append("+").append(knowPlanesMod);
			else
				sbSkills.append(knowPlanesMod);
			if (StringUtils.isNotBlank(knowPlanesNotes))
				sbSkills.append(" (").append(knowPlanesNotes).append(")");
			sbSkills.append(", ");
		}
		if (knowReligionMod!=null || knowAllMod != null)
		{
			sbSkills.append("Knowledge (religion) ");
			if (this.knowAllMod != null)
				sbSkills.append("+").append(knowAllMod);
			else if (this.knowReligionMod>=0)
				sbSkills.append("+").append(knowReligionMod);
			else
				sbSkills.append(knowReligionMod);
			if (StringUtils.isNotBlank(knowReligionNotes))
				sbSkills.append(" (").append(knowReligionNotes).append(")");
			sbSkills.append(", ");
		}
		if (linguisticsMod!=null)
		{
			sbSkills.append("Linguistics ");
			if (this.linguisticsMod>=0)
				sbSkills.append("+").append(linguisticsMod);
			else
				sbSkills.append(linguisticsMod);
			if (StringUtils.isNotBlank(linguisticsNotes))
				sbSkills.append(" (").append(linguisticsNotes).append(")");
			sbSkills.append(", ");
		}
		if (perceptionMod!=null)
		{
			sbSkills.append("Perception ");
			if (this.perceptionMod>=0)
				sbSkills.append("+").append(perceptionMod);
			else
				sbSkills.append(perceptionMod);
			if (StringUtils.isNotBlank(perceptionNotes))
				sbSkills.append(" (").append(perceptionNotes).append(")");
			sbSkills.append(", ");
		}
		if (performMod!=null)
		{
			sbSkills.append("Perform ");
			if (this.performMod>=0)
				sbSkills.append("+").append(performMod);
			else
				sbSkills.append(performMod);
			if (StringUtils.isNotBlank(performNotes))
				sbSkills.append(" (").append(performNotes).append(")");
			sbSkills.append(", ");
		}
		if (professionMod!=null)
		{
			sbSkills.append("Profession ");
			if (this.professionMod>=0)
				sbSkills.append("+").append(professionMod);
			else
				sbSkills.append(professionMod);
			if (StringUtils.isNotBlank(professionNotes))
				sbSkills.append(" (").append(professionNotes).append(")");
			sbSkills.append(", ");
		}
		if (rideMod!=null)
		{
			sbSkills.append("Ride ");
			if (this.rideMod>=0)
				sbSkills.append("+").append(rideMod);
			else
				sbSkills.append(rideMod);
			if (StringUtils.isNotBlank(rideNotes))
				sbSkills.append(" (").append(rideNotes).append(")");
			sbSkills.append(", ");
		}
		if (senseMotiveMod!=null)
		{
			sbSkills.append("Sense Motive ");
			if (this.senseMotiveMod>=0)
				sbSkills.append("+").append(senseMotiveMod);
			else
				sbSkills.append(senseMotiveMod);
			if (StringUtils.isNotBlank(senseMotiveNotes))
				sbSkills.append(" (").append(senseMotiveNotes).append(")");
			sbSkills.append(", ");
		}
		if (sleightOfHandMod!=null)
		{
			sbSkills.append("Sleight of Hand ");
			if (this.sleightOfHandMod>=0)
				sbSkills.append("+").append(sleightOfHandMod);
			else
				sbSkills.append(sleightOfHandMod);
			if (StringUtils.isNotBlank(sleightOfHandNotes))
				sbSkills.append(" (").append(sleightOfHandNotes).append(")");
			sbSkills.append(", ");
		}
		if (spellcraftMod!=null)
		{
			sbSkills.append("Spellcraft ");
			if (this.spellcraftMod>=0)
				sbSkills.append("+").append(spellcraftMod);
			else
				sbSkills.append(spellcraftMod);
			if (StringUtils.isNotBlank(spellcraftNotes))
				sbSkills.append(" (").append(spellcraftNotes).append(")");
			sbSkills.append(", ");
		}
		if (stealthMod!=null)
		{
			sbSkills.append("Stealth ");
			if (this.stealthMod>=0)
				sbSkills.append("+").append(stealthMod);
			else
				sbSkills.append(stealthMod);
			if (StringUtils.isNotBlank(stealthNotes))
				sbSkills.append(" (").append(stealthNotes).append(")");
			sbSkills.append(", ");
		}
		if (survivalMod!=null)
		{
			sbSkills.append("Survival ");
			if (this.survivalMod>=0)
				sbSkills.append("+").append(survivalMod);
			else
				sbSkills.append(survivalMod);
			if (StringUtils.isNotBlank(survivalNotes))
				sbSkills.append(" (").append(survivalNotes).append(")");
			sbSkills.append(", ");
		}
		if (swimMod!=null)
		{
			sbSkills.append("Swim ");
			if (this.swimMod>=0)
				sbSkills.append("+").append(swimMod);
			else
				sbSkills.append(swimMod);
			if (StringUtils.isNotBlank(swimNotes))
				sbSkills.append(" (").append(swimNotes).append(")");
			sbSkills.append(", ");
		}
		if (useMagicDeviceMod!=null)
		{
			sbSkills.append("Use Magic Device ");
			if (this.useMagicDeviceMod>=0)
				sbSkills.append("+").append(useMagicDeviceMod);
			else
				sbSkills.append(useMagicDeviceMod);
			if (StringUtils.isNotBlank(useMagicDeviceNotes))
				sbSkills.append(" (").append(useMagicDeviceNotes).append(")");
			sbSkills.append(", ");
		}
		if (sbSkills != null && sbSkills.length() > 0)
			return sbSkills.toString().substring(0, sbSkills.toString().length()-2);
		else
			return null;
	}

	private String printCode()
	{
		StringBuilder sbSkills = new StringBuilder();
		List<Skill> skills = Arrays.asList(Skill.values());
		for (Skill skill : skills)
		{
			String javaModName = skill.getJavaPrefixName()+"Mod";
			String javaNoteName = skill.getJavaPrefixName()+"Notes";
			String printName = skill.getParseName();

			String code = String.format("if (%s != null)\r\n"+"		{\r\n"+"			sbSkills.append(\"%s \" );\r\n"+"			if (this.%s >=0)\r\n"+"				sbSkills.append(\"+\").append(%s);\r\n"+"			else\r\n"+"				sbSkills.append(%s);\r\n"+"			if (StringUtils.isNotBlank(%s))\r\n"+"				sbSkills.append(\" (\").append(%s).append(\")\");\r\n"+"			sbSkills.append(\", \");\r\n"+"		}", javaModName, printName, javaModName, javaModName, javaModName, javaNoteName, javaNoteName);
			System.out.println(code);
		}

		// if (acrobaticsMod != null)
		// {
		// sbSkills.append("Acrobatics " );
		// if (this.acrobaticsMod >=0)
		// sbSkills.append("+").append(acrobaticsMod);
		// else
		// sbSkills.append(acrobaticsMod);
		// if (StringUtils.isNotBlank(acrobaticsNotes))
		// sbSkills.append(" (").append(acrobaticsNotes).append(")");
		// sbSkills.append(", ");
		// }
		return sbSkills.toString();
	}

	public String toString()
	{
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		return gson.toJson(this);
	}
}
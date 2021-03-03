package org.pf1etools.creature.utils;

import lombok.Getter;

@Getter
public enum Skill
{
	ACROBATICS("Acrobatics","acro", "acrobatics"),
	APPRAISE("Appraise","appra", "appraise"),
	BLUFF("Bluff","bluff", "bluff"),
	CLIMB("Climb","clmb", "climb"),
	CRAFT("Craft","crft", "craft"),
	DIPLOMACY("Diplomacy","diplo", "diplomacy"),
	DISABLE_DEVICE("Disable Device","dbldev", "disableDevice"),
	DISGUISE("Disguise","disg", "disguise"),
	ESCAPE_ARTIST("Escape Artist","esc", "escapeArtist"),
	FLY("Fly","fly", "fly"),
	HANDLE_ANIMAL("Handle Animal","hanim", "handleAnimal"),
	HEAL("Heal","heal", "heal"),
	INTIMIDATE("Intimidate","intim", "intimidate"),
	KNOWLEDGE_ALL("Knowledge \\(all\\)","kall", "knowAll"),
	KNOWLEDGE_ARCANA("Knowledge \\(arcana\\)","karca", "knowArcana"),
	KNOWLEDGE_DUNGEONEERING("Knowledge \\(dungeoneering\\)","kdung", "knowDungeoneering"),
	KNOWLEDGE_ENGINEERING("Knowledge \\(engineering\\)","keng", "knowEngineering"),
	KNOWLEDGE_GEOGRAPHY("Knowledge \\(geography\\)","kgeo", "knowGeography"),
	KNOWLEDGE_HISTORY("Knowledge \\(history\\)","khist", "knowHistory"),
	KNOWLEDGE_LOCAL("Knowledge \\(local\\)","kloc", "knowLocal"),
	KNOWLEDGE_NATURE("Knowledge \\(nature\\)","knat", "knowNature"),
	KNOWLEDGE_NOBILITY("Knowledge \\(nobility\\)","knob", "knowNobility"),
	KNOWLEDGE_PLANES("Knowledge \\(planes\\)","kplns", "knowPlanes"),
	KNOWLEDGE_RELIGION("Knowledge \\(religion\\)","krelig", "knowReligion"),
	LINGUISTICS("Linguistics","lang", "linguistics"),
	PERCEPTION("Perception","perc", "perception"),
	PERFORM("Perform","perf", "perform"),
	PROFESSION("Profession","prof", "profession"),
	RIDE("Ride","rd", "ride"),
	SENSE_MOTIVE("Sense Motive","sm", "senseMotive"),
	SLEIGHT_OF_HAND("Sleight of Hand","soh", "sleightOfHand"),
	SPELLCRAFT("Spellcraft","sc", "spellcraft"),
	STEALTH("Stealth","stlth", "stealth"),
	SURVIVAL("Survival","surv", "survival"),
	SWIM("Swim","swm", "swim"),
	USE_MAGIC_DEVICE("Use Magic Device","umd", "useMagicDevice");
	
	private String parseName;
	private String abbr;
	private String javaPrefixName;
	
	private Skill(String parseName, String abbr, String javaPrefixName)
	{
		this.parseName = parseName;
		this.abbr = abbr;
		this.javaPrefixName = javaPrefixName;
	}
}

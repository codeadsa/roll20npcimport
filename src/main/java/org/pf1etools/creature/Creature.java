package org.pf1etools.creature;

import java.util.HashSet;

import org.pf1etools.creature.utils.LevelFraction;
import org.pf1etools.creature.utils.Sense;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Creature
{
	String name;
	Integer level;
	LevelFraction levelFraction;
	Integer xp;
	
	String alignment;
	String size;
	String type;
	
	Integer initiativeMod;
	String senses;
	String aura;
	
	String feats;
	String SpecialQualities;
	
	String spellLike;
	String spellsPrepared;
	String spellsKnown;
	String specialAbilities;
	
	public Defenses defenses = new Defenses();
	public Skills skills = new Skills();
	public Statistics stats = new Statistics();
	public Offense offenses = new Offense();
	
	public String toString() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }
}




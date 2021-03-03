package org.pf1etools.roll20npc.parser;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.pf1etools.creature.Creature;
import org.pf1etools.roll20npc.parser.TextParser;
import org.pf1etools.roll20npc.scraper.D20PfsrdScraper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import lombok.extern.log4j.Log4j2;

@SpringBootTest
@ActiveProfiles("Dev")
@Log4j2
public class ParseD20PfsrdMonstersTest
{
	public void testDownloadedMonsters()
	{
		int TOTAL_ERRORS_PREVIOUS = 35;
		ArrayList<MonsterResultUnit> results = new ArrayList<>();
		//get all the monster files
		File monsterDir = new File(D20PfsrdScraper.CREATURE_OUTPUT_FOLDER);
		File[] monsters = monsterDir.listFiles();
		for (File file: monsters)
		{
			MonsterResultUnit mru = new MonsterResultUnit(file);
			results.add(mru);
			
			try
			{
				FileInputStream fis = new FileInputStream(file.getAbsoluteFile());
			    String text = IOUtils.toString(fis, "UTF-8");
				Creature creature = TextParser.parse(text);
				if (creature.defenses.getHp() == null)
					throw new Exception (" had no HP.");
				if (creature.getInitiativeMod() == null)
					throw new Exception (" had no init.");
				if (creature.defenses.getHd() == null)
					throw new Exception (" had no HD.");
				if (creature.defenses.getHitDie() == null)
					throw new Exception (" had no Hitdie.");
				if (creature.stats.getBab() == null)
					throw new Exception (" had no BAB.");
				if (creature.defenses.getAc() == null)
					throw new Exception (" had no AC.");
				if (creature.defenses.getAcTouch() == null)
					throw new Exception (" had no AC Touch");
				if (creature.defenses.getAcFlatFooted() == null)
					throw new Exception (" had no AC Flat Footed");
				if (creature.defenses.getSaveFort() == null)
					throw new Exception (" had no Save Fort");
				if (creature.defenses.getSaveReflex() == null)
					throw new Exception (" had no Save Reflex");
				if (creature.defenses.getSaveWill() == null)
					throw new Exception (" had no Save Will");
				if (creature.offenses.getSpeedLand() == null && creature.offenses.getSpeedFly() == null && creature.offenses.getSpeedSwim() == null)
					throw new Exception (" had no Speed Land,Fly or Swim");
				if (creature.offenses.getMelee() == null)
					throw new Exception (" had no Melee");
				if (creature.stats.getAbilityStr() == null 
						&& creature.stats.getAbilityDex() == null
						&& creature.stats.getAbilityCon() == null
						&& creature.stats.getAbilityInt() == null
						&& creature.stats.getAbilityWis() == null 
						&& creature.stats.getAbilityCha() == null)
					throw new Exception (" had Attributes that were null");
				if (creature.offenses.getMelee().contains("/+"))
					throw new Exception (" had iterative attack " +creature.offenses.getMelee());
			}
			catch(Exception err)
			{
				mru.error = err;
			}
		}
		
		log.info("**************************************************");
		int i=0;
		for (MonsterResultUnit result : results)
		{
			if (result.error != null)
			{
				i++;
				System.out.println(String.format("%s|%s", result.file.getName(), result.error.getMessage()));
			}
		}
		log.info("**************************************************");
		log.info("There are {} errors", i);
		if (i != 35)
			log.info("There are {} new errors", TOTAL_ERRORS_PREVIOUS - i);	
	}	
	
	public Creature testDownloadedMonster(String downloadedfileName) throws Exception
	{
		File file = new File(D20PfsrdScraper.CREATURE_OUTPUT_FOLDER +downloadedfileName);
		FileInputStream fis = new FileInputStream(file.getAbsoluteFile());
	    String text = IOUtils.toString(fis, "UTF-8");
		return TextParser.parse(text);
	}
	
	@Test 
	public void testGriffon() throws Exception
	{
		Creature creature = testDownloadedMonster("griffon_magical-beasts.txt");
		assertThat(creature.getName()).isEqualTo("Griffon");
		assertThat(creature.getLevel()).isEqualTo(4);
		assertThat(creature.getXp()).isEqualTo(1200);		
		assertThat(creature.getAlignment()).isEqualTo("N");
		assertThat(creature.getSize()).isEqualTo("Large");
		assertThat(creature.getType()).isEqualTo("Magical Beast");
		assertThat(creature.getInitiativeMod()).isEqualTo(2);
		assertThat(creature.getSenses()).isEqualTo("darkvision 60 ft., low-light vision, scent");
		assertThat(creature.skills.getPerceptionMod()).isEqualTo(12);
		assertThat(creature.defenses.getAc()).isEqualTo(17);
		assertThat(creature.defenses.getAcTouch()).isEqualTo(11);
		assertThat(creature.defenses.getAcFlatFooted()).isEqualTo(15);
		assertThat(creature.getDefenses().getAcNotes()).isEqualTo("+2 Dex, +6 natural, -1 size");
		assertThat(creature.defenses.getHp()).isEqualTo(42);
		assertThat(creature.defenses.getHd()).isEqualTo(5);
		assertThat(creature.defenses.getHitDie()).isEqualTo(10);
		assertThat(creature.defenses.getHpMod()).isEqualTo("+15");
		assertThat(creature.defenses.getSaveFort()).isEqualTo(7);
		assertThat(creature.defenses.getSaveReflex()).isEqualTo(6);
		assertThat(creature.defenses.getSaveWill()).isEqualTo(4);
		assertThat(creature.offenses.getSpeedLand()).isEqualTo(30);
		assertThat(creature.offenses.getSpeedFly()).isEqualTo(80);
		assertThat(creature.offenses.getSpeedFlyNotes()).isEqualTo("average");
		assertThat(creature.offenses.getSpace()).isEqualTo(10);
		assertThat(creature.offenses.getReach()).isEqualTo(5);
		assertThat(creature.offenses.getMelee()).isEqualTo("bite +8 (1d6+3), 2 talons +7 (1d6+3)");
		assertThat(creature.offenses.getSpecialAttacks()).isEqualTo("pounce, rake (2 claws +7, 1d4+3)");
		assertThat(creature.stats.getAbilityStr()).isEqualTo(16);
		assertThat(creature.stats.getAbilityDex()).isEqualTo(15);
		assertThat(creature.stats.getAbilityCon()).isEqualTo(16);
		assertThat(creature.stats.getAbilityInt()).isEqualTo(5);
		assertThat(creature.stats.getAbilityWis()).isEqualTo(13);
		assertThat(creature.stats.getAbilityCha()).isEqualTo(8);
		assertThat(creature.stats.getBab()).isEqualTo(5);
		assertThat(creature.stats.getCmb()).isEqualTo(9);
		assertThat(creature.stats.getCmd()).isEqualTo(21);
		assertThat(creature.stats.getCmdNotes()).isEqualTo("25 vs. trip");
		assertThat(creature.skills.getAcrobaticsMod()).isEqualTo(10);
		assertThat(creature.skills.getFlyMod()).isEqualTo(6);
		assertThat(creature.skills.getPerceptionMod()).isEqualTo(12);
		assertThat(creature.skills.getNotes()).isEqualTo("+4 Acrobatics, +4 Perception");
		assertThat(creature.getFeats()).isEqualTo("Iron Will, Skill Focus (Perception), Weapon Focus (bite)");
		System.out.println(creature.skills.getSkillsPrint());
	}	
	
	
	
	@Test 
	public void testCopperDragonIndexOutOfBoundsIssue() throws Exception
	{
		Creature creature = testDownloadedMonster("adult-copper-dragon_dragons-dragon-metallic-copper.txt");		
	}
	
	@Test 
	public void testAgathionSpellLick() throws Exception
	{
		Creature creature = testDownloadedMonster("agathion-draconal_outsiders-agathion.txt");		
	}
	
	@Test 
	public void testSlagWorm_Weakness() throws Exception
	{
		Creature creature = testDownloadedMonster("slag-worm-TOHC_outsiders.txt");		
		assertThat(creature.defenses.getWeakness()).isEqualTo("susceptible to rust, vulnerability to cold");
	}	
	
	@Test
	public void testNameCrDiffLines() throws Exception
	{
		Creature creature = testDownloadedMonster("dragon-adult-cave-kp_dragons-dragon-dragon-cave-kp.txt");
		assertThat(creature.getName()).isEqualTo("Adult Cave Dragon");
		assertThat(creature.getLevel()).isEqualTo(11);
	}
	
	@Test
	public void testOldOneHastur() throws Exception
	{
		Creature creature = testDownloadedMonster("great-old-one-hastur_aberrations-great-old-ones.txt");
		assertThat(creature.getSpecialQualities()).isEqualTo("otherworldly insight");
	}	
	
	@Test
	public void testSpirit() throws Exception
	{
		Creature creature = testDownloadedMonster("groaning-spirit-tohc_undead.txt");
		assertThat(creature.offenses.getSpeedFly()).isEqualTo(30);
		assertThat(creature.offenses.getSpeedFlyNotes()).isEqualTo("perfect");
	}
	
	@Test
	public void testSR() throws Exception
	{
		Creature creature = testDownloadedMonster("omnipath_aberrations.txt");
		assertThat(creature.defenses.getSpellResist()).isEqualTo(29);
	}
	
	@Test //has 2 init blocks
	public void testSensesOnNewLine() throws Exception
	{
		Creature creature = testDownloadedMonster("marilith_outsiders-demon.txt");
		assertThat(creature.getInitiativeMod()).isEqualTo(4);
		assertThat(creature.getSenses()).isEqualTo("darkvision 60 ft., trueseeing");
	}	
	
	
}

class MonsterResultUnit
{
	public File file;
	public Exception error;
	
	public MonsterResultUnit(File file)
	{
		this.file = file;
	}
}

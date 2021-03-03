package org.pf1etools.roll20npc.robot;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import org.apache.commons.lang3.StringUtils;
import org.pf1etools.creature.Creature;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class CreatureRobot
{
	private static final int TYPING_DELAY_MS = 10;
	private static final String KEY_TAB = new String("\t");
		
	private static void type(int i, Robot robot)
	{
		robot.delay(40);
		robot.keyPress(i);
		robot.delay(40);
		robot.keyRelease(i);
	}
	
	private static int shiftCode(char c)
	{
		int code = (byte)c;
		if (code>96&&code<123)
			code = code-32;
		return code;
	}
	
	private static void type(String s, Robot robot)
	{
		char[] chars = s.toCharArray();
		for (char c : chars)
		{
			robot.delay(TYPING_DELAY_MS);
			
			if (c >= (byte)'A' && c <= (byte)'Z')
			{				
				robot.keyPress(KeyEvent.VK_SHIFT);
				robot.delay(TYPING_DELAY_MS);
				robot.keyPress(shiftCode(Character.toLowerCase(c)));
				robot.delay(TYPING_DELAY_MS);
				robot.keyRelease(shiftCode(Character.toLowerCase(c)));
				robot.delay(TYPING_DELAY_MS);
				robot.keyRelease(KeyEvent.VK_SHIFT);
			}
			else if (c == '+')
			{
				robot.keyPress(KeyEvent.VK_SHIFT);
				robot.delay(TYPING_DELAY_MS);
				robot.keyPress(shiftCode('='));
				robot.delay(TYPING_DELAY_MS);
				robot.keyRelease(shiftCode('='));
				robot.keyRelease(KeyEvent.VK_SHIFT);
			}
			else if (c == '(')
			{
				robot.keyPress(KeyEvent.VK_SHIFT);
				robot.delay(TYPING_DELAY_MS);
				robot.keyPress(shiftCode('9'));
				robot.delay(TYPING_DELAY_MS);
				robot.keyRelease(shiftCode('9'));
				robot.delay(TYPING_DELAY_MS);
				robot.keyRelease(KeyEvent.VK_SHIFT);
			}
			else if (c == ')')
			{
				robot.keyPress(KeyEvent.VK_SHIFT);
				robot.delay(TYPING_DELAY_MS);
				robot.keyPress(shiftCode('0'));
				robot.delay(TYPING_DELAY_MS);
				robot.keyRelease(shiftCode('0'));
				robot.delay(TYPING_DELAY_MS);
				robot.keyRelease(KeyEvent.VK_SHIFT);
			}
			else if (c == '*')
			{
				robot.keyPress(KeyEvent.VK_SHIFT);
				robot.delay(TYPING_DELAY_MS);
				robot.keyPress(shiftCode('8'));
				robot.delay(TYPING_DELAY_MS);
				robot.keyRelease(shiftCode('8'));
				robot.delay(TYPING_DELAY_MS);
				robot.keyRelease(KeyEvent.VK_SHIFT);
			}
			else if (c == '&')
			{
				robot.keyPress(KeyEvent.VK_SHIFT);
				robot.delay(TYPING_DELAY_MS);
				robot.keyPress(shiftCode('7'));
				robot.delay(TYPING_DELAY_MS);
				robot.keyRelease(shiftCode('7'));
				robot.delay(TYPING_DELAY_MS);
				robot.keyRelease(KeyEvent.VK_SHIFT);
			}
			else if (c == ':')
			{
				robot.keyPress(KeyEvent.VK_SHIFT);
				robot.delay(TYPING_DELAY_MS);
				robot.keyPress(shiftCode(';'));
				robot.delay(TYPING_DELAY_MS);
				robot.keyRelease(shiftCode(';'));
				robot.delay(TYPING_DELAY_MS);
				robot.keyRelease(KeyEvent.VK_SHIFT);
			}
			else if (c == '\r')
			{
				//do nothing. enter will be done on \n
			}
			else if (c == '\n')
			{
				robot.keyPress(KeyEvent.VK_ENTER);
				robot.delay(TYPING_DELAY_MS);
				robot.keyRelease(KeyEvent.VK_ENTER);
			}
			else
			{
				robot.keyPress(shiftCode(c));
				robot.delay(TYPING_DELAY_MS);				
				robot.keyRelease(shiftCode(c));
			}
		}
	}
	
	private static void type2(String s, Robot robot)
	{
		byte[] bytes = s.getBytes();
		for (byte b : bytes)
		{
			int code = b;
			// keycode only handles [A-Z] (which is ASCII decimal [65-90])
			if (code>96&&code<123)
				code = code-32;
			robot.delay(TYPING_DELAY_MS);
			robot.keyPress(code);
			robot.keyRelease(code);
		}
	}
	
	private static String optiNull(String value)
	{
		if (StringUtils.isBlank(value))
			return "";
		else
			return value;
	}
	
	private static String optiNull(Integer value)
	{
		if (value == null)
			return "";
		else
			return ""+value.intValue();
	}
	
	private static void transferCreatureOfficialRoll20(Creature c, Robot robot)
	{
		copyPasta(c.getName(), robot);
		copyPasta(optiNull(c.getLevel()), robot);
		pressTabKey(robot);
		copyPasta(optiNull(c.getXp()), robot);
		pressTabKey(robot);
		copyPasta(c.getAlignment(), robot);
		copyPasta(c.getSize(), robot);
		copyPasta(c.getType(), robot);
		copyPasta(optiNull(c.getInitiativeMod()), robot);
		pressTabKey(robot);
		copyPasta(c.getSenses(), robot);
		copyPasta(optiNull(c.getSkills().getPerceptionMod()), robot);
		copyPasta(c.getAura(), robot);
		copyPasta(optiNull(c.defenses.getAc()), robot);
		copyPasta(optiNull(c.defenses.getAcTouch()), robot);
		copyPasta(optiNull(c.defenses.getAcFlatFooted()), robot);
		copyPasta(optiNull(c.defenses.getAcNotes()), robot);
		copyPasta(optiNull(c.defenses.getHp()), robot);
		copyPasta(optiNull(c.defenses.getHp()), robot);
		copyPasta(optiNull(c.defenses.getHpNotes()), robot);
		copyPasta(optiNull(c.defenses.getHpPrint()), robot);
		copyPasta(c.defenses.getHd() +"d" +c.defenses.getHitDie(), robot);
		copyPasta(optiNull(c.defenses.getSaveFort()), robot);
		copyPasta(optiNull(c.defenses.getSaveReflex()), robot);
		copyPasta(optiNull(c.defenses.getSaveWill()), robot);
		copyPasta(optiNull(c.defenses.getSaveNotes()), robot);
		copyPasta(optiNull(c.defenses.getSpellResist()), robot);
		copyPasta(c.defenses.getDefensiveAbilities(), robot);		
		copyPasta(c.defenses.getDamageReduction(), robot);
		copyPasta(c.defenses.getImmune(), robot);
		copyPasta(c.defenses.getResist(), robot);
		copyPasta(c.defenses.getWeakness(), robot);
		
		copyPasta(c.offenses.getSpeedPrint(), robot);
		copyPasta(c.offenses.getSpace() +" ft.", robot);
		copyPasta(c.offenses.getReachPrint(), robot);
		pressTabKey(robot);
		pressTabKey(robot);
		pressTabKey(robot);
		pressTabKey(robot);
		pressTabKey(robot);
		pressTabKey(robot);
		pressTabKey(robot);
		pressTabKey(robot);
		pressTabKey(robot);
		pressTabKey(robot);
		pressTabKey(robot);
		pressTabKey(robot);
		pressTabKey(robot);
		pressTabKey(robot);
		pressTabKey(robot);		
		pressTabKey(robot);
		pressTabKey(robot);
		pressTabKey(robot);
		pressTabKey(robot);
		pressTabKey(robot);
		pressTabKey(robot);
		pressTabKey(robot);
		pressTabKey(robot);
		
//		
//		copyPasta(c.offenses.getMelee(), robot);
//		copyPasta(c.offenses.getRange(), robot);
//		copyPasta(c.offenses.getSpecialAttacks(), robot);
//		copyPasta(c.getSpellLike(), robot);
//		copyPasta(c.getSpellsKnown(), robot);
//		copyPasta(c.getSpellsPrepared(), robot);
		copyPasta(optiNull(c.stats.getAbilityStr()), robot);
		copyPasta(optiNull(c.stats.getAbilityDex()), robot);
		copyPasta(optiNull(c.stats.getAbilityCon()), robot);
		copyPasta(optiNull(c.stats.getAbilityInt()), robot);
		copyPasta(optiNull(c.stats.getAbilityWis()), robot);
		copyPasta(optiNull(c.stats.getAbilityCha()), robot);
		copyPasta(optiNull(c.stats.getBab()), robot);
		copyPasta(optiNull(c.stats.getCmb()), robot);
		copyPasta(c.stats.getCmbNotes(), robot);
		copyPasta(optiNull(c.stats.getCmd()), robot);
		copyPasta(c.stats.getCmdNotes(), robot);
		copyPasta(c.skills.getNotes(), robot);
		copyPasta(optiNull(c.skills.getAcrobaticsMod()), robot);
		copyPasta(c.skills.getAcrobaticsNotes(), robot);
		copyPasta(optiNull(c.skills.getAppraiseMod()), robot);
		copyPasta(c.skills.getAppraiseNotes(), robot);
		copyPasta(optiNull(c.skills.getBluffMod()), robot);
		copyPasta(c.skills.getBluffNotes(), robot);
		copyPasta(optiNull(c.skills.getClimbMod()), robot);
		copyPasta(c.skills.getClimbNotes(), robot);
		copyPasta(optiNull(c.skills.getCraftMod()), robot);
		copyPasta(c.skills.getCraftNotes(), robot);
		copyPasta(optiNull(c.skills.getDiplomacyMod()), robot);
		copyPasta(c.skills.getDiplomacyNotes(), robot);
		copyPasta(optiNull(c.skills.getDisableDeviceMod()), robot);
		copyPasta(c.skills.getDisableDeviceNotes(), robot);
		copyPasta(optiNull(c.skills.getDisguiseMod()), robot);
		copyPasta(c.skills.getDisguiseNotes(), robot);
		copyPasta(optiNull(c.skills.getEscapeArtistMod()), robot);
		copyPasta(c.skills.getEscapeArtistNotes(), robot);
		copyPasta(optiNull(c.skills.getFlyMod()), robot);
		copyPasta(c.skills.getFlyNotes(), robot);
		copyPasta(optiNull(c.skills.getHandleAnimalMod()), robot);
		copyPasta(c.skills.getHandleAnimalNotes(), robot);
		copyPasta(optiNull(c.skills.getHealMod()), robot);
		copyPasta(c.skills.getHealNotes(), robot);
		copyPasta(optiNull(c.skills.getIntimidateMod()), robot);
		copyPasta(c.skills.getIntimidateNotes(), robot);
		copyPasta(optiNull(c.skills.getKnowArcanaMod()), robot);
		copyPasta(c.skills.getKnowArcanaNotes(), robot);
		copyPasta(optiNull(c.skills.getKnowDungeoneeringMod()), robot);
		copyPasta(c.skills.getKnowDungeoneeringNotes(), robot);
		copyPasta(optiNull(c.skills.getKnowEngineeringMod()), robot);
		copyPasta(c.skills.getKnowEngineeringNotes(), robot);
		copyPasta(optiNull(c.skills.getKnowGeographyMod()), robot);
		copyPasta(c.skills.getKnowGeographyNotes(), robot);
		copyPasta(optiNull(c.skills.getKnowHistoryMod()), robot);
		copyPasta(c.skills.getKnowHistoryNotes(), robot);
		copyPasta(optiNull(c.skills.getKnowLocalMod()), robot);
		copyPasta(c.skills.getKnowLocalNotes(), robot);
		copyPasta(optiNull(c.skills.getKnowNatureMod()), robot);
		copyPasta(c.skills.getKnowNatureNotes(), robot);
		copyPasta(optiNull(c.skills.getKnowNobilityMod()), robot);
		copyPasta(c.skills.getKnowNobilityNotes(), robot);
		copyPasta(optiNull(c.skills.getKnowPlanesMod()), robot);
		copyPasta(c.skills.getKnowPlanesNotes(), robot);
		copyPasta(optiNull(c.skills.getKnowReligionMod()), robot);
		copyPasta(c.skills.getKnowReligionNotes(), robot);
		copyPasta(optiNull(c.skills.getLinguisticsMod()), robot);
		copyPasta(c.skills.getLinguisticsNotes(), robot);
		copyPasta(optiNull(c.skills.getPerceptionMod()), robot);
		copyPasta(c.skills.getPerceptionNotes(), robot);
		copyPasta(optiNull(c.skills.getPerformMod()), robot);
		copyPasta(c.skills.getPerformNotes(), robot);
		copyPasta(optiNull(c.skills.getProfessionMod()), robot);
		copyPasta(c.skills.getProfessionNotes(), robot);
		copyPasta(optiNull(c.skills.getRideMod()), robot);
		copyPasta(c.skills.getRideNotes(), robot);
		copyPasta(optiNull(c.skills.getSenseMotiveMod()), robot);
		copyPasta(c.skills.getSenseMotiveNotes(), robot);
		copyPasta(optiNull(c.skills.getSleightOfHandMod()), robot);
		copyPasta(c.skills.getSleightOfHandNotes(), robot);
		copyPasta(optiNull(c.skills.getSpellcraftMod()), robot);
		copyPasta(c.skills.getSpellcraftNotes(), robot);
		copyPasta(optiNull(c.skills.getStealthMod()), robot);
		copyPasta(c.skills.getStealthNotes(), robot);
		copyPasta(optiNull(c.skills.getSurvivalMod()), robot);
		copyPasta(c.skills.getSurvivalNotes(), robot);
		copyPasta(optiNull(c.skills.getSwimMod()), robot);
		copyPasta(c.skills.getSwimNotes(), robot);
		copyPasta(optiNull(c.skills.getUseMagicDeviceMod()), robot);
		copyPasta(c.skills.getUseMagicDeviceNotes(), robot);
		pressTabKey(robot);
		pressTabKey(robot);
		copyPasta(c.getSpecialQualities(), robot);
		
	}
	
	private static void transferCreaturePfCommunity(Creature c, Robot robot)
	{
		copyPasta(c.getName(), robot);
		copyPasta(optiNull(c.getLevel()), robot);
		copyPasta(optiNull(c.getXp()), robot);
		copyPasta(c.getAlignment(), robot);
		copyPasta(c.getSize(), robot);
		copyPasta(c.getType(), robot);
		pressTabKey(robot);
		copyPasta(optiNull(c.getInitiativeMod()), robot);
		copyPasta(c.getSenses(), robot);
		copyPasta(c.getAura(), robot);		
		copyPasta(c.defenses.getAcPrint(), robot);
		copyPasta(c.defenses.getHpPrint(), robot);
		copyPasta(optiNull(c.defenses.getSaveFort()), robot);
		copyPasta(optiNull(c.defenses.getSaveReflex()), robot);
		copyPasta(optiNull(c.defenses.getSaveWill()), robot);
		copyPasta(c.defenses.getDamageReduction(), robot);
		copyPasta(optiNull(c.defenses.getSpellResist()), robot);
		copyPasta(c.defenses.getImmune(), robot);
		copyPasta(c.defenses.getResist(), robot);
		copyPasta(c.defenses.getWeakness(), robot);
		copyPasta(c.defenses.getDefensiveAbilities(), robot);
		copyPasta(c.offenses.getSpeedPrint(), robot);
		copyPasta(c.offenses.getSpace() +" ft.", robot);
		copyPasta(c.offenses.getReachPrint(), robot);
		copyPasta(c.offenses.getMelee(), robot);
		copyPasta(c.offenses.getRange(), robot);
		copyPasta(c.offenses.getSpecialAttacks(), robot);
		copyPasta(c.getSpellLike(), robot);
		copyPasta(c.getSpellsKnown(), robot);
		copyPasta(c.getSpellsPrepared(), robot);
		copyPasta(optiNull(c.stats.getAbilityStr()), robot);
		copyPasta(optiNull(c.stats.getAbilityDex()), robot);
		copyPasta(optiNull(c.stats.getAbilityCon()), robot);
		copyPasta(optiNull(c.stats.getAbilityInt()), robot);
		copyPasta(optiNull(c.stats.getAbilityWis()), robot);
		copyPasta(optiNull(c.stats.getAbilityCha()), robot);
		copyPasta(optiNull(c.stats.getBab()), robot);
		copyPasta(optiNull(c.stats.getCmbPrint()), robot);
		copyPasta(optiNull(c.stats.getCmdPrint()), robot);
		copyPasta(c.getFeats(), robot);
		copyPasta(c.skills.getSkillsPrint(), robot);
		copyPasta(c.skills.getNotes(), robot);
		pressTabKey(robot);
		copyPasta(c.getSpecialQualities(), robot);
		pressTabKey(robot);
		pressTabKey(robot);
		pressTabKey(robot);
		copyPasta(c.getSpecialAbilities(), robot);
	}
	
	private static void copyPasta(String text, Robot robot)
	{
		copyToClipboard(text);
		pasteClipboard(robot);
		pressTabKey(robot);
	}
	
	private static void copyToClipboard(String txt)
	{
		StringSelection ss = new StringSelection(txt);
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss,null);
	}
	
	private static void pasteClipboard(Robot robot)
	{
		String os = System.getProperty("os.name").toLowerCase();
		if (os.contains("mac"))
		{
			pasteClipboardMac(robot);
		}
		else
		{
			pasteClipboardWindows(robot);
		}
	}
	
	private static void pasteClipboardWindows(Robot robot)
	{
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.delay(TYPING_DELAY_MS);
		robot.keyPress(KeyEvent.VK_V);
		robot.delay(TYPING_DELAY_MS);
		robot.keyRelease(KeyEvent.VK_V);
		robot.delay(TYPING_DELAY_MS);
		robot.keyRelease(KeyEvent.VK_CONTROL);
	}
	
	private static void pasteClipboardMac(Robot robot)
	{
		robot.keyPress(KeyEvent.VK_META);
		robot.delay(TYPING_DELAY_MS);
		robot.keyPress(KeyEvent.VK_V);
		robot.delay(TYPING_DELAY_MS);
		robot.keyRelease(KeyEvent.VK_V);
		robot.delay(TYPING_DELAY_MS);
		robot.keyRelease(KeyEvent.VK_META);
	}
	
	private static void pressTabKey(Robot robot)
	{
		robot.keyPress(KeyEvent.VK_TAB);
		robot.delay(TYPING_DELAY_MS);
		robot.keyRelease(KeyEvent.VK_TAB);
	}
	
	private static String generateCreatureDataKeystrokes(Creature c)
	{
		StringBuilder sbCreature = new StringBuilder();
		sbCreature
			.append(c.getName()).append(KEY_TAB)
			.append(c.getLevel()).append(KEY_TAB)
			.append(optiNull(c.getXp())).append(KEY_TAB)
			.append(optiNull(c.getAlignment())).append(KEY_TAB)
			.append(optiNull(c.getSize())).append(KEY_TAB)
			.append(optiNull(c.getType())).append(KEY_TAB)
			.append(KEY_TAB)
			.append(optiNull(c.getInitiativeMod())).append(KEY_TAB)
			.append(optiNull(c.getSenses())).append(KEY_TAB)
			.append(optiNull(c.getAura())).append(KEY_TAB)
			.append("AC ").append(c.defenses.getAc()).append(", touch ").append(c.defenses.getAcTouch()).append(", flat-footed ").append(c.defenses.getAcFlatFooted()).append(" (").append(optiNull(c.defenses.getAcNotes())).append(")").append(KEY_TAB)
			.append(c.defenses.getHpPrint()).append(KEY_TAB)
			.append(optiNull(c.defenses.getSaveFort())).append(KEY_TAB)
			.append(optiNull(c.defenses.getSaveReflex())).append(KEY_TAB)
			.append(optiNull(c.defenses.getSaveWill())).append(KEY_TAB)
			.append(optiNull(c.defenses.getDamageReduction())).append(KEY_TAB)
			.append(optiNull(c.defenses.getSpellResist())).append(KEY_TAB)
			.append(optiNull(c.defenses.getImmune())).append(KEY_TAB)
			.append(optiNull(c.defenses.getResist())).append(KEY_TAB)
			.append(optiNull(c.defenses.getWeakness())).append(KEY_TAB)
			.append(optiNull(c.defenses.getDefensiveAbilities())).append(KEY_TAB)		
			.append(c.offenses.getSpeedPrint()).append(KEY_TAB)
			.append(optiNull(c.offenses.getSpace() +" ft.")).append(KEY_TAB)
			.append(optiNull(c.offenses.getReachPrint())).append(KEY_TAB)
			.append(optiNull(c.offenses.getMelee())).append(KEY_TAB)
			.append(optiNull(c.offenses.getRange())).append(KEY_TAB)
			.append(optiNull(c.offenses.getSpecialAttacks())).append(KEY_TAB)
			.append(optiNull(c.getSpellLike())).append(KEY_TAB)
			.append(optiNull(c.getSpellsKnown())).append(KEY_TAB)
			.append(optiNull(c.getSpellsPrepared())).append(KEY_TAB)
			.append(optiNull(c.stats.getAbilityStr())).append(KEY_TAB)
			.append(optiNull(c.stats.getAbilityDex())).append(KEY_TAB)
			.append(optiNull(c.stats.getAbilityCon())).append(KEY_TAB)
			.append(optiNull(c.stats.getAbilityInt())).append(KEY_TAB)
			.append(optiNull(c.stats.getAbilityWis())).append(KEY_TAB)
			.append(optiNull(c.stats.getAbilityCha())).append(KEY_TAB)
			.append(optiNull(c.stats.getBab())).append(KEY_TAB)
			.append(optiNull(c.stats.getCmb())).append(KEY_TAB)
			.append(optiNull(c.stats.getCmd())).append(KEY_TAB)
			.append(optiNull(c.getFeats())).append(KEY_TAB)
			.append(optiNull(c.skills.getSkillsPrint())).append(KEY_TAB)
			.append(optiNull(c.skills.getNotes())).append(KEY_TAB)
			.append(KEY_TAB)
			.append(optiNull(c.getSpecialQualities())).append(KEY_TAB)
			.append(KEY_TAB).append(KEY_TAB).append(KEY_TAB)
			.append(optiNull(c.getSpecialAbilities()));
		return sbCreature.toString();
	}
	
	public static void pasteCreatureDataCopyPasta(Creature c, boolean isPfCommunitySheet) throws AWTException
	{
		Robot robot = new Robot();
		robot.setAutoDelay(TYPING_DELAY_MS);
	    robot.setAutoWaitForIdle(true);
	    robot.delay(5000); //wait 5 seconds before printing characters.
	    if (isPfCommunitySheet)
	    	transferCreaturePfCommunity(c, robot);
	    else
	    	transferCreatureOfficialRoll20(c, robot);
	}
	
	public static void pasteCreatureDataAutotypeValues(Creature c) throws AWTException
	{
		String keystrokes = generateCreatureDataKeystrokes(c);
		log.debug("keystroke for creature '{}'", keystrokes);
		log.debug("Starting Robot");
		
		Robot robot = new Robot();
		robot.setAutoDelay(TYPING_DELAY_MS);
	    robot.setAutoWaitForIdle(true);
	    robot.delay(5000); //wait 5 seconds before printing characters.
	    
	    type(keystrokes, robot);
	    robot.delay(1000);
	    log.debug("Ending Robot");   
	}
	
	public static void main(String[] args) throws Exception
	{
		System.out.println("Starting robot");
		Robot robot = new Robot();
		robot.setAutoDelay(TYPING_DELAY_MS);
	    robot.setAutoWaitForIdle(true);	    
	    
	    robot.delay(5000);
	    type("Darakhul Ogre	5	1600	LE	Large	Undead (augmented Giant)		0	darkvision 120 ft.	null	AC 23, touch 10, flat-footed 19 (+4 armor, +1 Dex, +9 natural, -1 size)	hp 22	5	2	5	5/magic and daylight	null	undead traits	null	daylight weakness	channel resistance +4	Speed 30 ft., 15 ft.	10	10null	bite +10 (2d6+8 plus paralysis and disease) and 2 claws +10 (1d6+4 plus paralysis) or greatclub +10 (2d8+12) and bite +8 (1d10+8 plus paralysis and disease)	javelin +3 (1d8+8)	paralysis (1d4+1 rounds, DC 13, elves are immune to this effect)				27	12	null	10	14	13	3	12	23	", robot);
//	    type("(this). - 1 2 A b C", robot);
	    System.out.println("exiting");
	    robot.delay(1000);
	    System.exit(0);
	}

}

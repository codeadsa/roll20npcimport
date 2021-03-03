package org.pf1etools.roll20npc.ui;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.net.URI;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.JTextComponent;
import javax.swing.text.NumberFormatter;

import org.apache.commons.lang3.StringUtils;
import org.pf1etools.Pf1etoolsApplication;
import org.pf1etools.creature.Creature;
import org.pf1etools.roll20npc.parser.TextParser;
import org.pf1etools.roll20npc.robot.CreatureRobot;
import org.pf1etools.roll20npc.ui.controls.HintTextArea;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.ConfigurableApplicationContext;

import lombok.extern.log4j.Log4j2;

@Log4j2
//@SpringBootApplication
public class ConvertTextToRoll20PfNpcRobotUI
{
	private static String VERSION = "v1.0";
	
	Creature creature;
	
	private BufferedImage buffImg;
	private NumberFormatter numberFormatter;
	private ImageIcon iconD20, iconPfCommunitySheet, iconPfByRoll20;
	private JFrame mainUI;
	private HintTextArea txtAreaMonster;
	private JScrollPane scrollTxtAreaMonster, scrollCreatureData;
	private JScrollPane scroll;
	private JButton btnParse, btnPaste;
	private JPanel panelCreature, panelDiceRoll;
	private JLabel lblDie;
	private JLabel lblName, lblXp, lblCr, lblAlign, lblSize, lblType, lblInit, lblSenses, lblAura;
	private JLabel lblAc, lblAcTouch, lblAcFlatFoot, lblAcNotes, lblHp, lblHpNotes, lblSaveFort, lblSaveRef, lblSaveWill, lblDefDr, lblDefImmune, lblDefResist, lblDefWeakness, lblDefSpellResits, lblDefAbilities;
	private JLabel lblStatStr, lblStatDex, lblStatCon, lblStatInt, lblStatWis, lblStatCha, lblStatBab, lblStatCmb, lblStatCmd, lblStatCmbNotes, lblStatCmdNotes;
	private JLabel lblSkillAcrobatics, lblSkillAppraise, lblSkillBluff, lblSkillClimb, lblSkillCraft, lblSkillDiplomacy, lblSkillDisableDevice, lblSkillDisguise, lblSkillEscapeArtist, lblSkillFly, lblSkillHandleAnimal, lblSkillHeal, lblSkillIntimidate, lblSkillKnowAll, lblSkillKnowArcana, lblSkillKnowDungeoneering, lblSkillKnowEngineering, lblSkillKnowGeography, lblSkillKnowHistory, lblSkillKnowLocal, lblSkillKnowNature, lblSkillKnowNobility, lblSkillKnowPlanes, lblSkillKnowReligion, lblSkillLinguistics, lblSkillPerception, lblSkillPerform, lblSkillProfession, lblSkillRide, lblSkillSenseMotive, lblSkillSleightOfHand, lblSkillSpellcraft, lblSkillStealth, lblSkillSurvival, lblSkillSwim, lblSkillUseMagicDevice, lblSkillNotes;
	private JLabel lblSpeedLand, lblSpeedBurrow, lblSpeedClimb, lblSpeedFly, lblSpeedSwim, lblSpeedNotes, lblSpace, lblReach, lblReachNotes, lblMelee, lblRanged, lblSpecialAttacks;
	private JLabel lblFeats, lblSq, lblSpellLike, lblSpellsKnown, lblSpellsPrepared, lblSpecialAbilities;	
	
	private JTextField txtFieldName, txtFieldAlign, txtFieldType, txtFieldSenses, txtFieldAura;
	private JTextField txtFieldAcNotes, txtFieldHpMod, txtFieldHpNotes, txtFieldDefDr, txtFieldDefImmune, txtFieldDefResist, txtFieldDefWeakness, txtFieldDefAbilities;
	private JTextField txtFieldStatCmbNotes, txtFieldStatCmdNotes;
	private JTextField txtFieldSkillNotes;
	private JTextField txtFieldSkillAcrobaticsNotes, txtFieldSkillAppraiseNotes, txtFieldSkillBluffNotes, txtFieldSkillClimbNotes, txtFieldSkillCraftNotes, txtFieldSkillDiplomacyNotes, txtFieldSkillDisableDeviceNotes, txtFieldSkillDisguiseNotes, txtFieldSkillEscapeArtistNotes, txtFieldSkillFlyNotes, txtFieldSkillHandleAnimalNotes, txtFieldSkillHealNotes, txtFieldSkillIntimidateNotes, txtFieldSkillKnowAllNotes, txtFieldSkillKnowArcanaNotes, txtFieldSkillKnowDungeoneeringNotes, txtFieldSkillKnowEngineeringNotes, txtFieldSkillKnowGeographyNotes, txtFieldSkillKnowHistoryNotes, txtFieldSkillKnowLocalNotes, txtFieldSkillKnowNatureNotes, txtFieldSkillKnowNobilityNotes, txtFieldSkillKnowPlanesNotes, txtFieldSkillKnowReligionNotes, txtFieldSkillLinguisticsNotes, txtFieldSkillPerceptionNotes, txtFieldSkillPerformNotes, txtFieldSkillProfessionNotes, txtFieldSkillRideNotes, txtFieldSkillSenseMotiveNotes, txtFieldSkillSleightOfHandNotes, txtFieldSkillSpellcraftNotes, txtFieldSkillStealthNotes, txtFieldSkillSurvivalNotes, txtFieldSkillSwimNotes, txtFieldSkillUseMagicDeviceNotes;
	private JTextField txtFieldSpeedFlyNotes, txtFieldSpeedSwim, txtFieldSpeedNotes, txtFieldSpace, txtFieldReach, txtFieldReachNotes, txtFieldMelee, txtFieldRanged, txtFieldSpecialAttacks;
	private JTextField txtFieldFeats, txtFieldSq;
	private JTextArea txtAreaSpellLike, txtAreaSpellsKnown, txtAreaSpellsPrepared, txtAreaSpecialAbilities;
	
	private JFormattedTextField txtFieldXp, txtFieldCr, txtFieldSize, txtFieldInit, txtFieldAc, txtFieldAcTouch, txtFieldAcFlatFoot, txtFieldHp, txtFieldHd, txtFieldHitDie, txtFieldSaveFort, txtFieldSaveRef, txtFieldSaveWill, txtFieldDefSpellResits;
	private JFormattedTextField txtFieldStatStr, txtFieldStatDex, txtFieldStatCon, txtFieldStatInt, txtFieldStatWis, txtFieldStatCha, txtFieldStatBab, txtFieldStatCmb, txtFieldStatCmd;
	private JFormattedTextField txtFieldSkillAcrobatics, txtFieldSkillAppraise, txtFieldSkillBluff, txtFieldSkillClimb, txtFieldSkillCraft, txtFieldSkillDiplomacy, txtFieldSkillDisableDevice, txtFieldSkillDisguise, txtFieldSkillEscapeArtist, txtFieldSkillFly, txtFieldSkillHandleAnimal, txtFieldSkillHeal, txtFieldSkillIntimidate, txtFieldSkillKnowAll, txtFieldSkillKnowArcana, txtFieldSkillKnowDungeoneering, txtFieldSkillKnowEngineering, txtFieldSkillKnowGeography, txtFieldSkillKnowHistory, txtFieldSkillKnowLocal, txtFieldSkillKnowNature, txtFieldSkillKnowNobility, txtFieldSkillKnowPlanes, txtFieldSkillKnowReligion, txtFieldSkillLinguistics, txtFieldSkillPerception, txtFieldSkillPerform, txtFieldSkillProfession, txtFieldSkillRide, txtFieldSkillSenseMotive, txtFieldSkillSleightOfHand, txtFieldSkillSpellcraft, txtFieldSkillStealth, txtFieldSkillSurvival, txtFieldSkillSwim, txtFieldSkillUseMagicDevice;
	private JFormattedTextField txtFieldSpeedLand, txtFieldSpeedBurrow, txtFieldSpeedClimb, txtFieldSpeedFly;
	
	//Top notification panel
	private JPanel pnlNotify;
	private JLabel lblNotify;
	private static JLabel lblUrl;
	public ConvertTextToRoll20PfNpcRobotUI()
	{
		this.startUI();
	}

	private void clearTextFieldsExceptForCreatureTextArea()
	{
		findComponents(mainUI, JTextField.class).stream().forEach(f -> f.setText(""));
		findComponents(mainUI, JFormattedTextField.class).stream().forEach(f -> f.setText(null));
		findComponents(mainUI, JTextArea.class).stream().filter(c -> c.getName() != "txtAreaMonster").forEach(f ->f.setText(""));
	}

	private void createUIComponents()
	{
		pnlNotify = new JPanel();
		lblNotify = new JLabel("Please report bugs, suggestions or issues to");
		pnlNotify.add(lblNotify);
		pnlNotify.add(lblUrl);
		
		NumberFormat format = NumberFormat.getInstance();
		format.setGroupingUsed(false);
	    numberFormatter = new NumberFormatter(format) {
	    	public Object stringToValue(String string) //used to clear out the old value to null/blank. https://www.experts-exchange.com/questions/20453713/Allowing-blank-JFormattedTextField-fields.html
                    throws ParseException {
                    if (string == null || string.length() == 0) {
                        return null;
                    }
                    return super.stringToValue(string);
                }
	    	};
	    numberFormatter.setValueClass(Integer.class);
	    numberFormatter.setMinimum(0);
	    numberFormatter.setMaximum(Integer.MAX_VALUE);
	    numberFormatter.setAllowsInvalid(false);
	    // If you want the value to be committed on each keystroke instead of focus lost
	    numberFormatter.setCommitsOnValidEdit(true);
	    
		try
		{
			buffImg = ImageIO.read(this.getClass().getResourceAsStream("/images/d20x64.png"));
			iconD20 = new ImageIcon(buffImg);
			
			iconPfCommunitySheet = new ImageIcon(ImageIO.read(this.getClass().getResourceAsStream("/images/PFCommunitySheet.png")));
			iconPfByRoll20 = new ImageIcon(ImageIO.read(this.getClass().getResourceAsStream("/images/PFbyRoll20.png")));
		}
		catch(Exception err)
		{
			log.error("Coudn't load d20 icon", err);
		}
		
		mainUI = new JFrame();
		txtAreaMonster = new HintTextArea("Paste NPC stat block text here");
		txtAreaMonster.setName("txtAreaMonster");
		scrollTxtAreaMonster = new JScrollPane(txtAreaMonster);
		scrollTxtAreaMonster.getVerticalScrollBar().setUnitIncrement(16);

		btnParse = new JButton("Parse text");
		btnPaste = new JButton("Paste stats");
		// btnPaste.setSize(150, 40);
		panelCreature = new JPanel();
		panelCreature.hide();
		panelDiceRoll = new JPanel();
		

		final int LABEL_WIDTH = 50;
		final int LABEL_SKILL_WIDTH = 75;
		final int LABEL_LONG_WIDTH = 100;
		final int LABEL_HIGHT = 20;
		lblDie = new JLabel(new ImageIcon(buffImg));
		
		lblName = new JLabel("Name:", SwingConstants.RIGHT);
		lblName.setPreferredSize(new Dimension(LABEL_WIDTH, LABEL_HIGHT));
		lblXp = new JLabel("XP:");
		lblCr = new JLabel("CR:");

		lblAlign = new JLabel("Align:", SwingConstants.RIGHT);
		lblAlign.setPreferredSize(new Dimension(LABEL_WIDTH, LABEL_HIGHT));
		lblSize = new JLabel("Size:");
		lblType = new JLabel("Type:");

		lblInit = new JLabel("Init:", SwingConstants.RIGHT);
		lblInit.setPreferredSize(new Dimension(LABEL_WIDTH, LABEL_HIGHT));

		lblSenses = new JLabel("Senses:", SwingConstants.RIGHT);
		lblSenses.setPreferredSize(new Dimension(LABEL_WIDTH, LABEL_HIGHT));
		lblAura = new JLabel("Aura:");

		lblAc = new JLabel("AC:", SwingConstants.RIGHT);
		lblAc.setPreferredSize(new Dimension(LABEL_WIDTH, LABEL_HIGHT));
		lblAcTouch = new JLabel("Touch:");
		lblAcFlatFoot = new JLabel("Flat-Footed:");
		lblAcNotes = new JLabel("Notes:");

		lblHp = new JLabel("HP:", SwingConstants.RIGHT);
		lblHp.setPreferredSize(new Dimension(LABEL_WIDTH, LABEL_HIGHT));
		lblHpNotes = new JLabel("Notes:");

		lblSaveFort = new JLabel("Fort:", SwingConstants.RIGHT);
		lblSaveFort.setPreferredSize(new Dimension(LABEL_WIDTH, LABEL_HIGHT));
		lblSaveRef = new JLabel("Ref:");
		lblSaveWill = new JLabel("Will:");

		lblDefDr = new JLabel("DR:", SwingConstants.RIGHT);
		lblDefDr.setPreferredSize(new Dimension(LABEL_WIDTH, LABEL_HIGHT));
		lblDefImmune = new JLabel("Immune:", SwingConstants.RIGHT);
		lblDefImmune.setPreferredSize(new Dimension(LABEL_WIDTH, LABEL_HIGHT));
		lblDefResist = new JLabel("Resist:");
		lblDefWeakness = new JLabel("Weak:", SwingConstants.RIGHT);
		lblDefWeakness.setPreferredSize(new Dimension(LABEL_WIDTH, LABEL_HIGHT));
		lblDefSpellResits = new JLabel("SR:");
		lblDefAbilities = new JLabel("Abilities:");

		lblStatStr = new JLabel("Str:", SwingConstants.RIGHT);
		lblStatStr.setPreferredSize(new Dimension(LABEL_WIDTH, LABEL_HIGHT));
		lblStatDex = new JLabel("Dex:");
		lblStatCon = new JLabel("Con:");
		lblStatInt = new JLabel("Int:");
		lblStatWis = new JLabel("Wis:");
		lblStatCha = new JLabel("Cha:");

		lblStatBab = new JLabel("BAB:", SwingConstants.RIGHT);
		lblStatBab.setPreferredSize(new Dimension(LABEL_WIDTH, LABEL_HIGHT));
		lblStatCmb = new JLabel("CMB:");
		lblStatCmd = new JLabel("CMD:");
		lblStatCmbNotes = new JLabel("Notes:");
		lblStatCmdNotes = new JLabel("Notes:");

		lblSkillAcrobatics = new JLabel("Acrobatics:", SwingConstants.RIGHT);
		lblSkillAcrobatics.setPreferredSize(new Dimension(LABEL_SKILL_WIDTH, LABEL_HIGHT));
		lblSkillAppraise = new JLabel("Appraise:", SwingConstants.RIGHT);
		lblSkillAppraise.setPreferredSize(new Dimension(LABEL_SKILL_WIDTH, LABEL_HIGHT));
		lblSkillBluff = new JLabel("Bluff:", SwingConstants.RIGHT);
		lblSkillBluff.setPreferredSize(new Dimension(LABEL_SKILL_WIDTH, LABEL_HIGHT));
		lblSkillClimb = new JLabel("Climb:", SwingConstants.RIGHT);
		lblSkillClimb.setPreferredSize(new Dimension(LABEL_SKILL_WIDTH, LABEL_HIGHT));
		lblSkillCraft = new JLabel("Craft:", SwingConstants.RIGHT);
		lblSkillCraft.setPreferredSize(new Dimension(LABEL_SKILL_WIDTH, LABEL_HIGHT));
		lblSkillDiplomacy = new JLabel("Diplo:", SwingConstants.RIGHT);
		lblSkillDiplomacy.setPreferredSize(new Dimension(LABEL_SKILL_WIDTH, LABEL_HIGHT));
		lblSkillDisableDevice = new JLabel("Dsbl Dev:", SwingConstants.RIGHT);
		lblSkillDisableDevice.setPreferredSize(new Dimension(LABEL_SKILL_WIDTH, LABEL_HIGHT));
		lblSkillDisguise = new JLabel("Disguise:", SwingConstants.RIGHT);
		lblSkillDisguise.setPreferredSize(new Dimension(LABEL_SKILL_WIDTH, LABEL_HIGHT));
		lblSkillEscapeArtist = new JLabel("Esc Artist:", SwingConstants.RIGHT);
		lblSkillEscapeArtist.setPreferredSize(new Dimension(LABEL_SKILL_WIDTH, LABEL_HIGHT));		
		lblSkillFly = new JLabel("Fly:", SwingConstants.RIGHT);
		lblSkillFly.setPreferredSize(new Dimension(LABEL_SKILL_WIDTH, LABEL_HIGHT));
		lblSkillHandleAnimal = new JLabel("Handle Anim:", SwingConstants.RIGHT);
		lblSkillHandleAnimal.setPreferredSize(new Dimension(LABEL_SKILL_WIDTH, LABEL_HIGHT));
		lblSkillHeal = new JLabel("Heal:", SwingConstants.RIGHT);
		lblSkillHeal.setPreferredSize(new Dimension(LABEL_SKILL_WIDTH, LABEL_HIGHT));
		lblSkillIntimidate = new JLabel("Intimidate:", SwingConstants.RIGHT);
		lblSkillIntimidate.setPreferredSize(new Dimension(LABEL_SKILL_WIDTH, LABEL_HIGHT));
		lblSkillKnowAll = new JLabel("K. All:", SwingConstants.RIGHT);
		lblSkillKnowAll.setPreferredSize(new Dimension(LABEL_SKILL_WIDTH, LABEL_HIGHT));
		lblSkillKnowArcana = new JLabel("K. Arcana:", SwingConstants.RIGHT);		
		lblSkillKnowArcana.setPreferredSize(new Dimension(LABEL_SKILL_WIDTH, LABEL_HIGHT));
		lblSkillKnowDungeoneering = new JLabel("K. Dung:", SwingConstants.RIGHT);
		lblSkillKnowDungeoneering.setPreferredSize(new Dimension(LABEL_SKILL_WIDTH, LABEL_HIGHT));
		lblSkillKnowEngineering = new JLabel("K. Eng:", SwingConstants.RIGHT);
		lblSkillKnowEngineering.setPreferredSize(new Dimension(LABEL_SKILL_WIDTH, LABEL_HIGHT));
		lblSkillKnowGeography = new JLabel("K. Geo:", SwingConstants.RIGHT);
		lblSkillKnowGeography.setPreferredSize(new Dimension(LABEL_SKILL_WIDTH, LABEL_HIGHT));
		lblSkillKnowHistory = new JLabel("K Hist:", SwingConstants.RIGHT);
		lblSkillKnowHistory.setPreferredSize(new Dimension(LABEL_SKILL_WIDTH, LABEL_HIGHT));
		lblSkillKnowLocal = new JLabel("K Local:", SwingConstants.RIGHT);
		lblSkillKnowLocal.setPreferredSize(new Dimension(LABEL_SKILL_WIDTH, LABEL_HIGHT));
		lblSkillKnowNature = new JLabel("K Nature:", SwingConstants.RIGHT);
		lblSkillKnowNature.setPreferredSize(new Dimension(LABEL_SKILL_WIDTH, LABEL_HIGHT));
		lblSkillKnowNobility = new JLabel("K Nobil:", SwingConstants.RIGHT);
		lblSkillKnowNobility.setPreferredSize(new Dimension(LABEL_SKILL_WIDTH, LABEL_HIGHT));
		lblSkillKnowPlanes = new JLabel("K. Planes:", SwingConstants.RIGHT);
		lblSkillKnowPlanes.setPreferredSize(new Dimension(LABEL_SKILL_WIDTH, LABEL_HIGHT));
		lblSkillKnowReligion = new JLabel("K. Relig:", SwingConstants.RIGHT);
		lblSkillKnowReligion.setPreferredSize(new Dimension(LABEL_SKILL_WIDTH, LABEL_HIGHT));
		lblSkillLinguistics = new JLabel("Ling:", SwingConstants.RIGHT);
		lblSkillLinguistics.setPreferredSize(new Dimension(LABEL_SKILL_WIDTH, LABEL_HIGHT));
		lblSkillPerception = new JLabel("Percept:", SwingConstants.RIGHT);
		lblSkillPerception.setPreferredSize(new Dimension(LABEL_SKILL_WIDTH, LABEL_HIGHT));
		lblSkillPerform = new JLabel("Perform:", SwingConstants.RIGHT);
		lblSkillPerform.setPreferredSize(new Dimension(LABEL_SKILL_WIDTH, LABEL_HIGHT));
		lblSkillProfession = new JLabel("Profess:", SwingConstants.RIGHT);
		lblSkillProfession.setPreferredSize(new Dimension(LABEL_SKILL_WIDTH, LABEL_HIGHT));
		lblSkillRide = new JLabel("Ride:", SwingConstants.RIGHT);
		lblSkillRide.setPreferredSize(new Dimension(LABEL_SKILL_WIDTH, LABEL_HIGHT));
		lblSkillSenseMotive = new JLabel("Sns Motive:", SwingConstants.RIGHT);
		lblSkillSenseMotive.setPreferredSize(new Dimension(LABEL_SKILL_WIDTH, LABEL_HIGHT));
		lblSkillSleightOfHand = new JLabel("Slgt of Hnd:", SwingConstants.RIGHT);
		lblSkillSleightOfHand.setPreferredSize(new Dimension(LABEL_SKILL_WIDTH, LABEL_HIGHT));
		lblSkillSpellcraft = new JLabel("Spllcrft:", SwingConstants.RIGHT);
		lblSkillSpellcraft.setPreferredSize(new Dimension(LABEL_SKILL_WIDTH, LABEL_HIGHT));
		lblSkillStealth = new JLabel("Stealth:", SwingConstants.RIGHT);
		lblSkillStealth.setPreferredSize(new Dimension(LABEL_SKILL_WIDTH, LABEL_HIGHT));
		lblSkillSurvival = new JLabel("Survival:", SwingConstants.RIGHT);
		lblSkillSurvival.setPreferredSize(new Dimension(LABEL_SKILL_WIDTH, LABEL_HIGHT));
		lblSkillSwim = new JLabel("Swim:", SwingConstants.RIGHT);
		lblSkillSwim.setPreferredSize(new Dimension(LABEL_SKILL_WIDTH, LABEL_HIGHT));
		lblSkillUseMagicDevice = new JLabel("UMD:", SwingConstants.RIGHT);
		lblSkillUseMagicDevice.setPreferredSize(new Dimension(LABEL_SKILL_WIDTH, LABEL_HIGHT));
		lblSkillNotes = new JLabel("Notes:");
		
		lblSpeedLand = new JLabel("Speed:", SwingConstants.RIGHT);
		lblSpeedLand.setPreferredSize(new Dimension(LABEL_WIDTH, LABEL_HIGHT));
		lblSpeedBurrow = new JLabel("Burrow:");
		lblSpeedClimb = new JLabel("Climb:");
		lblSpeedFly = new JLabel("Fly:");
		lblSpeedSwim = new JLabel("Swim:");
		lblSpeedNotes = new JLabel("Notes:");
		lblSpace = new JLabel("Space:", SwingConstants.RIGHT);
		lblSpace.setPreferredSize(new Dimension(LABEL_WIDTH, LABEL_HIGHT));
		lblReach = new JLabel("Reach:");
		lblReachNotes = new JLabel("Notes:");
		lblMelee = new JLabel("Melee:", SwingConstants.RIGHT);
		lblMelee.setPreferredSize(new Dimension(LABEL_WIDTH, LABEL_HIGHT));
		lblRanged = new JLabel("Ranged:", SwingConstants.RIGHT);
		lblRanged.setPreferredSize(new Dimension(LABEL_WIDTH, LABEL_HIGHT));
		lblSpecialAttacks = new JLabel("Special:", SwingConstants.RIGHT);
		lblSpecialAttacks.setPreferredSize(new Dimension(LABEL_WIDTH, LABEL_HIGHT));
				
		lblFeats = new JLabel("Feats:", SwingConstants.RIGHT);
		lblFeats.setPreferredSize(new Dimension(LABEL_WIDTH, LABEL_HIGHT));
		lblSq = new JLabel("SQ:", SwingConstants.RIGHT);
		lblSq.setPreferredSize(new Dimension(LABEL_WIDTH, LABEL_HIGHT));

		lblSpellLike = new JLabel("Spell-Like:", SwingConstants.RIGHT);
		lblSpellLike.setPreferredSize(new Dimension(LABEL_LONG_WIDTH, LABEL_HIGHT));
		lblSpellsKnown = new JLabel("Spells Known:", SwingConstants.RIGHT);
		lblSpellsKnown.setPreferredSize(new Dimension(LABEL_LONG_WIDTH, LABEL_HIGHT));
		lblSpellsPrepared = new JLabel("Spells Prepared:", SwingConstants.RIGHT);
		lblSpellsPrepared.setPreferredSize(new Dimension(LABEL_LONG_WIDTH, LABEL_HIGHT));
		lblSpecialAbilities = new JLabel("Special Abilities:", SwingConstants.RIGHT);
		lblSpecialAbilities.setPreferredSize(new Dimension(LABEL_LONG_WIDTH, LABEL_HIGHT));
		
		txtFieldName = new JTextField(10);
		txtFieldXp = new JFormattedTextField(numberFormatter);
		txtFieldXp.setColumns(5);
		txtFieldCr = new JFormattedTextField(numberFormatter);
		txtFieldCr.setColumns(2);
		txtFieldAlign = new JTextField(10);
		txtFieldSize = new JFormattedTextField(numberFormatter);
		txtFieldSize.setColumns(10);
		txtFieldType = new JTextField(10);
		txtFieldInit = new JFormattedTextField(numberFormatter);
		txtFieldInit.setColumns(2);
		txtFieldSenses = new JTextField(20);
		txtFieldAura = new JTextField(20);

		txtFieldAc = new JFormattedTextField(numberFormatter);
		txtFieldAc.setColumns(2);
		txtFieldAcTouch = new JFormattedTextField(numberFormatter);
		txtFieldAcTouch.setColumns(2);
		txtFieldAcFlatFoot = new JFormattedTextField(numberFormatter);
		txtFieldAcFlatFoot.setColumns(2);
		txtFieldAcNotes = new JTextField(10);
		txtFieldHp = new JFormattedTextField(numberFormatter);
		txtFieldHp.setColumns(2);
		
		txtFieldHd = new JFormattedTextField(numberFormatter);
		txtFieldHd.setColumns(2);
		txtFieldHitDie = new JFormattedTextField(numberFormatter);
		txtFieldHitDie.setColumns(2);
		txtFieldHpMod = new JTextField(4);		
		txtFieldHpNotes = new JTextField(10);
		txtFieldSaveFort = new JFormattedTextField(numberFormatter);
		txtFieldSaveFort.setColumns(2);
		txtFieldSaveRef = new JFormattedTextField(numberFormatter);
		txtFieldSaveRef.setColumns(2);
		txtFieldSaveWill = new JFormattedTextField(numberFormatter);
		txtFieldSaveWill.setColumns(2);
		txtFieldDefDr = new JTextField(5);
		txtFieldDefImmune = new JTextField(20);
		txtFieldDefResist = new JTextField(20);
		txtFieldDefWeakness = new JTextField(20);
		txtFieldDefSpellResits = new JFormattedTextField(numberFormatter);
		txtFieldDefSpellResits.setColumns(2);
		txtFieldDefAbilities = new JTextField(20);

		txtFieldStatStr = new JFormattedTextField(numberFormatter);
		txtFieldStatStr.setColumns(2);
		txtFieldStatDex = new JFormattedTextField(numberFormatter);
		txtFieldStatDex.setColumns(2);
		txtFieldStatCon = new JFormattedTextField(numberFormatter);
		txtFieldStatCon.setColumns(2);
		txtFieldStatInt = new JFormattedTextField(numberFormatter);
		txtFieldStatInt.setColumns(2);
		txtFieldStatWis = new JFormattedTextField(numberFormatter);
		txtFieldStatWis.setColumns(2);
		txtFieldStatCha = new JFormattedTextField(numberFormatter);
		txtFieldStatCha.setColumns(2);
		txtFieldStatBab = new JFormattedTextField(numberFormatter);
		txtFieldStatBab.setColumns(2);
		txtFieldStatCmb = new JFormattedTextField(numberFormatter);
		txtFieldStatCmb.setColumns(2);
		txtFieldStatCmd = new JFormattedTextField(numberFormatter);
		txtFieldStatCmd.setColumns(2);
		txtFieldStatCmbNotes = new JTextField(10);
		txtFieldStatCmdNotes = new JTextField(10);

		txtFieldSkillAcrobatics = new JFormattedTextField(numberFormatter);
		txtFieldSkillAcrobatics.setColumns(2);
		txtFieldSkillAppraise = new JFormattedTextField(numberFormatter);
		txtFieldSkillAppraise.setColumns(2);
		txtFieldSkillBluff = new JFormattedTextField(numberFormatter);
		txtFieldSkillBluff.setColumns(2);
		txtFieldSkillClimb = new JFormattedTextField(numberFormatter);
		txtFieldSkillClimb.setColumns(2);
		txtFieldSkillCraft = new JFormattedTextField(numberFormatter);
		txtFieldSkillCraft.setColumns(2);
		txtFieldSkillDiplomacy = new JFormattedTextField(numberFormatter);
		txtFieldSkillDiplomacy.setColumns(2);
		txtFieldSkillDisableDevice = new JFormattedTextField(numberFormatter);
		txtFieldSkillDisableDevice.setColumns(2);
		txtFieldSkillDisguise = new JFormattedTextField(numberFormatter);
		txtFieldSkillDisguise.setColumns(2);
		txtFieldSkillEscapeArtist = new JFormattedTextField(numberFormatter);
		txtFieldSkillEscapeArtist.setColumns(2);
		txtFieldSkillFly = new JFormattedTextField(numberFormatter);
		txtFieldSkillFly.setColumns(2);
		txtFieldSkillHandleAnimal = new JFormattedTextField(numberFormatter);
		txtFieldSkillHandleAnimal.setColumns(2);
		txtFieldSkillHeal = new JFormattedTextField(numberFormatter);
		txtFieldSkillHeal.setColumns(2);
		txtFieldSkillIntimidate = new JFormattedTextField(numberFormatter);
		txtFieldSkillIntimidate.setColumns(2);
		txtFieldSkillKnowAll = new JFormattedTextField(numberFormatter);
		txtFieldSkillKnowAll.setColumns(2);
		txtFieldSkillKnowArcana = new JFormattedTextField(numberFormatter);
		txtFieldSkillKnowArcana.setColumns(2);
		txtFieldSkillKnowDungeoneering = new JFormattedTextField(numberFormatter);
		txtFieldSkillKnowDungeoneering.setColumns(2);
		txtFieldSkillKnowEngineering = new JFormattedTextField(numberFormatter);
		txtFieldSkillKnowEngineering.setColumns(2);
		txtFieldSkillKnowGeography = new JFormattedTextField(numberFormatter);
		txtFieldSkillKnowGeography.setColumns(2);
		txtFieldSkillKnowHistory = new JFormattedTextField(numberFormatter);
		txtFieldSkillKnowHistory.setColumns(2);
		txtFieldSkillKnowLocal = new JFormattedTextField(numberFormatter);
		txtFieldSkillKnowLocal.setColumns(2);
		txtFieldSkillKnowNature = new JFormattedTextField(numberFormatter);
		txtFieldSkillKnowNature.setColumns(2);
		txtFieldSkillKnowNobility = new JFormattedTextField(numberFormatter);
		txtFieldSkillKnowNobility.setColumns(2);
		txtFieldSkillKnowPlanes = new JFormattedTextField(numberFormatter);
		txtFieldSkillKnowPlanes.setColumns(2);
		txtFieldSkillKnowReligion = new JFormattedTextField(numberFormatter);
		txtFieldSkillKnowReligion.setColumns(2);
		txtFieldSkillLinguistics = new JFormattedTextField(numberFormatter);
		txtFieldSkillLinguistics.setColumns(2);
		txtFieldSkillPerception = new JFormattedTextField(numberFormatter);
		txtFieldSkillPerception.setColumns(2);
		txtFieldSkillPerform = new JFormattedTextField(numberFormatter);
		txtFieldSkillPerform.setColumns(2);
		txtFieldSkillProfession = new JFormattedTextField(numberFormatter);
		txtFieldSkillProfession.setColumns(2);
		txtFieldSkillRide = new JFormattedTextField(numberFormatter);
		txtFieldSkillRide.setColumns(2);
		txtFieldSkillSenseMotive = new JFormattedTextField(numberFormatter);
		txtFieldSkillSenseMotive.setColumns(2);
		txtFieldSkillSleightOfHand = new JFormattedTextField(numberFormatter);
		txtFieldSkillSleightOfHand.setColumns(2);
		txtFieldSkillSpellcraft = new JFormattedTextField(numberFormatter);
		txtFieldSkillSpellcraft.setColumns(2);
		txtFieldSkillStealth = new JFormattedTextField(numberFormatter);
		txtFieldSkillStealth.setColumns(2);
		txtFieldSkillSurvival = new JFormattedTextField(numberFormatter);
		txtFieldSkillSurvival.setColumns(2);
		txtFieldSkillSwim = new JFormattedTextField(numberFormatter);
		txtFieldSkillSwim.setColumns(2);
		txtFieldSkillUseMagicDevice = new JFormattedTextField(numberFormatter);
		txtFieldSkillUseMagicDevice.setColumns(2);
		txtFieldSkillAcrobaticsNotes = new JTextField(5);
		txtFieldSkillAppraiseNotes = new JTextField(5);
		txtFieldSkillBluffNotes = new JTextField(5);
		txtFieldSkillClimbNotes = new JTextField(5);
		txtFieldSkillCraftNotes = new JTextField(5);
		txtFieldSkillDiplomacyNotes = new JTextField(5);
		txtFieldSkillDisableDeviceNotes = new JTextField(5);
		txtFieldSkillDisguiseNotes = new JTextField(5);
		txtFieldSkillEscapeArtistNotes = new JTextField(5);
		txtFieldSkillFlyNotes = new JTextField(5);
		txtFieldSkillHandleAnimalNotes = new JTextField(5);
		txtFieldSkillHealNotes = new JTextField(5);
		txtFieldSkillIntimidateNotes = new JTextField(5);
		txtFieldSkillKnowAllNotes = new JTextField(5);
		txtFieldSkillKnowArcanaNotes = new JTextField(5);
		txtFieldSkillKnowDungeoneeringNotes = new JTextField(5);
		txtFieldSkillKnowEngineeringNotes = new JTextField(5);
		txtFieldSkillKnowGeographyNotes = new JTextField(5);
		txtFieldSkillKnowHistoryNotes = new JTextField(5);
		txtFieldSkillKnowLocalNotes = new JTextField(5);
		txtFieldSkillKnowNatureNotes = new JTextField(5);
		txtFieldSkillKnowNobilityNotes = new JTextField(5);
		txtFieldSkillKnowPlanesNotes = new JTextField(5);
		txtFieldSkillKnowReligionNotes = new JTextField(5);
		txtFieldSkillLinguisticsNotes = new JTextField(5);
		txtFieldSkillPerceptionNotes = new JTextField(5);
		txtFieldSkillPerformNotes = new JTextField(5);
		txtFieldSkillProfessionNotes = new JTextField(5);
		txtFieldSkillRideNotes = new JTextField(5);
		txtFieldSkillSenseMotiveNotes = new JTextField(5);
		txtFieldSkillSleightOfHandNotes = new JTextField(5);
		txtFieldSkillSpellcraftNotes = new JTextField(5);
		txtFieldSkillStealthNotes = new JTextField(5);
		txtFieldSkillSurvivalNotes = new JTextField(5);
		txtFieldSkillSwimNotes = new JTextField(5);
		txtFieldSkillUseMagicDeviceNotes = new JTextField(5);
		
		txtFieldSkillNotes = new JTextField(40);
		txtFieldSpeedLand = new JFormattedTextField(numberFormatter);
		txtFieldSpeedLand.setColumns(2);
		txtFieldSpeedBurrow = new JFormattedTextField(numberFormatter);
		txtFieldSpeedBurrow.setColumns(2);
		txtFieldSpeedClimb = new JFormattedTextField(numberFormatter);
		txtFieldSpeedClimb.setColumns(2);
		txtFieldSpeedFly = new JFormattedTextField(numberFormatter);
		txtFieldSpeedFly.setColumns(2);
		txtFieldSpeedFlyNotes = new JTextField(5);
		txtFieldSpeedSwim = new JTextField(2);
		txtFieldSpeedNotes = new JTextField(5);
		txtFieldSpace = new JTextField(2);
		txtFieldReach = new JTextField(2);
		txtFieldReachNotes = new JTextField(10);
		txtFieldMelee = new JTextField(40);
		txtFieldRanged = new JTextField(40);
		txtFieldSpecialAttacks = new JTextField(40);
		txtFieldFeats = new JTextField(40);
		txtFieldSq = new JTextField(40);
		txtAreaSpellLike = new JTextArea(5, 40);
		txtAreaSpellsKnown = new JTextArea(5, 40);
		txtAreaSpellsPrepared = new JTextArea(5, 40);
		txtAreaSpecialAbilities = new JTextArea(5, 40);
	}

	private void setPositionAndSize()
	{
		mainUI.setBounds(200, 100, 1025, 800);
		scrollTxtAreaMonster.setBounds(0,20, 400, 705);
		btnParse.setBounds(400-150-5, 725, 150, 30);
		panelCreature.setBounds(400, 20, 610, 741);
		panelDiceRoll.setBounds(400, 20, 610, 741);
		lblDie.setBounds(270,354,64,64);
		panelDiceRoll.add(lblDie);
		
		pnlNotify.setBounds(0,0,1025,20);
	}

	private void setLayouts()
	{
		panelCreature.setLayout(new BorderLayout());
		panelDiceRoll.setLayout(null);
		
		JPanel pnlNorth = new JPanel();
		pnlNorth.setLayout(new BoxLayout(pnlNorth, BoxLayout.Y_AXIS));

		JPanel pnlCore = new JPanel();
		pnlCore.setLayout(new BoxLayout(pnlCore, BoxLayout.Y_AXIS));
		pnlCore.setBorder(BorderFactory.createTitledBorder("Core"));

		JPanel pnlNameLevel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pnlNameLevel.add(lblName);
		pnlNameLevel.add(txtFieldName);
		pnlNameLevel.add(lblXp);
		pnlNameLevel.add(txtFieldXp);
		pnlNameLevel.add(lblCr);
		pnlNameLevel.add(txtFieldCr);
		pnlCore.add(pnlNameLevel);

		JPanel pnlAlignSizeType = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pnlAlignSizeType.add(lblInit);
		pnlAlignSizeType.add(txtFieldInit);
		pnlAlignSizeType.add(lblAlign);
		pnlAlignSizeType.add(txtFieldAlign);
		pnlAlignSizeType.add(lblSize);
		pnlAlignSizeType.add(txtFieldSize);
		pnlAlignSizeType.add(lblType);
		pnlAlignSizeType.add(txtFieldType);
		pnlCore.add(pnlAlignSizeType);

		JPanel pnlSenseAura = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pnlSenseAura.add(lblSenses);
		pnlSenseAura.add(txtFieldSenses);
		pnlSenseAura.add(lblAura);
		pnlSenseAura.add(txtFieldAura);
		pnlCore.add(pnlSenseAura);

		JPanel pnlDefenses = new JPanel();
		pnlDefenses.setLayout(new BoxLayout(pnlDefenses, BoxLayout.Y_AXIS));
		pnlDefenses.setBorder(BorderFactory.createTitledBorder("Defenses"));

		JPanel pnlArmor = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pnlArmor.add(lblAc);
		pnlArmor.add(txtFieldAc);
		pnlArmor.add(lblAcTouch);
		pnlArmor.add(txtFieldAcTouch);
		pnlArmor.add(lblAcFlatFoot);
		pnlArmor.add(txtFieldAcFlatFoot);
		pnlArmor.add(lblAcNotes);
		pnlArmor.add(txtFieldAcNotes);

		JPanel pnlHealth = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pnlHealth.add(lblHp);
		pnlHealth.add(txtFieldHp);
		pnlHealth.add(new JLabel(" ("));
		pnlHealth.add(txtFieldHd);
		pnlHealth.add(new JLabel("d"));
		pnlHealth.add(txtFieldHitDie);
		pnlHealth.add(new JLabel("+"));
		pnlHealth.add(txtFieldHpMod);
		pnlHealth.add(new JLabel(") "));
		pnlHealth.add(lblHpNotes);
		pnlHealth.add(txtFieldHpNotes);

		JPanel pnlSaves = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pnlSaves.add(lblSaveFort);
		pnlSaves.add(txtFieldSaveFort);
		pnlSaves.add(lblSaveRef);
		pnlSaves.add(txtFieldSaveRef);
		pnlSaves.add(lblSaveWill);
		pnlSaves.add(txtFieldSaveWill);

		JPanel pnlDefAbilities1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pnlDefAbilities1.add(lblDefDr);
		pnlDefAbilities1.add(txtFieldDefDr);
		pnlDefAbilities1.add(lblDefSpellResits);
		pnlDefAbilities1.add(txtFieldDefSpellResits);

		JPanel pnlDefAbilities2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pnlDefAbilities2.add(lblDefImmune);
		pnlDefAbilities2.add(txtFieldDefImmune);
		pnlDefAbilities2.add(lblDefResist);
		pnlDefAbilities2.add(txtFieldDefResist);

		JPanel pnlDefAbilities3 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pnlDefAbilities3.add(lblDefWeakness);
		pnlDefAbilities3.add(txtFieldDefWeakness);
		pnlDefAbilities3.add(lblDefAbilities);
		pnlDefAbilities3.add(txtFieldDefAbilities);

		pnlDefenses.add(pnlArmor);
		pnlDefenses.add(pnlDefAbilities1);
		pnlDefenses.add(pnlHealth);
		pnlDefenses.add(pnlSaves);
		pnlDefenses.add(pnlDefAbilities2);
		pnlDefenses.add(pnlDefAbilities3);

		JPanel pnlOffense = new JPanel();
		pnlOffense.setLayout(new BoxLayout(pnlOffense, BoxLayout.Y_AXIS));
		pnlOffense.setBorder(BorderFactory.createTitledBorder("Offenses"));
		JPanel pnlSpeed = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pnlSpeed.add(lblSpeedLand);
		pnlSpeed.add(txtFieldSpeedLand);
		pnlSpeed.add(lblSpeedBurrow);
		pnlSpeed.add(txtFieldSpeedBurrow);
		pnlSpeed.add(lblSpeedClimb);
		pnlSpeed.add(txtFieldSpeedClimb);
		pnlSpeed.add(lblSpeedFly);
		pnlSpeed.add(txtFieldSpeedFly);
		pnlSpeed.add(new JLabel("("));
		pnlSpeed.add(txtFieldSpeedFlyNotes);
		pnlSpeed.add(new JLabel(")"));
		pnlSpeed.add(lblSpeedSwim);
		pnlSpeed.add(txtFieldSpeedSwim);
		pnlSpeed.add(lblSpeedNotes);
		pnlSpeed.add(txtFieldSpeedNotes);
		pnlOffense.add(pnlSpeed);

		JPanel pnlSpaceReach = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pnlSpaceReach.add(lblSpace);
		pnlSpaceReach.add(txtFieldSpace);
		pnlSpaceReach.add(lblReach);
		pnlSpaceReach.add(txtFieldReach);
		pnlSpaceReach.add(lblReachNotes);
		pnlSpaceReach.add(txtFieldReachNotes);
		pnlOffense.add(pnlSpaceReach);

		JPanel pnlOffense1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pnlOffense1.add(lblMelee);
		pnlOffense1.add(txtFieldMelee);
		pnlOffense.add(pnlOffense1);

		JPanel pnlOffense2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pnlOffense2.add(lblRanged);
		pnlOffense2.add(txtFieldRanged);
		pnlOffense.add(pnlOffense2);

		JPanel pnlOffense3 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pnlOffense3.add(lblSpecialAttacks);
		pnlOffense3.add(txtFieldSpecialAttacks);
		pnlOffense.add(pnlOffense3);		
		
		JPanel pnlAbilities = new JPanel();
		pnlAbilities.setLayout(new BoxLayout(pnlAbilities, BoxLayout.Y_AXIS));
		pnlAbilities.setBorder(BorderFactory.createTitledBorder("Abilities"));
		
		JPanel pnlSpellLike = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pnlSpellLike.add(lblSpellLike);		
		JScrollPane scrollSpellLike = new JScrollPane(txtAreaSpellLike);
		pnlSpellLike.add(scrollSpellLike);
		pnlAbilities.add(pnlSpellLike);
		
		JPanel pnlSpellLKnown = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pnlSpellLKnown.add(lblSpellsKnown);
		JScrollPane scrollSpellsKnown = new JScrollPane(txtAreaSpellsKnown);
		pnlSpellLKnown.add(scrollSpellsKnown);
		pnlAbilities.add(pnlSpellLKnown);
		
		JPanel pnlSpellPrep = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pnlSpellPrep.add(lblSpellsPrepared);
		JScrollPane scrollSpellsPrep = new JScrollPane(txtAreaSpellsPrepared);
		pnlSpellPrep.add(scrollSpellsPrep);
		pnlAbilities.add(pnlSpellPrep);
		
		JPanel pnlSpecialAbilities = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pnlSpecialAbilities.add(lblSpecialAbilities);
		JScrollPane scrollSpecialAbilities = new JScrollPane(txtAreaSpecialAbilities);
		pnlSpecialAbilities.add(scrollSpecialAbilities);
		pnlAbilities.add(pnlSpecialAbilities);
		
		JPanel pnlStatistics = new JPanel();
		pnlStatistics.setLayout(new BoxLayout(pnlStatistics, BoxLayout.Y_AXIS));
		pnlStatistics.setBorder(BorderFactory.createTitledBorder("Stats"));
		JPanel pnlAbilityScores = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pnlAbilityScores.add(lblStatStr);
		pnlAbilityScores.add(txtFieldStatStr);
		pnlAbilityScores.add(lblStatDex);
		pnlAbilityScores.add(txtFieldStatDex);
		pnlAbilityScores.add(lblStatCon);
		pnlAbilityScores.add(txtFieldStatCon);
		pnlAbilityScores.add(lblStatInt);
		pnlAbilityScores.add(txtFieldStatInt);
		pnlAbilityScores.add(lblStatWis);
		pnlAbilityScores.add(txtFieldStatWis);
		pnlAbilityScores.add(lblStatCha);
		pnlAbilityScores.add(txtFieldStatCha);

		JPanel pnlBabCmbCmd = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pnlBabCmbCmd.add(lblStatBab);
		pnlBabCmbCmd.add(txtFieldStatBab);
		pnlBabCmbCmd.add(lblStatCmb);
		pnlBabCmbCmd.add(txtFieldStatCmb);
		pnlBabCmbCmd.add(lblStatCmbNotes);
		pnlBabCmbCmd.add(txtFieldStatCmbNotes);
		pnlBabCmbCmd.add(lblStatCmd);
		pnlBabCmbCmd.add(txtFieldStatCmd);
		pnlBabCmbCmd.add(lblStatCmdNotes);
		pnlBabCmbCmd.add(txtFieldStatCmdNotes);
		
		JPanel pnlFeats = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pnlFeats.add(lblFeats);
		pnlFeats.add(txtFieldFeats);
		
		JPanel pnlSq = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pnlSq.add(lblSq);
		pnlSq.add(txtFieldSq);
		
		pnlStatistics.add(pnlAbilityScores);
		pnlStatistics.add(pnlBabCmbCmd);
		pnlStatistics.add(pnlFeats);
		pnlStatistics.add(pnlSq);
		
		JPanel pnlSkills = new JPanel();
		pnlSkills.setLayout(new BoxLayout(pnlSkills, BoxLayout.Y_AXIS));
		pnlSkills.setBorder(BorderFactory.createTitledBorder("Skills"));
		JPanel pnlSkill1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pnlSkill1.add(lblSkillAcrobatics);
		pnlSkill1.add(txtFieldSkillAcrobatics);
		pnlSkill1.add(txtFieldSkillAcrobaticsNotes);
		pnlSkill1.add(lblSkillAppraise);
		pnlSkill1.add(txtFieldSkillAppraise);
		pnlSkill1.add(txtFieldSkillAppraiseNotes);
		pnlSkill1.add(lblSkillBluff);
		pnlSkill1.add(txtFieldSkillBluff);
		pnlSkill1.add(txtFieldSkillBluffNotes);
		JPanel pnlSkill1b = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pnlSkill1b.add(lblSkillClimb);
		pnlSkill1b.add(txtFieldSkillClimb);
		pnlSkill1b.add(txtFieldSkillClimbNotes);
		pnlSkill1b.add(lblSkillCraft);
		pnlSkill1b.add(txtFieldSkillCraft);
		pnlSkill1b.add(txtFieldSkillCraftNotes);
		pnlSkill1b.add(lblSkillDiplomacy);
		pnlSkill1b.add(txtFieldSkillDiplomacy);
		pnlSkill1b.add(txtFieldSkillDiplomacyNotes);
		JPanel pnlSkill2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pnlSkill2.add(lblSkillDisableDevice);
		pnlSkill2.add(txtFieldSkillDisableDevice);
		pnlSkill2.add(txtFieldSkillDisableDeviceNotes);
		pnlSkill2.add(lblSkillDisguise);
		pnlSkill2.add(txtFieldSkillDisguise);
		pnlSkill2.add(txtFieldSkillDisguiseNotes);
		pnlSkill2.add(lblSkillEscapeArtist);
		pnlSkill2.add(txtFieldSkillEscapeArtist);
		pnlSkill2.add(txtFieldSkillEscapeArtistNotes);
		JPanel pnlSkill2b = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pnlSkill2b.add(lblSkillFly);
		pnlSkill2b.add(txtFieldSkillFly);
		pnlSkill2b.add(txtFieldSkillFlyNotes);
		pnlSkill2b.add(lblSkillHandleAnimal);
		pnlSkill2b.add(txtFieldSkillHandleAnimal);
		pnlSkill2b.add(txtFieldSkillHandleAnimalNotes);
		pnlSkill2b.add(lblSkillHeal);
		pnlSkill2b.add(txtFieldSkillHeal);
		pnlSkill2b.add(txtFieldSkillHealNotes);
		JPanel pnlSkill3 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pnlSkill3.add(lblSkillIntimidate);
		pnlSkill3.add(txtFieldSkillIntimidate);
		pnlSkill3.add(txtFieldSkillIntimidateNotes);
		pnlSkill3.add(lblSkillKnowAll);
		pnlSkill3.add(txtFieldSkillKnowAll);
		pnlSkill3.add(txtFieldSkillKnowAllNotes);
		pnlSkill3.add(lblSkillKnowArcana);
		pnlSkill3.add(txtFieldSkillKnowArcana);
		pnlSkill3.add(txtFieldSkillKnowArcanaNotes);
		JPanel pnlSkill3b = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pnlSkill3b.add(lblSkillKnowDungeoneering);
		pnlSkill3b.add(txtFieldSkillKnowDungeoneering);
		pnlSkill3b.add(txtFieldSkillKnowDungeoneeringNotes);
		pnlSkill3b.add(lblSkillKnowEngineering);
		pnlSkill3b.add(txtFieldSkillKnowEngineering);
		pnlSkill3b.add(txtFieldSkillKnowEngineeringNotes);
		pnlSkill3b.add(lblSkillKnowGeography);
		pnlSkill3b.add(txtFieldSkillKnowGeography);
		pnlSkill3b.add(txtFieldSkillKnowGeographyNotes);
		JPanel pnlSkill4 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pnlSkill4.add(lblSkillKnowHistory);
		pnlSkill4.add(txtFieldSkillKnowHistory);
		pnlSkill4.add(txtFieldSkillKnowHistoryNotes);
		pnlSkill4.add(lblSkillKnowLocal);
		pnlSkill4.add(txtFieldSkillKnowLocal);
		pnlSkill4.add(txtFieldSkillKnowLocalNotes);
		pnlSkill4.add(lblSkillKnowNature);
		pnlSkill4.add(txtFieldSkillKnowNature);
		pnlSkill4.add(txtFieldSkillKnowNatureNotes);
		JPanel pnlSkill4b = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pnlSkill4b.add(lblSkillKnowNobility);
		pnlSkill4b.add(txtFieldSkillKnowNobility);
		pnlSkill4b.add(txtFieldSkillKnowNobilityNotes);
		pnlSkill4b.add(lblSkillKnowPlanes);
		pnlSkill4b.add(txtFieldSkillKnowPlanes);
		pnlSkill4b.add(txtFieldSkillKnowPlanesNotes);
		pnlSkill4b.add(lblSkillKnowReligion);
		pnlSkill4b.add(txtFieldSkillKnowReligion);
		pnlSkill4b.add(txtFieldSkillKnowReligionNotes);
		JPanel pnlSkill5 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pnlSkill5.add(lblSkillLinguistics);
		pnlSkill5.add(txtFieldSkillLinguistics);
		pnlSkill5.add(txtFieldSkillLinguisticsNotes);
		pnlSkill5.add(lblSkillPerception);
		pnlSkill5.add(txtFieldSkillPerception);
		pnlSkill5.add(txtFieldSkillPerceptionNotes);
		pnlSkill5.add(lblSkillPerform);
		pnlSkill5.add(txtFieldSkillPerform);
		pnlSkill5.add(txtFieldSkillPerformNotes);
		JPanel pnlSkill5b = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pnlSkill5b.add(lblSkillProfession);
		pnlSkill5b.add(txtFieldSkillProfession);
		pnlSkill5b.add(txtFieldSkillProfessionNotes);
		pnlSkill5b.add(lblSkillRide);
		pnlSkill5b.add(txtFieldSkillRide);
		pnlSkill5b.add(txtFieldSkillRideNotes);
		pnlSkill5b.add(lblSkillSenseMotive);
		pnlSkill5b.add(txtFieldSkillSenseMotive);
		pnlSkill5b.add(txtFieldSkillSenseMotiveNotes);
		JPanel pnlSkill6 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pnlSkill6.add(lblSkillSleightOfHand);
		pnlSkill6.add(txtFieldSkillSleightOfHand);
		pnlSkill6.add(txtFieldSkillSleightOfHandNotes);
		pnlSkill6.add(lblSkillSpellcraft);
		pnlSkill6.add(txtFieldSkillSpellcraft);
		pnlSkill6.add(txtFieldSkillSpellcraftNotes);
		pnlSkill6.add(lblSkillStealth);
		pnlSkill6.add(txtFieldSkillStealth);
		pnlSkill6.add(txtFieldSkillStealthNotes);
		JPanel pnlSkill6b = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pnlSkill6b.add(lblSkillSurvival);
		pnlSkill6b.add(txtFieldSkillSurvival);
		pnlSkill6b.add(txtFieldSkillSurvivalNotes);
		pnlSkill6b.add(lblSkillSwim);
		pnlSkill6b.add(txtFieldSkillSwim);
		pnlSkill6b.add(txtFieldSkillSwimNotes);
		pnlSkill6b.add(lblSkillUseMagicDevice);
		pnlSkill6b.add(txtFieldSkillUseMagicDevice);
		pnlSkill6b.add(txtFieldSkillUseMagicDeviceNotes);
		JPanel pnlSkill7 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pnlSkill7.add(lblSkillNotes);
		pnlSkill7.add(txtFieldSkillNotes);

		pnlSkills.add(pnlSkill1);
		pnlSkills.add(pnlSkill1b);
		pnlSkills.add(pnlSkill2);
		pnlSkills.add(pnlSkill2b);
		pnlSkills.add(pnlSkill3);
		pnlSkills.add(pnlSkill3b);
		pnlSkills.add(pnlSkill4);
		pnlSkills.add(pnlSkill4b);
		pnlSkills.add(pnlSkill5);
		pnlSkills.add(pnlSkill5b);
		pnlSkills.add(pnlSkill6);
		pnlSkills.add(pnlSkill6b);
		pnlSkills.add(pnlSkill7);

		pnlNorth.add(pnlCore);
		pnlNorth.add(pnlDefenses);
		pnlNorth.add(pnlOffense);
		pnlNorth.add(pnlStatistics);
		pnlNorth.add(pnlAbilities);		
		pnlNorth.add(pnlSkills);

		scroll = new JScrollPane(pnlNorth);
		scroll.getVerticalScrollBar().setUnitIncrement(16);
		JPanel pnlButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		pnlButtonPanel.add(btnPaste);

		panelCreature.add(scroll, BorderLayout.CENTER);
		panelCreature.add(pnlButtonPanel, BorderLayout.SOUTH);
	}

	private void setBorders()
	{
		scrollTxtAreaMonster.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5), BorderFactory.createEtchedBorder()));
		txtAreaMonster.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
	}

	private void addComponents()
	{
		mainUI.add(pnlNotify);
		mainUI.add(scrollTxtAreaMonster);
		mainUI.add(btnParse);
		mainUI.add(panelCreature);
		mainUI.add(panelDiceRoll);
		
	}

	private void addEvents()
	{
		ArrayList<JTextField> requiredTextFields = new ArrayList<>();
		requiredTextFields.add(txtFieldAc);
		requiredTextFields.add(txtFieldAcFlatFoot);
		requiredTextFields.add(txtFieldAcTouch);
		requiredTextFields.add(txtFieldHp);
		requiredTextFields.add(txtFieldInit);
		requiredTextFields.add(txtFieldName);
		requiredTextFields.add(txtFieldSaveFort);
		requiredTextFields.add(txtFieldSaveRef);
		requiredTextFields.add(txtFieldSaveWill);
		requiredTextFields.add(txtFieldStatBab);
		requiredTextFields.add(txtFieldStatStr);
		requiredTextFields.add(txtFieldStatDex);
		requiredTextFields.add(txtFieldStatCon);
		requiredTextFields.add(txtFieldStatWis);
		requiredTextFields.add(txtFieldStatInt);
		requiredTextFields.add(txtFieldStatCha);
		
		requiredTextFields.forEach(jTxtField -> {
			jTxtField.setBackground(Color.YELLOW);
			jTxtField.getDocument().addDocumentListener(new EmptyDocumentListener(jTxtField));
		});
			
		btnParse.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				panelCreature.show();
				panelDiceRoll.hide();
				String creatureText = txtAreaMonster.getText(); 
				creatureText = TextParser.preFormatTextForPdfFixes(creatureText);
				txtAreaMonster.setText(creatureText);
				clearTextFieldsExceptForCreatureTextArea();
				boolean isValid = TextParser.isValidCreatureText(creatureText);
				if (isValid)
				{
					try
					{
						creature = TextParser.parse(creatureText);
						System.out.println("****Creature parsed\r\n"+creature.toString());
						loadCreatureData();
					}
					catch (Exception err)
					{
						err.printStackTrace();
					}

				}
				else
				{
					JOptionPane.showMessageDialog(mainUI, "Invalid text: NPC text needs to have {name} CR {level}.");
				}
			}
		});

		btnPaste.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
//				JLabel btnPfCommunity = new JLabel(iconPfCommunitySheet);
//				JLabel btnPfByRoll20 = new JLabel(iconPfByRoll20);							
//				Object[] options = {btnPfCommunity, btnPfByRoll20};
				Object[] options = {"Pathfinder Community Sheet", "Pathfinder by Roll20"};
				
				int pfSheetTypeReturn = JOptionPane.showOptionDialog(mainUI, "Please select which Pathfinder Sheet you are using.","Select a Pathfinder Sheet", JOptionPane.YES_NO_OPTION, -1, null, options, options[0]);
				if (pfSheetTypeReturn == -1)
				{
					//do nothing
				}
				else
				{
					try
					{
						mainUI.setState(Frame.ICONIFIED);
						setCreatureDataFromUI();
						Thread thread = new Thread(() -> {
							try
							{
								log.error("Starting thread to parse data");
								if (pfSheetTypeReturn == 0)
									CreatureRobot.pasteCreatureDataCopyPasta(creature, true);
								else
									CreatureRobot.pasteCreatureDataCopyPasta(creature, false);
							}
							catch (Exception err)
							{
								log.error(err);
							}
						});
						
						thread.start();
					}
					catch (IllegalStateException ise)
					{
						log.error(ise);
					}
				}
			}
		});
					
	}
	
	private void setCreatureDataFromUI()
	{
		creature.setName(txtFieldName.getText());
		creature.setXp(getInt(txtFieldXp.getText()));
		creature.setLevel(getInt(txtFieldCr.getText()));
		creature.setAlignment(txtFieldAlign.getText());
		creature.setSize(txtFieldSize.getText());
		creature.setType(txtFieldType.getText());

		creature.setInitiativeMod(getInt(txtFieldInit.getText()));
		creature.setSenses(txtFieldSenses.getText());
		creature.setAura(txtFieldAura.getText());

		creature.defenses.setAc(getInt(txtFieldAc.getText()));
		creature.defenses.setAcTouch(getInt(txtFieldAcTouch.getText()));
		creature.defenses.setAcFlatFooted(getInt(txtFieldAcFlatFoot.getText()));
		creature.defenses.setAcNotes(txtFieldAcNotes.getText());

		creature.defenses.setHp(getInt(txtFieldHp.getText()));
		creature.defenses.setHd(getInt(txtFieldHd.getText()));
		creature.defenses.setHitDie(getInt(txtFieldHitDie.getText()));
		creature.defenses.setHpMod(txtFieldHpMod.getText());
		creature.defenses.setHpNotes(txtFieldHpNotes.getText());

		creature.defenses.setSaveFort(getInt(txtFieldSaveFort.getText()));
		creature.defenses.setSaveReflex(getInt(txtFieldSaveRef.getText()));
		creature.defenses.setSaveWill(getInt(txtFieldSaveWill.getText()));

		creature.defenses.setDamageReduction(txtFieldDefDr.getText());
		creature.defenses.setImmune(txtFieldDefImmune.getText());
		creature.defenses.setResist(txtFieldDefResist.getText());
		creature.defenses.setSpellResist(getInt(txtFieldDefSpellResits.getText()));
		creature.defenses.setWeakness(txtFieldDefWeakness.getText());
		creature.defenses.setDefensiveAbilities(txtFieldDefAbilities.getText());

		creature.offenses.setSpeedLand(getInt(txtFieldSpeedLand.getText()));
		creature.offenses.setSpeedBurrow(getInt(txtFieldSpeedBurrow.getText()));
		creature.offenses.setSpeedClimb(getInt(txtFieldSpeedClimb.getText()));
		creature.offenses.setSpeedFly(getInt(txtFieldSpeedFly.getText()));
		creature.offenses.setSpeedFlyNotes(txtFieldSpeedFlyNotes.getText());
		creature.offenses.setSpeedSwim(getInt(txtFieldSpeedSwim.getText()));
		creature.offenses.setSpeedNotes(txtFieldSpeedNotes.getText());
		creature.offenses.setSpace(getInt(txtFieldSpace.getText()));
		creature.offenses.setReach(getInt(txtFieldReach.getText()));
		creature.offenses.setReachNotes(txtFieldReachNotes.getText());
		creature.offenses.setMelee(txtFieldMelee.getText());
		creature.offenses.setRange(txtFieldRanged.getText());
		creature.offenses.setSpecialAttacks(txtFieldSpecialAttacks.getText());

		creature.setSpellLike(txtAreaSpellLike.getText());
		creature.setSpellsKnown(txtAreaSpellsKnown.getText());
		creature.setSpellsPrepared(txtAreaSpellsPrepared.getText());
		creature.setSpecialAbilities(txtAreaSpecialAbilities.getText());

		creature.stats.setAbilityStr(getInt(txtFieldStatStr.getText()));
		creature.stats.setAbilityDex(getInt(txtFieldStatDex.getText()));
		creature.stats.setAbilityCon(getInt(txtFieldStatCon.getText()));
		creature.stats.setAbilityInt(getInt(txtFieldStatInt.getText()));
		creature.stats.setAbilityWis(getInt(txtFieldStatWis.getText()));
		creature.stats.setAbilityCha(getInt(txtFieldStatCha.getText()));
		creature.stats.setBab(getInt(txtFieldStatBab.getText()));
		creature.stats.setCmb(getInt(txtFieldStatCmb.getText()));
		creature.stats.setCmbNotes(txtFieldStatCmbNotes.getText());
		creature.stats.setCmd(getInt(txtFieldStatCmd.getText()));
		creature.stats.setCmdNotes(txtFieldStatCmdNotes.getText());
		creature.setFeats(txtFieldFeats.getText());
		creature.setSpecialQualities(txtFieldSq.getText());

		creature.skills.setAcrobaticsMod(getInt(txtFieldSkillAcrobatics.getText()));
		creature.skills.setAppraiseMod(getInt(txtFieldSkillAppraise.getText()));
		creature.skills.setBluffMod(getInt(txtFieldSkillBluff.getText()));
		creature.skills.setClimbMod(getInt(txtFieldSkillClimb.getText()));
		creature.skills.setCraftMod(getInt(txtFieldSkillCraft.getText()));
		creature.skills.setDiplomacyMod(getInt(txtFieldSkillDiplomacy.getText()));
		creature.skills.setDisableDeviceMod(getInt(txtFieldSkillDisableDevice.getText()));
		creature.skills.setDisguiseMod(getInt(txtFieldSkillDisguise.getText()));
		creature.skills.setEscapeArtistMod(getInt(txtFieldSkillEscapeArtist.getText()));
		creature.skills.setFlyMod(getInt(txtFieldSkillFly.getText()));
		creature.skills.setHandleAnimalMod(getInt(txtFieldSkillHandleAnimal.getText()));
		creature.skills.setHealMod(getInt(txtFieldSkillHeal.getText()));
		creature.skills.setIntimidateMod(getInt(txtFieldSkillIntimidate.getText()));
		creature.skills.setKnowAllMod(getInt(txtFieldSkillKnowAll.getText()));
		creature.skills.setKnowArcanaMod(getInt(txtFieldSkillKnowArcana.getText()));
		creature.skills.setKnowDungeoneeringMod(getInt(txtFieldSkillKnowDungeoneering.getText()));
		creature.skills.setKnowEngineeringMod(getInt(txtFieldSkillKnowEngineering.getText()));
		creature.skills.setKnowGeographyMod(getInt(txtFieldSkillKnowGeography.getText()));
		creature.skills.setKnowHistoryMod(getInt(txtFieldSkillKnowHistory.getText()));
		creature.skills.setKnowLocalMod(getInt(txtFieldSkillKnowLocal.getText()));
		creature.skills.setKnowNatureMod(getInt(txtFieldSkillKnowNature.getText()));
		creature.skills.setKnowNobilityMod(getInt(txtFieldSkillKnowNobility.getText()));
		creature.skills.setKnowPlanesMod(getInt(txtFieldSkillKnowPlanes.getText()));
		creature.skills.setKnowReligionMod(getInt(txtFieldSkillKnowReligion.getText()));
		creature.skills.setLinguisticsMod(getInt(txtFieldSkillLinguistics.getText()));
		creature.skills.setPerceptionMod(getInt(txtFieldSkillPerception.getText()));
		creature.skills.setPerformMod(getInt(txtFieldSkillPerform.getText()));
		creature.skills.setProfessionMod(getInt(txtFieldSkillProfession.getText()));
		creature.skills.setRideMod(getInt(txtFieldSkillRide.getText()));
		creature.skills.setSenseMotiveMod(getInt(txtFieldSkillSenseMotive.getText()));
		creature.skills.setSleightOfHandMod(getInt(txtFieldSkillSleightOfHand.getText()));
		creature.skills.setSpellcraftMod(getInt(txtFieldSkillSpellcraft.getText()));
		creature.skills.setStealthMod(getInt(txtFieldSkillStealth.getText()));
		creature.skills.setSurvivalMod(getInt(txtFieldSkillSurvival.getText()));
		creature.skills.setSwimMod(getInt(txtFieldSkillSwim.getText()));
		creature.skills.setUseMagicDeviceMod(getInt(txtFieldSkillUseMagicDevice.getText()));

		creature.skills.setAcrobaticsNotes(txtFieldSkillAcrobaticsNotes.getText());
		creature.skills.setAppraiseNotes(txtFieldSkillAppraiseNotes.getText());
		creature.skills.setBluffNotes(txtFieldSkillBluffNotes.getText());
		creature.skills.setClimbNotes(txtFieldSkillClimbNotes.getText());
		creature.skills.setCraftNotes(txtFieldSkillCraftNotes.getText());
		creature.skills.setDiplomacyNotes(txtFieldSkillDiplomacyNotes.getText());
		creature.skills.setDisableDeviceNotes(txtFieldSkillDisableDeviceNotes.getText());
		creature.skills.setDisguiseNotes(txtFieldSkillDisguiseNotes.getText());
		creature.skills.setEscapeArtistNotes(txtFieldSkillEscapeArtistNotes.getText());
		creature.skills.setFlyNotes(txtFieldSkillFlyNotes.getText());
		creature.skills.setHandleAnimalNotes(txtFieldSkillHandleAnimalNotes.getText());
		creature.skills.setHealNotes(txtFieldSkillHealNotes.getText());
		creature.skills.setIntimidateNotes(txtFieldSkillIntimidateNotes.getText());
		creature.skills.setKnowAllNotes(txtFieldSkillKnowAllNotes.getText());
		creature.skills.setKnowArcanaNotes(txtFieldSkillKnowArcanaNotes.getText());
		creature.skills.setKnowDungeoneeringNotes(txtFieldSkillKnowDungeoneeringNotes.getText());
		creature.skills.setKnowEngineeringNotes(txtFieldSkillKnowEngineeringNotes.getText());
		creature.skills.setKnowGeographyNotes(txtFieldSkillKnowGeographyNotes.getText());
		creature.skills.setKnowHistoryNotes(txtFieldSkillKnowHistoryNotes.getText());
		creature.skills.setKnowLocalNotes(txtFieldSkillKnowLocalNotes.getText());
		creature.skills.setKnowNatureNotes(txtFieldSkillKnowNatureNotes.getText());
		creature.skills.setKnowNobilityNotes(txtFieldSkillKnowNobilityNotes.getText());
		creature.skills.setKnowPlanesNotes(txtFieldSkillKnowPlanesNotes.getText());
		creature.skills.setKnowReligionNotes(txtFieldSkillKnowReligionNotes.getText());
		creature.skills.setLinguisticsNotes(txtFieldSkillLinguisticsNotes.getText());
		creature.skills.setPerceptionNotes(txtFieldSkillPerceptionNotes.getText());
		creature.skills.setPerformNotes(txtFieldSkillPerformNotes.getText());
		creature.skills.setProfessionNotes(txtFieldSkillProfessionNotes.getText());
		creature.skills.setRideNotes(txtFieldSkillRideNotes.getText());
		creature.skills.setSenseMotiveNotes(txtFieldSkillSenseMotiveNotes.getText());
		creature.skills.setSleightOfHandNotes(txtFieldSkillSleightOfHandNotes.getText());
		creature.skills.setSpellcraftNotes(txtFieldSkillSpellcraftNotes.getText());
		creature.skills.setStealthNotes(txtFieldSkillStealthNotes.getText());
		creature.skills.setSurvivalNotes(txtFieldSkillSurvivalNotes.getText());
		creature.skills.setSwimNotes(txtFieldSkillSwimNotes.getText());
		creature.skills.setUseMagicDeviceNotes(txtFieldSkillUseMagicDeviceNotes.getText());
		creature.skills.setNotes(txtFieldSkillNotes.getText());
	}

	private void loadCreatureData()
	{
		Creature c = creature;
		txtFieldName.setText(c.getName());
		txtFieldXp.setText(getInt(c.getXp()));
		txtFieldCr.setText(getInt(c.getLevel()));
		txtFieldAlign.setText(c.getAlignment());
		txtFieldSize.setText(c.getSize());
		txtFieldType.setText(c.getType());

		txtFieldInit.setText(getInt(c.getInitiativeMod()));
		txtFieldSenses.setText(c.getSenses());
		txtFieldAura.setText(c.getAura());

		txtFieldAc.setText(getInt(c.defenses.getAc()));
		txtFieldAcTouch.setText(getInt(c.defenses.getAcTouch()));
		txtFieldAcFlatFoot.setText(getInt(c.defenses.getAcFlatFooted()));
		txtFieldAcNotes.setText(c.defenses.getAcNotes());

		txtFieldHp.setText(getInt(c.defenses.getHp()));
		txtFieldHd.setText(getInt(c.defenses.getHd()));
		txtFieldHitDie.setText(getInt(c.defenses.getHitDie()));
		txtFieldHpMod.setText(c.defenses.getHpMod());
		txtFieldHpNotes.setText(c.defenses.getHpNotes());

		txtFieldSaveFort.setText(getInt(c.defenses.getSaveFort()));
		txtFieldSaveRef.setText(getInt(c.defenses.getSaveReflex()));
		txtFieldSaveWill.setText(getInt(c.defenses.getSaveWill()));

		txtFieldDefDr.setText(c.defenses.getDamageReduction());
		txtFieldDefImmune.setText(c.defenses.getImmune());
		txtFieldDefResist.setText(c.defenses.getResist());
		txtFieldDefSpellResits.setText(getInt(c.defenses.getSpellResist()));
		txtFieldDefWeakness.setText(c.defenses.getWeakness());
		txtFieldDefAbilities.setText(c.defenses.getDefensiveAbilities());

		txtFieldSpeedLand.setText(getInt(c.offenses.getSpeedLand()));
		txtFieldSpeedBurrow.setText(getInt(c.offenses.getSpeedBurrow()));
		txtFieldSpeedClimb.setText(getInt(c.offenses.getSpeedClimb()));
		txtFieldSpeedFly.setText(getInt(c.offenses.getSpeedFly()));
		txtFieldSpeedFlyNotes.setText(c.offenses.getSpeedFlyNotes());
		txtFieldSpeedSwim.setText(getInt(c.offenses.getSpeedSwim()));
		txtFieldSpeedNotes.setText(c.offenses.getSpeedNotes());
		txtFieldSpace.setText(getInt(c.offenses.getSpace()));
		txtFieldReach.setText(getInt(c.offenses.getReach()));
		txtFieldReachNotes.setText(c.offenses.getReachNotes());
		txtFieldMelee.setText(c.offenses.getMelee());
		txtFieldRanged.setText(c.offenses.getRange());
		txtFieldSpecialAttacks.setText(c.offenses.getSpecialAttacks());

		txtAreaSpellLike.setText(c.getSpellLike());
		txtAreaSpellsKnown.setText(c.getSpellsKnown());
		txtAreaSpellsPrepared.setText(c.getSpellsPrepared());
		txtAreaSpecialAbilities.setText(c.getSpecialAbilities());
		
		txtFieldStatStr.setText(getInt(c.stats.getAbilityStr()));
		txtFieldStatDex.setText(getInt(c.stats.getAbilityDex()));
		txtFieldStatCon.setText(getInt(c.stats.getAbilityCon()));
		txtFieldStatInt.setText(getInt(c.stats.getAbilityInt()));
		txtFieldStatWis.setText(getInt(c.stats.getAbilityWis()));
		txtFieldStatCha.setText(getInt(c.stats.getAbilityCha()));
		txtFieldStatBab.setText(getInt(c.stats.getBab()));
		txtFieldStatCmb.setText(getInt(c.stats.getCmb()));
		txtFieldStatCmbNotes.setText(c.stats.getCmbNotes());
		txtFieldStatCmd.setText(getInt(c.stats.getCmd()));
		txtFieldStatCmdNotes.setText(c.stats.getCmdNotes());
		txtFieldFeats.setText(c.getFeats());
		txtFieldSq.setText(c.getSpecialQualities());

		txtFieldSkillAcrobatics.setText(getInt(c.skills.getAcrobaticsMod()));
		txtFieldSkillAppraise.setText(getInt(c.skills.getAppraiseMod()));
		txtFieldSkillBluff.setText(getInt(c.skills.getBluffMod()));
		txtFieldSkillClimb.setText(getInt(c.skills.getClimbMod()));
		txtFieldSkillCraft.setText(getInt(c.skills.getCraftMod()));
		txtFieldSkillDiplomacy.setText(getInt(c.skills.getDiplomacyMod()));
		txtFieldSkillDisableDevice.setText(getInt(c.skills.getDisableDeviceMod()));
		txtFieldSkillDisguise.setText(getInt(c.skills.getDisguiseMod()));
		txtFieldSkillEscapeArtist.setText(getInt(c.skills.getEscapeArtistMod()));
		txtFieldSkillFly.setText(getInt(c.skills.getFlyMod()));
		txtFieldSkillHandleAnimal.setText(getInt(c.skills.getHandleAnimalMod()));
		txtFieldSkillHeal.setText(getInt(c.skills.getHealMod()));
		txtFieldSkillIntimidate.setText(getInt(c.skills.getIntimidateMod()));
		txtFieldSkillKnowAll.setText(getInt(c.skills.getKnowAllMod()));
		txtFieldSkillKnowArcana.setText(getInt(c.skills.getKnowArcanaMod()));
		txtFieldSkillKnowDungeoneering.setText(getInt(c.skills.getKnowDungeoneeringMod()));
		txtFieldSkillKnowEngineering.setText(getInt(c.skills.getKnowEngineeringMod()));
		txtFieldSkillKnowGeography.setText(getInt(c.skills.getKnowGeographyMod()));
		txtFieldSkillKnowHistory.setText(getInt(c.skills.getKnowHistoryMod()));
		txtFieldSkillKnowLocal.setText(getInt(c.skills.getKnowLocalMod()));
		txtFieldSkillKnowNature.setText(getInt(c.skills.getKnowNatureMod()));
		txtFieldSkillKnowNobility.setText(getInt(c.skills.getKnowNobilityMod()));
		txtFieldSkillKnowPlanes.setText(getInt(c.skills.getKnowPlanesMod()));
		txtFieldSkillKnowReligion.setText(getInt(c.skills.getKnowReligionMod()));
		txtFieldSkillLinguistics.setText(getInt(c.skills.getLinguisticsMod()));
		txtFieldSkillPerception.setText(getInt(c.skills.getPerceptionMod()));
		txtFieldSkillPerform.setText(getInt(c.skills.getPerformMod()));
		txtFieldSkillProfession.setText(getInt(c.skills.getProfessionMod()));
		txtFieldSkillRide.setText(getInt(c.skills.getRideMod()));
		txtFieldSkillSenseMotive.setText(getInt(c.skills.getSenseMotiveMod()));
		txtFieldSkillSleightOfHand.setText(getInt(c.skills.getSleightOfHandMod()));
		txtFieldSkillSpellcraft.setText(getInt(c.skills.getSpellcraftMod()));
		txtFieldSkillStealth.setText(getInt(c.skills.getStealthMod()));
		txtFieldSkillSurvival.setText(getInt(c.skills.getSurvivalMod()));
		txtFieldSkillSwim.setText(getInt(c.skills.getSwimMod()));
		txtFieldSkillUseMagicDevice.setText(getInt(c.skills.getUseMagicDeviceMod()));
		
		txtFieldSkillAcrobaticsNotes.setText(c.skills.getAcrobaticsNotes());
		txtFieldSkillAppraiseNotes.setText(c.skills.getAppraiseNotes());
		txtFieldSkillBluffNotes.setText(c.skills.getBluffNotes());
		txtFieldSkillClimbNotes.setText(c.skills.getClimbNotes());
		txtFieldSkillCraftNotes.setText(c.skills.getCraftNotes());
		txtFieldSkillDiplomacyNotes.setText(c.skills.getDiplomacyNotes());
		txtFieldSkillDisableDeviceNotes.setText(c.skills.getDisableDeviceNotes());
		txtFieldSkillDisguiseNotes.setText(c.skills.getDisguiseNotes());
		txtFieldSkillEscapeArtistNotes.setText(c.skills.getEscapeArtistNotes());
		txtFieldSkillFlyNotes.setText(c.skills.getFlyNotes());
		txtFieldSkillHandleAnimalNotes.setText(c.skills.getHandleAnimalNotes());
		txtFieldSkillHealNotes.setText(c.skills.getHealNotes());
		txtFieldSkillIntimidateNotes.setText(c.skills.getIntimidateNotes());
		txtFieldSkillKnowAllNotes.setText(c.skills.getKnowAllNotes());
		txtFieldSkillKnowArcanaNotes.setText(c.skills.getKnowArcanaNotes());
		txtFieldSkillKnowDungeoneeringNotes.setText(c.skills.getKnowDungeoneeringNotes());
		txtFieldSkillKnowEngineeringNotes.setText(c.skills.getKnowEngineeringNotes());
		txtFieldSkillKnowGeographyNotes.setText(c.skills.getKnowGeographyNotes());
		txtFieldSkillKnowHistoryNotes.setText(c.skills.getKnowHistoryNotes());
		txtFieldSkillKnowLocalNotes.setText(c.skills.getKnowLocalNotes());
		txtFieldSkillKnowNatureNotes.setText(c.skills.getKnowNatureNotes());
		txtFieldSkillKnowNobilityNotes.setText(c.skills.getKnowNobilityNotes());
		txtFieldSkillKnowPlanesNotes.setText(c.skills.getKnowPlanesNotes());
		txtFieldSkillKnowReligionNotes.setText(c.skills.getKnowReligionNotes());
		txtFieldSkillLinguisticsNotes.setText(c.skills.getLinguisticsNotes());
		txtFieldSkillPerceptionNotes.setText(c.skills.getPerceptionNotes());
		txtFieldSkillPerformNotes.setText(c.skills.getPerformNotes());
		txtFieldSkillProfessionNotes.setText(c.skills.getProfessionNotes());
		txtFieldSkillRideNotes.setText(c.skills.getRideNotes());
		txtFieldSkillSenseMotiveNotes.setText(c.skills.getSenseMotiveNotes());
		txtFieldSkillSleightOfHandNotes.setText(c.skills.getSleightOfHandNotes());
		txtFieldSkillSpellcraftNotes.setText(c.skills.getSpellcraftNotes());
		txtFieldSkillStealthNotes.setText(c.skills.getStealthNotes());
		txtFieldSkillSurvivalNotes.setText(c.skills.getSurvivalNotes());
		txtFieldSkillSwimNotes.setText(c.skills.getSwimNotes());
		txtFieldSkillUseMagicDeviceNotes.setText(c.skills.getUseMagicDeviceNotes());
		txtFieldSkillNotes.setText(c.skills.getNotes());
		
		bringCursorToBeginningOfAllText();
	}

	private void bringCursorToBeginningOfAllText()
	{
		
		findComponents(mainUI, JTextField.class).stream().forEach(f -> f.setCaretPosition(0));
		findComponents(mainUI, JFormattedTextField.class).stream().forEach(f -> f.setCaretPosition(0));
		findComponents(mainUI, JTextArea.class).stream().forEach(f -> f.setCaretPosition(0));
	}
	
	public static <T extends JComponent> List<T> findComponents(
		    final Container container,
		    final Class<T> componentType
		) {
		    return Stream.concat(
		        Arrays.stream(container.getComponents())
		            .filter(componentType::isInstance)
		            .map(componentType::cast),
		        Arrays.stream(container.getComponents())
		            .filter(Container.class::isInstance)
		            .map(Container.class::cast)
		            .flatMap(c -> findComponents(c, componentType).stream())
		    ).collect(Collectors.toList());
		}
	
	private Integer getInt(String text)
	{
		if (StringUtils.isBlank(text))
			return null;
		else
			return Integer.parseInt(text);
	}
	
	private String getInt(Integer intgr)
	{
		if (intgr==null)
			return "";
		else
			return intgr.toString();
	}

	public void startUI()
	{
		createUIComponents();
		setPositionAndSize();
		setLayouts();
		setBorders();
		addComponents();
		addEvents();

		mainUI.setLayout(null);
		mainUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		mainUI.setTitle("Pathfinder RPG NPC Import Tool for Roll20 " +VERSION);
		if (iconD20 != null)
			mainUI.setIconImage(iconD20.getImage());
		mainUI.setVisible(true);// making the frame visible
	}

	
	public static void main(String args[])
	{
		boolean isToolExpired = false;
		Date dateToExpire = new Date(2020 -1900, 4, 5);
//		System.out.println("App expires on " +dateToExpire);
//		System.out.println("Today is " +new Date());
		
		lblUrl = new JLabel("https://kroythegm.itch.io/roll20-pathfinder-npc-import-tool");
		lblUrl.setForeground(Color.BLUE.darker());
		lblUrl.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblUrl.addMouseListener(new MouseListener()
		{
			
			@Override
			public void mouseReleased(MouseEvent e)
			{
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e)
			{
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e)
			{
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e)
			{
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseClicked(MouseEvent e)
			{
				try
				{
					Desktop.getDesktop().browse(new URI("https://kroythegm.itch.io/roll20-pathfinder-npc-import-tool"));

				}
				catch (Exception err)
				{
					err.printStackTrace();
				}
			}
		});
		
//		if (new Date().getTime() >= dateToExpire.getTime())
//			isToolExpired = true;
		
		if (isToolExpired)
		{
			showExpiredMessage();
		}
		else
		{
			ConvertTextToRoll20PfNpcRobotUI ui = new ConvertTextToRoll20PfNpcRobotUI();
		}		
	}

	private static void showExpiredMessage()
	{
		JPanel pnlExpired = new JPanel();
		pnlExpired.setLayout(new BoxLayout(pnlExpired, BoxLayout.Y_AXIS));
		JLabel lblTooOld = new JLabel("This Beta version is atleast a week old. Please download the lates veresion at");
		
		pnlExpired.add(lblTooOld);
		pnlExpired.add(lblUrl);
		JOptionPane.showMessageDialog(null, pnlExpired);
	}
}

class EmptyDocumentListener implements DocumentListener {
	
	private JTextField jTextField;
	
	public EmptyDocumentListener(JTextField jTextField)
	{
		this.jTextField = jTextField;
	}
	
	private void checkText()
	{		
		if (StringUtils.isBlank(jTextField.getText()))
		{
			jTextField.setBackground(Color.YELLOW);
		}
		else
		{
			jTextField.setBackground(Color.WHITE);
		}
	}
	
	@Override
	public void insertUpdate(DocumentEvent e)
	{
		checkText();
	}

	@Override
	public void removeUpdate(DocumentEvent e)
	{
		checkText();
	}

	@Override
	public void changedUpdate(DocumentEvent e)
	{
		checkText();
	}
   
}


package org.pf1etools.roll20npc.parser;

import static org.fest.assertions.api.Assertions.assertThat; // main one

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.stream.Collectors;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.pf1etools.creature.Creature;
import org.pf1etools.creature.utils.Sense;
import org.pf1etools.roll20npc.parser.TextParser;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("Dev")
public class ParserTestText
{
	@Test
	public void testParseGriffon() throws Exception
	{
		FileInputStream fis = new FileInputStream("./src/test/resources/creatures/Griffon_d20pfsrc.txt");
	    String data = IOUtils.toString(fis, "UTF-8");
	    Creature creat = TextParser.parse(data);
	    assertThat(creat.getName()).isEqualTo("Griffon");
	    assertThat(creat.getLevel()).isEqualTo(4);
	    assertThat(creat.getInitiativeMod()).isEqualTo(2);
	    assertThat(creat.defenses.getAc()).isEqualTo(17);
	    assertThat(creat.defenses.getAcTouch()).isEqualTo(11);
	    assertThat(creat.defenses.getAcFlatFooted()).isEqualTo(15);
	    assertThat(creat.defenses.getHp()).isEqualTo(42);
	    assertThat(creat.defenses.getHd()).isEqualTo(5);
	    assertThat(creat.defenses.getHitDie()).isEqualTo(10);
	    assertThat(creat.defenses.getSaveFort()).isEqualTo(7);
	    assertThat(creat.defenses.getSaveReflex()).isEqualTo(6);
	    assertThat(creat.defenses.getSaveWill()).isEqualTo(4);
	    assertThat(creat.stats.getAbilityStr()).isEqualTo(16);
	    assertThat(creat.stats.getAbilityDex()).isEqualTo(15);
	    assertThat(creat.stats.getAbilityCon()).isEqualTo(16);
	    assertThat(creat.stats.getAbilityInt()).isEqualTo(5);
	    assertThat(creat.stats.getAbilityCon()).isEqualTo(16);
	    assertThat(creat.stats.getAbilityCha()).isEqualTo(8);
	    assertThat(creat.stats.getBab()).isEqualTo(5);
	    assertThat(creat.stats.getCmb()).isEqualTo(9);
	    assertThat(creat.stats.getCmd()).isEqualTo(21);
	    assertThat(creat.skills.getAcrobaticsMod()).isEqualTo(10);
	    assertThat(creat.skills.getFlyMod()).isEqualTo(6);
	    assertThat(creat.skills.getPerceptionMod()).isEqualTo(12);	    
	}
}

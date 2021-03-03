package org.pf1etools.creature;

import static org.fest.assertions.api.Assertions.assertThat; // main one

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("Dev")
public class AbilityScoreTest
{
	@Test
	public void testAbilityScoreNull()
	{
		Creature creature = new Creature();
		assertThat(creature.stats.getAbilityScoreModifyer(null)).isNull();		
	}
	
	@Test
	public void testAbilityScore10()
	{
		Creature creature = new Creature();
		assertThat(creature.stats.getAbilityScoreModifyer(10)).isEqualTo(0);		
	}
	
	@Test
	public void testAbilityScore12()
	{
		Creature creature = new Creature();
		assertThat(creature.stats.getAbilityScoreModifyer(12)).isEqualTo(1);		
	}
	
	@Test
	public void testAbilityScore8()
	{
		Creature creature = new Creature();
		assertThat(creature.stats.getAbilityScoreModifyer(8)).isEqualTo(-1);		
	}
}

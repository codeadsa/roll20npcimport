package org.pf1etools.creature;

import static org.fest.assertions.api.Assertions.assertThat; // main one

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.pf1etools.creature.utils.Sense;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("Dev")
public class TestSense
{
	@Test
	public void testSenseParseLowLight()
	{
		Sense sense = Sense.parseSense("low-light vision");
		assertThat(sense.getName()).isEqualTo("low-light vision");
		assertThat(sense.getDistanceInFeet()).isNull();
	}

	@Test
	public void testSenseParseBlindsight()
	{
		Sense sense = Sense.parseSense("blindsight 30 ft.");
		assertThat(sense.getName()).isEqualTo("blindsight");
		assertThat(sense.getDistanceInFeet()).isEqualTo(30);
	}

	@Test
	public void testSenseParseBadName_isNull()
	{
		Sense sense = Sense.parseSense("dragon sense");
		assertThat(sense).isNull();
	}

	@Test
	public void testSenseParseBadNameWithDistance_isNull()
	{
		Sense sense = Sense.parseSense("dragon sense 120ft.");
		assertThat(sense).isNull();
	}

	@Test
	public void testSenseParseSeeInDarkness()
	{
		Sense sense = Sense.parseSense("see in darkness");
		assertThat(sense.getName()).isEqualTo("see in darkness");
		assertThat(sense.getDistanceInFeet()).isNull();
	}

	@Test
	public void testSenseParseDarkvision120()
	{
		Sense sense = Sense.parseSense("darkvision 120 ft.");
		assertThat(sense.getName()).isEqualTo("darkvision");
		assertThat(sense.getDistanceInFeet()).isEqualTo(120);
	}

	@Disabled
	public void testSenseParseDarkvisionNoSpace()
	{
		Sense sense = Sense.parseSense("darkvision 120ft.");
		assertThat(sense.getName()).isEqualTo("darkvision");
		assertThat(sense.getDistanceInFeet()).isEqualTo(120);
	}
}

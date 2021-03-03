package org.pf1etools.creature.utils;

import lombok.Getter;

@Getter
public enum LevelFraction
{
	ONE_HALF ("1/2", .5),
	ONE_THIRD ("1/3", .33),
	ONE_FOURTH ("1/4", .25),
	ONE_SIXTH ("1/6", .16),
	ONE_EIGHTH ("1/8", .125);
	
	private final String fraction;
	private final double value;
	LevelFraction(String fraction, double value)
	{
		this.fraction = fraction;
		this.value = value;
	}	
	
	public static LevelFraction parseFraction(String fraction)
	{
		switch (fraction)
		{
			case "1/2": return ONE_HALF;
			case "1/3": return ONE_THIRD;
			case "1/4": return ONE_FOURTH;
			case "1/6": return ONE_SIXTH;
			case "1/8": return ONE_EIGHTH;
		}		
		return null;
	}
}

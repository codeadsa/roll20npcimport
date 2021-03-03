package org.pf1etools.creature;

import org.apache.commons.lang3.StringUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Offense
{
	Integer speedLand;
	Integer speedFly;
	String speedFlyNotes;
	Integer speedClimb;
	Integer speedSwim;
	Integer speedBurrow;
	String speedBurrowNotes;
	String speedNotes;
	
	Integer space;
	Integer reach;
	String reachNotes;
	
	String melee;
	String range;
	String specialAttacks;

	private String getSpeedLandPrint()
	{
		if (this.speedLand == null)
			return "";
		else
			return speedLand.intValue() +" ft., ";
	}
	
	private String getSpeedBurrowPrint()
	{
		if (this.speedBurrow == null)
			return "";
		else
			return "Burrow " +speedBurrow.intValue() +" ft., ";
	}
	
	private String getSpeedClimbPrint()
	{
		if (this.speedClimb == null)
			return "";
		else
			return "Climb " +speedClimb.intValue() +" ft., ";
	}
	
	private String getSpeedFlyPrint()
	{
		if (this.speedFly == null)
			return "";
		else
			return "Fly " +speedFly.intValue() +" ft. (" +speedFlyNotes +"), ";
	}
	
	private String getSpeedSwimPrint()
	{
		if (this.speedSwim == null)
			return "";
		else
			return "Swim " +speedSwim.intValue() +" ft., ";
	}
	
	public String getSpeedPrint()
	{
		StringBuilder sbSpeed = new StringBuilder("");
		sbSpeed.append(getSpeedLandPrint())
		.append(getSpeedBurrowPrint())
		.append(getSpeedClimbPrint())
		.append(getSpeedFlyPrint())
		.append(getSpeedSwimPrint());
		if (sbSpeed.length() > 0)
			sbSpeed.delete(sbSpeed.length()-2, sbSpeed.length());
		if (StringUtils.isNotBlank(getSpeedNotes()))
			sbSpeed.append("; ").append(getSpeedNotes());
		return sbSpeed.toString();		
	}
	
	public String getReachPrint()
	{
		StringBuilder sbReach = new StringBuilder();
		sbReach.append(this.getReach()).append(" ft.");
		if (StringUtils.isNotBlank(getReachNotes()))
			sbReach.append(" (").append(getReachNotes()).append(")");
		return sbReach.toString();
	}
	
	public String toString() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }
}

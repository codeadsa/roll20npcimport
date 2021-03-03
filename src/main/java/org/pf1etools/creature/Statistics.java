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
public class Statistics
{
	Integer abilityStr;
	Integer abilityDex;
	Integer abilityCon;
	Integer abilityInt;
	Integer abilityWis;
	Integer abilityCha;

	Integer bab;
	Integer cmd;
	String cmbNotes;
	Integer cmb;
	String cmdNotes;
	
	public Integer getAbilityScoreModifyer(Integer abilityScore)
	{
		if (abilityScore == null)
			return null;
		else
		{
			return Math.floorDiv((abilityScore - 10), 2);
		}
	}
	
	public String toString() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }
	
	public String getCmbPrint()
	{
		String txtNotes = "";
		if (StringUtils.isNotBlank(cmbNotes))
			txtNotes = " (" +cmbNotes +")";
		return cmb +txtNotes;
	}
	
	public String getCmdPrint()
	{
		String txtNotes = "";
		if (StringUtils.isNotBlank(cmdNotes))
			txtNotes = " (" +cmdNotes +")";
		return cmd +txtNotes;
	}
}

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
public class Defenses
{
	Integer ac;
	Integer acTouch;
	Integer acFlatFooted;
	String acNotes;
	
	Integer hp;
	Integer hd;
	Integer hitDie;
	String hpMod;
	String hpNotes;
	
	Integer saveFort;
	Integer saveReflex;
	Integer saveWill;
	String saveNotes;
	
	String damageReduction;
	String immune;
	String resist;
	String defensiveAbilities;
	Integer spellResist;
	String weakness;

	public String getHpPrint()
	{
		StringBuilder sbHp = new StringBuilder();
		sbHp.append("hp ").append(this.getHp()).append(" (").append(this.getHd()).append("d").append(this.getHitDie()).append(this.getHpMod()).append(")");
		if (StringUtils.isNotBlank(this.getHpNotes()))
			sbHp.append(";").append(this.getHpNotes());
		
		return sbHp.toString();
	}
	
	public String getAcPrint()
	{
		String txtAcNotes = "";
		if (StringUtils.isNotBlank(acNotes)) {
			txtAcNotes = "(" +acNotes +")";
		}
		StringBuilder sbAppend = new StringBuilder()
		.append("AC ").append(getAc()).append(", touch ").append(getAcTouch()).append(", flat-footed ").append(getAcFlatFooted()).append(txtAcNotes);
		return sbAppend.toString();
	}
	
	public String toString() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }
}
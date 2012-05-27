package com.github.Holyvirus.Blacksmith.core.Tools.Enchanter;

import org.bukkit.entity.Player;

public class Enchanter {

	public static boolean validateMsg1(String m){
		if(m.equalsIgnoreCase("Protection") || m.equalsIgnoreCase("Fire_Protection") || m.equalsIgnoreCase("Feather_Fall") || m.equalsIgnoreCase("Blast_Protection") || m.equalsIgnoreCase("Projectile_Protection") || m.equalsIgnoreCase("Respiration") || m.equalsIgnoreCase("Aqua_Affinity") || m.equalsIgnoreCase("Sharpness") || m.equalsIgnoreCase("Smite") || m.equalsIgnoreCase("Bane_of_Arthropods") || m.equalsIgnoreCase("Knockback") || m.equalsIgnoreCase("Fire Aspect") || m.equalsIgnoreCase("Looting") || m.equalsIgnoreCase("Efficiency") || m.equalsIgnoreCase("Silk Touch") || m.equalsIgnoreCase("Unbreaking") || m.equalsIgnoreCase("Fortune") || m.equalsIgnoreCase("Power") || m.equalsIgnoreCase("Punch") || m.equalsIgnoreCase("Flame") || m.equalsIgnoreCase("Infinity")){
			return true;
		}
		return false;
	}

	public static void help(Player p){
		
	}
	public static void enchant(Player p, String e, int lvl){
		
	}
}
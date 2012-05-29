package com.github.Holyvirus.Blacksmith.core.Tools.Enchanter;

import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Enchanter {

	private static HashMap<String, ItemStack> item = new HashMap<String, ItemStack>();
	
	public static void add(Player p, ItemStack i){
		item.put(p.getName(), i);
	}
	
	public static boolean validateMsg1(String m){
		if(m.equalsIgnoreCase("Protection") || m.equalsIgnoreCase("Fire_Protection") || m.equalsIgnoreCase("Feather_Fall") || m.equalsIgnoreCase("Blast_Protection") || m.equalsIgnoreCase("Projectile_Protection") || m.equalsIgnoreCase("Respiration") || m.equalsIgnoreCase("Aqua_Affinity") || m.equalsIgnoreCase("Sharpness") || m.equalsIgnoreCase("Smite") || m.equalsIgnoreCase("Bane_of_Arthropods") || m.equalsIgnoreCase("Knockback") || m.equalsIgnoreCase("Fire Aspect") || m.equalsIgnoreCase("Looting") || m.equalsIgnoreCase("Efficiency") || m.equalsIgnoreCase("Silk Touch") || m.equalsIgnoreCase("Unbreaking") || m.equalsIgnoreCase("Fortune") || m.equalsIgnoreCase("Power") || m.equalsIgnoreCase("Punch") || m.equalsIgnoreCase("Flame") || m.equalsIgnoreCase("Infinity")){
			return true;
		}
		return false;
	}

	public static void enchant(Player p, String e, int lvl){
		ItemStack i = p.getInventory().getItem(item.get(p.getName()).getType().getId());
	}
}
package com.github.Holyvirus.Blacksmith.core.Tools.Cost;

import com.github.Holyvirus.Blacksmith.BlackSmith;
import com.github.Holyvirus.Blacksmith.core.Eco.iEco;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Repair {
	
	private static iEco eH;
	private static boolean FC = true;
	
	private static void init() {	
		FC = false;
		if(null != BlackSmith.getPlugin().getEcoHandler())
			eH = BlackSmith.getPlugin().getEcoHandler().getEngine();
	}
	
	public static String doRepair(Player p, ItemStack i) {
		if(FC)
			init();
		
		if(i != null) {
			if(eH != null) {
				double cost = Cost.calcCost(i);
				double b = eH.getBalance(p);
				if(b > cost) {
					eH.withdraw(p, cost);
					i.setDurability((short) 0);
					return null;
				}else{
					return "you do not have enough money!";
				}
			}else{
				return "no economy engine was found!";
			}
		}
		
		return "no item passed!";
	}
}
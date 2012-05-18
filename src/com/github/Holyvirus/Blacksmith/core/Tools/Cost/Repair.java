package com.github.Holyvirus.Blacksmith.core.Tools.Cost;

import com.github.Holyvirus.Blacksmith.BlackSmith;
import com.github.Holyvirus.Blacksmith.core.Eco.iEco;
import com.github.Holyvirus.Blacksmith.core.Eco.mEco;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Repair {
	
	private static iEco eH;
	private static mEco eM;
	private static boolean FC = true;
	
	private static void init() {	
		FC = false;
		if(null != BlackSmith.getPlugin().getEcoHandler())
			eH = BlackSmith.getPlugin().getEcoHandler().getEngine();
		
		eM = BlackSmith.getPlugin().getMatEngine();
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
				return "the economy engine specified was not found!";
			}
		}
		
		return "no item passed!";
	}
}
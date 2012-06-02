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
			Boolean hM = false;
			
			if(MaterialEn.useMatEn()) {
				if(MaterialEn.hasEnough(p, i)) {
					hM = true;
				}else{
					return "you do not have the required materials!";	
				}
			}
			
			if(eH != null) {
				double cost = Cost.calcCost(i);
				double b = eH.getBalance(p);
				if(b > cost) {
					if(MaterialEn.useMatEn() && hM) {
						i.setDurability((short) 0);
						MaterialEn.take(p, i);
						eH.withdraw(p, cost);
						return null;
					}else if(!MaterialEn.useMatEn()){
						i.setDurability((short) 0);
						eH.withdraw(p, cost);
						return null;
					}
					return "Materials lacking!";
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
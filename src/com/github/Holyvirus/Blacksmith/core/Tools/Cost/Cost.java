package com.github.Holyvirus.Blacksmith.core.Tools.Cost;

import com.github.Holyvirus.Blacksmith.BlackSmith;
import com.github.Holyvirus.Blacksmith.core.config;
import com.github.Holyvirus.Blacksmith.core.Eco.iEco;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Cost {

	private static iEco eH;
//  private static MatEn me = BlackSmith.getPlugin().getMatEngine();
	private static config cH = config.Obtain();
	private static boolean FC = true;
	private static int leatherBase = 5;
	private static int woodBase = 20;
	private static int stoneBase = 50;
	private static int ironBase = 100;
	private static int goldBase = 75;
	private static int diamondBase = 200;
	private static int chainBase = 600;
	
	private static void init() {
		FC = false;
		leatherBase = cH.getInt("BlackSmith.Prices.leatherbase");
		woodBase = cH.getInt("BlackSmith.Prices.woodbase");
		stoneBase = cH.getInt("BlackSmith.Prices.stonebase");
		ironBase = cH.getInt("BlackSmith.Prices.ironbase");
		goldBase = cH.getInt("BlackSmith.Prices.goldbase");
		diamondBase = cH.getInt("BlackSmith.Prices.diamondbase");
		chainBase = cH.getInt("BlackSmith.Prices.chainbase");
		
		if(null != BlackSmith.getPlugin().getEcoHandler())
			eH = BlackSmith.getPlugin().getEcoHandler().getEngine();
	}
	
	public static int calcCost(Player p, ItemStack i) {
		if(i == null)
			return 0;
		
		if(FC)
			init();
		
		
		
		return 0;
	}
}

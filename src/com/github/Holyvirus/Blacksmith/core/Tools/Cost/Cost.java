package com.github.Holyvirus.Blacksmith.core.Tools.Cost;

import com.github.Holyvirus.Blacksmith.BlackSmith;
import com.github.Holyvirus.Blacksmith.core.config;
import com.github.Holyvirus.Blacksmith.core.Eco.iEco;
import com.github.Holyvirus.Blacksmith.core.Misc.Misc;
import com.github.Holyvirus.Blacksmith.core.Misc.ToolType;

import org.bukkit.entity.Player;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

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

	public static double calcEnchantmentModifier(ItemStack i) {
		if(i == null)
			return 1;
		
		config conf = config.Obtain();
		double modifier = 1;
		
		Map<Enchantment, Integer> ench = i.getEnchantments();
		if(ench.size() > 0) {
			double em = conf.getDouble("BlackSmith.Prices.enchantmentModifier");
			if(em > 0) {
				for(Map.Entry<Enchantment, Integer> entry : ench.entrySet()) {
					modifier = modifier + em * entry.getValue() / 100D;
				}
			}
		}
		
		return modifier;
	}
	
	public static double calcCost(Player p, ItemStack i) {
		if(i == null)
			return 0;
		
		if(FC)
			init();
		
		double b = 0D;
		
		ToolType t = Misc.getKind(i);
		switch(t) {
			case LEATHER:
				b = leatherBase;
				break;
			case WOOD:
				b = woodBase;
				break;
			case STONE:
				b = stoneBase;
				break;
			case IRON:
				b = ironBase;
				break;
			case GOLD:
				b = goldBase;
				break;
			case DIAMOND:
				b = diamondBase;
				break;
			case CHAIN:
				b = chainBase;
				break;
			case INVALID:
				return 0;
		}
		
		if(b == 0D)
			return 0;
		
		double eM = calcEnchantmentModifier(i);
		
		
		
		return b;
	}
}

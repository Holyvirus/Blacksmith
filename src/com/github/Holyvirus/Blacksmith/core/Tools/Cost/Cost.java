package com.github.Holyvirus.Blacksmith.core.Tools.Cost;

import com.github.Holyvirus.Blacksmith.BlackSmith;
import com.github.Holyvirus.Blacksmith.core.config;
import com.github.Holyvirus.Blacksmith.core.Eco.iEco;
import com.github.Holyvirus.Blacksmith.core.Misc.Misc;
import com.github.Holyvirus.Blacksmith.core.Misc.ToolType;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Cost {

	private static iEco eH;
//  private static MatEn me = BlackSmith.getPlugin().getMatEngine();
	private static config cH = config.Obtain();
	private static boolean FC = true;
	private static double leatherBase = 5;
	private static double woodBase = 20;
	private static double stoneBase = 50;
	private static double ironBase = 100;
	private static double goldBase = 75;
	private static double diamondBase = 200;
	private static double chainBase = 600;
	
	public static double getUsedBlocks(ItemStack i) {
		if(i == null)
			return 1;
		
		ToolType t = Misc.getType(i);
		switch(t) {
			case SPADE:
			case FLINT_AND_STEEL:
			case ROD:
				return 1;
			case SWORD:
			case HOE:
			case BOW:
			case SHEARS:
				return 2;
			case AXE:
			case PICKAXE:
				return 3;
			case BOOT:
				return 4;
			case HELMET:
				return 5;
			case LEG:
				return 7;
			case CHEST:
				return 8;
		}
		
		return 1;
	}
	
	private static void init() {
		FC = false;
		leatherBase = cH.getDouble("BlackSmith.Prices.leatherbase");
		woodBase = cH.getDouble("BlackSmith.Prices.woodbase");
		stoneBase = cH.getDouble("BlackSmith.Prices.stonebase");
		ironBase = cH.getDouble("BlackSmith.Prices.ironbase");
		goldBase = cH.getDouble("BlackSmith.Prices.goldbase");
		diamondBase = cH.getDouble("BlackSmith.Prices.diamondbase");
		chainBase = cH.getDouble("BlackSmith.Prices.chainbase");
		
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
	
	public static List<String> calcCost(ItemStack i, Boolean asString) {
		DecimalFormat f = new DecimalFormat("#0.00");
		List<String> s = new ArrayList<String>();
		
		if(i == null) {
			s.add(f.format(0));
			return s;
		}
		
		if(FC)
			init();
		
		if(eH != null) {
			double b = 0D;
			double cost = 0D;
			
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
				default:
					b = 0D;
			}
			
			if(b == 0D) {
				s.add(f.format(0));
				return s;
			}
			
			double e = calcEnchantmentModifier(i);
			double u = getUsedBlocks(i);
			cost = e * (b * ((double) i.getDurability() / (double) i.getType().getMaxDurability() * u));
	
			s.add(f.format(Misc.Round(cost, 2)));
		}
		
		if(MaterialEn.useMatEn()) {
			s.addAll(MaterialEn.calcCost(i, true));
		}
		
		if(s.size() == 0) {
			s.add("This is odd, we can neither use an economy engine nor the materials engine!");
		}
		
		return s;
	}
	
	public static double calcCost(ItemStack i) {
		if(i == null)
			return 0;
		
		if(FC)
			init();
		
		double b = 0D;
		double cost = 0D;
		
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
			default:
				return 0;
		}
		
		if(b == 0D)
			return 0;
		
		if(eH != null) {
			double e = calcEnchantmentModifier(i);
			double u = getUsedBlocks(i);
			cost = e * (b * ((double) i.getDurability() / (double) i.getType().getMaxDurability() * u));
		}

		return Misc.Round(cost, 2);
	}
}
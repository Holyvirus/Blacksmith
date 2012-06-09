package com.github.Holyvirus.Blacksmith.core.Tools.Dismantling;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.github.Holyvirus.Blacksmith.BlackSmith;
import com.github.Holyvirus.Blacksmith.core.config;
import com.github.Holyvirus.Blacksmith.core.Eco.iEco;
import com.github.Holyvirus.Blacksmith.core.Eco.mEco;
import com.github.Holyvirus.Blacksmith.core.Tools.Cost.Cost;

public class Dismantling {
	
	private static boolean FC = true;
	private static iEco eH;
	
	static config conf = config.Obtain();
	
	private static void init() {	
		FC = false;
		if(null != BlackSmith.getPlugin().getEcoHandler())
			eH = BlackSmith.getPlugin().getEcoHandler().getEngine();
	}
	
	public static String dismantle(Player p, ItemStack I){
		if(FC)
			init();
		
		if(eH == null)
			return null;
		
		double b = eH.getBalance(p);
		double cost = Cost.calcCost(I) / conf.getInt("BlackSmith.Settings.DismantleDivider");

		if(b > cost) {
			eH.withdraw(p, cost);
			if(config.Obtain().getBoolean("BlackSmith.Settings.DismantleOnlyOnFull")){
				if(I.getDurability() == 0){
					Dismantling.take(p, I);
					Dismantling.add(p, I, 1);
					return null;
				}else{
					return "your settings require the item to be at full durability!";
				}
			}else{
				double c = (1 - (double) I.getDurability() / (double) I.getType().getMaxDurability());
				Dismantling.take(p, I);
				Dismantling.add(p, I, c);
				return null;
			}
		}else{
			return "you do not have enough money!";
		}
		//return "your item could not be handled, check the \"Blacksmith.Settings.DismantleOnlyOnFull\" node in your config!";
	}
	
	public static void take(Player p, ItemStack I){
		mEco mH = BlackSmith.getPlugin().getMatEngine();
		mH.withdraw(p, I, 1);
	}
	
	public static void add(Player p, ItemStack I, double c){
		int a = (int) Math.round(c * Cost.getUsedBlocks(I));
		a = (a < 1) ? 1 : a;
		ItemStack STICK = new ItemStack(Material.STICK);
		ItemStack WOOD = new ItemStack(Material.WOOD);
		ItemStack LEATHER = new ItemStack(Material.LEATHER);
		ItemStack STONE = new ItemStack(Material.STONE);
		ItemStack IRON = new ItemStack(Material.IRON_INGOT);
		ItemStack GOLD = new ItemStack(Material.GOLD_INGOT);
		ItemStack DIAMOND = new ItemStack(Material.DIAMOND);
		mEco mH = BlackSmith.getPlugin().getMatEngine();
		switch(I.getType()) {
			case WOOD_PICKAXE:
				mH.deposit(p, WOOD, a);
				mH.deposit(p, STICK, 2);
				break;
			case WOOD_AXE:
				mH.deposit(p, WOOD, a);
				mH.deposit(p, STICK, 2);
				break;
			case WOOD_SWORD:
				mH.deposit(p, WOOD, a);
				mH.deposit(p, STICK, 1);
				break;
			case WOOD_SPADE:
				mH.deposit(p, WOOD, a);
				mH.deposit(p, STICK, 2);
				break;
			case WOOD_HOE:
				mH.deposit(p, WOOD, a);
				mH.deposit(p, STICK, 2);
				break;
			case LEATHER_HELMET:
				mH.deposit(p, LEATHER, a);
				break;
			case LEATHER_CHESTPLATE:
				mH.deposit(p, LEATHER, a);
				break;
			case LEATHER_LEGGINGS:
				mH.deposit(p, LEATHER, a);
				break;
			case LEATHER_BOOTS:
				mH.deposit(p, LEATHER, a);
				break;
			case STONE_PICKAXE:
				mH.deposit(p, STONE, a);
				mH.deposit(p, STICK, 2);
				break;
			case STONE_AXE:
				mH.deposit(p, STONE, a);
				mH.deposit(p, STICK, 2);
				break;
			case STONE_SWORD:
				mH.deposit(p, STONE, a);
				mH.deposit(p, STICK, 1);
				break;
			case STONE_SPADE:
				mH.deposit(p, STONE, a);
				mH.deposit(p, STICK, 2);
				break;
			case STONE_HOE:
				mH.deposit(p, STONE, a);
				mH.deposit(p, STICK, 2);
				break;
			case IRON_PICKAXE:
				mH.deposit(p, IRON, a);
				mH.deposit(p, STICK, 2);
				break;
			case IRON_AXE:
				mH.deposit(p, IRON, a);
				mH.deposit(p, STICK, 2);
				break;
			case IRON_SWORD:
				mH.deposit(p, IRON, a);
				mH.deposit(p, STICK, 1);
				break;
			case IRON_SPADE:
				mH.deposit(p, IRON, a);
				mH.deposit(p, STICK, 2);
				break;
			case IRON_HOE:
				mH.deposit(p, IRON, a);
				mH.deposit(p, STICK, 2);
				break;
			case IRON_HELMET:
				mH.deposit(p, IRON, a);
				break;
			case IRON_CHESTPLATE:
				mH.deposit(p, IRON, a);
				break;
			case IRON_LEGGINGS:
				mH.deposit(p, IRON, a);
				break;
			case IRON_BOOTS:
				mH.deposit(p, IRON, a);
				break;
			case GOLD_PICKAXE:
				mH.deposit(p, GOLD, a);
				mH.deposit(p, STICK, 2);
				break;
			case GOLD_AXE:
				mH.deposit(p, GOLD, a);
				mH.deposit(p, STICK, 2);
				break;
			case GOLD_SWORD:
				mH.deposit(p, GOLD, a);
				mH.deposit(p, STICK, 1);
				break;
			case GOLD_SPADE:
				mH.deposit(p, GOLD, a);
				mH.deposit(p, STICK, 2);
				break;
			case GOLD_HOE:
				mH.deposit(p, GOLD, a);
				mH.deposit(p, STICK, 2);
				break;
			case GOLD_HELMET:
				mH.deposit(p, GOLD, a);
				break;
			case GOLD_CHESTPLATE:
				mH.deposit(p, GOLD, a);
				break;
			case GOLD_LEGGINGS:
				mH.deposit(p, GOLD, a);
				break;
			case GOLD_BOOTS:
				mH.deposit(p, GOLD, a);
				break;
			case DIAMOND_PICKAXE:
				mH.deposit(p, DIAMOND, a);
				mH.deposit(p, STICK, 2);
				break;
			case DIAMOND_AXE:
				mH.deposit(p, DIAMOND, a);
				mH.deposit(p, STICK, 2);
				break;
			case DIAMOND_SWORD:
				mH.deposit(p, DIAMOND, a);
				mH.deposit(p, STICK, 1);
				break;
			case DIAMOND_SPADE:
				mH.deposit(p, DIAMOND, a);
				mH.deposit(p, STICK, 2);
				break;
			case DIAMOND_HOE:
				mH.deposit(p, DIAMOND, a);
				mH.deposit(p, STICK, 2);
				break;
			case DIAMOND_HELMET:
				mH.deposit(p, DIAMOND, a);
				break;
			case DIAMOND_CHESTPLATE:
				mH.deposit(p, DIAMOND, a);
				break;
			case DIAMOND_LEGGINGS:
				mH.deposit(p, DIAMOND, a);
				break;
			case DIAMOND_BOOTS:
				mH.deposit(p, DIAMOND, a);
				break;
		}
	}
}
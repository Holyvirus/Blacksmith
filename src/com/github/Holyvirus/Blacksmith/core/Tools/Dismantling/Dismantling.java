package com.github.Holyvirus.Blacksmith.core.Tools.Dismantling;

import java.util.logging.Level;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.github.Holyvirus.Blacksmith.BlackSmith;
import com.github.Holyvirus.Blacksmith.core.Eco.mEco;

public class Dismantling {
	
	public static void take(Player p, ItemStack I){
		mEco mH = BlackSmith.getPlugin().getMatEngine();
		p.getInventory().removeItem(I);
	}
	public static void add(Player p, ItemStack I){
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
				mH.deposit(p, WOOD, 3);
				mH.deposit(p, STICK, 2);
				break;
			case WOOD_AXE:
				mH.deposit(p, WOOD, 3);
				mH.deposit(p, STICK, 2);
				break;
			case WOOD_SWORD:
				mH.deposit(p, WOOD, 2);
				mH.deposit(p, STICK, 1);
				break;
			case WOOD_SPADE:
				mH.deposit(p, WOOD, 1);
				mH.deposit(p, STICK, 2);
				break;
			case WOOD_HOE:
				mH.deposit(p, WOOD, 2);
				mH.deposit(p, STICK, 2);
				break;
			case LEATHER_HELMET:
				mH.deposit(p, LEATHER, 3);
				break;
			case LEATHER_CHESTPLATE:
				break;
			case LEATHER_LEGGINGS:
				mH.deposit(p, LEATHER, 7);
				break;
			case LEATHER_BOOTS:
				mH.deposit(p, LEATHER, 4);
				break;
			case STONE_PICKAXE:
				mH.deposit(p, STONE, 3);
				mH.deposit(p, STICK, 2);
				break;
			case STONE_AXE:
				mH.deposit(p, STONE, 3);
				mH.deposit(p, STICK, 2);
				break;
			case STONE_SWORD:
				mH.deposit(p, STONE, 2);
				mH.deposit(p, STICK, 1);
				break;
			case STONE_SPADE:
				mH.deposit(p, STONE, 1);
				mH.deposit(p, STICK, 2);
				break;
			case STONE_HOE:
				mH.deposit(p, STONE, 2);
				mH.deposit(p, STICK, 2);
				break;
			case IRON_PICKAXE:
				mH.deposit(p, IRON, 3);
				mH.deposit(p, STICK, 2);
				break;
			case IRON_AXE:
				mH.deposit(p, IRON, 3);
				mH.deposit(p, STICK, 2);
				break;
			case IRON_SWORD:
				mH.deposit(p, IRON, 2);
				mH.deposit(p, STICK, 1);
				break;
			case IRON_SPADE:
				mH.deposit(p, IRON, 1);
				mH.deposit(p, STICK, 2);
				break;
			case IRON_HOE:
				mH.deposit(p, IRON, 2);
				mH.deposit(p, STICK, 2);
				break;
			case IRON_HELMET:
				mH.deposit(p, IRON, 3);
				break;
			case IRON_CHESTPLATE:
				mH.deposit(p, IRON, 8);
				break;
			case IRON_LEGGINGS:
				mH.deposit(p, IRON, 7);
				break;
			case IRON_BOOTS:
				mH.deposit(p, IRON, 4);
				break;
			case GOLD_PICKAXE:
				mH.deposit(p, GOLD, 3);
				mH.deposit(p, STICK, 2);
				break;
			case GOLD_AXE:
				mH.deposit(p, GOLD, 3);
				mH.deposit(p, STICK, 2);
				break;
			case GOLD_SWORD:
				mH.deposit(p, GOLD, 2);
				mH.deposit(p, STICK, 1);
				break;
			case GOLD_SPADE:
				mH.deposit(p, GOLD, 1);
				mH.deposit(p, STICK, 2);
				break;
			case GOLD_HOE:
				mH.deposit(p, GOLD, 2);
				mH.deposit(p, STICK, 2);
				break;
			case GOLD_HELMET:
				mH.deposit(p, GOLD, 3);
				break;
			case GOLD_CHESTPLATE:
				mH.deposit(p, GOLD, 8);
				break;
			case GOLD_LEGGINGS:
				mH.deposit(p, GOLD, 7);
				break;
			case GOLD_BOOTS:
				mH.deposit(p, GOLD, 4);
				break;
			case DIAMOND_PICKAXE:
				mH.deposit(p, DIAMOND, 3);
				mH.deposit(p, STICK, 2);
				break;
			case DIAMOND_AXE:
				mH.deposit(p, DIAMOND, 3);
				mH.deposit(p, STICK, 2);
				break;
			case DIAMOND_SWORD:
				mH.deposit(p, DIAMOND, 2);
				mH.deposit(p, STICK, 1);
				break;
			case DIAMOND_SPADE:
				mH.deposit(p, DIAMOND, 1);
				mH.deposit(p, STICK, 2);
				break;
			case DIAMOND_HOE:
				mH.deposit(p, DIAMOND, 2);
				mH.deposit(p, STICK, 2);
				break;
			case DIAMOND_HELMET:
				mH.deposit(p, DIAMOND, 3);
				break;
			case DIAMOND_CHESTPLATE:
				mH.deposit(p, DIAMOND, 8);
				break;
			case DIAMOND_LEGGINGS:
				mH.deposit(p, DIAMOND, 7);
				break;
			case DIAMOND_BOOTS:
				mH.deposit(p, DIAMOND, 4);
				break;
				
		}
	}
}
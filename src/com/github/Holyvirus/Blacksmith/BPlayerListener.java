package com.github.Holyvirus.Blacksmith;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import com.iCo6.iConomy;
import com.iCo6.system.Account;
import com.iCo6.system.Accounts;
import com.iCo6.system.Holdings;

import cosine.boseconomy.BOSEconomy;

import java.util.logging.Level;

public class BPlayerListener implements Listener {
	private BlackSmith plugin;
	private boolean useIC = false;
	private boolean useBOSE = false;
	private boolean useMat = true;
	private iConomy iconomy;
	private BOSEconomy bose;

	public BPlayerListener(BlackSmith plugin)
	{
		this.plugin = plugin;
		this.useIC = plugin.getIconomyState();
		this.useBOSE = plugin.getBoseconomyState();
		this.useMat = plugin.getMatState();
		this.bose = plugin.getBose();
		this.iconomy = plugin.getIco();
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		Block block = event.getClickedBlock();
		if (block == null) {
			return;
		}
		
		BlockState state = block.getState();
		ItemStack item = event.getItem();
		if (item == null) {
			return;
		}

		if (!(state instanceof Sign)) {
			return;
		}

		Sign sign = (Sign)state;

		if (!hasCorrectFormat(sign)) {
			return;
		}
		
		if ((this.useIC && this.iconomy == null) || (this.useBOSE && this.bose == null)) {
			this.plugin.getLogger().log(Level.SEVERE, "Missing economy engine!");
		}
		
		int cost = getTotalCost(item);
		int mc = getBlocksUsed(item);
		String matCost = "one";
		if(mc == 1)
			matCost = "one";
		else if(mc == 2)
			matCost = "two";
		else if(mc == 3)
			matCost = "three";
		else if(mc == 4)
			matCost = "four";
		else if(mc == 5)
			matCost = "five";
		else if(mc == 6)
			matCost = "six";
		else if(mc == 7)
			matCost = "seven";
		else if(mc == 8)
			matCost = "eight";

		/*if (getSignType(sign).equalsIgnoreCase("Kill")) {
			killDura(item);
		}*/ 
		
		if (getSignType(sign).equalsIgnoreCase("Value")) {
			if(player.hasPermission("blacksmith.use.value")) {
				if ((item.getDurability() == 0) && (cost >= 0)) {
					player.sendMessage(ChatColor.GREEN + "Tool at full durability.");
					return;
				}
				
				if (cost < 0) {
					player.sendMessage(ChatColor.RED + "Item not a tool.");
					return;
				}
					
				if ((this.useIC) || (this.useBOSE)) {

					player.sendMessage(ChatColor.GOLD + "It will cost " + cost + " to repair your tool.");
				}
				else if (this.useMat) {
					String mat = getToolMaterial(item);
					sendMaterialMessages(player, mat, matCost);
				}	
			}else{
				player.sendMessage(ChatColor.RED + "You Don't Have Permission");
			}
		}

		if (getSignType(sign).equalsIgnoreCase("Repair")) {
			if(player.hasPermission("blacksmith.use.repair")) {
				if ((item.getDurability() == 0) && (cost >= 0)) {
					player.sendMessage(ChatColor.GREEN + "Tool at full durability.");
					return;
				}

				if (cost < 0) {
					player.sendMessage(ChatColor.RED + "Item not a tool.");
					return;
				}
		
				if ((this.useIC) || (this.useBOSE)) {
					if (!isBalanceGreaterThanCost(player, cost)) {
						player.sendMessage(ChatColor.RED + "Not enough money to repair.");
						return;
					}
					fixItem(item, player);
					subtractMoney(player, cost);
				}
				else if (this.useMat) {
					String mat = getToolMaterial(item);
					Material m = changeToMat(mat, player);
					ItemStack mate = new ItemStack(m);
					mate.setAmount(mc);
					if (!checkPlayerInv(player, m, mc)) {
						sendNoMessages(player, mat, matCost);
						return;
					}
					removeMat(player, mate, item);
					player.sendMessage(ChatColor.GREEN + "Tool repaired!");
				}
			}else{
				player.sendMessage(ChatColor.RED + "You Don't Have Permission");
			}
		}
	}

	
	private boolean hasCorrectFormat(Sign sign)
	{
		String first = sign.getLine(0);
		String second = sign.getLine(1);

		if ((first.equalsIgnoreCase("[BlackSmith]") && second.equalsIgnoreCase("Value"))) {
			return true;
		}

		return (first.equalsIgnoreCase("[BlackSmith]") && second.equalsIgnoreCase("Repair")) 
				|| (first.equalsIgnoreCase("[BlackSmith]") && second.equalsIgnoreCase("Value"));
//				|| (first.equalsIgnoreCase("[BlackSmith]") && second.equalsIgnoreCase("Kill"));
	}

	private String getSignType(Sign sign)
	{
		String line = sign.getLine(1);

		/*if (line.equalsIgnoreCase("Kill")) {
			return line;
		}*/

		if (line.equalsIgnoreCase("Value")) {
			return line;
		}

		if (line.equalsIgnoreCase("Repair")) {
			return line;
		}

		return null;
	}

	private int getTotalCost(ItemStack tool)
	{
		Material type = tool.getType();
		double max = type.getMaxDurability();
		double durability = tool.getDurability();
		double ratio = durability / max;
		double cost = 0.0;
		int base;
		int blocksUsed = getBlocksUsed(tool);
		String tm = getToolMaterial(tool);

		if (tm.equalsIgnoreCase("wood")) {
			base = BlackSmith.woodBase;
		}else if (tm.equalsIgnoreCase("stone")) {
			base = BlackSmith.stoneBase;
		}else if (tm.equalsIgnoreCase("iron")) {
			if (type == Material.IRON_SWORD)
				ratio = durability / 250.0;	
			base = BlackSmith.ironBase;
		}else if (tm.equalsIgnoreCase("gold")) {
			base = BlackSmith.goldBase;
		}else if (tm.equalsIgnoreCase("diamond")) {
			base = BlackSmith.diamondBase;
		}else{
			return -1;
		}
		
		cost = base * (ratio * blocksUsed);
		return (int) cost;
	}

	private String getToolMaterial(ItemStack tool) {
		Material type = tool.getType();

		if ((type == Material.WOOD_AXE) || (type == Material.WOOD_HOE) || 
			(type == Material.WOOD_PICKAXE) || (type == Material.WOOD_SPADE) || 
			(type == Material.WOOD_SWORD))
			return "wood";
		if ((type == Material.STONE_AXE) || (type == Material.STONE_HOE) || 
			(type == Material.STONE_PICKAXE) || (type == Material.STONE_SPADE) || 
			(type == Material.STONE_SWORD) || (type == Material.LEATHER_BOOTS) || 
			(type == Material.LEATHER_HELMET) || (type == Material.LEATHER_LEGGINGS) || 
			(type == Material.LEATHER_CHESTPLATE) || (type == Material.FISHING_ROD))
			return "stone";
		if ((type == Material.IRON_AXE) || (type == Material.IRON_HOE) || 
			(type == Material.IRON_PICKAXE) || (type == Material.IRON_SPADE) || 
			(type == Material.IRON_SWORD) || (type == Material.IRON_BOOTS) || 
			(type == Material.IRON_HELMET) || (type == Material.IRON_LEGGINGS) || 
			(type == Material.IRON_CHESTPLATE) || (type == Material.FLINT_AND_STEEL))
			return "iron";
		if ((type == Material.GOLD_AXE) || (type == Material.GOLD_HOE) || 
			(type == Material.GOLD_PICKAXE) || (type == Material.GOLD_SPADE) || 
			(type == Material.GOLD_SWORD) || (type == Material.GOLD_BOOTS) || 
			(type == Material.GOLD_HELMET) || (type == Material.GOLD_LEGGINGS) || 
			(type == Material.GOLD_CHESTPLATE))
			return "gold";
		if ((type == Material.DIAMOND_AXE) || (type == Material.DIAMOND_HOE) || 
			(type == Material.DIAMOND_PICKAXE) || (type == Material.DIAMOND_SPADE) || 
			(type == Material.DIAMOND_SWORD) || (type == Material.DIAMOND_BOOTS) || 
			(type == Material.DIAMOND_HELMET) || (type == Material.DIAMOND_LEGGINGS) || 
			(type == Material.DIAMOND_CHESTPLATE)) {
			return "diamond";
		}
		return "";
	}

	private int getBlocksUsed(ItemStack tool)
	{
		Material type = tool.getType();
		int blocksUsed = 1;

		if ((type == Material.WOOD_SPADE) || (type == Material.STONE_SPADE) || (type == Material.IRON_SPADE) || (type == Material.GOLD_SPADE) || (type == Material.DIAMOND_SPADE) || (type == Material.FLINT_AND_STEEL) || (type == Material.FISHING_ROD))
			blocksUsed = 1;
		else if ((type == Material.WOOD_HOE) || (type == Material.WOOD_SWORD) || (type == Material.STONE_HOE) || (type == Material.STONE_SWORD) || (type == Material.IRON_HOE) || (type == Material.IRON_SWORD) || (type == Material.GOLD_HOE) || (type == Material.GOLD_SWORD) || (type == Material.DIAMOND_HOE) || (type == Material.DIAMOND_SWORD))
			blocksUsed = 2;
		else if ((type == Material.WOOD_AXE) || (type == Material.WOOD_PICKAXE) || (type == Material.STONE_AXE) || (type == Material.STONE_PICKAXE) || (type == Material.IRON_AXE) || (type == Material.IRON_PICKAXE) || (type == Material.GOLD_AXE) || (type == Material.GOLD_PICKAXE) || (type == Material.DIAMOND_AXE) || (type == Material.DIAMOND_PICKAXE))
			blocksUsed = 3;
		else if ((type == Material.LEATHER_BOOTS) || (type == Material.IRON_BOOTS) || (type == Material.GOLD_BOOTS) || (type == Material.DIAMOND_BOOTS))
			blocksUsed = 4;
		else if ((type == Material.LEATHER_HELMET) || (type == Material.IRON_HELMET) || (type == Material.GOLD_HELMET) || (type == Material.DIAMOND_HELMET))
			blocksUsed = 5;
		else if ((type == Material.LEATHER_LEGGINGS) || (type == Material.IRON_LEGGINGS) || (type == Material.GOLD_LEGGINGS) || (type == Material.DIAMOND_LEGGINGS))
			blocksUsed = 7;
		else if ((type == Material.LEATHER_CHESTPLATE) || (type == Material.IRON_CHESTPLATE) || (type == Material.GOLD_CHESTPLATE) || (type == Material.DIAMOND_CHESTPLATE)) {
			blocksUsed = 8;
		}
		return blocksUsed;
	}

	private boolean isBalanceGreaterThanCost(Player player, int cost) {
		String playerName = player.getName();

		if (this.useIC) {
			Accounts accounts = new Accounts();
			Holdings balance = accounts.get(playerName).getHoldings();
			return balance.hasEnough(cost);
		}

		if (this.useBOSE)
		{
			double balance = this.bose.getPlayerMoneyDouble(playerName);

			return balance > cost;
		}

		return false;
	}

	private void subtractMoney(Player player, int cost) {
		String playerName = player.getName();
		if (this.useIC) {
			Holdings balance = new Account(playerName).getHoldings();
			balance.subtract(cost);
		}
		else if (this.useBOSE) {
			double balance = this.bose.getPlayerMoneyDouble(playerName);
			this.bose.setPlayerMoney(playerName, balance - cost, true);
		}
	}

	private void sendMaterialMessages(Player player, String mat, String used) {
		if (mat.equalsIgnoreCase("wood")) {
			player.sendMessage(ChatColor.GOLD + "It will cost " + used + " plank to repair your tool.");
		}
		else if (mat.equalsIgnoreCase("stone")) {
			player.sendMessage(ChatColor.GOLD + "It will cost " + used + " stone to repair your tool.");
		}
		else if (mat.equalsIgnoreCase("diamond")) {
			player.sendMessage(ChatColor.GOLD + "It will cost " + used + " diamond to repair your tool.");
		}
		else if (!mat.equals(""))
			player.sendMessage(ChatColor.GOLD + "It will cost " + used + " " + mat + " ingot to repair your tool.");
	}

	private boolean checkPlayerInv(Player player, Material mat, int req)
	{
		ItemStack[] inv = player.getInventory().getContents();
		int amt = 0;
		for (ItemStack is : inv) {
			if ((is != null) && (is.getType() == mat)) {
				amt = amt + is.getAmount();
			}
		}
		plugin.getLogger().log(Level.WARNING, amt + " " + req);
		if(amt >= req)
			return true;
		
		return false;
	}

	private void sendNoMessages(Player player, String mat, String used) {
		if (mat.equalsIgnoreCase("wood")) {
			
			player.sendMessage(ChatColor.RED + "You need " + used + " plank to fix this.");
		}
		else if (mat.equalsIgnoreCase("stone")) {
			player.sendMessage(ChatColor.RED + "You need " + used + " cobblestone to fix this.");
		}
		else if (mat.equalsIgnoreCase("diamond")) {
			player.sendMessage(ChatColor.RED + "You need " + used + " diamond to fix this.");
		}
		else if (!mat.equals(""))
			player.sendMessage(ChatColor.GOLD + "You need " + used + " " + mat + " ingot to fix this.");
	}

	private void removeMat(Player player, ItemStack mat, ItemStack item)
	{
		int amt = mat.getAmount();
		ItemStack[] items = player.getInventory().getContents();
		for (int i = 0; i < items.length; i++) {
			if (items[i] != null && items[i].getType() == mat.getType() && items[i].getDurability() == mat.getDurability()) {
				if (items[i].getAmount() > amt) {
					items[i].setAmount(items[i].getAmount() - amt);
					item.setDurability((short) 0);
					break;
				} else if (items[i].getAmount() == amt) {
					item.setDurability((short) 0);
					items[i] = null;
					break;
				} else {
					amt -= items[i].getAmount();
					items[i] = null;
				}
			}
		}
		player.getInventory().setContents(items);
		player.updateInventory();
	}

	private Material changeToMat(String mat, Player player)
	{
		if (mat.equals("wood")) {
			return Material.WOOD;
		}

		if (mat.equals("stone")) {
			return Material.COBBLESTONE;
		}

		if (mat.equals("iron")) {
			return Material.IRON_INGOT;
		}

		if (mat.equals("gold")) {
			return Material.GOLD_INGOT;
		}

		if (mat.equals("diamond")) {
			return Material.DIAMOND;
		}

		return Material.AIR;
	}

	private void fixItem(ItemStack item, Player player)
	{
		item.setDurability((short) 0);
		player.sendMessage(ChatColor.GREEN + "Tool repaired!");
	}
	
	private void killDura(ItemStack item) {
		item.setDurability((short) (item.getType().getMaxDurability() - 1));
	}
}
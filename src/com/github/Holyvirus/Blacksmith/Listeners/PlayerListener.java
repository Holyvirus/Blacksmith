package com.github.Holyvirus.Blacksmith.Listeners;

import com.github.Holyvirus.Blacksmith.BlackSmith;
//import com.github.Holyvirus.Blacksmith.core.Eco.iEco;
import com.github.Holyvirus.Blacksmith.core.config;
import com.github.Holyvirus.Blacksmith.core.perms.Permission;
import com.github.Holyvirus.Blacksmith.core.Misc.Misc;
import com.github.Holyvirus.Blacksmith.core.Tools.Cost.Cost;
import com.github.Holyvirus.Blacksmith.core.Tools.Cost.Repair;
import com.github.Holyvirus.Blacksmith.core.Tools.Dismantling.Dismantling;
import com.github.Holyvirus.Blacksmith.core.Tools.Sign.*;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerListener implements Listener {

	private BlackSmith plugin;
	private Permission pH;
	
	config conf = config.Obtain();
	
	public PlayerListener(BlackSmith plugin) {
		this.plugin = plugin;
		this.pH = plugin.getPermHandler().getEngine();
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		if(event.getAction() == Action.LEFT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if(event.getClickedBlock().getState() instanceof Sign) {
				SignType st = SignValidator.getType((Sign) event.getClickedBlock().getState());
				Player p = event.getPlayer();
				switch(st) {
					case VALUE:
						String rCost = Cost.calcCost(event.getItem(), true);
						String dCost = String.valueOf(Double.parseDouble(rCost) / conf.getInt("BlackSmith.Settings.DismantleDivider"));
						if(pH.has(p, "blacksmith.use.value")) {
							if(BlackSmith.getPlugin().getEcoHandler().getEngine() != null) {
								Material m = Misc.getMatType(event.getItem());
								if(m != null) {
									p.sendMessage(ChatColor.BLUE + "It will cost you \"" + rCost + "\" to repair this tool and \"" + dCost + "\" to dismantle!");
								}else{
									p.sendMessage(ChatColor.DARK_RED + "Item not a tool!");
								}
							}else{
								p.sendMessage(ChatColor.DARK_RED + "Did not calculate cost because the economy engine specified was not found!");
							}
						}else{
							p.sendMessage(ChatColor.DARK_RED + "You do not have permission to use that sign!");
						}
						break;
					case REPAIR:
						if(pH.has(p, "blacksmith.use.repair")) {
							Material m = Misc.getMatType(event.getItem());
							if(m != null) {
								String s = Repair.doRepair(p, event.getItem());
								if(s == null) {
									p.sendMessage(ChatColor.GREEN + "Tool repaired!");
								}else{
									p.sendMessage(ChatColor.DARK_RED + "Failed to repair tool because " + s); 
								}
							}else{
								p.sendMessage(ChatColor.DARK_RED + "Item not a tool!");
							}
						}else{
							p.sendMessage(ChatColor.DARK_RED + "You do not have permission to use that sign!");
						}
						break;
					case FREE:
						if(pH.has(p, "blacksmith.use.free")) {
							Material m = Misc.getMatType(event.getItem());
							if(m != null) {
								event.getItem().setDurability((short) 0);
								p.sendMessage(ChatColor.GREEN + "Tool repaired!");
							}else{
								p.sendMessage(ChatColor.DARK_RED + "Item not a tool!");
							}
						}else{
							p.sendMessage(ChatColor.DARK_RED + "You do not have permission to use that sign!");
						}
						break;
					case KILL:
						config c = config.Obtain();
						if(c.getBoolean("BlackSmith.global.debug")) {
							if(pH.has(p, "blacksmith.use.kill")) {
								Material m = Misc.getMatType(event.getItem());
								if(m != null) {
									event.getItem().setDurability((short) (event.getItem().getType().getMaxDurability() - 1));
									p.sendMessage(ChatColor.GREEN + "Tool killed!");
								}else{
									p.sendMessage(ChatColor.DARK_RED + "Item not a tool!");
								}
							}else{
								p.sendMessage(ChatColor.DARK_RED + "You do not have permission to use that sign!");
							}
						}else{
							if(pH.has(p, "blacksmith.use.kill")) {
								p.sendMessage(ChatColor.DARK_RED + "Debug mode is not enabled!");
							}
						}
						break;
					case DISMANTLE:
						if(pH.has(p, "blacksmith.use.dismantle")) {
							Material m = Misc.getMatType(event.getItem());
							if(m != null) {
								String d = Dismantling.dismantle(p, event.getItem());
								if(d == null) {
									p.sendMessage(ChatColor.GREEN + "Tool dismantled!");
								}else{
									p.sendMessage(ChatColor.DARK_RED + "Failed to repair tool because " + d); 
								}
							}else{
								p.sendMessage(ChatColor.DARK_RED + "Item not a tool!");
							}
						}else{
							p.sendMessage(ChatColor.DARK_RED + "You do not have permission to use that sign!");
						}
						break;
					default:
						return;
				}
			}
		}
	}
	
}
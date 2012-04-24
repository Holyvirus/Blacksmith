package com.github.Holyvirus.Blacksmith.Listeners;

import com.github.Holyvirus.Blacksmith.BlackSmith;
//import com.github.Holyvirus.Blacksmith.core.Eco.iEco;
import com.github.Holyvirus.Blacksmith.core.config;
import com.github.Holyvirus.Blacksmith.core.perms.Permission;
import com.github.Holyvirus.Blacksmith.core.Misc.Misc;
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
	
	public PlayerListener(BlackSmith plugin) {
		this.plugin = plugin;
		this.pH = plugin.getPermHandler().getEngine();
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		//iEco eH = plugin.getEcoHandler().getEngine();
		if(event.getAction() == Action.LEFT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if(event.getClickedBlock().getState() instanceof Sign) {
				SignType st = SignValidator.getType((Sign) event.getClickedBlock().getState());
				Player p = event.getPlayer();
				switch(st) {
					case VALUE:
						if(pH.has(p, "blacksmith.use.value")) {
							Material m = Misc.getType(event.getItem());
							if(m != null) {
								
							}else{
								p.sendMessage(ChatColor.DARK_RED + "Item not a tool!");
							}
						}else{
							p.sendMessage(ChatColor.DARK_RED + "You have no access to that!");
						}
						break;
					case REPAIR:
						if(pH.has(p, "blacksmith.use.repair")) {
							
						}else{
							p.sendMessage(ChatColor.DARK_RED + "You have no access to that!");
						}
						break;
					case KILL:
						config c = config.Obtain();
						if(c.getBoolean("BlackSmith.global.debug")) {
							if(pH.has(p, "blacksmith.use.kill")) {
								
							}else{
								p.sendMessage(ChatColor.DARK_RED + "You have no access to that!");
							}
						}else{
							if(pH.has(p, "blacksmith.use.kill")) {
								p.sendMessage(ChatColor.DARK_RED + "Debug mode is not enabled!");
							}
						}
						break;
					default:
						return;
				}
			}
		}
	}
	
}
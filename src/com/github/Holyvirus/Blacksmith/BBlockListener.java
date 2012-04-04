package com.github.Holyvirus.Blacksmith;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.logging.Level;
import org.bukkit.Material;

public class BBlockListener implements Listener {
	
	private BlackSmith plugin;
	
	private enum Signs {
		VALUE,
		REPAIR,
		UNKOWN
	}
	
	private boolean hasCorrectFormat(SignChangeEvent event)	{
		String first = event.getLine(0);
		String second = event.getLine(1);
		
		return (first.equalsIgnoreCase("[BlackSmith]") && second.equalsIgnoreCase("Repair")) || (first.equalsIgnoreCase("[BlackSmith]") && second.equalsIgnoreCase("Value"));
	}
	
	private boolean hasCorrectFormat(Sign sign)	{
		String first = sign.getLine(0);
		String second = sign.getLine(1);
		
		return (first.equalsIgnoreCase("[BlackSmith]") && second.equalsIgnoreCase("Repair")) || (first.equalsIgnoreCase("[BlackSmith]") && second.equalsIgnoreCase("Value"));
	}
	
	private Signs getType(SignChangeEvent event) {
		String first = event.getLine(0);
		String second = event.getLine(1);
		
		if(first.equalsIgnoreCase("[BlackSmith]")) {
			if(second.equalsIgnoreCase("Repair")) {
				return Signs.REPAIR;
			}else if(second.equalsIgnoreCase("Value")) {
				return Signs.VALUE;
			}
		}
		
		return Signs.UNKOWN;
	}
	
	private Signs getType(Sign sign) {
		String first = sign.getLine(0);
		String second = sign.getLine(1);
		
		if(first.equalsIgnoreCase("[BlackSmith]")) {
			if(second.equalsIgnoreCase("Repair")) {
				return Signs.REPAIR;
			}else if(second.equalsIgnoreCase("Value")) {
				return Signs.VALUE;
			}
		}
		
		return Signs.UNKOWN;
	}
	
	public BBlockListener(BlackSmith plugin)
	{
		this.plugin = plugin;
	}
	
	@EventHandler
	public void signChanged(SignChangeEvent event) {
		if(this.hasCorrectFormat(event)) {
			Player player = event.getPlayer();
			Signs type = this.getType(event);
			
			switch(type) {
				case VALUE:
					if(!player.hasPermission("blacksmith.place.value")) {
						event.setCancelled(true);
						player.sendMessage(ChatColor.DARK_RED + "You are not allowed to do that!");
						event.getBlock().breakNaturally();
						return;
					}else{
						player.sendMessage(ChatColor.GREEN + "Successfully placed BlackSmith Value sign!");
					}
					break;
				case REPAIR:
					if(!player.hasPermission("blacksmith.place.repair")) {
						event.setCancelled(true);
						player.sendMessage(ChatColor.DARK_RED + "You are not allowed to do that!");
						event.getBlock().breakNaturally();
						return;
					}else{
						player.sendMessage(ChatColor.GREEN + "Successfully placed BlackSmith Repair sign!");
					}
					break;
				case UNKOWN:
					player.sendMessage(ChatColor.DARK_RED + "Sign type incorrect. Type either ''Value'' or ''Repair'' into the second line!");
					break;
			}
		}
	}
	
	@EventHandler
	public void blockBreak(BlockBreakEvent event) {
		Block b = event.getBlock();
		if(b.getState() instanceof Sign) {
			Sign s = (Sign) b.getState();
			if(this.hasCorrectFormat(s)) {
				Player player = event.getPlayer();
				Signs type = this.getType(s);

				switch(type) {
					case VALUE:
						if(!player.hasPermission("blacksmith.remove.value")) {
							event.setCancelled(true);
							player.sendMessage(ChatColor.DARK_RED + "You are not allowed to do that!");
							s.update();
							return;
						}else{
							player.sendMessage(ChatColor.GREEN + "Successfully removed BlackSmith Value sign!");
						}
						break;
					case REPAIR:
						if(!player.hasPermission("blacksmith.remove.repair")) {
							event.setCancelled(true);
							player.sendMessage(ChatColor.DARK_RED + "You are not allowed to do that!");
							s.update();
							return;
						}else{
							player.sendMessage(ChatColor.GREEN + "Successfully removed BlackSmith Repair sign!");
						}
						break;
					case UNKOWN:
						player.sendMessage(ChatColor.DARK_RED + "Sign type incorrect. Type either ''Value'' or ''Repair'' into the second line!");
						break;
				}
			}
		}
	}
}

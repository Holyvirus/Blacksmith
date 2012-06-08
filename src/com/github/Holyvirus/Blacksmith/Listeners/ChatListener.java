package com.github.Holyvirus.Blacksmith.Listeners;

import java.util.HashMap;
import java.util.logging.Level;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;

import com.github.Holyvirus.Blacksmith.BlackSmith;
import com.github.Holyvirus.Blacksmith.core.Tools.Enchanter.Enchanter;

public class ChatListener implements Listener {

	private static HashMap<String, Integer> msgCount = new HashMap<String, Integer>();
	private static HashMap<String, String> StringEnchant = new HashMap<String, String>();
	
	public static void add(Player p, Integer i){
		msgCount.put(p.getName(), i);
	}
	
	public static void remove(Player p){
		msgCount.remove(p.getName());
		p.sendMessage(ChatColor.RED + "Thank you for using the BlackSmith enchanter!");
	}
	
	@EventHandler
	public void onChat(PlayerChatEvent event){
		Player p = event.getPlayer();
		String m = event.getMessage();
		if(msgCount.containsKey(p.getName())){
			Integer c = msgCount.get(p.getName());
			if(c == 1){
				if(m.matches("^[a-zA-Z_]+ [0-9]+$")){
					String[] s = m.split(" ");
					String e = s[0];
					StringEnchant.put(p.getName(), e);
					int lvl = Integer.parseInt(s[1]);
					if(Enchanter.validateMsg1(e)){
						Enchanter.enchantValidate(p, e, lvl);
					}else{
						p.sendMessage("You have entered an invalid enchant, you may type \"/bs enchants (pg)\" for a list of enchantments!");
					}
				}else if (m.equalsIgnoreCase("exit")){
					msgCount.remove(p.getName());
					p.sendMessage(ChatColor.RED + "Thank you for using the BlackSmith enchanter!");
				}else{
					p.sendMessage(ChatColor.RED + "Please type: \"enchant\" \"Level\". You may also type \"/bs enchants (pg)\" for a list of enchants or \"exit\" to leave ");
				}
			}else if(c == 2){
				if(m.matches("yes")){
					String e = StringEnchant.get(p.getName());
					Enchanter.enchant(p, e);
				}else if(m.matches("no")){
					add(p, 1);
					p.sendMessage(ChatColor.RED + "Alright, you may try again or type \"exit\" to leave!");
				}else if (m.equalsIgnoreCase("exit")){
					msgCount.remove(p.getName());
					p.sendMessage(ChatColor.RED + "Thank you for using the BlackSmith enchanter!");
				}else{
					p.sendMessage(ChatColor.RED + "Please type \"yes\" or \"no\"!");
				}
			}
			event.setCancelled(true);
		}
	}
}

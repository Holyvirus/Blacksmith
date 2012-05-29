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
	
	public static void add(Player p, Integer i){
		msgCount.put(p.getName(), i);
	}
	
	@EventHandler
	public void onChat(PlayerChatEvent event){
		Player p = event.getPlayer();
		String m = event.getMessage();
		p.sendMessage(event.getFormat());
		p.sendMessage("size: " + msgCount.size());
		p.sendMessage("msg: " + m);
		if(msgCount.containsKey(p.getName())){
			Integer c = msgCount.get(p.getName());
			p.sendMessage("c: " + c);
			if(c == 1){
				if(m.matches("^[a-zA-Z]+ [0-9]+$")){
					String[] s = m.split(" ");
					String e = s[0];
					int lvl = Integer.parseInt(s[1]);
					Enchanter.enchant(p, e, lvl);
				}else if (m.equalsIgnoreCase("exit")){
					msgCount.remove(p.getName());
					p.sendMessage(ChatColor.RED + "Thank you for using the BlackSmith enchanter!");
				}else{
					p.sendMessage(ChatColor.RED + "Please type: \"enchant\" \"Level\". You may also type \"help (pg)\" for a list of enchants or \"exit\" to leave ");
				}
			}
			event.setCancelled(true);
		}
	}
}

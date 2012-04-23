package com.github.Holyvirus.Blacksmith.Listeners;

import com.github.Holyvirus.Blacksmith.BlackSmith;
import com.github.Holyvirus.Blacksmith.core.Eco.iEco;

import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerListener implements Listener {

	BlackSmith plugin;
	
	public PlayerListener(BlackSmith plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		iEco eH = plugin.getEcoHandler().getEngine();
	}
	
}
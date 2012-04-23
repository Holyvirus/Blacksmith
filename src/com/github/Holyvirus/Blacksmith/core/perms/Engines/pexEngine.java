package com.github.Holyvirus.Blacksmith.core.perms.Engines;

import com.github.Holyvirus.Blacksmith.BlackSmith;
import com.github.Holyvirus.Blacksmith.core.perms.Permission;

import org.bukkit.entity.Player;

public class pexEngine implements Permission {

	private BlackSmith plugin;
	
	public pexEngine(BlackSmith plugin) {
		this.plugin = plugin;
		
		
	}
	
	@Override
	public boolean has(String p, String perm) {
		Player player = plugin.getServer().getPlayer(p);
		if(player != null)
			return this.has(player, perm);
		
		return false;
	}

	@Override
	public boolean has(Player p, String perm) {
		return false;
	}

	@Override
	public boolean inGroup(String p, String group) {
		Player player = plugin.getServer().getPlayer(p);
		if(player != null)
			return this.inGroup(player, group);
		
		return false;
	}

	@Override
	public boolean inGroup(Player p, String group) {
		return false;
	}

	@Override
	public boolean hasGroup(String p) {
		Player player = plugin.getServer().getPlayer(p);
		if(player != null)
			return this.hasGroup(player);
		
		return false;
	}

	@Override
	public boolean hasGroup(Player p) {
		return false;
	}

	@Override
	public String getGroup(String p) {
		Player player = plugin.getServer().getPlayer(p);
		if(player != null)
			return this.getGroup(player);
		
		return null;
	}

	@Override
	public String getGroup(Player p) {
		return null;
	}
	
	@Override
	public boolean isEnabled() {
		return false;
	}
}

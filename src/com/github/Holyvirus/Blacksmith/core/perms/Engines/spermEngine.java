package com.github.Holyvirus.Blacksmith.core.perms.Engines;

import com.github.Holyvirus.Blacksmith.BlackSmith;
import com.github.Holyvirus.Blacksmith.core.perms.Permission;

import org.bukkit.entity.Player;

import java.util.logging.Level;

public class spermEngine implements Permission {

	private BlackSmith plugin;
	
	public spermEngine(BlackSmith plugin) {
		this.plugin = plugin;
		plugin.getLogger().log(Level.INFO, "Now using Bukkit SuperPerms!");
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
		return p.hasPermission(perm);
	}
	
	@Override
	public boolean has(String p, String perm, String world) {
		Player player = plugin.getServer().getPlayer(p);
		if(player != null)
			return this.has(player, perm, world);
		
		return false;
	}

	@Override
	public boolean has(Player p, String perm, String world) {
		return p.hasPermission(world);
	}

	@Override
	public boolean groupHasPerm(String group, String perm) {
		plugin.getLogger().log(Level.WARNING, "Superperms does not support groups!");
		return false;
	}

	@Override
	public boolean groupHasPerm(String group, String perm, String world) {
		plugin.getLogger().log(Level.WARNING, "Superperms does not support groups!");
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
		plugin.getLogger().log(Level.WARNING, "Superperms does not support groups!");
		return false;
	}
	
	@Override
	public boolean inGroup(String p, String group, String world) {
		plugin.getLogger().log(Level.WARNING, "Superperms does not support groups!");
		return false;
	}

	@Override
	public boolean inGroup(Player p, String group, String world) {
		return this.inGroup(p.getName(), group, world);
	}

	@Override
	public String getGroup(String p) {
		plugin.getLogger().log(Level.WARNING, "Superperms does not support groups!");
		return null;
	}

	@Override
	public String getGroup(Player p) {
		return this.getGroup(p.getName());
	}

	@Override
	public String getGroup(String p, String world) {
		plugin.getLogger().log(Level.WARNING, "Superperms does not support groups!");
		return null;
	}

	@Override
	public String getGroup(Player p, String world) {
		return this.getGroup(p.getName(), world);
	}

	@Override
	public String[] getGroups(String p) {
		plugin.getLogger().log(Level.WARNING, "Superperms does not support groups!");
		return null;
	}

	@Override
	public String[] getGroups(Player p) {
		return this.getGroups(p.getName());
	}

	@Override
	public String[] getGroups(String p, String world) {
		plugin.getLogger().log(Level.WARNING, "Superperms does not support groups!");
		return null;
	}

	@Override
	public String[] getGroups(Player p, String world) {
		return this.getGroups(p.getName(), world);
	}
	
	@Override
	public boolean isEnabled() {
		return true;
	}
}

package com.github.Holyvirus.Blacksmith.core.perms;

import org.bukkit.entity.Player;

public interface Permission {

	public boolean has(String p, String perm);
	public boolean has(Player p, String perm);
	
	public boolean inGroup(String p, String group);
	public boolean inGroup(Player p, String group);

	public boolean hasGroup(String p);
	public boolean hasGroup(Player p);

	public String getGroup(String p);
	public String getGroup(Player p);
	
	public boolean isEnabled();
}

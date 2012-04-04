package com.github.Holyvirus.Blacksmith;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.plugin.Plugin;

import com.iCo6.iConomy;

import cosine.boseconomy.BOSEconomy;

import java.util.logging.Level;

public class BPluginListener
	implements Listener
{
  private final BlackSmith plugin;
  public static iConomy iconomy;

  public BPluginListener(BlackSmith plugin)
  {
	 this.plugin = plugin;
  }

  @EventHandler
  public void onPluginEnable(PluginEnableEvent event) {
	 if ((event.getPlugin().getDescription().getName().equals("iConomy")) && (this.plugin.getIconomyState()))
	 {
		 Plugin p = (iConomy)event.getPlugin();
		 if(p != null && p.isEnabled()) {
			this.plugin.setIco((iConomy) p);
			this.plugin.getLogger().log(Level.INFO, "attached to iConomy.");
		 }
	 }
	 else if ((event.getPlugin().getDescription().getName().equals("BOSEconomy")) && (this.plugin.getBoseconomyState()))
	 {
		 Plugin p = (BOSEconomy)event.getPlugin();
		 if(p != null && p.isEnabled()) {
			this.plugin.setBose((BOSEconomy) p);
			this.plugin.getLogger().log(Level.INFO, "attached to BOSEconomy.");
		 }
	 }
  }

  @EventHandler
  public void onPluginDisable(PluginDisableEvent event) {
	 if ((event.getPlugin().getDescription().getName().equals("iConomy")) && (this.plugin.getIconomyState()))
	 {
		iconomy = null;
		this.plugin.getLogger().log(Level.INFO, "lost connection to iConomy.");
	 }
	 else if ((event.getPlugin().getDescription().getName().equals("BOSEconomy")) && (this.plugin.getBoseconomyState()))
	 {
		this.plugin.setBose(null);
		this.plugin.getLogger().log(Level.INFO, "lost connection to BOSEconomy.");
	 }
  }
}
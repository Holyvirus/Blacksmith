package com.github.Holyvirus.Blacksmith;

import org.bukkit.Server;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.Plugin;

import com.iCo6.iConomy;
import cosine.boseconomy.BOSEconomy;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.logging.Logger;
import java.util.logging.Level;

public class BlackSmith extends JavaPlugin
{
	private YamlConfiguration config = null;
	private BOSEconomy bose;
	private iConomy ico;
	private BPluginListener bplugl;
	private boolean bIconomy = false;
	private boolean bBoseconomy = false;
	private boolean bUseMat = false;
	private static Server Server;
	public static final Logger log = Logger.getLogger("Minecraft");
	public static int woodBase = 20;
	public static int stoneBase = 50;
	public static int ironBase = 100;
	public static int goldBase = 75;
	public static int diamondBase = 200;
	
	@Override
	public void onDisable()
	{
		log.log(Level.INFO, "[Blacksmith] disabled.");
	}

	@Override
  public void onEnable()
  {
	 Server = this.getServer();
	 loadconfig();
	 int baseTmp;
	 baseTmp = this.config.getInt("BlackSmith.Prices.woodbase");
	 if(baseTmp > 0) woodBase = baseTmp;
	 
	 baseTmp = this.config.getInt("BlackSmith.Prices.stonebase");
	 if(baseTmp > 0) stoneBase = baseTmp;
	 
	 baseTmp = this.config.getInt("BlackSmith.Prices.ironbase");
	 if(baseTmp > 0) ironBase = baseTmp;
	 
	 baseTmp = this.config.getInt("BlackSmith.Prices.goldbase");
	 if(baseTmp > 0) goldBase = baseTmp;
	 
	 baseTmp = this.config.getInt("BlackSmith.Prices.diamondbase");
	 if(baseTmp > 0) diamondBase = baseTmp;
	 
	 String ecoEn = this.config.getString("BlackSmith.global.ecoEngine");
	 if(ecoEn == null) {
		 log.log(Level.WARNING, "[Blacksmith] unable to detect config node, using default! (BlackSmith.global.ecoEngine)");
		 this.bUseMat = true;
	 }else{
		 if(ecoEn.equalsIgnoreCase("BOSECONOMY")) {
			 this.bBoseconomy = true;
		 }else if(ecoEn.equalsIgnoreCase("ICONOMY")) {
			 this.bIconomy = true;
		 }else if(ecoEn.equalsIgnoreCase("MATERIALS")) {
			 this.bUseMat = true;
		 }else{
			 log.log(Level.WARNING, "[Blacksmith] Invalid value detected at config node, using default! (BlackSmith.global.ecoEngine)");
			 this.bBoseconomy = true;
		 }
	 }

	 this.bplugl = new BPluginListener(this);

	 PluginManager pm = getServer().getPluginManager();
	 pm.registerEvents(this.bplugl, this);
	 
	 if(this.bBoseconomy == true) {
		 if(this.bose == null) {
			Plugin p = (BOSEconomy)Server.getPluginManager().getPlugin("BOSEconomy");
			
			if(p != null && p.isEnabled()) {
				this.setBose((BOSEconomy) p);
				
				if(this.bose == null) {
					log.log(Level.WARNING, "[Blacksmith] Failed to hook into BOSEconomy!");
				}else{
					log.log(Level.INFO, "[Blacksmith] Succesfully hooked into BOSEconomy!");
				}
			}
		}
	 }else if(this.bIconomy == true) {
		 if(this.ico == null) {
			Plugin p = (iConomy)Server.getPluginManager().getPlugin("iConomy");
			
			if(p != null && p.isEnabled()) {
				this.setIco((iConomy) p);
				
				if(this.ico == null) {
					log.log(Level.WARNING, "[Blacksmith] Failed to hook into iConomy!");
				}else{
					log.log(Level.INFO, "[Blacksmith] Succesfully hooked into iConomy!");
				}
			}
		}
	 }
	 
	 pm.registerEvents(new BBlockListener(this), this);
	 pm.registerEvents(new BPlayerListener(this), this);
	 
	 log.log(Level.INFO, "[Blacksmith] enabled.");
  }

  public void setBose(BOSEconomy bose) {
	 this.bose = bose; 
  }

  public BOSEconomy getBose() {
	 return this.bose; 
  }

  public void setIco(iConomy ico) {
	  this.ico = ico;
  }

  public iConomy getIco() {
	  return this.ico;
  }
  
  public YamlConfiguration getConf() {
	  return this.config;
  }

  public boolean getIconomyState()
  {
	 return bIconomy; 
  }
  
  public boolean getBoseconomyState() { 
	  return bBoseconomy;
  }
  
  public boolean getMatState() { 
	  return bUseMat;
  }

  public void loadconfig()
  {
		File configFile = new File(getDataFolder(), "config.yml");
		if(!configFile.exists()) {
			getDataFolder().mkdir();
			getDataFolder().setWritable(true);
			getDataFolder().setExecutable(true);
			
			extractDefaultFile("config.yml");
		}
		
		this.config = YamlConfiguration.loadConfiguration(configFile);
  }
	
	private void extractDefaultFile(String file) {
		File configFile = new File(getDataFolder(), file);
		if (!configFile.exists()) {
			InputStream input = this.getClass().getResourceAsStream("/com/github/Holyvirus/Blacksmith/Default/" + file);
			if (input != null) {
				FileOutputStream output = null;

				try {
					output = new FileOutputStream(configFile);
					byte[] buf = new byte[8192];
					int length = 0;

					while ((length = input.read(buf)) > 0) {
						output.write(buf, 0, length);
					}

					log.log(Level.INFO, "[Blacksmith] copied default file: " + file);
				} catch (Exception e) {
					Server.getPluginManager().disablePlugin(this);
					log.log(Level.SEVERE, "[Blacksmith] AAAAAAH!!! Can't extract the requested file!!", e);
					return;
				} finally {
					try {
						if (input != null) {
							input.close();
						}
					} catch (Exception e) {
						Server.getPluginManager().disablePlugin(this);
						log.log(Level.SEVERE, "[Blacksmith] AAAAAAH!!! Severe error!!", e);	
					}
					try {
						if (output != null) {
							output.close();
						}
					} catch (Exception e) {
						Server.getPluginManager().disablePlugin(this);
						log.log(Level.SEVERE, "[Blacksmith] AAAAAAH!!! Severe error!!", e);
					}
				}
			}
		}
	}
}
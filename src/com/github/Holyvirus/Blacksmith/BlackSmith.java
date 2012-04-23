package com.github.Holyvirus.Blacksmith;

import com.github.Holyvirus.Blacksmith.core.config;
import com.github.Holyvirus.Blacksmith.core.Eco.Eco;
import com.github.Holyvirus.Blacksmith.core.perms.PermHandler;

import org.bukkit.Server;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.logging.Logger;
import java.util.logging.Level;

public class BlackSmith extends JavaPlugin {
	
	public static final Logger log = Logger.getLogger("Minecraft");
	
	private static BlackSmith plugin;
	private static Server Server;
	private Eco econHandler;
	private PermHandler permHandler;
	private String name, dir, pubName;
	private String bName = "Spikey Hamer";
	
	//Clear this out later
	public static int woodBase = 20;
	public static int stoneBase = 50;
	public static int ironBase = 100;
	public static int goldBase = 75;
	public static int diamondBase = 200;
	public static int chainBase = 600;
	
	private void setPlugin() {
		plugin = this;
	}
	
	public BlackSmith() {
		this.setPlugin();
	}
	
	@Override
	public void onEnable() {
		Server = this.getServer();
		
		this.name = getDescription().getName();
		this.dir = getDataFolder().toString();
		
		File configFile = new File(getDataFolder(), "conf.yml");
		if(!configFile.exists()) {
			getDataFolder().mkdir();
			getDataFolder().setWritable(true);
			getDataFolder().setExecutable(true);
			
			extractDefaultFile("conf.yml");
		}
		
		config conf = config.Obtain();
		try {
			conf.loadConfig(configFile);
			
			pubName = conf.getString("BlackSmith.global.name");
			
			if(conf.getString("BlackSmith.Economy.type").equalsIgnoreCase("HYBRID") || !conf.getString("BlackSmith.Economy.type").equalsIgnoreCase("MATERIALS")) {
				econHandler = new Eco(this, conf.getString("BlackSmith.Economy.Engine"));
				if(conf.getString("BlackSmith.Economy.type").equalsIgnoreCase("HYBRID")) {
					//set up materials aswell
					
				}
			}else{
				//use materials instead
				
			}
			
			permHandler = new PermHandler(this, conf.getString("BlackSmith.permissions.Engine"));
			//msgHandler = new Messages(this);
			
			if(econHandler.isLoaded()) {
				log.log(Level.INFO, "[" + this.name + "](" + this.bName + ") Was successfully enabled!");
			}else{
				log.log(Level.WARNING, "[" + this.name + "] Could not load economy engine yet!");
				log.log(Level.WARNING, "[" + this.name + "] Errors might occur if you do not see '[" + name + "]Successfully hooked into (whichever) Engine!' after this message!");
			}
		}catch(Exception e) {
			log.log(Level.SEVERE, "[" + this.name + "](" + this.bName + ") Failed to load!");
			if(conf.getBoolean("BlackSmith.global.debug"))
				log.log(Level.INFO, "" + e);
			Server.getPluginManager().disablePlugin(this);
		}
	}

	@Override
	public void onDisable() {
		log.log(Level.INFO, "[" + this.name + "] Was successfully disabled!");
	}
	
	public String getPluginName() {
		return this.name;
	}
	
	public String getPubName() {
		return this.pubName;
	}
	
	public String getDir() {
		return this.dir;
	}
	
	public Eco getEcoHandler() {
		return this.econHandler;
	}
	
	public PermHandler getPermHandler() {
		return this.permHandler;
	}
	
	public static BlackSmith getPlugin() {
		return plugin;
	}
	
	public void extract(String file) {
		extractDefaultFile(file);
	}

	private void extractDefaultFile(String file) {
		File configFile = new File(getDataFolder(), file);
		if (!configFile.exists()) {
			InputStream input = this.getClass().getResourceAsStream("/com/github/Holyvirus/" + name + "/Default/" + file);
			if (input != null) {
				FileOutputStream output = null;

				try {
					output = new FileOutputStream(configFile);
					byte[] buf = new byte[8192];
					int length = 0;

					while ((length = input.read(buf)) > 0) {
						output.write(buf, 0, length);
					}

					log.log(Level.INFO, "[" + name + "] copied default file: " + file);
				} catch (Exception e) {
					Server.getPluginManager().disablePlugin(this);
					log.log(Level.SEVERE, "[" + name + "] AAAAAAH!!! Can't extract the requested file!!", e);
					return;
				} finally {
					try {
						if (input != null) {
							input.close();
						}
					} catch (Exception e) {
						Server.getPluginManager().disablePlugin(this);
						log.log(Level.SEVERE, "[" + name + "] AAAAAAH!!! Severe error!!", e);	
					}
					try {
						if (output != null) {
							output.close();
						}
					} catch (Exception e) {
						Server.getPluginManager().disablePlugin(this);
						log.log(Level.SEVERE, "[" + name + "] AAAAAAH!!! Severe error!!", e);
					}
				}
			}
		}
	}
}
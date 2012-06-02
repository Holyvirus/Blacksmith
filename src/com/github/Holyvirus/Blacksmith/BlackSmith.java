package com.github.Holyvirus.Blacksmith;

import com.github.Holyvirus.Blacksmith.core.config;
import com.github.Holyvirus.Blacksmith.core.Eco.Eco;
import com.github.Holyvirus.Blacksmith.core.Eco.mEco;
import com.github.Holyvirus.Blacksmith.core.Items.Items;
import com.github.Holyvirus.Blacksmith.core.Tools.Materials.Materials;
import com.github.Holyvirus.Blacksmith.core.perms.PermHandler;
import com.github.Holyvirus.Blacksmith.Listeners.*;

import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
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
	private mEco matHandler;
	private PermHandler permHandler;
	private Items itemHandler;
	private String name, dir, pubName;
	private String bName = "Spikey Hamer";
	private Boolean useEco = true;
	
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
			itemHandler = new Items(this);
			if(conf.getString("BlackSmith.Economy.type").equalsIgnoreCase("HYBRID") || !conf.getString("BlackSmith.Economy.type").equalsIgnoreCase("MATERIALS")) {
				econHandler = new Eco(this, conf.getString("BlackSmith.Economy.Engine"));
				if(conf.getString("BlackSmith.Economy.type").equalsIgnoreCase("HYBRID")) {
					//set up materials aswell
					Materials.getInstance();
					matHandler = (mEco) new Eco(this, "Materials").getEngine();
				}
			}else{
				//use materials instead
				useEco = false;
				Materials.getInstance();
				matHandler = (mEco) new Eco(this, "Materials").getEngine();
			}
			
			if(conf.getBoolean("BlackSmith.permissions.usePermissions")) {
				permHandler = new PermHandler(this, conf.getString("BlackSmith.permissions.Engine"), conf.getBoolean("BlackSmith.permissions.opHasPerms"));
			}else{
				permHandler = new PermHandler(this, "NOPERM", true);
			}
			//msgHandler = new Messages(this);

			getServer().getPluginManager().registerEvents(new BlockListener(this), this);
			getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
			getServer().getPluginManager().registerEvents(new ChatListener(), this);
			
			if(!permHandler.isEnabled()) {
				log.log(Level.WARNING, "[" + this.name + "] Could not load permissions engine yet!");
				log.log(Level.WARNING, "[" + this.name + "] Errors might occur if you do not see '[" + name + "]Successfully hooked into (whichever)!' after this message!");
			}
			
			if(useEco) {
				if(econHandler.isLoaded()) {
					log.log(Level.INFO, "[" + this.name + "](" + this.bName + ") Was successfully enabled!");
				}else{
					log.log(Level.WARNING, "[" + this.name + "] Could not load economy engine yet!");
					log.log(Level.WARNING, "[" + this.name + "] Errors might occur if you do not see '[" + name + "]Successfully hooked into (whichever) Engine!' after this message!");
				}
			}else{
				log.log(Level.INFO, "[" + this.name + "] now using materials");
				log.log(Level.INFO, "[" + this.name + "](" + this.bName + ") Was successfully enabled!");
			}
			
		}catch(Exception e) {
			log.log(Level.SEVERE, "[" + this.name + "](" + this.bName + ") Failed to load!");
			if(conf.getBoolean("BlackSmith.global.debug")){
				e.printStackTrace();
			}else{
				log.log(Level.INFO, "" + e);
			}
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
	
	public mEco getMatEngine() {
		return this.matHandler;
	}
	
	public Items getItemHandler() {
		return this.itemHandler;
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
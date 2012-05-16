package com.github.Holyvirus.Blacksmith.core.Tools.Materials;

import java.io.File;
import java.util.*;
import java.util.logging.Level;

import org.bukkit.configuration.file.YamlConfiguration;

import com.github.Holyvirus.Blacksmith.BlackSmith;
import com.github.Holyvirus.Blacksmith.core.Items.ItemID;
import com.github.Holyvirus.Blacksmith.core.Items.Items;

public class Materials {
	
	private static Materials instance;
	
	private BlackSmith plugin;
	private Items iH;
	private YamlConfiguration yaml;
	private HashMap<String, HashMap<ItemID, Integer>> list = new HashMap<String, HashMap<ItemID, Integer>>();
	
	private void parseCosts() {
		Set<String> tools = yaml.getConfigurationSection("Material").getKeys(false);
		if(tools == null) {
			BlackSmith.log.log(Level.INFO, "[" + plugin.getName() + "] There are no item types specified in the MaterialCost.yml file?!");
			return;
		}
		
		for(String tool : tools) {
			HashMap<ItemID, Integer> tmp = new HashMap<ItemID, Integer>();
			Set<String> mats = yaml.getConfigurationSection("Material.tool").getKeys(false);
			for(String mat : mats) {
				ItemID iID = iH.getItemIDByName(mat);
				if(iID == null) {
					BlackSmith.log.log(Level.INFO, "[" + plugin.getName() + "] Invalid key detected in MaterialCost.yml (Materials." + tool + "." + tool + ")");
					continue;
				}
				
				int amount = yaml.getInt("Materials." + tool + "." + mat);
				if(amount <= 0) {
					BlackSmith.log.log(Level.INFO, "[" + plugin.getName() + "] Invalid amount detected for MaterialCost.yml (Materials." + tool + "." + tool + ")");
					return;
				}
				
				tmp.put(iID, amount);
			}
			
			list.put(tool, tmp);
		}
	}
	
	private Materials() {
		this.plugin = BlackSmith.getPlugin();
		iH = plugin.getItemHandler();
		File yamlFile = new File(this.plugin.getDir(), "MaterialCost.yml");
		if(!yamlFile.exists()){
			plugin.getLogger().log(Level.INFO, "[Blacksmith] MaterialCost.yml did not exist...extracting file now!");
			try{
				plugin.extract("MaterialCost.yml");
				BlackSmith.log.log(Level.INFO, "[Blacksmith] MaterialCost.yml was successfully extracted!");
			}catch(Exception e){
				BlackSmith.log.log(Level.SEVERE, "[Blacksmith] MaterialCost.yml was NOT successfully extracted!");
				e.printStackTrace();
			}
		}else{
			yaml = YamlConfiguration.loadConfiguration(yamlFile);
			this.parseCosts();
		}
	}
	
	public List<String> getCostString(String name) {
		if(!list.containsKey(name))
			return null;
		
		List<String> sL = new ArrayList<String>();
		HashMap<ItemID, Integer> tmp = list.get(name);
		for(Map.Entry<ItemID, Integer> entry : tmp.entrySet()) {
			ItemID iID = entry.getKey();
			StringBuilder s = new StringBuilder();
			s.append("It will cost you ");
			s.append(entry.getValue());
			s.append(" of " + iH.getItemNameByID(iID.getId(), iID.getType()));
		}
		
		return sL;
	}
	
	public HashMap<ItemID, Integer> getCostMap(String name) {
		if(!list.containsKey(name))
			return null;
		
		return list.get(name);
	}
	
	public static Materials getInstance() {
		if(instance == null || !(instance instanceof Materials))
			instance = new Materials();
		
		return instance;
	}
}

package com.github.Holyvirus.Blacksmith.core.Tools.Materials;

import java.io.File;
import java.util.HashMap;
import java.util.Set;
import java.util.logging.Level;

import org.bukkit.configuration.file.YamlConfiguration;

import com.github.Holyvirus.Blacksmith.BlackSmith;
import com.github.Holyvirus.Blacksmith.core.Items.ItemID;
import com.github.Holyvirus.Blacksmith.core.Items.Items;

public class Materials {
	
	private BlackSmith plugin;
	Items Items;
	private YamlConfiguration Mats;
	private HashMap<String, HashMap<ItemID, Integer>> Mat = new HashMap<String, HashMap<ItemID, Integer>>();
	
	public Materials(){
		File MatsFile = new File(this.plugin.getDir(), "MaterialCost.yml");
		if(!MatsFile.exists()){
			plugin.getLogger().log(Level.INFO, "[Blacksmith] MaterialCost.yml did not exist...extracting file now!");
			try{
				plugin.extract("MaterialCost.yml");
				plugin.getLogger().log(Level.INFO, "[Blacksmith] MaterialCost.yml was successfully extracted!");
			}catch(Exception e){
				plugin.getLogger().log(Level.SEVERE, "[Blacksmith] MaterialCost.yml was NOT successfully extracted!");
				e.printStackTrace();
			}
		}else{
			Mats = YamlConfiguration.loadConfiguration(MatsFile);
		}
	}
	public HashMap<String, HashMap<ItemID, Integer>> getCost(String name){
		Set<String> repairers = Mats.getConfigurationSection("Material." + name).getKeys(false);
		if(!Mats.getConfigurationSection("Material").getKeys(false).contains(name)){
			plugin.getLogger().log(Level.SEVERE, "[Blacksmith] The key: \"" + name + "\" does not exist in the MaterialCost.yml!");
			return null;
		}else{
			HashMap<ItemID, Integer> itemAmt = new HashMap<ItemID, Integer>();
				for(String ramt : repairers){
					int amt = Integer.parseInt(ramt);
					ItemID id = Items.getItemIDByName(name);
					itemAmt.put(id, amt);
				}
			Mat.put(name, itemAmt);
		}
		return Mat;
	}
}

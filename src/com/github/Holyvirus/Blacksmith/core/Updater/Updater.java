package com.github.Holyvirus.Blacksmith.core.Updater;

import java.net.URL;
import java.util.logging.Level;

import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import com.github.Holyvirus.Blacksmith.BlackSmith;
import com.github.Holyvirus.Blacksmith.core.config;

public class Updater {

	private BlackSmith plugin;
	private int tID;
	private config conf = config.Obtain();

	private boolean outOfDate = false;
	private String newVersion = "";

	private void start() {
		tID = this.plugin.scheduleAsyncRepeatingTask(new Runnable() {
			@Override
			public void run() {
				newVersion = updateCheck(plugin.getDescription().getVersion());
				if(isNewer(newVersion, plugin.getDescription().getVersion())) {
					outOfDate = true;
					plugin.log.log(Level.WARNING, "[" + plugin.getName() + "] " + newVersion + " has been released! You are currently running: " + plugin.getDescription().getVersion());
					Bukkit.getServer().broadcastMessage(ChatColor.RED + "A new version of BlackSmith has just ben released! You are currently running: " + plugin.getDescription().getVersion() + " while the latest version is: " + newVersion + "!");
				}
			}
		}, 0L, 432000L);
	}

	public Updater(BlackSmith plugin) {
		this.plugin = plugin;
		this.start();
	}

	public void stop() {
		if(!Double.isNaN(tID)) {
			plugin.getServer().getScheduler().cancelTask(tID);
		}
	}

	public String updateCheck(String version) {
		String uri = "http://dev.bukkit.org/server-mods/blacksmith_2/files.rss";
		try {
			URL url = new URL(uri);
			Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(url.openConnection().getInputStream());
			doc.getDocumentElement().normalize();
			Node firstNode = doc.getElementsByTagName("item").item(0);
			if(firstNode.getNodeType() == 1) {
				NodeList firstElementTagName = ((Element)firstNode).getElementsByTagName("title");
				NodeList firstNodes = ((Element)firstElementTagName.item(0)).getChildNodes();
				return firstNodes.item(0).getNodeValue().replace("Blacksmith", "").replaceAll(" \\(([a-zA-Z ]+)\\)", "").trim();
			}
		}catch (Exception e) {	
		}

		return version;
	}

	public boolean isNewer(String newVersion, String version) {
		String[] nv = newVersion.replaceAll("\\.[a-zA-Z]+", "").split("\\.");
		String[] v = version.replaceAll("\\.[a-zA-Z]+", "").split("\\.");
		Boolean isNew = false;
		Boolean prevIsEqual = false; 

		for(int i = 0; i < nv.length; i++) {
			int tn = Integer.parseInt(nv[i]);
			int tv = 0;
			if(v.length - 1 >= i)
				tv = Integer.parseInt(v[i]);

			if(tn > tv) {
				if(i == 0 || prevIsEqual == true) {
					isNew = true;
					break;
				}
			}else if(tn == tv) {
				prevIsEqual = true;
			}else{
				break;
			}

		}

		return isNew;
	}

	public Boolean isOutOfDate() {
		return this.outOfDate;
	}

	public String getNewVersion() {
		return this.newVersion;
	}
}

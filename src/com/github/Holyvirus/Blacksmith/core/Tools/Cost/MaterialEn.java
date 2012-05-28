package com.github.Holyvirus.Blacksmith.core.Tools.Cost;

import com.github.Holyvirus.Blacksmith.BlackSmith;
import com.github.Holyvirus.Blacksmith.core.Eco.mEco;
import com.github.Holyvirus.Blacksmith.core.Items.ItemID;
import com.github.Holyvirus.Blacksmith.core.Misc.Misc;
import com.github.Holyvirus.Blacksmith.core.Tools.Materials.Materials;

import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class MaterialEn {

	private static boolean FC = true;
	private static mEco mE;
	private static Materials mH;
	
	private static void init() {
		FC = false;
		mH = Materials.getInstance();
		
		if(null != BlackSmith.getPlugin().getMatEngine())
			mE = BlackSmith.getPlugin().getMatEngine();
	}
	
	public static Boolean useMatEn() {
		if(FC)
			init();
		
		return mE != null;
	}
	
	public static List<String> calcCost(ItemStack i, Boolean asString) {
		if(FC)
			init();
		
		List<String> s = new ArrayList<String>();
		
		if(i == null) {
			s.add("No item not given!");
			return s;
		}
		
		String item = Misc.getType(i).toString() + Misc.getKind(i).toString();
		
		HashMap<ItemID, Integer> m = mH.getCostMap(item.toLowerCase());
		
		
		return s;
	}
}

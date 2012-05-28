package com.github.Holyvirus.Blacksmith.core.Misc;

public enum ToolType {
	LEATHER("leather"),
	WOOD("wood"),
	STONE("stone"),
	IRON("iron"),
	GOLD("gold"),
	DIAMOND("chain"),
	CHAIN("chain"),
	
	SWORD("sword"),
	SPADE("spade"),
	AXE("axe"),
	PICKAXE("pick"),
	HOE("hoe"),
	HELMET("helmet"),
	CHEST("chest"),
	LEG("pants"),
	BOOT("boots"),
	BOW("bow"),
	ROD("fishingrod"),
	FLINT_AND_STEEL("flint"),
	SHEARS("shears"),
	
	INVALID("invalid");
	
	private String s = "";
	
	private ToolType(String s) {
		this.s = s;
	}
	
	@Override
	public String toString() {
		return s;
	}
}

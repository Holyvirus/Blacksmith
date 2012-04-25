package com.github.Holyvirus.Blacksmith.core.Eco;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public abstract class mEco implements iEco {

	public abstract int getBalance(String p, ItemStack i);
	public abstract int getBalance(Player p, ItemStack i);

	public abstract boolean withdraw(String p, ItemStack i, int amount);
	public abstract boolean withdraw(Player p, ItemStack i, int amount);
	
	public abstract boolean deposit(String p, ItemStack i, int amount);
	public abstract boolean deposit(Player p, ItemStack i, int amount);
	
	@Override
	public double getBalance(Player player) {
		return 0D;
	}

	@Override
	public double getBalance(String player) {
		return 0D;
	}

	@Override
	public boolean withdraw(Player player, double amount) {
		return false;
	}

	@Override
	public boolean withdraw(String player, double amount) {
		return false;
	}

	@Override
	public boolean deposit(Player player, double amount) {
		return false;
	}

	@Override
	public boolean deposit(String player, double amount) {
		return false;
	}
}

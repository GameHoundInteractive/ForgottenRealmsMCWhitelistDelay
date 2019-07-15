package com.GHStudios.WhitelistDelay;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.GHStudios.WhitelistDelay.commands.MainCommands;
import com.GHStudios.WhitelistDelay.core.Config;
import com.GHStudios.WhitelistDelay.listener.PlayerEvents;

public class Main extends JavaPlugin
{
	public static Plugin plugin;
	public static long waitTime;
	public static List<UUID> whitelistUUID;

	@Override
	public void onDisable() {
		Main.plugin = null;
	}

	@Override
	public void onEnable() {
		Config.setup(Main.plugin = this);
		final int wait = Config.config.getInt("WaitTime");
		Main.waitTime = System.currentTimeMillis() / 1000L + wait;
		registerEvents((Plugin)this, (Listener)new PlayerEvents());
		this.loadCommands();
		final List<String> uuid = Config.config.getStringList("WhitelistedUUID");
		for (final String string : uuid) {
			Main.whitelistUUID.add(UUID.fromString(string));
		}
	}

	private void loadCommands() {
		this.getCommand("wd").setExecutor(new MainCommands());
	}

	private static void registerEvents(final Plugin plugin, final Listener... listeners) {
		for (final Listener listener : listeners) {
			Bukkit.getServer().getPluginManager().registerEvents(listener, plugin);
		}
	}

	static {
		Main.whitelistUUID = new ArrayList<>();
	}
}

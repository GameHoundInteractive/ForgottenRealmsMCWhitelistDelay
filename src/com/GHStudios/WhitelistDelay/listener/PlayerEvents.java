package com.GHStudios.WhitelistDelay.listener;

import java.util.UUID;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

import com.GHStudios.WhitelistDelay.Main;
import com.GHStudios.WhitelistDelay.core.Config;
import com.GHStudios.WhitelistDelay.util.TextUtil;

public class PlayerEvents implements Listener
{
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerPreJoin(final AsyncPlayerPreLoginEvent event) {
		final long currentTime = System.currentTimeMillis() / 1000L;
		final UUID playerUUID = event.getUniqueId();
		if (currentTime <= Main.waitTime && !Main.whitelistUUID.contains(playerUUID)) {
			final int timeLeft = (int)(Main.waitTime - currentTime);
			event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_WHITELIST, TextUtil.colorize(Config.config.getString("WhitelistMessage").replaceAll("%time%", String.valueOf(timeLeft))));
		}
	}
}

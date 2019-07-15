package com.GHStudios.WhitelistDelay.util;

import org.bukkit.ChatColor;

public class TextUtil
{
	public static String colorize(final String message) {
		return ChatColor.translateAlternateColorCodes('&', message);
	}
}

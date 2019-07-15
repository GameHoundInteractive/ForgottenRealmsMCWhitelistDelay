package com.GHStudios.WhitelistDelay.commands;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.GHStudios.WhitelistDelay.core.Config;
import com.GHStudios.WhitelistDelay.util.TextUtil;

public class MainCommands implements CommandExecutor
{
	@Override
	public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
		if (cmd.getName().equalsIgnoreCase("wd")) {
			if (sender.hasPermission("whitelist.commands")) {
				if (args.length == 0 || args.length > 2) {
					this.helpMessage(sender);
				}
				else if (args.length == 1) {
					if (args[0].equalsIgnoreCase("add")) {
						this.sendMessage(sender, "&7Usage: &f/wd add <online player>");
					}
					else if (args[0].equalsIgnoreCase("remove")) {
						this.sendMessage(sender, "&7Usage: &f/wd remove <online player>");
					}
					else if (args[0].equalsIgnoreCase("time")) {
						this.sendMessage(sender, "&7Usage: &f/wd time <integer>");
					}
					else {
						this.helpMessage(sender);
					}
				}
				else if (args.length == 2) {
					if (args[0].equalsIgnoreCase("add") || args[0].equalsIgnoreCase("remove")) {
						if (sender.getServer().getPlayer(args[1]) == null) {
							this.sendMessage(sender, "&cPlayer not found.");
							return true;
						}
						final Player targetPlayer = Bukkit.getServer().getPlayer(args[1]);
						final List<String> uuid = Config.config.getStringList("WhitelistedUUID");
						if (args[0].equalsIgnoreCase("add")) {
							uuid.add(targetPlayer.getUniqueId().toString());
							Config.config.set("WhitelistedUUID", uuid);
							Config.saveConfig();
							this.sendMessage(sender, "&aAdded " + targetPlayer.getName() + ": " + targetPlayer.getUniqueId().toString());
						}
						else {
							uuid.remove(targetPlayer.getUniqueId().toString());
							Config.config.set("WhitelistedUUID", uuid);
							Config.saveConfig();
							this.sendMessage(sender, "&cRemoved " + targetPlayer.getName() + ": " + targetPlayer.getUniqueId().toString());
						}
						return true;
					}
					else {
						if (args[0].equalsIgnoreCase("time")) {
							Config.config.set("WaitTime", Integer.valueOf(args[1]));
							Config.saveConfig();
							this.sendMessage(sender, "&aSet time to: " + args[1]);
							return true;
						}
						this.helpMessage(sender);
					}
				}
			}
			else {
				final String permissionMessage = Config.config.getString("PermissionDenied");
				this.sendMessage(sender, permissionMessage);
			}
			return true;
		}
		return false;
	}

	private void helpMessage(final CommandSender sender) {
		this.sendMessage(sender, "&f/wd &7- Shows help menu");
		this.sendMessage(sender, "&f/wd add <player> &7- Adds player UUID to bypass whitelist");
		this.sendMessage(sender, "&f/wd remove <player> &7- Removes player UUID to bypass whitelist");
		this.sendMessage(sender, "&f/wd time <integer> &7- Sets the length of whitelist (seconds)");
	}

	private void sendMessage(final CommandSender sender, final String message) {
		sender.sendMessage(TextUtil.colorize(message));
	}
}

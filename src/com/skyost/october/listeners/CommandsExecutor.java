package com.skyost.october.listeners;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.skyost.october.Halloween;
import com.skyost.october.util.ScareUtils;

public class CommandsExecutor implements CommandExecutor {
	
	@Override
	public boolean onCommand(final CommandSender sender, Command cmd, String label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("scare")) {
			final Player player;
			switch(args.length) {
			case 1:
				player = Bukkit.getPlayer(args[0]);
				if(player != null) {
					if(Halloween.config.Worlds.contains(player.getWorld().getName())) {
						int id = new Random().nextInt(5) + 1;
						ScareUtils.scarePlayer(player, id);
						sender.sendMessage(ChatColor.GREEN + player.getName() + " is scared !");
					}
					else {
						sender.sendMessage(ChatColor.RED + "This player is on a world where the plugin is disabled.");
					}
				}
				else {
					sender.sendMessage(ChatColor.RED + "This player is not connected !");
				}
				break;
			case 2:
				try {
				player = Bukkit.getPlayer(args[0]);
					if(player != null) {
						if(Halloween.config.Worlds.contains(player.getWorld().getName())) {
							ScareUtils.scarePlayer(player, Integer.parseInt(args[1]));
							sender.sendMessage(ChatColor.GREEN + player.getName() + " is scared !");
						}
						else {
							sender.sendMessage(ChatColor.RED + "This player is on a world where the plugin is disabled.");
						}
					}
					else {
						sender.sendMessage(ChatColor.RED + "This player is not connected !");
					}
				}
				catch(Exception ex) {
					ex.printStackTrace();
					sender.sendMessage(ChatColor.RED + "/scare <player> [id]");
				}
				break;
			default:
				sender.sendMessage(ChatColor.RED + "/scare <player> [id]");
				break;
			}
		}
		return true;
	}

}

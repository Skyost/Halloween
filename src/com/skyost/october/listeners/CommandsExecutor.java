package com.skyost.october.listeners;

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
		final Player player;
		if(cmd.getName().equalsIgnoreCase("scare")) {
			switch(args.length) {
			case 0:
				Player[] online = Bukkit.getOnlinePlayers();
				player = online[Halloween.rand.nextInt(online.length)];
				if(player != null) {
					if(Halloween.config.Worlds.contains(player.getWorld().getName())) {
						int id = Halloween.rand.nextInt(ScareUtils.getMaxID()) + 1;
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
			case 1:
				player = Bukkit.getPlayer(args[0]);
				if(player != null) {
					if(Halloween.config.Worlds.contains(player.getWorld().getName())) {
						int id = Halloween.rand.nextInt(ScareUtils.getMaxID()) + 1;
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
			default:
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
					sender.sendMessage(ChatColor.RED + "/scare <player> [id]");
				}
				break;
			}
		}
		else if(cmd.getName().equalsIgnoreCase("haunt")) {
			switch(args.length) {
			case 0:
				Player[] online = Bukkit.getOnlinePlayers();
				player = online[Halloween.rand.nextInt(online.length)];
				if(player != null) {
					if(Halloween.config.Worlds.contains(player.getWorld().getName())) {
						Halloween.haunted.add(player.getName());
						sender.sendMessage(ChatColor.GREEN + player.getName() + " is now haunted !");
						player.sendMessage(ChatColor.DARK_RED + "I think someone threw you a curse...");
					}
					else {
						sender.sendMessage(ChatColor.RED + "This player is on a world where the plugin is disabled.");
					}
				}
				else {
					sender.sendMessage(ChatColor.RED + "This player is not connected !");
				}
				break;
			case 1:
			default:
				player = Bukkit.getPlayer(args[0]);
				if(player != null) {
					if(Halloween.config.Worlds.contains(player.getWorld().getName())) {
						Halloween.haunted.add(player.getName());
						sender.sendMessage(ChatColor.GREEN + player.getName() + " is now haunted !");
						player.sendMessage(ChatColor.DARK_RED + "I think someone threw you a curse...");
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
							Halloween.haunted.add(player.getName());
							sender.sendMessage(ChatColor.GREEN + player.getName() + " is now haunted !");
							player.sendMessage(ChatColor.DARK_RED + "I think someone threw you a curse...");
							Bukkit.getScheduler().scheduleSyncDelayedTask(Halloween.plugin, new Runnable() {
								
								@Override
								public void run() {
									Halloween.haunted.remove(player.getName());
									player.sendMessage(ChatColor.GREEN + "You are safe !");
								}
								
							}, Integer.parseInt(args[1]) * 20);
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
					sender.sendMessage(ChatColor.RED + "/haunt <player> [time]");
				}
				break;
			}
		}
		return true;
	}

}

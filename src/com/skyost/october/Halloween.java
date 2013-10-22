package com.skyost.october;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.skyost.october.ConfigFile;
import com.skyost.october.listeners.EventsListener;
import com.skyost.october.tasks.Thundering;

public class Halloween extends JavaPlugin {
	
	public static ConfigFile config;
	public static Plugin plugin;
	
	public void onEnable() {
		try {
			config = new ConfigFile(this);
			config.init();
			plugin = this;
			for(String s : config.Worlds) {
				if(Bukkit.getWorld(s) != null) {
					World world = Bukkit.getWorld(s);
					if(config.PermanentNight) {
						world.setTime(18000);
						world.setGameRuleValue("doDaylightCycle", "false");
					}
					if(config.Storm) {
						world.setStorm(true);
					}
					if(config.FakeLightning) {
						Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Thundering(world), new Random().nextInt(500));
					}
				}
				else {
					Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[Halloween] The world '" + s + "' was not found !");
				}
			}
			for(String s : config.Sounds) {
				if(Sound.valueOf(s) == null) {
					Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[Halloween] The sound '" + s + "' was not found, it has been removed !");
					config.Sounds.remove(s);
				}
			}
			Bukkit.getServer().getPluginManager().registerEvents(new EventsListener(), this);
			Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "[Halloween] The plugin has been started.");
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
	}
}

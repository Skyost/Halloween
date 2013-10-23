package com.skyost.october;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.skyost.october.ConfigFile;
import com.skyost.october.listeners.CommandsExecutor;
import com.skyost.october.listeners.EventsListener;
import com.skyost.october.tasks.Thundering;
import com.skyost.october.util.CitizensUtils;
import com.skyost.october.util.Metrics;
import com.skyost.october.util.Metrics.Graph;
import com.skyost.october.util.Updater;
import com.skyost.october.util.Updater.UpdateType;

public class Halloween extends JavaPlugin {
	
	public static ConfigFile config;
	public static Plugin plugin;
	public static CitizensUtils citizensutils = null;
	
	public static final List<String> haunted = new ArrayList<String>();
	public static final Random rand = new Random();
	
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
						Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Thundering(world), rand.nextInt(500));
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
			CommandExecutor executor = new CommandsExecutor();
			this.getCommand("scare").setExecutor(executor);
			this.getCommand("haunt").setExecutor(executor);
			if(config.EnableUpdater) {
				new Updater(this, 67739, this.getFile(), UpdateType.DEFAULT, true);
			}
			startMetrics();
			if(Bukkit.getPluginManager().getPlugin("Citizens") != null) {
				Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD + "[Halloween] Now using Citizens.");
				citizensutils = new CitizensUtils();
			}
			Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "[Halloween] The plugin has been started.");
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	private final void startMetrics() {
		try {
		    Metrics metrics = new Metrics(this);
		    Graph checkUpdatesGraph = metrics.createGraph("checkUpdatesGraph");
		    checkUpdatesGraph.addPlotter(new Metrics.Plotter("Checking for Updates") {  
		    @Override
		    public int getValue() {  
		    	return 1;
		    }
		    
		    @Override
		    public String getColumnName() {
		    	if(config.EnableUpdater) {
		    		return "Yes";
		    	}
		    	else if(!config.EnableUpdater) {
		    		return "No";
		    	}
		    	else {
		    		return "Maybe";
		    	}
		    }
		    });
    		Graph worldsNumberGraph = metrics.createGraph("worldsNumberGraph");
    		worldsNumberGraph.addPlotter(new Metrics.Plotter("Worlds number") {	
    			@Override
    			public int getValue() {	
    				return config.Worlds.size();
    			}
    		});
    		Graph soundsNumberGraph = metrics.createGraph("soundsNumberGraph");
    		soundsNumberGraph.addPlotter(new Metrics.Plotter("Sounds number") {	
    			@Override
    			public int getValue() {	
    				return config.Sounds.size();
    			}
    		});
    		Graph spawnWithPumpkinGraph = metrics.createGraph("spawnWithPumpkinGraph");
    		spawnWithPumpkinGraph.addPlotter(new Metrics.Plotter("Spawn with pumpkin luck") {	
    			@Override
    			public int getValue() {	
    				return config.SpawnWithPumpkin;
    			}
    		});
		    metrics.start();
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
	}
}

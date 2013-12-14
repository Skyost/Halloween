package fr.skyost.october;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.milkbowl.vault.permission.Permission;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.command.CommandExecutor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import fr.skyost.october.ConfigFile;
import fr.skyost.october.listeners.CommandsExecutor;
import fr.skyost.october.listeners.EventsListener;
import fr.skyost.october.tasks.Thundering;
import fr.skyost.october.utils.CitizensUtils;
import fr.skyost.october.utils.Metrics;
import fr.skyost.october.utils.Updater;
import fr.skyost.october.utils.Metrics.Graph;
import fr.skyost.october.utils.Updater.UpdateType;

public class Halloween extends JavaPlugin {
	
	public static ConfigFile config;
	public static Plugin plugin;
	public static CitizensUtils citizensutils;
	public static Permission vault;
	
	public static final List<String> haunted = new ArrayList<String>();
	public static final Random rand = new Random();
	
	@Override
	public final void onEnable() {
		try {
			checkConfig();
			init();
			startMetrics();
			Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "[Halloween] The plugin has been started.");
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	@Override
	public final void onDisable() {
		for(String s : config.Worlds) {
			World world = Bukkit.getWorld(s);
			if(world != null) {
				if(world.getGameRuleValue("doDaylightCycle") != null || world.getGameRuleValue("doDaylightCycle").equals("false")) {
					world.setGameRuleValue("doDaylightCycle", "true");
				}
			}
		}
	}
	
	private final void checkConfig() throws InvalidConfigurationException {
		config = new ConfigFile(this);
		config.init();
		for(String s : config.Worlds) {
			World world = Bukkit.getWorld(s);
			if(world != null) {
				world.setStorm(config.Storm);
				if(config.PermanentNight) {
					world.setTime(18000);
					world.setGameRuleValue("doDaylightCycle", "false");
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
		if(config.EnableUpdater) {
			new Updater(this, 67739, this.getFile(), UpdateType.DEFAULT, true);
		}
		if(config.SpawnWithPumpkin > 100) {
			config.SpawnWithPumpkin = 100;
		}
		config.save();
	}
	
	private final void init() {
		plugin = this;
		Bukkit.getServer().getPluginManager().registerEvents(new EventsListener(), this);
		CommandExecutor executor = new CommandsExecutor();
		this.getCommand("scare").setExecutor(executor);
		this.getCommand("haunt").setExecutor(executor);
		if(Bukkit.getPluginManager().getPlugin("Citizens") != null) {
			Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD + "[Halloween] Now using Citizens.");
			citizensutils = new CitizensUtils();
		}
		if(Bukkit.getPluginManager().getPlugin("Vault") != null) {
			RegisteredServiceProvider<Permission> permissionProvider = Bukkit.getServicesManager().getRegistration(Permission.class);
	        if(permissionProvider != null) {
	        	Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD + "[Halloween] Now using Vault Permissions.");
	        	vault = permissionProvider.getProvider();
	        }
		}
	}
	
	private final void startMetrics() throws IOException {
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
	
}

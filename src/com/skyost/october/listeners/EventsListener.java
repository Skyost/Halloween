package com.skyost.october.listeners;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.inventory.ItemStack;

import com.skyost.october.Halloween;
import com.skyost.october.tasks.RandomEvent;

public class EventsListener implements Listener {
	
	@EventHandler
	private static final void onWeatherChange(WeatherChangeEvent event) {
		if(Halloween.config.Worlds.contains(event.getWorld().getName())) {
			if(Halloween.config.Storm) {
				if(!event.toWeatherState()) {
					event.setCancelled(true);
				}
			}
		}
	}
	
	@EventHandler
	private final void onPlayerJoin(PlayerJoinEvent event) {
		if(Halloween.config.Worlds.contains(event.getPlayer().getWorld().getName())) {
			if(Halloween.config.SoundOnLogin) {
				Sound sound = Sound.valueOf(Halloween.config.Sounds.get(new Random().nextInt(Halloween.config.Sounds.size())));
				for(Player online : event.getPlayer().getWorld().getPlayers()) {
					online.playSound(online.getLocation(), sound, 1F, 1F);
				}
			}
			if(Halloween.config.RandomEvents) {
				Bukkit.getScheduler().scheduleSyncDelayedTask(Halloween.plugin, new RandomEvent(event.getPlayer().getName()), Halloween.config.MaxRandom * 20);
			}
		}
	}
	
	@EventHandler
	private static final void onCreatureSpawn(CreatureSpawnEvent event) {
		if(Halloween.config.Worlds.contains(event.getEntity().getWorld().getName())) {
			if(Halloween.config.CreaturesWearPumpkins) {
				if(event.getEntity().getType().equals(EntityType.ZOMBIE)) {
					Zombie zombie = (Zombie)event.getEntity();
				    if(!zombie.isBaby()) {
				    	event.getEntity().getEquipment().setHelmet(new ItemStack(Material.PUMPKIN));
				    	event.getEntity().getEquipment().setHelmetDropChance(0.0F);
				    }
				}
				else {
					event.getEntity().getEquipment().setHelmet(new ItemStack(Material.PUMPKIN));
				 	event.getEntity().getEquipment().setHelmetDropChance(0.0F);
				}
			}
		}
	}

}

package fr.skyost.october.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Bat;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerEggThrowEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.inventory.ItemStack;

import fr.skyost.october.Halloween;
import fr.skyost.october.tasks.RandomEvent;

public class EventsListener implements Listener {
	
	@EventHandler
	private final void onWeatherChange(final WeatherChangeEvent event) {
		if(Halloween.config.Worlds.contains(event.getWorld().getName())) {
			if(Halloween.config.Storm) {
				if(!event.toWeatherState()) {
					event.setCancelled(true);
				}
			}
		}
	}
	
	@EventHandler
	private final void onPlayerJoin(final PlayerJoinEvent event) {
		if(Halloween.config.Worlds.contains(event.getPlayer().getWorld().getName())) {
			if(Halloween.config.SoundOnLogin) {
				Sound sound = Sound.valueOf(Halloween.config.Sounds.get(Halloween.rand.nextInt(Halloween.config.Sounds.size())));
				for(Player online : event.getPlayer().getWorld().getPlayers()) {
					online.playSound(online.getLocation(), sound, 1F, 1F);
				}
			}
			if(Halloween.config.RandomEvents) {
				if(Halloween.vault == null) {
					if(!event.getPlayer().hasPermission("halloween.events.bypass")) {
						return;
					}
				}
				else {
					if(!Halloween.vault.has(event.getPlayer(), "halloween.events.bypass")) {
						return;
					}
				}
				Bukkit.getScheduler().scheduleSyncDelayedTask(Halloween.plugin, new RandomEvent(event.getPlayer().getName()), Halloween.config.MaxRandom * 20);
			}
		}
	}
	
	@EventHandler
	private final void onCreatureSpawn(final CreatureSpawnEvent event) {
		if(Halloween.config.Worlds.contains(event.getEntity().getWorld().getName())) {
			if(Halloween.config.CreaturesWearPumpkins) {
				int luck = Halloween.rand.nextInt(100) + 1;
				if(luck <= Halloween.config.SpawnWithPumpkin) {
					if(event.getEntity().getType() == EntityType.ZOMBIE) {
						Zombie zombie = (Zombie)event.getEntity();
					    if(!zombie.isBaby()) {
					    	zombie.getEquipment().setHelmet(new ItemStack(Material.PUMPKIN));
					    	zombie.getEquipment().setHelmetDropChance(0.0F);
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
	
	@EventHandler
	private final void onBlockPlace(final BlockPlaceEvent event) {
		if(Halloween.config.Worlds.contains(event.getPlayer().getWorld().getName())) {
			if(Halloween.haunted.contains(event.getPlayer().getName())) {
				final Block block = event.getBlock();
				if(block.getType() == Material.TORCH) {
					Bukkit.getScheduler().scheduleSyncDelayedTask(Halloween.plugin, new Runnable() {
	
						@Override
						public void run() {
							if(!block.breakNaturally()) {
								block.setType(Material.AIR);
								block.getWorld().dropItem(event.getBlock().getLocation(), new ItemStack(Material.TORCH));
							}
						}
						
					}, Halloween.rand.nextInt(300));
				}
			}
		}
	}
	
	@EventHandler
	private final void onPlayerEggThrow(final PlayerEggThrowEvent event) {
		if(Halloween.config.Worlds.contains(event.getPlayer().getWorld().getName())) {
			if(Halloween.haunted.contains(event.getPlayer().getName())) {
				if(event.isHatching()) {
					final Location loc = event.getEgg().getLocation();
					Zombie zombie = loc.getWorld().spawn(loc, Zombie.class);
					zombie.getEquipment().setHelmet(new ItemStack(Material.AIR));
					zombie.setBaby(true);
					event.setHatching(false);
				}
			}
		}
	}
	
	@EventHandler
    private final void onInventoryOpenEvent(final InventoryOpenEvent event) {
		if(Halloween.config.Worlds.contains(event.getPlayer().getWorld().getName())) {
			if(Halloween.haunted.contains(event.getPlayer().getName())) {
		        if(event.getInventory().getType() == InventoryType.CHEST) {
		            event.getPlayer().getWorld().spawn(event.getPlayer().getLocation(), Bat.class);
		        }
			}
		}
    }

}

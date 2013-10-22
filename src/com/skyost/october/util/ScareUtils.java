package com.skyost.october.util;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Bat;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.ItemStack;

import com.skyost.october.Halloween;

public class ScareUtils {
	
	private static final Random rand = new Random();
	
	public static final void scarePlayer(final Player player, int id) {
		final Location loc = player.getLocation();
		switch(id) {
		case 0:
			Sound sound = Sound.valueOf(Halloween.config.Sounds.get(rand.nextInt(Halloween.config.Sounds.size())));
			player.playSound(player.getLocation(), sound, 1F, 1F);
			break;
		case 1:
			player.getWorld().strikeLightningEffect(player.getLocation());
			break;
		case 2:
			final ItemStack helmet = player.getEquipment().getHelmet();
			player.getEquipment().setHelmet(new ItemStack(Material.PUMPKIN));
			player.playSound(player.getLocation(), Sound.BLAZE_BREATH, 1F, 2F);
			Bukkit.getScheduler().scheduleSyncDelayedTask(Halloween.plugin, new Runnable() {

				@Override
				public void run() {
					if(helmet != null) {
						player.getEquipment().setHelmet(helmet);
					}
					else {
						player.getEquipment().setHelmet(new ItemStack(Material.AIR));
					}
				}
				
			}, 60);
			break;
		case 3:
			Location spawn = loc.add(5, 0, 0);
			int y = player.getWorld().getHighestBlockYAt(spawn);
			spawn.setY(y);
			Zombie zone = (Zombie)player.getWorld().spawn(spawn, Zombie.class);
			spawn = loc.subtract(5, 0, 0);
			y = player.getWorld().getHighestBlockYAt(spawn);
			spawn.setY(y);
			Zombie ztwo = (Zombie)player.getWorld().spawn(spawn, Zombie.class);
			spawn = loc.add(0, 0, 5);
			y = player.getWorld().getHighestBlockYAt(spawn);
			spawn.setY(y);
			Zombie zthree = (Zombie)player.getWorld().spawn(spawn, Zombie.class);
			spawn = loc.subtract(0, 0, 5);
			y = player.getWorld().getHighestBlockYAt(spawn);
			spawn.setY(y);
			Zombie zfour = (Zombie)player.getWorld().spawn(spawn, Zombie.class);
			zone.setTarget(ztwo);
			ztwo.setTarget(zthree);
			zthree.setTarget(zfour);
			zfour.setTarget(zone);
			break;
		case 4:
		default:
			final Bat bone = (Bat)player.getWorld().spawnEntity(loc, EntityType.BAT);
			final Bat btwo = (Bat)player.getWorld().spawnEntity(loc, EntityType.BAT);
			final Bat bthree = (Bat)player.getWorld().spawnEntity(loc, EntityType.BAT);
			final Bat bfour = (Bat)player.getWorld().spawnEntity(loc, EntityType.BAT);
			player.sendMessage(ChatColor.DARK_RED + "Happy Halloween !");
			Bukkit.getScheduler().scheduleSyncDelayedTask(Halloween.plugin, new Runnable() {

				@Override
				public void run() {
					bone.setHealth(0);
					btwo.setHealth(0);
					bthree.setHealth(0);
					bfour.setHealth(0);
				}
				
			}, 200);
			break;
		}
	}
	
	public static final void scarePlayer(final Player player) {
		final Location loc = player.getLocation();
		switch(rand.nextInt(5)) {
		case 0:
			Sound sound = Sound.valueOf(Halloween.config.Sounds.get(rand.nextInt(Halloween.config.Sounds.size())));
			player.playSound(player.getLocation(), sound, 1F, 1F);
			break;
		case 1:
			player.getWorld().strikeLightningEffect(player.getLocation());
			break;
		case 2:
			final ItemStack helmet = player.getEquipment().getHelmet();
			player.getEquipment().setHelmet(new ItemStack(Material.PUMPKIN));
			player.playSound(player.getLocation(), Sound.BLAZE_BREATH, 1F, 2F);
			Bukkit.getScheduler().scheduleSyncDelayedTask(Halloween.plugin, new Runnable() {

				@Override
				public void run() {
					if(helmet != null) {
						player.getEquipment().setHelmet(helmet);
					}
					else {
						player.getEquipment().setHelmet(new ItemStack(Material.AIR));
					}
				}
				
			}, 60);
			break;
		case 3:
			Location spawn = loc.add(5, 0, 0);
			int y = player.getWorld().getHighestBlockYAt(spawn);
			spawn.setY(y);
			Zombie zone = (Zombie)player.getWorld().spawn(spawn, Zombie.class);
			spawn = loc.subtract(5, 0, 0);
			y = player.getWorld().getHighestBlockYAt(spawn);
			spawn.setY(y);
			Zombie ztwo = (Zombie)player.getWorld().spawn(spawn, Zombie.class);
			spawn = loc.add(0, 0, 5);
			y = player.getWorld().getHighestBlockYAt(spawn);
			spawn.setY(y);
			Zombie zthree = (Zombie)player.getWorld().spawn(spawn, Zombie.class);
			spawn = loc.subtract(0, 0, 5);
			y = player.getWorld().getHighestBlockYAt(spawn);
			spawn.setY(y);
			Zombie zfour = (Zombie)player.getWorld().spawn(spawn, Zombie.class);
			zone.setTarget(ztwo);
			ztwo.setTarget(zthree);
			zthree.setTarget(zfour);
			zfour.setTarget(zone);
			break;
		case 4:
			final Bat bone = (Bat)player.getWorld().spawnEntity(loc, EntityType.BAT);
			final Bat btwo = (Bat)player.getWorld().spawnEntity(loc, EntityType.BAT);
			final Bat bthree = (Bat)player.getWorld().spawnEntity(loc, EntityType.BAT);
			final Bat bfour = (Bat)player.getWorld().spawnEntity(loc, EntityType.BAT);
			player.sendMessage(ChatColor.DARK_RED + "Happy Halloween !");
			Bukkit.getScheduler().scheduleSyncDelayedTask(Halloween.plugin, new Runnable() {

				@Override
				public void run() {
					bone.setHealth(0);
					btwo.setHealth(0);
					bthree.setHealth(0);
					bfour.setHealth(0);
				}
				
			}, 200);
			break;
		}
	}

}

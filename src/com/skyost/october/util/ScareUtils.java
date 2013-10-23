package com.skyost.october.util;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Bat;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.skyost.october.Halloween;

public class ScareUtils {
	
	public static final void scarePlayer(final Player player, int id) {
		final Location loc = player.getLocation();
		switch(id) {
		case 1:
			Sound sound = Sound.valueOf(Halloween.config.Sounds.get(Halloween.rand.nextInt(Halloween.config.Sounds.size())));
			player.playSound(player.getLocation(), sound, 1F, 1F);
			break;
		case 2:
			player.getWorld().strikeLightningEffect(player.getLocation());
			break;
		case 3:
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
		case 4:
			default:
			final Bat bone = (Bat)player.getWorld().spawnEntity(loc, EntityType.BAT);
			final Bat btwo = (Bat)player.getWorld().spawnEntity(loc, EntityType.BAT);
			final Bat bthree = (Bat)player.getWorld().spawnEntity(loc, EntityType.BAT);
			final Bat bfour = (Bat)player.getWorld().spawnEntity(loc, EntityType.BAT);
			player.sendMessage(Halloween.config.HalloweenMessage);
			Bukkit.getScheduler().scheduleSyncDelayedTask(Halloween.plugin, new Runnable() {

				@Override
				public void run() {
					bone.setHealth(0.0);
					btwo.setHealth(0.0);
					bthree.setHealth(0.0);
					bfour.setHealth(0.0);
				}
				
			}, 200);
			break;
		case 5:
			player.playSound(player.getLocation(), Sound.WITHER_SPAWN, 1F, 0);
			player.getWorld().playEffect(player.getLocation(), Effect.MOBSPAWNER_FLAMES, 1);
			if(Halloween.citizensutils != null) {
				Halloween.citizensutils.spawnNPCForPlayer("Herobrine", player, 150);
			}
			break;
		}
	}
	
	public static final int getMaxID() {
		return 5;
	}

}

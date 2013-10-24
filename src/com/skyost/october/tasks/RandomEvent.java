package com.skyost.october.tasks;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.skyost.october.Halloween;
import com.skyost.october.util.ScareUtils;

public class RandomEvent implements Runnable {
	
	private static String playername;
	
	public RandomEvent(String targetPlayer) {
		playername = targetPlayer;
	}

	@Override
	public void run() {
		Player player = Bukkit.getPlayer(playername);
		if(player != null && Halloween.config.Worlds.contains(player.getWorld().getName())) {
			if(Halloween.vault == null) {
				if(player.hasPermission("halloween.events.bypass")) {
					return;
				}
			}
			else {
				if(Halloween.vault.has(player, "halloween.events.bypass")) {
					return;
				}
			}
			ScareUtils.scarePlayer(player, Halloween.rand.nextInt(ScareUtils.getMaxID()) + 1);
			if(Halloween.config.RandomEvents) {
				Bukkit.getScheduler().scheduleSyncDelayedTask(Halloween.plugin, new RandomEvent(playername), Halloween.rand.nextInt(Halloween.config.MaxRandom) * 20);
			}
		}
	}

}

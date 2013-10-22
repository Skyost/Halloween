package com.skyost.october.tasks;

import org.bukkit.Bukkit;

import com.skyost.october.Halloween;
import com.skyost.october.util.ScareUtils;

public class RandomEvent implements Runnable {
	
	private static String playername;
	
	public RandomEvent(String targetPlayer) {
		playername = targetPlayer;
	}

	@Override
	public void run() {
		if(Bukkit.getPlayer(playername) != null && Halloween.config.Worlds.contains(Bukkit.getPlayer(playername).getWorld().getName())) {
			ScareUtils.scarePlayer(Bukkit.getPlayer(playername), Halloween.rand.nextInt(ScareUtils.getMaxID()) + 1);
			if(Halloween.config.RandomEvents) {
				Bukkit.getScheduler().scheduleSyncDelayedTask(Halloween.plugin, new RandomEvent(playername), Halloween.rand.nextInt(Halloween.config.MaxRandom) * 20);
			}
		}
	}

}

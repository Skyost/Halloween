package com.skyost.october.tasks;

import java.util.Random;

import org.bukkit.Bukkit;

import com.skyost.october.Halloween;
import com.skyost.october.util.ScareUtils;

public class RandomEvent implements Runnable {
	
	private static String playername;
	private static Random rand = new Random();
	
	public RandomEvent(String targetPlayer) {
		playername = targetPlayer;
	}

	@Override
	public void run() {
		if(Bukkit.getPlayer(playername) != null && Halloween.config.Worlds.contains(Bukkit.getPlayer(playername).getWorld().getName())) {
			ScareUtils.scarePlayer(Bukkit.getPlayer(playername));
			if(Halloween.config.RandomEvents) {
				Bukkit.getScheduler().scheduleSyncDelayedTask(Halloween.plugin, new RandomEvent(playername), rand.nextInt(Halloween.config.MaxRandom) * 20);
			}
		}
	}

}

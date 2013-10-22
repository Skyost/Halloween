package com.skyost.october.tasks;

import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;

import com.skyost.october.Halloween;

public class Thundering implements Runnable {
	
	private static World world;
	private static final Random rand = new Random();
	
	public Thundering(World targetWorld) {
		world = targetWorld;
	}

	@Override
	public void run() {
		List<Player> online = world.getPlayers();
		if(online.size() != 0) {
			int player = rand.nextInt(online.size());
			int x = rand.nextInt(25);
			int z = rand.nextInt(25);
			world.strikeLightningEffect(online.get(player).getLocation().add(x, 0, z));
		}
		Bukkit.getScheduler().scheduleSyncDelayedTask(Halloween.plugin, this, rand.nextInt(500));
	}

}

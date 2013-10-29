package com.skyost.october.tasks;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import com.skyost.october.Halloween;

public class Thundering implements Runnable {
	
	private static World world;
	private static Location loc;
	
	public Thundering(World targetWorld) {
		world = targetWorld;
	}

	@Override
	public void run() {
		List<Player> online = world.getPlayers();
		if(online.size() != 0) {
			int player = Halloween.rand.nextInt(online.size());
			int x = Halloween.rand.nextInt(30);
			int z = Halloween.rand.nextInt(30);
			loc = online.get(player).getLocation().add(x, 0, z);
			loc.setY(world.getHighestBlockYAt(loc));
			world.strikeLightningEffect(loc);
		}
		Bukkit.getScheduler().scheduleSyncDelayedTask(Halloween.plugin, this, Halloween.rand.nextInt(400));
	}

}

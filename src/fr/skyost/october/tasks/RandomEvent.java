package fr.skyost.october.tasks;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import fr.skyost.october.Halloween;
import fr.skyost.october.utils.ScareUtils;

public class RandomEvent implements Runnable {
	
	private String playername;
	
	public RandomEvent(String targetPlayer) {
		playername = targetPlayer;
	}

	@Override
	public void run() {
		Player player = Bukkit.getPlayer(playername);
		if(player != null && Halloween.config.Worlds.contains(player.getWorld().getName())) {
			ScareUtils.scarePlayer(player, Halloween.rand.nextInt(ScareUtils.getMaxID()) + 1);
			if(Halloween.config.RandomEvents) {
				Bukkit.getScheduler().scheduleSyncDelayedTask(Halloween.plugin, new RandomEvent(playername), Halloween.rand.nextInt(Halloween.config.MaxRandom) * 20);
			}
		}
	}

}

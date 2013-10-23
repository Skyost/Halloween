package com.skyost.october.util;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.npc.NPCRegistry;
import net.citizensnpcs.trait.LookClose;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import com.skyost.october.Halloween;

@SuppressWarnings("deprecation")
public class CitizensUtils {
	
	public final void spawnNPCForPlayer(final String name, final Player player) {
		NPCRegistry registry = CitizensAPI.getNPCRegistry();
		final NPC npc = registry.createNPC(EntityType.PLAYER, name);
		npc.setProtected(true);
		player.getWorld().playEffect(player.getTargetBlock(null, 100).getLocation(), Effect.MOBSPAWNER_FLAMES, 1);
		npc.spawn(player.getTargetBlock(null, 100).getLocation().add(0, 1, 0));
		LookClose lookclose = CitizensAPI.getTraitFactory().getTrait(LookClose.class);
		lookclose.lookClose(true);
		lookclose.setRange((int)player.getLocation().distance(npc.getBukkitEntity().getLocation()));
		npc.addTrait(lookclose);
	}
	
	public final void spawnNPCForPlayer(final String name, final Player player, int time) {
		NPCRegistry registry = CitizensAPI.getNPCRegistry();
		final NPC npc = registry.createNPC(EntityType.PLAYER, name);
		npc.setProtected(true);
		player.getWorld().playEffect(player.getTargetBlock(null, 100).getLocation(), Effect.MOBSPAWNER_FLAMES, 1);
		npc.spawn(player.getTargetBlock(null, 100).getLocation().add(0, 1, 0));
		LookClose lookclose = CitizensAPI.getTraitFactory().getTrait(LookClose.class);
		lookclose.lookClose(true);
		lookclose.setRange((int)player.getLocation().distance(npc.getBukkitEntity().getLocation()));
		npc.addTrait(lookclose);
		Bukkit.getScheduler().scheduleSyncDelayedTask(Halloween.plugin, new Runnable() {

			@Override
			public void run() {
				player.getWorld().playEffect(player.getLocation(), Effect.MOBSPAWNER_FLAMES, 1);
				player.getWorld().playEffect(npc.getBukkitEntity().getLocation(), Effect.MOBSPAWNER_FLAMES, 1);
				npc.destroy();
			}
			
		}, time);
	}

}

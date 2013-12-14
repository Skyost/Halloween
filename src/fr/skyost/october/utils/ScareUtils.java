package fr.skyost.october.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Bat;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fr.skyost.october.Halloween;

public class ScareUtils {
	
	public static final void scarePlayer(final Player player, int id) {
		final Location loc;
		switch(id) {
		case 1:
			Sound sound = Sound.valueOf(Halloween.config.Sounds.get(Halloween.rand.nextInt(Halloween.config.Sounds.size())));
			player.playSound(player.getLocation(), sound, 1F, 1F);
			break;
		case 2:
			loc = player.getLocation().add(Halloween.rand.nextInt(5), 0, Halloween.rand.nextInt(5));
			loc.setY(player.getWorld().getHighestBlockYAt(loc));
			player.getWorld().strikeLightningEffect(loc);
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
			loc = player.getLocation();
			final Bat bone = player.getWorld().spawn(loc, Bat.class);
			final Bat btwo = player.getWorld().spawn(loc, Bat.class);
			final Bat bthree = player.getWorld().spawn(loc, Bat.class);
			final Bat bfour = player.getWorld().spawn(loc, Bat.class);
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
		case 6:
			final Bat bat = player.getWorld().spawn(player.getLocation(), Bat.class);
			final Skeleton skeleton = player.getWorld().spawn(player.getLocation(), Skeleton.class);
			bat.setPassenger(skeleton);
			skeleton.getEquipment().setHelmet(new ItemStack(Material.JACK_O_LANTERN));
			bat.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 0));
			bat.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, 0));
			skeleton.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 0));
			skeleton.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, 0));
			Bukkit.getScheduler().scheduleSyncDelayedTask(Halloween.plugin, new Runnable() {

				@Override
				public void run() {
					skeleton.setHealth(0.0);
					bat.setHealth(0.0);
				}
				
			}, 1200);
			break;
		case 7:
			loc = player.getLocation().add(Halloween.rand.nextInt(120) + 60, 0, Halloween.rand.nextInt(90) + 45);
			loc.setY(player.getWorld().getHighestBlockYAt(loc));
			player.getWorld().spawnEntity(loc, EntityType.ZOMBIE);
			player.getWorld().spawnEntity(loc, EntityType.ZOMBIE);
			player.getWorld().spawnEntity(loc, EntityType.SKELETON);
			player.getWorld().spawnEntity(loc, EntityType.SKELETON);
			player.getWorld().spawnEntity(loc, EntityType.SPIDER);
			player.getWorld().spawnEntity(loc, EntityType.SPIDER);
			player.sendMessage("<????> Help me or he will kill us ! I am at X : " + loc.getBlockX() + ", Y : " + loc.getBlockY() + ", Z : " + loc.getBlockZ() + ". Please come quickly :s");
			break;
		case 8:
			final Player random = player.getWorld().getPlayers().get(Halloween.rand.nextInt(player.getWorld().getPlayers().size()));
			if(!random.getName().equalsIgnoreCase(player.getName())) {
				player.sendMessage("<" + random.getName() + "> Help me dude, I will die !");
				Bukkit.getScheduler().scheduleSyncDelayedTask(Halloween.plugin, new Runnable() {

					@Override
					public void run() {
						player.sendMessage(random.getName() + "was death.");
						final Zombie grimReaper = player.getWorld().spawn(player.getLocation(), Zombie.class);
						EntityEquipment equip = grimReaper.getEquipment();
						equip.setHelmet(new ItemStack(Material.SKULL_ITEM, 1, (short)1));
					    ItemStack chestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
					    LeatherArmorMeta meta = (LeatherArmorMeta)chestplate.getItemMeta();
					    meta.setColor(Color.BLACK);
					    chestplate.setItemMeta(meta);
					    equip.setChestplate(chestplate);
					    equip.setItemInHand(new ItemStack(Material.IRON_HOE));
					    grimReaper.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, 1, false));
					    grimReaper.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1, false));
					    grimReaper.setMaxHealth(30);
					    grimReaper.setTarget(player);
					    grimReaper.setCustomName("Grim Reaper");
					    grimReaper.setCustomNameVisible(true);
						player.playSound(player.getLocation(), Sound.GHAST_SCREAM2, 1F, 0F);
						Bukkit.getScheduler().scheduleSyncDelayedTask(Halloween.plugin, new Runnable() {

							@Override
							public void run() {
								grimReaper.setHealth(0.0);
							}
							
						}, 100);
					}
					
				}, 100);
			}
			else {
				player.sendMessage("<Skyost> BWAAAAAA !");
				player.playSound(player.getLocation(), Sound.AMBIENCE_CAVE, 2F, 0);
			}
			break;
		case 9:
			loc = player.getLocation();
			player.teleport(player.getWorld().getHighestBlockAt(loc.getBlockX(), loc.getBlockZ()).getLocation().add(0, 256, 0));
			player.sendMessage(ChatColor.DARK_RED + "Whooooosh !");
			Bukkit.getScheduler().scheduleSyncDelayedTask(Halloween.plugin, new Runnable() {

				@Override
				public void run() {
					player.teleport(loc);
					player.sendMessage(ChatColor.RED + "Are you scared now ?");
				}
				
			}, 100);
			break;
		}
	}
	
	public static final int getMaxID() {
		return 9;
	}

}

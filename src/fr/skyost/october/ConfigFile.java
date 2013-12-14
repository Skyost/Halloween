package fr.skyost.october;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Sound;
import org.bukkit.plugin.Plugin;

import fr.skyost.october.utils.Config;

public class ConfigFile extends Config {
	
	public List<String> Worlds = new ArrayList<String>();
	
	public List<String> Sounds = new ArrayList<String>();
	
	public boolean RandomEvents = true;
	public boolean PermanentNight = true;
	public boolean SoundOnLogin = true;
	public boolean Storm = false;
	public boolean FakeLightning = true;
	public boolean CreaturesWearPumpkins = true;
	
	public boolean EnableUpdater = true;
	
	public String HalloweenMessage = "§4Happy Halloween !";
	
	public int MaxRandom = 1200;
	public int SpawnWithPumpkin = 100;
	
	public ConfigFile(Plugin plugin) {
		CONFIG_FILE = new File(plugin.getDataFolder(), "config.yml");
		CONFIG_HEADER = "Halloween Config";
		CONFIG_HEADER += "\n\nSounds available here : http://url.skyost.eu/n.";
		
		Sounds.add(Sound.COW_WALK.name());
		Sounds.add(Sound.CREEPER_HISS.name());
		Sounds.add(Sound.ITEM_BREAK.name());
		Sounds.add(Sound.WITHER_SPAWN.name());
		Sounds.add(Sound.WOLF_GROWL.name());
		Sounds.add(Sound.WOLF_HOWL.name());
		Sounds.add(Sound.ZOMBIE_UNFECT.name());
		
		Worlds.add("WorldA");
		Worlds.add("WorldB");
		Worlds.add("WorldC");
	}
	
}

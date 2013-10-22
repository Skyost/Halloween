package com.skyost.october;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Sound;
import org.bukkit.plugin.Plugin;

import com.skyost.october.util.Config;

public class ConfigFile extends Config {
	
	public ConfigFile(Plugin plugin) {
		CONFIG_FILE = new File(plugin.getDataFolder(), "config.yml");
		CONFIG_HEADER = "Halloween Config";
		CONFIG_HEADER += "\n\nSounds available here : http://url.skyost.eu/n.";
		
		Sounds.add(Sound.AMBIENCE_THUNDER.name());
		Sounds.add(Sound.BAT_LOOP.name());
		Sounds.add(Sound.CHEST_OPEN.name());
		Sounds.add(Sound.COW_WALK.name());
		Sounds.add(Sound.CREEPER_HISS.name());
		Sounds.add(Sound.ITEM_BREAK.name());
		Sounds.add(Sound.WITHER_SPAWN.name());
		Sounds.add(Sound.ZOMBIE_UNFECT.name());
		
		Worlds.add("WorldA");
		Worlds.add("WorldB");
		Worlds.add("WorldC");
	}
	
	public List<String> Worlds = new ArrayList<String>();
	
	public List<String> Sounds = new ArrayList<String>();
	
	public boolean RandomEvents = true;
	public boolean PermanentNight = true;
	public boolean SoundOnLogin = true;
	public boolean Storm = true;
	public boolean FakeLightning = true;
	public boolean CreaturesWearPumpkins = true;
	
	public boolean EnableUpdater = true;
	
	public int MaxRandom = 1200;
	public int SpawnWithPumpkin = 100;
}

package galaxy.gabrielblink.galaxymambascore;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.scheduler.BukkitRunnable;

public class ConfigurationManager {

	                   // WORLD  FANCY_NAME
	public static String DEFAULT_WORLD_NAME = "world";
	public static String DEFAULT_URL_SCOREBOARD = "§7redegalaxy.com";
	public static HashMap<String,String> EXTRA_WORLDS = new HashMap<String,String>();
	
	public static void loadWorlds() {
		for(String z : Main.getInstance().getConfig().getConfigurationSection("EXTRA_WORLDS").getKeys(false)) {
			EXTRA_WORLDS.put(z, Main.getInstance().getConfig().getString("EXTRA_WORLDS."+z+".Name_Fancy").replace("&", "§").replace("&", "§"));
		}
		new BukkitRunnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				DEFAULT_WORLD_NAME = Main.getInstance().getConfig().getString("DEFAULT_WORLD_NAME");
				DEFAULT_URL_SCOREBOARD = Main.getInstance().getConfig().getString("ScoreBoard_URL").replace("&", "§");
			}
		}.runTaskLaterAsynchronously(Main.getPlugin(Main.class),1L);
	}
	public static String getWorldFancyName(String world_name) {
		if(EXTRA_WORLDS.size() != 0) {
		if(EXTRA_WORLDS.containsKey(world_name)) {
			return EXTRA_WORLDS.get(world_name);
		}else {
			return "§7Lugar desconhecido";
		}
		}else {
			for(String z : Main.getInstance().getConfig().getConfigurationSection("EXTRA_WORLDS").getKeys(false)) {
				EXTRA_WORLDS.put(z, Main.getInstance().getConfig().getString("EXTRA_WORLDS."+z+".Name_Fancy").replace("&", "§"));
			}
			return "§7Lugar desconhecido";
		}
	}
	public ArrayList<String> getAllWorldsRegistreds(){
		ArrayList<String> ARRAY = new ArrayList<String>();
		if(EXTRA_WORLDS.size() == 0) {
			for(String z : Main.getInstance().getConfig().getConfigurationSection("EXTRA_WORLDS").getKeys(false)) {
				EXTRA_WORLDS.put(z, Main.getInstance().getConfig().getString("EXTRA_WORLDS."+z+".Name_Fancy").replace("&", "§"));
				ARRAY.add(z);
			}
		}else {
			for(String a : EXTRA_WORLDS.keySet()) {
				ARRAY.add(a);
			}
		}
		return ARRAY;
	}
}

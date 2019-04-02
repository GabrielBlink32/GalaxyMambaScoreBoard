package galaxy.gabrielblink.galaxymambascore;

import java.io.File;
import java.nio.charset.Charset;

import org.black_ixx.playerpoints.PlayerPoints;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import com.google.common.io.Resources;
import com.massivecraft.factions.entity.MPlayer;

import galaxy.gabrielblink.GalaxyScoreBoard.ScoreBoard.ScoreBoard;
import galaxy.gabrielblink.GalaxyScoreBoard.ScoreBoard.ScoreUpdater;
import galaxy.gabrielblink.GalaxyScoreBoard.ScoreBoard.mcMMOScoreBoard;
import galaxy.gabrielblink.licenseServer.Core;
import net.milkbowl.vault.economy.Economy;


public class Main extends JavaPlugin{

    private static Economy econ = null;
	public static Main instance;
	private static PlayerPoints playerPoints;

	/**
	 * Validate that we have access to PlayerPoints
	 *
	 * @return True if we have PlayerPoints, else false.
	 */
	private boolean hookPlayerPoints() {
	    final Plugin plugin = this.getServer().getPluginManager().getPlugin("PlayerPoints");
	    playerPoints = PlayerPoints.class.cast(plugin);
	    return playerPoints != null; 
	}

	public static Economy getEconomy() {
        return econ;
    }
	/**
	 * Accessor for other parts of your plugin to retrieve PlayerPoints.
	 *
	 * @return PlayerPoints plugin instance
	 */
	public static PlayerPoints getPlayerPoints() {
	    return playerPoints;
	}
	public static Main getInstance() {
		return instance;
	}
    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }
	
    private void loadScoreTask() {
		Bukkit.getOnlinePlayers().forEach(p -> {
			 MPlayer mp = MPlayer.get(p);
			if(mp.hasFaction()) {
			ScoreBoard.createScoreBoardWithFac(p);
			}else {
				ScoreBoard.createScoreBoardWithoutFac(p);
			}
		});
		new ScoreUpdater().runTaskTimerAsynchronously(this, 40L, 100L);
	}
	public void onEnable() {
		if(!setupEconomy()) {
			Bukkit.getConsoleSender().sendMessage("§c[GalaxyMambaScoreBoard] Erro ao registrar o Vault / Economia do servidor, revise a pasta plugins.");
			super.onDisable();
		}
		if(!hookPlayerPoints()) {
			Bukkit.getConsoleSender().sendMessage("§c[GalaxyMambaScoreBoard] Erro ao registrar o PlayerPoints, revise a pasta plugins.");
			super.onDisable();
		}
		saveDefaultConfig();
		try {
			  File file = new File(getDataFolder() + File.separator, "config.yml");
			  String allText = Resources.toString(file.toURI().toURL(), Charset.forName("UTF-8"));
			  getConfig().loadFromString(allText);
			} catch (Exception e) {
			  e.printStackTrace();
			}
		(Main.instance = this).saveDefaultConfig();
		switch(Core.check("GalaxyMambaScoreBoard", Main.getPlugin(Main.class).getConfig().getString("Licenca"), Bukkit.getIp(), Bukkit.getPort())) {
		case INVALID_PLUGIN_NAME:
			Bukkit.getConsoleSender().sendMessage("§c[GalaxyMambaScoreBoard] INVALID_PLUGIN_NAME");
			super.onDisable();
			break;
		case INVALID_PORT:
			Bukkit.getConsoleSender().sendMessage("§c[GalaxyMambaScoreBoard] Porta inválida!");
			super.onDisable();
			break;
		case IP_ADRESS_INVALID:
			Bukkit.getConsoleSender().sendMessage("§c[GalaxyMambaScoreBoard] Endereço de IP inválido");
			super.onDisable();
			break;
		case LICENSE_INVALID:
			Bukkit.getConsoleSender().sendMessage("§c[GalaxyMambaScoreBoard] Licenca Inválida!");
			super.onDisable();
			break;
		case LICENSE_VALID:
			Bukkit.getConsoleSender().sendMessage("§a[GalaxyMambaScoreBoard] Obrigado por comprar conosco!.");
			ConfigurationManager.loadWorlds();
			Bukkit.getPluginManager().registerEvents(new ScoreBoard(), this);
			Bukkit.getPluginManager().registerEvents(new mcMMOScoreBoard(), this);
			loadScoreTask();
			break;
		case WEBSITE_ERROR:
			Bukkit.getConsoleSender().sendMessage("§c[GalaxyMambaScoreBoard] Conexão com o servidor de verificação ficou indisponivel , tente novamente mais tarde.");
			super.onDisable();
			break;
		default:
			Bukkit.getConsoleSender().sendMessage("§c[GalaxyMambaScoreBoard] "+Core.check("GalaxyMambaScoreBoard", Main.getPlugin(Main.class).getConfig().getString("Licenca"), Bukkit.getIp(), Bukkit.getPort()));
			super.onDisable();
			break;
		
		}
	
		
	}
	
	
	public void onDisable() {
	}
	
	
}

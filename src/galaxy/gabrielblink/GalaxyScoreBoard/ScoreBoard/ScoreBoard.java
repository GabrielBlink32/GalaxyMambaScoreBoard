package galaxy.gabrielblink.GalaxyScoreBoard.ScoreBoard;

import java.util.WeakHashMap;

import org.black_ixx.playerpoints.event.PlayerPointsChangeEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;

import com.massivecraft.factions.RelationParticipator;
import com.massivecraft.factions.entity.BoardColl;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.factions.event.EventFactionsCreate;
import com.massivecraft.factions.event.EventFactionsDisband;
import com.massivecraft.massivecore.ps.PS;

import galaxy.gabrielblink.galaxymambascore.ConfigurationManager;
import galaxy.gabrielblink.galaxymambascore.Main;


public class ScoreBoard implements Listener{
	@EventHandler
	public void asbnd(EventFactionsDisband event) {
		MPlayer mp = event.getMPlayer();
		new BukkitRunnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				createScoreBoardWithoutFac(mp.getPlayer());
				ScoreUpdater.updateScoreBoard(mp.getPlayer());
			}
		}.runTaskLater(Main.getPlugin(Main.class), 20L*1);
	}
	@EventHandler
	public void asaa(EventFactionsCreate event) {
		MPlayer mp = event.getMPlayer();
		new BukkitRunnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				createScoreBoardWithFac(mp.getPlayer());
				ScoreUpdater.updateScoreBoard(mp.getPlayer());
			}
		}.runTaskLater(Main.getPlugin(Main.class), 20L*1);
	}
	  public static String getLocationName(Player p) {
          if (p.getWorld().getName().equalsIgnoreCase(ConfigurationManager.DEFAULT_WORLD_NAME)) {
              return BoardColl.get().getFactionAt(PS.valueOf(p.getLocation())).getName((RelationParticipator)MPlayer.get((Object)p.getUniqueId()));
          }else {
        	  return ConfigurationManager.getWorldFancyName(p.getWorld().getName());
          }
      }
	public static WeakHashMap<Player, ScoreBoardAPI> boards = new WeakHashMap<>();
	public boolean mcMMO = true;
	public static void createScoreBoardWithoutFac(Player p) {
		p.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
		String UUIDRandom = new RandomUUID().getUUID();

		ScoreBoardAPI sb = new ScoreBoardAPI(getLocationName(p), UUIDRandom);
		sb.blankLine(10);
		sb.add("",9);
		sb.add("  §fPoder: ",8);
		sb.blankLine(7);
		sb.add("  §7Sem facção",6);
		sb.blankLine(5);
		sb.add("  §fCoins: ", 4);
		sb.add("  §fCash: ", 3);
		sb.blankLine(2);
		sb.add("  "+ConfigurationManager.DEFAULT_URL_SCOREBOARD, 1);
		
		sb.build();
		ScoreBoard.boards.remove(p);
		sb.send(p);
		ScoreBoard.boards.put(p, sb);
	}
	public static void createScoreBoardWithFac(Player p) {
		p.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
		String UUIDRandom = new RandomUUID().getUUID();

		ScoreBoardAPI sb = new ScoreBoardAPI(getLocationName(p), UUIDRandom);
		sb.blankLine(13);
		sb.add("",12);
		sb.add("  §fPoder: ",11);
		sb.blankLine(10);
		sb.add(" §e",9);
		sb.add("   §fOnline: ",8);
		sb.add("   §fPoder: ",7);
		sb.add("   §fTerras: ",6);
		sb.blankLine(5);
		sb.add("  §fCoins: ", 4);
		sb.add("  §fCash: ", 3);
		sb.blankLine(2);
		sb.add("  "+ConfigurationManager.DEFAULT_URL_SCOREBOARD, 1);
		
		sb.build();
		ScoreBoard.boards.remove(p);
		sb.send(p);
		ScoreBoard.boards.put(p, sb);
	}
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {

		Player p = e.getPlayer();
		 MPlayer mp = MPlayer.get(p);
		new BukkitRunnable() {
			
			@Override
			public void run() {
				if(!mp.hasFaction()) {
				createScoreBoardWithoutFac(p);
				ScoreUpdater.updateScoreBoard(p);
					ScoreBoardAPI sb = (ScoreBoardAPI)ScoreBoard.boards.get(p);
					sb.update("  §fNível: §a"+mcMMOScoreBoard.getMcmmoPowerLevel(p), 9);
				}else {
					//create with fac
					createScoreBoardWithFac(p);
					ScoreUpdater.updateScoreBoard(p);
						ScoreBoardAPI sb = (ScoreBoardAPI)ScoreBoard.boards.get(p);
						sb.update("  §fNível: §a"+mcMMOScoreBoard.getMcmmoPowerLevel(p), 12);
				}
			}
		}.runTaskLater(Main.getPlugin(Main.class), 4l);
	}

	@EventHandler
	public void gastoucash(PlayerPointsChangeEvent event) {
		if(Bukkit.getPlayer(event.getPlayerId())!=null) {
			ScoreUpdater.updateScoreBoard(Bukkit.getPlayer(event.getPlayerId()));
		}
	}
	@EventHandler
	public void onExit(PlayerQuitEvent e) {
		boards.remove(e.getPlayer());
	}
	
}

package galaxy.gabrielblink.GalaxyScoreBoard.ScoreBoard;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Locale;

import org.black_ixx.playerpoints.PlayerPointsAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.massivecraft.factions.RelationParticipator;
import com.massivecraft.factions.entity.BoardColl;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.massivecore.ps.PS;

import galaxy.gabrielblink.galaxymambascore.ConfigurationManager;
import galaxy.gabrielblink.galaxymambascore.Main;


public class ScoreUpdater
  extends BukkitRunnable
{
  public void run()
  {
    Iterator<Player> iter = ScoreBoard.boards.keySet().iterator();
    try
    {
      while (iter.hasNext())
      {
        Player p = (Player)iter.next();
        updateScoreBoard(p);
      }
    }
    catch (ConcurrentModificationException localConcurrentModificationException) {}
  }
  public static int getAllMembersOnline(Faction f) {
	  int count = 0;
	  for(MPlayer mp : f.getMPlayers()) {
		  if(Bukkit.getPlayer(mp.getUuid())!=null) {
			  count++;
		  }
	  }
	  return count;
  }
  public static String getLocationName(Player p) {
          if (p.getWorld().getName().equalsIgnoreCase(ConfigurationManager.DEFAULT_WORLD_NAME)) {
              return BoardColl.get().getFactionAt(PS.valueOf(p.getLocation())).getName((RelationParticipator)MPlayer.get((Object)p.getUniqueId()));
          }else {
        	  return ConfigurationManager.getWorldFancyName(p.getWorld().getName());
          }
      }
  public static void updateScoreBoard(Player p)
  {
	  DecimalFormatSymbols DFS = new DecimalFormatSymbols(new Locale("pt", "BR"));
	  DecimalFormat FORMATTER = new DecimalFormat("###,###,###", DFS);
	  ScoreBoardAPI sb = (ScoreBoardAPI)ScoreBoard.boards.get(p);
	  MPlayer mp = MPlayer.get(p);
	  PS ps = PS.valueOf(p.getLocation());
	    if (sb != null) {
	    	if(mp.hasFaction()) {
	    		if(!mcMMOScoreBoard.runnables.containsKey(p.getName())) {
	    			sb.update("  §fNível: §a"+mcMMOScoreBoard.getMcmmoPowerLevel(p), 12);
	    		}
	    		sb.update("§a"+mp.getPower()+"/"+mp.getPowerMax(), 11);
	    		sb.update(" §e"+mp.getFactionName(), 9);
	    		sb.update("§e"+getAllMembersOnline(mp.getFaction())+"/"+mp.getFaction().getMPlayers().size(), 8);
	    		sb.update("§e"+mp.getFaction().getPower()+"/"+mp.getFaction().getPowerMax(), 7);
	    		sb.update("§e"+mp.getFaction().getClaimedWorlds().size(), 6);
	    		sb.update("§a"+FORMATTER.format(Main.getPlayerPoints().getAPI().look(p.getName())), 3);
	    		sb.update("§a"+FORMATTER.format(Main.getEconomy().getBalance(p.getName())), 4);
	    	}else {
	    		if(!mcMMOScoreBoard.runnables.containsKey(p.getName())) {
	    			sb.update("  §fNível: §a"+mcMMOScoreBoard.getMcmmoPowerLevel(p), 9);
	    		}
	    		sb.update("§a"+mp.getPower()+"/"+mp.getPowerMax(), 8);
	    		sb.update("§a"+FORMATTER.format(Main.getEconomy().getBalance(p.getName())), 4);
	    		sb.update("§a"+FORMATTER.format(Main.getPlayerPoints().getAPI().look(p.getName())), 3);
	    	}
	    	sb.setDisplayName(getLocationName(p)); 
	    }
	    
  }
}

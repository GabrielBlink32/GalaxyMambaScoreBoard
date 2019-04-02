package galaxy.gabrielblink.GalaxyScoreBoard.ScoreBoard;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

import com.gmail.nossr50.mcMMO;
import com.gmail.nossr50.api.ExperienceAPI;
import com.gmail.nossr50.datatypes.skills.SkillType;
import com.gmail.nossr50.events.experience.McMMOPlayerXpGainEvent;
import com.massivecraft.factions.entity.MPlayer;

import galaxy.gabrielblink.galaxymambascore.Main;

public class mcMMOScoreBoard implements Listener{

	public HashMap<String,Long> cooldownmanager = new HashMap<String,Long>();
	public static HashMap<String,Integer> runnables = new HashMap<String,Integer>();
	public static int getMcmmoPowerLevel(Player p) {
		try {
			return ExperienceAPI.getPowerLevel(p);
		} catch (Exception e) {
			mcMMO.getDatabaseManager().loadPlayerProfile(p.getUniqueId());
		   return 0;
		}
	}
	@EventHandler
	public void mcMMOScore(McMMOPlayerXpGainEvent event) {
		Player p = event.getPlayer();
		 MPlayer mp = MPlayer.get(p);
		if(ScoreBoard.boards.containsKey(p)) {
			if(cooldownmanager.containsKey(p.getName())) {
				if(System.currentTimeMillis() - cooldownmanager.get(p.getName()) >= 1000*3) {
				cooldownmanager.put(p.getName(), System.currentTimeMillis());
				}
			}else {
				cooldownmanager.put(p.getName(), System.currentTimeMillis());
			}
			  if(!runnables.containsKey(p.getName())) {
				  int id = new BukkitRunnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							if(ScoreBoard.boards.containsKey(p)) {
							if(System.currentTimeMillis() - cooldownmanager.get(p.getName()) >= 1000*15) {
								  if(!mp.hasFaction()) {
									  ScoreBoard.createScoreBoardWithoutFac(p);
										ScoreUpdater.updateScoreBoard(p);
											ScoreBoardAPI sb = (ScoreBoardAPI)ScoreBoard.boards.get(p);
											sb.update("  §fNível: §a"+mcMMOScoreBoard.getMcmmoPowerLevel(p), 9);
									}else {
									    ScoreBoard.createScoreBoardWithFac(p);
										ScoreUpdater.updateScoreBoard(p);
											ScoreBoardAPI sb = (ScoreBoardAPI)ScoreBoard.boards.get(p);
											sb.update("  §fNível: §a"+mcMMOScoreBoard.getMcmmoPowerLevel(p), 12);
									}
								  cooldownmanager.put(p.getName(), System.currentTimeMillis());
									runnables.remove(p.getName());
									this.cancel();
							  }
							}else {
								runnables.remove(p.getName());
								this.cancel();
							}
						}
					}.runTaskTimer(Main.getPlugin(Main.class), 20L*10, 20L*10).getTaskId();
					runnables.put(p.getName(), id);
			  }
			  ScoreBoardAPI sb = (ScoreBoardAPI)ScoreBoard.boards.get(p);
		switch(event.getSkill()) {
		
		case ACROBATICS:
			if(!mp.hasFaction()) {
				sb.update("  §fAcrobacia: §a"+ExperienceAPI.getLevel(p, event.getSkill().toString()), 9);
			}else {
				sb.update("  §fAcrobacia: §a"+ExperienceAPI.getLevel(p, event.getSkill().toString()), 12);
			}
			break;
		case ALCHEMY:
			if(!mp.hasFaction()) {
				sb.update("  §fAlquimia: §a"+ExperienceAPI.getLevel(p, event.getSkill().toString()), 9);
			}else {
				sb.update("  §fAlquimia: §a"+ExperienceAPI.getLevel(p, event.getSkill().toString()), 12);
			}
			break;
		case ARCHERY:
			if(!mp.hasFaction()) {
				sb.update("  §fArqueiro: §a"+ExperienceAPI.getLevel(p, event.getSkill().toString()), 9);
			}else {
				sb.update("  §fArqueiro: §a"+ExperienceAPI.getLevel(p, event.getSkill().toString()), 12);
			}
			break;
		case AXES:
			if(!mp.hasFaction()) {
				sb.update("  §fMachado: §a"+ExperienceAPI.getLevel(p, event.getSkill().toString()), 9);
			}else {
				sb.update("  §fMachado: §a"+ExperienceAPI.getLevel(p, event.getSkill().toString()), 12);
			}
			break;
		case EXCAVATION:
			if(!mp.hasFaction()) {
				sb.update("  §fEscavação: §a"+ExperienceAPI.getLevel(p, event.getSkill().toString()), 9);
			}else {
				sb.update("  §fEscavação: §a"+ExperienceAPI.getLevel(p, event.getSkill().toString()), 12);
			}
			break;
		case FISHING:
			if(!mp.hasFaction()) {
				sb.update("  §fPescaria: §a"+ExperienceAPI.getLevel(p, event.getSkill().toString()), 9);
			}else {
				sb.update("  §fPescaria: §a"+ExperienceAPI.getLevel(p, event.getSkill().toString()), 12);
			}
			break;
		case HERBALISM:
			if(!mp.hasFaction()) {
				sb.update("  §fHerbalismo: §a"+ExperienceAPI.getLevel(p, event.getSkill().toString()), 9);
			}else {
				sb.update("  §fHerbalismo: §a"+ExperienceAPI.getLevel(p, event.getSkill().toString()), 12);
			}
			break;
		case MINING:
			if(!mp.hasFaction()) {
				sb.update("  §fMineração: §a"+ExperienceAPI.getLevel(p, event.getSkill().toString()), 9);
			}else {
				sb.update("  §fMineração: §a"+ExperienceAPI.getLevel(p, event.getSkill().toString()), 12);
			}
			break;
		case REPAIR:
			if(!mp.hasFaction()) {
				sb.update("  §fReparação: §a"+ExperienceAPI.getLevel(p, event.getSkill().toString()), 9);
			}else {
				sb.update("  §fReparação: §a"+ExperienceAPI.getLevel(p, event.getSkill().toString()), 12);
			}
			break;
		case SWORDS:
			if(!mp.hasFaction()) {
				sb.update("  §fEspadas: §a"+ExperienceAPI.getLevel(p, event.getSkill().toString()), 9);
			}else {
				sb.update("  §fEspadas: §a"+ExperienceAPI.getLevel(p, event.getSkill().toString()), 12);
			}
			break;
		default:
			break;
		
		}
		}
	}
}

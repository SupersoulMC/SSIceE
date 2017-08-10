package hell.supersoul.ice;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import hell.supersoul.managers.MissionManager;
import hell.supersoul.npc.Mission;
import hell.supersoul.npc.SPlayer;

public class TeamManager {
	private static TeamManager tm = new TeamManager();

	public static TeamManager getManager() {
		return tm;
	}

	private HashMap<String, String> team = new HashMap<>();

	public void loadData() {
		for (String playerName : Main.getInstance().getConfig().getConfigurationSection("data.team").getKeys(false)) {
			team.put(playerName, Main.getInstance().getConfig().getString("data.team." + playerName));
		}
	}

	public boolean chooseTeam(String playerName) {
		return false;
	}

	public boolean teamDecided(String playerName, String team) {
		if (team.contains(playerName)) {
			Player player = Bukkit.getPlayer(playerName);
			team = TeamManager.getManager().getTeam(playerName);
			MissionManager.getManager().missionAccepted(player, Mission.getMission("Two Brave Warriors"));
			if (team.equals("Sero")) {
				SPlayer.getSPlayer(player).getPendingM().add(Mission.getMission("Sero's Welcome"));
				MissionManager.getManager().missionAccepted(player, Mission.getMission("Sero's Welcome"));
			} else {
				SPlayer.getSPlayer(player).getPendingM().add(Mission.getMission("Elub's Welcome"));
				MissionManager.getManager().missionAccepted(player, Mission.getMission("Elub's Welcome"));
			}
			return false;
		}
		this.team.put(playerName, team);
		Player player = Bukkit.getPlayer(playerName);
		MissionManager.getManager().missionAccepted(player, Mission.getMission("Two Brave Warriors"));
		if (team.equals("Sero")) {
			SPlayer.getSPlayer(player).getPendingM().add(Mission.getMission("Sero's Welcome"));
			MissionManager.getManager().missionAccepted(player, Mission.getMission("Sero's Welcome"));
		} else {
			SPlayer.getSPlayer(player).getPendingM().add(Mission.getMission("Elub's Welcome"));
			MissionManager.getManager().missionAccepted(player, Mission.getMission("Elub's Welcome"));
		}
		Main.getInstance().getConfig().set("data.team." + playerName, team);
		for (Player p : Bukkit.getOnlinePlayers()) {
			p.sendMessage(Main.prefix + ChatColor.GREEN + playerName + ChatColor.GRAY + " joined Team "
					+ getTeamWithColor(playerName) + ChatColor.GRAY + "!");
		}
		Main.getInstance().saveConfig();
		return true;
	}

	public String getTeamWithColor(String playerName) {
		if (!team.containsKey(playerName))
			return ChatColor.RED + "" + ChatColor.BOLD + "Error: Invalid Team!";
		else if (team.get(playerName).equals("Sero"))
			return ChatColor.LIGHT_PURPLE + "Sero";
		else
			return ChatColor.AQUA + "Elub";
	}

	public String getTeam(String playerName) {
		if (team.containsKey(playerName))
			return team.get(playerName);
		else
			return ChatColor.RED + "" + ChatColor.BOLD + "Error: Invalid Team!";
	}
	
	public Team getTeamType(String playerName) {
		if (!team.containsKey(playerName)) return null;
		else if (team.get(playerName).equals("Sero")) return Team.SERO;
		return Team.ELUB;
	}
	
	public Team getTeamType(Player player) {
		return getTeamType(player.getName());
	}

	public String getTeam(Player player) {
		return getTeam(player.getName());
	}

	public HashMap<String, String> getTeam() {
		return team;
	}
	
	public enum Team {
		SERO, ELUB;
	}
	
	public String getTeamColor(String playerName) {
		if (!team.containsKey(playerName))
			return ChatColor.GRAY + "";
		else if (team.get(playerName).equals("Sero"))
			return ChatColor.LIGHT_PURPLE + "";
		else
			return ChatColor.AQUA + "";
	}
	
	public String getTeamColor(Player player) {
		return getTeamColor(player.getName());
	}
}

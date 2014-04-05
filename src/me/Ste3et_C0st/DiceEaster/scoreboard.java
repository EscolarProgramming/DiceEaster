package me.Ste3et_C0st.DiceEaster;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

public class scoreboard
{
  public static void create(Player p, int i)
  {
    ScoreboardManager manager = Bukkit.getScoreboardManager();
    Scoreboard board = manager.getNewScoreboard();
    String BoardName = "E" + main.trimPlayerName(p.getName(), 13);
    Objective objective = board.registerNewObjective(BoardName, "dummy");
    objective.setDisplaySlot(DisplaySlot.SIDEBAR);
    objective.setDisplayName("§2§oEaster-Egg");
    Score map = objective.getScore(Bukkit.getOfflinePlayer("§2Find:"));
    map.setScore(i);
    Score eier = objective.getScore(Bukkit.getOfflinePlayer("§7Hidden:"));
    eier.setScore(variablen.eier);
    
    variablen.playerScoreboard.put(p, board);
  }
  
  public static void set(Player p)
  {
    p.setScoreboard((Scoreboard)variablen.playerScoreboard.get(p));
  }
  
  public static void leave(Player p)
  {
    if ((p.getScoreboard() != null) && 
      (p.getScoreboard().getObjective(DisplaySlot.SIDEBAR) != null) && 
      (p.getScoreboard().getObjective(DisplaySlot.SIDEBAR).getDisplayName().equalsIgnoreCase("§2§oEaster-Egg")))
    {
      p.getScoreboard().getObjective(DisplaySlot.SIDEBAR).unregister();
      if (variablen.playerScoreboard.get(p) != null) {
        variablen.playerScoreboard.remove(p);
      }
    }
  }
  
  public static void update(Player p, int i)
  {
    Scoreboard board = (Scoreboard)variablen.playerScoreboard.get(p);
    String BoardName = "E" + main.trimPlayerName(p.getName(), 13);
    Objective objective = board.getObjective(BoardName);
    objective.setDisplayName("§2§oEaster-Egg");
    board.resetScores(Bukkit.getOfflinePlayer("§2Find:"));
    Score s = objective.getScore(Bukkit.getOfflinePlayer("§2Find:"));
    if (objective.getScore(Bukkit.getOfflinePlayer("§7Hidden:")).getScore() != variablen.eier)
    {
      board.resetScores(Bukkit.getOfflinePlayer("§7Hidden:"));
      Score hidden = objective.getScore(Bukkit.getOfflinePlayer("§7Hidden:"));
      hidden.setScore(variablen.eier);
    }
    s.setScore(i + 1);
  }
}

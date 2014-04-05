package me.Ste3et_C0st.DiceEaster;

import java.util.HashMap;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;

public class variablen
{
  public static HashMap<Player, Integer> EasterEggs = new HashMap<Player, Integer>();
  public static HashMap<Player, Scoreboard> playerScoreboard = new HashMap<Player, Scoreboard>();
  public static HashMap<Player, Integer> playerEisammler = new HashMap<Player, Integer>();
  public static HashMap<Player, Integer> playerEisammler2 = new HashMap<Player, Integer>();
  public static HashMap<Player, Integer> info = new HashMap<Player, Integer>();
  public static HashMap<String, String> msg = new HashMap<String, String>();
  public static HashMap<Player, Integer> playerSpeere = new HashMap<Player, Integer>();
  
  public variablen(main main) {}
  
  public static String replace(String txt, String arg, String replaced)
  {
    String replace = txt;
    return replace.replace(arg, replaced);
  }
  
  public static int eier = 0;
}

package de.Ste3et_C0st.DiceEaster;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;

public class variablen
{
  public static HashMap<Player, Integer> EasterEggs = new HashMap<Player, Integer>();
  public static HashMap<Player, Scoreboard> playerScoreboard = new HashMap<Player, Scoreboard>();
  public static List<Player> playerEisammler = new ArrayList<Player>();
  public static HashMap<String, String> msg = new HashMap<String, String>();
  
  public static String replace(String txt, String arg, String replaced)
  {
    String replace = txt;
    return replace.replace(arg, replaced);
  }
  
	public static String createRandomRegistryId()
	{  
	    String val = main.getInstance().getName().substring(0,3);      
	    int ranChar = 65 + (new Random()).nextInt(90-65);
	    char ch = (char)ranChar;        
	    val += ch;      
	    Random r = new Random();
	    int numbers = 100000 + (int)(r.nextFloat() * 899900);
	    val += String.valueOf(numbers);
	    val += "-";
	    for(int i = 0; i<6;){
	        int ranAny = 48 + (new Random()).nextInt(90-65);
	        if(!(57 < ranAny && ranAny<= 65)){
	        char c = (char)ranAny;      
	        val += c;
	        i++;
	        }
	    }

	    return val;
	}
}

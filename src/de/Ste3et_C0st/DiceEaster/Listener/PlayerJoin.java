package de.Ste3et_C0st.DiceEaster.Listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import de.Ste3et_C0st.DiceEaster.EggManager;
import de.Ste3et_C0st.DiceEaster.DiceEaster;

public class PlayerJoin implements Listener {

	  @EventHandler
	  public void onPlayerJoin(PlayerJoinEvent e){
		  EggManager.loadPlayer(e.getPlayer());
		  DiceEaster.getInstance().updateScoreboard(e.getPlayer());
	  }
}

package de.Ste3et_C0st.DiceEaster.Listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import de.Ste3et_C0st.DiceEaster.EggManager;
import de.Ste3et_C0st.DiceEaster.DiceEaster;

public class PlayerQuit implements Listener {
	  @EventHandler
	  public void onPlayerQuit(PlayerQuitEvent e){
		  if(DiceEaster.getInstance().data != null){DiceEaster.getInstance().data.addPlayer(e.getPlayer());}
		  EggManager.savePlayer(e.getPlayer());
		  if(DiceEaster.getInstance().scoreboards.containsKey(e.getPlayer())){
			  DiceEaster.getInstance().scoreboards.get(e.getPlayer()).destroy();
			  DiceEaster.getInstance().scoreboards.remove(e.getPlayer());
		  }
	  }
}

package de.Ste3et_C0st.DiceEaster.Listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

import de.Ste3et_C0st.DiceEaster.DiceEaster;

public class PlayerChangeWorld implements Listener {

	  @EventHandler
	  public void onPlayerSwitchWorld(PlayerChangedWorldEvent e){
		  if(!DiceEaster.getInstance().world.contains(e.getPlayer().getWorld().getName())){
			  if(DiceEaster.getInstance().scoreboards.containsKey(e.getPlayer())){
				  DiceEaster.getInstance().scoreboards.get(e.getPlayer()).destroy();
				  DiceEaster.getInstance().scoreboards.remove(e.getPlayer());
				  return;
			  }
		  }
		  
		  DiceEaster.getInstance().updateScoreboard(e.getPlayer());
	  }
}

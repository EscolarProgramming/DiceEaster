package de.Ste3et_C0st.DiceEaster.Listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

import de.Ste3et_C0st.DiceEaster.main;

public class PlayerChangeWorld implements Listener {

	  @EventHandler
	  public void onPlayerSwitchWorld(PlayerChangedWorldEvent e){
		  if(!main.getInstance().world.contains(e.getPlayer().getWorld().getName())){
			  if(main.getInstance().scoreboards.containsKey(e.getPlayer())){
				  main.getInstance().scoreboards.get(e.getPlayer()).destroy();
				  main.getInstance().scoreboards.remove(e.getPlayer());
				  return;
			  }
		  }
		  
		  main.getInstance().updateScoreboard(e.getPlayer());
	  }
}

package de.Ste3et_C0st.DiceEaster.Listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;

import de.Ste3et_C0st.DiceEaster.EggManager;
import de.Ste3et_C0st.DiceEaster.DiceEaster;

public class PlayerKick implements Listener {
	  @EventHandler
	  public void onPlayerQuit(PlayerKickEvent e){
		  if(DiceEaster.getInstance().data != null){DiceEaster.getInstance().data.addPlayer(e.getPlayer());}
		  EggManager.savePlayer(e.getPlayer());
		  if(DiceEaster.getInstance().scoreboards.containsKey(e.getPlayer())){
			  DiceEaster.getInstance().scoreboards.get(e.getPlayer()).destroy();
			  DiceEaster.getInstance().scoreboards.remove(e.getPlayer());
		  }
	  }
}

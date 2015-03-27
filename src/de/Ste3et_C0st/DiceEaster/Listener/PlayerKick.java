package de.Ste3et_C0st.DiceEaster.Listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;

import de.Ste3et_C0st.DiceEaster.EggManager;
import de.Ste3et_C0st.DiceEaster.main;

public class PlayerKick implements Listener {
	  @EventHandler
	  public void onPlayerQuit(PlayerKickEvent e){
		  if(main.getInstance().data != null){main.getInstance().data.addPlayer(e.getPlayer());}
		  EggManager.savePlayer(e.getPlayer());
		  if(main.getInstance().scoreboards.containsKey(e.getPlayer())){
			  main.getInstance().scoreboards.get(e.getPlayer()).destroy();
			  main.getInstance().scoreboards.remove(e.getPlayer());
		  }
	  }
}

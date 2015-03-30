package de.Ste3et_C0st.DiceEaster.Listener;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.gmail.filoghost.holographicdisplays.api.Hologram;

import de.Ste3et_C0st.DiceEaster.Egg;
import de.Ste3et_C0st.DiceEaster.DiceEaster;
import de.Ste3et_C0st.DiceEaster.variablen;

public class PlayerDropEvent implements Listener {

	  @EventHandler
	  public void onDrop(PlayerDropItemEvent e){
		  if(e.getItemDrop() == null){return;}
		  if(!e.getItemDrop().getItemStack().hasItemMeta()){return;}
		  if(!e.getItemDrop().getItemStack().getItemMeta().hasDisplayName()){return;}
		  if(!e.getItemDrop().getItemStack().getType().equals(DiceEaster.getInstance().m)){return;}
		  Item item = e.getItemDrop();
		  ItemStack is = item.getItemStack();
		  ItemMeta im = is.getItemMeta();
		  Location loc = item.getLocation();
		  Player p = e.getPlayer();
		  if(!im.getDisplayName().equals(variablen.msg.get("EggName"))){return;}
		  if(!DiceEaster.getInstance().world.contains(item.getLocation().getWorld().getName())){p.sendMessage(DiceEaster.getInstance().header + variablen.msg.get("WrongWorld")); return;}
		  is.setDurability(DiceEaster.getInstance().eggs.get(DiceEaster.rnd(0, DiceEaster.getInstance().eggs.size())));
		  Hologram holo = DiceEaster.getInstance().createHolo(e.getItemDrop().getLocation().add(0,-0.5,0), is);
		  item.remove();
		  Egg egg = new Egg(variablen.createRandomRegistryId(), holo, loc.getWorld(), loc.add(0,-0.5,0), is, DiceEaster.getInstance());
		  DiceEaster.egglist.add(egg);
			  for(Player player : Bukkit.getOnlinePlayers()){
				  DiceEaster.getInstance().updateScoreboard(player);
			  }
	  }
	  
	  
}

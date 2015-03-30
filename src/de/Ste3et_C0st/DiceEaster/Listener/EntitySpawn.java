package de.Ste3et_C0st.DiceEaster.Listener;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

import de.Ste3et_C0st.DiceEaster.Bunny;
import de.Ste3et_C0st.DiceEaster.DiceEaster;

public class EntitySpawn implements Listener {

	@EventHandler
	public void onSpawn(CreatureSpawnEvent e){
		if(e.getEntityType().equals(EntityType.RABBIT) && e.getSpawnReason().equals(SpawnReason.DEFAULT)){
			if(DiceEaster.rnd(0, DiceEaster.getInstance().spawnRate) == 1){
				new Bunny(e.getLocation(), DiceEaster.getInstance(), false);
			}
		}else if(e.getEntityType().equals(EntityType.SKELETON) && e.getSpawnReason().equals(SpawnReason.DEFAULT)){
			if(DiceEaster.getInstance().aggrisive){
				new Bunny(e.getLocation(), DiceEaster.getInstance(), true);
			}else{
				new Bunny(e.getLocation(), DiceEaster.getInstance(), false);
			}
		}
	}
}

package de.Ste3et_C0st.DiceEaster.Listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

import de.Ste3et_C0st.DiceEaster.Bunny;
import de.Ste3et_C0st.DiceEaster.DiceEaster;

public class EntitySpawn implements Listener {

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onSpawn(CreatureSpawnEvent e){
		if((e.getSpawnReason().equals(SpawnReason.DEFAULT) || e.getSpawnReason().equals(SpawnReason.SPAWNER_EGG)  || e.getSpawnReason().equals(SpawnReason.SPAWNER))){
			//Rabbit ID = 101
			//Skeleton ID = 51
			if(e.getEntityType().getTypeId() == 101){
				new Bunny(e.getLocation(), DiceEaster.getInstance(), false);
			}else if(e.getEntityType().getTypeId() == 51){
				if(DiceEaster.getInstance().aggrisive){
					new Bunny(e.getLocation(), DiceEaster.getInstance(), true);
				}else{
					new Bunny(e.getLocation(), DiceEaster.getInstance(), false);
				}
			}
		}
	}
}

package de.Ste3et_C0st.DiceEaster;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Skeleton;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;


@SuppressWarnings("deprecation")
public class Bunny implements Listener{

	Skeleton skeleton1;
	Skeleton skeleton2;
	Boolean aggrisive = false;
	public Bunny(Location loc, Plugin plugin, Boolean b){
		if(!DiceEaster.getInstance().world.contains(loc.getWorld().getName())){return;}
		
		aggrisive = b;
		skeleton1 = (Skeleton) loc.getWorld().spawnCreature(loc, EntityType.SKELETON);
		skeleton2 = (Skeleton) loc.getWorld().spawnCreature(loc, EntityType.SKELETON);
		skeleton2.getEquipment().setItemInHand(null);
		skeleton1.setPassenger(skeleton2);
		skeleton2.setCustomName("Dinnerbone");
		skeleton2.setCustomNameVisible(false);
		ItemStack is1 = Skull.getCustomSkull(DiceEaster.getInstance().link1);
		ItemStack is2 = Skull.getCustomSkull(DiceEaster.getInstance().link2);
		skeleton1.getEquipment().setHelmet(is1);
		skeleton2.getEquipment().setHelmet(is2);

		setItem(skeleton1, DiceEaster.getInstance().item1);
		setItem(skeleton2, DiceEaster.getInstance().item2);
		
		
		if(DiceEaster.getInstance().rndItemRange != 0){
			if(DiceEaster.rnd(0, DiceEaster.getInstance().rndItemRange) == 1){
				setItem(skeleton1, DiceEaster.getInstance().rnd_item1);
				setItem(skeleton2, DiceEaster.getInstance().rnd_item2);
			}
		}
		
		if(DiceEaster.getInstance().rndSpawnEggs != 0){
			if(DiceEaster.rnd(0, DiceEaster.getInstance().rndSpawnEggs) == 1){
				for(int i = 0; i<=DiceEaster.getInstance().rndSpawnEggs; i++){
					ItemStack is = new ItemStack(Material.getMaterial(DiceEaster.getInstance().rndSpawnEggs), DiceEaster.getInstance().rndAmount);
					ItemMeta im = is.getItemMeta();
					im.setLore(Arrays.asList(variablen.createRandomRegistryId()));
					is.setItemMeta(im);
					skeleton1.getLocation().getWorld().dropItemNaturally(loc, is);
				}
			}
		}
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
		DiceEaster.getInstance().bunnyList.add(this);
	}
	
	public void destroy(){
		skeleton1.remove();
		skeleton2.remove();
	}
	
	public void setItem(Skeleton skeleton, Integer integer){
		if(integer != 0){
			skeleton.getEquipment().setItemInHand(new ItemStack(Material.getMaterial(integer)));
		}else{
			skeleton.getEquipment().setItemInHand(new ItemStack(Material.STONE));
			Bukkit.getScheduler().runTaskLater(DiceEaster.getInstance(), new Runnable() {
			@Override
			public void run() {skeleton2.getEquipment().setItemInHand(new ItemStack(Material.AIR));}}, 5);
		}
	}

	@EventHandler
	public void onDie(EntityDeathEvent e){
		Boolean diBunny = false;
		Skeleton skeleton = null;
		if(e.getEntity().equals(skeleton2) || e.getEntity().equals(skeleton1)){
			skeleton = skeleton1;
			diBunny=true;
			skeleton1.remove();
		}
		if(diBunny){
			e.getDrops().clear();
			if(skeleton!=null && DiceEaster.getInstance().bunnysDropItems){
				e.getDrops().add(skeleton1.getEquipment().getItemInHand());
			}
			DiceEaster.getInstance().bunnyList.remove(this);
		}
	}
	
	@EventHandler
	public void onCreatureDamage(EntityDamageEvent e){
		if(e.getEntity().equals(skeleton2)){skeleton1.damage(e.getDamage());}
		if(e.getEntity().equals(skeleton1)){skeleton2.damage(e.getDamage());}
	}
	
	@EventHandler
	public void onCreatureDamgebyEntity(EntityDamageByEntityEvent e){
		if(!aggrisive){
			if(e.getDamager().equals(skeleton1) || e.getDamager().equals(skeleton2)){
				e.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void onTarget(EntityTargetEvent e){
		if(!aggrisive){
			if(e.getEntity().equals(skeleton1) || e.getEntity().equals(skeleton2)){
				e.setCancelled(true);
			}
		}
	}
}

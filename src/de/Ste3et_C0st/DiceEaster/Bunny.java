package de.Ste3et_C0st.DiceEaster;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.Sound;
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

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;


@SuppressWarnings("deprecation")
public class Bunny implements Listener{

	Skeleton skeleton1;
	Skeleton skeleton2;
	Boolean aggrisive = false, hurt = false;
	public Bunny(Location loc, Plugin plugin, Boolean b){
		if(!DiceEaster.getInstance().world.contains(loc.getWorld().getName())){return;}
		
		aggrisive = b;
		skeleton1 = (Skeleton) loc.getWorld().spawnEntity(loc, EntityType.SKELETON);
		skeleton2 = (Skeleton) loc.getWorld().spawnEntity(loc, EntityType.SKELETON);
		skeleton2.getEquipment().setItemInMainHand(null);
		skeleton1.setPassenger(skeleton2);
		skeleton2.setCustomName("Dinnerbone");
		skeleton2.setCustomNameVisible(false);
		skeleton1.setSilent(true);
		skeleton2.setSilent(true);
		
		ItemStack is1 = null;
		ItemStack is2 = null;
		
		try{
			is1 = getSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZGM3YTMxN2VjNWMxZWQ3Nzg4Zjg5ZTdmMWE2YWYzZDJlZWI5MmQxZTk4NzljMDUzNDNjNTdmOWQ4NjNkZTEzMCJ9fX0=");
			is2 = getSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTk4YmEyYjM3NGNmYzg5NDU0YzFiOGMzMmRiNDU4YTI3MDY3NTQzOWE0OTU0OTZjOTY3NzFjOTg5MTE2MTYyIn19fQ==");
		}catch(Exception e){
			is1 = Skull.getPlayerSkull("rabbit2077");
			is2 = Skull.getPlayerSkull("bananasquad");
		}
		
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
	
	public String generateSessionKey(int length){
		String alphabet = new String("0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"); //9
		int n = alphabet.length();
		String result = new String(); 
		Random r = new Random();
		for (int i=0; i<length; i++) result = result + alphabet.charAt(r.nextInt(n));
		return result;
	}
	
	public ItemStack getSkull(String s) {
		ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());
		ItemMeta headMeta = skull.getItemMeta();
        GameProfile profile = new GameProfile(UUID.randomUUID(), generateSessionKey(10));
        Property textures = new Property(
            "textures", s
        );
        profile.getProperties().put(textures.getName(), textures);
        
        try {
            Field profileField = headMeta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(headMeta, profile);
        } catch (NoSuchFieldException | SecurityException e) {
           	e.printStackTrace();
        } catch (IllegalArgumentException | IllegalAccessException e) {
        	e.printStackTrace();
        }
        skull.setItemMeta(headMeta);
        return skull;
    }
	
	public void destroy(){
		skeleton1.remove();
		skeleton2.remove();
	}
	
	public void setItem(Skeleton skeleton, Integer integer){
		if(integer != 0){
			skeleton.getEquipment().setItemInMainHand(new ItemStack(Material.getMaterial(integer)));
		}else{
			skeleton.getEquipment().setItemInMainHand(new ItemStack(Material.STONE));
			Bukkit.getScheduler().runTaskLater(DiceEaster.getInstance(), new Runnable() {
			@Override
			public void run() {skeleton2.setCustomNameVisible(false);skeleton2.getEquipment().setItemInMainHand(new ItemStack(Material.AIR));}}, 5);
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
		if(!hurt){
			hurt = true;
			if(e.getEntity().getUniqueId().equals(skeleton2.getUniqueId())){
				skeleton1.damage(e.getDamage());
				skeleton1.getWorld().playSound(skeleton1.getLocation(), Sound.ENTITY_RABBIT_ATTACK, 1, 10);
			}else{
				skeleton2.damage(e.getDamage());
				skeleton2.getWorld().playSound(skeleton1.getLocation(), Sound.ENTITY_RABBIT_ATTACK, 1, 10);
			}
		}else{
			hurt = false;
		}
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

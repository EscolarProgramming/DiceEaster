package de.Ste3et_C0st.DiceEaster;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;

public class Egg {

	String id;
	Hologram hologram;
	World w;
	Location loc;
	ItemStack is;
	Hologram debug = null;
	Plugin plugin;
	public Egg(String id, Hologram hologram, World w, Location loc, ItemStack is, Plugin plugin){
		this.id = id;
		this.hologram = hologram;
		this.w = w;
		this.loc = loc;
		this.is = is;
		this.plugin = plugin;
	}
	
	public void debug(){
		if(debug == null){
			Location location = new Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ());
			this.debug = HologramsAPI.createHologram(plugin, location.add(0,0.5,0));
			this.debug.appendTextLine(id);
		}else{
			this.debug.delete();
			this.debug = null;
		}
	}
	
	public Boolean isDebug(){if(debug == null){return false;}else{return true;}}
	public String getID(){return this.id;}
	public Hologram getHologram(){return this.hologram;}
	public World getWorld(){return this.w;}
	public Location getLocation(){return this.loc;}
	public ItemStack getItemStack(){return this.is;}
}

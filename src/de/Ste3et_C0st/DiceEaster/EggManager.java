package de.Ste3et_C0st.DiceEaster;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.gmail.filoghost.holographicdisplays.api.Hologram;

public class EggManager {
	  private static config cc;
	  private static FileConfiguration fc;
	  @SuppressWarnings("deprecation")
	  public static void saveEggs(){
		  if(!main.egglist.isEmpty()){
			  World w = null;
			  for(Egg egg : main.egglist){
				  if(w==null){w=egg.getWorld();}else if(!w.equals(egg.getWorld())){w=egg.getWorld();}
				  cc = new config();
				  String name = egg.getID() + ".yml";
				  String folder = "/Eggs/" + w.getName();
				  fc = cc.getConfig(name, folder);
				  fc.set("Egg.ID", egg.getID());
				  fc.set("Egg.Location.X", egg.getLocation().getX());
				  fc.set("Egg.Location.Y", egg.getLocation().getY());
				  fc.set("Egg.Location.Z", egg.getLocation().getZ());
				  fc.set("Egg.Location.World", w.getName());
				  fc.set("Egg.ItemStack.Material",(Integer) egg.getItemStack().getType().getId());
				  fc.set("Egg.ItemStack.subbID",egg.getItemStack().getDurability());
				  cc.saveConfig(name, fc, folder);
			  }
		  }
	  }
	  
	  @SuppressWarnings("deprecation")
	  public static void loadEggs(){
		if(!new File("plugins/DiceEaster/Eggs").exists()){return;}
		String[] ordner = new File("plugins/DiceEaster/Eggs").list();
		List<String> stringListe = new ArrayList<String>();
		for(String a : ordner){
			stringListe.add(a);
		}
		
		for(String folders : stringListe){
			String[] files = new File("plugins/DiceEaster/Eggs/" + folders).list();
			for(String file : files){
				if(Bukkit.getWorld(folders) != null){
					World w  = Bukkit.getWorld(folders);
					cc = new config();
					fc = cc.getConfig(file, "/Eggs/" + folders);
					Location loc = new Location(w, fc.getDouble("Egg.Location.X"), fc.getDouble("Egg.Location.Y"), fc.getDouble("Egg.Location.Z"));
					Integer matID = fc.getInt("Egg.ItemStack.Material");
					Integer subID = fc.getInt("Egg.ItemStack.subbID");
					ItemStack is = new ItemStack(Material.getMaterial(matID));
					is.setDurability(subID.shortValue());
					Hologram holo = main.getInstance().createHolo(loc, is);
					Egg egg = new Egg(fc.getString("Egg.ID"), holo, w, loc, is, main.getInstance());
					main.egglist.add(egg);
				}
			}
		}
	} 
	  
	public static void killALL(){
		if(!main.egglist.isEmpty()){
		 for(Egg egg : main.egglist){
		  if(egg.isDebug()){egg.debug();}
		  egg.getHologram().clearLines();
		  egg.getHologram().delete();
		 }
		 main.egglist.clear();
	}
		  
	if(!main.getInstance().scoreboards.isEmpty()){
		for(Player player : main.getInstance().scoreboards.keySet()){
			main.getInstance().scoreboards.get(player).destroy();
		}
	  }
	}
	
	  public static void savePlayer(Player player){
		  new File("plugins/DiceEaster/players/" + player.getUniqueId().toString() + ".yml").delete();
		  cc = new config();
		  String name = player.getUniqueId().toString() + ".yml";
		  String folder = "/players";
		  fc = cc.getConfig(name, folder);
		  List<String> idLIst = new ArrayList<String>();
		  if(!main.getInstance().playerEggs.isEmpty()){
			  for(Egg egg : main.getInstance().playerEggs.get(player)){
				  idLIst.add(egg.getID());
			  }
			  fc.set("Eggs", idLIst);
			  cc.saveConfig(name, fc, folder);
		  }

	  }
	  @SuppressWarnings("unchecked")
	  public static void loadPlayer(Player player){
		  try{
			  cc = new config();
			  String name = player.getUniqueId().toString() + ".yml";
			  String folder = "/players";
			  fc = cc.getConfig(name, folder);
			  List<String> playerEggsLIst = (List<String>) fc.getList("Eggs");
			  main.getInstance().playerEggs.put(player, getEggs(playerEggsLIst));
		  }catch(Exception e){}
	  }
	  
	  public static List<Egg> getEggs(List<String> list){
		  List<Egg> eggs = new ArrayList<Egg>();
		  for(Egg egg : main.egglist){
			  if(list.contains(egg.getID())){
				  eggs.add(egg);
			  }
		  }
		  return eggs;
	  }
}

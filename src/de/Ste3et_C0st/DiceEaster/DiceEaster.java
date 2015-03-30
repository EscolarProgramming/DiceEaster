package de.Ste3et_C0st.DiceEaster;

import io.puharesource.mc.titlemanager.api.ActionbarTitleObject;
import io.puharesource.mc.titlemanager.api.TitleObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import me.confuser.barapi.BarAPI;
import net.milkbowl.vault.Metrics;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import com.gmail.filoghost.holographicdisplays.api.handler.PickupHandler;
import com.gmail.filoghost.holographicdisplays.api.line.ItemLine;

import de.Ste3et_C0st.DiceEaster.Listener.EntitySpawn;
import de.Ste3et_C0st.DiceEaster.Listener.PlayerChangeWorld;
import de.Ste3et_C0st.DiceEaster.Listener.PlayerDropEvent;
import de.Ste3et_C0st.DiceEaster.Listener.PlayerJoin;
import de.Ste3et_C0st.DiceEaster.Listener.PlayerKick;
import de.Ste3et_C0st.DiceEaster.Listener.PlayerQuit;

public class DiceEaster extends JavaPlugin implements Listener
{
  public String pl = "";
  private Economy econ = null;
  private static Permission perms = null;
  public Double Prize = Double.valueOf(0.0D);
  public List<String> world = new ArrayList<String>();
  public static List<Egg> egglist = new ArrayList<Egg>();
  private static List<String> commandList = new ArrayList<String>();
  public HashMap<Player, List<Egg>> playerEggs = new HashMap<Player, List<Egg>>();
  public HashMap<Player, ScoreB> scoreboards = new HashMap<Player, ScoreB>();
  public HashMap<Player, Egg> lastEgg = new HashMap<Player, Egg>();
  public String sendMsg = "Action";
  public String header = "";
  public String Language = "en_en";
  public String link1 = "https://dl.dropboxusercontent.com/u/79646035/Heads/skin02.png";
  public String link2 = "https://dl.dropboxusercontent.com/u/79646035/Heads/skin03.png";
  public Integer item1 = 391;
  public Integer item2 = 0;
  public Integer rnd_item1 = 396;
  public Integer rnd_item2 = 264;
  public Integer rndItemRange = 5;
  public Integer rndSpawnEggs = 344;
  public Integer rndAmount = 20;
  public Integer rndSpawnEggsRange = 5;
  public Integer spawnRate = 3;
  private static DiceEaster Main;
  public Material m = null;
  public List<Short> eggs = new ArrayList<Short>();
  public List<Bunny> bunnyList = new ArrayList<Bunny>();
  public Boolean aggrisive = true;
  public Boolean bunnysDropItems = true;
  public Boolean spawnBunnys = true;
  public Boolean defaultConfig = false;
  public Boolean autoUpdate = false;
  private Boolean TitleManager = false;
  public Boolean ScoreBoard = true;
  private Boolean Barapi = true;
  public sql database = null;
  public sql data = null;
@Override  
public void onDisable() {
	EggManager.saveEggs();
	for(Player player : Bukkit.getOnlinePlayers()){
		EggManager.savePlayer(player);
	}
	if(!bunnyList.isEmpty()){
		for(Bunny b : bunnyList){
			b.destroy();
		}
		bunnyList.clear();
	}
	EggManager.killALL();
};

@SuppressWarnings({ "unchecked", "deprecation" })
@Override
public void onEnable()
  {
	Main = this;
    PluginManager manager = getServer().getPluginManager();
    manager.registerEvents(this, this);
    try
    {
      Metrics metrics = new Metrics(this);
      metrics.start();
    }
    catch (IOException localIOException) {}
    if (!setupEconomy())
    {
      getServer().getPluginManager().disablePlugin(this);
      return;
    }
    if(Bukkit.getPluginManager().isPluginEnabled("TitleManager")){this.TitleManager = true;}
    if(Bukkit.getPluginManager().isPluginEnabled("BarAPI")){this.Barapi = true;}
    loadConfig();
    this.Language = getConfig().getString("DiceEasterEggs.Message.language").toLowerCase();
    this.ScoreBoard = getConfig().getBoolean("DiceEasterEggs.Scoreboard.Enabled");
    this.sendMsg = getConfig().getString("DiceEasterEggs.Message.type");
    colourreplace("EggName", getConfig().getString("DiceEasterEggs.EggName"));
    if(getConfig().isSet("DiceEasterEggs.Worlds")){world = (List<String>) getConfig().getList("DiceEasterEggs.Worlds");}
    eggs = getConfig().getShortList("DiceEasterEggs.Item.SubIDs");
    m = Material.getMaterial(getConfig().getInt("DiceEasterEggs.Item.Material"));
    
    if(!getConfig().isList("DiceEasterEggs.Prize")){
    	Prize = Double.valueOf(getConfig().getDouble("DiceEasterEggs.Prize"));
    }else{
    	List<String> stringlist = getConfig().getStringList("DiceEasterEggs.Prize");
    	commandList.addAll(stringlist);
    }
    if(Language!="de_de" && Language!="en_en"){defaultConfig=true;}
    configManager.loadActionBarMessage(defaultConfig);
    configManager.loadBarAPIMessage(defaultConfig);
    configManager.loadChatMessage(defaultConfig);
    configManager.loadScoreboard(defaultConfig);
    configManager.loadTitleMessage(defaultConfig);
    configManager.loadBunnyConfig();
    configManager.loadTop();
    configManager.loadSQL();
    getCommand("easter").setExecutor(new command());
    this.header = variablen.msg.get("Header");
    pl = variablen.msg.get("Header");
    EggManager.loadEggs();

    if(!Bukkit.getOnlinePlayers().isEmpty()){
    	for(Player player : Bukkit.getOnlinePlayers()){
    		EggManager.loadPlayer(player);
    		updateScoreboard(player);
    	}
    }
    
    Bukkit.getPluginManager().registerEvents(new PlayerChangeWorld(), this);
    Bukkit.getPluginManager().registerEvents(new PlayerDropEvent(), this);
    Bukkit.getPluginManager().registerEvents(new PlayerJoin(), this);
    Bukkit.getPluginManager().registerEvents(new PlayerQuit(), this);
    Bukkit.getPluginManager().registerEvents(new PlayerKick(), this);
    Bukkit.getPluginManager().registerEvents(new EntitySpawn(), this);
    setupPermissions();
  }
  
  public void colourreplace(String string, String string2) {
	  variablen.msg.put(string, ChatColor.translateAlternateColorCodes('&', string2));
  }

@SuppressWarnings("deprecation")
private void loadConfig()
  {
	YamlConfiguration yml = YamlConfiguration.loadConfiguration(this.getResource("config.yml"));
	getConfig().addDefaults(yml);
    FileConfiguration cfg = getConfig();
    cfg.options().copyDefaults(true);
    saveDefaultConfig();
  }


  public Hologram createHolo(final Location loc, ItemStack is){
      Hologram holo = HologramsAPI.createHologram(this, loc);
      ItemLine im = holo.appendItemLine(is);
      im.setPickupHandler(new PickupHandler(){
			@Override
			public void onPickup(Player player) {
				  if(!player.hasPermission("Easter.player")){return;}
				  if(player.getInventory().getItemInHand()!=null){
					  if(player.getInventory().getItemInHand().hasItemMeta() && player.getInventory().getItemInHand().getItemMeta().hasDisplayName() && 
						 player.getInventory().getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase(variablen.msg.get("EggName"))){
						  return;
					  }
				  }
				  if(returnEGG(player) != null){
					  Egg holo = returnEGG(player);
					  if(!variablen.playerEisammler.contains(player)){
						  if(lastEgg.containsKey(player) && lastEgg.get(player).equals(holo)){return;}
						  List<Egg> playerHolos = new ArrayList<Egg>();
						  if(playerEggs.containsKey(player)){
							  playerHolos.addAll(playerEggs.get(player));
						  }
						  
						  if(lastEgg.containsKey(player) && lastEgg.get(player).equals(holo)){return;}
						  if(!playerHolos.contains(holo)){
							  player.playSound(loc, Sound.ITEM_PICKUP, 1, 1);
							  
							  
							  playerHolos.add(holo);
							  playerEggs.put(player, playerHolos);
							  
							  lastEgg.put(player, holo);
							  updateScoreboard(player);
							  if(autoUpdate){new topTen(null, Integer.MAX_VALUE);}
							  if(DiceEaster.getInstance().data != null){DiceEaster.getInstance().data.addPlayer(player);}
							  if(playerHolos.size() >= egglist.size()){
								  if(!commandList.isEmpty()){
									  for(String cmd : commandList){
										  String l = cmd;
										  l = l.replace("@PLAYER", player.getName()).replace("@EGGS", egglist.size() + "");
										  Bukkit.dispatchCommand(Bukkit.getConsoleSender(), l);
									  }
									  return;
								  }else{
									  econ.depositPlayer(player.getName(), Prize); 
									  player.sendMessage(header + variablen.msg.get("Win").replace("@money", Prize + ""));
									  return;
								  }
							  }
							  
								  if(sendMsg.equalsIgnoreCase("Action") && TitleManager){
									  new ActionbarTitleObject(variablen.msg.get("ActionBarFind").replace("@NR", playerHolos.size()+ "").replace("@MAX", egglist.size() + "")).send(player);
								  }else if(sendMsg.equalsIgnoreCase("Title") && TitleManager){
									  new TitleObject(variablen.msg.get("Title").replace("@NR", playerHolos.size() + "").replace("@MAX", egglist.size() + ""), 
									  variablen.msg.get("SubTitle").replace("@NR", playerHolos.size()+ "").replace("@MAX", egglist.size() + "")).send(player);
								  }else if(sendMsg.equalsIgnoreCase("BarAPI") && Barapi){
									  BarAPI.setMessage(player, variablen.msg.get("BarAPIFind").replace("@NR", playerHolos.size() + "").replace("@MAX", egglist.size() + ""));
								  }else{
									  player.sendMessage(header + variablen.msg.get("EggPickUp").replace("@NR", playerHolos.size() + "").replace("@MAX", egglist.size() + "")); 
								  }
						  }else{
								  if(sendMsg.equalsIgnoreCase("Action") && TitleManager){
									  new ActionbarTitleObject(variablen.msg.get("ActionBarAlready").replace("@NR", playerHolos.size()  + "").replace("@MAX", egglist.size() + "")).send(player);
								  }else if(sendMsg.equalsIgnoreCase("Title") && TitleManager){
									  new TitleObject(variablen.msg.get("already_Title").replace("@NR", playerHolos.size()  + "").replace("@MAX", egglist.size() + ""), 
									  variablen.msg.get("already_SubTitle").replace("@NR", playerHolos.size() + "").replace("@MAX", egglist.size() + "")).send(player);
								  }else if(sendMsg.equalsIgnoreCase("BarAPI") && Barapi){
									  BarAPI.setMessage(player, variablen.msg.get("BarAPIAlready").replace("@NR", playerHolos.size() + "").replace("@MAX", egglist.size() + ""));
								  }else{
									  player.sendMessage(header + variablen.msg.get("Already").replace("@NR", playerHolos.size() +"").replace("@MAX", egglist.size() + "")); 
								  }
							  lastEgg.put(player, holo);
							  return;
						  }
					  }else{
						  Egg egg = holo;
						  String id = egg.getID();
						  
						  for(Player players : playerEggs.keySet()){
							  if(playerEggs.get(players).contains(egg)){
								  playerEggs.get(players).remove(egg);
							  }
						  }
						  
						  if(egg.isDebug()){egg.debug();}
						  egg.getHologram().delete();
						  egglist.remove(egg);
						  player.sendMessage(header + variablen.msg.get("EggRemove").replace("@EGG", id));
						  new File("plugins/DiceEaster/Eggs/" + player.getWorld().getName() + "/" + id + ".yml").delete();
						  player.playSound(player.getLocation(), Sound.FIZZ, 1, 1);
						  
						  for(Player players : Bukkit.getOnlinePlayers()){
							  updateScoreboard(players);
						  }
						  if(autoUpdate){new topTen(null, Integer.MAX_VALUE);}
					  }
				  }
			}
		});
	return holo;
  }

  public Boolean isDouble(String s){
	  try{
		  Double.parseDouble(s);
		  return true;
	  }catch(NumberFormatException e){
		  return false;
	  }
  }
  
  public Boolean isInt(String s){
	  try{
		  Integer.parseInt(s);
		  return true;
	  }catch(NumberFormatException e){
		  return false;
	  }
  }
  
  private Egg returnEGG(Player player){
	  if(!egglist.isEmpty()){
		  for(Egg egg : egglist){
			  if(egg.getLocation().equals(player.getLocation())){
				  return egg;
			  }else if(player.getLocation().toVector().distance(egg.getLocation().toVector()) <= 2){
				  return egg;
			  }
		  }
	  }
	return null;
  } 
  private boolean setupEconomy()
  {
    if (getServer().getPluginManager().getPlugin("Vault") == null) {
      return false;
    }
    RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
    if (rsp == null) {
      return false;
    }
    this.econ = ((Economy)rsp.getProvider());
    return this.econ != null;
  }
  private boolean setupPermissions()
  {
    RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
    perms = (Permission)rsp.getProvider();
    return perms != null;
  }
  public static int rnd(Integer i, Integer o)
  {
    Random r = new Random();
    int Low = i;
    int High = o;
    int R = r.nextInt(High - Low) + Low;
    return R;
  }
  public static DiceEaster getInstance() {return Main;}
  public void debug(){
	  if(!egglist.isEmpty()){
		  for(Egg egg : egglist){
			egg.debug();  
		  }
	  }
  }
  public void updateScoreboard(Player player){
	  if(ScoreBoard){
		  if(!world.contains(player.getWorld().getName())){
			  if(scoreboards.containsKey(player)){
				  scoreboards.get(player).destroy();
				  scoreboards.remove(player);
				  return;
			  }
		  }else{
			  if(scoreboards.containsKey(player)){
				  scoreboards.get(player).update();
			  }else{
				  ScoreB score = new ScoreB(player);
				  scoreboards.put(player, score);
			  }
		  }
	  }
  }
  public int getPlayerScore(Player player) {
	if(!playerEggs.isEmpty()){
		if(playerEggs.containsKey(player)){
			return playerEggs.get(player).size();
		}
	}
	return 0;
}
 
public int getEggs() {
	if(!egglist.isEmpty()){
		return egglist.size();
	}
	return 0;
}

  public String getHeader() {return this.header;}
}
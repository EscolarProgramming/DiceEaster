package me.Ste3et_C0st.DiceEaster;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.milkbowl.vault.Metrics;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ItemDespawnEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class main
  extends JavaPlugin
  implements Listener
{
  public static config customConfig;
  public static FileConfiguration matchConfig;
  public static String pl = "§7[§2Easter-Egg§7] ";
  public Economy econ = null;
  public static Permission perms = null;
  public static Double Prize = Double.valueOf(0.0D);
  
  public void onEnable()
  {
    PluginManager manager = getServer().getPluginManager();
    manager.registerEvents(this, this);
    new variablen(this);
    loadConfig();
    variablen.eier = Integer.parseInt(getConfig().getString("DiceEasterEggs.EasterEggs"));
    for (Player p : Bukkit.getOnlinePlayers())
    {
      World w = Bukkit.getWorld(getConfig().getString("DiceEasterEggs.World"));
      if ((w != null) && 
        (p.getWorld() == w))
      {
        customConfig = new config(this);
        matchConfig = customConfig.getConfig(p.getName());
        int Zahl = 0;
        Zahl = matchConfig.getInt("DiceEasterEggs.Player." + p.getName() + ".eier");
        variablen.EasterEggs.put(p, Integer.valueOf(Zahl));
        check2(p, Zahl);
      }
    }
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
    setupPermissions();
    
    colourreplace("NoPermissions", getConfig().getString("DiceEasterEggs.Message.NoPermissions"));
    colourreplace("RemoveEgg", getConfig().getString("DiceEasterEggs.Message.RemoveEgg"));
    colourreplace("GiveEgg", getConfig().getString("DiceEasterEggs.Message.GiveEgg"));
    colourreplace("PickupEgg", getConfig().getString("DiceEasterEggs.Message.PickupEgg"));
    colourreplace("EggDrop", getConfig().getString("DiceEasterEggs.Message.EggDrop"));
    colourreplace("EggPickUp1", getConfig().getString("DiceEasterEggs.Message.EggPickUp1"));
    colourreplace("EggPickUp2", getConfig().getString("DiceEasterEggs.Message.EggPickUp2"));
    colourreplace("EggPickUp3", getConfig().getString("DiceEasterEggs.Message.EggPickUp3"));
    colourreplace("EggPickUp4", getConfig().getString("DiceEasterEggs.Message.EggPickUp4"));
    colourreplace("NoMoreRemoveEggs", getConfig().getString("DiceEasterEggs.Message.NoMoreRemoveEggs"));
    colourreplace("NoRemoveEggs", getConfig().getString("DiceEasterEggs.Message.NoRemoveEgs"));
    colourreplace("ExactRemoveEggs", getConfig().getString("DiceEasterEggs.Message.ExactRemoveEggs"));
    colourreplace("NoRightArgumentRemove", getConfig().getString("DiceEasterEggs.Message.NoRightArgumentRemove"));
    colourreplace("NoRightArgumentExact", getConfig().getString("DiceEasterEggs.Message.NoRightArgumentExact"));
    colourreplace("NoRightArgumentInt", getConfig().getString("DiceEasterEggs.Message.NoRightArgumentInt"));
    colourreplace("Already", getConfig().getString("DiceEasterEggs.Message.Already"));
    colourreplace("YesExactRemoveEggs", getConfig().getString("DiceEasterEggs.Message.YesExactRemoveEggs"));
    colourreplace("Info", getConfig().getString("DiceEasterEggs.Message.Info"));
    colourreplace("WrongWorld", getConfig().getString("DiceEasterEggs.Message.WrongWorld"));
    colourreplace("WrongWorld2", getConfig().getString("DiceEasterEggs.Message.WrongWorld2"));
    colourreplace("World", getConfig().getString("DiceEasterEggs.Message.World"));
    colourreplace("Win", getConfig().getString("DiceEasterEggs.Message.Win"));
    colourreplace("WinWrong", getConfig().getString("DiceEasterEggs.Message.WinWrong"));
    colourreplace("setWin", getConfig().getString("DiceEasterEggs.Message.setWin"));
    colourreplace("infoWrong", getConfig().getString("DiceEasterEggs.Message.infoWrong"));
    colourreplace("infoMode", getConfig().getString("DiceEasterEggs.Message.infoMode"));
    Prize = Double.valueOf(getConfig().getDouble("DiceEasterEggs.Prize"));
  }
  
  public void onDisable()
  {
    for (Player p : variablen.EasterEggs.keySet())
    {
      customConfig = new config(this);
      matchConfig = customConfig.getConfig(p.getName());
      matchConfig.set("DiceEasterEggs.Player." + p.getName() + ".eier", variablen.EasterEggs.get(p));
      customConfig.saveConfig(p.getName(), matchConfig);
    }
  }
  
  private void loadConfig()
  {
    getConfig().addDefault("DiceEasterEggs.EasterEggs", Integer.valueOf(0));
    getConfig().addDefault("DiceEasterEggs.World", "world");
    getConfig().addDefault("DiceEasterEggs.Message.NoPermissions", "&cYou don't have Permissions");
    getConfig().addDefault("DiceEasterEggs.Message.RemoveEgg", "You can remove @NR Egg/s");
    getConfig().addDefault("DiceEasterEggs.Message.GiveEgg", "You become a Stack Easter Eggs");
    getConfig().addDefault("DiceEasterEggs.Message.PickupEgg", "You pickup one egg NR.:&c @NR");
    getConfig().addDefault("DiceEasterEggs.Message.EggDrop", "Easter Egg NR.:&c @NR &7placed");
    getConfig().addDefault("DiceEasterEggs.Message.EggPickUp1", "You have &c@NR&7/&c@MAX &7Easter eggs");
    getConfig().addDefault("DiceEasterEggs.Message.EggPickUp2", "You have &c@NR Easter eggs");
    getConfig().addDefault("DiceEasterEggs.Message.EggPickUp3", "You remove the &c@NR Easter eggs");
    getConfig().addDefault("DiceEasterEggs.Message.EggPickUp4", "You can &c@NR Easter eggs");
    getConfig().addDefault("DiceEasterEggs.Message.NoMoreRemoveEggs", "You leave the remove mode");
    getConfig().addDefault("DiceEasterEggs.Message.NoRemoveEgs", "You are not in the remove mode");
    getConfig().addDefault("DiceEasterEggs.Message.Info", "Egg number &2@EGG");
    getConfig().addDefault("DiceEasterEggs.Message.ExactRemoveEggs", "You can remove the egg number &2@EGG");
    getConfig().addDefault("DiceEasterEggs.Message.YesExactRemoveEggs", "You remove the egg number &2@EGG");
    getConfig().addDefault("DiceEasterEggs.Message.NoRightArgumentRemove", "Please use &2/easter remove (exact/leave/int)");
    getConfig().addDefault("DiceEasterEggs.Message.NoRightArgumentExact", "Please use &2/easter remove exact eggNR");
    getConfig().addDefault("DiceEasterEggs.Message.NoRightArgumentInt", "Please use &2/easter remove int");
    getConfig().addDefault("DiceEasterEggs.Message.Already", "You have already the egg");
    getConfig().addDefault("DiceEasterEggs.Message.WrongWorld", "You are not in the Easter Egg World");
    getConfig().addDefault("DiceEasterEggs.Message.World", "You set the Easter Egg World");
    getConfig().addDefault("DiceEasterEggs.Message.WrongWorld2", "Please use &2/easter world");
    getConfig().addDefault("DiceEasterEggs.Message.Win", "You win &c@money &7Gold");
    getConfig().addDefault("DiceEasterEggs.Message.setWin", "You set the win &c@money &7Gold");
    getConfig().addDefault("DiceEasterEggs.Message.WinWrong", "Please use &2/easter setwin <double>");
    getConfig().addDefault("DiceEasterEggs.Message.infoWrong", "Please use &2/easter info");
    getConfig().addDefault("DiceEasterEggs.Message.infoMode", "You are in the info Mode");
    getConfig().addDefault("DiceEasterEggs.Prize", 10000.0);
    FileConfiguration cfg = getConfig();
    cfg.options().copyDefaults(true);
    saveConfig();
  }
  
  public static void colourreplace(String variable, String pfad)
  {
    pfad = ChatColor.translateAlternateColorCodes('&', pfad);
    variablen.msg.put(variable, pfad);
  }
  

	public static String trimPlayerName(String s, int length){
		List<String> strings = new ArrayList<String>();
        int index = 0;
        
        while (index<s.length()) {
            strings.add(s.substring(index, Math.min(index+length, s.length())));
            index+=length;
        }
        return strings.get(0);
	}
  
  @EventHandler
  public void onItemDrop(PlayerDropItemEvent e)
  {
    Player p = e.getPlayer();
    if ((e.getItemDrop().getItemStack().hasItemMeta()) && 
      (e.getItemDrop().getItemStack().getItemMeta().hasDisplayName()) && 
      (e.getItemDrop().getItemStack().getItemMeta().getDisplayName().equals("§2Easter-Egg")) && (e.getItemDrop().getItemStack().getType() == Material.MONSTER_EGG)) {
      if (p.hasPermission("Diceei.drop"))
      {
        ItemStack IS = e.getItemDrop().getItemStack();
        ItemMeta im = IS.getItemMeta();
        im.setDisplayName("Easter-Egg=" + variablen.eier);
        IS.setItemMeta(im);
        IS.setDurability((short)rnd());
        String s = variablen.replace(variablen.msg.get("EggDrop"), "@NR", variablen.eier + "");
        p.sendMessage(pl + s);
        e.getItemDrop().setItemStack(IS);
        e.getItemDrop().setTicksLived(1);
        variablen.eier += 1;
        getConfig().set("DiceEasterEggs.EasterEggs", Integer.valueOf(variablen.eier));
        saveConfig();
      }
      else
      {
        e.setCancelled(true);
        p.sendMessage((String)variablen.msg.get("NoPermissions"));
      }
    }
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
  
  public static int rnd()
  {
    Random r = new Random();
    int Low = 50;
    int High = 67;
    int R = r.nextInt(High - Low) + Low;
    return R;
  }
  
  @EventHandler
  public void onJoin(PlayerJoinEvent e)
  {
    Player p = e.getPlayer();
    if (variablen.EasterEggs.get(p) != null)
    {
      int Zahl = ((Integer)variablen.EasterEggs.get(p)).intValue();
      p.sendMessage(Zahl + "");
      variablen.EasterEggs.put(p, Integer.valueOf(Zahl));
      check2(p, Zahl);
    }
    else
    {
      customConfig = new config(this);
      matchConfig = customConfig.getConfig(p.getName());
      int Zahl = matchConfig.getInt("DiceEasterEggs.Player." + p.getName() + ".eier");
      variablen.EasterEggs.put(p, Integer.valueOf(Zahl));
      check2(p, Zahl);
    }
  }
  
  @EventHandler
  public void onQuit(PlayerQuitEvent e)
  {
    Player p = e.getPlayer();
    if (variablen.EasterEggs.get(p) != null)
    {
      int zahl = ((Integer)variablen.EasterEggs.get(p)).intValue();
      customConfig = new config(this);
      matchConfig = customConfig.getConfig(p.getName());
      matchConfig.set("DiceEasterEggs.Player." + p.getName() + ".eier", Integer.valueOf(zahl));
      customConfig.saveConfig(p.getName(), matchConfig);
      variablen.EasterEggs.remove(p);
    }
  }
  
  @EventHandler
  public void onPlayerWorldJoinEvent(PlayerChangedWorldEvent e)
  {
    Player p = e.getPlayer();
    World w = Bukkit.getWorld(getConfig().getString("DiceEasterEggs.World"));
    if (w != null) {
      if (p.getWorld() == w)
      {
        if (variablen.EasterEggs.get(p) != null)
        {
          int Zahl = ((Integer)variablen.EasterEggs.get(p)).intValue();
          variablen.EasterEggs.put(p, Integer.valueOf(Zahl));
          check2(p, Zahl);
        }
        else
        {
          customConfig = new config(this);
          matchConfig = customConfig.getConfig(p.getName());
          int Zahl = matchConfig.getInt("DiceEasterEggs.Player." + p.getName() + ".eier");
          variablen.EasterEggs.put(p, Integer.valueOf(Zahl));
          check2(p, Zahl);
        }
      }
      else {
        scoreboard.leave(p);
      }
    }
  }
  
  @EventHandler
  public void onDespawn(ItemDespawnEvent e)
  {
    if ((e.getEntity().getItemStack().hasItemMeta()) && 
      (e.getEntity().getItemStack().getItemMeta().hasDisplayName()))
    {
      String name = e.getEntity().getItemStack().getItemMeta().getDisplayName();
      if (name.startsWith("Easter-Egg"))
      {
        e.setCancelled(true);
        e.getEntity().setTicksLived(99999999);
      }
    }
  }
  
  public void playEffect(Location loc, Effect e)
  {
    World world = loc.getWorld();
    world.playEffect(loc, e, 5);
  }
  
  @EventHandler
  public void onItemPickup(PlayerPickupItemEvent e)
  {
    Player p = e.getPlayer();
    if ((e.getItem().getItemStack().hasItemMeta()) && 
      (e.getItem().getItemStack().getItemMeta().hasDisplayName()))
    {
      String name = e.getItem().getItemStack().getItemMeta().getDisplayName();
      String Anzahl = name.replace("Easter-Egg=", "");
      if (name.startsWith("Easter-Egg"))
      {
        e.setCancelled(true);
        if (p.hasPermission("Diceei"))
        {
          int Zahl = Integer.parseInt(Anzahl);
          if (variablen.playerEisammler.get(p) != null)
          {
            if (((Integer)variablen.playerEisammler.get(p)).intValue() > 0)
            {
              e.setCancelled(false);
              ItemStack IS = e.getItem().getItemStack();
              ItemMeta IM = IS.getItemMeta();
              IM.setDisplayName("§2Easter-Egg");
              IS.setItemMeta(IM);
              IS.setDurability((short)0);
              int i = ((Integer)variablen.playerEisammler.get(p)).intValue();
              i--;
              if (i == 0)
              {
                variablen.playerEisammler.remove(p);
                String s = variablen.replace((String)variablen.msg.get("RemoveEgg"), "@NR", "0");
                p.sendMessage(pl + s);
              }
              else
              {
                variablen.playerEisammler.put(p, Integer.valueOf(i));
                String s = variablen.replace(variablen.msg.get("RemoveEgg"), "@NR", variablen.playerEisammler.get(p) + "");
                p.sendMessage(pl + s);
              }
              variablen.eier -= 1;
            }
          }
          else if (variablen.playerEisammler2.get(p) != null)
          {
            if (((Integer)variablen.playerEisammler2.get(p)).intValue() == Zahl)
            {
              e.setCancelled(false);
              ItemStack IS = e.getItem().getItemStack();
              ItemMeta IM = IS.getItemMeta();
              IM.setDisplayName("§2Easter-Egg");
              IS.setItemMeta(IM);
              IS.setDurability((short)0);
              variablen.playerEisammler2.remove(p);
              variablen.eier -= 1;
              String s = variablen.replace(variablen.msg.get("YesExactRemoveEggs"), "@EGG", Zahl + "");
              p.sendMessage(pl + s);
            }
          }
          else if (variablen.info.get(p) != null)
          {
            variablen.info.remove(p);
            String s = variablen.replace(variablen.msg.get("Info"), "@EGG", Zahl + "");
            p.sendMessage(pl + s);
          }
          else if (!checkPlayer(p, Zahl))
          {
            if (variablen.EasterEggs.get(p) == null)
            {
              int eier = 1;
              int eieranzahl = variablen.eier;
              String s1 = variablen.replace(variablen.msg.get("EggPickUp2"), "@NR", eier + "");
              String s2 = variablen.replace(variablen.msg.get("EggPickUp1"), "@NR", eier + "");
              String s3 = variablen.replace(s2, "@MAX", eieranzahl + "");
              p.sendMessage(pl + s1);
              p.sendMessage(pl + s3);
              variablen.EasterEggs.put(p, Integer.valueOf(1));
              savePlayer(p, Zahl);
              check(p, ((Integer)variablen.EasterEggs.get(p)).intValue());
              playEffect(p.getLocation(), Effect.SMOKE);
              p.playSound(p.getLocation(), Sound.LEVEL_UP, 10.0F, 1.0F);
              variablen.playerSpeere.put(p, Integer.valueOf(Zahl));
            }
            else
            {
              int eier = ((Integer)variablen.EasterEggs.get(p)).intValue() + 1;
              int eieranzahl = variablen.eier;
              String s1 = variablen.replace(variablen.msg.get("EggPickUp2"), "@NR", eier + "");
              String s2 = variablen.replace(variablen.msg.get("EggPickUp1"), "@NR", eier + "");
              String s3 = variablen.replace(s2, "@MAX", eieranzahl + "");
              p.sendMessage(pl + s1);
              p.sendMessage(pl + s3);
              savePlayer(p, Zahl);
              check(p, ((Integer)variablen.EasterEggs.get(p)).intValue());
              int SpielerEier = ((Integer)variablen.EasterEggs.get(p)).intValue() + 1;
              variablen.EasterEggs.put(p, Integer.valueOf(SpielerEier));
              playEffect(p.getLocation(), Effect.SMOKE);
              p.playSound(p.getLocation(), Sound.LEVEL_UP, 10.0F, 1.0F);
              gewinn(p, eier, eieranzahl);
              variablen.playerSpeere.put(p, Integer.valueOf(Zahl));
            }
          }
          else
          {
            e.setCancelled(true);
            if (variablen.playerSpeere.get(p) == null)
            {
              variablen.playerSpeere.put(p, Integer.valueOf(Zahl));
              p.sendMessage(pl + (String)variablen.msg.get("Already"));
            }
            else if (((Integer)variablen.playerSpeere.get(p)).intValue() != Zahl)
            {
              variablen.playerSpeere.put(p, Integer.valueOf(Zahl));
              p.sendMessage(pl + (String)variablen.msg.get("Already"));
            }
          }
        }
      }
    }
  }
  
  private void gewinn(Player p, int eier, int eieranzahl)
  {
    int prozent = eieranzahl - eier;
    if (prozent == 0)
    {
      this.econ.depositPlayer(p.getName(), Prize.doubleValue());
      p.sendMessage(pl + variablen.replace((String)variablen.msg.get("Win"), "@money", new StringBuilder().append(Prize).toString()));
    }
  }
  
  public void check(Player p, int Zahl)
  {
    customConfig = new config(this);
    matchConfig = customConfig.getConfig(p.getName());
    if (variablen.playerScoreboard.get(p) == null)
    {
      if (matchConfig.getInt("DiceEasterEggs.Player." + p.getName() + ".eier") == 0)
      {
        scoreboard.create(p, 1);
        scoreboard.set(p);
      }
      else
      {
        int Eier = matchConfig.getInt("DiceEasterEggs.Player." + p.getName() + ".eier");
        scoreboard.create(p, Eier);
        scoreboard.set(p);
      }
    }
    else {
      scoreboard.update(p, ((Integer)variablen.EasterEggs.get(p)).intValue());
    }
  }
  
  public void check2(Player p, int Zahl)
  {
    customConfig = new config(this);
    matchConfig = customConfig.getConfig(p.getName());
    scoreboard.create(p, Zahl);
    scoreboard.set(p);
  }
  
  public void savePlayer(Player p, int Zahl)
  {
    customConfig = new config(this);
    matchConfig = customConfig.getConfig(p.getName());
    matchConfig.set("DiceEasterEggs.Player." + p.getName() + ".eggs." + Zahl, Boolean.valueOf(true));
    customConfig.saveConfig(p.getName(), matchConfig);
    saveConfig();
  }
  
  public boolean checkPlayer(Player p, int Zahl)
  {
    customConfig = new config(this);
    matchConfig = customConfig.getConfig(p.getName());
    if (!matchConfig.getBoolean("DiceEasterEggs.Player." + p.getName() + ".eggs." + Zahl)) {
      return false;
    }
    return true;
  }
  
  public static boolean checkplayer(Player p)
  {
    if (variablen.playerEisammler.get(p) != null) {
      return true;
    }
    return false;
  }
  
  public static boolean isInt(String string)
  {
    try
    {
      Integer.parseInt(string);
    }
    catch (NumberFormatException nFE)
    {
      return false;
    }
    return true;
  }
  
  public static boolean isDouble(String string)
  {
    try
    {
      Double.parseDouble(string);
    }
    catch (NumberFormatException nFE)
    {
      return false;
    }
    return true;
  }
  
  public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
  {
    if (cmd.getName().equalsIgnoreCase("easter"))
    {
      if ((sender instanceof Player))
      {
        Player p = (Player)sender;
        if (args.length == 0)
        {
          if (p.hasPermission("Diceei"))
          {
            p.sendMessage("§2==================================");
            p.sendMessage("§2/easter give | §7You becom the Easter Eggs");
            p.sendMessage("§2/easter remove | §7You can Pickup one Easter Egg");
            p.sendMessage("§2/easter remove leave | §7Leave Easter Egg remove Mode");
            p.sendMessage("§2/easter remove <int> | §7You can remove more Easter Eggs");
            p.sendMessage("§2/easter remove exact <int> | §7You can remove only the Easter Egg with the numeber");
            p.sendMessage("§2/easter info | §7You become an Info about the Egg");
            p.sendMessage("§2/easter world | §7Set Easter Egg World");
            p.sendMessage("§2/easter setwin <double> | §7Set the win");
            p.sendMessage("§2Easter Eggs: §7" + variablen.eier);
            p.sendMessage("§2==================================");
          }
          else
          {
            p.sendMessage((String)variablen.msg.get("NoPermissions"));
          }
        }
        else
        {
          if (args[0].equalsIgnoreCase("give")) {
            if (p.hasPermission("Diceei.give"))
            {
              if (args.length == 1)
              {
                World w = Bukkit.getWorld(getConfig().getString("DiceEasterEggs.World"));
                if (p.getWorld() == w)
                {
                  @SuppressWarnings("deprecation")
                  ItemStack is = new ItemStack(Material.getMaterial(383));
                  ItemMeta im = is.getItemMeta();
                  im.setDisplayName("§2Easter-Egg");
                  is.setItemMeta(im);
                  is.setAmount(64);
                  p.getInventory().addItem(new ItemStack[] { is });
                  
                  p.sendMessage((String)variablen.msg.get("GiveEgg"));
                }
                else
                {
                  p.sendMessage((String)variablen.msg.get("WrongWorld"));
                }
              }
            }
            else {
              p.sendMessage((String)variablen.msg.get("NoPermissions"));
            }
          }
          if (args[0].equalsIgnoreCase("world")) {
            if (p.hasPermission("Diceei.world"))
            {
              if (args.length == 1)
              {
                getConfig();
                getConfig().set("DiceEasterEggs.World", p.getWorld().getName());
                saveConfig();
                p.sendMessage(pl + (String)variablen.msg.get("World"));
              }
              else
              {
                p.sendMessage(pl + (String)variablen.msg.get("WrongWorld2"));
              }
            }
            else {
              p.sendMessage(pl + (String)variablen.msg.get("NoPermissions"));
            }
          }
          if (args[0].equalsIgnoreCase("setwin")) {
            if (p.hasPermission("Diceei.setwin"))
            {
              if (args.length == 2)
              {
                if (isDouble(args[1]))
                {
                  getConfig();
                  getConfig().set("DiceEasterEggs.Prize", Double.valueOf(Double.parseDouble(args[1])));
                  saveConfig();
                  p.sendMessage(pl + variablen.replace((String)variablen.msg.get("setWin"), "@money", args[1]));
                }
                else
                {
                  p.sendMessage(pl + (String)variablen.msg.get("WinWrong"));
                }
              }
              else {
                p.sendMessage(pl + (String)variablen.msg.get("WinWrong"));
              }
            }
            else {
              p.sendMessage(pl + (String)variablen.msg.get("NoPermissions"));
            }
          }
          if (args[0].equalsIgnoreCase("info")) {
            if (p.hasPermission("Diceei.info"))
            {
              if (args.length == 1)
              {
                variablen.info.put(p, Integer.valueOf(1));
                p.sendMessage(pl + (String)variablen.msg.get("infoMode"));
              }
              else
              {
                p.sendMessage(pl + (String)variablen.msg.get("infoWrong"));
              }
            }
            else {
              p.sendMessage(pl + (String)variablen.msg.get("NoPermissions"));
            }
          }
          if (args[0].equalsIgnoreCase("remove")) {
            if (p.hasPermission("Diceei.remove"))
            {
              if (args.length == 1)
              {
                if (variablen.playerEisammler2.get(p) != null) {
                  variablen.playerEisammler2.remove(p);
                }
                variablen.playerEisammler.put(p, Integer.valueOf(1));
                String s = variablen.replace((String)variablen.msg.get("RemoveEgg"), "@NR", "1");
                p.sendMessage(pl + s);
              }
              else if (args.length == 2)
              {
                if (isInt(args[1]))
                {
                  if (variablen.playerEisammler2.get(p) != null) {
                    variablen.playerEisammler2.remove(p);
                  }
                  variablen.playerEisammler.put(p, Integer.valueOf(Integer.parseInt(args[1])));
                  String s = variablen.replace((String)variablen.msg.get("RemoveEgg"), "@NR", args[1]);
                  p.sendMessage(pl + s);
                }
                else if (args[1].equalsIgnoreCase("leave"))
                {
                  if ((variablen.playerEisammler.get(p) != null) || (variablen.playerEisammler2.get(p) != null))
                  {
                    if (variablen.playerEisammler.get(p) != null) {
                      variablen.playerEisammler.remove(p);
                    }
                    if (variablen.playerEisammler2.get(p) != null) {
                      variablen.playerEisammler2.remove(p);
                    }
                    p.sendMessage(pl + (String)variablen.msg.get("NoMoreRemoveEggs"));
                  }
                  else
                  {
                    p.sendMessage(pl + (String)variablen.msg.get("NoRemoveEggs"));
                  }
                }
                else
                {
                  p.sendMessage(pl + (String)variablen.msg.get("NoRightArgumentRemove"));
                }
              }
              else if (args.length == 3)
              {
                if ((args[1].equalsIgnoreCase("exact")) && 
                  (p.hasPermission("Diceei.remove"))) {
                  if (isInt(args[2]))
                  {
                    if (variablen.playerEisammler.get(p) != null) {
                      variablen.playerEisammler.remove(p);
                    }
                    variablen.playerEisammler2.put(p, Integer.valueOf(Integer.parseInt(args[2])));
                    String s = variablen.replace((String)variablen.msg.get("ExactRemoveEggs"), "@EGG", args[2]);
                    p.sendMessage(pl + s);
                  }
                  else
                  {
                    p.sendMessage(pl + (String)variablen.msg.get("NoRightArgumentRemove"));
                  }
                }
              }
              else
              {
                p.sendMessage(pl + (String)variablen.msg.get("NoRightArgumentRemove"));
              }
            }
            else {
              p.sendMessage((String)variablen.msg.get("NoPermissions"));
            }
          }
        }
      }
      return true;
    }
    return false;
  }
}

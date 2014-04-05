package me.Ste3et_C0st.DiceEaster;

import java.io.File;
import java.io.IOException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class config
{
  public JavaPlugin plugin;
  public String fileName;
  
  public config(JavaPlugin plugin)
  {
    this.plugin = plugin;
  }
  
  public FileConfiguration createConfig(String name)
  {
    if (!name.endsWith(".yml")) {
      name = name + ".yml";
    }
    File arena = new File(this.plugin.getDataFolder() + File.separator + "player", name);
    if (!arena.exists())
    {
      this.plugin.getDataFolder().mkdir();
      try
      {
        arena.createNewFile();
      }
      catch (IOException e)
      {
        e.printStackTrace();
      }
    }
    return YamlConfiguration.loadConfiguration(arena);
  }
  
  public void saveConfig(String name, FileConfiguration config)
  {
    if (!name.endsWith(".yml")) {
      name = name + ".yml";
    }
    File arena = new File(this.plugin.getDataFolder() + File.separator + "player", name);
    try
    {
      config.save(arena);
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }
  
  public FileConfiguration getConfig(String name)
  {
    if (!name.endsWith(".yml")) {
      name = name + ".yml";
    }
    createConfig(name);
    File arena = new File(this.plugin.getDataFolder() + File.separator + "player", name);
    return YamlConfiguration.loadConfiguration(arena);
  }
}

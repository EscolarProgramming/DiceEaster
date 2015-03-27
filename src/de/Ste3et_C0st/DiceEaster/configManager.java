package de.Ste3et_C0st.DiceEaster;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

@SuppressWarnings("deprecation")
public class configManager {
	private static config cc;
	private static FileConfiguration fc;
	
	public static void loadScoreboard(Boolean b){
		defautlMessage(b, "scoreboard.yml");
		cc = new config();
		fc = cc.getConfig("scoreboard.yml", "/Messages/" + main.getInstance().Language);
		main.getInstance().colourreplace("ScoreboardHeader", fc.getString("Message.Header"));
		main.getInstance().colourreplace("ScoreboardHidden", fc.getString("Message.Hidden"));
		main.getInstance().colourreplace("ScoreboardFind", fc.getString("Message.Find"));
	}
	public static void loadChatMessage(Boolean b){
		defautlMessage(b, "chat.yml");
		cc = new config();
		fc = cc.getConfig("chat.yml", "/Messages/" + main.getInstance().Language);
		main.getInstance().colourreplace("Header", fc.getString("Message.Header"));
		main.getInstance().colourreplace("NoPermissions", fc.getString("Message.NoPermissions"));
		main.getInstance().colourreplace("RemoveEgg", fc.getString("Message.RemoveEgg"));
		main.getInstance().colourreplace("GiveEgg", fc.getString("Message.GiveEgg"));
		main.getInstance().colourreplace("EggDrop", fc.getString("Message.EggDrop"));
		main.getInstance().colourreplace("EggPickUp", fc.getString("Message.EggPickUp"));
		main.getInstance().colourreplace("EggRemove", fc.getString("Message.EggRemove"));
		main.getInstance().colourreplace("Already", fc.getString("Message.Already"));
		main.getInstance().colourreplace("WrongWorld", fc.getString("Message.WrongWorld"));
		main.getInstance().colourreplace("World", fc.getString("Message.World"));
		main.getInstance().colourreplace("Win", fc.getString("Message.Win"));
		main.getInstance().colourreplace("setWin", fc.getString("Message.setWin"));
		main.getInstance().colourreplace("WinWrong", fc.getString("Message.WinWrong"));
		main.getInstance().colourreplace("TopCommand", fc.getString("Message.TopCommand"));
		main.getInstance().colourreplace("topOutput1", fc.getString("Message.TopOutputStardet"));
		main.getInstance().colourreplace("topOutput2", fc.getString("Message.TopOutputFinish"));
		main.getInstance().colourreplace("NoMoreRemoveEggs", fc.getString("Message.NoMoreRemoveEggs"));
	}
	public static void loadActionBarMessage(Boolean b){
		defautlMessage(b, "actionbar.yml");
		cc = new config();
		fc = cc.getConfig("actionbar.yml", "/Messages/" + main.getInstance().Language);
		main.getInstance().colourreplace("ActionBarFind", fc.getString("Message.find"));
		main.getInstance().colourreplace("ActionBarAlready", fc.getString("Message.already"));
	}
	public static void loadTitleMessage(Boolean b){
		defautlMessage(b, "title.yml");
		cc = new config();
		fc = cc.getConfig("title.yml", "/Messages/" + main.getInstance().Language);
		main.getInstance().colourreplace("Title", fc.getString("Message.title"));
		main.getInstance().colourreplace("SubTitle", fc.getString("Message.subtitle"));
		main.getInstance().colourreplace("already_Title", fc.getString("Message.already_title"));
		main.getInstance().colourreplace("already_SubTitle", fc.getString("Message.already_subtitle"));
	}
	public static void loadBarAPIMessage(Boolean b){
		defautlMessage(b, "barapi.yml");
		cc = new config();
		fc = cc.getConfig("barapi.yml", "/Messages/" + main.getInstance().Language);
		main.getInstance().colourreplace("BarAPIFind", fc.getString("Message.find"));
		main.getInstance().colourreplace("BarAPIAlready", fc.getString("Message.already"));
	}
	public static void loadBunnyConfig(){
		defaultBunnyConfig();
		cc = new config();
		fc = cc.getConfig("bunny.yml", ""); 
		main.getInstance().link1 = fc.getString("Bunny.Skeleton.head_textur_url1");
		main.getInstance().link2 = fc.getString("Bunny.Skeleton.head_textur_url2");
		main.getInstance().item1 = fc.getInt("Bunny.items.leftHandNormal");
		main.getInstance().item2 = fc.getInt("Bunny.items.rightHandNormal");
		main.getInstance().rnd_item1 = fc.getInt("Bunny.items.leftHandRandom");
		main.getInstance().rnd_item2 = fc.getInt("Bunny.items.rightHandRandom");
		main.getInstance().rndItemRange = fc.getInt("Bunny.items.randomRange");
		main.getInstance().rndSpawnEggs = fc.getInt("Bunny.onSpawn.dropItem");
		main.getInstance().rndAmount = fc.getInt("Bunny.onSpawn.dropAmount");
		main.getInstance().rndSpawnEggsRange = fc.getInt("Bunny.onSpawn.dropRange");
		main.getInstance().spawnBunnys = fc.getBoolean("Bunny.spawn.spawning");
		main.getInstance().spawnRate = fc.getInt("Bunny.spawn.spawnRate");
		main.getInstance().aggrisive = fc.getBoolean("Bunny.spawn.spawnAgrissive");
		main.getInstance().bunnysDropItems = fc.getBoolean("Bunny.spawn.bunnysDropItems");
	}
	public static void loadTop(){
		defaultTopConfig();
		cc = new config();
		fc = cc.getConfig("topConfig.yml", ""); 
		main.getInstance().colourreplace("TopHeader", fc.getString("TopList.Header"));
		main.getInstance().colourreplace("TopFooter", fc.getString("TopList.Footer"));
		main.getInstance().colourreplace("TopMessage", fc.getString("TopList.Message"));
		main.getInstance().autoUpdate = fc.getBoolean("TopList.AutoUpdate");
	}
	public static void loadSQL() {
		defaultSQLConfig();
		cc = new config();
		fc = cc.getConfig("database.yml", ""); 
		
		if(fc.getBoolean("database.enable")){
			sql database = new sql(fc.getString("database.host"), fc.getString("database.password"), fc.getString("database.database"), fc.getString("database.table"), fc.getString("database.username"));
			main.getInstance().database = database;
		}
	}
	public static void defautlMessage(Boolean b, String file){
		
		cc = new config();
		fc = cc.getConfig(file, "/Messages/" + main.getInstance().Language);
		if(b){
			fc.addDefaults(YamlConfiguration.loadConfiguration(main.getInstance().getResource("en_en/actionbar.yml")));
		}else{
			fc.addDefaults(YamlConfiguration.loadConfiguration(main.getInstance().getResource("en_en/" + file)));
		}
		fc.addDefaults(YamlConfiguration.loadConfiguration(main.getInstance().getResource( main.getInstance().Language.toLowerCase() + "/" +  file)));
		fc.options().copyDefaults(true);
		cc.saveConfig(file, fc, "/Messages/" + main.getInstance().Language);
	}
	public static void defaultBunnyConfig(){
		cc = new config();
		YamlConfiguration yml = YamlConfiguration.loadConfiguration(main.getInstance().getResource("bunny.yml"));
		fc = cc.getConfig("bunny.yml", ""); 
		fc.addDefaults(yml);
		fc.options().copyDefaults(true);
		cc.saveConfig("bunny.yml", fc, "");
	}
	public static void defaultTopConfig(){
		cc = new config();
		YamlConfiguration yml = YamlConfiguration.loadConfiguration(main.getInstance().getResource("topConfig.yml"));
		fc = cc.getConfig("topConfig.yml", ""); 
		fc.addDefaults(yml);
		fc.options().copyDefaults(true);
		cc.saveConfig("topConfig.yml", fc, "");
	}
	public static void defaultSQLConfig(){
		cc = new config();
		YamlConfiguration yml = YamlConfiguration.loadConfiguration(main.getInstance().getResource("database.yml"));
		fc = cc.getConfig("database.yml", ""); 
		fc.addDefaults(yml);
		fc.options().copyDefaults(true);
		cc.saveConfig("database.yml", fc, "");
	}
}

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
		fc = cc.getConfig("scoreboard.yml", "/Messages/" + DiceEaster.getInstance().Language);
		DiceEaster.getInstance().colourreplace("ScoreboardHeader", fc.getString("Message.Header"));
		DiceEaster.getInstance().colourreplace("ScoreboardHidden", fc.getString("Message.Hidden"));
		DiceEaster.getInstance().colourreplace("ScoreboardFind", fc.getString("Message.Find"));
	}
	public static void loadChatMessage(Boolean b){
		defautlMessage(b, "chat.yml");
		cc = new config();
		fc = cc.getConfig("chat.yml", "/Messages/" + DiceEaster.getInstance().Language);
		DiceEaster.getInstance().colourreplace("Header", fc.getString("Message.Header"));
		DiceEaster.getInstance().colourreplace("NoPermissions", fc.getString("Message.NoPermissions"));
		DiceEaster.getInstance().colourreplace("RemoveEgg", fc.getString("Message.RemoveEgg"));
		DiceEaster.getInstance().colourreplace("GiveEgg", fc.getString("Message.GiveEgg"));
		DiceEaster.getInstance().colourreplace("EggDrop", fc.getString("Message.EggDrop"));
		DiceEaster.getInstance().colourreplace("EggPickUp", fc.getString("Message.EggPickUp"));
		DiceEaster.getInstance().colourreplace("EggRemove", fc.getString("Message.EggRemove"));
		DiceEaster.getInstance().colourreplace("Already", fc.getString("Message.Already"));
		DiceEaster.getInstance().colourreplace("WrongWorld", fc.getString("Message.WrongWorld"));
		DiceEaster.getInstance().colourreplace("World", fc.getString("Message.World"));
		DiceEaster.getInstance().colourreplace("Win", fc.getString("Message.Win"));
		DiceEaster.getInstance().colourreplace("setWin", fc.getString("Message.setWin"));
		DiceEaster.getInstance().colourreplace("WinWrong", fc.getString("Message.WinWrong"));
		DiceEaster.getInstance().colourreplace("TopCommand", fc.getString("Message.TopCommand"));
		DiceEaster.getInstance().colourreplace("topOutput1", fc.getString("Message.TopOutputStardet"));
		DiceEaster.getInstance().colourreplace("topOutput2", fc.getString("Message.TopOutputFinish"));
		DiceEaster.getInstance().colourreplace("NoMoreRemoveEggs", fc.getString("Message.NoMoreRemoveEggs"));
	}
	public static void loadActionBarMessage(Boolean b){
		defautlMessage(b, "actionbar.yml");
		cc = new config();
		fc = cc.getConfig("actionbar.yml", "/Messages/" + DiceEaster.getInstance().Language);
		DiceEaster.getInstance().colourreplace("ActionBarFind", fc.getString("Message.find"));
		DiceEaster.getInstance().colourreplace("ActionBarAlready", fc.getString("Message.already"));
	}
	public static void loadTitleMessage(Boolean b){
		defautlMessage(b, "title.yml");
		cc = new config();
		fc = cc.getConfig("title.yml", "/Messages/" + DiceEaster.getInstance().Language);
		DiceEaster.getInstance().colourreplace("Title", fc.getString("Message.title"));
		DiceEaster.getInstance().colourreplace("SubTitle", fc.getString("Message.subtitle"));
		DiceEaster.getInstance().colourreplace("already_Title", fc.getString("Message.already_title"));
		DiceEaster.getInstance().colourreplace("already_SubTitle", fc.getString("Message.already_subtitle"));
	}
	public static void loadBarAPIMessage(Boolean b){
		defautlMessage(b, "barapi.yml");
		cc = new config();
		fc = cc.getConfig("barapi.yml", "/Messages/" + DiceEaster.getInstance().Language);
		DiceEaster.getInstance().colourreplace("BarAPIFind", fc.getString("Message.find"));
		DiceEaster.getInstance().colourreplace("BarAPIAlready", fc.getString("Message.already"));
	}
	public static void loadBunnyConfig(){
		defaultBunnyConfig();
		cc = new config();
		fc = cc.getConfig("bunny.yml", ""); 
		DiceEaster.getInstance().link1 = fc.getString("Bunny.Skeleton.head_textur_url1");
		DiceEaster.getInstance().link2 = fc.getString("Bunny.Skeleton.head_textur_url2");
		DiceEaster.getInstance().item1 = fc.getInt("Bunny.items.leftHandNormal");
		DiceEaster.getInstance().item2 = fc.getInt("Bunny.items.rightHandNormal");
		DiceEaster.getInstance().rnd_item1 = fc.getInt("Bunny.items.leftHandRandom");
		DiceEaster.getInstance().rnd_item2 = fc.getInt("Bunny.items.rightHandRandom");
		DiceEaster.getInstance().rndItemRange = fc.getInt("Bunny.items.randomRange");
		DiceEaster.getInstance().rndSpawnEggs = fc.getInt("Bunny.onSpawn.dropItem");
		DiceEaster.getInstance().rndAmount = fc.getInt("Bunny.onSpawn.dropAmount");
		DiceEaster.getInstance().rndSpawnEggsRange = fc.getInt("Bunny.onSpawn.dropRange");
		DiceEaster.getInstance().spawnBunnys = fc.getBoolean("Bunny.spawn.spawning");
		DiceEaster.getInstance().spawnRate = fc.getInt("Bunny.spawn.spawnRate");
		DiceEaster.getInstance().aggrisive = fc.getBoolean("Bunny.spawn.spawnAgrissive");
		DiceEaster.getInstance().bunnysDropItems = fc.getBoolean("Bunny.spawn.bunnysDropItems");
	}
	public static void loadTop(){
		defaultTopConfig();
		cc = new config();
		fc = cc.getConfig("topConfig.yml", ""); 
		DiceEaster.getInstance().colourreplace("TopHeader", fc.getString("TopList.Header"));
		DiceEaster.getInstance().colourreplace("TopFooter", fc.getString("TopList.Footer"));
		DiceEaster.getInstance().colourreplace("TopMessage", fc.getString("TopList.Message"));
		DiceEaster.getInstance().autoUpdate = fc.getBoolean("TopList.AutoUpdate");
	}
	public static void loadSQL() {
		defaultSQLConfig();
		cc = new config();
		fc = cc.getConfig("database.yml", ""); 
		
		if(fc.getBoolean("database.enable")){
			sql database = new sql(fc.getString("database.host"), fc.getString("database.password"), fc.getString("database.database"), fc.getString("database.table"), fc.getString("database.username"));
			DiceEaster.getInstance().database = database;
		}
	}
	public static void defautlMessage(Boolean b, String file){
		
		cc = new config();
		fc = cc.getConfig(file, "/Messages/" + DiceEaster.getInstance().Language);
		if(b){
			fc.addDefaults(YamlConfiguration.loadConfiguration(DiceEaster.getInstance().getResource("en_en/actionbar.yml")));
		}else{
			fc.addDefaults(YamlConfiguration.loadConfiguration(DiceEaster.getInstance().getResource("en_en/" + file)));
		}
		fc.addDefaults(YamlConfiguration.loadConfiguration(DiceEaster.getInstance().getResource( DiceEaster.getInstance().Language.toLowerCase() + "/" +  file)));
		fc.options().copyDefaults(true);
		cc.saveConfig(file, fc, "/Messages/" + DiceEaster.getInstance().Language);
	}
	public static void defaultBunnyConfig(){
		cc = new config();
		YamlConfiguration yml = YamlConfiguration.loadConfiguration(DiceEaster.getInstance().getResource("bunny.yml"));
		fc = cc.getConfig("bunny.yml", ""); 
		fc.addDefaults(yml);
		fc.options().copyDefaults(true);
		cc.saveConfig("bunny.yml", fc, "");
	}
	public static void defaultTopConfig(){
		cc = new config();
		YamlConfiguration yml = YamlConfiguration.loadConfiguration(DiceEaster.getInstance().getResource("topConfig.yml"));
		fc = cc.getConfig("topConfig.yml", ""); 
		fc.addDefaults(yml);
		fc.options().copyDefaults(true);
		cc.saveConfig("topConfig.yml", fc, "");
	}
	public static void defaultSQLConfig(){
		cc = new config();
		YamlConfiguration yml = YamlConfiguration.loadConfiguration(DiceEaster.getInstance().getResource("database.yml"));
		fc = cc.getConfig("database.yml", ""); 
		fc.addDefaults(yml);
		fc.options().copyDefaults(true);
		cc.saveConfig("database.yml", fc, "");
	}
}

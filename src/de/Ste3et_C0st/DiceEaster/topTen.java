package de.Ste3et_C0st.DiceEaster;

import static org.bukkit.util.NumberConversions.ceil;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class topTen {
	private static config cc;
	private static FileConfiguration fc;
	HashMap<UUID, Integer> uuidList = new HashMap<UUID, Integer>();
	public topTen(Player player, Integer side){
		if(side == 0){side = 1;}
		String[] files = new File("plugins/DiceEaster/players").list();
		for(String s : files){
			String path = "/players";
			cc = new config();
			fc = cc.getConfig(s, path);
			UUID uuid = UUID.fromString(s.replace(".yml", ""));
			Integer eggs = 0;
			if(fc.isList("Eggs")){
				eggs = fc.getList("Eggs").size();
			}
			uuidList.put(uuid, eggs);
		}
		
		HashMap<UUID, Integer> sortedMap = sortByKeys(uuidList);
		
		if(side!=Integer.MAX_VALUE){
			Integer entryPerSide = 10;
			Integer min = side * entryPerSide - entryPerSide;
			Integer max = side * entryPerSide;
			Integer maxSides = ceil((double)sortedMap.size()/(double)entryPerSide);
			
			if(side > maxSides){
				player.sendMessage(main.getInstance().header + variablen.msg.get("TopCommand").replace("@MIN", "0").replace("@MAX", maxSides.toString()));
				return;
			}
			
			int i = 0;
			player.sendMessage(variablen.msg.get("TopHeader"));
			for(UUID uuid : sortedMap.keySet()){
				if(i >= min && i <= max){
					Integer L = i + 1;
					OfflinePlayer of = Bukkit.getOfflinePlayer(uuid);
					player.sendMessage(variablen.msg.get("TopMessage").replace("@NR", L.toString()).replace("@PLAYERNAME", of.getName()).replace("@EGGS", sortedMap.get(uuid).toString()));
				}
				i++;
			}
			player.sendMessage(variablen.msg.get("TopFooter").replace("@MIN", "0").replace("@MAX", maxSides.toString()));
		}else{
			if(player!=null){player.sendMessage(variablen.msg.get("topOutput1"));}
			new File("plugins/DiceEaster/top/List.yml").delete();
			cc = new config();
			fc = cc.getConfig("List.yml", "/top");
			for(UUID uuid : sortedMap.keySet()){
				if(Bukkit.getOfflinePlayer(uuid).hasPlayedBefore()){
					fc.set("Players." + uuid.toString() + ".PlayerName", Bukkit.getOfflinePlayer(uuid).getName());
					fc.set("Players." + uuid.toString() + ".Eggs", sortedMap.get(uuid));
				}
			}
			
			cc.saveConfig("List.yml", fc, "/top");
			if(player!=null){player.sendMessage(variablen.msg.get("topOutput2"));}
		}

	}
	
	public HashMap<UUID, Integer> sortByKeys(HashMap<UUID, Integer> passedMap) {
		   List<UUID> mapKeys = new ArrayList<UUID>(passedMap.keySet());
		   List<Integer> mapValues = new ArrayList<Integer>(passedMap.values());
		   Collections.sort(mapValues);
		   Collections.sort(mapKeys);

		   HashMap<UUID, Integer> sortedMap = new HashMap<UUID, Integer>();

		   Iterator<Integer> valueIt = mapValues.iterator();
		   while (valueIt.hasNext()) {
		       Object val = valueIt.next();
		       Iterator<UUID> keyIt = mapKeys.iterator();

		       while (keyIt.hasNext()) {
		           Object key = keyIt.next();
		           String comp1 = passedMap.get(key).toString();
		           String comp2 = val.toString();

		           if (comp1.equals(comp2)){
		               passedMap.remove(key);
		               mapKeys.remove(key);
		               sortedMap.put((UUID)key, (Integer)val);
		               break;
		           }

		       }

		   }
		   return sortedMap;
		}
}

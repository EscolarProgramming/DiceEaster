package de.Ste3et_C0st.DiceEaster;

import java.sql.SQLException;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class sql {
	
	String hostname;
	String password;
	String database;
	String username;
	String table;
	//SQLL, H2, Oracle, Microsoft
	public sql(String host, String psw, String db, String table, String username){
		this.hostname = host;
		this.password = psw;
		this.database = db;
		this.username = username;
		this.table = table;
		try
	    {
			MySQLDatabase.executeQuery("CREATE DATABASE IF NOT EXISTS " + database, MySQLDatabase.getConnection(hostname, username, password));
			MySQLDatabase.executeQuery("CREATE TABLE IF NOT EXISTS " + database + "." + table + " (uuid CHAR(40) UNIQUE, eggs CHAR(40))", MySQLDatabase.getConnection(host, username, psw));
			main.getInstance().data = this;
	    }
		catch (Exception e)
	    {
	      System.out.println("Couldnt connect with MySQL, disabling Nick v1.0 ...");Bukkit.getServer().getPluginManager().disablePlugin(main.getInstance());
	    }
	}
	
	public void addPlayer(Player player){
		UUID uuid = player.getUniqueId();
		Integer eggs = main.getInstance().getPlayerScore(player);
		String q = "INSERT INTO " + database + "." + table + " (uuid, eggs) VALUES (\"" + uuid.toString() + "\",\"" + eggs.toString() + "\") ON DUPLICATE KEY UPDATE eggs='" + eggs.toString()  + "'";
		try {
			MySQLDatabase.executeQuery(q, MySQLDatabase.getConnection(hostname, username, password));
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {}
	}
}

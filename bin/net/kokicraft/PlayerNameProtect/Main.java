package net.kokicraft.PlayerNameProtect;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import net.kokicraft.PlayerNameProtect.command.ban;
import net.kokicraft.PlayerNameProtect.command.info;
import net.kokicraft.PlayerNameProtect.command.unban;
import net.kokicraft.PlayerNameProtect.event.PlayerJoin;
import net.kokicraft.PlayerNameProtect.event.PlayerPreLogin;
import net.kokicraft.PlayerNameProtect.mysql.MySQL;

public class Main extends JavaPlugin implements Listener {

	public static Main plugin;
	public static Logger log = Logger.getLogger("Minecraft");
	public static MySQL mysql;

	String sqlHost;
	String sqlPort;
	String sqlDb;
	String sqlUser;
	String sqlPw;

	public void onEnable() {
		plugin = this;
		registerEvent();
		registerCommand();
		setUpConfig();

		connectMySQL();
		Bukkit.getServer().getConsoleSender().sendMessage("[PlayerNameProtect] Has been enabled correctly!");
		Bukkit.getServer().getConsoleSender().sendMessage("[PlayerNameProtect] Author: Koke_Cacao");
	}
	
	public void registerEvent() {
		Bukkit.getServer().getPluginManager().registerEvents(new PlayerPreLogin(plugin), this);
		Bukkit.getServer().getPluginManager().registerEvents(new PlayerJoin(plugin), this);
	}
	
	public void registerCommand() {
		getCommand("pnp").setExecutor(new ban(plugin));
		getCommand("pnp").setExecutor(new unban(plugin));
		getCommand("pnp").setExecutor(new info(plugin));
	}
	
	
	public void setUpConfig() {
		getConfig().options().copyDefaults(true);
		sqlHost = plugin.getConfig().getString("MySQL.host");
		sqlPort = plugin.getConfig().getString("MySQL.port");
		sqlDb = plugin.getConfig().getString("MySQL.database");
		sqlUser = plugin.getConfig().getString("MySQL.user");
		sqlPw = plugin.getConfig().getString("MySQL.password");
		saveConfig();
	}
	
	public void connectMySQL() {
		mysql = new MySQL(sqlHost, sqlPort, sqlDb, sqlUser, sqlPw);
		mysql.update("CREATE TABLE IF NOT EXIXTS pnp_users(name varchar(32), lowname varchar(32), UUID varchar(64), ban int, unban int, banreason varchar(64), unbanreason varchar(64))");
	}

	public void onDisable() {
		plugin = null;
		Bukkit.getServer().getConsoleSender().sendMessage("[PlayerNameProtect] Has been disabled correctly!");
	}

	
}

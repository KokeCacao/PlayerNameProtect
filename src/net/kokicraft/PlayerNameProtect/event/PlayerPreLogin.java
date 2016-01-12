package net.kokicraft.PlayerNameProtect.event;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPreLoginEvent;
import org.bukkit.event.player.PlayerPreLoginEvent.Result;

import net.kokicraft.PlayerNameProtect.Main;
import net.kokicraft.PlayerNameProtect.tables.pnp_users;

public class PlayerPreLogin implements Listener {
	
	private Main plugin;

	public PlayerPreLogin(Main plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onPlayerPreLogin(PlayerPreLoginEvent event) {
		if (plugin.getConfig().getStringList("Login.AntiLoginName").contains(event.getName())) {
			event.disallow(Result.KICK_WHITELIST, System.getProperty("line.separator") + "You can not use this name to login");
			return;
		}
		if (plugin.getConfig().getBoolean("Enable")) {
			String name = event.getName();
			String uuid = event.getUniqueId().toString();
			pnp_users.createData(name, uuid);
			if ((testName(event, name, uuid)) && (plugin.getConfig().getBoolean("Login.CheckBan"))) {
				testBan(event, name);
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	public boolean testName(PlayerPreLoginEvent event, String name, String uuid) {
		String lowname = name.toLowerCase();
		String dataname = pnp_users.getname(lowname);
		String datalowname = pnp_users.getlowname(name);
		String datauuid = pnp_users.getUUID(name);
		if ((plugin.getConfig().getBoolean("Login.CheckName")) && (name.equalsIgnoreCase(dataname)) && (name.equals(dataname) == false)) {
			event.disallow(Result.KICK_WHITELIST, System.getProperty("line.separator") + "Please use name: '" + dataname + "' to login");
			plugin.getServer().getConsoleSender().sendMessage(name  + " login with wrong name");
			return false;
		} else {
			if ((plugin.getConfig().getBoolean("Login.CheckUUID")) && (!uuid.equals(datauuid))) {
				event.disallow(Result.KICK_WHITELIST, System.getProperty("line.separator") + "Your uuid is not true");
				plugin.getServer().getConsoleSender().sendMessage(name  + " login with wrong uuid");
				return false;
			} else {
				return true;
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	public boolean testBan(PlayerPreLoginEvent event, String name) {
		Integer ban = pnp_users.getBan(name);
		Integer unban = pnp_users.getUnban(name);
		String reason = pnp_users.getBanreason(name);
		if (ban > unban) {
			event.disallow(Result.KICK_WHITELIST, System.getProperty("line.separator") + "You have been baned because" + reason);
			plugin.getServer().getConsoleSender().sendMessage("baned player: " + name  + " try to login");
			return false;
		} else {
			plugin.getServer().getConsoleSender().sendMessage(name  + " login successfully");
			return true;
		}
	}
}

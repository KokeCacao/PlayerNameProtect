package net.kokicraft.PlayerNameProtect.tables;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import net.kokicraft.PlayerNameProtect.Main;

public class pnp_users {

	public static boolean nameExists(String name) {
		try {
			ResultSet rs = Main.mysql.query("SELECT * FROM pnp_users WHERE name = '" + name + "';");
			if (rs.next()) {
				return rs.getString("name") != null;
			}
			return false;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static void createData(String name, String uuid) {
		if (!nameExists(name)) {
			Main.plugin.getServer().getConsoleSender().sendMessage(name + "dose not have database, create one");
			String lowname = name.toLowerCase();
			String ban = "0";
			String unban = "0";
			String banreason = null;
			String unbanreason = null;
			Main.mysql
					.update("INSERT INTO `pnp_users` (`name`, `lowname`, `UUID`, `ban`, `banreason`, `unban`, `unbanreason`) VALUES ('"
							+ name + "', '" + lowname + "', '" + uuid + "', '" + ban + "', '" + banreason + "', '"
							+ unban + "', '" + unbanreason + "');");
			Main.plugin.getServer().getConsoleSender().sendMessage(name + "'s database create successfully");
		}
	}

	public static void createData(Player p) {
		String name = p.getName();
		String uuid = p.getUniqueId().toString();
		createData(name, uuid);
	}

	public static void createData(String s) {
		Player p = (Player) Bukkit.getOfflinePlayer(s);
		createData(p);
	}

	public static String getlowname(String name) {
		String lowname = null;
		if (nameExists(name)) {
			try {
				ResultSet rs = Main.mysql.query("SELECT * FROM pnp_users WHERE name = '" + name + "';");
				rs.next();
				lowname = rs.getString("lowname");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			createData(name);
			getlowname(name);
		}
		return lowname;
	}

	public static String getname(String lowname) {
		String name = null;
		try {
			ResultSet rs = Main.mysql.query("SELECT * FROM pnp_users WHERE lowname = '" + lowname + "';");
			rs.next();
			name = rs.getString("name");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return name;
	}

	public static String getUUID(String name) {
		String uuid = null;
		if (nameExists(name)) {
			try {
				ResultSet rs = Main.mysql.query("SELECT * FROM pnp_users WHERE name = '" + name + "';");
				rs.next();
				uuid = rs.getString("UUID");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			createData(name);
			getUUID(name);
		}
		return uuid;
	}

	public static Integer getBan(String name) {
		Integer ban = null;
		if (nameExists(name)) {
			try {
				ResultSet rs = Main.mysql.query("SELECT * FROM pnp_users WHERE name = '" + name + "';");
				rs.next();
				ban = rs.getInt("ban");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			createData(name);
			getBan(name);
		}
		return ban;
	}

	public static Integer getUnban(String name) {
		Integer unban = null;
		if (nameExists(name)) {
			try {
				ResultSet rs = Main.mysql.query("SELECT * FROM pnp_users WHERE name = '" + name + "';");
				rs.next();
				unban = rs.getInt("unban");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			createData(name);
			getUnban(name);
		}
		return unban;
	}

	public static void setBan(String name, Integer i) {
		if (nameExists(name)) {
			Main.mysql.update("UPDATE pnp_users SET ban = '" + i.toString() + "' WHERE name = " + name + " ;");
		} else {
			createData(name);
			setBan(name, i);
		}
	}

	public static void setBanreason(String name, String reason) {
		if (nameExists(name)) {
			Main.mysql.update("UPDATE pnp_users SET banreason = '" + reason + "' WHERE name = " + name + " ;");
		} else {
			createData(name);
			setBanreason(name, reason);
		}
	}

	public static String getBanreason(String name) {
		String banreason = null;
		if (nameExists(name)) {
			try {
				ResultSet rs = Main.mysql.query("SELECT * FROM pnp_users WHERE name = '" + name + "';");
				rs.next();
				banreason = rs.getString("banreason");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			createData(name);
			getBanreason(name);
		}
		return banreason;
	}

	public static void setUnban(String name, Integer i) {
		if (nameExists(name)) {
			Main.mysql.update("UPDATE pnp_users SET unban = '" + i.toString() + "' WHERE name = " + name + " ;");
		} else {
			createData(name);
			setUnban(name, i);
		}
	}

	public static void setUnbanreason(String name, String reason) {
		if (nameExists(name)) {
			Main.mysql.update("UPDATE pnp_users SET unbanreason = '" + reason + "' WHERE name = " + name + " ;");
		} else {
			createData(name);
			setUnbanreason(name, reason);
		}
	}

	public static String getUnbanreason(String name) {
		String unbanreason = null;
		if (nameExists(name)) {
			try {
				ResultSet rs = Main.mysql.query("SELECT * FROM pnp_users WHERE name = '" + name + "';");
				rs.next();
				unbanreason = rs.getString("unbanreason");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			createData(name);
			getUnbanreason(name);
		}
		return unbanreason;
	}

	public static void addBan(String name, Integer i) {
		if (nameExists(name)) {
			setBan(name, Integer.valueOf((getBan(name).intValue()) + (i.intValue())));
		} else {
			createData(name);
			addBan(name, i);
		}
	}

	public static void addUnban(String name, Integer i) {
		if (nameExists(name)) {
			setUnban(name, Integer.valueOf((getUnban(name).intValue()) + (i.intValue())));
		} else {
			createData(name);
			addUnban(name, i);
		}
	}
}

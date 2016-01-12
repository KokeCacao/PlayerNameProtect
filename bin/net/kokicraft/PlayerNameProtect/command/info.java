package net.kokicraft.PlayerNameProtect.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.kokicraft.PlayerNameProtect.Main;
import net.kokicraft.PlayerNameProtect.tables.pnp_users;

public class info implements CommandExecutor {

	private Main plugin;

	public info(Main plugin) {
		this.plugin = plugin;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if ((sender instanceof Player) && (((Player)sender).isOp() == false)) {
			sender.sendMessage("[PlayerNameProtect] You do not have permission to use this command");
			return true;
		}
		if (args[0] == "info") {
			if (args[1] != null) {
				String name = pnp_users.getname(args[1].toLowerCase());
				if (name != null) {
					String uuid = pnp_users.getUUID(name);
					String ban = pnp_users.getBan(name).toString();
					String banreason = pnp_users.getBanreason(name);
					String unban = pnp_users.getUnban(name).toString();
					String unbanreason = pnp_users.getUnbanreason(name);
					sender.sendMessage("[]----------INFO----------[]");
					sender.sendMessage("Name: " + name);
					sender.sendMessage("UUID: " + uuid);
					sender.sendMessage("Ban: " + ban);
					sender.sendMessage("BanReason: " + banreason);
					sender.sendMessage("Unban: " + unban);
					sender.sendMessage("UnanReason: " + unbanreason);
					return true;
				} else {
					sender.sendMessage("[PlayerNameProtect] Name no find!");
					return true;
				}
			}
		}
		return notCorrect(sender);
	}

	public boolean notCorrect(CommandSender sender) {
		sender.sendMessage("Correct usage:");
		sender.sendMessage("/pnp info [Player]");
		return false;
	}
}

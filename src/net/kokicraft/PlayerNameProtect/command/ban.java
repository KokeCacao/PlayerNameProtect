package net.kokicraft.PlayerNameProtect.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.kokicraft.PlayerNameProtect.Main;
import net.kokicraft.PlayerNameProtect.tables.pnp_users;

public class ban implements CommandExecutor {

	private Main plugin;

	public ban(Main plugin) {
		this.plugin = plugin;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if ((sender instanceof Player) && (((Player)sender).isOp() == false)) {
			sender.sendMessage("[PlayerNameProtect] You do not have permission to use this command");
			return true;
		}
		if (args[0] == "ban") {
			if (args[1] != null) {
				if (args[2] != null) {
					String name = args[1];
					String reason = args[2];
					pnp_users.addBan(name, 1);
					pnp_users.setBanreason(name, reason);
					sender.sendMessage("You ban " + name + " with reason:" + reason);
					return true;
				}
			}
		}
		return notCorrect(sender);
	}

	public boolean notCorrect(CommandSender sender) {
		sender.sendMessage("Correct usage:");
		sender.sendMessage("/pnp ban [Player] [reason]");
		return false;
	}
}

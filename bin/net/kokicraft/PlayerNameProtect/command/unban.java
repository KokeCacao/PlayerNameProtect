package net.kokicraft.PlayerNameProtect.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.kokicraft.PlayerNameProtect.Main;
import net.kokicraft.PlayerNameProtect.tables.pnp_users;

public class unban implements CommandExecutor {

	private Main plugin;

	public unban(Main plugin) {
		this.plugin = plugin;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if ((sender instanceof Player) && (((Player)sender).isOp() == false)) {
			sender.sendMessage("[PlayerNameProtect] You do not have permission to use this command");
			return true;
		}
		if (args[0] == "unban") {
			if (args[1] != null) {
				if (args[2] != null) {
					String name = args[1];
					String reason = args[2];

					if (pnp_users.getUnban(name).intValue() >= Main.plugin.getConfig().getInt("Ban.Unban.Max")) {
						sender.sendMessage("You can not unban " + name + " anymore");
						return false;
					} else {
						pnp_users.addUnban(name, 1);
						pnp_users.setUnbanreason(name, reason);
						sender.sendMessage("You unban " + name + " with reason:" + reason);
						return true;
					}
					
				}
			}
		}
		return notCorrect(sender);
	}

	public boolean notCorrect(CommandSender sender) {
		sender.sendMessage("Correct usage:");
		sender.sendMessage("/pnp unban [Player] [reason]");
		return false;
	}
}

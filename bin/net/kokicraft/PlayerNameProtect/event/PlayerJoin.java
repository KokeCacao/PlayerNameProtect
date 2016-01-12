package net.kokicraft.PlayerNameProtect.event;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

import net.kokicraft.PlayerNameProtect.Main;

public class PlayerJoin implements Listener {

	private Main plugin;

	public PlayerJoin(Main plugin) {
		this.plugin = plugin;
	}

	@EventHandler(priority = EventPriority.LOW)
	public void onPlayerJoin(PlayerJoinEvent e) {
		if (plugin.getConfig().getBoolean("DoublePlayer.Enable")) {
			Integer time = plugin.getConfig().getInt("DoublePlayer.CheckTime");
			new DoubleCheckTask(e.getPlayer()).runTaskLater(plugin, time);
		}
	}

	class DoubleCheckTask extends BukkitRunnable {
		Player player1;
		int i;

		DoubleCheckTask(Player player) {
			this.player1 = player;
			this.i = 0;
		}

		public void run() {
			if (player1.isOnline()) {
				Player[] players = Bukkit.getOnlinePlayers();
				for (int j = 0; j < players.length; j++) {
					Player player2 = players[j];
					if ((this.player1.getName().equals(player2.getName())) && (++this.i > 1)) {
						this.player1.kickPlayer("Someone try to login with your player name.");
						player2.kickPlayer("Someone try to login with your player name.");
						plugin.getServer().getConsoleSender()
								.sendMessage("Kick " + player2.getPlayer().getName() + " to prevent double player bug");
						plugin.getServer().getConsoleSender()
								.sendMessage("Kick " + player1.getPlayer().getName() + " to prevent double player bug");
						break;
					}
				}
			}
		}
	}
}

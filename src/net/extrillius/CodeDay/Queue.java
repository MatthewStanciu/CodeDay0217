package net.extrillius.CodeDay;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

/**
 * Created by mattbstanciu on 2/19/17.
 */
public class Queue extends JavaPlugin {
    private ArrayList<Player> queue = new ArrayList<>();
    public static int seconds = 10;
    int timer;

    public void addToQueue(Player p) {
        if (queue.contains(p)) {
            p.sendMessage(ChatColor.RED + "You are already in the queue!");
        }
        else {
            queue.add(p);
            p.sendMessage(ChatColor.GREEN + "You have been added to the queue");
        }
    }
    public void teleportPlayers() {
        for (Player p : getServer().getOnlinePlayers()) {
            queue.remove(p);
            p.teleport(new Location(p.getWorld(), getConfig().getDouble("checkpoints.beginning.X"),
                    getConfig().getDouble("checkpoints.beginning.Y"),
                    getConfig().getDouble("checkpoints.beginning.Z")));
        }
    }
    public void clear() {
        for (Player p : getServer().getOnlinePlayers()) {
            queue.remove(p);
        }
    }
    public boolean isEmpty() {
        return queue.isEmpty();
    }
    public void startGame() {
        if (queue.size() > 2) {
            timer = getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
                @Override
                public void run() {
                    seconds--;
                    if (seconds != 0) {
                        getServer().broadcastMessage(ChatColor.GREEN + "" + seconds + " until the game starts");
                    }
                    else {
                        getServer().getScheduler().cancelTask(timer);
                        seconds = 10;
                        teleportPlayers();
                        getServer().broadcastMessage(ChatColor.RED + "GAME STARTED!");
                    }
                }
            }, 20L,20L);
        }
    }
}

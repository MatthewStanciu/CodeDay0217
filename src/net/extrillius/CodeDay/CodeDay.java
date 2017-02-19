package net.extrillius.CodeDay;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by mattbstanciu on 2/18/17.
 */
public class CodeDay extends JavaPlugin {

    public void onEnable() {
        this.getConfig().addDefault("checkpoints." + "", "");
        this.getConfig().set("checkpoints." + "1.X", 0.0); //gold
        this.getConfig().set("checkpoints." + "1.Y", 0.0);
        this.getConfig().set("checkpoints." + "1.Z", 0.0);
        this.getConfig().set("checkpoints." + "2.X", 0.0); //iron
        this.getConfig().set("checkpoints." + "2.Y", 0.0);
        this.getConfig().set("checkpoints." + "2.Z", 0.0);
        this.getConfig().set("checkpoints." + "3.X", 0.0); //diamond
        this.getConfig().set("checkpoints." + "3.Y", 0.0);
        this.getConfig().set("checkpoints." + "3.Z", 0.0);
        this.getConfig().set("checkpoints." + "end.X", 0.0); //obsidian
        this.getConfig().set("checkpoints." + "end.Y", 0.0);
        this.getConfig().set("checkpoints." + "end.Z", 0.0);
        this.getConfig().set("checkpoints." + "beginning.X", 0.0);
        this.getConfig().set("checkpoints." + "beginning.Y", 0.0);
        this.getConfig().set("checkpoints." + "beginning.Z", 0.0);
        saveConfig();
        reloadConfig();

        Logger logger = getLogger();
        if (getConfig().getDouble("checkpoints.beginning.X") == 0.0) {
            logger.log(Level.WARNING, "Don't forget to set the beginning loc");
        }

        getServer().getPluginManager().registerEvents(new Events(), this);
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player p = (Player) sender;
        Queue queue = new Queue();

        if (cmd.getName().equalsIgnoreCase("startloc")) {
            getConfig().set("checkpoints.beginning.X", p.getLocation().getX());
            getConfig().set("checkpoints.beginning.Y", p.getLocation().getY());
            getConfig().set("checkpoints.beginning.Z", p.getLocation().getZ());
            saveConfig();
            reloadConfig();
            p.sendMessage(ChatColor.GREEN + "startloc saved");
        }

        if (cmd.getName().equalsIgnoreCase("join")) {
            if (!queue.isEmpty()) {
                queue.addToQueue(p);
                queue.startGame();
            }
            else {
                p.sendMessage(ChatColor.RED + "Game already in progress!");
                return false;
            }
        }

        return true;
    }
}

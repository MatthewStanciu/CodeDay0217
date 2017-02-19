package net.extrillius.CodeDay;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.*;


import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by mattbstanciu on 2/18/17.
 */
public class CodeDay extends JavaPlugin implements Listener {

    public void onEnable() {
        this.getConfig().addDefault("checkpoints", "");
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

        getServer().getPluginManager().registerEvents(this, this);
    }
    //events
    public Map<String, String> checkpoint = new HashMap<>();

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        Inventory i = p.getInventory();

        if (p.getLocation().getBlock().getType() == Material.GOLD_BLOCK) {

            getConfig().set("checkpoints.1", p.getLocation()); //iffy
            checkpoint.put(p.getName(), "1");

            ItemStack goldSword = new ItemStack(Material.GOLD_SWORD);
            ItemStack[] cp1 = {
                    new ItemStack(Material.LEATHER_HELMET),
                    new ItemStack(Material.LEATHER_CHESTPLATE),
                    new ItemStack(Material.LEATHER_LEGGINGS) ,
                    new ItemStack(Material.LEATHER_BOOTS)
            };
            cp1[0].setItemMeta(cp1[0].getItemMeta());
            cp1[1].setItemMeta(cp1[1].getItemMeta());
            cp1[2].setItemMeta(cp1[2].getItemMeta());
            cp1[3].setItemMeta(cp1[3].getItemMeta());
            goldSword.setItemMeta(goldSword.getItemMeta());
            goldSword.setDurability((short) - 1);

            i.clear();
            i.setItem(1, goldSword);
            p.getInventory().setArmorContents(cp1);
        }
        if (p.getLocation().getBlock().getType() == Material.IRON_BLOCK) {
            getConfig().get("checkpoints.2", p.getLocation());
            checkpoint.put(p.getName(), "2");

            ItemStack ironSword = new ItemStack(Material.IRON_SWORD);
            ItemStack[] cp2 = {
                    new ItemStack(Material.IRON_HELMET),
                    new ItemStack(Material.IRON_CHESTPLATE),
                    new ItemStack(Material.IRON_LEGGINGS),
                    new ItemStack(Material.IRON_BOOTS)
            };
            cp2[0].setItemMeta(cp2[0].getItemMeta());
            cp2[1].setItemMeta(cp2[1].getItemMeta());
            cp2[2].setItemMeta(cp2[2].getItemMeta());
            cp2[3].setItemMeta(cp2[3].getItemMeta());
            ironSword.setItemMeta(ironSword.getItemMeta());
            ironSword.setDurability((short) - 1);

            i.clear();
            i.setItem(1, ironSword);
            p.getInventory().setArmorContents(cp2);
        }
        if (p.getLocation().getBlock().getType() == Material.DIAMOND_BLOCK) {
            getConfig().get("checkpoints.3", p.getLocation());
            checkpoint.put(p.getName(), "3");

            ItemStack diamondSword = new ItemStack(Material.IRON_SWORD);
            ItemStack[] cp3 = {
                    new ItemStack(Material.DIAMOND_HELMET),
                    new ItemStack(Material.DIAMOND_CHESTPLATE),
                    new ItemStack(Material.DIAMOND_LEGGINGS),
                    new ItemStack(Material.DIAMOND_BOOTS)
            };
            cp3[0].setItemMeta(cp3[0].getItemMeta());
            cp3[1].setItemMeta(cp3[1].getItemMeta());
            cp3[2].setItemMeta(cp3[2].getItemMeta());
            cp3[3].setItemMeta(cp3[3].getItemMeta());
            diamondSword.setItemMeta(diamondSword.getItemMeta());
            diamondSword.setDurability((short) - 1);

            i.clear();
            i.setItem(1, diamondSword);
            p.getInventory().setArmorContents(cp3);
        }
        if (p.getLocation().getBlock().getType() == Material.OBSIDIAN) {
            getConfig().get("checkpoints.end", p.getLocation());
            checkpoint.put(p.getName(), "end");
            clearScoreboard();

            i.clear();
            for (Player q : getServer().getOnlinePlayers()) {
                q.teleport(new Location(p.getWorld(), getConfig().getDouble("checkpoints.beginning.X"),
                        getConfig().getDouble("checkpoints.beginning.Y"),
                        getConfig().getDouble("checkpoints.beginning.Z")));
            }
            getServer().broadcastMessage(ChatColor.DARK_RED + p.getName() + " WON THE GAME!");
            clear();
        }
        ArrayList<Double> playerY = new ArrayList<>();
        Iterator it = playerY.iterator();
        for (Player q : getServer().getOnlinePlayers()) {
            playerY.add(q.getLocation().getY());

            while (it.hasNext()) {
                if (it.equals(it.next())) {
                    if (!(checkpoint.get(q.getName()).equals("beginning"))) {
                        deathmatchTimer();
                    }
                }
            }
        }
     }

     @EventHandler
     public void onHit(EntityDamageByEntityEvent e) {
        if (e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();


        }
     }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        final Player p = e.getEntity();
        Player killer = p.getKiller();
        updateScoreboard(killer);

        getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
            @Override
            public void run() {
                if (p.isDead()) {
                    p.setMaxHealth(20);
                    p.setHealth(20);
                }
            }
        });
        p.teleport(new Location(p.getWorld(), getConfig().getDouble("checkpoints." + checkpoint.get(p.getName()) + ".X"),
                getConfig().getDouble("checkpoints." + checkpoint.get(p.getName()) + ".Y"),
                getConfig().getDouble("checkpoints." + checkpoint.get(p.getName()) + ".Z")));
    }
    //queue
    private ArrayList<Player> queue = new ArrayList<>();
    public static int seconds = 10;
    public static int dm = 20;
    int timer;
    int dmTimer;
    public boolean deathmatch = false;

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
                        makeScoreboard();
                        getServer().broadcastMessage(ChatColor.RED + "GAME STARTED!");
                    }
                }
            }, 20L,20L);
        }
    }
    public void deathmatchTimer() {
        deathmatch = true;
        dmTimer = getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
                dm--;
                if (dm != 0) {
                    getServer().broadcastMessage(ChatColor.GREEN + "Deathmatch ends in " + dm);
                }
                else {
                    getServer().getScheduler().cancelTask(dmTimer);
                    dm = 20;
                    deathmatch = false;
                    int score;

                    Iterator entryIterator = board.getEntries().iterator();
                    for (String s : board.getEntries()) {
                        score = Integer.parseInt(s);
                    }
                    while (entryIterator.hasNext()) {

                    }
                }
            }
        }, 20L, 20L);
    }
    ScoreboardManager sm;
    Scoreboard board;
    Team team;
    Objective obj;
    Score score;
    public void makeScoreboard() {
        sm = Bukkit.getScoreboardManager();
        board = sm.getNewScoreboard();
        for (Player p : getServer().getOnlinePlayers()) {
            team = board.registerNewTeam(p.getName());
            team.addEntry(p.getName());
            team.setDisplayName(p.getName());
            obj = board.registerNewObjective("Kills", "playerKillCount");
            obj.setDisplaySlot(DisplaySlot.SIDEBAR);
            score = obj.getScore(p.getName());
        }
    }
    public void updateScoreboard(Player p) {
        score = obj.getScore(p.getName() + 1);
    }
    public void clearScoreboard() {
        for (Player p : getServer().getOnlinePlayers()) {
            board.resetScores(p.getName());
            board.resetScores("Kills");
            p.setScoreboard(sm.getNewScoreboard());
        }
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player p = (Player) sender;

        if (cmd.getName().equalsIgnoreCase("startloc")) {
            getConfig().set("checkpoints.beginning.X", p.getLocation().getX());
            getConfig().set("checkpoints.beginning.Y", p.getLocation().getY());
            getConfig().set("checkpoints.beginning.Z", p.getLocation().getZ());
            saveConfig();
            reloadConfig();
            p.sendMessage(ChatColor.GREEN + "startloc saved");
        }

        if (cmd.getName().equalsIgnoreCase("join")) {
            if (getConfig().getDouble("checkpoints.beginning.X") == 0.0) {
                p.sendMessage(ChatColor.RED + "Please set your beginning location first");
                return false;
            }
            if (!isEmpty()) {
                addToQueue(p);
                startGame();
            }
            else {
                p.sendMessage(ChatColor.RED + "Game already in progress!");
                return false;
            }
        }

        return true;
    }
}

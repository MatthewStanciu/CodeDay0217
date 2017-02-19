package net.extrillius.CodeDay;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mattbstanciu on 2/18/17.
 */
public class Events extends JavaPlugin implements Listener {

    public Map<String, String> checkpoint = new HashMap<>();

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        Inventory i = p.getInventory();
        Queue queue = new Queue();

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

            i.clear();
            for (Player q : getServer().getOnlinePlayers()) {
                q.teleport(new Location(p.getWorld(), getConfig().getDouble("checkpoints.beginning.X"),
                        getConfig().getDouble("checkpoints.beginning.Y"),
                        getConfig().getDouble("checkpoints.beginning.Z")));
            }
            getServer().broadcastMessage(ChatColor.DARK_RED + p.getName() + " WON THE GAME!");
            queue.clear();
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        final Player p = e.getEntity();

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
}

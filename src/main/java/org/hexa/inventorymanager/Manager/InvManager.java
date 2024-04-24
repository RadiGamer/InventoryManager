package org.hexa.inventorymanager.Manager;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.hexa.inventorymanager.InventoryManager;

public class InvManager {
    private InventoryManager plugin;

    public InvManager(InventoryManager plugin) {
        this.plugin = plugin;
    }

    public void saveInventory(Player player) {
        ItemStack[] items = player.getInventory().getContents();
        for (int i = 0; i < items.length; i++) {
            plugin.getConfig().set("savedInventories." + player.getUniqueId() + "." + i, items[i]);
        }
        plugin.saveConfig();
    }

    public void clearInventory(Player player) {
        player.getInventory().clear();
    }

    public void restoreInventory(Player player) {
        if (!plugin.getConfig().contains("savedInventories." + player.getUniqueId())) return;

        ItemStack[] items = new ItemStack[player.getInventory().getSize()];
        for (int i = 0; i < items.length; i++) {
            items[i] = plugin.getConfig().getItemStack("savedInventories." + player.getUniqueId() + "." + i);
        }
        player.getInventory().setContents(items);
        plugin.getConfig().set("savedInventories." + player.getUniqueId(), null);
        plugin.saveConfig();
    }

    public void restoreAllInventories() {
        for (Player player : Bukkit.getServer().getOnlinePlayers()) {
            restoreInventory(player);
        }
    }
}

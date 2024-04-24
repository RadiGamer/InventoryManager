package org.hexa.inventorymanager.Listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

public class KitManager implements Listener {
    private JavaPlugin plugin;

    public KitManager(JavaPlugin plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    public void createKitGUI(Player player, String kitName) {
        Inventory kitInventory = Bukkit.createInventory(null, 54, "Creando el kit: " + kitName);
        player.openInventory(kitInventory);
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if (!event.getView().getTitle().startsWith("Creando el kit: ")) {
            return;
        }

        String title = event.getView().getTitle();
        String kitName = title.substring("Creando el kit: ".length());
        ItemStack[] items = event.getInventory().getContents();

        List<ItemStack> nonNullItems = Arrays.stream(items)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        plugin.getConfig().set("kits." + kitName, nonNullItems);
        plugin.saveConfig();

        Player player = (Player) event.getPlayer();
        player.sendMessage(ChatColor.GREEN + "Kit '" + kitName + "' guardado!");
    }

    public void deployKit(Player player, String kitName) {
        if (!plugin.getConfig().contains("kits." + kitName)) {
            player.sendMessage(ChatColor.RED + "Kit '" + kitName + "' no existe");
            return;
        }

        List<?> kitList = plugin.getConfig().getList("kits." + kitName);
        if (kitList == null || kitList.isEmpty()) {
            player.sendMessage(ChatColor.RED + "Kit '" + kitName + "' esta vacio o mal configurado.");
            return;
        }

        savePlayerInventory(player);

        player.getInventory().clear();
        player.getInventory().setArmorContents(new ItemStack[4]);

        ItemStack[] kitItems = new ItemStack[kitList.size()];
        for (int i = 0; i < kitList.size(); i++) {
            Object item = kitList.get(i);
            if (item instanceof ItemStack) {
                kitItems[i] = (ItemStack) item;
            }
        }

        player.getInventory().setContents(kitItems);
        player.sendMessage(ChatColor.GREEN + "Se dio el kit '" + kitName + "' correctamente!");
    }

    public void restoreAllInventories() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            try {
                reloadAndRestoreInventory(player);
            } catch (Exception e) {
                player.sendMessage(ChatColor.RED + "Hubo un error restaurando el inventario.");
                e.printStackTrace();
            }
        }
    }

    private void savePlayerInventory(Player player) {
        ItemStack[] contents = player.getInventory().getContents();
        ItemStack[] armor = player.getInventory().getArmorContents();
        plugin.getConfig().set("savedInventories." + player.getUniqueId() + ".items", contents);
        plugin.getConfig().set("savedInventories." + player.getUniqueId() + ".armor", armor);
        plugin.saveConfig();
    }

    private void reloadAndRestoreInventory(Player player) {
        UUID playerId = player.getUniqueId();
        String basePath = "savedInventories." + playerId;

        try {
            plugin.reloadConfig();

            if (!plugin.getConfig().contains(basePath)) {
                player.sendMessage(ChatColor.RED + "No hay un inventario guardado.");
                return;
            }

            List<?> itemsList = plugin.getConfig().getList(basePath + ".items");
            ItemStack[] contents = itemsList != null ? convertListToItemStackArray(itemsList, 36) : new ItemStack[36];
            player.getInventory().setContents(contents);

            List<?> armorList = plugin.getConfig().getList(basePath + ".armor");
            ItemStack[] armorContents = armorList != null ? convertListToItemStackArray(armorList, 4) : new ItemStack[4];
            player.getInventory().setArmorContents(armorContents);

            plugin.getConfig().set(basePath, null);
            plugin.saveConfig();

            player.sendMessage(ChatColor.GREEN + "Inventarios restaurados!");
        } catch (Exception e) {
            player.sendMessage(ChatColor.RED + "Hubo un error restaurando inventarios");
            e.printStackTrace();
        }
    }

    private ItemStack[] convertListToItemStackArray(List<?> list, int length) {
        ItemStack[] items = new ItemStack[length];
        for (int i = 0; i < list.size() && i < length; i++) {
            Object item = list.get(i);
            items[i] = item instanceof ItemStack ? (ItemStack) item : null;
        }
        return items;
    }
}

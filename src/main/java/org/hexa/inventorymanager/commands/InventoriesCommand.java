package org.hexa.inventorymanager.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.hexa.inventorymanager.InventoryManager;
import org.hexa.inventorymanager.Listeners.KitManager;

public class InventoriesCommand implements CommandExecutor {

    private InventoryManager plugin;
    private KitManager kitManager;

    public InventoriesCommand(InventoryManager plugin) {
        this.plugin = plugin;
        this.kitManager = new KitManager(plugin);
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Solo los jugadores pueden usar este comando crack");
            return true;
        }

        Player player = (Player) sender;
        if (args.length == 0) {
            player.sendMessage(ChatColor.RED + "Uso: /inventories <make|deploy|restore> [Nombre del kit]");
            return true;
        }

        String subcommand = args[0].toLowerCase();

        switch (subcommand) {
            case "make":
                if (args.length < 2) {
                    player.sendMessage(ChatColor.RED + "Uso: /inventories make <Nombre del Kit>");
                    return true;
                }
                kitManager.createKitGUI(player, args[1]);
                break;

            case "deploy":
                if (args.length < 2) {
                    player.sendMessage(ChatColor.RED + "Uso: /inventories deploy <Nombre del kit>");
                    return true;
                }
                kitManager.deployKit(player, args[1]);
                break;

            case "restore":
                if (args.length > 1) {
                    player.sendMessage(ChatColor.RED + "Uso: /inventories restore");
                    return true;
                }
                kitManager.restoreAllInventories();
                break;

            default:
                player.sendMessage(ChatColor.RED + "Comando invalido. Usa /inventories <make|deploy|restore> [Nombre del kit]");
                return true;
        }
        return true;
    }

}

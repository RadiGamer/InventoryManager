package org.hexa.inventorymanager.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.hexa.inventorymanager.InventoryManager;
import org.hexa.inventorymanager.Manager.InvManager;

public class RestoreInventory implements CommandExecutor {

    private InvManager invManager;

    public RestoreInventory(InventoryManager plugin){
        this.invManager = new InvManager(plugin);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("restoreinv")) {
                invManager.restoreAllInventories();
                sender.sendMessage("Inventarios restaurados!");
                return true;
        }
        return false;
    }
}

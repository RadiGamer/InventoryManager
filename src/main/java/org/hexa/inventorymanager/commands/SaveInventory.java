/*package org.hexa.inventorymanager.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.hexa.inventorymanager.InventoryManager;
import org.hexa.inventorymanager.Manager.InvManager;

public class SaveInventory implements CommandExecutor {
    private InvManager invManager;

    public SaveInventory(InventoryManager plugin) {
        this.invManager = new InvManager(plugin);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("saveinv")) {
            invManager.saveAndClearAllInventories();
            sender.sendMessage("All inventories saved and cleared!");
            return true;
        }
        return false;
    }
}
*/
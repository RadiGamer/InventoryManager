package org.hexa.inventorymanager;

import org.bukkit.plugin.java.JavaPlugin;
import org.hexa.inventorymanager.Listeners.KitManager;
import org.hexa.inventorymanager.Manager.InvManager;
import org.hexa.inventorymanager.commands.InventoriesCommand;
import org.hexa.inventorymanager.commands.RestoreInventory;

public final class InventoryManager extends JavaPlugin {
    private InvManager invManager;
    private KitManager kitManager;

    @Override
    public void onEnable() {
      // getCommand("saveinv").setExecutor(new SaveInventory(this));
        //MANAGERS
        this.invManager = new InvManager(this);
        this.kitManager = new KitManager(this);

        //COMMANDS
        getCommand("restoreinv").setExecutor(new RestoreInventory(this));
        getCommand("inventories").setExecutor(new InventoriesCommand(this));

        //LISTENERS
        getServer().getPluginManager().registerEvents(new KitManager(this), this);

       saveDefaultConfig();

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}

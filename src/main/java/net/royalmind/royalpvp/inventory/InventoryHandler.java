package net.royalmind.royalpvp.inventory;

import fr.minuskube.inv.InventoryManager;
import net.milkbowl.vault.economy.Economy;
import net.royalmind.royalpvp.data.containers.effects.EffectsContainerImpl;
import net.royalmind.royalpvp.data.containers.inventory.InventoriesContainerImpl;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class InventoryHandler implements Listener {

    private InventoryManager inventoryManager;
    private InventoriesContainerImpl inventoriesContainer;
    private EffectsContainerImpl effectsContainer;
    private InventoryStorage InventoryStorage;
    private FileConfiguration configEffects;
    private Economy economy;

    public InventoryHandler(final EffectsContainerImpl effectsContainer, final InventoriesContainerImpl inventoriesContainer,
                            final InventoryManager inventoryManager, final FileConfiguration configEffects,
                            final Economy economy) {
        this.effectsContainer = effectsContainer;
        this.inventoriesContainer = inventoriesContainer;
        this.inventoryManager = inventoryManager;
        this.configEffects = configEffects;
        this.economy = economy;
        this.InventoryStorage = new InventoryStorage(this.effectsContainer, this.inventoriesContainer, this.inventoryManager, this.economy, this.configEffects);
    }

    @EventHandler
    public void onChat(final AsyncPlayerChatEvent event) {
        final Player player = event.getPlayer();
        if(event.getMessage().equalsIgnoreCase("menu")) {
            this.InventoryStorage.getEffects().open(player);
        }
    }

}

package net.royalmind.royalpvp.inventory;

import fr.minuskube.inv.InventoryManager;
import fr.minuskube.inv.SmartInventory;
import net.royalmind.royalpvp.data.containers.effects.EffectsContainerImpl;
import net.royalmind.royalpvp.data.containers.inventory.InventoriesContainerImpl;
import net.royalmind.royalpvp.inventory.providers.EffectsProvider;
import net.royalmind.royalpvp.utils.Chat;
import org.bukkit.configuration.file.FileConfiguration;

public class InventoryStorage {

    public static final String EFFECTS_ID = "Effects_RM";

    private InventoryManager inventoryManager;
    private EffectsContainerImpl effectsContainer;
    private InventoriesContainerImpl inventoriesContainer;
    private SmartInventory effects;
    private FileConfiguration configEffects;

    public InventoryStorage(final EffectsContainerImpl effectsContainer, final InventoriesContainerImpl inventoriesContainer, final InventoryManager inventoryManager, final FileConfiguration configEffects) {
        this.effectsContainer = effectsContainer;
        this.inventoriesContainer = inventoriesContainer;
        this.inventoryManager = inventoryManager;
        this.configEffects = configEffects;
        this.configuration();
    }

    private void configuration() {
        this.effects = SmartInventory
                .builder()
                .id(EFFECTS_ID)
                .provider(new EffectsProvider(this.effectsContainer, this.inventoriesContainer, this.configEffects))
                .size(4, 9)
                .title(Chat.translate("&cEfectos de Muerte"))
                .closeable(true)
                .manager(this.inventoryManager)
                .build();
    }

    public SmartInventory getEffects() {
        return effects;
    }
}

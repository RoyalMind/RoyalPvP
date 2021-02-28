package net.royalmind.royalpvp.inventory.providers;

import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import net.royalmind.royalpvp.data.containers.effects.EffectsContainerImpl;
import net.royalmind.royalpvp.data.containers.effects.EffectsDataContainer;
import net.royalmind.royalpvp.data.containers.inventory.InventoriesContainerImpl;
import net.royalmind.royalpvp.data.containers.inventory.InventoriesDataContainer;
import net.royalmind.royalpvp.effects.EffectType;
import net.royalmind.royalpvp.utils.Chat;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class EffectsProvider implements InventoryProvider {

    private InventoriesContainerImpl inventoriesContainer;
    private EffectsContainerImpl effectsContainer;
    private FileConfiguration configEffects;

    public EffectsProvider(final EffectsContainerImpl effectsContainer, final InventoriesContainerImpl inventoriesContainer, final FileConfiguration configEffects) {
        this.effectsContainer = effectsContainer;
        this.inventoriesContainer = inventoriesContainer;
        this.configEffects = configEffects;
    }

    @Override
    public void init(final Player player, final InventoryContents contents) {
        final EffectsDataContainer effectsContainer = this.effectsContainer.get(player.getUniqueId());
        for (final EffectType type : EffectType.values()) {
            if (type == null) continue;
            player.sendMessage(type.getId());
            final ClickableItem clickableItem = ClickableItem.of(this.inventoriesContainer.getItemWithLore(type, player), inventoryClickEvent -> {
                final InventoriesDataContainer.InventoriesDataTypes inventoryDataType = this.inventoriesContainer.getInventoryDataType(type, player);
                if (inventoryDataType == null) return;
                String message = "?";
                switch (inventoryDataType) {
                    case SELECTED:
                        message = "&cYa tienes este efecto.";
                        break;
                    case HAS_PERMISSION:
                        effectsContainer.setCurrentEffect(type.getId());
                        message = "&aEfecto seleccionado.";
                        break;
                    case DONT_HAS_PERMISSION:
                        message = "&cNecesitas comprar este efecto.";
                }
                player.sendMessage(Chat.translate(message));
            });
            contents.add(clickableItem);
        }
    }

    @Override
    public void update(final Player player, final InventoryContents contents) {

    }
}

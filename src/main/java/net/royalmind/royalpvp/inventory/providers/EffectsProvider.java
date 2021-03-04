package net.royalmind.royalpvp.inventory.providers;

import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import fr.minuskube.inv.content.SlotPos;
import net.milkbowl.vault.economy.Economy;
import net.royalmind.royalpvp.data.containers.effects.EffectsContainerImpl;
import net.royalmind.royalpvp.data.containers.effects.EffectsDataContainer;
import net.royalmind.royalpvp.data.containers.inventory.InventoriesContainerImpl;
import net.royalmind.royalpvp.data.containers.inventory.InventoriesDataContainer;
import net.royalmind.royalpvp.effects.EffectType;
import net.royalmind.royalpvp.utils.Chat;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class EffectsProvider implements InventoryProvider {

    private InventoriesContainerImpl inventoriesContainer;
    private EffectsContainerImpl effectsContainer;
    private Economy economy;

    public EffectsProvider(final EffectsContainerImpl effectsContainer, final InventoriesContainerImpl inventoriesContainer,
                           final Economy economy) {
        this.effectsContainer = effectsContainer;
        this.inventoriesContainer = inventoriesContainer;
        this.economy = economy;
    }

    @Override
    public void init(final Player player, final InventoryContents contents) {
        final EffectsDataContainer effectsContainer = this.effectsContainer.get(player.getUniqueId());
        for (final EffectType type : EffectType.values()) {
            if (type == null) continue;
            final ClickableItem clickableItem = ClickableItem.of(this.inventoriesContainer.getItemWithLore(type, player), inventoryClickEvent -> {
                final InventoriesDataContainer.InventoriesDataTypes inventoryDataType = this.inventoriesContainer.getInventoryDataType(type, player);
                if (inventoryDataType == null) return;
                final InventoriesDataContainer inventoriesDataContainer = this.inventoriesContainer.get(type);
                String message = null;
                if (type != EffectType.NONE) {
                    switch (inventoryDataType) {
                        case SELECTED:
                            message = "&cYa tienes este efecto.";
                            break;
                        case HAS_PERMISSION:
                            effectsContainer.setCurrentEffect(type.getId());
                            message = "&aEfecto seleccionado.";
                            break;
                        case DONT_HAS_PERMISSION:
                            buy(player, inventoriesDataContainer);
                            break;
                        case DONT_HAS_PERMISSION_NEEDED:
                            message = "&cNo tienes permisos necesarios para esto.";
                    }
                } else {
                    effectsContainer.setCurrentEffect(EffectType.NONE.getId());
                    message = "&cNingun efecto seleccionado!";
                }
                if (message != null) {
                    player.sendMessage(Chat.translate(message));
                }
                player.closeInventory();
            });
            final SlotPos slot = this.inventoriesContainer.get(type).getSlot();
            contents.set(slot, clickableItem);
        }
    }

    @Override
    public void update(final Player player, final InventoryContents contents) {

    }

    private void buy(final Player player, final InventoriesDataContainer inventoriesDataContainer) {
        final double balance = this.economy.getBalance(player);
        final double price = inventoriesDataContainer.getPrice();
        final String command = inventoriesDataContainer.getCommand().replaceAll("%player%", player.getName());
        final String displayName = inventoriesDataContainer.getItem().getItemMeta().getDisplayName();
        final String message;
        if (balance < price) {
            message = "&cNo tienes dinero suficiente para comprar &6" + displayName;
        } else {
            this.economy.withdrawPlayer(player, price);
            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), command);
            message = "&eHas comprado &6" + displayName;
        }
        player.sendMessage(Chat.translate(message));
    }
}

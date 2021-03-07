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
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class EffectsProvider implements InventoryProvider {

    private InventoriesContainerImpl inventoriesContainer;
    private EffectsContainerImpl effectsContainer;
    private Economy economy;
    private FileConfiguration configEffects;

    public EffectsProvider(final EffectsContainerImpl effectsContainer, final InventoriesContainerImpl inventoriesContainer,
                           final Economy economy, final FileConfiguration configEffects) {
        this.effectsContainer = effectsContainer;
        this.inventoriesContainer = inventoriesContainer;
        this.economy = economy;
        this.configEffects = configEffects;
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
        //Configurations
        ClickableItem configItem = ClickableItem.of(getConfigurationItem(effectsContainer, "Sounds"), inventoryClickEvent -> {
            effectsContainer.setEnableSounds(!(effectsContainer.isEnableSounds()));
            final ItemStack item = getConfigurationItem(effectsContainer, "Sounds");
            inventoryClickEvent.setCurrentItem(item);
        });
        contents.set(getConfigurationSlot("Sounds"), configItem);

        configItem = ClickableItem.of(getConfigurationItem(effectsContainer, "Particles"), inventoryClickEvent -> {
            effectsContainer.setEnableParticles(!(effectsContainer.isEnableParticles()));
            final ItemStack item = getConfigurationItem(effectsContainer, "Particles");
            inventoryClickEvent.setCurrentItem(item);
        });
        contents.set(getConfigurationSlot("Particles"), configItem);
    }

    @Override
    public void update(final Player player, final InventoryContents contents) { }

    private SlotPos getConfigurationSlot(final String path) {
        final int row = this.configEffects.getInt("Configurations." + path + ".Slot.Row");
        final int column = this.configEffects.getInt("Configurations." + path + ".Slot.Column");
        return new SlotPos(row, column);
    }

    private ItemStack getConfigurationItem(final EffectsDataContainer effectContainer, final String path) {
        final String enable = "Enable";
        final String disable = "Disable";
        final String togglePath = (path.equalsIgnoreCase("sounds")) ?
                (effectContainer.isEnableSounds()) ? enable : disable :
                (effectContainer.isEnableParticles()) ? enable : disable;
        final String name = this.configEffects.getString("Configurations." + path + "." + togglePath + ".Name");
        final String icon = this.configEffects.getString("Configurations." + path + "." + togglePath + ".Icon");
        final int iconData = this.configEffects.getInt("Configurations." + path + "." + togglePath + ".IconData");
        final ItemStack itemStack = new ItemStack(Material.getMaterial(icon), 1, ((short) iconData));
        final ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(Chat.translate(name));
        itemStack.setItemMeta(itemMeta);
        return itemStack;
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

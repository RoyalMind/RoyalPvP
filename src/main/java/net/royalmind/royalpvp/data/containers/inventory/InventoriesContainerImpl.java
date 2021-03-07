package net.royalmind.royalpvp.data.containers.inventory;

import fr.minuskube.inv.content.SlotPos;
import net.royalmind.royalpvp.RoyalPvP;
import net.royalmind.royalpvp.data.containers.AbstractDataMap;
import net.royalmind.royalpvp.data.containers.effects.EffectsDataContainer;
import net.royalmind.royalpvp.effects.EffectType;
import net.royalmind.royalpvp.utils.Chat;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class InventoriesContainerImpl extends AbstractDataMap<EffectType, InventoriesDataContainer> {

    final RoyalPvP plugin;
    final FileConfiguration configEffects;

    public InventoriesContainerImpl(final RoyalPvP plugin, final FileConfiguration configEffects) {
        this.plugin = plugin;
        this.configEffects = configEffects;
        load();
}

    private void load() {
        String rootPath = "Effects.Kills";
        final ConfigurationSection configurationSection = this.configEffects.getConfigurationSection(rootPath);
        for (final String key : configurationSection.getKeys(false)) {
            rootPath = "Effects.Kills." + key + ".";
            final ItemStack item = this.buildItemStack(rootPath);
            final String idEffect = this.configEffects.getString(rootPath + "ID");
            final String permissionEffect = this.configEffects.getString(rootPath + "Permission");
            final int itemRow = this.configEffects.getInt(rootPath + "Slot.Row");
            final int itemColum = this.configEffects.getInt(rootPath + "Slot.Column");
            final SlotPos slotPos = new SlotPos(itemRow, itemColum);
            final double price = this.configEffects.getDouble(rootPath + "Buy.Price");
            final String permissionNeeded = this.configEffects.getString(rootPath + "Buy.Need");
            final String command = this.configEffects.getString(rootPath + "Buy.Command");
            final InventoriesDataContainer invDataContainer = new InventoriesDataContainer(
                    idEffect, permissionEffect, item, slotPos, price, permissionNeeded, command
            );
            List<String> currentLore;
            currentLore = this.configEffects.getStringList(rootPath + "Lore.HasPermission");
            invDataContainer.set(InventoriesDataContainer.InventoriesDataTypes.HAS_PERMISSION, currentLore);
            currentLore = this.configEffects.getStringList(rootPath + "Lore.DontHasPermission");
            invDataContainer.set(InventoriesDataContainer.InventoriesDataTypes.DONT_HAS_PERMISSION, currentLore);
            currentLore = this.configEffects.getStringList(rootPath + "Lore.Selected");
            invDataContainer.set(InventoriesDataContainer.InventoriesDataTypes.SELECTED, currentLore);;
            currentLore = this.configEffects.getStringList(rootPath + "Lore.DontHasPermissionNeeded");
            invDataContainer.set(InventoriesDataContainer.InventoriesDataTypes.DONT_HAS_PERMISSION_NEEDED, currentLore);
            set(
                    EffectType.getByID(idEffect), invDataContainer
            );
        }
    }

    private ItemStack buildItemStack(final String rootPath) {
        final ItemStack itemStack = new ItemStack(
                Material.valueOf(this.configEffects.getString(rootPath + "Icon")),
                this.configEffects.getInt(rootPath + "Amount")
        );
        final ItemMeta itemMeta = itemStack.getItemMeta();
        final String itemName = this.configEffects.getString(rootPath + "Name");
        itemMeta.setDisplayName(Chat.translate(itemName));
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public ItemStack getItemWithLore(final EffectType effectType, final Player player) {
        final InventoriesDataContainer invDataContainer = get(effectType);
        final List<String> lore = invDataContainer.get(getInventoryDataType(effectType, player));
        return setLore(invDataContainer.getItem(), lore, player);
    }

    public InventoriesDataContainer.InventoriesDataTypes getInventoryDataType(final EffectType effectType, final Player player) {
        final EffectsDataContainer effectsDataContainer = this.plugin.getEffectsContainer().get(player.getUniqueId());
        final InventoriesDataContainer invDataContainer = get(effectType);
        if (invDataContainer == null) return null;
        final boolean hasPermission = player.hasPermission(invDataContainer.getPermission());
        final String needPermission = invDataContainer.getPermissionNeeded();
        final boolean hasPermissionNeeded = player.hasPermission(needPermission);
        if (!(hasPermissionNeeded) && !(needPermission.equalsIgnoreCase("none"))) {
            return InventoriesDataContainer.InventoriesDataTypes.DONT_HAS_PERMISSION_NEEDED;
        }
        if (hasPermission) {
            if (EffectType.getByID(effectsDataContainer.getCurrentEffect()) != effectType) {
                return InventoriesDataContainer.InventoriesDataTypes.HAS_PERMISSION;
            } else {
                return InventoriesDataContainer.InventoriesDataTypes.SELECTED;
            }
        } else {
            return InventoriesDataContainer.InventoriesDataTypes.DONT_HAS_PERMISSION;
        }
    }

    private ItemStack setLore(final ItemStack itemStack, final List<String> lore, final Player player) {
        final ItemMeta itemMeta = itemStack.getItemMeta();
        final List<String> subLore = new ArrayList<>();
        String text;
        for (final String str : lore) {
            text = Chat.translate(str)
                    .replaceAll("%player%", player.getName()
            );
            subLore.add(text);
        }
        itemMeta.setLore(subLore);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
}
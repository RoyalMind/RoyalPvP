package net.royalmind.royalpvp.data.containers.inventory;

import fr.minuskube.inv.content.SlotPos;
import net.royalmind.royalpvp.data.containers.AbstractDataMap;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class InventoriesDataContainer extends AbstractDataMap<InventoriesDataContainer.InventoriesDataTypes, List<String>> {

    private final String id;
    private final String permission;
    private final ItemStack item;
    private final SlotPos slotPos;
    private final double price;
    private final String permissionNeeded;
    private final String command;

    public InventoriesDataContainer(final String id, final String permission, final ItemStack item, final SlotPos slotPos,
                                    final double price, final String permissionNeeded, final String command) {
        this.id = id;
        this.permission = permission;
        this.item = item;
        this.slotPos = slotPos;
        this.price = price;
        this.permissionNeeded = permissionNeeded;
        this.command = command;
    }

    public enum InventoriesDataTypes {
        HAS_PERMISSION, DONT_HAS_PERMISSION, SELECTED, DONT_HAS_PERMISSION_NEEDED;
    }

    public String getId() {
        return id;
    }

    public String getPermission() {
        return permission;
    }

    public ItemStack getItem() {
        return item;
    }

    public SlotPos getSlot() {
        return slotPos;
    }

    public double getPrice() {
        return price;
    }

    public SlotPos getSlotPos() {
        return slotPos;
    }

    public String getPermissionNeeded() {
        return permissionNeeded;
    }

    public String getCommand() {
        return command;
    }
}
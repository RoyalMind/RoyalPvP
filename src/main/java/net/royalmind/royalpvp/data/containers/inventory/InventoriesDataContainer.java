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

    public InventoriesDataContainer(final String id, final String permission, final ItemStack item, final SlotPos slotPos) {
        this.id = id;
        this.permission = permission;
        this.item = item;
        this.slotPos = slotPos;
    }

    public enum InventoriesDataTypes {
        HAS_PERMISSION, DONT_HAS_PERMISSION, SELECTED;
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

    @Override
    public String toString() {
        return "InventoriesDataContainer{" +
                "id='" + id + '\'' +
                ", permission='" + permission + '\'' +
                ", item=" + item +
                '}';
    }
}
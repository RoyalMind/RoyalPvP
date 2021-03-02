package net.royalmind.royalpvp.effects.types;

import net.royalmind.royalpvp.utils.Chat;
import net.royalmind.royalpvp.utils.PlayersData;
import net.royalmind.royalpvp.utils.Sounds;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import xyz.xenondevs.particle.ParticleEffect;

public class SkullEffect extends AbstractEffect {

    public SkullEffect(final Player player, final JavaPlugin plugin, final PlayersData playersData) {
        super(player, plugin, playersData);
    }

    @Override
    public void run() {
        final String playerName = getPlayer().getName();
        final Location location = getPlayer().getLocation();
        ParticleEffect.FIREWORKS_SPARK.display(location, getPlayersData().getPlayersHave(PlayersData.DataType.PARTICLES));
        for (final Player player : getPlayersData().getPlayersHave(PlayersData.DataType.SOUNDS)) {
            player.playSound(location, Sounds.FIREWORK_LAUNCH.bukkitSound(), 0.5f, 1f);
        }
        final ItemStack skull = getSkull(playerName);
        final Item item = location.getWorld().dropItemNaturally(location, skull);
        item.setPickupDelay(Integer.MAX_VALUE);
        item.setCustomNameVisible(true);
        item.setCustomName(Chat.translate("&cRIP - " + playerName));
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!(item.isEmpty()) || !(item.isDead())) item.remove();
                cancel();
            }
        }.runTaskLater(getPlugin(), 20*5L);
    }

    private ItemStack getSkull(final String name) {
        final ItemStack itemStack = new ItemStack(Material.SKULL_ITEM, 1, ((short) SkullType.PLAYER.ordinal()));
        final SkullMeta skullMeta = (SkullMeta) itemStack.getItemMeta();
        skullMeta.setOwner(name);
        itemStack.setItemMeta(skullMeta);
        return itemStack;
    }
}

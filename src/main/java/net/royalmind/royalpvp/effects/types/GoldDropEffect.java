package net.royalmind.royalpvp.effects.types;

import net.royalmind.royalpvp.utils.Chat;
import net.royalmind.royalpvp.utils.PlayersData;
import net.royalmind.royalpvp.utils.Sounds;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import xyz.xenondevs.particle.ParticleEffect;

import java.util.ArrayList;
import java.util.List;

public class GoldDropEffect extends AbstractEffect {

    public GoldDropEffect(final Player player, final JavaPlugin plugin, final PlayersData playersData) {
        super(player, plugin, playersData);
    }

    @Override
    public void run() {
        final ItemStack itemStack = new ItemStack(Material.GOLD_INGOT);
        final String playerName = getPlayer().getName();
        new BukkitRunnable() {
            final List<Item> items = new ArrayList<>();
            int time = 5;
            @Override
            public void run() {
                final Location location = getPlayer().getLocation();
                for (final Player player : getPlayersData().getPlayersHave(PlayersData.DataType.SOUNDS)) {
                    player.playSound(location, Sounds.SUCCESSFUL_HIT.bukkitSound(), 1, 2f);
                }
                final Item item = location.getWorld().dropItemNaturally(location.add(0, 2, 0), itemStack);
                item.setPickupDelay(Integer.MAX_VALUE);
                item.setCustomName(Chat.translate("&6Oro - " + playerName));
                item.setCustomNameVisible(true);
                item.setVelocity(new Vector(0, 0.5, 0));
                items.add(item);
                if (time <= 0) {
                    for (final Item drop : items) {
                        if (!(drop.isDead()) || !(drop.isEmpty())) {
                            final Location dropLocation = drop.getLocation();
                            for (final Player player : getPlayersData().getPlayersHave(PlayersData.DataType.SOUNDS)) {
                                player.playSound(dropLocation, Sounds.CLICK.bukkitSound(), 1, 1f);
                            }
                            ParticleEffect.LAVA.display(dropLocation, getPlayersData().getPlayersHave(PlayersData.DataType.PARTICLES));
                            drop.remove();
                        }
                    }
                    items.clear();
                    cancel();
                    return;
                }
                time--;
            }
        }.runTaskTimer(getPlugin(), 0L, 20L);
    }
}

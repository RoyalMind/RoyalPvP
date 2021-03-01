package net.royalmind.royalpvp.effects.types;

import net.royalmind.royalpvp.utils.PlayersData;
import net.royalmind.royalpvp.utils.Sounds;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.entity.Squid;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import xyz.xenondevs.particle.ParticleEffect;

public class SquidEffect extends AbstractEffect {

    public SquidEffect(final Player player, final JavaPlugin plugin, final PlayersData playersData) {
        super(player, plugin, playersData);
    }

    @Override
    public void run() {
        final World world = getPlayer().getWorld();
        final Location location = getPlayer().getLocation();
        final ArmorStand armorStand = world.spawn(location, ArmorStand.class);
        armorStand.setVisible(false);
        armorStand.setGravity(false);
        final Squid squid = world.spawn(location, Squid.class);
        squid.setMaxHealth(2000);
        squid.setHealth(2000);
        armorStand.setPassenger(squid);
        new BukkitRunnable() {
            float time = 2f;
            @Override
            public void run() {
                //Bug with passengers
                armorStand.eject();
                armorStand.teleport(armorStand.getLocation().add(0, 0.2, 0));
                armorStand.setPassenger(squid);
                if (time <= 2) {
                    for (final Player player : getPlayersData().getPlayersHave(PlayersData.DataType.SOUNDS)) {
                        player.playSound(armorStand.getLocation(), Sounds.ITEM_PICKUP.bukkitSound(), 1, Math.abs(3 - time));
                    }
                }
                time -= 0.1;
                if (time <= 0 || squid.isDead() || armorStand.isDead()) {
                    cancel();
                    squid.remove();
                    armorStand.remove();
                    ParticleEffect.LAVA.display(armorStand.getLocation(),
                            0, 0, 0, 0, 25, null, getPlayersData().getPlayersHave(PlayersData.DataType.PARTICLES));
                    for (final Player player : getPlayersData().getPlayersHave(PlayersData.DataType.SOUNDS)) {
                        player.playSound(armorStand.getLocation(), Sounds.EXPLODE.bukkitSound(), 1, 1);
                    }
                    return;
                }
                ParticleEffect.FIREWORKS_SPARK.display(armorStand.getLocation(), getPlayersData().getPlayersHave(PlayersData.DataType.PARTICLES));
            }
        }.runTaskTimer(this.getPlugin(), 0L, 1L);
    }

}

package net.royalmind.royalpvp.effects.types;

import net.royalmind.royalpvp.utils.PlayersData;
import net.royalmind.royalpvp.utils.Sounds;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import xyz.xenondevs.particle.ParticleEffect;

public class FireEffect extends AbstractEffect {

    public FireEffect(final Player player, final JavaPlugin plugin, final PlayersData playersData) {
        super(player, plugin, playersData);
    }

    @Override
    public void run() {
        new BukkitRunnable() {
            int time = 5;
            @Override
            public void run() {
                final Location location = getPlayer().getLocation().add(0, 2, 0);
                for (final Player player : getPlayersData().getPlayersHave(PlayersData.DataType.SOUNDS)) {
                    player.playSound(location, Sounds.GHAST_FIREBALL.bukkitSound(), 0.3f, 1f);
                }
                ParticleEffect.FLAME.display(location, getPlayersData().getPlayersHave(PlayersData.DataType.PARTICLES));
                ParticleEffect.LAVA.display(location, new Vector(0.2, 1, 0.2), 0.35f, 15, null, getPlayersData().getPlayersHave(PlayersData.DataType.PARTICLES));
                ParticleEffect.SMOKE_NORMAL.display(location, getPlayersData().getPlayersHave(PlayersData.DataType.PARTICLES));
                if (time <= 0) {
                    cancel();
                    return;
                }
                time--;
            }
        }.runTaskTimer(getPlugin(), 0L, 20L);
    }
}

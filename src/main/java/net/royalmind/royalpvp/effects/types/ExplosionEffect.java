package net.royalmind.royalpvp.effects.types;

import net.royalmind.royalpvp.utils.PlayersData;
import net.royalmind.royalpvp.utils.Sounds;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;
import xyz.xenondevs.particle.ParticleEffect;

public class ExplosionEffect extends AbstractEffect {

    public ExplosionEffect(final Player player, final JavaPlugin plugin, final PlayersData playersData) {
        super(player, plugin, playersData);
    }

    @Override
    public void run() {
        final Location location = getPlayer().getLocation().add(0, 2, 0);
        ParticleEffect.CLOUD.display(location, new Vector(), 0.2f, 15, null, getPlayersData().getPlayersHave(PlayersData.DataType.PARTICLES));
        for (final Player player : getPlayersData().getPlayersHave(PlayersData.DataType.SOUNDS)) {
            player.playSound(location, Sounds.EXPLODE.bukkitSound(), 0.5f, 1f);
        }
    }
}

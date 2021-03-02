package net.royalmind.royalpvp.effects.types;

import net.royalmind.royalpvp.utils.Chat;
import net.royalmind.royalpvp.utils.PlayersData;
import net.royalmind.royalpvp.utils.Sounds;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import xyz.xenondevs.particle.ParticleEffect;

public class TNTEffect extends AbstractEffect {

    public TNTEffect(final Player player, final JavaPlugin plugin, final PlayersData playersData) {
        super(player, plugin, playersData);
    }

    @Override
    public void run() {
        final Location location = getPlayer().getLocation();
        final TNTPrimed tntPrimed = location.getWorld().spawn(location, TNTPrimed.class);
        tntPrimed.setFuseTicks(Integer.MAX_VALUE);
        tntPrimed.setCustomNameVisible(true);
        tntPrimed.setCustomName(Chat.translate("&c&l¡¡BOOOOM!!"));
        tntPrimed.setVelocity(new Vector(0, 1.5, 0));
        for (final Player player : getPlayersData().getPlayersHave(PlayersData.DataType.SOUNDS)) {
            player.playSound(location, Sounds.FIREWORK_LAUNCH.bukkitSound(), 1, 1f);
        }
        ParticleEffect.FIREWORKS_SPARK.display(location.clone().add(0, 2, 0), new Vector(), 0.2f, 5, null,
                getPlayersData().getPlayersHave(PlayersData.DataType.PARTICLES));
        new BukkitRunnable() {
            @Override
            public void run() {
                Location tntLocation = null;
                if (!(tntPrimed.isDead()) || !(tntPrimed.isEmpty())) {
                    tntLocation = tntPrimed.getLocation();
                    tntPrimed.remove();
                }
                for (final Player player : getPlayersData().getPlayersHave(PlayersData.DataType.SOUNDS)) {
                    player.playSound(tntLocation, Sounds.EXPLODE.bukkitSound(), 0.5f, 1f);
                }
                ParticleEffect.CLOUD.display(location.clone().add(0, 2, 0), new Vector(), 0.2f, 15, null,
                        getPlayersData().getPlayersHave(PlayersData.DataType.PARTICLES));
            }
        }.runTaskLater(getPlugin(), 20*3);
    }
}

package net.royalmind.royalpvp.effects.types;

import net.royalmind.royalpvp.utils.Chat;
import net.royalmind.royalpvp.utils.PlayersData;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import xyz.xenondevs.particle.ParticleEffect;

public class RektEffect extends AbstractEffect {

    public RektEffect(final Player player, final JavaPlugin plugin, final PlayersData playersData) {
        super(player, plugin, playersData);
    }

    @Override
    public void run() {
        final String playerName = getPlayer().getName();
        final Location location = getPlayer().getLocation();
        final ArmorStand armorStand = location.getWorld().spawn(location.clone().add(0, 1, 0), ArmorStand.class);
        armorStand.setVisible(false);
        armorStand.setSmall(true);
        armorStand.setGravity(false);
        armorStand.setCustomName(Chat.translate("&b" + playerName + " &efue #rekteado a aquí"));
        armorStand.setCustomNameVisible(true);
        new BukkitRunnable() {
            int time = 5;
            @Override
            public void run() {
                if (time <= 0) {
                    ParticleEffect.SMOKE_NORMAL.display(armorStand.getLocation().clone().add(0, 1, 0),
                            getPlayersData().getPlayersHave(PlayersData.DataType.PARTICLES));
                    armorStand.remove();
                    cancel();
                    return;
                }
                time--;
            }
        }.runTaskTimer(getPlugin(), 0L, 20L);
    }
}

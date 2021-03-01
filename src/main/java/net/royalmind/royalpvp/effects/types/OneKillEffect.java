package net.royalmind.royalpvp.effects.types;

import net.royalmind.royalpvp.utils.Chat;
import net.royalmind.royalpvp.utils.PlayersData;
import net.royalmind.royalpvp.utils.RandomLocation;
import net.royalmind.royalpvp.utils.Sounds;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class OneKillEffect extends AbstractEffect {

    public OneKillEffect(final Player player, final JavaPlugin plugin, final PlayersData playersData) {
        super(player, plugin, playersData);
    }

    @Override
    public void run() {
        new BukkitRunnable() {
            int time = 15;
            @Override
            public void run() {
                final Location location = getPlayer().getLocation();
                for (final Player player : getPlayersData().getPlayersHave(PlayersData.DataType.SOUNDS)) {
                    player.playSound(location, Sounds.SUCCESSFUL_HIT.bukkitSound(), 0.5f, 1.5f);
                }
                generateArmortand(location.clone().add(
                        RandomLocation.randomBetween(-1f, 1f),
                        0,
                        RandomLocation.randomBetween(-1f, 1f)
                ));
                if (time <= 0) {
                    cancel();
                    return;
                }
                time--;
            }
        }.runTaskTimer(this.getPlugin(), 0L, 5L);
    }

    private void generateArmortand(final Location location) {
        final World world = location.getWorld();
        final ArmorStand armorStand = world.spawn(location.clone().subtract(0, 0.9, 0), ArmorStand.class);
        armorStand.setVisible(false);
        armorStand.setGravity(false);
        armorStand.setSmall(true);
        armorStand.setCustomNameVisible(true);
        armorStand.setCustomName(Chat.translate("&a+1 Kill"));
        new BukkitRunnable() {
            float time = 3;
            @Override
            public void run() {
                armorStand.teleport(armorStand.getLocation().add(0, 0.2, 0));
                if (time <= 0 || armorStand.isDead()) {
                    cancel();
                    armorStand.remove();
                    return;
                }
                time -= 0.1;
            }
        }.runTaskTimer(this.getPlugin(), 0L, 1L);
    }
}

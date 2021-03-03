package net.royalmind.royalpvp.effects.types;

import net.royalmind.royalpvp.utils.PlayersData;
import net.royalmind.royalpvp.utils.Sounds;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;
import xyz.xenondevs.particle.ParticleEffect;

public class SwordEffect extends AbstractEffect {

    public SwordEffect(final Player player, final JavaPlugin plugin, final PlayersData playersData) {
        super(player, plugin, playersData);
    }

    @Override
    public void run() {
        final Location location = getPlayer().getLocation();
        final ArmorStand armorStand = location.getWorld().spawn(location.clone().add(0, 5, 0), ArmorStand.class);
        armorStand.setVisible(false);
        armorStand.setSmall(true);
        armorStand.setGravity(false);
        armorStand.setArms(true);
        armorStand.setItemInHand(new ItemStack(Material.DIAMOND_SWORD));
        armorStand.setRightArmPose(new EulerAngle(-0.5, 5, 2));
        new BukkitRunnable() {
            final Location location = getPlayer().getLocation();
            int time = 50;
            boolean down = true;
            boolean wait = false;
            @Override
            public void run() {
                if (down) {
                    armorStand.teleport(armorStand.getLocation().subtract(0, 0.5, 0));
                }
                if (armorStand.getLocation().getBlock().getType() != Material.AIR && down) {
                    armorStand.teleport(armorStand.getLocation().subtract(0, 0.5, 0));
                    down = false;
                }
                if (!down && !wait) {
                    wait = true;
                    for (final Player player : getPlayersData().getPlayersHave(PlayersData.DataType.SOUNDS)) {
                        player.playSound(location, Sounds.ANVIL_LAND.bukkitSound(), 0.3f, 1f);
                    }
                    ParticleEffect.SMOKE_LARGE.display(location, new Vector(0, 0.2, 0), 0.35f, 15, null,
                            getPlayersData().getPlayersHave(PlayersData.DataType.PARTICLES));
                }
                if (time <= 0) {
                    ParticleEffect.FLAME.display(location, new Vector(0, 0.2, 0), 0.35f, 15, null,
                            getPlayersData().getPlayersHave(PlayersData.DataType.PARTICLES));
                    armorStand.remove();
                    cancel();
                    return;
                }
                time -= 0.1;
            }
        }.runTaskTimer(getPlugin(), 0L, 1L);
    }
}

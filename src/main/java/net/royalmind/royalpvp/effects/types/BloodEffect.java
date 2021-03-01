package net.royalmind.royalpvp.effects.types;

import net.royalmind.royalpvp.utils.PlayersData;
import net.royalmind.royalpvp.utils.Sounds;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.xenondevs.particle.ParticleEffect;
import xyz.xenondevs.particle.data.texture.ItemTexture;

public class BloodEffect extends AbstractEffect {

    public BloodEffect(final Player player, final JavaPlugin plugin, final PlayersData playersData) {
        super(player, plugin, playersData);
    }

    @Override
    public void run() {
        final Location location = this.getPlayer().getLocation();
        ParticleEffect.ITEM_CRACK.display(
                location.add(0, 1, 0),
                0, 0, 0, 0.1f, 50,
                new ItemTexture(new ItemStack(Material.REDSTONE_BLOCK)),
                getPlayersData().getPlayerHaveEnableParticles()
        );
        for (final Player player : getPlayersData().getPlayerHaveEnableSounds()) {
            player.playSound(location, Sounds.IRONGOLEM_HIT.bukkitSound(), 1, 0.7f);
        }
    }

}

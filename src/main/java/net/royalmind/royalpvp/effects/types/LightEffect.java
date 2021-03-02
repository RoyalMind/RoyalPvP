package net.royalmind.royalpvp.effects.types;

import net.royalmind.royalpvp.utils.PlayersData;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class LightEffect extends AbstractEffect {

    public LightEffect(final Player player, final JavaPlugin plugin, final PlayersData playersData) {
        super(player, plugin, playersData);
    }

    @Override
    public void run() {
        getPlayer().getWorld().strikeLightningEffect(getPlayer().getLocation());
    }
}

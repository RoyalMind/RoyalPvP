package net.royalmind.royalpvp.effects.types;


import net.royalmind.royalpvp.utils.PlayersData;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class AbstractEffect {

    private final Player player;
    private final JavaPlugin plugin;
    private final PlayersData playersData;

    public AbstractEffect(final Player player, final JavaPlugin plugin, final PlayersData playersData) {
        this.player = player;
        this.plugin = plugin;
        this.playersData = playersData;
    }

    public void run() { }

    public Player getPlayer() {
        return player;
    }

    public JavaPlugin getPlugin() {
        return plugin;
    }

    public PlayersData getPlayersData() {
        return playersData;
    }
}

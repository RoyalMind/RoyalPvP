package net.royalmind.royalpvp.effects.types;


import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class AbstractEffect {

    private final Player player;
    private final JavaPlugin plugin;

    public AbstractEffect(final Player player, final JavaPlugin plugin) {
        this.player = player;
        this.plugin = plugin;
    }

    public void run() { }

    public Player getPlayer() {
        return player;
    }

    public JavaPlugin getPlugin() {
        return plugin;
    }
}

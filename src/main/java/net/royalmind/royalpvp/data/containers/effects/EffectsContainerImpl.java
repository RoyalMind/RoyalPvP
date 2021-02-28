package net.royalmind.royalpvp.data.containers.effects;

import net.royalmind.royalpvp.RoyalPvP;
import net.royalmind.royalpvp.data.containers.AbstractDataMap;
import net.royalmind.royalpvp.data.containers.threads.ThreadsContainerImpl;
import net.royalmind.royalpvp.data.containers.threads.ThreadsDataContainer;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.PreparedStatement;
import java.util.UUID;

public class EffectsContainerImpl extends AbstractDataMap<UUID, EffectsDataContainer> {

    private JavaPlugin plugin;
    private FileConfiguration config;
    private ThreadsContainerImpl threadsContainer;

    public EffectsContainerImpl(final JavaPlugin plugin, final FileConfiguration config, final ThreadsContainerImpl threadsContainer) {
        this.plugin = plugin;
        this.config = config;
        this.threadsContainer = threadsContainer;
    }

    public void sendUpdateToDatabase(final UUID uuid) {
        final String mainTable = this.config.getString("DataBase.Tables.Main");
        final String effectsTable = this.config.getString("DataBase.Tables.Effects");
        final RoyalPvP royalPvP = (RoyalPvP) plugin;
        new BukkitRunnable() {
            @Override
            public void run() {
                royalPvP.getDataSource().execute(conn -> {
                    final String query = "CALL updateEffectsData(?, ?, ?, ?, ?, ?);";
                    final PreparedStatement updateSP = conn.prepareStatement(query);
                    final EffectsDataContainer effectsDataContainer = get(uuid);
                    if (effectsDataContainer == null) return null;
                    updateSP.setString(1, effectsDataContainer.getCurrentEffect());
                    updateSP.setBoolean(2, effectsDataContainer.isEnableParticles());
                    updateSP.setBoolean(3, effectsDataContainer.isEnableSounds());
                    updateSP.setString(4, effectsDataContainer.getUUID().toString());
                    updateSP.setString(5, mainTable);
                    updateSP.setString(6, effectsTable);
                    updateSP.execute();
                    return null;
                });
            }
        }.runTaskAsynchronously(plugin);
    }

    public void close(final UUID uuid) {
        sendUpdateToDatabase(uuid);
        remove(uuid);
    }

    public void closeAndKeep(final UUID uuid) {
        final int minutes = this.config.getInt("Data.Keep.Time-Minutes");
        final int time = (20 * 60) * minutes;
        sendUpdateToDatabase(uuid);
        if (this.threadsContainer.contains(uuid)) return;
        final BukkitRunnable runnable = new BukkitRunnable() {
            @Override
            public void run() {
                final Player player = Bukkit.getPlayer(uuid);
                if (player != null) return;
                remove(uuid);
                if (!(threadsContainer.contains(uuid))) return;
                threadsContainer.remove(uuid);
            }
        };
        runnable.runTaskLaterAsynchronously(this.plugin, time);
        this.threadsContainer.set(uuid, new ThreadsDataContainer(runnable));
    }
}

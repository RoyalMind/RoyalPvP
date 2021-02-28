package net.royalmind.royalpvp.data;

import net.royalmind.royalpvp.RoyalPvP;
import net.royalmind.royalpvp.data.containers.effects.EffectsContainerImpl;
import net.royalmind.royalpvp.data.containers.effects.EffectsDataContainer;
import net.royalmind.royalpvp.data.containers.threads.ThreadsContainerImpl;
import net.royalmind.royalpvp.data.containers.threads.ThreadsDataContainer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.UUID;

public class DataHandler implements Listener {

    private FileConfiguration config;
    private RoyalPvP plugin;

    public DataHandler(final FileConfiguration config, final RoyalPvP plugin) {
        this.config = config;
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(final PlayerJoinEvent event) {
        final Player player = event.getPlayer();
        if (this.plugin.getEffectsContainer().get(player.getUniqueId()) != null) return;
        final String mainTable = this.config.getString("DataBase.Tables.Main");
        final String effectsTable = this.config.getString("DataBase.Tables.Effects");
        new BukkitRunnable() {
            @Override
            public void run() {
                plugin.getDataSource().execute(conn -> {
                    final String playerName = player.getName();
                    final String playerUUIDStr = player.getUniqueId().toString();
                    final UUID playerUUID = player.getUniqueId();
                    String query = "CALL registerPlayerSP(?, ?, ?, ?);";
                    final PreparedStatement registerSP = conn.prepareStatement(query);
                    registerSP.setString(1, playerName);
                    registerSP.setString(2, playerUUIDStr);
                    registerSP.setString(3, mainTable);
                    registerSP.setString(4, effectsTable);
                    registerSP.executeQuery();

                    query = "CALL getEffectsData(?, ?, ?, ?)";
                    final PreparedStatement getDataSP = conn.prepareStatement(query);
                    getDataSP.setString(1, "currentEffect, enableParticles, enableSounds");
                    getDataSP.setString(2, playerUUIDStr);
                    getDataSP.setString(3, mainTable);
                    getDataSP.setString(4, effectsTable);

                    final ResultSet resultSet = getDataSP.executeQuery();
                    if (!(resultSet.next())) return null;
                    final String currentEffect = resultSet.getString("currentEffect");
                    final boolean enableParticles = resultSet.getBoolean("enableParticles");
                    final boolean enableSounds = resultSet.getBoolean("enableSounds");
                    plugin.getEffectsContainer().set(
                            playerUUID,
                            new EffectsDataContainer(
                                    playerUUID,
                                    currentEffect,
                                    enableParticles,
                                    enableSounds
                            )
                    );
                    return null;
                });
            }
        }.runTaskAsynchronously(this.plugin);
    }

    @EventHandler
    public void onPlayerQuit(final PlayerQuitEvent event) {
        final UUID playerUUID = event.getPlayer().getUniqueId();
        final EffectsContainerImpl effectsContainer = plugin.getEffectsContainer();
        final boolean isKeepEnable = this.config.getBoolean("Data.Keep.Enable");
        if (isKeepEnable) {
            effectsContainer.closeAndKeep(playerUUID);
        } else {
            effectsContainer.close(playerUUID);
        }
    }

    @EventHandler
    public void onPlayerJoinCheckThread(final PlayerJoinEvent event) {
        final Player player = event.getPlayer();
        final UUID playerUUID = player.getUniqueId();
        final ThreadsContainerImpl threadsContainer = this.plugin.getThreadsContainer();
        if (threadsContainer.get(playerUUID) == null) return;
        final ThreadsDataContainer threadsDataContainer = threadsContainer.get(playerUUID);
        threadsDataContainer.getRunnable().cancel();
    }
}
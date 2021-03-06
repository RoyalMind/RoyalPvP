package net.royalmind.royalpvp.utils;

import net.royalmind.royalpvp.data.containers.effects.EffectsContainerImpl;
import net.royalmind.royalpvp.data.containers.effects.EffectsDataContainer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PlayersData {

    private EffectsContainerImpl effectsContainer;

    public PlayersData(final EffectsContainerImpl effectsContainer) {
        this.effectsContainer = effectsContainer;
    }

    public List<Player> getPlayersHave(final DataType dataType) {
        List<Player> players = new ArrayList<>();
        for (final Player player : Bukkit.getOnlinePlayers()) {
            final UUID playerUUID = player.getUniqueId();
            if (!(this.effectsContainer.contains(playerUUID))) continue;
            final EffectsDataContainer effectsDataContainer = this.effectsContainer.get(playerUUID);
            switch (dataType) {
                case SOUNDS:
                    if (!(effectsDataContainer.isEnableSounds())) continue;
                case PARTICLES:
                    if (!(effectsDataContainer.isEnableParticles())) continue;
            }
            players.add(player);
        }
        return players;
    }

    public enum DataType {
        PARTICLES, SOUNDS
    }
}

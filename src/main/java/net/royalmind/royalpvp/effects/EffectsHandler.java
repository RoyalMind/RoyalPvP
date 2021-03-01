package net.royalmind.royalpvp.effects;

import net.royalmind.royalpvp.data.containers.effects.EffectsContainerImpl;
import net.royalmind.royalpvp.data.containers.effects.EffectsDataContainer;
import net.royalmind.royalpvp.utils.PlayersData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class EffectsHandler implements Listener {

    private JavaPlugin plugin;
    private EffectsContainerImpl effectsContainer;
    private PlayersData playersData;

    public EffectsHandler(final JavaPlugin plugin, final EffectsContainerImpl effectsContainer, final PlayersData playersData) {
        this.plugin = plugin;
        this.effectsContainer = effectsContainer;
        this.playersData = playersData;
    }

    @EventHandler
    public void onKillEvent(final PlayerDeathEvent event) {
        final Player dead = event.getEntity();
        final Player killer = dead.getKiller();
        playEffect(killer, dead);
    }

    public void playEffect(final Player killer, final Player dead) {
        final EffectsDataContainer effectsDataContainer = this.effectsContainer.get(killer.getUniqueId());
        if (effectsDataContainer == null) return;
        final String currentEffect = effectsDataContainer.getCurrentEffect();
        final EffectType effectType = EffectType.getByID(currentEffect);
        effectType.run(this.plugin, killer, dead, this.playersData);
    }
}

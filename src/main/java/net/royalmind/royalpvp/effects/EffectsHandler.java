package net.royalmind.royalpvp.effects;

import net.royalmind.royalpvp.RoyalPvP;
import net.royalmind.royalpvp.effects.types.AbstractEffect;
import net.royalmind.royalpvp.effects.types.BloodEffect;
import net.royalmind.royalpvp.effects.types.OneKillEffect;
import net.royalmind.royalpvp.effects.types.SquidEffect;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

public class EffectsHandler implements Listener {

    private RoyalPvP royalPvP;

    public EffectsHandler(final RoyalPvP royalPvP) {
        this.royalPvP = royalPvP;
    }

    @EventHandler
    public void onKillMob(final EntityDeathEvent event) {
        final LivingEntity entity = event.getEntity();
        final Player player = entity.getKiller();

        switch (entity.getType()) {
            case COW:
                new OneKillEffect(player, this.royalPvP).run();
                break;
            case PIG:
                new BloodEffect(player, this.royalPvP).run();
                break;
            case SHEEP:
                new SquidEffect(player, this.royalPvP).run();
                break;
        }
    }

    @EventHandler
    public void onKillEvent(final PlayerDeathEvent event) {
        final Player dead = event.getEntity();
        final Player killer = dead.getKiller();
        playEffect(killer, dead);
    }

    public void playEffect(final Player killer, final Player dead) {
        //Obtener que tipo de efecto tiene
        //si es que tiene uno buscar cual es con un switch y reproducirlo
        AbstractEffect effect = null;
        if (killer.isOp()) {
            effect = new OneKillEffect(dead, this.royalPvP);
        }
        if (effect != null) effect.run();
    }
}

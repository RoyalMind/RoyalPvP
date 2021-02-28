package net.royalmind.royalpvp.effects;

import net.royalmind.royalpvp.effects.types.AbstractEffect;
import net.royalmind.royalpvp.effects.types.BloodEffect;
import net.royalmind.royalpvp.effects.types.OneKillEffect;
import net.royalmind.royalpvp.effects.types.SquidEffect;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public enum EffectType {

    NONE("none"), KILL_BLOOD("blood"), KILL_ONEKILL("onekill"), KILL_SQUID("squid");

    private String id;

    EffectType(final String id) {
        this.id = id;
    }

    public static EffectType getByID(final String id) {
        switch (id.toLowerCase()) {
            case "blood":
                return KILL_BLOOD;
            case "onekill":
                return KILL_ONEKILL;
            case "squid":
                return KILL_SQUID;
            default:
                return NONE;
        }
    }

    public void run(final JavaPlugin plugin, final Player killer, final Player dead) {
        AbstractEffect abstractEffect = null;
        switch (this) {
            case KILL_BLOOD:
                abstractEffect = new BloodEffect(dead, plugin);
                break;
            case KILL_SQUID:
                abstractEffect = new SquidEffect(dead, plugin);
                break;
            case KILL_ONEKILL:
                abstractEffect = new OneKillEffect(killer, plugin);
                break;
        }
        if (abstractEffect == null) return;
        abstractEffect.run();
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "EffectType{" +
                "id='" + id + '\'' +
                '}';
    }
}

package net.royalmind.royalpvp.effects;

import net.royalmind.royalpvp.effects.types.*;
import net.royalmind.royalpvp.utils.PlayersData;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public enum EffectType {

    NONE("none"), KILL_BLOOD("blood"), KILL_ONEKILL("onekill"), KILL_SQUID("squid"),
    KILL_EXPLOSION("explosion"), KILL_LIGHT("light"), KILL_SKULL("skull"), KILL_TNT("tnt"),
    KILL_FIRE("fire"), KILL_GOLDDROP("gold"), KILL_SWORD("sword"), KILL_REKT("rekt"),
    KILL_BREAK("break");

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
            case "explosion":
                return KILL_EXPLOSION;
            case "light":
                return KILL_LIGHT;
            case "skull":
                return KILL_SKULL;
            case "tnt":
                return KILL_TNT;
            case "fire":
                return KILL_FIRE;
            case "gold":
                return KILL_GOLDDROP;
            case "sword":
                return KILL_SWORD;
            case "rekt":
                return KILL_REKT;
            case "break":
                return KILL_BREAK;
            default:
                return NONE;
        }
    }

    public void run(final JavaPlugin plugin, final Player killer, final Player dead, final PlayersData playersData) {
        AbstractEffect abstractEffect = null;
        switch (this) {
            case KILL_BLOOD:
                abstractEffect = new BloodEffect(dead, plugin, playersData);
                break;
            case KILL_SQUID:
                abstractEffect = new SquidEffect(dead, plugin, playersData);
                break;
            case KILL_ONEKILL:
                abstractEffect = new OneKillEffect(killer, plugin, playersData);
                break;
            case KILL_EXPLOSION:
                abstractEffect = new ExplosionEffect(dead, plugin, playersData);
                break;
            case KILL_LIGHT:
                abstractEffect = new LightEffect(dead, plugin, playersData);
                break;
            case KILL_SKULL:
                abstractEffect = new SkullEffect(dead, plugin, playersData);
                break;
            case KILL_TNT:
                abstractEffect = new TNTEffect(dead, plugin, playersData);
                break;
            case KILL_FIRE:
                abstractEffect = new FireEffect(killer, plugin, playersData);
                break;
            case KILL_GOLDDROP:
                abstractEffect = new GoldDropEffect(killer, plugin, playersData);
                break;
            case KILL_SWORD:
                abstractEffect = new SwordEffect(dead, plugin, playersData);
                break;
            case KILL_REKT:
                abstractEffect = new RektEffect(dead, plugin, playersData);
                break;
            case KILL_BREAK:
                abstractEffect = new BreakEffect(dead, plugin, playersData);
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

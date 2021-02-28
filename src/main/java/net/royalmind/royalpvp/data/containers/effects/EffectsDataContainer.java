package net.royalmind.royalpvp.data.containers.effects;

import java.util.UUID;

public class EffectsDataContainer {

    private UUID uuid;
    private String currentEffect;
    private boolean enableParticles;
    private boolean enableSounds;

    public EffectsDataContainer(final UUID uuid, final String currentEffect, final boolean enableParticles, final boolean enableSounds) {
        this.uuid = uuid;
        this.currentEffect = currentEffect;
        this.enableParticles = enableParticles;
        this.enableSounds = enableSounds;
    }

    public UUID getUUID() {
        return uuid;
    }

    public String getCurrentEffect() {
        return currentEffect;
    }

    public boolean isEnableParticles() {
        return enableParticles;
    }

    public boolean isEnableSounds() {
        return enableSounds;
    }

    public void setCurrentEffect(final String currentEffect) {
        this.currentEffect = currentEffect;
    }

    public void setEnableParticles(final boolean enableParticles) {
        this.enableParticles = enableParticles;
    }

    public void setEnableSounds(final boolean enableSounds) {
        this.enableSounds = enableSounds;
    }
}

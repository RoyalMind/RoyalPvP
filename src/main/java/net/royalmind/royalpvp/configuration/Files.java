package net.royalmind.royalpvp.configuration;

import net.royalmind.royalpvp.RoyalPvP;

public class Files {

    private final RoyalPvP royalPvP;

    private ConfigurationFile config;
    private ConfigurationFile league;
    private ConfigurationFile effects;

    public Files(final RoyalPvP royalPvP) {
        this.royalPvP = royalPvP;
        this.loadDirectory();
    }

    private void loadDirectory() {
        this.config = new ConfigurationFile(this.royalPvP, "config");
        this.league = new ConfigurationFile(this.royalPvP, "leagues");
        this.effects = new ConfigurationFile(this.royalPvP, "effects");
    }

    public ConfigurationFile getConfig() {
        return config;
    }

    public ConfigurationFile getLeague() {
        return league;
    }

    public ConfigurationFile getEffects() {
        return effects;
    }
}

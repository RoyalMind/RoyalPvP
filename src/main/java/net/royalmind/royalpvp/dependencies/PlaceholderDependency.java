package net.royalmind.royalpvp.dependencies;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import net.royalmind.royalpvp.data.containers.leagues.LeaguesContainerImpl;
import org.bukkit.entity.Player;

public class PlaceholderDependency extends PlaceholderExpansion {

    private LeaguesContainerImpl leaguesContainer;

    public PlaceholderDependency(final LeaguesContainerImpl leaguesContainer) {
        this.leaguesContainer = leaguesContainer;
    }

    @Override
    public String onPlaceholderRequest(final Player player, final String identifier) {
        if (identifier.equalsIgnoreCase("player_league")) {
            return this.leaguesContainer.getPlayerLevel(player);
        }
        return null;
    }

    @Override
    public boolean canRegister() {
        return true;
    }

    @Override
    public String getIdentifier() {
        return "royalpvp";
    }

    @Override
    public String getAuthor() {
        return "ImSrPanda";
    }

    @Override
    public String getVersion() {
        return "1.0";
    }
}
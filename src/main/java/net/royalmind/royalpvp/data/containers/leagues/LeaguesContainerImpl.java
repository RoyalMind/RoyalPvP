package net.royalmind.royalpvp.data.containers.leagues;

import net.royalmind.royalpvp.data.containers.AbstractDataMap;
import net.royalmind.royalpvp.utils.Chat;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class LeaguesContainerImpl extends AbstractDataMap<String, LeaguesDataContainer> {

    private FileConfiguration configLeagues;

    public LeaguesContainerImpl(final FileConfiguration configLeagues) {
        this.configLeagues = configLeagues;
        load();
    }

    private void load() {
        for (final String league : this.configLeagues.getConfigurationSection("Leagues").getKeys(false)) {
            final String rootPath = "Leagues." + league + ".";
            final String name = this.configLeagues.getString(rootPath + "Name");
            final int minimumLevel = this.configLeagues.getInt(rootPath + "Level.Minimum");
            final int maximumLevel = this.configLeagues.getInt(rootPath + "Level.Maximum");
            set(
                    league,
                    new LeaguesDataContainer(
                            name, minimumLevel, maximumLevel
                    )
            );
        }
    }

    public String getPlayerLevel(final Player player) {
        for (final LeaguesDataContainer leagueContainer : getValues()) {
            final int maximumLevel = leagueContainer.getMaximumLevel();
            final int minimumLevel = leagueContainer.getMinimumLevel();
            final String name = Chat.translate(leagueContainer.getName());
            final int level = player.getLevel();
            if (maximumLevel < 0) {
                if (level >= minimumLevel) return name;
            } else {
                if (level >= minimumLevel && level <= maximumLevel) return name;
            }
        }
        return "none";
    }
}

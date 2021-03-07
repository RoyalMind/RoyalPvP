package net.royalmind.royalpvp.data.containers.leagues;

public class LeaguesDataContainer {

    private String name;
    private int minimumLevel;
    private int maximumLevel;

    public LeaguesDataContainer(final String name, final int minimumLevel, final int maximumLevel) {
        this.name = name;
        this.minimumLevel = minimumLevel;
        this.maximumLevel = maximumLevel;
    }

    public String getName() {
        return name;
    }

    public int getMinimumLevel() {
        return minimumLevel;
    }

    public int getMaximumLevel() {
        return maximumLevel;
    }
}

package net.royalmind.royalpvp.league;

public enum LeagueType {

    BRONCE("Bronce"),
    PLATA("Plata"),
    ORO("Oro"),
    DIAMANTE("Diamante");

    private String name;
    private int maximumLevel;
    private int minimumLevel;

    LeagueType(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getMaximumLevel() {
        return maximumLevel;
    }

    public int getMinimumLevel() {
        return minimumLevel;
    }

    public void setMaximumLevel(int maximumLevel) {
        this.maximumLevel = maximumLevel;
    }

    public void setMinimumLevel(int minimumLevel) {
        this.minimumLevel = minimumLevel;
    }
}

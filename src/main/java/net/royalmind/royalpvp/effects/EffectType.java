package net.royalmind.royalpvp.effects;

public enum EffectType {

    NONE("none"), KILL_BLOOD("blood");

    private String id;

    EffectType(final String id) {
        this.id = id;
    }

    public static EffectType getByID(final String id) {
        switch (id.toLowerCase()) {
            case "blood":
                return KILL_BLOOD;
            default:
                return NONE;
        }
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

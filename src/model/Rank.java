package model;

public enum Rank {
    TECHNICIAN("Technician"),
    PETTY_OFFICER("Petty Officer"),
    CHIEF_PETTY_OFFICER("Chief Petty Officer"),
    ENSIGN("Ensign"),
    LIEUTENANT_JR_GRADE("Lieutenant Jr. Grade"),
    LIEUTENANT("Lieutenant"),
    LIEUTENANT_COMMANDER("Lieutenant Commander"),
    COMMANDER("Commander"),
    CAPTAIN("Captain"),
    ADMIRAL("Admiral");

    public final String rank_name;

    Rank(String name) {
        this.rank_name = name;
    }

    @Override
    public String toString() {
        return this.rank_name;
    }
}

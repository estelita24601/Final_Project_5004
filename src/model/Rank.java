package model;

public enum Rank {
    TECHNICIAN(0, "Technician"),
    PETTY_OFFICER(1, "Petty Officer"),
    CHIEF_PETTY_OFFICER(1.5, "Chief Petty Officer"),
    ENSIGN(2, "Ensign"),
    LIEUTENANT_JR_GRADE(3, "Lieutenant Jr. Grade"),
    LIEUTENANT(3.25, "Lieutenant"),
    LIEUTENANT_COMMANDER(4, "Lieutenant Commander"),
    COMMANDER(4.5, "Commander"),
    CAPTAIN(5, "Captain"),
    ADMIRAL(6, "Admiral");

    public final double rank_value;
    public final String rank_name;

    Rank(double i, String name) {
        this.rank_value = i;
        this.rank_name = name;
    }

    @Override
    public String toString() {
        return this.rank_name;
    }
}

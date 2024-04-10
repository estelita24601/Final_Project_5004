package model;

public enum Rank {
    TECHNICIAN(0),
    PETTY_OFFICER(1),
    CHIEF_PETTY_OFFICER(1.5),
    ENSIGN(2),
    LIEUTENANT_JR_GRADE(3),
    LIEUTENANT(3.25),
    LIEUTENANT_COMMANDER(4),
    COMMANDER(4.5),
    CAPTAIN(5),
    ADMIRAL(6);


    private double rank_hierarchy;

    Rank(double i) {
        this.rank_hierarchy = i;
    }

    @Override
    public String toString() {
        switch(this){
            case TECHNICIAN: return "Technician";
            case PETTY_OFFICER: return "Petty Officer";
            case CHIEF_PETTY_OFFICER: return "Chief Petty Officer";
            case ENSIGN: return "Ensign";
            case LIEUTENANT_JR_GRADE: return "Lieutenant Jr. Grade";
            case LIEUTENANT: return "Lieutenant";
            case LIEUTENANT_COMMANDER: return "Lieutenant Commander";
            case COMMANDER: return "Commander";
            case CAPTAIN: return "Captain";
            case ADMIRAL: return "Admiral";
            default: return "Unknown Rank";
        }
    }

    public double getRankValue(){
        return this.rank_hierarchy;
    }
}

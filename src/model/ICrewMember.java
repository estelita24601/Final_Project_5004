package model;

import java.util.ArrayList;
import java.util.function.Consumer;

public interface ICrewMember {
    void promote(Rank newRank);
    void demote(Rank newRank);
    Rank getRank();

    void changeName(String newName);
    String getName();

    void setHeritage(ArrayList<Species> heritage);
    void addToHeritage(Species newSpecies);
    void removeFromHeritage(Species newSpecies);
    ArrayList<Species> getHeritage();

    void editShift(Consumer<Shift> shiftEditor);
    void setShift(Shift newShift);
    Shift getShift();

    void setJob(Department newDepartment);
    Department getJob();
}

package model.personnel;

import model.Shift;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import model.*;

public interface ICrewMember {
    void promote(Rank newRank);
    void demote(Rank newRank);
    Rank getRank();

    void changeName(String newName);
    String getName();

    void setHeritage(ArrayList<Species> heritage);
    void addToHeritage(Species newSpecies);
    void removeFromHeritage(Species newSpecies);
    List<String> getHeritage();

    void editShift(Consumer<Shift> shiftEditor);
    void setShift(Shift newShift);
    Shift getShift();

    void setJob(Department newDepartment);
    Department getJob();
}

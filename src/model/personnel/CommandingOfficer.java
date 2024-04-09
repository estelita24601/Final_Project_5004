package model.personnel;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import model.scheduling.Department;
import model.scheduling.Shift;

public class CommandingOfficer implements ICrewMember {

    @Override
    public void promote(Rank newRank) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'promote'");
    }

    @Override
    public void demote(Rank newRank) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'demote'");
    }

    @Override
    public Rank getRank() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getRank'");
    }

    @Override
    public void changeName(String newName) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'changeName'");
    }

    @Override
    public String getName() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getName'");
    }

    @Override
    public void setHeritage(ArrayList<Species> heritage) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setHeritage'");
    }

    @Override
    public void addToHeritage(Species newSpecies) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addToHeritage'");
    }

    @Override
    public void removeFromHeritage(Species newSpecies) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'removeFromHeritage'");
    }

    @Override
    public List<String> getHeritage() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getHeritage'");
    }

    @Override
    public void editShift(Consumer<Shift> shiftEditor) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'editShift'");
    }

    @Override
    public void setShift(Shift newShift) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setShift'");
    }

    @Override
    public Shift getShift() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getShift'");
    }

    @Override
    public void setJob(Department newDepartment) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setJob'");
    }

    @Override
    public Department getJob() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getJob'");
    }

}

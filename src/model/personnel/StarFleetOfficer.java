package model.personnel;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import model.*;
import model.scheduling.Shift;

public class StarFleetOfficer implements ICrewMember {
    String fullName;
    Rank rank;
    Boolean canCommand;
    Department assignment;
    Shift shift;
    ArrayList<Species> heritage;

    public StarFleetOfficer(String name, Rank rank, Department department) {
        this.fullName = name;
        this.rank = rank;
        this.assignment = department;
        this.heritage = new ArrayList<Species>();

        if (rank == Rank.TECHNICIAN) {
            this.canCommand = false;
        } else {
            this.canCommand = true;
        }

        
    }

    public StarFleetOfficer(String name, Rank rank, Department department, ArrayList<Species> heritage) {
        this(name, rank, department);
        this.heritage.addAll(heritage);
    }

    public StarFleetOfficer(String name, Rank rank, Department department, Species heritage) {
        this(name, rank, department);
        this.heritage.add(heritage);
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
    public Shift getShift() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getShift'");
    }

    @Override
    public Department getJob() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getJob'");
    }

    @Override
    public void promote(Rank newRank) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'promote'");
    }

    @Override
    public void setShift(Shift newShift) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setShift'");
    }

    @Override
    public void setJob(Department newDepartment) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setJob'");
    }

}
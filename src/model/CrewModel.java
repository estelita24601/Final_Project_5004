package model;

import java.util.ArrayList;

import model.personnel.ICrew;
import model.personnel.ICrewMember;

public class CrewModel implements IModel {
    ArrayList<String> rankOptions;
    ArrayList<String> speciesOptions;
    ArrayList<String> departmentOptions;
    ICrew<ICrewMember> crew;

    // need a constructor!!

    @Override
    public ArrayList<String> getRankOptions() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getRankOptions'");
    }

    @Override
    public ArrayList<String> getSpeciesOptions() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getSpeciesOptions'");
    }

    @Override
    public ArrayList<String> getDepartmentOptions() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getDepartmentOptions'");
    }

    @Override
    public ArrayList<String> getShiftOptions() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getShiftOptions'");
    }

    @Override
    public ICrewMember creatCrewMember() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'creatCrewMember'");
    }

    @Override
    public Shift createShift() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createShift'");
    }

}

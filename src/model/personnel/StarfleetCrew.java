package model.personnel;

import treeADT.*;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

import model.Rank;

public class StarFleetCrew implements ICrew<StarFleetOfficer> {
    private TreeNode<StarFleetOfficer> root;

    public StarFleetCrew(StarFleetOfficer commandingOfficer) {
        if (Rank.ENSIGN.compareTo(commandingOfficer.getRank()) > 0) {
            throw new IllegalArgumentException("this officer is inelligible to command a starfleet crew");
        }
        this.root = new BranchNode<StarFleetOfficer>(commandingOfficer);
    }

    @Override
    public int countAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'countAll'");
    }

    @Override
    public int countFilter(Predicate<StarFleetOfficer> filter) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'countFilter'");
    }

    @Override
    public List membersList() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'membersList'");
    }

    @Override
    public List membersList(Predicate<StarFleetOfficer> filter) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'membersList'");
    }

    @Override
    public List membersNameList() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'membersNameList'");
    }

    @Override
    public List membersNameList(Predicate<StarFleetOfficer> filter) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'membersNameList'");
    }

    @Override
    public StarFleetOfficer getCrewMember(Predicate<StarFleetOfficer> thisMember) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getCrewMember'");
    }

    @Override
    public String getCrewMemberInfo(Predicate<StarFleetOfficer> thisMember) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getCrewMemberInfo'");
    }

    @Override
    public String getCrewMemberInfo(Predicate<StarFleetOfficer> thisMember, Function<StarFleetOfficer, String> convertInfoToStr) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getCrewMemberInfo'");
    }

    @Override
    public void editCrewMember(Predicate<StarFleetOfficer> thisMember, Consumer<StarFleetOfficer> crewEditor) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'editCrewMember'");
    }

    @Override
    public void removeCrewMember(Predicate<StarFleetOfficer> identifier) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'removeCrewMember'");
    }

    @Override
    public void reAssignTo(Predicate<StarFleetOfficer> thisMember, Predicate<StarFleetOfficer> newSuperior) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'reAssignTo'");
    }

    @Override
    public void putInCommandOf(Predicate<StarFleetOfficer> thisMember, Predicate<StarFleetOfficer> newSubordinate) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'putInCommandOf'");
    }

    @Override
    public void addCrewMember(StarFleetOfficer newCrewMember, Predicate<StarFleetOfficer> newSuperior) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addCrewMember'");
    }

}

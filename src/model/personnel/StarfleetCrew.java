package model.personnel;

import treeADT.*;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

import model.Rank;

public class StarFleetCrew implements ICrew<StarFleetOfficer> {
    private BranchNode<StarFleetOfficer> root;

    public StarFleetCrew(StarFleetOfficer commandingOfficer) {
        // officer must be ensign or higher to be in command
        if (Rank.ENSIGN.compareTo(commandingOfficer.getRank()) > 0) {
            throw new IllegalArgumentException("this officer is ineligible to command a starfleet crew");
        }
        this.root = new BranchNode<StarFleetOfficer>(commandingOfficer);
    }

    @Override
    public int countAll() {
        return root.countAll();
    }

    @Override
    public int countFilter(Predicate<StarFleetOfficer> filter) {
        return root.countIf(filter);
    }

    @Override
    public List<StarFleetOfficer> getMemberList() {
        return root.toList();
    }

    @Override
    public List<StarFleetOfficer> getMemberList(Predicate<StarFleetOfficer> filter) {
        return root.filterToList(filter);
    }

    @Override
    public StarFleetOfficer getCrewMember(Predicate<StarFleetOfficer> findThisMember) {
        TreeNode<StarFleetOfficer> memberNode = root.findNode(findThisMember);
        return memberNode.getData();
    }

    @Override
    public String getCrewMemberInfo(Predicate<StarFleetOfficer> findThisMember,
            Function<StarFleetOfficer, String> convertInfoToStr) {
        TreeNode<StarFleetOfficer> memberNode = root.findNode(findThisMember);
        return convertInfoToStr.apply(memberNode.getData());
    }

    @Override
    public void editCrewMember(Predicate<StarFleetOfficer> findMemberToEdit,
            Consumer<StarFleetOfficer> crewMemberEditor) {
        TreeNode<StarFleetOfficer> memberNode = root.findNode(findMemberToEdit);
        crewMemberEditor.accept(memberNode.getData());
    }

    @Override
    public void removeCrewMember(Predicate<StarFleetOfficer> findMemberToRemove) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'removeCrewMember'");
    }

    @Override
    public void reAssignTo(Predicate<StarFleetOfficer> thisMember, Predicate<StarFleetOfficer> findNewSuperior) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'reAssignTo'");
    }

    @Override
    public void putInCommandOf(Predicate<StarFleetOfficer> thisMember, Predicate<StarFleetOfficer> findNewSubordinate) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'putInCommandOf'");
    }

    @Override
    public void addCrewMember(StarFleetOfficer newCrewMember, Predicate<StarFleetOfficer> findNewSuperior) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addCrewMember'");
    }

    @Override
    public List<String> getMemberInfoList(Predicate<StarFleetOfficer> filter,
            Function<StarFleetOfficer, String> convertInfoToStr) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getMemberInfoList'");
    }

}

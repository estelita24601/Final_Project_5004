package model;

import treeADT.BranchNode;
import treeADT.TreeNode;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class StarFleetCrew<StarFleetOfficer> implements ICrewModel<StarFleetOfficer> {
    //lambda for the starfleet requirement for being a commanding officer
    private final Predicate<StarFleetOfficer> canCommand = (officer) -> officer.getRank().compareTo(Rank.PETTY_OFFICER) > 0;
    private BranchNode<StarFleetOfficer> root;

    public StarFleetCrew() {
    }

    public void setCommandingOfficer(StarFleetOfficer officer) {
        if (canCommand.test(officer)) {
            this.root = new BranchNode<StarFleetOfficer>(officer);
        } else {
            throw new IllegalArgumentException("this officer is ineligible to command a starfleet crew");
        }
    }

    @Override
    public Rank[] getRankOptions() {
        return Rank.values();
    }

    @Override
    public Species[] getSpeciesOptions() {
        return Species.values();
    }

    @Override
    public Rotation[] getShiftRotationOptions() {
        return Rotation.values();
    }

    @Override
    public Department[] getDepartmentOptions() {
        return Department.values();
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
    public List<String> getMemberInfoList(Predicate<StarFleetOfficer> filter, Function<StarFleetOfficer, String> convertInfoToStr) {
        List<StarFleetOfficer> memberList = getMemberList(filter);
        List<String> memberInfoList = new ArrayList<>();
        for (StarFleetOfficer member : memberList) {
            memberInfoList.add(convertInfoToStr.apply(member));
        }
        return memberInfoList;
    }

    @Override
    public StarFleetOfficer getCrewMember(Predicate<StarFleetOfficer> findThisMember) {
        TreeNode<StarFleetOfficer> memberNode = root.findNode(findThisMember);
        return memberNode.getData();
    }

    @Override
    public String getCrewMemberInfo(Predicate<StarFleetOfficer> findThisMember, Function<StarFleetOfficer, String> convertInfoToStr) {
        TreeNode<StarFleetOfficer> memberNode = root.findNode(findThisMember);
        return convertInfoToStr.apply(memberNode.getData());
    }

    @Override
    public void editCrewMember(Predicate<StarFleetOfficer> findMemberToEdit, Consumer<StarFleetOfficer> crewMemberEditor) {
        TreeNode<StarFleetOfficer> memberNode = root.findNode(findMemberToEdit);
        crewMemberEditor.accept(memberNode.getData());
    }

    @Override
    public void addCrewMember(StarFleetOfficer newCrewMember, Predicate<StarFleetOfficer> findNewSuperior) {
        BranchNode<StarFleetOfficer> newSuperior = (BranchNode<StarFleetOfficer>) root.findNode(findNewSuperior);
        newSuperior.addChild(newCrewMember, canCommand);
    }

    @Override
    public void removeCrewMember(Predicate<StarFleetOfficer> findMemberToRemove) {
        TreeNode<StarFleetOfficer> memberNode = root.findNode(findMemberToRemove);

        //there are children so need to move them before removing their parent
        ArrayList<TreeNode<StarFleetOfficer>> memberChildren = memberNode.getChildren();
        if (!memberChildren.isEmpty() && memberChildren != null) {
            BranchNode<StarFleetOfficer> newSuperior = (BranchNode<StarFleetOfficer>) memberNode.getParent();
            memberNode.moveChildren((t) -> true, newSuperior);
        }

        //now just remove ourselves from our parent
        BranchNode<StarFleetOfficer> parent = (BranchNode<StarFleetOfficer>) memberNode.getParent();
        parent.deleteChild(memberNode);
    }

    @Override
    public void reAssignTo(Predicate<StarFleetOfficer> thisMember, Predicate<StarFleetOfficer> findNewSuperior) {
        TreeNode<StarFleetOfficer> memberNode = root.findNode(thisMember);
        BranchNode<StarFleetOfficer> newSuperior = (BranchNode<StarFleetOfficer>) root.findNode(findNewSuperior);
        memberNode.setParent(newSuperior);

        //if needed move children to grandparent
        ArrayList<TreeNode<StarFleetOfficer>> oldChildren = memberNode.getChildren();
        if (!oldChildren.isEmpty() && oldChildren != null) {
            BranchNode<StarFleetOfficer> oldSuperior = (BranchNode<StarFleetOfficer>) memberNode.getParent();
            memberNode.moveChildren(oldSuperior);
        }
    }

    @Override
    public void putInCommandOf(Predicate<StarFleetOfficer> findThisMember, Predicate<StarFleetOfficer> findNewSubordinate) {
        BranchNode<StarFleetOfficer> thisMember = (BranchNode<StarFleetOfficer>) root.findNode(findThisMember);
        TreeNode<StarFleetOfficer> newSubordinate = root.findNode(findNewSubordinate);

        thisMember.addChild(newSubordinate);
    }
}

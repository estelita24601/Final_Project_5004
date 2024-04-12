import treeADT.BranchNode;
import treeADT.TreeNode;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class StarFleetCrew implements ICrewModel<ICrewMember> {
    //lambda for the starfleet requirement for being a commanding officer
    private final Predicate<ICrewMember> canCommand = (officer) -> officer.getRank().compareTo(Rank.PETTY_OFFICER) > 0;
    private BranchNode<ICrewMember> root;

    public StarFleetCrew() {
    }

    @Override
    public void setRoot(ICrewMember officer) {
        if (canCommand.test(officer)) {
            this.root = new BranchNode<>(officer);
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
    public int countFilter(Predicate<ICrewMember> filter) {
        return root.countIf(filter);
    }

    @Override
    public List<ICrewMember> getMemberList() {
        return root.toList();
    }

    @Override
    public List<ICrewMember> getMemberList(Predicate<ICrewMember> filter) {
        return root.filterToList(filter);
    }

    @Override
    public List<String> getMemberInfoList(Predicate<ICrewMember> filter, Function<ICrewMember, String> convertInfoToStr) {
        List<ICrewMember> memberList = getMemberList(filter);
        List<String> memberInfoList = new ArrayList<>();
        for (ICrewMember member : memberList) {
            memberInfoList.add(convertInfoToStr.apply(member));
        }
        return memberInfoList;
    }

    @Override
    public ICrewMember getCrewMember(Predicate<ICrewMember> findThisMember) {
        TreeNode<ICrewMember> memberNode = root.findNode(findThisMember);
        return memberNode.getData();
    }

    @Override
    public String getCrewMemberInfo(Predicate<ICrewMember> findThisMember, Function<ICrewMember, String> convertInfoToStr) {
        TreeNode<ICrewMember> memberNode = root.findNode(findThisMember);
        return convertInfoToStr.apply(memberNode.getData());
    }

    @Override
    public void editCrewMember(Predicate<ICrewMember> findMemberToEdit, Consumer<ICrewMember> crewMemberEditor) {
        TreeNode<ICrewMember> memberNode = root.findNode(findMemberToEdit);
        crewMemberEditor.accept(memberNode.getData());
    }

    @Override
    public void addCrewMember(ICrewMember newCrewMember, Predicate<ICrewMember> findNewSuperior) {
        BranchNode<ICrewMember> newSuperior = (BranchNode<ICrewMember>) root.findNode(findNewSuperior);
        newSuperior.addChild(newCrewMember, canCommand);
    }

    @Override
    public void removeCrewMember(Predicate<ICrewMember> findMemberToRemove) {
        TreeNode<ICrewMember> memberNode = root.findNode(findMemberToRemove);

        //there are children so need to move them before removing their parent
        ArrayList<TreeNode<ICrewMember>> memberChildren = memberNode.getChildren();
        if (!memberChildren.isEmpty()) {
            BranchNode<ICrewMember> newSuperior = (BranchNode<ICrewMember>) memberNode.getParent();
            memberNode.moveChildren((t) -> true, newSuperior);
        }

        //now just remove ourselves from our parent
        BranchNode<ICrewMember> parent = (BranchNode<ICrewMember>) memberNode.getParent();
        parent.deleteChild(memberNode);
    }

    @Override
    public void reAssignTo(Predicate<ICrewMember> thisMember, Predicate<ICrewMember> findNewSuperior) {
        TreeNode<ICrewMember> memberNode = root.findNode(thisMember);
        BranchNode<ICrewMember> newSuperior = (BranchNode<ICrewMember>) root.findNode(findNewSuperior);
        memberNode.setParent(newSuperior);

        //move children to grandparent if needed
        ArrayList<TreeNode<ICrewMember>> oldChildren = memberNode.getChildren();
        if (!oldChildren.isEmpty()) {
            BranchNode<ICrewMember> oldSuperior = (BranchNode<ICrewMember>) memberNode.getParent();
            memberNode.moveChildren(oldSuperior);
        }
    }

    @Override
    public void putInCommandOf(Predicate<ICrewMember> findThisMember, Predicate<ICrewMember> findNewSubordinate) {
        BranchNode<ICrewMember> thisMember = (BranchNode<ICrewMember>) root.findNode(findThisMember);
        TreeNode<ICrewMember> newSubordinate = root.findNode(findNewSubordinate);

        thisMember.addChild(newSubordinate);
    }
}

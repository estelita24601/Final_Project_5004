import treeADT.BranchNode;
import treeADT.ITree;
import treeADT.TreeNode;

import java.io.FileNotFoundException;
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
    public String toString() {
        return root.toString();
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
    public Department[] getDepartmentOptions() {
        return Department.values();
    }

    @Override
    public void loadFromFile(String filename) throws FileNotFoundException {
        StarFleetCSVReader reader = new StarFleetCSVReader();
        this.root = (BranchNode<ICrewMember>) reader.loadRootFromFile(filename);
    }

    @Override
    public int countAll() {
        //make sure we even have a root crew member
        if (root == null) {
            return 0;
        }
        return root.countAll();
    }

    @Override
    public int countFilter(Predicate<ICrewMember> filter) {
        //make sure we even have a root crew member
        if (root == null) {
            return 0;
        }
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
        //find the superior officer using the predicate
        BranchNode<ICrewMember> newSuperior = (BranchNode<ICrewMember>) root.findNode(findNewSuperior);

        if (newSuperior != null) {
            //assign the new crew member to the superior
            newSuperior.addChild(newCrewMember, canCommand);
        }
    }

    @Override
    public void removeCrewMember(Predicate<ICrewMember> findMemberToRemove) {
        //get the node of the member we're removing
        TreeNode<ICrewMember> memberNode = root.findNode(findMemberToRemove);

        //if there are children move them all to new superior so we don't lose them as well
        ArrayList<TreeNode<ICrewMember>> memberChildren = memberNode.getChildren();
        if (!memberChildren.isEmpty()) {
            BranchNode<ICrewMember> newSuperior = (BranchNode<ICrewMember>) memberNode.getParent();
            memberNode.moveChildren((t) -> true, newSuperior); //move every single child to the new superior
        }

        //get the parent of the node we're removing
        BranchNode<ICrewMember> parent = (BranchNode<ICrewMember>) memberNode.getParent();
        //remove them from the parent
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

    @Override
    public ITree<ICrewMember> getRoot() {
        return this.root;
    }

    @Override
    public ICrewMember getDirectSuperiorOf(Predicate<ICrewMember> findCrewMember) {
        TreeNode<ICrewMember> crewMember = root.findNode(findCrewMember);
        return crewMember.getParent().getData();
    }

    @Override
    public List<ICrewMember> getDirectSuboordinatesOf(Predicate<ICrewMember> findCrewMember) {
        //find the crew member we want to get information on
        TreeNode<ICrewMember> crewMember = root.findNode(findCrewMember);
        //get all the suboordinates of that crew member
        List<TreeNode<ICrewMember>> suboordinateCrewNodes = crewMember.getChildren();

        //extract just the crew member data from the child tree nodes
        ArrayList<ICrewMember> suboordinateCrew = new ArrayList<>();
        for (TreeNode<ICrewMember> node : suboordinateCrewNodes) {
            suboordinateCrew.add(node.getData());
        }
        return suboordinateCrew;
    }

}

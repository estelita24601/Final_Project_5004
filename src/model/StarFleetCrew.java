package model;

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
    public final Predicate<ICrewMember> canCommand = (officer) -> officer.getRank().compareTo(Rank.PETTY_OFFICER) > 0;
    private BranchNode<ICrewMember> root;

    public StarFleetCrew() {
    }

    /**
     * @return Predicate<ICrewMember> the predicate used to determine if a crew member can be a commanding officer in this crew
     */
    @Override
    public Predicate<ICrewMember> getCommandingOfficerRequirement() {
        return this.canCommand;
    }

    /**
     * @return Rank[] an array of all the possible Rank enum values
     */
    @Override
    public Rank[] getRankOptions() {
        return Rank.values();
    }

    /**
     * @return Species[] an array of all the possible Species enum values
     */
    @Override
    public Species[] getSpeciesOptions() {
        return Species.values();
    }

    /**
     * @return Rotation[] an array of all the possible shift Rotation enum values
     */
    @Override
    public Rotation[] getShiftRotationOptions() {
        return Rotation.values();
    }

    /**
     * @return Department[] an array of all the possible Department enum values
     */
    @Override
    public Department[] getDepartmentOptions() {
        return Department.values();
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

    /**
     * use a CSV file to load in crew members
     *
     * @param filename (String) name of the file that has crew information
     * @throws FileNotFoundException if unable to open/read file given
     */
    @Override
    public void loadFromFile(String filename) throws FileNotFoundException {
        StarFleetCSVReader reader = new StarFleetCSVReader();
        this.root = (BranchNode<ICrewMember>) reader.loadRootFromFile(filename);
    }

    /**
     * @return (int) the total number of crew members in this crew
     */
    @Override
    public int countAll() {
        //make sure we even have a root crew member
        if (root == null) {
            return 0;
        }
        return root.countAll();
    }

    /**
     * @param filter (Predicate<ICrewMember>) predicate that returns true when you want a crew member to counted
     * @return (int) the number of crew members in this crew that passed the predicate given
     */
    @Override
    public int countFilter(Predicate<ICrewMember> filter) {
        //make sure we even have a root crew member
        if (root == null) {
            return 0;
        }
        return root.countIf(filter);
    }

    /**
     * @return (List < ICrewMember >) a list of all the crew members in this crew
     */
    @Override
    public List<ICrewMember> getMemberList() {
        return root.toList();
    }

    /**
     * @param filter (Predicate<ICrewMember>) predicate that determines if we want the crew member to be included in
     *               the list
     * @return (List < ICrewMember >) list of the crew members in this crew that passed the filter
     */
    @Override
    public List<ICrewMember> getMemberList(Predicate<ICrewMember> filter) {
        return root.filterToList(filter);
    }

    /**
     * Get a list of string information from members of this crew that fit the requirements set by the filter
     *
     * @param filter           (Predicate<ICrewMember>) predicate that determines which crew members we want information about
     * @param convertInfoToStr (Function<ICrewMember, String>) function that takes the information from a crew member
     *                         and formats it in a string however you want
     * @return (List < String >) list of information on the crew members that passed the filter. the strings in this
     * list are formatted however was specified in convertInfoToStr function
     */
    @Override
    public List<String> getMemberInfoList(Predicate<ICrewMember> filter, Function<ICrewMember, String> convertInfoToStr) {
        List<ICrewMember> memberList = getMemberList(filter); //get a list of the crew members using the predicate filter
        List<String> memberInfoList = new ArrayList<>();
        for (ICrewMember member : memberList) {
            memberInfoList.add(convertInfoToStr.apply(member)); //now convert everything using the function and add to our final list
        }
        return memberInfoList; //return after we've converted all the members
    }

    /**
     * @param findThisMember (Predicate<ICrewMember>) predicate that will return true when it finds the person you're
     *                       looking for
     * @return (ICrewMember) the first crew member in the crew that passed the findThisMember predicate
     */
    @Override
    public ICrewMember getCrewMember(Predicate<ICrewMember> findThisMember) {
        TreeNode<ICrewMember> memberNode = root.findNode(findThisMember); //find the node with the crew member we want
        return memberNode.getData(); //return just the crew member not the entire node
    }

    /**
     * @param findThisMember   (Predicate<ICrewMember>) predicate that will return true when it finds the person you're
     *                         looking for
     * @param convertInfoToStr (Function<ICrewMember, String>) function that takes the information from a crew member
     *                         and formats it in a string however you want
     * @return (String) information about the crew member found by the predicate in the form of a string that was
     * formatted by the convertInfoToStr function
     */
    @Override
    public String getCrewMemberInfo(Predicate<ICrewMember> findThisMember, Function<ICrewMember, String> convertInfoToStr) {
        TreeNode<ICrewMember> memberNode = root.findNode(findThisMember); //use predicate to find the crew member
        return convertInfoToStr.apply(memberNode.getData()); //then use function to convert to string format specified
    }

    /**
     * @param findMemberToEdit (Predicate<ICrewMember>) predicate that returns true when it finds the crew member you
     *                         want to edit
     * @param crewMemberEditor (Consumer<ICrewMember>) consumer that will accept a crew member then edit it however
     *                         you specify
     */
    @Override
    public void editCrewMember(Predicate<ICrewMember> findMemberToEdit, Consumer<ICrewMember> crewMemberEditor) {
        TreeNode<ICrewMember> memberNode = root.findNode(findMemberToEdit);
        crewMemberEditor.accept(memberNode.getData());
    }

    /**
     * @param newCrewMember   (ICrewMember) the new crew member you want to add to this crew
     * @param findNewSuperior (Predicate<ICrewMember>) predicate that finds who will be the direct superior of this
     *                        new crew member
     */
    @Override
    public void addCrewMember(ICrewMember newCrewMember, Predicate<ICrewMember> findNewSuperior) {
        //find the superior officer using the predicate
        BranchNode<ICrewMember> newSuperior = (BranchNode<ICrewMember>) root.findNode(findNewSuperior);

        if (newSuperior != null) {
            //assign the new crew member to the superior
            newSuperior.addChild(newCrewMember, canCommand);
        }
    }

    /**
     * will remove a crew member without affecting anyone under their command
     *
     * @param findMemberToRemove (Predicate<ICrewMember>) predicate that returns true when it finds the person you
     *                           want to remove from the crew
     */
    @Override
    public void removeCrewMember(Predicate<ICrewMember> findMemberToRemove) {
        //get the node of the member we're removing
        TreeNode<ICrewMember> memberToRemove = root.findNode(findMemberToRemove);
        if (memberToRemove == null) {
            throw new IllegalStateException("Unable to find crew member that fits given specifications");
        }

        //if there are children move them all to new superior so we don't lose them as well
        ArrayList<TreeNode<ICrewMember>> memberChildren = memberToRemove.getChildren();
        if (!memberChildren.isEmpty()) {
            BranchNode<ICrewMember> newSuperior = (BranchNode<ICrewMember>) memberToRemove.getParent();
            memberToRemove.moveChildren((t) -> true, newSuperior); //move every single child to the new superior
        }

        //get the parent of the node we're removing
        BranchNode<ICrewMember> parent = (BranchNode<ICrewMember>) memberToRemove.getParent();
        //remove them from the parent
        parent.deleteChild(memberToRemove);
    }

    /**
     * will reassign a crew member to serve under a new commanding officer. any officers commanded by the original
     * officer will be re-assigned to the next officer in their original chain of command
     *
     * @param thisMember      predicate that finds the crew member you are re-assigning
     * @param findNewSuperior predicate that finds the new superior for the member you're re-assigning
     */
    @Override
    public void reAssignTo(Predicate<ICrewMember> thisMember, Predicate<ICrewMember> findNewSuperior) {
        TreeNode<ICrewMember> memberNode = root.findNode(thisMember);
        BranchNode<ICrewMember> newSuperior = (BranchNode<ICrewMember>) root.findNode(findNewSuperior);

        //move children to grandparent if needed
        ArrayList<TreeNode<ICrewMember>> oldChildren = memberNode.getChildren();
        BranchNode<ICrewMember> grandparent = (BranchNode) memberNode.getParent();
        if (!oldChildren.isEmpty()) {
            memberNode.moveChildren(grandparent);
        }

        //now we can re-assign to the new superior
        grandparent.deleteChild(memberNode); //remove member node from old parent
        newSuperior.addChild(memberNode); //add member node to the new parent
        memberNode.setParent(newSuperior); //update the member node with it's new parent
    }

    @Override
    public void putInCommandOf(Predicate<ICrewMember> findThisMember, Predicate<ICrewMember> findNewSubordinate) {
        reAssignTo(findNewSubordinate, findThisMember); // put the new suboordinate underneath this member w/o affecting grandchildren
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

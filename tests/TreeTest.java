import org.junit.Before;
import org.junit.Test;
import treeADT.BranchNode;
import treeADT.TreeNode;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

import static org.junit.Assert.assertEquals;

public class TreeTest {
    TreeNode<String> stringTree;
    TreeNode<Integer> intTree;
    TreeNode<ICrewMember> crewMemberTree;
    ICrewMember captain;


    @Before
    public void setUp() {
        stringTree = new BranchNode<String>("root");
        intTree = new BranchNode<Integer>(0);
        captain = new StarFleetOfficer("janeway", Rank.CAPTAIN, Department.BRIDGE, Rotation.ALPHA, Species.HUMAN);
        crewMemberTree = new BranchNode<>(captain);
    }

    //helper method that adds more items to all the trees so we can test more complicated methods
    public void addToAllTrees() {
        //todo: add strings to the str tree
        //todo: add integers to the int tree
        //todo: add crew members to the crew tree
    }

    @Test
    public void testGetData() {
        assertEquals("root", stringTree.getData());
        assertEquals(Integer.valueOf(0), intTree.getData());
        assertEquals(captain, crewMemberTree.getData());
    }

    @Test
    public void testSetData() {
        stringTree.setData("NEW root");
        assertEquals("NEW root", stringTree.getData());

        intTree.setData(Integer.valueOf(100));
        assertEquals(Integer.valueOf(100), intTree.getData());

        ICrewMember temporaryCaptain = new StarFleetOfficer("chakotay", Rank.COMMANDER, Department.BRIDGE, Rotation.BETA, Species.HUMAN);
        crewMemberTree.setData(temporaryCaptain);
        assertEquals(temporaryCaptain, crewMemberTree.getData());
    }

    @Test
    public void testEditData() {
        Consumer<ICrewMember> consumer = (crewMember) -> {
            crewMember.setJob(Department.SCIENCE);
        };
        crewMemberTree.editData(consumer);
        assertEquals(Department.SCIENCE, crewMemberTree.getData().getJob());
    }

    @Test
    public void testGetParent() {
    }

    @Test
    public void testSetParent() {
    }

    @Test
    public void testAddChild() {
        //testing adding to a tree with a TreeNode object as the argument
        TreeNode<Integer> intLeftChild = new BranchNode<>(-1);

        //first add a child to the right child and see if that works
        TreeNode<Integer> intRightChild = new BranchNode<>(1);
        TreeNode<Integer> intGrandchild = new BranchNode<>(2);
        intRightChild.addChild(intGrandchild);
        ArrayList<TreeNode<Integer>> expectedChildList = new ArrayList<>();
        expectedChildList.add(intGrandchild);
        assertEquals(expectedChildList, intRightChild.getChildren());
        expectedChildList.remove(intGrandchild);

        //now add the left child to the root
        intTree.addChild(intLeftChild);
        expectedChildList.add(intLeftChild);
        assertEquals(expectedChildList, intTree.getChildren());

        //add right child to root, the grandchild should come too
        intTree.addChild(intRightChild);
        expectedChildList.add(intRightChild);
        assertEquals(expectedChildList, intTree.getChildren());

        assertEquals(4, intTree.countAll());

        //now testing adding to the tree with the raw data value and a predicate
        Predicate<Integer> canBeBranch = (number) -> number % 2 == 0;
        intTree.addChild(3, canBeBranch);
        intTree.addChild(4, canBeBranch);

        assertEquals(6, intTree.countAll());
    }

    @Test
    public void testAddChildCrew() {
        ICrewMember seniorOfficer = new StarFleetOfficer("tuvok", Rank.LIEUTENANT_COMMANDER, Department.SECURITY, Rotation.BETA, Species.VULCAN);
        crewMemberTree.addChild(seniorOfficer);
        assertEquals(1, crewMemberTree.getChildren().size());

        seniorOfficer = new StarFleetOfficer("b'elanna torres", Rank.LIEUTENANT, Department.ENGINEERING, Rotation.GAMMA, List.of(Species.HUMAN, Species.KLINGON));
        crewMemberTree.addChild(seniorOfficer);
        assertEquals(2, crewMemberTree.getChildren().size());

        System.out.println(crewMemberTree);
    }

    @Test
    public void testRemoveChild() {
        //todo give a tree node object that we want to delete
        //todo give a predicate to find child to delete
    }


    @Test
    public void testGetChildren() {
    }

    @Test
    public void testCountIf() {
    }

    @Test
    public void testCountAll() {
    }

    @Test
    public void testToList() {
    }

    @Test
    public void testFindNode() {
    }

    @Test
    public void testFold() {
    }

    @Test
    public void testMapToList() {
    }

    @Test
    public void testToString() {
    }

    @Test
    public void testFilterToList() {
    }

    @Test
    public void testMap() {

    }

}
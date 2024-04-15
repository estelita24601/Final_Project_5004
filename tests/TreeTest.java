import org.junit.Before;
import org.junit.Test;
import treeADT.BranchNode;
import treeADT.TreeNode;

import java.util.function.Consumer;

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

    //helper method that adds to the trees for us so we can more easily test certain methods
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
    public void testAddChild() {
        //todo: add child using tree node object as arg
        //todo: add child using data and a predicate determining if the child is a branch or not
    }

    @Test
    public void testRemoveChild() {
        //todo give a tree node object that we want to delete
        //todo give a predicate to find child to delete
    }

    @Test
    public void testGetParent() {
    }

    @Test
    public void testSetParent() {
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
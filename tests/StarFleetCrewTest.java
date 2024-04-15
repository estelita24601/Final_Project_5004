import org.junit.Before;
import org.junit.Test;
import treeADT.TreeNode;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class StarFleetCrewTest {
    StarFleetOfficer testOfficer;
    StarFleetCrew voyagerCrew;
    StarFleetOfficer janeway;
    StarFleetOfficer belanna;
    StarFleetCrew deepSpaceNineCrew;
    StarFleetOfficer sisko;
    StarFleetOfficer dax;

    @Before
    public void setUp() throws Exception {
        //making crew members
        janeway = new StarFleetOfficer("Kathryn Janeway", Rank.CAPTAIN, Department.BRIDGE, Rotation.ALPHA, Species.HUMAN);
        sisko = new StarFleetOfficer("Benjamin Sisko", Rank.CAPTAIN, Department.BRIDGE, Rotation.ALPHA, Species.HUMAN);
        ArrayList<Species> belannaHeritage = new ArrayList<>(List.of(Species.HUMAN, Species.KLINGON));
        belanna = new StarFleetOfficer("B'Elanna Torres", Rank.LIEUTENANT_JR_GRADE, Department.ENGINEERING, Rotation.BETA, belannaHeritage);
        dax = new StarFleetOfficer("Jadzia Dax", Rank.LIEUTENANT_COMMANDER, Department.SCIENCE, Rotation.GAMMA, Species.TRILL);
        testOfficer = new StarFleetOfficer("Jane Doe", Rank.ENSIGN, Department.SECURITY, Rotation.DELTA);

        //making voyager crew and loading in from file
        voyagerCrew = new StarFleetCrew();
        voyagerCrew.loadFromFile("resources/VoyagerCrew.csv");
        //making ds9 crew empty
        deepSpaceNineCrew = new StarFleetCrew();
    }

    @Test
    public void setRoot() {
        deepSpaceNineCrew.setRoot(sisko);
        assertEquals(sisko, deepSpaceNineCrew.getRoot());
    }

    @Test
    public void loadFromFile() throws FileNotFoundException {
        voyagerCrew.loadFromFile("resources/VoyagerCrew.csv");
        System.out.println(voyagerCrew);
    }

    @Test
    public void countAll() {
        assertEquals(26, voyagerCrew.countAll());
        assertEquals(0, deepSpaceNineCrew.countAll());
        deepSpaceNineCrew.setRoot(sisko);
        assertEquals(1, deepSpaceNineCrew.countAll());
        deepSpaceNineCrew.addCrewMember(dax, (o) -> o.getName().equalsIgnoreCase(sisko.getName()));
        assertEquals(2, deepSpaceNineCrew.countAll());
    }

    @Test
    public void countFilter() {
        Predicate<ICrewMember> isEngineer = (officer) -> officer.getJob() == Department.ENGINEERING;
        assertEquals(9, voyagerCrew.countFilter(isEngineer));

        Predicate<ICrewMember> isLieutenant = (officer) -> officer.getRank() == Rank.LIEUTENANT;
        assertEquals(5, voyagerCrew.countFilter(isLieutenant));

        Predicate<ICrewMember> isVulcan = (officer) -> officer.getHeritage().contains(Species.VULCAN);
        assertEquals(2, voyagerCrew.countFilter(isVulcan));
    }

    @Test
    public void getMemberList() {
        List<ICrewMember> allVoyagerCrew = voyagerCrew.getMemberList();
        assertEquals(26, allVoyagerCrew.size());

        List<ICrewMember> voyagerBridgeCrew = voyagerCrew.getMemberList((c) -> c.getJob() == Department.BRIDGE);
        assertEquals(6, voyagerBridgeCrew.size());
    }

    @Test
    public void getMemberInfoList() {
    }

    @Test
    public void getCrewMember() {
        Predicate<ICrewMember> findSeven = (officer) -> officer.getHeritage().contains(Species.BORG);
        ICrewMember sevenOfNine = voyagerCrew.getCrewMember(findSeven);
        assertEquals(sevenOfNine.getName(), "Seven of Nine");
        assertEquals(sevenOfNine.getJob(), Department.SCIENCE);
    }

    @Test
    public void getCrewMemberInfo() {
    }

    @Test
    public void editCrewMember() {
    }

    @Test
    public void addCrewMember() {
        voyagerCrew.setRoot(janeway);
        Predicate<ICrewMember> findJaneway = (officer) -> "Kathryn Janeway".equalsIgnoreCase(officer.getName());
        assertTrue(findJaneway.test(janeway)); //make sure the predicate will find janeway
        voyagerCrew.addCrewMember(belanna, findJaneway);

        ArrayList<TreeNode<ICrewMember>> janewaySubordinates = voyagerCrew.getRoot().getChildren();
        assertEquals(1, janewaySubordinates.size());
    }

    @Test
    public void removeCrewMember() {
        Predicate<ICrewMember> findJoeCarey = (officer) -> officer.getName().equalsIgnoreCase("joe carey");
        ICrewMember superiorOfficer = voyagerCrew.getDirectSuperiorOf(findJoeCarey);
        System.out.println(superiorOfficer);


        voyagerCrew.removeCrewMember(findJoeCarey);
        assertEquals(25, voyagerCrew.countAll());
    }

    @Test
    public void reAssignTo() {
    }

    @Test
    public void putInCommandOf() {
    }

    @Test
    public void testToString() {
    }
}

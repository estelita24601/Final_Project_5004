import org.junit.Before;
import org.junit.Test;
import treeADT.TreeNode;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import static org.junit.Assert.assertEquals;

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
        testOfficer = new StarFleetOfficer("Jane Doe", Rank.ENSIGN, Department.SECURITY, Rotation.DELTA);
        voyagerCrew = new StarFleetCrew();
        janeway = new StarFleetOfficer("Kathryn Janeway", Rank.CAPTAIN, Department.BRIDGE, Rotation.ALPHA, Species.HUMAN);
        ArrayList<Species> belannaHeritage = new ArrayList<>(List.of(Species.HUMAN, Species.KLINGON));
        belanna = new StarFleetOfficer("B'Elanna Torres", Rank.LIEUTENANT_JR_GRADE, Department.ENGINEERING, Rotation.BETA, belannaHeritage);
        deepSpaceNineCrew = new StarFleetCrew();
        sisko = new StarFleetOfficer("Benjamin Sisko", Rank.CAPTAIN, Department.BRIDGE, Rotation.ALPHA, Species.HUMAN);
        dax = new StarFleetOfficer("Jadzia Dax", Rank.LIEUTENANT_COMMANDER, Department.SCIENCE, Rotation.GAMMA, Species.TRILL);
    }

    @Test
    public void setRoot() {
        voyagerCrew.setRoot(janeway);
        assertEquals(janeway, voyagerCrew.getRoot().getData());
    }

    @Test
    public void loadFromFile() throws FileNotFoundException {
        //FIXME: only loading in the first officer in the file
        voyagerCrew.loadFromFile("resources/VoyagerCrew.csv");
        assert (false);
    }

    @Test
    public void countAll() {
    }

    @Test
    public void countFilter() {
    }

    @Test
    public void getMemberList() {
    }

    @Test
    public void testGetMemberList() {
    }

    @Test
    public void getMemberInfoList() {
    }

    @Test
    public void getCrewMember() {
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
        voyagerCrew.addCrewMember(belanna, findJaneway);

        ArrayList<TreeNode<ICrewMember>> janewaySubordinates = voyagerCrew.getRoot().getChildren();
        assertEquals(1, janewaySubordinates.size());
    }

    @Test
    public void removeCrewMember() {
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

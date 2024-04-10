package model.personnel;

import model.Department;
import model.Rank;
import model.Rotation;
import model.Species;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class StarFleetOfficerTest {
    StarFleetOfficer janeway;
    StarFleetOfficer sisko;
    StarFleetOfficer belanna;
    StarFleetOfficer jadzia;
    StarFleetOfficer testOfficer;

    @Before
    public void setUp() {
        janeway = new StarFleetOfficer("Katheryn Janeway", Rank.CAPTAIN, Department.BRIDGE, Rotation.ALPHA, Species.HUMAN);
        sisko = new StarFleetOfficer("Benjamin Sisko", Rank.CAPTAIN, Department.BRIDGE, Rotation.ALPHA, Species.HUMAN);
        ArrayList<Species> belannaHeritage = new ArrayList<>(List.of(Species.HUMAN, Species.KLINGON));
        belanna = new StarFleetOfficer("B'Elanna Torres", Rank.LIEUTENANT_JR_GRADE, Department.ENGINEERING, Rotation.BETA, belannaHeritage);
        jadzia = new StarFleetOfficer("Jadzia Dax", Rank.LIEUTENANT_COMMANDER, Department.SCIENCE, Rotation.GAMMA, Species.TRILL);
        testOfficer = new StarFleetOfficer("Jane Doe", Rank.ENSIGN, Department.SECURITY, Rotation.DELTA);
    }

    @Test
    public void compareTo() {
    }

    @Test
    public void promote() {
    }

    @Test
    public void demote() {
    }

    @Test
    public void changeName() {
    }

    @Test
    public void setHeritage() {
    }

    @Test
    public void addToHeritage() {
    }

    @Test
    public void removeFromHeritage() {
    }

    @Test
    public void editShift() {
    }

    @Test
    public void setShift() {
    }

    @Test
    public void setJob() {
    }
}
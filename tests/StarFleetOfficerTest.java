import model.*;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import static org.junit.Assert.*;

public class StarFleetOfficerTest {
    StarFleetOfficer janeway;
    StarFleetOfficer sisko;
    StarFleetOfficer belanna;
    StarFleetOfficer dax;
    StarFleetOfficer testOfficer;

    @Before
    public void setUp() {
        janeway = new StarFleetOfficer("Katheryn Janeway", Rank.CAPTAIN, Department.BRIDGE, Rotation.ALPHA, Species.HUMAN);
        sisko = new StarFleetOfficer("Benjamin Sisko", Rank.CAPTAIN, Department.BRIDGE, Rotation.ALPHA, Species.HUMAN);
        dax = new StarFleetOfficer("Jadzia Dax", Rank.LIEUTENANT_COMMANDER, Department.SCIENCE, Rotation.GAMMA, Species.TRILL);
        ArrayList<Species> belannaHeritage = new ArrayList<>(List.of(Species.HUMAN, Species.KLINGON));
        belanna = new StarFleetOfficer("B'Elanna Torres", Rank.LIEUTENANT_JR_GRADE, Department.ENGINEERING, Rotation.BETA, belannaHeritage);
        testOfficer = new StarFleetOfficer("Jane Doe", Rank.ENSIGN, Department.SECURITY, Rotation.DELTA);
    }

    @Test
    public void testToString() {
        System.out.println(janeway.toString());
        System.out.println(sisko.toString());
        System.out.println(dax.toString());
        System.out.println(belanna.toString());
        System.out.println(testOfficer.toString());
    }

    @Test
    public void testEquals() {
        assert (janeway.equals(janeway));
        //now make an officer with the same name, rank and species as someone else
        //even though the department and rotation differ they should still be "equal"
        StarFleetOfficer daxWithNewJob = new StarFleetOfficer("Jadzia Dax", Rank.LIEUTENANT_COMMANDER, Department.MEDICAL_BAY, Rotation.BETA, Species.TRILL);
        assert (dax.equals(daxWithNewJob));

        //now test that it returns false when necessary
        assertFalse(janeway.equals(sisko)); //everything the same except name
        StarFleetOfficer futureJaneway = new StarFleetOfficer("Katheryn Janeway", Rank.ADMIRAL, Department.BRIDGE, Rotation.ALPHA, Species.HUMAN);
        ;
        assertFalse(janeway.equals(futureJaneway)); //everything the same except rank
        StarFleetOfficer hologramJaneway = new StarFleetOfficer("Katheryn Janeway", Rank.CAPTAIN, Department.BRIDGE, Rotation.ALPHA, Species.HOLOGRAM);
        assertFalse(janeway.equals(hologramJaneway)); //everything the same except species

        assertFalse(janeway.equals(Rank.CAPTAIN)); //wrong object type should give false
    }

    @Test
    public void testHashCode() {
        StarFleetOfficer daxWithNewJob = new StarFleetOfficer("Jadzia Dax", Rank.LIEUTENANT_COMMANDER, Department.MEDICAL_BAY, Rotation.BETA, Species.TRILL);
        assertEquals(dax.hashCode(), daxWithNewJob.hashCode());
        assertNotEquals(janeway.hashCode(), sisko.hashCode());
    }

    @Test
    public void compareTo() {
        assertEquals(janeway.compareTo(sisko), 0); //both are the same rank
        assertEquals(janeway.compareTo(belanna) > 0, true); //janeway outranks belanna
        assertEquals(dax.compareTo(sisko) < 0, true); // dax is lower rank than sisko
    }

    @Test
    public void promote() {
        janeway.promote(Rank.ADMIRAL);
        assertEquals(Rank.ADMIRAL, janeway.getRank());
        testOfficer.promote(Rank.LIEUTENANT);
        assertEquals(Rank.LIEUTENANT, testOfficer.getRank());
    }

    @Test(expected = IllegalArgumentException.class)
    public void promoteBad() {
        sisko.promote(Rank.COMMANDER);
    }

    @Test
    public void demote() {
        dax.demote(Rank.LIEUTENANT);
        assertEquals(Rank.LIEUTENANT, dax.getRank());
        testOfficer.demote(Rank.CHIEF_PETTY_OFFICER);
        assertEquals(Rank.CHIEF_PETTY_OFFICER, testOfficer.getRank());
    }

    @Test(expected = IllegalArgumentException.class)
    public void demoteBad() {
        belanna.demote(Rank.COMMANDER);
    }

    @Test
    public void changeName() {
        dax.changeName("Ezri Dax");
        assertEquals("Ezri Dax", dax.getName());
    }

    @Test
    public void setHeritage() {
        ArrayList<Species> newHeritage = new ArrayList<>(List.of(Species.ANDORIAN));
        testOfficer.setHeritage(newHeritage);
        assertEquals(newHeritage, testOfficer.getHeritage());

        newHeritage.add(Species.BETAZOID);
        testOfficer.setHeritage(newHeritage);
        assertEquals(newHeritage, testOfficer.getHeritage());
    }

    @Test
    public void addAndRemoveToHeritage() {
        ArrayList<Species> expectedHeritage = new ArrayList<>();

        expectedHeritage.add(Species.BETAZOID);
        testOfficer.addToHeritage(Species.BETAZOID);
        assertEquals(expectedHeritage, testOfficer.getHeritage());

        expectedHeritage.add(Species.VULCAN);
        testOfficer.addToHeritage(Species.VULCAN);
        assertEquals(expectedHeritage, testOfficer.getHeritage());

        expectedHeritage.add(Species.HUMAN);
        testOfficer.addToHeritage(Species.HUMAN);
        assertEquals(expectedHeritage, testOfficer.getHeritage());

        expectedHeritage.remove(Species.BETAZOID);
        testOfficer.removeFromHeritage(Species.BETAZOID);
        assertEquals(expectedHeritage, testOfficer.getHeritage());

        expectedHeritage.remove(Species.HUMAN);
        testOfficer.removeFromHeritage(Species.HUMAN);
        assertEquals(expectedHeritage, testOfficer.getHeritage());
    }

    @Test(expected = IllegalStateException.class)
    public void addToHeritageWrong() {
        sisko.addToHeritage(Species.HUMAN); //he's already human should throw exception
    }

    @Test(expected = IllegalStateException.class)
    public void removeFromHeritageWrong() {
        belanna.removeFromHeritage(Species.ROMULAN); //she isn't romulan so should throw exception
    }

    @Test
    public void editShift() {
        Consumer<Shift> editBetaName = (shift) -> shift.setName("Best Shift");
        belanna.editShift(editBetaName);
        assertEquals("Best Shift", belanna.getShift().getName());
    }

    @Test
    public void setShift() {
        Shift newShift = new Shift(Department.MEDICAL_BAY, "Medical Shift 1", LocalTime.of(9,0), LocalTime.of(5,0));
        testOfficer.setShift(newShift);
        assertEquals(newShift, testOfficer.getShift());
    }

    @Test
    public void setJob() {
        dax.setJob(Department.MEDICAL_BAY);
        assertEquals(Department.MEDICAL_BAY, dax.getJob());
    }
}
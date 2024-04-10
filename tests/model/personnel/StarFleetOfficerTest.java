package model.personnel;

import model.Department;
import model.Rank;
import model.Rotation;
import model.Species;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class StarFleetOfficerTest {
    StarFleetOfficer janeway;
    StarFleetOfficer sisko;
    StarFleetOfficer belanna;
    StarFleetOfficer kira;

    @Before
    public void setUp(){
        janeway = new StarFleetOfficer("Katheryn Janeway", Rank.CAPTAIN, Department.BRIDGE, Rotation.ALPHA, Species.HUMAN);
        sisko = new StarFleetOfficer("Benjamin Sisko", Rank.CAPTAIN, Department.BRIDGE, Rotation.ALPHA, Species.HUMAN);
        belanna = new StarFleetOfficer("B'Elanna Torres", Rank.LIEUTENANT_JR_GRADE, Department.BRIDGE, Rotation.BETA);
    }

    @Test
    public void canBeCommandingOfficer() {
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
    public void getRank() {
    }

    @Test
    public void changeName() {
    }

    @Test
    public void getName() {
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
    public void getHeritage() {
    }

    @Test
    public void editShift() {
    }

    @Test
    public void getShift() {
    }

    @Test
    public void getJob() {
    }

    @Test
    public void setShift() {
    }

    @Test
    public void setJob() {
    }
}
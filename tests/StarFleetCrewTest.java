import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;

public class StarFleetCrewTest {
    StarFleetCrew voyagerCrew;
    StarFleetCrew deepSpaceNineCrew;

    @Before
    public void setUp() throws Exception {
        voyagerCrew = new StarFleetCrew();

    }

    @Test
    public void getRankOptions() {
    }

    @Test
    public void getSpeciesOptions() {
    }

    @Test
    public void setRoot() {
    }

    @Test
    public void getShiftRotationOptions() {
    }

    @Test
    public void getDepartmentOptions() {
    }

    @Test
    public void loadFromFile() throws FileNotFoundException {
        voyagerCrew.loadFromFile("resources/VoyagerCrew.csv");
        System.out.println(voyagerCrew);
    }

    @Test
    public void testToString() {
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
    public void getRoot() {
    }
}
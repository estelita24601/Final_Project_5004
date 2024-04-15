import model.*;
import org.junit.Before;
import org.junit.Test;
import treeADT.TreeNode;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

import static org.junit.Assert.*;

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
        assertEquals(sisko, deepSpaceNineCrew.getRoot().getData());
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
    public void getCrewMember() {
        Predicate<ICrewMember> findSeven = (officer) -> officer.getHeritage().contains(Species.BORG);
        ICrewMember sevenOfNine = voyagerCrew.getCrewMember(findSeven);
        assertEquals(sevenOfNine.getName(), "Seven of Nine");
        assertEquals(sevenOfNine.getJob(), Department.SCIENCE);
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
        ICrewMember joeCarey = voyagerCrew.getCrewMember(findJoeCarey); //find joe
        ICrewMember superiorOfficer = voyagerCrew.getDirectSuperiorOf(findJoeCarey); //find his superior
        assertEquals(superiorOfficer.getName(), "B'Elanna Torres"); //make sure superior has correct name
        List<ICrewMember> engineeringSuboordinates = voyagerCrew.getDirectSuboordinatesOf((o) -> o.equals(superiorOfficer));
        assertTrue(engineeringSuboordinates.contains(joeCarey)); //make sure he is also listed as suboordinate for his superior

        voyagerCrew.removeCrewMember(findJoeCarey); //finally remove him
        assertEquals(25, voyagerCrew.countAll()); //make sure crew count went down
        //make sure he isn't listed as a suboordinate of b'elanna anymore
        engineeringSuboordinates = voyagerCrew.getDirectSuboordinatesOf((o) -> o.equals(superiorOfficer));
        assertTrue(!engineeringSuboordinates.contains(joeCarey));
    }

    @Test
    public void reAssignTo() {
        Predicate<ICrewMember> findLonSudor = (officer) -> officer.getName().equalsIgnoreCase("Lon Sudor");
        Predicate<ICrewMember> findVorik = (officer) -> officer.getName().equalsIgnoreCase("Vorik");
        Predicate<ICrewMember> findBelanna = (officer) -> officer.getName().equalsIgnoreCase("B'Elanna Torres");

        //re-assign lon sudor to vorik
        voyagerCrew.reAssignTo(findLonSudor, findVorik);

        //now check if lon sudor, vorik and belanna were updated correctly
        ICrewMember lonSudor = voyagerCrew.getCrewMember(findLonSudor);
        ICrewMember vorik = voyagerCrew.getCrewMember(findVorik);
        ICrewMember chiefEngineer = voyagerCrew.getCrewMember(findBelanna);

        //make sure lon sudor's superior is listed as vorik
        assertEquals(vorik, voyagerCrew.getDirectSuperiorOf(findLonSudor));
        //vice versa make sure vorik's list of suboordinates now includes lon suder
        List<ICrewMember> voriksSuboordinates = voyagerCrew.getDirectSuboordinatesOf(findVorik);
        System.out.println(voriksSuboordinates);
        assertTrue(voriksSuboordinates.contains(lonSudor));
        //now make sure lon sudor is no longer directly under b'elanna
        List<ICrewMember> belannasSuboordiantes = voyagerCrew.getDirectSuboordinatesOf(findBelanna);
        assertFalse(belannasSuboordiantes.contains(lonSudor));
    }

    @Test
    public void putInCommandOf() {
        //instead of having chell as b'elanna's direct suboordinate he will first report to joe carey who then reports to b'elanna
        Predicate<ICrewMember> findChell = (officer) -> officer.getName().equalsIgnoreCase("Chell");
        Predicate<ICrewMember> findCarey = (officer) -> officer.getName().equalsIgnoreCase("Joe Carey");
        Predicate<ICrewMember> findBelanna = (officer) -> officer.getName().equalsIgnoreCase("B'Elanna Torres");

        //put joe carey in command of chell
        voyagerCrew.putInCommandOf(findCarey, findChell);

        //now make sure everyone was updated correctly
        ICrewMember chell = voyagerCrew.getCrewMember(findChell);
        ICrewMember carey = voyagerCrew.getCrewMember(findCarey);
        ICrewMember chiefEngineer = voyagerCrew.getCrewMember(findBelanna);

        //make sure chell's superior is carey
        assertEquals(carey, voyagerCrew.getDirectSuperiorOf(findChell));

        //make sure carey's suboordinates include chell
        List<ICrewMember> careySuboordinates = voyagerCrew.getDirectSuboordinatesOf(findCarey);
        assertTrue(careySuboordinates.contains(chell));

        //make sure belanna's direct suboordinates don't include chell anymore
        List<ICrewMember> belannaSuboordinates = voyagerCrew.getDirectSuboordinatesOf(findBelanna);
        assertFalse(belannaSuboordinates.contains(chell));
    }

    @Test
    public void testToString() {
        System.out.println(voyagerCrew);
    }

    @Test
    public void getMemberInfoList() {
//        List<String> getMemberInfoList(Predicate<ICrewMember> filter, Function<ICrewMember, String> convertInfoToStr)
        //lets get a list of all members names + departments only
        Predicate<ICrewMember> getAllCrewMembers = (o) -> true;
        Function<ICrewMember, String> memberNameAndDepartment = (crewMember) -> {
            return String.format("%s - %s\n", crewMember.getName(), crewMember.getJob());
        };
        List<String> crewNamesAndDepartments = voyagerCrew.getMemberInfoList(getAllCrewMembers, memberNameAndDepartment);
        System.out.println(crewNamesAndDepartments);

        //lets get a list of name + rank for everyone in engineering
        Predicate<ICrewMember> getEngineeringCrew = (o) -> o.getJob() == Department.ENGINEERING;
        Function<ICrewMember, String> memberNameAndRank = (crewMember) -> {
            return String.format("%s %s\n", crewMember.getRank(), crewMember.getName());
        };
        List<String> engineeringNamesAndRanks = voyagerCrew.getMemberInfoList(getEngineeringCrew, memberNameAndRank);
        System.out.println(engineeringNamesAndRanks);
    }

    @Test
    public void getCrewMemberInfo() {
        Predicate<ICrewMember> findHarryKim = new FindByName("Harry Kim");
        Function<ICrewMember, String> getAbbrviatedInfo = (crewMember) -> {
            return String.format("%s %s - %s\n", crewMember.getRank(), crewMember.getName(), crewMember.getJob());
        };
        String harryAbbreviatedInfo = voyagerCrew.getCrewMemberInfo(findHarryKim, getAbbrviatedInfo);
        assertEquals("Ensign Harry Kim - Bridge\n", harryAbbreviatedInfo);
    }

    @Test
    public void editCrewMember() {
        Consumer<ICrewMember> promote = (starfleetOfficer) -> {
            starfleetOfficer.promote(Rank.LIEUTENANT_JR_GRADE);
        };
        Predicate<ICrewMember> findHarryKim = new FindByName("Harry Kim");
        ICrewMember harryKim = voyagerCrew.getCrewMember(findHarryKim);

        voyagerCrew.editCrewMember(findHarryKim, promote);
        assertEquals(Rank.LIEUTENANT_JR_GRADE, harryKim.getRank());
    }
}

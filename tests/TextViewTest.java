import model.*;
import org.junit.Before;
import org.junit.Test;
import view.ICrewView;
import view.TextView;

public class TextViewTest {
    ICrewView view = new TextView(System.out);
    ICrewModel model;
    Rank[] rankOptions;
    Species[] speciesOptions;
    Rotation[] rotationOptions;
    Department[] departmentOptions;

    @Before
    public void setUp() {
        model = new StarFleetCrew();
        rankOptions = model.getRankOptions();
        speciesOptions = model.getSpeciesOptions();
        rotationOptions = model.getShiftRotationOptions();
        departmentOptions = model.getDepartmentOptions();
    }

    @Test
    public void welcomeMessage() {
        view.welcomeMessage();
    }

    @Test
    public void askForCaptain() {
        view.askForCaptain();
        view.askForName();
        view.askForRank();
        view.displayOptions(rankOptions);
        view.askForRotation();
        view.displayOptions(rotationOptions);
    }


    @Test
    public void displayYesOrNo() {
    }

    @Test
    public void askIfFinishedGivingHeritage() {
    }

    @Test
    public void displayMainMenu() {
    }

    @Test
    public void goodbyeMessage() {
    }

    @Test
    public void displayError() {
    }

    @Test
    public void displayTryAgainMessage() {
    }
}
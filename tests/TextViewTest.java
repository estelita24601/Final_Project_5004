import model.*;
import org.junit.Before;
import org.junit.Test;
import view.ICrewView;
import view.TextView;

public class TextViewTest {
    ICrewView view = new TextView(System.out);
    ICrewModel<ICrewMember> model;
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


}
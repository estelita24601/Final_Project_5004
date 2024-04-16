import controller.ICrewController;
import controller.StarFleetCommand;
import model.ICrewMember;
import model.ICrewModel;
import model.StarFleetCrew;
import view.ICrewView;
import view.TextView;

import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) {
        ICrewModel<ICrewMember> crewModel = new StarFleetCrew();

        ICrewView textBasedUI = new TextView(System.out);
        ICrewController controller = new StarFleetCommand(new InputStreamReader(System.in));

        controller.go(crewModel, textBasedUI);
    }
}

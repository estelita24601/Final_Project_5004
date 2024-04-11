import model.ICrewMember;
import model.ICrewModel;
import model.StarFleetCrew;

import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) {
        ICrewModel<ICrewMember> crewModel = new StarFleetCrew<>();

        ICrewView textBasedUI = new TerminalCrewView(System.out);
        ICrewController controller = new StarfleetCommand(new InputStreamReader(System.in));

        controller.go(crewModel, textBasedUI);
    }
}

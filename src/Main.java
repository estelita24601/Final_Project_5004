import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) {
        ICrewModel<ICrewMember> crewModel = new StarFleetCrew();

        ICrewView textBasedUI = new TextView(System.out);
        ICrewController controller = new StarfleetCommand(new InputStreamReader(System.in));

        controller.go(crewModel, textBasedUI);
    }
}

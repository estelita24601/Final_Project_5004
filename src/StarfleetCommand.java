import model.ICrewMember;
import model.ICrewModel;
import model.StarFleetOfficer;

import java.util.ArrayList;
import java.util.Scanner;

public class StarfleetCommand implements ICrewController {
    final Readable in;
    private Scanner scanner;
    private ICrewModel<ICrewMember> model;
    private ICrewView view;

    public StarfleetCommand(Readable inputStream) {
        this.in = inputStream;
    }

    @Override
    public void go(ICrewModel<ICrewMember> model, ICrewView view) {
        this.model = model;
        this.view = view;
        boolean wantToContinue = true;
        scanner = new Scanner(in);

        view.welcomeMessage();

        createCaptain();

        while (wantToContinue) {
            view.displayMainMenu();
            int choice = getMainMenuChoice(); //this method has a loop that continues until valid input is given
            if (choice == 1) {
                runCrewCountingMenu();
            } else if (choice == 2) {
                runMemberInformationMenu();
            } else if (choice == 3) {
                runListNamesMenu();
            } else if (choice == 4) {
                runScheduleMenu();
            } else if (choice == 5) {
                runCrewEditorMenu();
            } else if (choice == -1) {
                wantToContinue = false;
            }
            //if they didn't decide to exit go back to main menu after they finished what they were doing
        }

        view.goodbyeMessage();

    }

    private void runCrewEditorMenu() {

    }

    private void runScheduleMenu() {
    }

    private void runListNamesMenu() {
    }

    private void runMemberInformationMenu() {
    }

    private void runCrewCountingMenu() {
    }

    private int getMainMenuChoice() {
    }

    private void createCaptain() {
        boolean invalidInput = true;

        while (invalidInput) {
            view.askForCaptain();

            view.askForNewCrewMembersName();
            String name = scanner.nextLine();

            view.askForNewCrewMembersRank();
            String rank = scanner.nextLine();

            view.askForMembersNewRotation();
            String rotation = scanner.nextLine();

            view.askToDiscloseSpecies();
            String disclosureDecision = scanner.nextLine();
            if (yesDetected(disclosureDecision)) {
                ArrayList<String> species = runSpeciesSelectionMenu();
            }

            try {
                StarFleetOfficer captain = new StarFleetOfficer(name, rank, rotation);
                this.model.setRoot(captain);
                invalidInput = false;
            } catch (IllegalArgumentException e) {
                view.displayError(e);
            }
        }
    }

    private Rank convertRank(String rankName) {

    }

    private ArrayList<String> runSpeciesSelectionMenu() {
    }


    private boolean yesDetected(String userInput) {
        userInput = userInput.strip();
        return userInput.compareToIgnoreCase("yes") == 0;
    }

}

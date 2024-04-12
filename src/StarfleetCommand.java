import model.ICrewMember;
import model.ICrewModel;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.function.Function;

//note: default commands will either be integer cgiuces
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
            Function<String, Integer> choiceConverter = (str) -> Integer.parseInt(str);
            ViewDisplayer mainMenuPrompt = () -> view.displayMainMenu();
            int menuChoice = getValidInput(choiceConverter, mainMenuPrompt);

            if (menuChoice == 1) {
                runCrewCountingMenu();
            } else if (menuChoice == 2) {
                runMemberInformationMenu();
            } else if (menuChoice == 3) {
                runScheduleMenu();
            } else if (menuChoice == 4) {
                runCrewEditorMenu();
            } else if (menuChoice == -1) {
                wantToContinue = false;
            }
            //if they didn't decide to exit go back to main menu after they finished what they were doing
        }

        view.goodbyeMessage();
    }

    /**
     * @param userInput       (String)
     * @param expectedCommand (String)
     * @return true <- if user input matches the expected command
     * false <- if the user input does not match the expected command
     */
    private boolean commandDetected(String userInput, String expectedCommand) {
        userInput = userInput.strip();
        return userInput.compareToIgnoreCase(expectedCommand) == 0;
    }

    /**
     * @param converter    (Function<String,T>) - lambda that will take user input and attempt to convert it to desired data type
     * @param viewPrompter (ViewDisplayer) - lambda that will call the correct view methods for the given context
     * @return T - the user input converted to desired type
     * @return null - if the user decided to quit and we were unable to get valid value from them
     */
    private <T> T getValidInput(Function<String, T> converter, ViewDisplayer viewPrompter
    ) {
        boolean validInput = false;
        T result;

        while (!validInput) {
            viewPrompter
                    .display(); //get the view to show whatever message we need
            String input = scanner.nextLine(); //get the user's input
            input = input.strip();

            //first make sure they don't want to leave
            if (commandDetected(input, "quit")) {
                result = null;
                validInput = true;
            }

            try {
                result = converter.apply(input);
                validInput = true; //leave loop we were able to convert the input to correct data type
            } catch (IllegalArgumentException e) {
                this.view.displayError(e);
                this.view.displayTryAgainMessage();
            }
        }

        return result;
    }

    //todo: in progress
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
            if (commandDetected(disclosureDecision, "yes")) {
                ArrayList<String> species = runSpeciesSelectionMenu();
            }

            try {
                //TODO: fixme
                //StarFleetOfficer captain = new StarFleetOfficer(name, rank, rotation);
                //this.model.setRoot(captain);
                invalidInput = false;
            } catch (IllegalArgumentException e) {
                view.displayError(e);
            }
        }
    }

    //todo:
    private ArrayList<String> runSpeciesSelectionMenu() {
    }

    //todo:
    private void runCrewEditorMenu() {

    }

    //todo:
    private void runScheduleMenu() {
    }

    //todo:
    private void runMemberInformationMenu() {
    }

    //todo:
    private void runCrewCountingMenu() {
    }


}

import java.util.ArrayList;
import java.util.Scanner;
import java.util.function.Function;

public class StarfleetCommand implements ICrewController {
    \
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
        boolean exit = false;
        scanner = new Scanner(in);

        this.view.welcomeMessage();

        createCaptain();

        while (!exit) {
            ViewDisplayer mainMenu = () -> view.displayMainMenu();
            ViewDisplayer invalidChoiceMessage = () -> view.displayTryAgainMessage();
            int menuChoice = getValidChoice(mainMenu, invalidChoiceMessage, 4);

            if (menuChoice == 0) {
                runCrewCountingMenu();
            } else if (menuChoice == 1) {
                runMemberInformationMenu();
            } else if (menuChoice == 2) {
                runCrewEditorMenu();
            } else if (menuChoice == 3) {
                runScheduleMenu();
            } else if (menuChoice == -1) {
                exit = true;
            }
            // if they didn't decide to exit go back to main menu after they finished what
            // they were doing
        }

        this.view.goodbyeMessage();
    }

    // view will present numbered options and we just need to make sure we got valid
    // number, no more words
    // IDEA: maybe return object from options list that was selected instead? and
    // return null for exit command
    private Object getValidChoice(Object[] options, ViewDisplayer choicePrompt, ViewDisplayer invalidResponse) {
        int choiceNumber;
        Object chosenOption = null;
        boolean receivedValidChoice = false;

        while (!receivedValidChoice) {
            choicePrompt.display();
            view.displayOptions(options);
            String userInput = scanner.nextLine().strip();

            // now see if input corresponds to an option in the list
            try {
                choiceNumber = Integer.valueOf(userInput);
                chosenOption = options[choiceNumber];
                receivedValidChoice = true;
            } catch (NumberFormatException e) {
                // didn't even receive a number
                invalidResponse.display();
            } catch (ArrayIndexOutOfBoundsException e) {
                // check if they tried to exit
                if (choiceNumber == -1) {
                    receivedValidChoice = true;
                } else {
                    // otherwise number given was just wrong
                    invalidResponse.display();
                }
            }
        }
        return chosenOption;
    }

    private int getValidChoice(ViewDisplayer choicePrompt, ViewDisplayer invalidResponse, int numOptions) {
        int choice;
        boolean receivedValidChoice = false;

        while (!receivedValidChoice) {
            choicePrompt.display();
            String userInput = scanner.nextLine().strip();
            try {
                choice = Integer.valueOf(userInput);
            } catch (NumberFormatException e) {
                // didn't even receive a number
                invalidResponse.display();
            }
            if (choice == -1 || choice < numOptions) {
                receivedValidChoice = true;
            } else {
                // number received wasn't one of the options
                invalidResponse.display();
            }
        }
        return choice;
    }

    private void createCaptain() {
        boolean invalidInput = true;
        boolean quitEarly = false;
        ViewDisplayer askToTryAgain = () -> view.displayTryAgainMessage();

        while (invalidInput) {
            view.askForCaptain();

            view.askForName();
            String name = scanner.nextLine().strip();

            //todo: fix rank
            Function<String, Rank> rankConverter = (str) -> convertInputToEnum(str, model.getRankOptions());
            ViewDisplayer askForRank = () -> view.askForRank(); // lambda that gets the view to ask for a rank
            Rank rank = getValidInput(rankConverter, askForRank); // give two lambdas to helper that will run loop until
            // receiving valid input or until user quits
            // if getValidInput returned null then user wants to exit early
            if (rank == null) {
                quitEarly = true;
                break;
            }

            //todo: fix rotation
            Function<String, Rotation> rotationConverter = (str) -> convertInputToEnum(str, model.getShiftRotationOptions());
            ViewDisplayer askForRotation = () -> view.askForRotation();
            Rotation rotation = getValidInput(rotationConverter, askForRotation);
            // if getValidInput returned null then user wants to exit early
            if (rotation == null) {
                quitEarly = true;
                break;
            }

            ViewDisplayer askForSpecies = () -> view.askToDiscloseSpecies();
            Boolean[] yesOrNo = {true, false};
            boolean discloseSpecies = (Boolean) getValidChoice(yesOrNo, askForSpecies, askToTryAgain);

            ArrayList<Species> heritage;
            if (discloseSpecies) {
                heritage = runSpeciesSelectionMenu(); // allow them to continue adding species until they stop or quit
            }

            try {
                StarFleetOfficer captain = new StarFleetOfficer(name, rank, Department.BRIDGE, rotation, heritage);
                this.model.setRoot(captain);
                invalidInput = false; // only break out of the loop if succesfully able to set root for the crew
            } catch (IllegalArgumentException e) {
                view.displayError(e);
            }
        }
    }

    private ArrayList<Species> runSpeciesSelectionMenu() {
        ArrayList<Species> heritage = new ArrayList<>();

        boolean wantToContinue = true;
        while (wantToContinue) {
            ViewDisplayer askForSpecies = () -> view.askForSpecies();
            ViewDisplayer errorMessage = () -> view.displayTryAgainMessage();
            Species currentSpecies = (Species) getValidChoice(model.getSpeciesOptions(), askForSpecies, errorMessage);

            if (currentSpecies == null) {
                // they want to quit early
                break;
            }
            heritage.add(currentSpecies);

            //check if they want to continue or want to stop
            Boolean[] yesOrNo = {true, false};
            ViewDisplayer askIfContinue = () -> view.askIfWantToContinueGivingSpecies();
            wantToContinue = (Boolean) getValidChoice(yesOrNo, askIfContinue, errorMessage);
        }

        return heritage;
    }
}

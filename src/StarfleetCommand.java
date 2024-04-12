import model.*;

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
     * @param userInput (str)
     * @return (boolean)
     * true if user input was "yes"
     * false if user input was "no"
     * @throws IllegalArgumentException if user input was not "yes" or "no"
     */
    private boolean checkYesOrNo(String userInput) {
        if (commandDetected(userInput, "yes")) {
            return true;
        } else if (commandDetected(userInput, "no")) {
            return false;
        }
        throw new IllegalArgumentException("must answer yes or no");

    }

    /**
     * @param converter    (Function<String,T>) - lambda that will take user input and attempt to convert it to desired data type
     * @param viewPrompter (ViewDisplayer) - lambda that will call the correct view methods for the given context
     * @return T - the user input converted to desired type
     * @return null - if the user decided to quit and we were unable to get valid value from them
     */
    private <T> T getValidInput(Function<String, T> converter, ViewDisplayer viewPrompter) {
        boolean validInput = false;
        T result = null;

        while (!validInput) {
            viewPrompter.display(); //get the view to show whatever message we need
            String input = scanner.nextLine(); //get the user's input
            input = input.strip();

            //first make sure they don't want to leave
            if (commandDetected(input, "quit")) {
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

    /**
     * @param input       (String) - input from the user
     * @param enumOptions (T[]) - a list of every option for a given enum
     * @return (T) - the enum that matches the user input
     * @throws IllegalArgumentException - if the user input doesn't match any of the enum options
     */
    private <T> T convertInputToEnum(String input, T[] enumOptions) {
        for (T option : enumOptions) {
            if (option.toString().equalsIgnoreCase(input)) {
                return option;
            }
        }
        throw new IllegalArgumentException(String.format("%s is not a valid value", input));
    }

    private void createCaptain() {
        boolean invalidInput = true;
        boolean quitEarly = false;

        while (invalidInput) {
            view.askForCaptain();

            view.askForNewCrewMembersName();
            String name = scanner.nextLine().strip();

            Function<String, Rank> rankConverter = (str) -> convertInputToEnum(str, model.getRankOptions()); //lambda that tries to convert input to rank enum
            ViewDisplayer askForRank = () -> view.askForNewCrewMembersRank(); //lambda that gets the view to ask for a rank
            Rank rank = getValidInput(rankConverter, askForRank); //give two lambdas to helper that will run loop until receiving valid input or until user quits
            //if getValidInput returned null then user wants to exit early
            if (rank == null) {
                quitEarly = true;
                break;
            }

            Function<String, Rotation> rotationConverter = (str) -> convertInputToEnum(str, model.getShiftRotationOptions());
            ViewDisplayer askForRotation = () -> view.askForMembersNewRotation();
            Rotation rotation = getValidInput(rotationConverter, askForRotation);
            // if getValidInput returned null then user wants to exit early
            if (rotation == null) {
                quitEarly = true;
                break;
            }

            //species is optional so check
            Function<String, Boolean> decisionConverter = (str) -> checkYesOrNo(str);
            ViewDisplayer askForSpecies = () -> view.askToDiscloseSpecies();
            boolean discloseSpecies = getValidInput(decisionConverter, askForSpecies);

            ArrayList<Species> heritage;
            if (discloseSpecies) {
                heritage = runSpeciesSelectionMenu(); //allow them to continue adding species until they stop or quit

                // check if the user wants to quit early
                if (heritage == null) {
                    quitEarly = true;
                    break;
                }
            }

            try {
                StarFleetOfficer captain = new StarFleetOfficer(name, rank, Department.BRIDGE, rotation);
                this.model.setRoot(captain);
                invalidInput = false; //only break out of the loop if succesfully able to set root for the crew
            } catch (IllegalArgumentException e) {
                view.displayError(e);
            }
        }
    }

    private ArrayList<Species> runSpeciesSelectionMenu() {
        ArrayList<Species> heritage = new ArrayList<>();

        boolean finished = false;
        while (!finished) {
            Function<String, Species> speciesConverter = (str) -> convertInputToEnum(str, model.getSpeciesOptions());
            ViewDisplayer askForSpecies = () -> view.askForSpecies();
            Species current = getValidInput(speciesConverter, askForSpecies);

            if (current == null) {
                //they want to quit early
                return null;
            }
            heritage.add(current);

            //check for exit condition
            Function<String, Boolean> convertDecision = (str) -> checkYesOrNo(str);
            ViewDisplayer askIfFinished = () -> view.askIfFinishedGivingHeritage();
            finished = getValidInput(convertDecision, askIfFinished);
        }

        return heritage;
    }

    /////////////////////////////////////////////

    //todo:
    private void runCrewCountingMenu() {
    }

    //todo:
    private void runCrewEditorMenu() {

    }

    //todo: ONLY IF TIME
    private void runScheduleMenu() {
    }

    //todo:
    private void runMemberInformationMenu() {
    }

}

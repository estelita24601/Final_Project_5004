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
        boolean exit = false;
        scanner = new Scanner(in);

        this.view.welcomeMessage();

        createCaptain();

        while (!exit) {
            ViewDisplayer mainMenu = () -> view.displayMainMenu();
            ViewDisplayer invalidChoiceMessage = () -> view.displayTryAgainMessage();
            int menuChoice = getValidChoice(mainMenu, invalidChoiceMessage, 4);

            if (menuChoice == 0) {
                //runCrewCountingMenu(); TODO
            } else if (menuChoice == 1) {
                //runMemberInformationMenu(); TODO
            } else if (menuChoice == 2) {
                //runCrewEditorMenu(); TODO
            } else if (menuChoice == 3) {
                //runScheduleMenu(); TODO
            } else if (menuChoice == -1) {
                exit = true;
            }
            // if they didn't decide to exit go back to main menu after they finished what
            // they were doing
        }

        this.view.goodbyeMessage();
    }

    private Object getValidChoice(Object[] options, ViewDisplayer choicePrompt, ViewDisplayer invalidResponse) {
        int choiceNumber = -1000; //initializing with something that will never be an option
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
        int choice = -1000; //initializing with something that will never be an option
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

    private boolean getYesOrNo(ViewDisplayer choicePrompt, ViewDisplayer invalidResponse) {
        int menuSelection = -1000;
        boolean receivedValidChoice = false;

        while (!receivedValidChoice) {
            choicePrompt.display();
            view.displayYesOrNo();
            String userInput = scanner.nextLine().strip();

            //make sure user gave a number for the menu item they chose
            try {
                menuSelection = Integer.valueOf(userInput);
            } catch (NumberFormatException e) {
                // didn't even receive a number
                invalidResponse.display();
            }

            //make sure the number received was one of the options
            if (menuSelection == 0 || menuSelection == 1) {
                receivedValidChoice = true;
            } else {
                // number received wasn't one of the options
                invalidResponse.display();
            }
        }

        return (menuSelection == 1);
    }

    private Rank getRank() {
        ViewDisplayer askForRank = () -> view.askForRank();
        ViewDisplayer askToTryAgain = () -> view.displayTryAgainMessage();
        return (Rank) getValidChoice(model.getRankOptions(), askForRank, askToTryAgain);
    }

    private Species getSpecies() {
        ViewDisplayer askForSpecies = () -> view.askForSpecies();
        ViewDisplayer askToTryAgain = () -> view.displayTryAgainMessage();
        return (Species) getValidChoice(model.getSpeciesOptions(), askForSpecies, askToTryAgain);
    }

    private Rotation getShiftRotation() {
        ViewDisplayer askForRotation = () -> view.askForRotation();
        ViewDisplayer askToTryAgain = () -> view.displayTryAgainMessage();
        return (Rotation) getValidChoice(model.getShiftRotationOptions(), askForRotation, askToTryAgain);
    }

    private void createCaptain() {
        boolean invalidInput = true;
        boolean quitEarly = false;
        ViewDisplayer askToTryAgain = () -> view.displayTryAgainMessage();

        while (invalidInput) {
            view.askForCaptain();

            view.askForName();
            String name = scanner.nextLine().strip();

            Rank rank = getRank();
            // if rank is null then user wants to exit early
            if (rank == null) {
                quitEarly = true;
                break;
            }

            Rotation rotation = getShiftRotation();
            // if rotation is null then user wants to exit early
            if (rotation == null) {
                quitEarly = true;
                break;
            }

            ViewDisplayer askForSpecies = () -> view.askToDiscloseSpecies();
            boolean discloseSpecies = getYesOrNo(askForSpecies, askToTryAgain);


            ArrayList<Species> heritage = new ArrayList<>();
            if (discloseSpecies) {
                view.debugDisplay("going to enter species disclosure menu");
                heritage = runSpeciesSelectionMenu(); // allow them to continue adding species until they stop or quit
                view.debugDisplay("exited species disclosure menu");
            }

            try {
                //make sure we can create the crew member and assign them as the commander of entire crew
                StarFleetOfficer captain = new StarFleetOfficer(name, rank, Department.BRIDGE, rotation, heritage);
                this.model.setRoot(captain);
                view.displaySuccessfullyCreatedMember(captain); //give feedback so user know it worked
                invalidInput = false;
            } catch (IllegalArgumentException e) {
                view.displayError(e);
            }
        }
    }

    private ArrayList<Species> runSpeciesSelectionMenu() {
        ArrayList<Species> heritage = new ArrayList<>();

        boolean wantToContinue = true;
        while (wantToContinue) {
            Species currentSpecies = getSpecies();

            // check if they want to quit early
            if (currentSpecies == null) {
                view.debugDisplay("chose to exit species selection menu early");
                return heritage;
            }
            heritage.add(currentSpecies);

            //check if they want to continue or want to stop
            ViewDisplayer askIfContinue = () -> view.askIfWantToContinueGivingSpecies();
            wantToContinue = getYesOrNo(askIfContinue, () -> view.displayTryAgainMessage());
        }

        view.debugDisplay("finished getting species now returning array");
        return heritage;
    }
}

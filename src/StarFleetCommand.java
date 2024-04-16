import model.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class StarFleetCommand implements ICrewController {
    final Readable in;
    private Scanner scanner;
    private ICrewModel<ICrewMember> model;
    private ICrewView view;

    public StarFleetCommand(Readable inputStream) {
        this.in = inputStream;
    }

    @Override
    public void go(ICrewModel<ICrewMember> model, ICrewView view) {
        this.model = model;
        this.view = view;
        boolean exit = false;
        scanner = new Scanner(in);

        this.view.welcomeMessage();
        boolean createdCrew = initializeCrew();

        if (createdCrew == false) {
            //they declined to initialize a crew so there's nothing left to do
            view.goodbyeMessage();
            return;
        }

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

    private boolean initializeCrew() {
        boolean crewSuccessfullyInitialized = false;
        ViewDisplayer askToInitializeCrew = () -> view.askToCreateCrew();
        ViewDisplayer askToTryAgain = () -> view.displayTryAgainMessage();

        while (crewSuccessfullyInitialized == false) {
            int initializationMethod = getValidChoice(askToInitializeCrew, askToTryAgain, 2);

            if (initializationMethod == 0) {
                //then they want to create a crew by hand
                crewSuccessfullyInitialized = createCaptain(); //will be true when we successfully create a captain for the crew

            } else if (initializationMethod == 1) {
                //then they want to load in from a file
                crewSuccessfullyInitialized = loadFile(); //will be true when we successfully load in a file
            } else if (initializationMethod == -1) {
                //then they want to quit
                break;
            }
        }
        return crewSuccessfullyInitialized;
    }

    private boolean loadFile() {
        boolean validFile = false;

        while (validFile == false) {
            String filename = getStringData(() -> view.askForFileName());
            if (filename == null) {
                //they want to quit
                return validFile;
            }

            try {
                model.loadFromFile(filename);
                validFile = true; //leave the loop
            } catch (FileNotFoundException e) {
                view.displayError(e); //loading file didn't work, continue to loop
            }
        }

        return validFile; //we made it out of the loops so they must've successfully loaded a file
    }

    private boolean createCaptain() {
        ViewDisplayer askForCaptainParameters = () -> view.askForCaptain();
        boolean captainNotCreated = true;

        while (captainNotCreated) {
            ICrewMember captain = createCrewMember(askForCaptainParameters);
            if (captain == null) {
                //they want to quit early
                return false;
            }

            //make sure we can set the crew member they created as the root of the crew
            try {
                this.model.setRoot(captain);
                view.displaySuccessfullyCreatedMember(captain); //give feedback so user know it worked
                return true;
            } catch (IllegalArgumentException e) {
                view.displayError(e); //let user know there was an issue
                //loop again to get info
            }
        }
        return captainNotCreated;
    }

    private ICrewMember createCrewMember(ViewDisplayer createMemberPrompt) {
        boolean tryingToCreateNewMember = true;
        ViewDisplayer askToTryAgain = () -> view.displayTryAgainMessage();
        StarFleetOfficer newOfficer = null;

        while (tryingToCreateNewMember) {
            createMemberPrompt.display();

            String name = getStringData(() -> view.askForName());
            //if name is null that means user wants to exit early
            if (name == null) {
                return newOfficer;
            }


            Rank rank = getRank();
            // if rank is null then user wants to exit early
            if (rank == null) {
                return newOfficer;
            }

            Rotation rotation = getShiftRotation();
            // if rotation is null then user wants to exit early
            if (rotation == null) {
                return newOfficer;
            }

            ViewDisplayer askForSpecies = () -> view.askToDiscloseSpecies();
            boolean discloseSpecies = getYesOrNo(askForSpecies, askToTryAgain);


            ArrayList<Species> heritage = new ArrayList<>();
            if (discloseSpecies) {
                view.debugDisplay("going to enter species disclosure menu");
                heritage = runSpeciesSelectionMenu(); // allow them to continue adding species until they stop or quit
                view.debugDisplay("exited species disclosure menu");
            }

            //make sure we can create the crew member without errors
            try {
                newOfficer = new StarFleetOfficer(name, rank, Department.BRIDGE, rotation, heritage);
                view.displaySuccessfullyCreatedMember(newOfficer); //give feedback so user know it worked
                tryingToCreateNewMember = false;
            } catch (IllegalArgumentException e) {
                view.displayError(e);
            }
        }
        return newOfficer;
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

    private String getStringData(ViewDisplayer userPrompt) {
        String userInput = null;

        userPrompt.display();
        view.displayQuitOption();
        userInput = scanner.nextLine().strip();

        try {
            int exitChoice = Integer.valueOf(userInput); //see if they gave us a number
            if (exitChoice == -1) {
                //if the number was -1 they want to quit
                return null; //return null so caller knows the user tried to quit
            }
            //if input was able to convert to int but didn't equal -1 it wasnt an exit request so just return
            return userInput;
        } catch (NumberFormatException e) {
            //user input wasn't even a number, they don't want to quit so return what they sent
            return userInput;
        }
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

    private ArrayList<Species> runSpeciesSelectionMenu() {
        ArrayList<Species> speciesArray = new ArrayList<>();

        boolean wantToContinue = true;
        while (wantToContinue) {
            Species currentSpecies = getSpecies();

            // check if they want to quit early
            if (currentSpecies == null) {
                view.debugDisplay("chose to exit species selection menu early");
                return speciesArray;
            }
            speciesArray.add(currentSpecies);

            //check if they want to continue or want to stop
            ViewDisplayer askIfContinue = () -> view.askIfWantToContinueGivingSpecies();
            wantToContinue = getYesOrNo(askIfContinue, () -> view.displayTryAgainMessage());
        }

        view.debugDisplay("finished getting species now returning array");
        return speciesArray;

    }
}

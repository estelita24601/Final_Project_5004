import model.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;

public class StarFleetCommand implements ICrewController {
    final Readable in;
    private Scanner scanner;
    private ICrewModel<ICrewMember> model;
    private ICrewView view;

    public StarFleetCommand(Readable inputStream) {
        this.in = inputStream;
    }

    /**
     * @param model (ICrewModel) the model we're using for this run of the program
     * @param view  (ICrewView) the view we're using for this run of the program
     */
    @Override
    public void go(ICrewModel<ICrewMember> model, ICrewView view) {
        //load in the model and view
        this.model = model;
        this.view = view;
        this.scanner = new Scanner(in); //create scanner to get input

        this.view.welcomeMessage();
        boolean createdCrew = initializeCrew(); //try to get user to create the crew from a file or by hand
        //check if user succesfulyl created crew or if they decided to quit entire program
        if (createdCrew == false) {
            //they declined to initialize a crew so there's nothing left to do
            view.goodbyeMessage();
            return;
        }

        boolean exit = false;
        while (!exit) {
            ViewDisplayer mainMenu =
                    () -> {
                        view.displayMainMenu();
                        view.debugDisplay("NOTE: only options 0 and 8 are functional\n");
                    };
            ViewDisplayer invalidChoiceMessage = () -> view.displayTryAgainMessage();
            //use helper to loop until user gives us a valid choice from the main menu
            int menuChoice = getValidChoice(mainMenu, invalidChoiceMessage, 8);

            if (menuChoice == 0) {
                //add new crew member
                addNewCrewMember();
            } else if (menuChoice == 1) {
                //remove crew member
                removeCrewMemberMenu();
            } else if (menuChoice == 2) {
                //edit crew member
                crewEditorMenu();
            } else if (menuChoice == 3) {
                //find crew member
                findCrewMemberMenu();
            } else if (menuChoice == 4) {
                //filter crew members
                filterCrewMemberMenu();
            } else if (menuChoice == 5) {
                //count crew members
                countCrewMemberMenu();
            } else if (menuChoice == 6) {
                //view schedule
                scheduleDisplayMenu();
            } else if (menuChoice == 7) {
                //edit schedule
                scheduleEditingMenu();
            } else if (menuChoice == 8) {
                view.displayEntireCrew(model);
            } else if (menuChoice == -1) {
                exit = true; //they want to leave so end the loop
            }
            // if they didn't decide to exit go back to main menu after they finished what they were doing
        }

        this.view.goodbyeMessage();
    }

    /**
     * Menu that lets user choose how they want to edit the schedule and then prints the results
     * will either keep going until it successfully edits the schedule or will until user asks to exit
     */
    private void scheduleEditingMenu() {
        view.debugDisplay("Sorry this feature isn't supported yet\n");
        /* psuedocode:
        while finishedEditing == false:
            get view to display different editing options (edit name, rank, department, species)
            int choice = getValidChoice()

            if choice == -1
                quit early
            else if choice == 0
                finishedEditing = try to edit name method //returns true if successfully edited name
            else if choice == 1
                finished editting = try to edit rank method //returns true if successful
            etc for all the other options...

         outside of loop:
         display success message
         display crew member with updated info
         */
    }

    //TODO
    private void scheduleDisplayMenu() {
        view.debugDisplay("Sorry this feature isn't supported yet\n");
        /* psuedocode:
        while finished == false
            Predicate<ICrewMember> memberIsOnDesiredShift

            desiredRotation = getValidChoice(rotation_options_list, choose_rotation_prompt, try_again_message)
            if desiredRotation == null
                finished == true; //aka quit early
            memberIsOnDesiredShift = member.shift.rotation == desiredRotation //update predicate with desired shift

            boolean wantToSpecifyDepartment = getYesOrNo(ask_about_department, try_again_message)

            if wantToSpecifyDepartment:
                desiredDepartment = getValidChoice()
                if desiredDepartment == null
                    //they want to quit specifying the department
                    break
                else
                    memberIsOnDesiredShift = (member) -> {
                        member.shift.rotation == desiredRotation && member.shift.department ==
                        desiredDepartment
                    }

            //now that we've defined our predicate
            //create a list of crew members that are working the shift use wants to see
            List<ICrewMember> membersOnDesiredShift = model.getMemberList(memberIsOnDesiredShift)

            model.display desired shift
            model.display members on desired shift
         */
    }

    //TODO
    private void countCrewMemberMenu() {
        view.debugDisplay("Sorry this feature isn't supported yet\n");
    }

    //TODO
    private void filterCrewMemberMenu() {
        view.debugDisplay("Sorry this feature isn't supported yet\n");
    }

    //TODO
    private void findCrewMemberMenu() {
        view.debugDisplay("Sorry this feature isn't supported yet\n");
    }

    //TODO
    private void crewEditorMenu() {
        view.debugDisplay("Sorry this feature isn't supported yet\n");
    }

    //TODO
    private void removeCrewMemberMenu() {
        view.debugDisplay("Sorry this feature isn't supported yet\n");
    }

    /**
     * helper method for when the user wants to add a new person to the crew
     */
    private void addNewCrewMember() {
        ViewDisplayer askForNewMemberInfo = () -> view.displayCreatNewCrewMemberMessage();
        ICrewMember newCrewMember = createCrewMember(askForNewMemberInfo); //keep on asking user to give info for new crew member
        if (newCrewMember == null) {
            // if they didn't create a crew member and it's just null that means they want to quit
            return;
        }


        //list of crew members that are valid options
        Function<ICrewMember, String> abbreviatedInfo = (person) -> String.format("%s %s (%s)", person.getRank(),
                person.getName(), person.getJob());
        List<String> superiorOfficerList = model.getMemberInfoList(model.getCommandingOfficerRequirement(), abbreviatedInfo);

        //specify the prompt and error message user gets when giving invalid input
        ViewDisplayer askForSuperiorOfficer = () -> {
            view.askForSuperiorOfficer();
            view.displayQuitOption();
        };
        ViewDisplayer tryAgain = () -> view.displayTryAgainMessage();
        //use view displayers to get the name of the superior officer
        String nameOfSuperior = (String) getValidChoice(superiorOfficerList.toArray(), askForSuperiorOfficer, tryAgain);
        //double check they don't want to quit
        if (nameOfSuperior == null) {
            //they want to quit
            return;
        }

        model.addCrewMember(newCrewMember, (officer) -> officer.getName().equalsIgnoreCase(nameOfSuperior));
        view.displaySuccessfullyCreatedMember(newCrewMember);
    }

    /**
     * @return (boolean)
     * true if user successfully initialized the crew
     * false if the user asked to quit before initializing the crew
     */
    private boolean initializeCrew() {
        //what we want the view to do while we're trying to get valid input
        ViewDisplayer askToInitializeCrew = () -> view.askToCreateCrew();
        ViewDisplayer askToTryAgain = () -> view.displayTryAgainMessage();

        boolean crewSuccessfullyInitialized = false;
        while (crewSuccessfullyInitialized == false) {
            //prompt the user for input until we get valid response
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
        return crewSuccessfullyInitialized; //will only be true if createCaptain() or loadFile() returned true
    }

    /**
     * @return (boolean)
     * true if program was able to open the file and read in it's data
     * false if program was unable to open a file and the user decided to qui
     */
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

    /**
     * '
     * TODO
     *
     * @return
     */
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

    /**
     * TODO
     *
     * @param createMemberPrompt (ViewDisplayer)
     * @return
     */
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

            Department department = getDepartment();
            //if department is null then user wants to exit early
            if (department == null) {
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

    /**
     * @param options
     * @param choicePrompt
     * @param invalidResponse
     * @return
     */
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

    /**
     * @param choicePrompt
     * @param invalidResponse
     * @param numOptions
     * @return
     */
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
            if (choice == -1 || choice <= numOptions) {
                receivedValidChoice = true;
            } else {
                // number received wasn't one of the options
                invalidResponse.display();
            }
        }
        return choice;
    }

    /**
     * @param choicePrompt
     * @param invalidResponse
     * @return
     */
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

    /**
     * @param userPrompt
     * @return
     */
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

    private Department getDepartment() {
        ViewDisplayer askForDepartment = () -> view.askForDepartment();
        ViewDisplayer askToTryAgain = () -> view.displayTryAgainMessage();
        return (Department) getValidChoice(model.getDepartmentOptions(), askForDepartment, askToTryAgain);
    }

    /**
     * @return
     */
    private ArrayList<Species> runSpeciesSelectionMenu() {
        ArrayList<Species> speciesArray = new ArrayList<>();

        boolean wantToContinue = true;
        while (wantToContinue) {
            Species currentSpecies = getSpecies();

            // check if they want to quit early
            if (currentSpecies == null) {
                return speciesArray; //return whatever we have
            }
            speciesArray.add(currentSpecies);

            //check if they want to continue or want to stop
            ViewDisplayer askIfContinue = () -> view.askIfWantToContinueGivingSpecies();
            wantToContinue = getYesOrNo(askIfContinue, () -> view.displayTryAgainMessage());
        }
        return speciesArray;
    }
}

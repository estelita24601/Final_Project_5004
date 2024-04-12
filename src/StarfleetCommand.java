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
        boolean exit = false;
        scanner = new Scanner(in);

        view.welcomeMessage();

        createCaptain();

        while (!exit) {
            // Function<String, Integer> choiceConverter = (str) -> Integer.parseInt(str);
            // ViewDisplayer mainMenuPrompt = () -> view.displayMainMenu();
            // int menuChoice = getValidInput(choiceConverter, mainMenuPrompt);

            if (menuChoice == 0) {
                runCrewCountingMenu();
            } else if (menuChoice == 1) {
                runMemberInformationMenu();
            } else if (menuChoice == 2) {
                runCrewEditorMenu();
            } else if (menuChoice == 3) {
                runScheduleMenu();
            } else if (menuChoice == 4) {
                exit = true;
            }
            //if they didn't decide to exit go back to main menu after they finished what they were doing
        }

        view.goodbyeMessage();
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

    //NOTE: trying to replace getValidInput with this instead
    //view will present numbered options and we just need to make sure we got valid number, no more words
    //IDEA: maybe return object from options list that was selected instead? and return null for exit command
    private int getValidChoice(Object[] options, ViewDisplayer choicePrompt, ViewDisplayer invalidResponse){
        boolean receivedValidChoice = false;
        while(!receivedValidChoice){
            choicePrompt.display();
            view.displayOptions(options);
            String userInput = scanner.nextLine().strip();
            int choice; 
            try{
                choice = Integer.valueOf(userInput);
            }catch (){ //todo: look up exact name of error
                //didn't even receive a number
                invalidResponse.display();
            }
            if(choice == -1 || choice < options.length){
                receivedValidChoice = true;
            }else{
                //number received wasn't one of the options
                invalidResponse.display(); //todo: improve how this fits with try catch
            }
        }    
        return choice;   
    }


    private void createCaptain() {
        boolean invalidInput = true;
        boolean quitEarly = false;

        while (invalidInput) {
            view.askForCaptain();

            view.askForName();
            String name = scanner.nextLine().strip();

            Function<String, Rank> rankConverter = (str) -> convertInputToEnum(str, model.getRankOptions()); //lambda that tries to convert input to rank enum
            ViewDisplayer askForRank = () -> view.askForRank(); //lambda that gets the view to ask for a rank
            Rank rank = getValidInput(rankConverter, askForRank); //give two lambdas to helper that will run loop until receiving valid input or until user quits
            //if getValidInput returned null then user wants to exit early
            if (rank == null) {
                quitEarly = true;
                break;
            }

            Function<String, Rotation> rotationConverter = (str) -> convertInputToEnum(str, model.getShiftRotationOptions());
            ViewDisplayer askForRotation = () -> view.askForRotation();
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
}

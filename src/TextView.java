import model.ICrewMember;
import model.ICrewModel;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;


public class TextView implements ICrewView {
    private final OutputStream out;

    public TextView(OutputStream outputDestination) {
        this.out = outputDestination;
    }

    private void printToTerminal(String newMessage) {
        // output stream needs to receive array of bytes
        byte[] byteMessage = newMessage.getBytes(StandardCharsets.UTF_8);
        try {
            out.write(byteMessage); //try to add array of bytes to our output stream
            out.flush(); //flush everything in our stream to the terminal
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void welcomeMessage() {
        printToTerminal("Welcome to Starfleet Crew Management Simulator\n");
    }

    @Override
    public void goodbyeMessage() {
        printToTerminal("Goodbye\n");
    }

    @Override
    public void askForCaptain() {
        printToTerminal("Specify parameters for commanding officer\n");
    }

    @Override
    public void askForName() {
        printToTerminal("Specify first and last name\n");
    }

    @Override
    public void askForRank() {
        printToTerminal("Specify rank\n");
    }

    @Override
    public void askForRotation() {
        printToTerminal("Specify rotation\n");
    }

    @Override
    public void askForDepartment() {
        printToTerminal("Specify department\n");
    }

    @Override
    public void askForSpecies() {
        printToTerminal("Specify species\n");
    }

    @Override
    public void askToDiscloseSpecies() {
        printToTerminal("Do you wish to specify crew member's heritage?\n");
    }

    @Override
    public void askIfWantToContinueGivingSpecies() {
        printToTerminal("Do you wish to continue specifying species?\n");
    }

    @Override
    public void displayOptions(Object[] optionsList) {
        printToTerminal(String.format("\t-1. Terminate Current Operation\n")); //show exit option first
        //go through every option in the list and print it to terminal with an associated integer
        for (int i = 0; i < optionsList.length; i++) {
            printToTerminal(String.format("\t%d. %s\n", i, optionsList[i]));
        }
    }

    @Override
    public void displayMainMenu() {
        printToTerminal("\nSpecify crew operation\n");
        String[] mainMenuOptions = {"Add New Crew Member", "Remove Crew Member", "Edit Crew Member", "Find Crew " +
                "Member", "Filter Crew Members", "Count Crew Members", "View Shift Schedule", "Edit Shift Schedule",
                "List All Crew Members"};
        displayOptions(mainMenuOptions);
    }

    @Override
    public void displayError(Exception e) {
        String errorMessage = String.format("WARNING:\n\t%s\n", e.getMessage());
        printToTerminal(errorMessage);
    }

    @Override
    public void displayTryAgainMessage() {
        printToTerminal("Directions unclear. Please repeat command.\n");
    }

    @Override
    public void displayYesOrNo() {
        String[] yesOrNo = {"YES", "NO"};
        printToTerminal(String.format("0. NO\n"));
        printToTerminal(String.format("1. YES\n"));
    }

    @Override
    public void displayCrewMember(ICrewMember crewMember) {
        printToTerminal(crewMember.toString());
    }

    @Override
    public void displayEntireCrew(ICrewModel entireCrew) {
        printToTerminal(entireCrew.toString());
    }

    @Override
    public void displayListOfCrewMembers(ArrayList<ICrewMember> crewMembers) {
        for (ICrewMember crewMember : crewMembers) {
            printToTerminal(crewMember.toString());
        }
    }

    @Override
    public void debugDisplay(String debugMessage) {
        printToTerminal(String.format("DEBUG:\t%s\n", debugMessage));
    }

    @Override
    public void displaySuccessfullyCreatedMember(ICrewMember newCrewMember) {
        printToTerminal("Successfully Initialized Crew Member:\n");
        displayCrewMember(newCrewMember);
    }

    @Override
    public void displayCreatNewCrewMemberMessage() {
        printToTerminal("Specify parameters for new crew member\n");
    }

    @Override
    public void askForFileName() {
        printToTerminal("Specify filename: ");
    }

    @Override
    public void askToCreateCrew() {
        printToTerminal("Please create crew:\n");
        String[] crewCreationOptions = {"Create New Commanding Officer", "Load Existing Crew From File"};
        displayOptions(crewCreationOptions);
    }

    @Override
    public void displayQuitOption() {
        printToTerminal("or enter \"-1\" to terminate current operation\n");
    }

    @Override
    public void askForSuperiorOfficer() {
        printToTerminal("Please specify superior officer for this crew member\n");
    }

    @Override
    public void displayNumberCounted(int number) {
        printToTerminal(String.format("Counted %d crew members that fit specifications\n", number));
    }
}

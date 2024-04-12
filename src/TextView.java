import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
//SPECIFY __ parameters
//specify
//I'm sorry I can't do that
//warning:
//computer activate ____ program
//computer resume
//


public class TextView implements ICrewView {
    byte[] byteMessage;
    private OutputStream out; //.write(byte array) and .flush() to display

    public TextView(OutputStream outputDestination) {
        this.out = outputDestination;
    }

    private void printToTerminal(String newMessage) {
        byteMessage = newMessage.getBytes(StandardCharsets.UTF_8);
        try {
            out.write(byteMessage);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void welcomeMessage() {
        printToTerminal("Welcome to Starfleet Crew Management Simulator\n");
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
    public void askToDiscloseSpecies() {
        printToTerminal("Do you wish to specify crew member's heritage?\n");
    }

    @Override
    public void askIfFinishedGivingHeritage() {
        printToTerminal("");
    }

    @Override
    public void askForSpecies() {
        printToTerminal("Specify species\n");
    }

    @Override
    public void displayOptions(Object[] optionsList) {
        for (int i = 0; i < optionsList.length; i++) {
            printToTerminal(String.format("\t%d. %s\n", i, optionsList[i]));
        }
    }

    @Override
    public void displayMainMenu() {
        printToTerminal("Specify crew operation");
        String[] mainMenuOptions = {"Determine crew demographics", "Obtain crew member information", "Edit Crew", "Scheduling"};
        displayOptions(mainMenuOptions);
    }

    @Override
    public void displayError(Exception e) {
        String errorMessage = String.format("WARNING:\n\t%s\n", e.getMessage());
        printToTerminal(errorMessage);
    }

    @Override
    public void goodbyeMessage() {

    }

    @Override
    public void displayTryAgainMessage() {
        printToTerminal("Directions unclear. Please repeat command.");
    }

    @Override
    public void displayYesOrNo() {
        String[] yesOrNo = {"YES", "NO"};
        displayOptions(yesOrNo);
    }
}
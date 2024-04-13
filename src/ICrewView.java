import java.util.ArrayList;

public interface ICrewView {
    void welcomeMessage();

    void goodbyeMessage();

    void askForCaptain();

    void askForName();

    void askForRank();

    void askForRotation();

    void askForDepartment();

    void askForSpecies();

    void askToDiscloseSpecies();

    void askIfWantToContinueGivingSpecies();

    void displayOptions(Object[] optionsList);

    void displayMainMenu();

    void displayError(Exception e);

    void displayTryAgainMessage();

    void displayYesOrNo();

    void displayCrewMember(ICrewMember crewMember);

    void displayEntireCrew(ICrewModel entireCrew);

    void displayListOfCrewMembers(ArrayList<ICrewMember> crewMembers);

    void debugDisplay(String debugMessage);

    void displaySuccessfullyCreatedMember(ICrewMember newCrewMember);
}

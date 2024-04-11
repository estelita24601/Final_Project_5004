import model.Species;

public interface ICrewView {
    void welcomeMessage();

    void askForCaptain();

    void askForNewCrewMembersName();

    void askForNewCrewMembersRank();

    void askForMembersNewRotation();

    void askToDiscloseSpecies();

    void displaySpeciesOptions(Species[] speciesOptions);

    void displayMainMenu();

    void goodbyeMessage();

    void displayError(Exception e);

    void displayTryAgainMessage();
}

public interface ICrewView {
    void welcomeMessage();

    void goodbyeMessage();

    void askForCaptain();

    void askForName();

    void askForRank();

    void askForRotation();

    void askForDepartment();

    void askToDiscloseSpecies();

    void askIfFinishedGivingHeritage();

    void askForSpecies();

    void displayOptions(Object[] optionsList);

    void displayMainMenu();

    void displayError(Exception e);

    void displayTryAgainMessage();

    void displayYesOrNo();

}

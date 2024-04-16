package view;

import model.ICrewMember;
import model.ICrewModel;

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

    void displayEntireCrew(ICrewModel<ICrewMember> entireCrew);

    void displayListOfCrewMembers(ArrayList<ICrewMember> crewMembers);

    void debugDisplay(String debugMessage);

    void displaySuccessfullyCreatedMember(ICrewMember newCrewMember);

    void displayCreatNewCrewMemberMessage();

    void askForFileName();

    void askToCreateCrew();

    void displayQuitOption();

    void askForSuperiorOfficer();

    void displayNumberCounted(int number);
}

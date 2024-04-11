import model.Species;

public class TextView implements ICrewView {
    private Appendable out;

    public TextView(Appendable outputStream) {
        this.out = outputStream;
    }

    @Override
    public void welcomeMessage() {

    }

    @Override
    public void askForCaptain() {
        //please create the commanding officer for this crew
    }

    @Override
    public void askForNewCrewMembersName() {
        //what is the name of this new crew member?

    }

    @Override
    public void askForNewCrewMembersRank() {
        //what is the rank of this new crew member?

    }

    @Override
    public void askForMembersNewRotation() {
        //what rotation do you want to assign this crew member to?

    }

    @Override
    public void askToDiscloseSpecies() {
        //do you wish to disclose their heritage?
        //YES or NO
    }

    @Override
    public void displaySpeciesOptions(Species[] speciesOptions) {
        //You may select from the following species:
        //for i in range length of speciesOptions
        //String.format("%d. %s", i, speciesOptions[i]);
    }

    @Override
    public void displayMainMenu() {
//        What do you want to do with your crew?
//            1. count crew members
//            2. get full information on crew members
//            3. list crew members
//            4. scheduling
//            5. edit crew

    }

    @Override
    public void goodbyeMessage() {

    }
}

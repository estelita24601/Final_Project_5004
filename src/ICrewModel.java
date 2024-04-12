import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public interface ICrewModel<ICrewMember> {
    //enum values for menu options
    Rank[] getRankOptions();

    Species[] getSpeciesOptions();

    Rotation[] getShiftRotationOptions();

    Department[] getDepartmentOptions();

    public void setRoot(ICrewMember root);

    //folding to an integer
    int countAll();
    int countFilter(Predicate<ICrewMember> filter);

    //getting info on entire crew
    List<ICrewMember> getMemberList();
    List<ICrewMember> getMemberList(Predicate<ICrewMember> filter);
    List<String> getMemberInfoList(Predicate<ICrewMember> filter, Function<ICrewMember, String> convertInfoToStr);

    //getting info on individual member of the crew
    ICrewMember getCrewMember(Predicate<ICrewMember> findThisMember);
    String getCrewMemberInfo(Predicate<ICrewMember> findThisMember, Function<ICrewMember, String> convertInfoToStr);

    //used to update state of the crew
    void editCrewMember(Predicate<ICrewMember> findMemberToEdit, Consumer<ICrewMember> crewMemberEditor);
    void addCrewMember(ICrewMember newCrewMember, Predicate<ICrewMember> findNewSuperior);
    void removeCrewMember(Predicate<ICrewMember> findMemberToRemove);
    void reAssignTo(Predicate<ICrewMember> thisMember, Predicate<ICrewMember> findNewSuperior);
    void putInCommandOf(Predicate<ICrewMember> thisMember, Predicate<ICrewMember> findNewSubordinate);
}

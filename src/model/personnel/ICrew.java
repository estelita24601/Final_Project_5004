package model.personnel;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.List;

public interface ICrew<ICrewMember> {
    //folding to an integer
    int countAll();
    int countFilter(Predicate<ICrewMember> filter);

    //getting info on entire crew
    List<String> membersList();
    List<String> membersList(Predicate<ICrewMember> filter);
    List<String> membersNameList();
    List<String> membersNameList(Predicate<ICrewMember> filter);

    //getting info on individual member of the crew
    ICrewMember getCrewMember(Predicate<ICrewMember> thisMember);
    String getCrewMemberInfo(Predicate<ICrewMember> thisMember);
    String getCrewMemberInfo(Predicate<ICrewMember> thisMember, Function<ICrewMember, String> convertInfoToStr);

    //used to update state of the crew
    void editCrewMember(Predicate<ICrewMember> thisMember, Consumer<ICrewMember> crewEditor);
    void addCrewMember(ICrewMember newCrewMember, Predicate<ICrewMember> newSuperior);
    void removeCrewMember(Predicate<ICrewMember> identifier);
    void reAssignTo(Predicate<ICrewMember> thisMember, Predicate<ICrewMember> newSuperior);
    void putInCommandOf(Predicate<ICrewMember> thisMember, Predicate<ICrewMember> newSubordinate);
}

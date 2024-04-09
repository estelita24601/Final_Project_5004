package model.personnel;

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

    List<String> memberNameList();
    List<String> memberNameList(Predicate<ICrewMember> filter);

    //getting info on individual member of the crew
    String getCrewMemberInfo(String crewMemberName);
    String getCrewMemberInfo(String crewMemberName, Function<ICrewMember, String> convertInfoToStr);

    //used to update state of the crew
    void promote(String crewMemberName, Rank newRank);
    void demote(String crewMemberName, Rank newRank);
    void addCrewMember(String crewMemberName, String superiorsName);
    void removeCrewMember(String crewMemberName);
    void reAssignTo(String crewMemberName, String newSuperiorName);
    void putInCommandOf(String commandingMemberName, String newSubordinateName);
}

package personnel;

import java.util.function.Function;
import java.util.function.Predicate;
import java.util.List;

public interface ICrew {
    //folding to an integer
    int countAll();
//    int countRank(String rankName);
//    int countSpecies(String speciesName);
    int countFilter(Predicate<ICrewMember> filter);

    //getting info on entire crew
    List<String> membersList();
    List<String> membersList(Predicate<ICrewMember> filter);
//    List<String> memberRankList(String rankName);
//    List<String> memberSpeciesList(String speciesName);
//    List<String> memberDepartmentList(String departmentName);
//    List<String> dutyRoster (String shiftName);

    List<String> memberNameList();
//    List<String> memberNameList(Predicate<ICrewMember> filter);

    //getting info on individual member of the crew
    String getCrewMemberInfo(String crewMemberName);
    String getCrewMemberInfo(String crewMemberName, Function<ICrewMember, String> convertInfoToStr);
//    String getCrewMemberShift(String crewMemberName);
//    String getCrewMemberRank(String crewMemberName);
//    String getCrewMemberSpecies(String crewMemberName);
//    String getCrewMemberSuperior(String crewMemberName);
//    String getCrewMembersSubordinates(String crewMemberName);
//    String getCrewMemberDepartment(String crewMemberName);

    //used to update state of the crew
    void promote(String crewMemberName, String newRank);
    void demote(String crewMemberName, String newRank);
    void addCrewMember(String crewMemberName, String superiorsName);
    void removeCrewMember(String crewMemberName);
    void reAssignTo(String crewMemberName, String newSuperiorName);
    void putInCommandOf(String commandingMemberName, String newSubordinateName);
}

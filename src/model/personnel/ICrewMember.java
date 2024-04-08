package model.personnel;


import java.util.List;

public interface ICrewMember {
    void promote(Rank newRank);
    void demote(Rank newRank);
    String getName();
    List<String> getHeritage();


}

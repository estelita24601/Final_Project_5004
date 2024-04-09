package model.personnel;

import java.util.List;

public class NonCommandingOfficer implements ICrewMember {
    @Override
    public void promote(Rank newRank) {

    }

    @Override
    public void demote(Rank newRank) {

    }

    @Override
    public String getName() {
        return "";
    }

    @Override
    public List<String> getHeritage() {
        return List.of();
    }
}

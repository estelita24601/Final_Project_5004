import model.ICrewMember;

import java.util.function.Predicate;

public class FindByName implements Predicate<ICrewMember> {
    private String name;

    public FindByName(String nameWeWantToFind) {
        this.name = nameWeWantToFind;
    }

    @Override
    public boolean test(ICrewMember crewMember) {
        return crewMember.getName().equalsIgnoreCase(name);
    }
}


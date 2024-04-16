package model; /**
 * Functional class that extends predicate so we can more easily create predicates that find crew members by name
 */

import java.util.function.Predicate;

public class FindByName implements Predicate<ICrewMember> {
    private final String name;

    public FindByName(String nameWeWantToFind) {
        this.name = nameWeWantToFind;
    }

    @Override
    public boolean test(ICrewMember crewMember) {
        return crewMember.getName().equalsIgnoreCase(name);
    }
}


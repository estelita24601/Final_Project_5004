import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class StarFleetOfficer implements ICrewMember, Comparable<StarFleetOfficer> {
    private String fullName;
    private Rank rank;
    private Department job;
    private Shift shift;
    private ArrayList<Species> heritage;

    public StarFleetOfficer(String name, Rank rank, Department department, Rotation shifRotation) {
        this.fullName = name;
        this.rank = rank;
        this.job = department;
        this.heritage = new ArrayList<Species>();
        this.shift = new Shift(department, shifRotation);
    }

    public StarFleetOfficer(String name, Rank rank, Department department, Rotation shifRotation,
                            List<Species> heritage) {
        this(name, rank, department, shifRotation);
        this.heritage.addAll(heritage);
    }

    public StarFleetOfficer(String name, Rank rank, Department department, Rotation shiftRotation, Species heritage) {
        this(name, rank, department, shiftRotation);
        this.heritage.add(heritage);
    }

    @Override
    public int compareTo(StarFleetOfficer otherOfficer) {
        return this.rank.compareTo(otherOfficer.getRank());
    }

    @Override
    public int hashCode() {
        int result = 31;
        result = 31 * result + fullName.hashCode();
        result = 31 * result + rank.hashCode();
        result = 31 * result + heritage.hashCode();
        //making the hashcode result only dependent on name, rank and heritage
        //same as our equals method is only dependent on those three attributes
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof StarFleetOfficer)) {
            return false; //object given isn't even from right interface
        }
        //now that we know its safe we can typecast
        StarFleetOfficer otherOfficer = (StarFleetOfficer) o;

        if (!this.getName().equals(otherOfficer.getName())) {
            return false;
        }
        if (this.rank != otherOfficer.getRank()) {
            return false;
        }
        if (this.heritage != otherOfficer.getHeritage()) {
            return false;
        }
        return true; //if all that differs is their job and shift they're basically the same person
    }

    @Override
    public String toString() {
        StringBuilder heritageString = new StringBuilder();
        if (heritage.size() == 0) {
            heritageString.append("Undisclosed");
        } else {
            for (Species s : this.heritage) {
                heritageString.append(s.toString());
                heritageString.append(" ");
            }
        }

        return String.format("%s %s\n\tDepartment: %s\n\tSpecies: %s\n", rank, fullName, job, heritageString);
    }

    @Override
    public void promote(Rank newRank) {
        if (this.rank.compareTo(newRank) >= 0) {
            throw new IllegalArgumentException(String.format("%s is not a promotion from %s", newRank, this.rank));
        }
        this.rank = newRank;
    }

    @Override
    public void demote(Rank newRank) {
        if (this.rank.compareTo(newRank) <= 0) {
            throw new IllegalArgumentException(String.format("%s is not a demotion from %s", newRank, this.rank));
        }
        this.rank = newRank;
    }

    @Override
    public Rank getRank() {
        return this.rank;
    }

    @Override
    public void changeName(String newName) {
        this.fullName = newName;
    }

    @Override
    public String getName() {
        return this.fullName;
    }

    @Override
    public void setHeritage(ArrayList<Species> heritage) {
        this.heritage = heritage;
    }

    @Override
    public void addToHeritage(Species newSpecies) {
        for (Species s : this.heritage) {
            if (s.equals(newSpecies)) {
                throw new IllegalStateException(String.format("%s is already in crew member's heritage list!", s));
            }
        }
        this.heritage.add(newSpecies);
    }

    @Override
    public void removeFromHeritage(Species removedSpecies) {
        boolean success = this.heritage.remove(removedSpecies);
        if (!success) {
            throw new IllegalStateException(
                    String.format("Couldn't find %s in crew member's heritage to remove", removedSpecies));
        }
    }

    @Override
    public ArrayList<Species> getHeritage() {
        return this.heritage;
    }

    @Override
    public void editShift(Consumer<Shift> shiftEditor) {
        try {
            shiftEditor.accept(this.shift);
        } catch (Exception e) { // TODO: maybe make this more specific...?
            throw new IllegalArgumentException("Proposed edits to shift are not possible");
        }
    }

    @Override
    public Shift getShift() {
        return this.shift;
    }

    @Override
    public Department getJob() {
        return this.job;
    }

    @Override
    public void setShift(Shift newShift) {
        this.shift = newShift;
    }

    @Override
    public void setJob(Department newDepartment) {
        this.job = newDepartment;
    }


}
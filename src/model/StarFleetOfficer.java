package model;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class StarFleetOfficer implements ICrewMember, Comparable<StarFleetOfficer> {
    private String fullName;
    private Rank rank;
    private Boolean canCommand;
    private Department job;
    private Shift shift;
    private ArrayList<Species> heritage;

    public StarFleetOfficer(String name, Rank rank, Department department, Rotation shifRotation) {
        this.fullName = name;
        this.rank = rank;
        this.job = department;
        this.heritage = new ArrayList<Species>();
        this.shift = new Shift(department, shifRotation);
        this.setCommandAbility();
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

    private void setCommandAbility() {
        if (this.rank == Rank.TECHNICIAN) {
            this.canCommand = false;
        } else {
            this.canCommand = true;
        }
    }

    // TODO: maybe remove this field + method if I dont end up using it
    public boolean canBeCommandingOfficer() {
        return this.canCommand;
    }

    @Override
    public int compareTo(StarFleetOfficer otherOfficer) {
        return this.rank.compareTo(otherOfficer.getRank());
    }

    @Override
    public void promote(Rank newRank) {
        if (this.rank.compareTo(newRank) >= 0) {
            throw new IllegalArgumentException(String.format("%s is not a promotion from %s", newRank, this.rank));
        }
        this.rank = newRank;
        this.setCommandAbility();
    }

    @Override
    public void demote(Rank newRank) {
        if (this.rank.compareTo(newRank) <= 0) {
            throw new IllegalArgumentException(String.format("%s is not a demotion from %s", newRank, this.rank));
        }
        this.rank = newRank;
        this.setCommandAbility();
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
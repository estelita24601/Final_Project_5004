package model;

import java.time.Duration;
import java.time.LocalTime;
//
//ALPHA("Alpha", LocalTime.of(6, 0), LocalTime.of(12, 0)),
//BETA("Beta", LocalTime.of(12, 0), LocalTime.of(18, 0)),
//GAMMA("Gamma", LocalTime.of(18, 0), LocalTime.of(0, 0)),
//DELTA("Delta", LocalTime.of(0, 0), LocalTime.of(6, 0));

public class Shift {
    static final Duration MAX_HOURS = Duration.ofHours(10);
    static final LocalTime midnight = LocalTime.of(0, 0);
    static final LocalTime sixAM = LocalTime.of(6, 0);
    static final LocalTime noon = LocalTime.of(12, 0);
    static final LocalTime sixPM = LocalTime.of(18, 0);

    String name;
    LocalTime startTime;
    LocalTime endTime;
    Duration length;
    Department location;

    // constructor for custom shift
    public Shift(Department department, String name, LocalTime start, LocalTime end) {
        this.location = department;
        this.name = name;
        this.startTime = start;
        this.endTime = end;
        this.length = calculateDuration(start, end);
    }

    // constructor for default shifts
    public Shift(Department department, Rotation rotation) {
        String name;
        LocalTime start;
        LocalTime end;
        if (rotation == Rotation.ALPHA) {
            name = "Alpha";
            start = sixAM;
            end = noon;
        } else if (rotation == Rotation.BETA) {
            name = "Beta";
            start = noon;
            end = sixPM;
        } else if (rotation == Rotation.GAMMA) {
            name = "Gamma";
            start = sixPM;
            end = midnight;
        } else if (rotation == Rotation.DELTA) {
            name = "Delta";
            start = midnight;
            end = sixAM;
        }else{
            throw new IllegalArgumentException("Rotation given does not have default values");
        }

        this.location = department;
        this.name = name;
        this.startTime = start;
        this.endTime = end;
        this.length = calculateDuration(start, end);
    }

    @Override
    public String toString() {
        return String.format("%s (%s)\n\t%s - %s\n", name, location, startTime.toString(),
                endTime.toString());
    }

    public Department getDepartment() {
        return this.location;
    }

    public void setDepartment(Department newDepartment) {
        this.location = newDepartment;
    }

    public double getNumHours() {
        return (double) this.length.toHours();
    }

    public LocalTime getStartTime() {
        return this.startTime;
    }

    public LocalTime getEndTime() {
        return this.endTime;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String newName) {
        this.name = newName;
    }

    //TODO: make this more readable maybe split it up
    private boolean validateNewTime(LocalTime proposedStart, LocalTime proposedEnd) {
        Duration newLength = this.calculateDuration(proposedStart, proposedEnd);
        if (newLength.compareTo(MAX_HOURS) > 0) {
            return false; // proposed times creates a shift that's too long
        } else {
            this.startTime = proposedStart;
            this.endTime = proposedEnd;
            this.length = newLength;
            return true;
        }
    }

    private Duration calculateDuration(LocalTime proposedStart, LocalTime proposedEnd) {
        Duration newLength = Duration.between(proposedStart, proposedEnd);
        if (newLength.isNegative()) {
            newLength = newLength.plusHours(24);
        }
        return newLength;
    }


    public void changeStart(LocalTime newStart) {
        if (!validateNewTime(newStart, this.endTime)) {
            throw new IllegalStateException(String.format("Proposed start %s is incompatible with current end %s",
                    this.startTime, this.endTime));
        }
    }

    public void changeStart(int hour, int minute) {
        changeStart(LocalTime.of(hour, minute));
    }

    public void changeEnd(LocalTime newEnd) {
        if (!validateNewTime(this.startTime, newEnd)) {
            throw new IllegalStateException(String.format("Proposed end %s is incompatible with current start %s",
                    this.endTime, this.startTime));
        }
    }

    public void changeEnd(int hour, int minute) {
        changeEnd(LocalTime.of(hour, minute));
    }

    public void changeStartAndEnd(LocalTime newStart, LocalTime newEnd) {
        if (!validateNewTime(newStart, newEnd)) {
            throw new IllegalArgumentException(
                    String.format("Proposed time of %s - %s violates allowed length of a shift", newStart, newEnd));
        }
    }

    public void changeStartAndEnd(int startHour, int startMinute, int endHour, int endMinute) {
        LocalTime newStart = LocalTime.of(startHour, startMinute);
        LocalTime newEnd = LocalTime.of(endHour, endMinute);
        changeStartAndEnd(newStart, newEnd);
    }
}

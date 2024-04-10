package model.scheduling;

import model.Department;
import model.Rotation;

import java.time.Duration;
import java.time.LocalTime;

public class Shift {
    static final Duration MAX_HOURS = Duration.ofHours(10);
    String shiftName;
    LocalTime startTime;
    LocalTime endTime;
    Duration length;
    Department location;

    // constructor for custom shift
    public Shift(Department d, String name, LocalTime start, LocalTime end) {
        this.location = d;
        this.shiftName = name;
        this.startTime = start;
        this.endTime = end;
        this.length = this.calculateDuration(start, end);
    }

    // constructor for default shift
    public Shift(Department d, Rotation r) {
        this(d, r.name, r.start, r.end);
    }

    @Override
    public String toString() {
        return String.format("%s Shift: %s\n\t%s - %s\n", this.shiftName, this.location, this.startTime.toString(),
                this.endTime.toString());
    }

    public Department getDepartment() {
        return this.location;
    }

    public void setDepartment(Department newDepartment) {
        this.location = newDepartment;
    }

    public double getNumHours() {
        return Double.valueOf(this.length.toHours());
    }

    public LocalTime getStartTime() {
        return this.startTime;
    }

    public LocalTime getEndTime() {
        return this.endTime;
    }

    public String getShiftName() {
        return this.shiftName;
    }

    public void setShiftName(String newName) {
        this.shiftName = newName;
    }

    boolean validateNewTime(LocalTime proposedStart, LocalTime proposedEnd) {
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

    Duration calculateDuration(LocalTime proposedStart, LocalTime proposedEnd) {
        Duration newLength = Duration.between(proposedStart, proposedEnd);
        if (newLength.isNegative()) {
            newLength = newLength.plusHours(24);
        }
        return newLength;
    }

    public void changeStart(LocalTime newStart) {
        if (!this.validateNewTime(newStart, this.endTime)) {
            throw new IllegalStateException(String.format("Proposed start %s is incompatible with current end %s",
                    this.startTime, this.endTime));
        }
    }

    public void changeStart(int hour, int minute) {
        this.changeStart(LocalTime.of(hour, minute));
    }

    public void changeEnd(LocalTime newEnd) {
        if (!this.validateNewTime(this.startTime, newEnd)) {
            throw new IllegalStateException(String.format("Proposed end %s is incompatible with current start %s",
                    this.endTime, this.startTime));
        }
    }

    public void changeEnd(int hour, int minute) {
        this.changeEnd(LocalTime.of(hour, minute));
    }

    public void changeTimes(LocalTime newStart, LocalTime newEnd) {
        if (!this.validateNewTime(newStart, newEnd)) {
            throw new IllegalArgumentException(
                    String.format("Proposed time of %s - %s violates allowed length of a shift", newStart, newEnd));
        }
    }

    public void changeTimes(int startHour, int Startminute, int endHour, int Endminute) {
        LocalTime newStart = LocalTime.of(startHour, Startminute);
        LocalTime newEnd = LocalTime.of(endHour, Endminute);
        this.changeTimes(newStart, newEnd);
    }
}

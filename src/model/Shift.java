package model;

import java.time.Duration;
import java.time.LocalTime;
//
//ALPHA("Alpha", LocalTime.of(6, 0), LocalTime.of(12, 0)),
//BETA("Beta", LocalTime.of(12, 0), LocalTime.of(18, 0)),
//GAMMA("Gamma", LocalTime.of(18, 0), LocalTime.of(0, 0)),
//DELTA("Delta", LocalTime.of(0, 0), LocalTime.of(6, 0));

public class Shift {
    //Constants for creating the default shifts
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

    // constructor for default shifts
    public Shift(Department department, Rotation rotation) {
        String name;
        LocalTime start;
        LocalTime end;
        //decide which constants to use based on rotation received
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
        } else {
            throw new IllegalArgumentException("model.Rotation given does not have default values");
        }

        this.location = department;
        this.name = name;
        this.startTime = start;
        this.endTime = end;
        this.length = calculateDuration(start, end);
    }

    // constructor for custom shift
    public Shift(Department department, String name, LocalTime start, LocalTime end) {
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

    /**
     * @return (Department) the location/department this shift object is taking place in
     */
    public Department getDepartment() {
        return this.location;
    }

    /**
     * @param newDepartment (Department) set the location this shift is taking place in
     */
    public void setDepartment(Department newDepartment) {
        this.location = newDepartment;
    }

    /**
     * @return (double) the number of hours this shift lasts for
     */
    public double getNumHours() {
        return (double) this.length.toHours();
    }

    /**
     * @return (LocalTime) the time this shift starts
     */
    public LocalTime getStartTime() {
        return this.startTime;
    }

    /**
     * @return (LocalTime) the time this shift ends
     */
    public LocalTime getEndTime() {
        return this.endTime;
    }

    /**
     * @return (String) the name of this shift
     */
    public String getName() {
        return this.name;
    }

    /**
     * @param newName (String) the new name for this shift
     */
    public void setName(String newName) {
        this.name = newName;
    }

    /**
     * helper method that makes sure we don't make this shift too long
     *
     * @param proposedStart (LocalTime) the time this shift would start if changes were made
     * @param proposedEnd   (LocalTime) the time this shift would end if changes were made
     * @return true if proposed times will not cause a labor rights violation
     * false if proposed times would cause shift to be too long
     */
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

    /**
     * private helper that helps calculate how long a shift would be after proposed changes to the start and or end
     * time. Also accounts for start and end time being on different days causing a negative duration
     *
     * @param proposedStart (LocalTime) when the shift would start if changes were made
     * @param proposedEnd   (LocalTime) when the shift would end if changes were made
     * @return (Duration) the amount of time between the start and end times
     */
    private Duration calculateDuration(LocalTime proposedStart, LocalTime proposedEnd) {
        Duration newLength = Duration.between(proposedStart, proposedEnd);
        if (newLength.isNegative()) {
            //fix duration if end time was the next day and caused negative amount of time
            newLength = newLength.plusHours(24);
        }
        return newLength;
    }

    /**
     * @param newStart (LocalTime) the time you want the shift to start at
     * @throws IllegalStateException if new start time would cause a labor violation
     */
    public void changeStart(LocalTime newStart) {
        if (!validateNewTime(newStart, this.endTime)) {
            throw new IllegalStateException(String.format("Proposed start %s is incompatible with current end %s",
                    this.startTime, this.endTime));
        }
    }

    /**
     * method override of changeStart(LocalTime)
     *
     * @param hour   (int) the hour of the new start time
     * @param minute (int) the minute of the new start time
     * @throws IllegalStateException if new start time would cause a labor violation
     */
    public void changeStart(int hour, int minute) {
        changeStart(LocalTime.of(hour, minute));
    }

    /**
     * @param newEnd (LocalTime) the new time you want the shift to end
     * @throws IllegalStateException if new end time would cause a labor violation
     */
    public void changeEnd(LocalTime newEnd) {
        if (!validateNewTime(this.startTime, newEnd)) {
            throw new IllegalStateException(String.format("Proposed end %s is incompatible with current start %s",
                    this.endTime, this.startTime));
        }
    }

    /**
     * method override of changeEnd(LocalTime)
     *
     * @param hour   (int) the hour of the new end time
     * @param minute (int) the minute of the new end time
     * @throws IllegalStateException if new end time would cause a labor violation
     */
    public void changeEnd(int hour, int minute) {
        changeEnd(LocalTime.of(hour, minute));
    }

    /**
     * change the start and end time together
     *
     * @param newStart (LocalTime) new time you want the shift to start at
     * @param newEnd   (LocalTime) new time you want the shift to end at
     * @throws IllegalStateException if the new start or end time would cause a labor violation
     */
    public void changeStartAndEnd(LocalTime newStart, LocalTime newEnd) {
        if (!validateNewTime(newStart, newEnd)) {
            throw new IllegalArgumentException(
                    String.format("Proposed time of %s - %s violates allowed length of a shift", newStart, newEnd));
        }
    }

    /**
     * method override for changeStartAndEnd
     *
     * @param startHour   (int) the hour of the new time you want the shift to START at
     * @param startMinute (int) the minute of the new time you want the shift to START at
     * @param endHour     (int) the hour of the new time you want the shift to END at
     * @param endMinute   (int) the minute of the new time you want the shift to END at
     */
    public void changeStartAndEnd(int startHour, int startMinute, int endHour, int endMinute) {
        LocalTime newStart = LocalTime.of(startHour, startMinute);
        LocalTime newEnd = LocalTime.of(endHour, endMinute);
        changeStartAndEnd(newStart, newEnd);
    }
}

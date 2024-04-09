package model.scheduling;

import java.time.LocalTime;

public class DeltaShift extends Shift{
    public DeltaShift(Department department) {
        super(department, "Delta", LocalTime.of(0, 0), LocalTime.of(6, 0));
    }
}

package model.scheduling;

import java.time.LocalTime;

public class BetaShift extends Shift{
    public BetaShift(Department department) {
        super(department, "Beta", LocalTime.of(12, 0), LocalTime.of(18, 0));
    }
}

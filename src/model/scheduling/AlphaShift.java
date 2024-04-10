package model.scheduling;

import model.Department;

import java.time.LocalTime;

public class AlphaShift extends Shift{
    public AlphaShift(Department department) {
        super(department, "Alpha", LocalTime.of(6, 0), LocalTime.of(12, 0));
    }
}

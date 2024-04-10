package model.scheduling;

import model.Department;

import java.time.LocalTime;

public class GammaShift extends Shift{
    public GammaShift(Department department){
        super(department, "Gamma", LocalTime.of(18, 0), LocalTime.of(0, 0));
    }
}

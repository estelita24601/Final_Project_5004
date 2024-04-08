package model.scheduling;

public class AlphaShift extends Shift{

    public AlphaShift(Department d) {
        super(d);
        this.shiftName = "Alpha";
        this.startTime = 600;
        this.endTime = 1200;
        this.length = 6;
    }
}

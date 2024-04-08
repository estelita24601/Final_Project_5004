package scheduling;

import personnel.ICrewMember;
import java.time.Duration;
import java.sql.Time;


public abstract class Shift {
    String shiftName;
    int startTime;
    int endTime;
    double length;
    Department location;

    public Shift(Department d){
        this.location = d;
    }

    @Override
    public String toString(){
        return String.format("%s Shift: %s\n\t%d - %d\n", shiftName, location, startTime, endTime);
    }

    public Department getDepartment(){
        return this.location;
    }

    public void setDepartment(Department newDepartment){
        this.location = newDepartment;
    }

    public double getNumHours(){
        return this.length;
    }

    double calculateDuration(){
        //TODO
        return 0;
    }

    boolean validateNewTime(){
        //return true;//if shift wouldnt be too long
        return false; //if shift is too long
    }

    public void changeStart(){
        //TODO
        //this.updateNumHours();
    }

    public void changeEnd(){
        //TODO
        //this.updateNumHours();
    }
}

package starships;

public interface SpaceFaringVessel {
    void goToQuarterImpulse();

    void goToHalfImpulse();

    void goToFullImpulse();

    void fullStop();

    void raiseShields();

    void dropShields();

    void setCourseFor(double xCoord, double yCoord, double zCoord);

    double[] getCoordinates();

    double getCurrentSpeed();

    String getName();
}

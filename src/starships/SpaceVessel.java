package starships;

import model.ICrewMember;
import model.ICrewModel;

public class SpaceVessel implements SpaceFaringVessel {
    String name;
    double maxImpulse;
    double currentSpeed;
    double xCoordinate;
    double yCoordinate;
    double zCoordinate;
    boolean shieldsRaised;
    ICrewModel<ICrewMember> crewComplement;

    public SpaceVessel(String shipName, double maximumImpulse) {
        this.name = shipName;
        this.maxImpulse = maximumImpulse;
        this.currentSpeed = 0;
        this.xCoordinate = 0;
        this.yCoordinate = 0;
        this.zCoordinate = 0;
        this.shieldsRaised = false;
    }

    @Override
    public void goToQuarterImpulse() {
        this.currentSpeed = maxImpulse / 4;

    }

    @Override
    public void goToHalfImpulse() {
        this.currentSpeed = maxImpulse / 2;
    }

    @Override
    public void goToFullImpulse() {
        this.currentSpeed = maxImpulse;
    }

    @Override
    public void fullStop() {
        this.currentSpeed = 0;
    }

    @Override
    public void raiseShields() {
        this.shieldsRaised = true;
    }

    @Override
    public void dropShields() {
        this.shieldsRaised = false;
    }

    @Override
    public void setCourseFor(double xCoord, double yCoord, double zCoord) {
        this.xCoordinate = xCoord;
        this.yCoordinate = yCoord;
        this.zCoordinate = zCoord;
    }

    @Override
    public double[] getCoordinates() {
        double[] coordinates = new double[3];
        coordinates[0] = this.xCoordinate;
        coordinates[1] = this.yCoordinate;
        coordinates[2] = this.zCoordinate;
        return coordinates;
    }

    @Override
    public double getCurrentSpeed() {
        return this.currentSpeed;
    }

    @Override
    public String getName() {
        return this.name;
    }
}

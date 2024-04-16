package starships;

import model.ICrewMember;

import java.time.Duration;
import java.util.Random;

public class StarFleetShip extends SpaceVessel implements WarpCapable, CombatShip {
    double maximumWarp;
    boolean isCloaked;
    boolean isAtWarp;
    double[] currentTarget;
    int weaponStrength;

    public StarFleetShip(String shipName, double maximumImpulse, double maximumWarp, int weaponStrength) {
        super(shipName, maximumImpulse);
        this.maximumWarp = maximumWarp;
        this.isAtWarp = false;
        this.currentTarget = null;
        this.weaponStrength = weaponStrength;
    }

    public void selfDestruct(ICrewMember officerRequestingSelfDestruct, Duration delay) {
        ICrewMember shipCaptain = crewComplement.getRoot().getData();
        if (shipCaptain == officerRequestingSelfDestruct) {
            System.out.printf("%s to auto-destruct", delay);
            //add some method that overloads the warp core or something
        }
    }

    @Override
    public void targetWeapons(double xCoord, double yCoord, double zCoord) {
        double[] target = new double[3];
        target[0] = this.xCoordinate;
        target[1] = this.yCoordinate;
        target[2] = this.zCoordinate;
        this.currentTarget = target;

    }

    @Override
    public int fireWeapons() {
        int damageDealt = 0;

        //only fire if we have a target set
        if (this.currentTarget != null) {
            //determine how much damage is dealt
            //make it psuedo random since enemy ship could have shields or be doing evasive manuvers
            Random rand = new Random();
            damageDealt = rand.nextInt(weaponStrength);
        }

        return damageDealt;
    }

    @Override
    public void standDown() {
        this.currentTarget = null;
    }

    @Override
    public double getMaximumWarp() {
        return this.maximumWarp;
    }

    @Override
    public void goToWarp(double warpSpeed) {
        this.isAtWarp = true;
        this.currentSpeed = warpSpeed;
    }

    @Override
    public void modifyWarpSpeed(double newWarpSpeed) {
        if (this.isAtWarp == false) {
            throw new IllegalStateException("Unable to modify warp speed due to not being at warp");
        }
        this.currentSpeed = newWarpSpeed;
    }

    @Override
    public void dropOutOfWarp() {
        this.isAtWarp = false;
        this.currentSpeed = this.maxImpulse;
    }
}

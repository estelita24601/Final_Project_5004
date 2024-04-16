package starships;

import java.util.Random;

public class BirdOfPrey extends SpaceVessel implements WarpCapable, Cloakable, CombatShip {
    double maximumWarp;
    boolean isCloaked;
    boolean isAtWarp;
    double[] currentTarget;
    int weaponStrength;

    public BirdOfPrey(String name, double maxImpulse, double maxWarp, int weaponStrength) {
        super(name, maxImpulse);
        this.maximumWarp = maxWarp;
        this.isCloaked = false;
        this.isAtWarp = false;
        this.currentTarget = null;
        this.weaponStrength = weaponStrength;
    }

    @Override
    public void activateCloak() {
        this.isCloaked = true;
    }

    @Override
    public void dropCloak() {
        this.isCloaked = false;
    }

    @Override
    public boolean isCloaked() {
        return this.isCloaked;
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
        //stop targeting anyone
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

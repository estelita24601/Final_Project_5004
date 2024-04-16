package starships;

public interface WarpCapable {
    double getMaximumWarp();

    void goToWarp(double warpSpeed);

    void modifyWarpSpeed(double warpSpeed);

    void dropOutOfWarp();
}

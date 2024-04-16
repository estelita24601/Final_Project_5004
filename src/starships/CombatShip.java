package starships;

public interface CombatShip {
    void targetWeapons(double xCoord, double yCoord, double zCoord);

    int fireWeapons();

    void standDown();
}

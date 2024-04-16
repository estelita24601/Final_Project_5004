package starships;

import java.util.ArrayList;
import java.util.function.Predicate;

public class Freighter extends SpaceVessel {
    ArrayList<Object> cargoHold;

    public Freighter(String shipName, double maximumSpeed) {
        super(shipName, maximumSpeed);
        this.cargoHold = new ArrayList<>();
    }

    public void loadCargo(Object newCargo) {
        this.cargoHold.add(newCargo);
    }

    public ArrayList<Object> deliverCargo(Predicate<Object> cargoDueForDelivery) {
        ArrayList<Object> deliveredCargo = new ArrayList<>();

        for (Object cargo : cargoHold) {
            //if current item in cargo hold we're looking at is due for delivery
            if (cargoDueForDelivery.test(cargo)) {
                this.cargoHold.remove(cargo); //remove it from the ship
                deliveredCargo.add(cargo); //add it to the list of cargo we're delivering
            }
        }

        return deliveredCargo;
    }


}

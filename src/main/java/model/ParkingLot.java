package model;

import constants.VehicleType;
import exception.ParkingSlotExhaustedException;
import lombok.Getter;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;

//Could be extended to multilevel parking lot.
public abstract class ParkingLot {
    protected String name;
    protected int entrances;
    protected Map<VehicleType, List<String>> inventory;
    protected Map<VehicleType, List<Vehicle>> vehicleMap;
    protected Map<String, Map<VehicleType, Integer>> entryGateMap;
    protected Map<String, Map<VehicleType, Integer>> exitGateMap;


    @Getter
    protected ExecutorService executorService;

    public boolean isAvailable(VehicleType type){
        return inventory.get(type).size()>0;
    }

    public abstract boolean parkVehicle(Vehicle vehicle, String entryGate) throws ParkingSlotExhaustedException;

    public abstract boolean removeVehicle(VehicleType vehicleType, String exitGate);

    public void printAllAvailableSlots() {
        int totalCount = 0;
        System.out.println("\n\n------------------Available Slots-----------------\n\n");
        for (VehicleType vehicleType : inventory.keySet()) {
            System.out.println(vehicleType + "\t" + inventory.get(vehicleType).size());
            totalCount += inventory.get(vehicleType).size();
        }
        System.out.println("\n\n------------------Available Slots-----------------");
        System.out.println("Total Available: \t" + totalCount);
    }

    public abstract void printTotalOut(String exitGate);
    public abstract void printTotalIn(String entryGate);
}

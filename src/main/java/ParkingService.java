import constants.VehicleType;
import exception.ParkingLotDoesNotExistException;
import exception.ParkingSlotExhaustedException;
import model.ParkingLot;
import model.SingleLevelParkingLot;
import model.Vehicle;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;

public class ParkingService {
    ExecutorService executorService;
    Map<String, ParkingLot> parkingLotMap;

    public ParkingService() {
        this.parkingLotMap = new HashMap<>();
    }

    public boolean addParkingLot(String name, Map<VehicleType, Integer> inventoryCount, int entryGates, int exitGates) {
        parkingLotMap.put(name, new SingleLevelParkingLot(name, inventoryCount, entryGates, exitGates));
        return true;
    }

    public boolean isAvailable(String name, VehicleType vehicleType) {
        try {
            validate(name);
            ParkingLot parkingLot = parkingLotMap.get(name);
            return parkingLot.isAvailable(vehicleType);
        } catch (ParkingLotDoesNotExistException e) {
            System.out.println(e.getMsg());
            return false;
        }
    }

    public void parkVehicle(String name, VehicleType vehicleType, String entryGate, String number) {
        try {
            validate(name);
            ParkingLot parkingLot = parkingLotMap.get(name);
            Vehicle vehicle = VehicleFactory.getVehicle(vehicleType, number);
            ExecutorService executorService = parkingLot.getExecutorService();

            Callable callable = () -> {
                try {
                    parkingLot.parkVehicle(vehicle, entryGate);
                    System.out.println("ParkVehicle : "+vehicleType+" : True");
                    return true;
                } catch (ParkingSlotExhaustedException e) {
//                    e.printStackTrace();
                    System.out.println("ParkVehicle : "+vehicleType+" : False");
                    return false;
                }
            };
            executorService.submit(callable);
        } catch (ParkingLotDoesNotExistException e) {
            System.out.println(e.getMsg());
        }
    }

    public void unparkVehicle(String name, VehicleType vehicleType, String exitGate) {
        try {
            validate(name);
            ParkingLot parkingLot = parkingLotMap.get(name);
            parkingLot.removeVehicle(vehicleType, exitGate);
        } catch (ParkingLotDoesNotExistException e) {
            System.out.println(e.getMsg());
        }
    }

    public void printAllAvailableSlots(String name) {
        try{
            validate(name);
            ParkingLot parkingLot = parkingLotMap.get(name);
            parkingLot.printAllAvailableSlots();
        } catch (ParkingLotDoesNotExistException e) {
            System.out.println(e.getMsg());
        }
    }

    public void printTotalOut(String name, String exitGate){
        try{
            validate(name);
            ParkingLot parkingLot = parkingLotMap.get(name);
            parkingLot.printTotalOut(exitGate);
        }catch (ParkingLotDoesNotExistException e) {
            System.out.println(e.getMsg());
        }
    }

    public void printTotalIn(String name, String entryGate){
        try{
            validate(name);
            ParkingLot parkingLot = parkingLotMap.get(name);
            parkingLot.printTotalIn(entryGate);
        }catch (ParkingLotDoesNotExistException e) {
            System.out.println(e.getMsg());
        }
    }

    private void validate(String name) throws ParkingLotDoesNotExistException {
        if (!parkingLotMap.containsKey(name)) {
            throw new ParkingLotDoesNotExistException("No parking Lot exists");
        }
    }
}

package model;

import constants.VehicleType;
import exception.ParkingSlotExhaustedException;
import lombok.Synchronized;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;

public class SingleLevelParkingLot extends ParkingLot {



    public SingleLevelParkingLot(String name, Map<VehicleType, Integer> inventoryCount, int entryGates, int exitGates) {
        this.executorService = Executors.newFixedThreadPool(entryGates);
        this.name = name;
        this.inventory = new HashMap<>();
        this.vehicleMap = new HashMap<>();
        this.entryGateMap = new HashMap<>();
        this.exitGateMap = new HashMap<>();
        for(int i = 0;i<entryGates;i++){
            entryGateMap.put("Entry Gate "+(i+1), new HashMap<>());
        }
        for(int i = 0;i<exitGates;i++){
            exitGateMap.put("Exit Gate "+(i+1), new HashMap<>());
        }
        for (VehicleType vehicleType : inventoryCount.keySet()) {
            inventory.put(vehicleType, new ArrayList<>());
            vehicleMap.put(vehicleType, new ArrayList<>());
            for (int i = 0; i < inventoryCount.get(vehicleType); i++) {
                inventory.get(vehicleType).add(vehicleType.name() + String.valueOf(i));
            }
        }
    }


    @Override
    @Synchronized
    public boolean parkVehicle(Vehicle vehicle, String entryGate) throws ParkingSlotExhaustedException {
        if(!validateEntry(entryGate)){
            System.out.println("Invalid gate "+entryGate+" for entry.");
        }
        if (!isAvailable(vehicle.getVehicleType())) {
            throw new ParkingSlotExhaustedException("Parking slot exhausted. Cannot park Vehicle Type:" + vehicle.getVehicleType());
        }
        vehicle.setParkingSlotId(inventory.get(vehicle.getVehicleType()).remove(0));
        vehicleMap.get(vehicle.getVehicleType()).add(vehicle);
        Map<VehicleType, Integer> map = entryGateMap.get(entryGate);
        if(map.size()==0){
            map.put(vehicle.getVehicleType(),1);
        }else{
            Integer count = map.get(vehicle.getVehicleType());
            map.put(vehicle.getVehicleType(), count+1);
        }
        return true;
    }

    @Override
    public boolean removeVehicle(VehicleType vehicleType, String exitGate) {
        if(!validateExit(exitGate)){
            System.out.println("Invalid gate "+exitGate+" for exit.");
        }
        if (vehicleMap.get(vehicleType).size() > 0) {
            Vehicle vehicle = vehicleMap.get(vehicleType).remove(0);
            inventory.get(vehicleType).add(vehicle.parkingSlotId);
            Map<VehicleType, Integer> map = exitGateMap.get(exitGate);
            if(map.size()==0){
                map.put(vehicle.getVehicleType(),1);
            }else{
                Integer count = map.get(vehicleType);
                map.put(vehicleType, count+1);
            }
            return true;
        } else {
            return false;
        }
    }

    private boolean validateEntry(String entryGate) {
        if(!entryGateMap.containsKey(entryGate)){
            return false;
        }
        return true;
    }
    private boolean validateExit(String exitGate) {
        if(!exitGateMap.containsKey(exitGate)){
            return false;
        }
        return true;
    }

    @Override
    public void printTotalOut(String exitGate) {
        if(validateExit(exitGate)){
            Map<VehicleType, Integer> map = exitGateMap.get(exitGate);
            for(VehicleType type: map.keySet()){
                System.out.println(type +"\t"+ map.get(type));
            }
        }
    }

    @Override
    public void printTotalIn(String entryGate) {
        if(validateEntry(entryGate)){
            Map<VehicleType, Integer> map = exitGateMap.get(entryGate);
            for(VehicleType type: map.keySet()){
                System.out.println(type +"\t"+ map.get(type));
            }
        }
    }
}

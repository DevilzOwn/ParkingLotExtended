package model;

import constants.VehicleType;
import lombok.Data;

@Data
public abstract class Vehicle {
    String parkingSlotId;
    VehicleType vehicleType;
}

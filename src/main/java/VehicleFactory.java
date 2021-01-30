import constants.VehicleType;
import model.Bicycle;
import model.Bike;
import model.Car;
import model.Vehicle;

public class VehicleFactory {
    public static Vehicle getVehicle(VehicleType type, String number) {
        Vehicle vehicle = null;
        switch (type){
            case Bike:
                vehicle = new Bike(number);
                vehicle.setVehicleType(type);
                break;
            case Car:
                vehicle = new Car(number);
                vehicle.setVehicleType(type);
                break;
            case Bicycle:
                vehicle = new Bicycle();
                vehicle.setVehicleType(type);
                break;
            default:

        }
        return vehicle;
    }
}

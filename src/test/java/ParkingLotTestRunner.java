import constants.VehicleType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RunWith(MockitoJUnitRunner.Silent.class)
public class ParkingLotTestRunner {
    ParkingService parkingService;

    @Before
    public void setup() {
        parkingService = new ParkingService();
    }

    @Test
    public void testParkingLot() throws IOException {
//        BufferedReader reader = new BufferedReader(new FileReader("./src/main/resources/input.txt"));
//        StringBuilder stringBuilder = new StringBuilder();
//        String line = null;
//        while ((line = reader.readLine()) != null) {
//            stringBuilder.append(line);
//        }
//        System.out.println(stringBuilder.toString());
        String parkingLotName = "PVR Koramangala";
        Map<VehicleType, Integer> inventoryCount = new HashMap<>();
        inventoryCount.put(VehicleType.Car, 2);
        inventoryCount.put(VehicleType.Bike, 1);
        inventoryCount.put(VehicleType.Bicycle, 1);
        parkingService.addParkingLot(parkingLotName, inventoryCount, 4, 2);

        System.out.println(parkingService.isAvailable(parkingLotName, VehicleType.Bike));
        System.out.println(parkingService.isAvailable(parkingLotName, VehicleType.Car));
        System.out.println(parkingService.isAvailable(parkingLotName, VehicleType.Bike));


        parkingService.parkVehicle(parkingLotName, VehicleType.Car,"Entry Gate 1","random");
        parkingService.parkVehicle(parkingLotName, VehicleType.Bike,"Entry Gate 2","random");
        parkingService.parkVehicle(parkingLotName, VehicleType.Bike,"Entry Gate 3","random");

        parkingService.isAvailable(parkingLotName, VehicleType.Bike);
        parkingService.printAllAvailableSlots(parkingLotName);

        parkingService.unparkVehicle(parkingLotName, VehicleType.Bike, "Exit Gate 1");

        parkingService.printTotalOut(parkingLotName,"Exit Gate 1");

    }
}

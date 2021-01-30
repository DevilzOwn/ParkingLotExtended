package exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ParkingSlotExhaustedException extends Throwable {
    String msg;
}

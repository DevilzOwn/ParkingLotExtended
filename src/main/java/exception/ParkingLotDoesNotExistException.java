package exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ParkingLotDoesNotExistException extends Throwable {
    String msg;
}

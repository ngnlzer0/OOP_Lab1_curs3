package train.models;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import train.enums.WagonLevel;

public class PassengerCarTest {

    @Test
    void testInitialization() {
        PassengerCar car = new PassengerCar(1, 12000, 1500, 100, WagonLevel.PREMIUM, 50, 1000);
        assertEquals(1, car.getID());
        assertEquals(12000, car.getWeight());
        assertEquals(100, car.getCapacity());
        assertEquals(50, car.getCurrentPassengers());
        assertEquals(1000, car.getLuggageWeight());
    }

    @Test
    void testSettersValidation() {
        PassengerCar car = new PassengerCar(2, 10000, 1400, 80, WagonLevel.RESERVED_SEAT, 30, 500);
        assertThrows(IllegalArgumentException.class, () -> car.setCurrentPassengers(-1));
        assertThrows(IllegalArgumentException.class, () -> car.setCurrentPassengers(200));
        assertThrows(IllegalArgumentException.class, () -> car.setLuggageWeight(-10));
    }

    @Test
    void testCalculateTotalWeight() {
        PassengerCar car = new PassengerCar(3, 11000, 1400, 100, WagonLevel.RESERVED_SEAT, 60, 700);
        double expected = 11000 + 60 * 70 + 700;
        assertEquals(expected, car.calculateTotalWeight());
    }

    @Test
    void testToString() {
        PassengerCar car = new PassengerCar(4, 13000, 1400, 50, WagonLevel.SITTING, 10, 300);
        String info = car.toString();
        assertTrue(info.contains("PassengerCar"));
        assertTrue(info.contains("SITTING"));
    }
}

package train.models;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class FreightCarTest {

    @Test
    void testInitialization() {
        FreightCar car = new FreightCar(1, 10000, 1500, 200, 50000);
        assertEquals(1, car.getID());
        assertEquals(10000, car.getWeight());
        assertEquals(200, car.getVolume());
        assertEquals(50000, car.getMaxWeight());
        assertEquals(10000, car.getCurrentWeight()); // початкова = власна вага
    }

    @Test
    void testSetCurrentVolumeValid() {
        FreightCar car = new FreightCar(2, 8000, 1400, 100, 30000);
        car.setCurrentVolume(50);
        assertEquals(50, car.getCurrentVolume());
    }

    @Test
    void testSetCurrentVolumeInvalid() {
        FreightCar car = new FreightCar(3, 9000, 1400, 80, 25000);
        assertThrows(IllegalArgumentException.class, () -> car.setCurrentVolume(-5));
        assertThrows(IllegalArgumentException.class, () -> car.setCurrentVolume(200));
    }

    @Test
    void testLoadAndUnloadCargo() {
        FreightCar car = new FreightCar(4, 9000, 1400, 150, 40000);

        // Завантажуємо
        car.loadCargo(10000, 50);
        assertEquals(19000, car.getCurrentWeight());
        assertEquals(50, car.getCurrentVolume());

        // Вивантажуємо
        car.unloadCargo(5000, 25);
        assertEquals(14000, car.getCurrentWeight());
        assertEquals(25, car.getCurrentVolume());
    }

    @Test
    void testLoadOverLimit() {
        FreightCar car = new FreightCar(5, 9000, 1400, 100, 20000);
        assertThrows(IllegalArgumentException.class, () -> car.loadCargo(25000, 10));
        assertThrows(IllegalArgumentException.class, () -> car.loadCargo(1000, 150));
    }

    @Test
    void testToString() {
        FreightCar car = new FreightCar(6, 9500, 1400, 120, 30000);
        String info = car.toString();
        assertTrue(info.contains("FreightCar"));
        assertTrue(info.contains("9500"));
    }
}

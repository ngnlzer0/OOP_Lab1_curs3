package train.models;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import train.enums.FuelType;

public class LocomotiveTest {

    @Test
    void testInitialization() {
        Locomotive loco = new Locomotive(1, 20000, 1500, 5000, FuelType.ELECTRICITY, 160);
        assertEquals(1, loco.getID());
        assertEquals(20000, loco.getWeight());
        assertEquals(5000, loco.getPotency());
        assertEquals(FuelType.ELECTRICITY, loco.getFuelType());
        assertEquals(160, loco.getMaxSpeed());
    }

    @Test
    void testCalculateTotalWeight() {
        Locomotive loco = new Locomotive(2, 25000, 1600, 6000, FuelType.DIESEL, 200);
        assertEquals(25000, loco.calculateTotalWeight());
    }

    @Test
    void testToStringContainsFuel() {
        Locomotive loco = new Locomotive(3, 18000, 1400, 4500, FuelType.COAL, 120);
        String info = loco.toString();
        assertTrue(info.contains("fuel"));
        assertTrue(info.contains("COAL"));
    }
}


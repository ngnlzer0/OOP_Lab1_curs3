package train.models;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import train.enums.FuelType;
import train.enums.WagonLevel;

import java.util.List;

public class TrainTest {

    @Test
    void testTrainInitializationAndAddCars() {
        Locomotive loco = new Locomotive(1, 20000, 1500, 5000, FuelType.DIESEL, 150);
        Train train = new Train(loco);

        PassengerCar pc = new PassengerCar(2, 12000, 1500, 100, WagonLevel.PREMIUM, 60, 800);
        FreightCar fc = new FreightCar(3, 15000, 1400, 200, 50000);

        train.addCar(pc);
        train.addCar(fc);

        assertEquals(2, train.getCars().size());
        assertTrue(train.getCars().contains(pc));
        assertTrue(train.getCars().contains(fc));
    }

    @Test
    void testTotalCalculations() {
        Locomotive loco = new Locomotive(1, 20000, 1500, 5000, FuelType.DIESEL, 150);
        Train train = new Train(loco);

        PassengerCar p1 = new PassengerCar(2, 10000, 1500, 100, WagonLevel.COMPARTMENT, 50, 500);
        FreightCar f1 = new FreightCar(3, 15000, 1400, 100, 30000);

        train.addCar(p1);
        train.addCar(f1);

        assertTrue(train.getTotalWeight() > 0);
        assertEquals(100, train.getTotalPassengerCapacity());
        assertEquals(50, train.getTotalPassengers());
    }

    @Test
    void testFindAndRemoveCars() {
        Locomotive loco = new Locomotive(1, 20000, 1500, 5000, FuelType.DIESEL, 150);
        Train train = new Train(loco);
        PassengerCar p1 = new PassengerCar(2, 10000, 1500, 80, WagonLevel.COMPARTMENT, 40, 300);
        train.addCar(p1);

        assertNotNull(train.findCarById(2));
        train.removeCarById(2);
        assertNull(train.findCarById(2));
    }

    @Test
    void testFindPassengerCarsByRange() {
        Locomotive loco = new Locomotive(1, 20000, 1500, 5000, FuelType.DIESEL, 150);
        Train train = new Train(loco);

        train.addCar(new PassengerCar(2, 10000, 1500, 100, WagonLevel.LUXURY, 40, 300));
        train.addCar(new PassengerCar(3, 11000, 1500, 100, WagonLevel.PREMIUM, 80, 500));
        train.addCar(new PassengerCar(4, 12000, 1500, 100, WagonLevel.SITTING, 10, 100));

        List<PassengerCar> result = train.findPassengerCarsByRange(30, 70);
        assertEquals(1, result.size());
        assertEquals(40, result.get(0).getCurrentPassengers());
    }

    @Test
    void testToString() {
        Locomotive loco = new Locomotive(1, 20000, 1500, 5000, FuelType.DIESEL, 150);
        Train train = new Train(loco);
        String info = train.toString();
        assertTrue(info.contains("Train"));
        assertTrue(info.contains("composition"));
    }
}

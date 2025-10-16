import train.enums.FuelType;
import train.models.Locomotive;
import train.models.PassengerCar;
import train.models.TrainCar;
import train.enums.WagonLevel;
import train.models.FreightCar;
import train.models.Train;

public class Main {
    public static void main(String[] args )
    {

        // Створюємо локомотив
        Locomotive loco = new Locomotive(1, 80000, 1500, 3000, FuelType.DIESEL, 120);

        // Створюємо пасажирські вагони
        PassengerCar car1 = new PassengerCar(2, 20000, 1500, 50, WagonLevel.SITTING, 40, 500);
        PassengerCar car2 = new PassengerCar(3, 25000, 1500, 70, WagonLevel.LUXURY, 60, 1000);
        PassengerCar car3 = new PassengerCar(6, 15000, 1500, 70, WagonLevel.COMPARTMENT, 50, 800);


        // Створюємо вантажні вагони
        FreightCar freight1 = new FreightCar(4, 18000, 1500, 100, 30000);
        freight1.loadCargo(10000, 60);

        FreightCar freight2 = new FreightCar(5, 19000, 1500, 80, 25000);
        freight2.loadCargo(8000, 40);

        // Створюємо поїзд
        Train train = new Train(loco);
        train.addCar(car1);
        train.addCar(car2);
        train.addCar(freight1);
        train.addCar(freight2);
        train.addCar(car3);

        // Вивід інформації
        System.out.println(train);

        // Спробуємо сортування
        train.sortByComfortLevel();
        System.out.println("\n=== After sorting by weight ===");
        System.out.println(train);
    }
}
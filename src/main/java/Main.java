import train.models.PassengerCar;
import train.models.TrainCar;
import train.enums.WagonLevel;

public class Main {
    public static void main(String[] args )
    {
        System.out.println("Hello dear java");

        PassengerCar wagon1 = new PassengerCar(1,1000,1280,80,WagonLevel.PREMIUM,30);

        System.out.println(wagon1.toString());
    }
}
package train.models;

import train.enums.FuelType;

public class Locomotive extends TrainCar{
    double potency; //power, measured in kW
    FuelType fuelType;
    double maxSpeed; // max speed in km per hour

    public Locomotive(int id, double weight, double suspension_width,double potency, FuelType fuelType,double maxSpeed)
    {
        super(id,weight,suspension_width);
        this.potency = potency;
        this.fuelType = fuelType;
        this.maxSpeed = maxSpeed;
    }

    //getters:

    public double getPotency() {return potency;}
    public FuelType getFuelType() {return fuelType;}
    public double getMaxSpeed(){return maxSpeed;}

    @Override
    public int getCapacity(){return 0;}
    public double calculateTotalWeight() { return getWeight();}

    @Override
    public String toString() {
        return "Locomotive{" +
                "id=" + getID() +
                ", weight=" + getWeight() +
                ", potency=" + potency +
                " kW, maxSpeed=" + maxSpeed +
                " km/h, fuel=" + fuelType +
                '}';
    }

}

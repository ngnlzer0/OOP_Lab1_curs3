package train.models;

import train.enums.WagonLevel;

public class PassengerCar extends TrainCar{
    private int number_cities;
    private WagonLevel level; //car type by comfort
    private int current_pasangers;

    public PassengerCar(int id,double weight, double suspension_width, int number_cities, WagonLevel level, int current_pasangers)
    {
        super(id,weight,suspension_width);
        this.number_cities = number_cities;
        this.level = level;
        this.current_pasangers = current_pasangers;
    }

    //getters:
    public int getNumber_cities(){return number_cities;}
    public WagonLevel getLevel(){return level;}
    public int getCurrent_pasangers(){return current_pasangers;}

    //setters:
    public int setCurrent_pasangers(int new_number){this.current_pasangers = new_number; return current_pasangers;}

    @Override
    public int getCapacity(){return number_cities;}

    @Override
    public double calculateTotalWeight() {return getWeight() + current_pasangers * 70;}

    @Override
    public String toString() {
        return "PassengerCar{" +
                "id=" + getID() +
                ", number cities= " + number_cities +
                ", passengers=" + current_pasangers +
                ", comfortLevel=" + level +
                ", totalWeight=" + calculateTotalWeight() +
                '}';
    }
}

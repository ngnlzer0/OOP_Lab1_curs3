package train.models;

public abstract class TrainCar {
    final int ID; // unique wagon number
    private double weight; // weight empty train car
    private double suspension_width; // The width of the car suspension is measured in millimeters

    public TrainCar(int ID, double weight, double suspension_width)
    {
        this.ID = ID;
        this.weight = weight;
        this.suspension_width = suspension_width;
    }

    //geters:
    public int getID(){return ID;}
    public double getWeight(){return weight;}
    public double getSuspensionWidth(){return suspension_width;}

    //setters:

    public double setWeight(double newWeight){this.weight = newWeight; return weight;}
    public double setSuspensionWidth(double newWidth){this.suspension_width = newWidth; return suspension_width;}

    //abstract methods:
    public abstract double calculateTotalWeight();
    public abstract int getCapacity();

    @Override
    public String toString()
    {
        return "TrainCar{" +
                "id=" + ID +
                ", weight=" + weight +
                ", suspension width" + suspension_width +
                '}';
    }

}

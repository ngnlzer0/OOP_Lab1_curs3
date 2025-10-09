package train.models;

/*class that realize freight car locomotive*/

public class FreightCar extends TrainCar{
    private double maxVolume;
    private double maxWeight;
    private double currentWeight;
    private double currentVolume;

    public FreightCar(int ID, double weight, double suspension_width, double maxVolume, double maxWeight)
    {
        super(ID,weight,suspension_width);
        this.maxVolume = maxVolume;
        this.maxWeight = maxWeight;
        this.currentVolume = 0;
        this.currentWeight = this.getWeight();
    }

    //Getters
    public double getVolume() {return maxVolume;}
    public double getMaxWeight() {return maxWeight;}
    public double getCurrentWeight() {return currentWeight;}
    public double getCurrentVolume() {return currentVolume;}

    //Setters

    public void setCurrentVolume(double newVolume) {
        if (newVolume < 0) {
            throw new IllegalArgumentException("Volume cannot be negative");
        }
        if (newVolume > maxVolume) {
            throw new IllegalArgumentException("Volume exceeds maximum capacity");
        }
        this.currentVolume = newVolume;
    }

    public void setCurrentWeight(double newWeight) {
        if (newWeight < getWeight()) {
            throw new IllegalArgumentException("Current weight cannot be less than car's own weight");
        }
        if (newWeight > getWeight() + maxWeight) {
            throw new IllegalArgumentException("Current weight exceeds maximum load capacity");
        }
        this.currentWeight = newWeight;
    }

    public void loadCargo(double cargoWeight, double cargoVolume) {
        if (cargoWeight < 0 || cargoVolume < 0) {
            throw new IllegalArgumentException("Cargo weight and volume must be non-negative");
        }
        if (currentWeight + cargoWeight > getWeight() + maxWeight) {
            throw new IllegalArgumentException("Overloaded: cargo weight exceeds limit");
        }
        if (currentVolume + cargoVolume > maxVolume) {
            throw new IllegalArgumentException("Cargo volume exceeds capacity");
        }
        currentWeight += cargoWeight;
        currentVolume += cargoVolume;
    }

    public void unloadCargo(double cargoWeight, double cargoVolume) {
        if (cargoWeight < 0 || cargoVolume < 0) {
            throw new IllegalArgumentException("Cargo weight and volume must be non-negative");
        }
        if (currentWeight - cargoWeight < getWeight()) {
            throw new IllegalArgumentException("Cannot unload more than current cargo weight");
        }
        if (currentVolume - cargoVolume < 0) {
            throw new IllegalArgumentException("Cannot unload more than current cargo volume");
        }
        currentWeight -= cargoWeight;
        currentVolume -= cargoVolume;
    }

    // --- abstract methods ---
    @Override
    public double calculateTotalWeight() {
        return currentWeight;
    }

    @Override
    public int getCapacity() {
        return (int) Math.round(maxVolume);
    }

    @Override
    public String toString() {
        return String.format(
                "FreightCar(ID=%d, ownWeight=%.2f, currentWeight=%.2f, maxLoad=%.2f, volume=%.2f, currentVolume=%.2f)",
                getID(), getWeight(), currentWeight, maxWeight, maxVolume, currentVolume
        );
    }
}

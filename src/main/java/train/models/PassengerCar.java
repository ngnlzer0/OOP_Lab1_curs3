package train.models;

import train.enums.WagonLevel;

public class PassengerCar extends TrainCar {
    private int capacity;
    private WagonLevel level;
    private int currentPassengers;
    private double luggageWeight;

    public PassengerCar(int id, double weight, double suspensionWidth, int capacity,
                        WagonLevel level, int currentPassengers, double luggageWeight) {
        super(id, weight, suspensionWidth);
        this.capacity = capacity;
        this.level = level;
        this.currentPassengers = currentPassengers;
        this.luggageWeight = luggageWeight;
    }

    // Getters
    public int getCapacity() { return capacity; }
    public WagonLevel getLevel() { return level; }
    public int getCurrentPassengers() { return currentPassengers; }
    public double getLuggageWeight() { return luggageWeight; }

    // Setters
    public void setCurrentPassengers(int newCount) {
        if (newCount < 0 || newCount > capacity)
            throw new IllegalArgumentException("Invalid number of passengers");
        this.currentPassengers = newCount;
    }

    public void setLuggageWeight(double luggageWeight) {
        if (luggageWeight < 0)
            throw new IllegalArgumentException("Luggage weight cannot be negative");
        this.luggageWeight = luggageWeight;
    }

    @Override
    public double calculateTotalWeight() {
        // базова вага + пасажири + багаж
        return getWeight() + currentPassengers * 70 + luggageWeight;
    }

    @Override
    public String toString() {
        return String.format(
                "PassengerCar{id=%d, comfort=%s, capacity=%d, passengers=%d, totalWeight=%.2f}",
                getID(), level, capacity, currentPassengers, calculateTotalWeight()
        );
    }
}


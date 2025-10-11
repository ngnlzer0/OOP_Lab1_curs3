package train.models;

import train.enums.WagonLevel;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Train {
    private final Locomotive locomotive;
    private final List<TrainCar> cars;

    public Train(Locomotive locomotive) {
        if (locomotive == null)
            throw new IllegalArgumentException("Train must have a locomotive");
        this.locomotive = locomotive;
        this.cars = new ArrayList<>();
    }

    // --- Manage cars ---
    public void addCar(TrainCar car) {
        if (car == null)
            throw new IllegalArgumentException("Car cannot be null");
        cars.add(car);
    }

    public void removeCarById(int id) {
        cars.removeIf(c -> c.getID() == id);
    }

    public TrainCar findCarById(int id) {
        return cars.stream()
                .filter(c -> c.getID() == id)
                .findFirst()
                .orElse(null);
    }

    public List<TrainCar> getCars() {
        return new ArrayList<>(cars); // copy to prevent modification outside
    }

    // --- Aggregated info ---
    public double getTotalWeight() {
        double total = locomotive.calculateTotalWeight();
        for (TrainCar car : cars)
            total += car.calculateTotalWeight();
        return total;
    }

    public int getTotalPassengerCapacity() {
        return cars.stream()
                .filter(c -> c instanceof PassengerCar)
                .mapToInt(TrainCar::getCapacity)
                .sum();
    }

    public int getTotalPassengers() {
        return cars.stream()
                .filter(c -> c instanceof PassengerCar)
                .mapToInt(c -> ((PassengerCar) c).getCurrentPassengers())
                .sum();
    }

    public double getTotalLuggageWeight() {
        return cars.stream()
                .filter(c -> c instanceof PassengerCar)
                .mapToDouble(c -> ((PassengerCar) c).getLuggageWeight())
                .sum();
    }

    public double getTotalFreightCapacity() {
        return cars.stream()
                .filter(c -> c instanceof FreightCar)
                .mapToDouble(TrainCar::getCapacity)
                .sum();
    }

    // --- Sorting ---
    public void sortByComfortLevel() {
        cars.sort(Comparator.comparingInt(c -> {
            if (c instanceof PassengerCar)
                return ((PassengerCar) c).getLevel().ordinal();
            return Integer.MAX_VALUE; // freight cars go last
        }));
    }

    public void sortByWeight() {
        cars.sort(Comparator.comparingDouble(TrainCar::calculateTotalWeight));
    }

    public void sortByCapacity() {
        cars.sort(Comparator.comparingInt(TrainCar::getCapacity));
    }

    // --- Filtering ---
    public List<PassengerCar> findPassengerCarsByRange(int minPassengers, int maxPassengers) {
        return cars.stream()
                .filter(c -> c instanceof PassengerCar)
                .map(c -> (PassengerCar) c)
                .filter(pc -> pc.getCurrentPassengers() >= minPassengers && pc.getCurrentPassengers() <= maxPassengers)
                .collect(Collectors.toList());
    }

    // --- Info ---
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== Train composition ===\n");
        sb.append(locomotive.toString()).append("\n");
        for (TrainCar car : cars) {
            sb.append(" -> ").append(car.toString()).append("\n");
        }
        sb.append(String.format("Total train weight: %.2f kg\n", getTotalWeight()));
        sb.append(String.format("Passenger capacity: %d (currently %d)\n",
                getTotalPassengerCapacity(), getTotalPassengers()));
        sb.append(String.format("Total luggage weight: %.2f kg\n", getTotalLuggageWeight()));
        sb.append(String.format("Freight volume capacity: %.2f\n", getTotalFreightCapacity()));
        return sb.toString();
    }
}



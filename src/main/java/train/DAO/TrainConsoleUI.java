package train.ui;

import train.DAO.*;
import train.enums.FuelType;
import train.enums.WagonLevel;
import train.models.FreightCar;
import train.models.Locomotive;
import train.models.PassengerCar;
import train.models.Train;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class TrainConsoleUI {
    private static final Scanner scanner = new Scanner(System.in);
    private static final TrainDAO trainDAO = new TrainDAO();
    private static final LocomotiveDAO locomotiveDAO = new LocomotiveDAO();
    private static final PassengerCarDAO passengerCarDAO = new PassengerCarDAO();
    private static final FreightCarDAO freightCarDAO = new FreightCarDAO();

    public static void main(String[] args) {
        // !!! УВАГА: Перед запуском переконайтеся, що налаштовано DBConnection
        // та створено таблиці у базі даних PostgreSQL.
        mainMenu();
    }

    private static void mainMenu() {
        while (true) {
            System.out.println("\n=== Головне меню ===");
            System.out.println("1. Переглянути всі потяги");
            System.out.println("2. Створити новий потяг");
            System.out.println("3. Керувати потягом (додати вагони, переглянути)");
            System.out.println("4. Видалити потяг");
            System.out.println("0. Вихід");
            System.out.print("Оберіть опцію: ");

            try {
                int choice = scanner.nextInt();
                scanner.nextLine(); // consume newline
                switch (choice) {
                    case 1 -> viewAllTrains();
                    case 2 -> createNewTrain();
                    case 3 -> manageTrain();
                    case 4 -> deleteTrain();
                    case 0 -> {
                        System.out.println("Програма завершена.");
                        return;
                    }
                    default -> System.out.println("Невірна опція. Спробуйте ще.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Помилка: Невірний формат введення. Спробуйте ввести число.");
                scanner.nextLine(); // очистити буфер
            } catch (Exception e) {
                System.out.println("Виникла помилка: " + e.getMessage());
            }
        }
    }

    // Метод для запуску консольного інтерфейсу
    public void start() {
        mainMenu();
    }

    // --- Логіка Головного Меню ---

    private static void viewAllTrains() {
        System.out.println("\n--- Список потягів ---");
        List<String> trains = trainDAO.getAllTrains();
        if (trains.isEmpty()) {
            System.out.println("Наразі немає жодного потяга.");
        } else {
            trains.forEach(System.out::println);
        }
    }

    private static void createNewTrain() {
        System.out.print("Введіть назву нового потяга: ");
        String name = scanner.nextLine();
        int trainId = trainDAO.saveTrain(name);

        if (trainId != -1) {
            System.out.println("Потяг '" + name + "' створено з ID: " + trainId);
            // Одразу пропонуємо додати локомотив
            addLocomotiveToTrain(trainId);
        } else {
            System.out.println("Помилка при створенні потяга.");
        }
    }

    private static void deleteTrain() {
        System.out.print("Введіть ID потяга для видалення: ");
        try {
            int trainId = scanner.nextInt();
            scanner.nextLine();
            trainDAO.deleteTrain(trainId);
            System.out.println("Потяг з ID " + trainId + " видалено (якщо існував).");
        } catch (InputMismatchException e) {
            System.out.println("Помилка: ID потяга має бути числом.");
            scanner.nextLine();
        }
    }

    private static void manageTrain() {
        System.out.print("Введіть ID потяга для керування: ");
        try {
            int trainId = scanner.nextInt();
            scanner.nextLine();

            // Перевірка, чи існує локомотив (обов'язкова умова для об'єкта Train)
            Locomotive loco = locomotiveDAO.getLocomotiveByTrainId(trainId);
            if (loco == null) {
                System.out.println("Потяг з ID " + trainId + " не знайдено або в нього немає локомотива.");
                System.out.print("Бажаєте додати локомотив? (y/n): ");
                if (scanner.nextLine().trim().equalsIgnoreCase("y")) {
                    addLocomotiveToTrain(trainId);
                    // Спробуємо отримати локомотив ще раз
                    loco = locomotiveDAO.getLocomotiveByTrainId(trainId);
                    if (loco == null) return;
                } else {
                    return;
                }
            }

            // Якщо локомотив знайдено, створюємо об'єкт Train
            Train train = createTrainObjectFromDB(trainId, loco);
            trainManagementMenu(trainId, train);

        } catch (InputMismatchException e) {
            System.out.println("Помилка: ID потяга має бути числом.");
            scanner.nextLine();
        }
    }

    private static Train createTrainObjectFromDB(int trainId, Locomotive loco) {
        Train train = new Train(loco);
        List<PassengerCar> passengerCars = passengerCarDAO.getPassengerCarsByTrainId(trainId);
        List<FreightCar> freightCars = freightCarDAO.getFreightCarsByTrainId(trainId);

        passengerCars.forEach(train::addCar);
        freightCars.forEach(train::addCar);
        return train;
    }

    // --- Меню Керування Потягом ---

    private static void trainManagementMenu(int trainId, Train train) {
        while (true) {
            System.out.println("\n=== Керування потягом ID: " + trainId + " ===");
            System.out.println("1. Переглянути деталі потяга");
            System.out.println("2. Додати пасажирський вагон");
            System.out.println("3. Додати вантажний вагон");
            System.out.println("4. Знайти пасажирські вагони за кількістю пасажирів");
            System.out.println("5. Сортувати вагони по... (зараз не реалізовано для DAO)");
            System.out.println("0. Повернутися до головного меню");
            System.out.print("Оберіть опцію: ");

            try {
                int choice = scanner.nextInt();
                scanner.nextLine();
                switch (choice) {
                    case 1 -> System.out.println(train.toString());
                    case 2 -> addPassengerCar(trainId, train);
                    case 3 -> addFreightCar(trainId, train);
                    case 4 -> findCarsByPassengerCount(train);
                    case 5 -> System.out.println("Сортування потрібно реалізовувати на рівні завантаженого об'єкта Train, але зміни не зберігаються у БД.");
                    case 0 -> { return; }
                    default -> System.out.println("Невірна опція. Спробуйте ще.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Помилка: Невірний формат введення. Спробуйте ввести число.");
                scanner.nextLine();
            }
        }
    }

    // --- Логіка Додавання ---

    private static void addLocomotiveToTrain(int trainId) {
        System.out.println("\n--- Додавання Локомотива ---");
        try {
            System.out.print("Вага (кг): ");
            double weight = scanner.nextDouble();
            System.out.print("Ширина підвіски (мм): ");
            double width = scanner.nextDouble();
            System.out.print("Потужність (кВт): ");
            double potency = scanner.nextDouble();
            System.out.print("Макс. швидкість (км/год): ");
            double maxSpeed = scanner.nextDouble();
            scanner.nextLine(); // consume newline

            System.out.print("Тип палива (DIESEL/ELECTRIC/STEAM): ");
            FuelType fuelType = FuelType.valueOf(scanner.nextLine().toUpperCase());

            // ID локомотива буде генеруватися БД, тому тут передаємо 0
            Locomotive loco = new Locomotive(0, weight, width, potency, fuelType, maxSpeed);
            locomotiveDAO.saveLocomotive(trainId, loco);
            System.out.println("Локомотив успішно додано до потяга ID: " + trainId);

        } catch (InputMismatchException e) {
            System.out.println("Помилка: Невірний формат числа.");
            scanner.nextLine();
        } catch (IllegalArgumentException e) {
            System.out.println("Помилка: Невірний тип палива. Використовуйте DIESEL, ELECTRIC або STEAM.");
        }
    }

    private static void addPassengerCar(int trainId, Train train) {
        System.out.println("\n--- Додавання Пасажирського Вагона ---");
        try {
            System.out.print("Вага (кг): ");
            double weight = scanner.nextDouble();
            System.out.print("Ширина підвіски (мм): ");
            double width = scanner.nextDouble();
            System.out.print("Місткість (кількість місць): ");
            int capacity = scanner.nextInt();
            System.out.print("Кількість пасажирів: ");
            int passengers = scanner.nextInt();
            scanner.nextLine(); // consume newline

            System.out.print("Рівень комфорту (LUXURY/BUSINESS/ECONOMY): ");
            WagonLevel level = WagonLevel.valueOf(scanner.nextLine().toUpperCase());

            // ID вагона буде генеруватися БД, тому тут передаємо 0
            PassengerCar car = new PassengerCar(0, weight, width, capacity, level, passengers, 0);
            passengerCarDAO.savePassengerCar(trainId, car);
            train.addCar(car); // Додаємо до об'єкта в пам'яті для поточного керування
            System.out.println("Пасажирський вагон успішно додано.");

        } catch (InputMismatchException e) {
            System.out.println("Помилка: Невірний формат числа.");
            scanner.nextLine();
        } catch (IllegalArgumentException e) {
            System.out.println("Помилка: " + e.getMessage() + ". Використовуйте LUXURY, BUSINESS або ECONOMY.");
        }
    }

    private static void addFreightCar(int trainId, Train train) {
        System.out.println("\n--- Додавання Вантажного Вагона ---");
        try {
            System.out.print("Вага (кг): ");
            double weight = scanner.nextDouble();
            System.out.print("Ширина підвіски (мм): ");
            double width = scanner.nextDouble();
            System.out.print("Макс. об'єм: ");
            double maxVolume = scanner.nextDouble();
            System.out.print("Макс. вантажопідйомність (кг): ");
            double maxWeight = scanner.nextDouble();
            scanner.nextLine(); // consume newline

            // ID вагона буде генеруватися БД, тому тут передаємо 0
            FreightCar car = new FreightCar(0, weight, width, maxVolume, maxWeight);
            // Початкова вага і об'єм вже встановлені в конструкторі FreightCar
            freightCarDAO.saveFreightCar(trainId, car);
            train.addCar(car); // Додаємо до об'єкта в пам'яті для поточного керування
            System.out.println("Вантажний вагон успішно додано.");

        } catch (InputMismatchException e) {
            System.out.println("Помилка: Невірний формат числа.");
            scanner.nextLine();
        } catch (IllegalArgumentException e) {
            System.out.println("Помилка: " + e.getMessage());
        }
    }

    // --- Логіка Фільтрації ---

    private static void findCarsByPassengerCount(Train train) {
        System.out.println("\n--- Пошук Пасажирських Вагонів за Кількістю Пасажирів ---");
        try {
            System.out.print("Введіть мінімальну кількість пасажирів: ");
            int min = scanner.nextInt();
            System.out.print("Введіть максимальну кількість пасажирів: ");
            int max = scanner.nextInt();
            scanner.nextLine();

            List<PassengerCar> foundCars = train.findPassengerCarsByRange(min, max);

            if (foundCars.isEmpty()) {
                System.out.println("Вагонів з такою кількістю пасажирів не знайдено.");
            } else {
                System.out.println("Знайдені вагони:");
                foundCars.forEach(System.out::println);
            }
        } catch (InputMismatchException e) {
            System.out.println("Помилка: Межі кількості пасажирів мають бути числами.");
            scanner.nextLine();
        }
    }
}

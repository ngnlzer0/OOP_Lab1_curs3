package train.DAO;

import train.enums.WagonLevel;
import train.models.PassengerCar;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PassengerCarDAO {

    public void savePassengerCar(int trainId, PassengerCar car) {
        String sql = """
            INSERT INTO passenger_cars (train_id, weight, suspension_width, number_cities, level, current_passengers)
            VALUES (?, ?, ?, ?, ?, ?)
            """;
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, trainId);
            ps.setDouble(2, car.getWeight());
            ps.setDouble(3, car.getSuspensionWidth());
            ps.setInt(4, car.getCapacity());
            ps.setString(5, car.getLevel().name());
            ps.setInt(6, car.getCurrentPassengers());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deletePassengerCar(int carId) {
        String sql = "DELETE FROM passenger_cars WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, carId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<PassengerCar> getPassengerCarsByTrainId(int trainId) {
        List<PassengerCar> cars = new ArrayList<>();
        String sql = "SELECT * FROM passenger_cars WHERE train_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, trainId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                cars.add(new PassengerCar(
                        rs.getInt("id"),
                        rs.getDouble("weight"),
                        rs.getDouble("suspension_width"),
                        rs.getInt("number_cities"),
                        WagonLevel.valueOf(rs.getString("level")),
                        rs.getInt("current_passengers"),
                        0 // luggageWeight не зберігається у таблиці, можна додати пізніше
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cars;
    }
}

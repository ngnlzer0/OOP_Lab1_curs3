package train.DAO;

import train.models.FreightCar;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FreightCarDAO {

    public void saveFreightCar(int trainId, FreightCar car) {
        String sql = """
            INSERT INTO freight_cars (train_id, weight, suspension_width, max_volume, max_weight, current_volume, current_weight)
            VALUES (?, ?, ?, ?, ?, ?, ?)
            """;
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, trainId);
            ps.setDouble(2, car.getWeight());
            ps.setDouble(3, car.getSuspensionWidth());
            ps.setDouble(4, car.getVolume());
            ps.setDouble(5, car.getMaxWeight());
            ps.setDouble(6, car.getCurrentVolume());
            ps.setDouble(7, car.getCurrentWeight());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteFreightCar(int carId) {
        String sql = "DELETE FROM freight_cars WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, carId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<FreightCar> getFreightCarsByTrainId(int trainId) {
        List<FreightCar> cars = new ArrayList<>();
        String sql = "SELECT * FROM freight_cars WHERE train_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, trainId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                FreightCar car = new FreightCar(
                        rs.getInt("id"),
                        rs.getDouble("weight"),
                        rs.getDouble("suspension_width"),
                        rs.getDouble("max_volume"),
                        rs.getDouble("max_weight")
                );
                car.setCurrentWeight(rs.getDouble("current_weight"));
                car.setCurrentVolume(rs.getDouble("current_volume"));
                cars.add(car);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cars;
    }
}

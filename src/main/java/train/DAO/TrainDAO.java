package train.DAO;

import train.models.Train;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TrainDAO {

    public int saveTrain(String name) {
        String sql = "INSERT INTO trains (name) VALUES (?) RETURNING id";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt("id");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public List<String> getAllTrains() {
        List<String> trains = new ArrayList<>();
        String sql = "SELECT * FROM trains";
        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                trains.add(rs.getInt("id") + ": " + rs.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return trains;
    }

    public void deleteTrain(int trainId) {
        // Завдяки FOREIGN KEY з ON DELETE CASCADE, цей запит видалить
        // пов'язані записи з locomotives, passenger_cars та freight_cars.
        String sql = "DELETE FROM trains WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, trainId);
            int rowsAffected = ps.executeUpdate();

            // Додамо логіку перевірки
            if (rowsAffected == 0) {
                System.out.println("Помилка: Потяг з ID " + trainId + " не знайдено.");
            }

        } catch (SQLException e) {
            System.err.println("Помилка БД при видаленні потяга: " + e.getMessage());
        }
    }
}


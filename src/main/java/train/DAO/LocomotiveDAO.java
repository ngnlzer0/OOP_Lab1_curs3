package train.DAO;

import train.enums.FuelType;
import train.models.Locomotive;
import java.sql.*;

public class LocomotiveDAO {

    public void saveLocomotive(int trainId, Locomotive loco) {
        String sql = """
            INSERT INTO locomotives (train_id, weight, suspension_width, potency, fuel_type, max_speed)
            VALUES (?, ?, ?, ?, ?, ?)
            """;
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, trainId);
            ps.setDouble(2, loco.getWeight());
            ps.setDouble(3, loco.getSuspensionWidth());
            ps.setDouble(4, loco.getPotency());
            ps.setString(5, loco.getFuelType().name());
            ps.setDouble(6, loco.getMaxSpeed());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Locomotive getLocomotiveByTrainId(int trainId) {
        String sql = "SELECT * FROM locomotives WHERE train_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, trainId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Locomotive(
                        rs.getInt("id"),
                        rs.getDouble("weight"),
                        rs.getDouble("suspension_width"),
                        rs.getDouble("potency"),
                        FuelType.valueOf(rs.getString("fuel_type")),
                        rs.getDouble("max_speed")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}

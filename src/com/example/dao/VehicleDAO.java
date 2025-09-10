package com.example.dao;

import com.example.model.Vehicle;
import com.example.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VehicleDAO {

    public List<Vehicle> getAllVehicles() {
        List<Vehicle> vehicles = new ArrayList<>();
        String sql = "SELECT * FROM Vehicles";
        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Vehicle v = new Vehicle(
                    rs.getInt("id"),
                    rs.getInt("customer_id"),
                    rs.getString("vehicle_type"),
                    rs.getString("number_plate")
                );
                vehicles.add(v);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return vehicles;
    }

    public boolean registerVehicle(Vehicle vehicle) {
        String sql = "INSERT INTO Vehicles (customer_id, vehicle_type, number_plate) VALUES (?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, vehicle.getCustomerId());
            ps.setString(2, vehicle.getVehicleType());
            ps.setString(3, vehicle.getNumberPlate());

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}

package com.example.dao;

import com.example.model.ParkingSession;
import com.example.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ParkingSessionDAO {

    public List<ParkingSession> getAllSessions() {
        List<ParkingSession> sessions = new ArrayList<>();
        String sql = "SELECT * FROM ParkingSessions";
        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                ParkingSession session = new ParkingSession(
                    rs.getInt("id"),
                    rs.getInt("vehicle_id"),
                    rs.getString("slot_id"),
                    rs.getTimestamp("entry_time"),
                    rs.getTimestamp("exit_time"),
                    rs.getDouble("charges")
                );
                sessions.add(session);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sessions;
    }

    public boolean startSession(ParkingSession session) {
        String sql = "INSERT INTO ParkingSessions (vehicle_id, slot_id, entry_time) VALUES (?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, session.getVehicleId());
            ps.setString(2, session.getSlotId());
            ps.setTimestamp(3, session.getEntryTime());

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean endSession(int sessionId, Timestamp exitTime, double charges) {
        String sql = "UPDATE ParkingSessions SET exit_time = ?, charges = ? WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setTimestamp(1, exitTime);
            ps.setDouble(2, charges);
            ps.setInt(3, sessionId);

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}

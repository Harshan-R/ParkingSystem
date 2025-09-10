package com.example.dao;

import com.example.model.TopUpRequest;
import com.example.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TopUpRequestDAO {

    public List<TopUpRequest> getAllRequests() {
        List<TopUpRequest> requests = new ArrayList<>();
        String sql = "SELECT * FROM TopUpRequests";
        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                TopUpRequest request = new TopUpRequest(
                    rs.getInt("id"),
                    rs.getInt("customer_id"),
                    rs.getDouble("amount"),
                    rs.getString("status"),
                    rs.getTimestamp("request_date")
                );
                requests.add(request);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return requests;
    }

    public boolean updateRequestStatus(int requestId, String newStatus) {
        String sql = "UPDATE TopUpRequests SET status = ? WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, newStatus);
            ps.setInt(2, requestId);

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean createRequest(TopUpRequest request) {
        String sql = "INSERT INTO TopUpRequests (customer_id, amount, status) VALUES (?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, request.getCustomerId());
            ps.setDouble(2, request.getAmount());
            ps.setString(3, request.getStatus());

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}

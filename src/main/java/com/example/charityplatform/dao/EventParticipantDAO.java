package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EventParticipantDAO {
    private final Connection conn;

    public EventParticipantDAO(Connection conn) {
        this.conn = conn;
    }

    // 取得指定使用者已參加的活動ID列表
    public List<Integer> getJoinedEventIdsByUserId(int userId) throws SQLException {
        String sql = "SELECT EventID FROM EventParticipants WHERE UserID = ?";
        List<Integer> eventIds = new ArrayList<>();

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    eventIds.add(rs.getInt("EventID"));
                }
            }
        }
        return eventIds;
    }

    // 新增使用者報名活動紀錄
    public boolean joinEvent(int userId, int eventId) throws SQLException {
        String sql = "INSERT INTO EventParticipants (UserID, EventID) VALUES (?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setInt(2, eventId);
            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        }
    }

    // 取消使用者報名活動紀錄
    public boolean cancelJoinEvent(int userId, int eventId) throws SQLException {
        String sql = "DELETE FROM EventParticipants WHERE UserID = ? AND EventID = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setInt(2, eventId);
            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        }
    }
    public boolean isJoined(int userId, int eventId) throws SQLException {
        String sql = "SELECT 1 FROM EventParticipants WHERE UserID = ? AND EventID = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setInt(2, eventId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }
}

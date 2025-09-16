package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import model.Event;

public class EventDAO {
	private Connection conn;

	public EventDAO(Connection conn) {
		this.conn = conn;
	}

	public Event getEventById(int eventId) throws SQLException {
		String sql = "SELECT * FROM Events WHERE EventID = ?";
		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, eventId);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					Event event = new Event();
					event.setEventId(rs.getInt("EventID"));
					event.setCharityId(rs.getInt("CharitiesID")); // 注意你的欄位名稱
					event.setEventName(rs.getString("EventName"));
					event.setEventDescription(rs.getString("EventDescription"));
					event.setEventDate(rs.getDate("EventDate"));
					event.setEventLocation(rs.getString("Location"));
					event.setEventStart(rs.getTime("EventStart")); // 新增開始時間
					event.setEventEnd(rs.getTime("EventEnd")); // 新增結束時間
					return event;
				}
			}
		}
		return null;
	}

	public List<Event> getEventByCharityId(int charityId) throws SQLException {
		String sql = "SELECT * FROM Events WHERE CharitiesID = ?";
		List<Event> eventList = new ArrayList<>();

		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, charityId);
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					Event event = new Event();
					event.setEventId(rs.getInt("EventID"));
					event.setCharityId(rs.getInt("CharitiesID"));
					event.setEventName(rs.getString("EventName"));
					event.setEventDescription(rs.getString("EventDescription"));
					event.setEventDate(rs.getDate("EventDate"));
					event.setEventLocation(rs.getString("Location"));
					event.setEventStart(rs.getTime("EventStart"));
					event.setEventEnd(rs.getTime("EventEnd"));
					eventList.add(event);
				}
			}
		}
		return eventList;
	}

	public List<Event> getAllEvents() throws SQLException {
		String sql = "SELECT * FROM Events";
		List<Event> eventList = new ArrayList<>();

		try (PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
			while (rs.next()) {
				Event event = new Event();
				event.setEventId(rs.getInt("EventID"));
				event.setCharityId(rs.getInt("CharitiesID"));
				event.setEventName(rs.getString("EventName"));
				event.setEventDescription(rs.getString("EventDescription"));
				event.setEventDate(rs.getDate("EventDate"));
				event.setEventLocation(rs.getString("Location"));
				event.setEventStart(rs.getTime("EventStart"));
				event.setEventEnd(rs.getTime("EventEnd"));
				eventList.add(event);
			}
		}
		return eventList;
	}

	public boolean updateEvent(Event event) throws SQLException {
		String sql = "UPDATE Events SET EventName = ?, EventDescription = ?, EventDate = ?, Location = ?, EventStart = ?, EventEnd = ? WHERE EventID = ?";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, event.getEventName());
			pstmt.setString(2, event.getEventDescription());
			pstmt.setDate(3, event.getEventDate());
			pstmt.setString(4, event.getEventLocation());
			pstmt.setTime(5, event.getEventStart());
			pstmt.setTime(6, event.getEventEnd());
			pstmt.setInt(7, event.getEventId());

			int affectedRows = pstmt.executeUpdate();
			System.out.println("影響的列數: " + affectedRows);
			return affectedRows > 0;
		}
	}

	public void deleteEvent(int eventId) throws SQLException {
		String sql = "DELETE FROM Events WHERE EventID = ?";
		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, eventId);
			ps.executeUpdate();
		}
	}

	public void deleteEventAndParticipants(int eventId) throws SQLException {
		String deleteParticipants = "DELETE FROM EventParticipants WHERE EventID = ?";
		String deleteEvent = "DELETE FROM Events WHERE EventID = ?";

		try (PreparedStatement ps1 = conn.prepareStatement(deleteParticipants);
				PreparedStatement ps2 = conn.prepareStatement(deleteEvent)) {
			ps1.setInt(1, eventId);
			ps1.executeUpdate();

			ps2.setInt(1, eventId);
			ps2.executeUpdate();
		}
	}

	public void joinEvent(int userId, int eventId) throws SQLException {
		String sql = "INSERT INTO EventParticipants (UserID, EventID) VALUES (?, ?)";
		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, userId);
			ps.setInt(2, eventId);
			ps.executeUpdate();
		}
	}

	public List<Integer> getJoinedEventIds(int userId) throws SQLException {
		String sql = "SELECT EventID FROM EventParticipants WHERE UserID = ?";
		List<Integer> ids = new ArrayList<>();
		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, userId);
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					ids.add(rs.getInt("EventID"));
				}
			}
		}
		return ids;
	}

	public void insertEvent(Event event) throws SQLException {
		String sql = "INSERT INTO Events (CharitiesID, EventName, EventDescription, EventDate, Location, EventStart, EventEnd) VALUES (?, ?, ?, ?, ?, ?, ?)";
		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, event.getCharityId());
			ps.setString(2, event.getEventName());
			ps.setString(3, event.getEventDescription());
			ps.setDate(4, event.getEventDate());
			ps.setString(5, event.getEventLocation());
			ps.setTime(6, event.getEventStart());
			ps.setTime(7, event.getEventEnd());
			ps.executeUpdate();
		}
	}

	public boolean isEventOwnedByCharity(int eventId, int charityId) throws SQLException {
	    String sql = "SELECT COUNT(*) FROM Events WHERE EventID = ? AND CharitiesID = ?";
	    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
	        stmt.setInt(1, eventId);
	        stmt.setInt(2, charityId);
	        try (ResultSet rs = stmt.executeQuery()) {
	            if (rs.next()) {
	                return rs.getInt(1) > 0;
	            }
	        }
	    }
	    return false;
	}

}
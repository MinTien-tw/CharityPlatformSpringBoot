package util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountDAO {

	private Connection conn;

	public AccountDAO(Connection conn) {
		this.conn = conn;
	}

	public boolean isAccountExist(String account) throws SQLException {
		String userSql = "SELECT 1 FROM Users WHERE UserAccount = ?";
		String charitySql = "SELECT 1 FROM Charities WHERE CharitiesAccount = ?";

		try (PreparedStatement userPs = conn.prepareStatement(userSql)) {
			userPs.setString(1, account);
			try (ResultSet userRs = userPs.executeQuery()) {
				if (userRs.next()) {
					return true;
				}
			}
		}

		try (PreparedStatement charityPs = conn.prepareStatement(charitySql)) {
			charityPs.setString(1, account);
			try (ResultSet charityRs = charityPs.executeQuery()) {
				return charityRs.next();
			}
		}
	}

	// 驗證一般會員帳密是否正確
	public boolean validateUser(String account, String password) throws SQLException {
		String sql = "SELECT 1 FROM Users WHERE UserAccount = ? AND Password = ?";
		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, account);
			ps.setString(2, password);
			try (ResultSet rs = ps.executeQuery()) {
				return rs.next();
			}
		}
	}

	// 驗證慈善團體帳密是否正確
	public boolean validateCharity(String account, String password) throws SQLException {
		String sql = "SELECT 1 FROM Charities WHERE CharitiesAccount = ? AND CharitiesPassword = ?";
		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, account);
			ps.setString(2, password);
			try (ResultSet rs = ps.executeQuery()) {
				return rs.next();
			}
		}
	}

	// 透過慈善團體帳號取得 CharityID
	public int getCharityIdByAccount(String account) throws SQLException {
		String sql = "SELECT CharitiesID FROM Charities WHERE CharitiesAccount = ?";
		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, account);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return rs.getInt("CharitiesID");
				}
			}
		}
		return -1;
	}

	// 透過使用者帳號取得 UserID
	public int getUserIdByAccount(String account) throws SQLException {
		String sql = "SELECT UserID FROM Users WHERE UserAccount = ?";
		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, account);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return rs.getInt("UserID");
				}
			}
		}
		return -1;
	}
}

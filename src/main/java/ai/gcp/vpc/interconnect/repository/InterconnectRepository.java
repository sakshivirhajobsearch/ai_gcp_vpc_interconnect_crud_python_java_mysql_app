package ai.gcp.vpc.interconnect.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class InterconnectRepository {

	// DB Connection Setup
	private static final String DB_URL = "jdbc:mysql://localhost:3306/ai_gcp_vpc_interconnect?useSSL=false&allowPublicKeyRetrieval=true";
	private static final String DB_USER = "root";
	private static final String DB_PASSWORD = "admin"; // ← change to your actual MySQL password

	// Load JDBC Driver (for MySQL 8+)
	static {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver"); // Ensure driver is registered
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	// Insert new interconnect
	public void insertInterconnect(String name, String location, String bandwidth, String state) {
		String query = "INSERT INTO interconnect_details (interconnect_name, location, bandwidth, state) VALUES (?, ?, ?, ?)";
		try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
				PreparedStatement pstmt = conn.prepareStatement(query)) {
			pstmt.setString(1, name);
			pstmt.setString(2, location);
			pstmt.setString(3, bandwidth);
			pstmt.setString(4, state);
			int rows = pstmt.executeUpdate();
			System.out.println("Inserted " + rows + " row(s).");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Update interconnect by ID
	public void updateInterconnect(int id, String name, String location, String bandwidth, String state) {
		String query = "UPDATE interconnect_details SET interconnect_name=?, location=?, bandwidth=?, state=? WHERE id=?";
		try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
				PreparedStatement pstmt = conn.prepareStatement(query)) {
			pstmt.setString(1, name);
			pstmt.setString(2, location);
			pstmt.setString(3, bandwidth);
			pstmt.setString(4, state);
			pstmt.setInt(5, id);
			int rows = pstmt.executeUpdate();
			System.out.println("Updated " + rows + " row(s).");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Delete interconnect by ID
	public void deleteInterconnect(int id) {
		String query = "DELETE FROM interconnect_details WHERE id=?";
		try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
				PreparedStatement pstmt = conn.prepareStatement(query)) {
			pstmt.setInt(1, id);
			int rows = pstmt.executeUpdate();
			System.out.println("Deleted " + rows + " row(s).");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public String getAllInterconnects() {
		StringBuilder sb = new StringBuilder("=== Interconnect Details ===\n");
		String query = "SELECT * FROM interconnect_details";
		try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(query)) {
			while (rs.next()) {
				sb.append(rs.getInt("id")).append(" | ").append(rs.getString("interconnect_name")).append(" | ")
						.append(rs.getString("location")).append(" | ").append(rs.getString("bandwidth")).append(" | ")
						.append(rs.getString("state")).append("\n");
			}
		} catch (SQLException e) {
			sb.append("Error: ").append(e.getMessage());
		}
		return sb.toString();
	}

	public String getInterconnectById(int id) {
		StringBuilder sb = new StringBuilder();
		String query = "SELECT * FROM interconnect_details WHERE id=?";
		try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
				PreparedStatement pstmt = conn.prepareStatement(query)) {
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				sb.append("Interconnect Found:\n").append(rs.getInt("id")).append(" | ")
						.append(rs.getString("interconnect_name")).append(" | ").append(rs.getString("location"))
						.append(" | ").append(rs.getString("bandwidth")).append(" | ").append(rs.getString("state"))
						.append("\n");
			} else {
				sb.append("No Interconnect found with ID = ").append(id);
			}
		} catch (SQLException e) {
			sb.append("Error: ").append(e.getMessage());
		}
		return sb.toString();
	}

}

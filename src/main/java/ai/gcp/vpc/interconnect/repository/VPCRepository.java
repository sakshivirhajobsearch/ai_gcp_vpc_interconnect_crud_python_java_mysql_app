package ai.gcp.vpc.interconnect.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class VPCRepository {

	private static final String DB_URL = "jdbc:mysql://localhost:3306/ai_gcp_vpc_interconnect?useSSL=false&allowPublicKeyRetrieval=true";
	private static final String DB_USER = "root";
	private static final String DB_PASSWORD = "admin"; // Replace with your MySQL password

	// Load MySQL Driver
	static {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver"); // For MySQL 8+
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	// Insert VPC
	public void insertVPC(String name, String subnets, String region, String ipRange) {
		String query = "INSERT INTO vpc_details (vpc_name, subnet_name, region, ip_range) VALUES (?, ?, ?, ?)";
		try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
				PreparedStatement pstmt = conn.prepareStatement(query)) {
			pstmt.setString(1, name);
			pstmt.setString(2, subnets);
			pstmt.setString(3, region);
			pstmt.setString(4, ipRange);
			int rows = pstmt.executeUpdate();
			System.out.println("Inserted " + rows + " row(s).");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Update VPC by ID
	public void updateVPC(int id, String name, String subnets, String region, String ipRange) {
		String query = "UPDATE vpc_details SET vpc_name=?, subnet_name=?, region=?, ip_range=? WHERE id=?";
		try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
				PreparedStatement pstmt = conn.prepareStatement(query)) {
			pstmt.setString(1, name);
			pstmt.setString(2, subnets);
			pstmt.setString(3, region);
			pstmt.setString(4, ipRange);
			pstmt.setInt(5, id);
			int rows = pstmt.executeUpdate();
			System.out.println("Updated " + rows + " row(s).");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Delete VPC by ID
	public void deleteVPC(int id) {
		String query = "DELETE FROM vpc_details WHERE id=?";
		try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
				PreparedStatement pstmt = conn.prepareStatement(query)) {
			pstmt.setInt(1, id);
			int rows = pstmt.executeUpdate();
			System.out.println("Deleted " + rows + " row(s).");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public String getAllVPCs() {
		StringBuilder sb = new StringBuilder("=== VPC Details ===\n");
		String query = "SELECT * FROM vpc_details";
		try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(query)) {
			while (rs.next()) {
				sb.append(rs.getInt("id")).append(" | ").append(rs.getString("vpc_name")).append(" | ")
						.append(rs.getString("subnet_name")).append(" | ").append(rs.getString("region")).append(" | ")
						.append(rs.getString("ip_range")).append("\n");
			}
		} catch (SQLException e) {
			sb.append("Error: ").append(e.getMessage());
		}
		return sb.toString();
	}

	public String getVPCById(int id) {
		StringBuilder sb = new StringBuilder();
		String query = "SELECT * FROM vpc_details WHERE id=?";
		try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
				PreparedStatement pstmt = conn.prepareStatement(query)) {
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				sb.append("VPC Found:\n").append(rs.getInt("id")).append(" | ").append(rs.getString("vpc_name"))
						.append(" | ").append(rs.getString("subnet_name")).append(" | ").append(rs.getString("region"))
						.append(" | ").append(rs.getString("ip_range")).append("\n");
			} else {
				sb.append("No VPC found with ID = ").append(id);
			}
		} catch (SQLException e) {
			sb.append("Error: ").append(e.getMessage());
		}
		return sb.toString();
	}

}

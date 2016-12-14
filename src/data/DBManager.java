package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBManager 
{
	Connection conn;
	
	public DBManager() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver");
		conn = DriverManager.getConnection("jdbc:mysql://localhost/rankingsystem", "root", "");
	}
	
	public void addBenutzer(int benutzerID, String benutzername, int punktezahl) throws SQLException 
	{
		String sql = "INSERT INTO Benutzer VALUES(?, ?, ?)";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setInt(1, benutzerID);
		stmt.setString(2, benutzername);
		stmt.setInt(3, punktezahl);
		stmt.executeUpdate();
		stmt.close();
	}
	
	public void changePoints(int benutzerID, int punktezahl) throws SQLException
	{
		String sql = "UPDATE Benutzer SET Punktezahl = ? WHERE BenutzerID = ?";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setInt(1, punktezahl);
		stmt.setInt(2, benutzerID);
		stmt.executeUpdate();
		stmt.close();
	}
	
	public void close() throws SQLException {
		conn.close();
	}
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		DBManager db = new DBManager();
		db.addBenutzer(3, "Tali", 500);
		db.changePoints(3, 800);
	}
}
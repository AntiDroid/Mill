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
	
	public void addBenutzer(String benutzername, int punktezahl) throws SQLException 
	{
		String sql = "INSERT INTO Benutzer VALUES(?, ?)";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setString(1, benutzername);
		stmt.setInt(2, punktezahl);
		stmt.executeUpdate();
		stmt.close();
	}
	
	public void changePoints(String benutzername, int punktezahl) throws SQLException
	{
		String sql = "UPDATE Benutzer SET Punktezahl = ? WHERE Benutzername = ?";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setInt(1, punktezahl);
		stmt.setString(2, benutzername);
		stmt.executeUpdate();
		stmt.close();
	}
	
	public void close() throws SQLException 
	{
		conn.close();
	}
}
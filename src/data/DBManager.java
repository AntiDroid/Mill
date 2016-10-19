package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBManager 
{
	Connection conn;
	
	public DBManager() throws ClassNotFoundException, SQLException
	{
		Class.forName("com.mysql.jdbc.Driver");
		conn = DriverManager.getConnection("jdbc:mysql://localhost/rankingsystem", "root", "");
	}
	
	public void addBenutzer(int benutzerID, String vorname, String nachname, int punktezahl) throws SQLException
	{
		String sql = "INSERT INTO Benutzer VALUES(?, ?, ?, ?)";
		java.sql.PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setInt(1, benutzerID);
		stmt.setString(2, vorname);
		stmt.setString(3, nachname);
		stmt.setInt(4, punktezahl);
		stmt.executeUpdate(sql);
		stmt.close();
	}
	
	public void close() throws SQLException
	{
		conn.close();
	}
}
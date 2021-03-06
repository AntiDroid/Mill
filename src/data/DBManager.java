package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DBManager 
{
	Connection conn;
	
	public DBManager() throws ClassNotFoundException, SQLException 
	{
		Class.forName("com.mysql.jdbc.Driver");
		conn = DriverManager.getConnection("jdbc:mysql://localhost/rankingsystem", "root", "");
	}
	
	public void addBenutzer(String benutzername, int punktezahl) throws SQLException 
	{
		ArrayList<String> names = new ArrayList<String>();
		for(Benutzer b : viewBenutzer())
		{
			names.add(b.getBenutzername());
		}
		if(!names.contains(benutzername))
		{
			String sql = "INSERT INTO Benutzer VALUES(?, ?)";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, benutzername);
			stmt.setInt(2, punktezahl);
			stmt.executeUpdate();
			stmt.close();
		}
	}
	
	public void addPoints(String benutzername, int punktezahl) throws SQLException
	{
		int punkte = 0;
		Statement s = conn.createStatement();
		ResultSet rs = s.executeQuery("SELECT * FROM Benutzer WHERE Benutzername = '" + benutzername + "'");
		while(rs.next()) 
		{
			punkte = rs.getInt(2);
		}
		
		punkte += punktezahl;
		
		String sql = "UPDATE Benutzer SET Punktezahl = ? WHERE Benutzername = ?";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setInt(1, punkte);
		stmt.setString(2, benutzername);
		stmt.executeUpdate();
		stmt.close();
	}
	
	public ArrayList<Benutzer> viewBenutzer() throws SQLException
	{
		ArrayList<Benutzer> listBenutzer = new ArrayList<Benutzer>();
		java.sql.Statement s = conn.createStatement();
		ResultSet rs = s.executeQuery("SELECT * FROM Benutzer ORDER BY Punktezahl DESC");
		
		while(rs.next()) 
		{
			String benutzername = rs.getString(1);
			int punktezahl = rs.getInt(2);
			
			Benutzer b = new Benutzer(benutzername, punktezahl);
			listBenutzer.add(b);
		}
		
		return listBenutzer;
	}
	
	
	public void close() throws SQLException 
	{
		conn.close();
	}
	
}
package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
	
	public ArrayList<Benutzer> viewBenutzer() throws SQLException
	{
		ArrayList<Benutzer> listBenutzer = new ArrayList<Benutzer>();
		java.sql.Statement s = conn.createStatement();
		ResultSet rs = s.executeQuery("SELECT * FROM Benutzer");
		
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
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException
	{
		DBManager db = new DBManager();
		db.addBenutzer("Michl", 5000);
		for(Benutzer b: db.viewBenutzer()){
			System.out.print(b.getBenutzername()+" ");
			System.out.println(b.getPunktezahl());
		}
		
	}
}
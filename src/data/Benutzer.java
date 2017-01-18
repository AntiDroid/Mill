package data;

public class Benutzer 
{
	private String benutzername;
	private int punktezahl;
	
	public Benutzer(String benutzername, int punktezahl) 
	{
		this.benutzername = benutzername;
		this.punktezahl = punktezahl;
	}

	public String getBenutzername() {
		return benutzername;
	}

	public void setBenutzername(String benutzername) {
		this.benutzername = benutzername;
	}

	public int getPunktezahl() {
		return punktezahl;
	}

	public void setPunktezahl(int punktezahl) {
		this.punktezahl = punktezahl;
	}
}
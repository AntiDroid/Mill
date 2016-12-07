package data;

public class Position {

	private int ebene, x, y;
	private double koordX, koordY;
	
	private Stein belegung;
	
	public Position(int ebene, int x, int y, double kX, double kY){
		this.ebene = ebene;
		this.x = x;
		this.y = y;
		this.koordX = kX;
		this.koordY = kY;
		this.belegung = null;
	}

	public int getEbene() {
		return ebene;
	}

	public int getY() {
		return y;
	}

	public int getX() {
		return x;
	}	
	
	public String toString(){
		return "("+ebene+", "+x+", "+y+")";
	}

	public double getKoordY() {
		return koordY;
	}

	public double getKoordX() {
		return koordX;
	}
	
	public boolean isInRange(double posX, double posY){
		if(Math.abs((koordX+20) - posX) < 10 && Math.abs((koordY+20) - posY) < 10) return true;
		else return false;
	}

	public Stein getBelegung() {
		return belegung;
	}

	public void setBelegung(Stein belegung) {
		this.belegung = belegung;
	}
	
}

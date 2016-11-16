package data;

public class Position {

	private int ebene, x, y;
	private int koordX, koordY;
	
	public Position(int ebene, int x, int y, int kX, int kY){
		this.ebene = ebene;
		this.x = x;
		this.y = y;
		this.koordX = kX;
		this.koordY = kY;
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

	public int getKoordY() {
		return koordY;
	}

	public int getKoordX() {
		return koordX;
	}
	
}

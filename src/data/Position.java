package data;

public class Position {

	private int ebene, x, y;
	private double koordX, koordY;
	private boolean isBelegt;

	public Position(int ebene, int x, int y, double kX, double kY){
		this.ebene = ebene;
		this.x = x;
		this.y = y;
		this.koordX = kX;
		this.koordY = kY;
		this.isBelegt = false;
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

		int rangeDif = 15;

		if(Math.abs(koordX+20 - posX) < rangeDif && Math.abs(koordY+20 - posY) < rangeDif)
			return true;

		return false;
	}

	public boolean isBelegt() {
		return isBelegt;
	}

	public void setBelegt(boolean isBelegt) {
		this.isBelegt = isBelegt;
	}


}

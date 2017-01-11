package data;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Stein extends ImageView {
	
	private int id;
	
	static private int idCounter = 0;
	
    static Image imageG = new Image("file:res/img/grun.png");
    static Image imageR = new Image("file:res/img/rot.png");
	
	private boolean isRed, isThree;
	
	public Stein(boolean isR){
		if(isR)
			this.setImage(imageR);
		else
			this.setImage(imageG);
		
		this.setFitHeight(42);
		this.setFitWidth(42);
		
		this.isRed = isR;
		this.isThree = false;
		this.id = idCounter;
		idCounter++;
	}

	public static Image getImageG() {
		return imageG;
	}

	public static void setImageG(Image imageG) {
		Stein.imageG = imageG;
	}

	public static Image getImageR() {
		return imageR;
	}

	public static void setImageR(Image imageR) {
		Stein.imageR = imageR;
	}

	public boolean isRed() {
		return isRed;
	}

	public void setRed(boolean isRed) {
		this.isRed = isRed;
	}

	public boolean isThree() {
		return isThree;
	}

	public void setThree(boolean isThree) {
		this.isThree = isThree;
	}

	public int getID() {
		return id;
	}

	public void setID(int id) {
		this.id = id;
	}
	
	public boolean equals(Object o){
	
		if(o instanceof Stein){
			Stein x = (Stein) o;
		
			return (this.id == x.id) && (this.isRed == x.isRed);
		}

		return super.equals(o);
	}
	
}
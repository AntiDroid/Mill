package data;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Stein {

    static Image imageG = new Image("file:res/img/grun.png");
    static Image imageR = new Image("file:res/img/rot.png");
	
	private boolean isRed, isThree;
	private ImageView img;
	
	public Stein(boolean isR){
		if(isR)
			img.setImage(imageR);
		else
			img.setImage(imageG);
		
		this.isRed = isR;
		this.isThree = false;
	}
	
}

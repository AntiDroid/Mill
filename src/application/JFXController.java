package application;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;


public class JFXController implements Initializable {
	
	@FXML
	private Pane mainPane;
	
	@FXML
	private List<ImageView> greenSel;
	
	@FXML
	private List<ImageView> redSel;
	
	@FXML
	private ImageView feld;
	
	@FXML
	ImageView test;

    @Override // This method is called by the FXMLLoader when initialization is complete
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
    	
        assert greenSel != null : "fx:id=\"greenSel\" was not injected: check your FXML file 'GUI.fxml'.";
        assert redSel != null : "fx:id=\"redSel\" was not injected: check your FXML file 'GUI.fxml'.";
        
        assert mainPane != null : "fx:id=\"mainPane\" was not injected: check your FXML file 'GUI.fxml'.";

        // initialize your logic here: all @FXML variables will have been injected
        
        Image imageG = new Image("file:res/img/grun.png");
        Image imageR = new Image("file:res/img/rot.png");
        Image imageF = new Image("file:res/img/mill.png");
        
        feld.setImage(imageF);
        
        for(int i = 0; i<9; i++){
        	greenSel.get(i).setImage(imageG);
        	redSel.get(i).setImage(imageR);
        }
        
        test.setImage(imageR); 
        
        /*
        double x = mainPane.getBoundsInLocal().getWidth();
        double y = mainPane.getBoundsInLocal().getHeight();
        
        test.setLayoutX(x/4.795918367);
        test.setLayoutY(y/4.32);
        */
        
        
    }

}
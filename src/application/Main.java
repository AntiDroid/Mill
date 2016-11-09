package application;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;
    

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Mill");

        showLayout(primaryStage);
    }

    /**
     * Initializes the root layout.
     */
    public void showLayout(Stage s) {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("GUI.fxml"));
            rootLayout = (BorderPane) loader.load();
            
            rootLayout.getLeft().setStyle("-fx-padding: 10;" + 
                    "-fx-border-style: solid inside;" + 
                    "-fx-border-width: 5;" +
                    "-fx-border-insets: 1;" + 
                    "-fx-border-radius: 15;" + 
                    "-fx-border-color: DarkTurquoise;");
            
            rootLayout.getRight().setStyle("-fx-padding: 10;" + 
                    "-fx-border-style: solid inside;" + 
                    "-fx-border-width: 5;" +
                    "-fx-border-insets: 1;" + 
                    "-fx-border-radius: 15;" + 
                    "-fx-border-color: CornflowerBlue;");
            
            rootLayout.getCenter().setStyle("-fx-padding: 10;" + 
                    "-fx-border-style: solid inside;" + 
                    "-fx-border-width: 2;" +
                    "-fx-border-insets: 5;" + 
                    "-fx-border-radius: 5;" + 
                    "-fx-border-color: red;");
            
            // load the image
            Image imageF = new Image("file:res/img/mill.png");
            
            Image imageG = new Image("file:res/img/grun.png");
            Image imageR = new Image("file:res/img/rot.png");
            
            // simple displays ImageView the image as is
            ((ImageView)rootLayout.getCenter()).setImage(imageF);
            
            int value = 9;
            ImageView[] greenSel = new ImageView[value];
            ImageView[] redSel = new ImageView[value];
            
            for(int i = 0; i < value; i++){
            	greenSel[i] = (ImageView)loader.getNamespace().get("greenS"+(i+1));
            	greenSel[i].setImage(imageG);
            	
            	redSel[i] = (ImageView)loader.getNamespace().get("redS"+(i+1));
            	redSel[i].setImage(imageR);
            }
            

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.show();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Returns the main stage.
     * @return
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
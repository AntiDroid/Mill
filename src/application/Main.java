package application;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
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
            
            JFXController f = (JFXController) loader.getController();

            
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

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.show();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args); 
    }
}
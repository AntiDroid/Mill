package application;

import java.io.IOException;

import javax.swing.JOptionPane;

import data.Stein;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;

    static public String name1, name2;

    @Override
    public void start(Stage primaryStage) {

		namenAngeben();

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

            //JFXController f = (JFXController) loader.getController();

            s.getIcons().add(Stein.getImageG());

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
    
    private void namenAngeben(){
    	try{
    		do{
    		name1 = JOptionPane.showInputDialog(null, "Enter your name!",
                    "Spieler 1",
                    JOptionPane.PLAIN_MESSAGE);

    		if(name1.equals(""))
    			throw new NullPointerException();

    		}while(!name1.matches("\\w+"));

    		}
    		catch(NullPointerException n){
    			name1 = "Player 1";
    		}

    		try{

    		do{
    		name2 = JOptionPane.showInputDialog(null, "Enter your name!",
                    "Spieler 2",
                    JOptionPane.PLAIN_MESSAGE);

    		if(name2.equals(""))
    			throw new NullPointerException();

    		}while(name2.equals("") || !name2.matches("\\w+") || name2.equals(name1));

    		}
    		catch(NullPointerException n){
    			name2 = "Player 2";
    		}
    }
}
package data;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class Spielfeld implements Initializable {
	
	private int redState, greenState;
	private Position[][][] positionen;
	private Position posSelStein = null;
	
	static Image imageF = new Image("file:res/img/mill.png");
	
	@FXML
	private Pane mainPane;
	@FXML
	private ImageView feld;
	@FXML
	private GridPane greenGridSel;
	@FXML
	private GridPane redGridSel;
	
	List<Stein> greenSel = new ArrayList<Stein>();
	List<Stein> redSel = new ArrayList<Stein>();

	private boolean isRedTurn = true;

	Position[] pos = { new Position(0, 0, 0, 44, 70.5), new Position(0, 0, 1, 44, 249), new Position(0, 0, 2, 44, 428), new Position(0, 1, 0, 222.5, 70.5),
			new Position(0, 2, 0, 401, 70.5), new Position(0, 2, 1, 401, 249), new Position(0, 2, 2, 401, 428), new Position(0, 1, 2, 222.5, 428),
			new Position(1, 0, 0, 98, 125), new Position(1, 1, 0, 222.5, 125), new Position(1, 2, 0, 346, 125), new Position(1, 0, 1, 98, 249), new Position(1, 0, 2, 98, 372),
			new Position(1, 1, 2, 222.5, 372), new Position(1, 2, 2, 346, 372), new Position(1, 2, 1, 346, 249),
			new Position(2, 0, 0, 153, 179.5), new Position(2, 0, 1, 153, 249), new Position(2, 0, 2, 153, 316), new Position(2, 1, 0, 222.5, 179.5),
			new Position(2, 2, 0, 291.5, 179.5), new Position(2, 2, 1, 291.5, 249), new Position(2, 2, 2, 291.5, 316), new Position(2, 1, 2, 222.5, 316)};

    @Override // This method is called by the FXMLLoader when initialization is complete
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
    	
		positionen = new Position[3][3][3];
		
		for(int ebene = 0; ebene < 3; ebene++){
			for(int x = 0; x < 3; x++){
				for(int y = 0; y < 3; y++){
					positionen[ebene][x][y] = new Position(ebene, x, y, 5, 5);
				}
			}
		}

		redState = 0;
		greenState = 0;

    	assert greenGridSel != null : "fx:id=\"greenGridSel\" was not injected: check your FXML file 'GUI.fxml'.";
    	assert redGridSel != null : "fx:id=\"redGridSel\" was not injected: check your FXML file 'GUI.fxml'.";
    	
        assert mainPane != null : "fx:id=\"mainPane\" was not injected: check your FXML file 'GUI.fxml'.";

        // initialize your logic here: all @FXML variables will have been injected
        
        feld.setImage(imageF);

        for(int i = 0; i<10; i++){
        	greenSel.add(new Stein(false));
        	redSel.add(new Stein(true));
        }
        
        int x = 0;
        
        for(int i = 0; i<5; i++){
        	for(int j = 0; j < 2; j++){
        		if(i == 4 && j == 1)
        			break;
        		greenGridSel.add((ImageView)greenSel.get(x), j, i);
        		x++;
        	}
        }
        
        x = 0;
        
        for(int i = 0; i < 5; i++){
        	for(int j = 0; j < 2; j++){
        		if(i == 4 && j == 0)
        			j++;
        		redGridSel.add((ImageView)redSel.get(x), j, i);
        		x++;
        	}
        }

        mainPane.setOnMouseClicked(new EventHandler<MouseEvent>(){

            @Override
            public void handle(MouseEvent t) {
            	
            	
            	
            	
            	
            	if((isRedTurn && !redGridSel.getChildren().isEmpty()) || (!isRedTurn && !greenGridSel.getChildren().isEmpty())){
            		
            		for(int i = 0; i < pos.length; i++){

            			if(pos[i].getBelegung() == null && pos[i].isInRange(t.getX(), t.getY())){
                			
                			isRedTurn = !isRedTurn;
                				
            				if(!isRedTurn){
            						
            					int index = redGridSel.getChildren().size()-1;
            					
            					pos[i].setBelegung(redSel.get(index));
            					
            					redGridSel.getChildren().remove(redSel.get(index));
            					mainPane.getChildren().add(redSel.get(index));
            					redSel.get(index).setLayoutX(pos[i].getKoordX());
            					redSel.get(index).setLayoutY(pos[i].getKoordY());

            					break;
            				}
            				else{
            						
            					int index = greenGridSel.getChildren().size()-1;
            						
            					pos[i].setBelegung(greenSel.get(index));
            					
            					greenGridSel.getChildren().remove(greenSel.get(index));
            					mainPane.getChildren().add(greenSel.get(index));
            					greenSel.get(index).setLayoutX(pos[i].getKoordX());
            					greenSel.get(index).setLayoutY(pos[i].getKoordY());

            					break;
            				}
            			}
            		}

            	}

            }

        });
    }
	
	
	public ArrayList<Position> movePos(Position p){
		ArrayList<Position> movePoss = new ArrayList<Position>();
		int pSum = p.getEbene()+p.getX()+p.getY();
		
		for(int ebene = 0; ebene < 3; ebene++){
			for(int x = 0; x < 3; x++){
				for(int y = 0; y < 3; y++){
					Position n = positionen[ebene][x][y];
					int nSum = n.getEbene()+n.getX()+n.getY();
					
					//Summe darf sich nur um eins �ndern
					if(Math.abs(pSum - nSum) == 1){
					
						// Eckstein
						if((p.getX()+p.getY())%2 == 0){
							//Muss sich auf der gleichen Ebene Bewegen
							if(p.getEbene() == n.getEbene()){
								movePoss.add(n);
							}
						}
						// Mittelstein
						if((p.getX()+p.getY())%2 == 1){
							// Gleiche Ebene
							if(p.getEbene() == n.getEbene()){
								movePoss.add(n);
							}
							// Ebenenwechsel
							if(p.getEbene() != n.getEbene()){
								// Darf sich nur die Ebene �ndern
								if(p.getX() == n.getX() && p.getY() == n.getY()){
									movePoss.add(n);
								}
							}
						}	
					}
					
				}
			}
		}
		
		return movePoss;
	}


	public int getRedState() {
		return redState;
	}


	public void setRedState(int redState) {
		this.redState = redState;
	}


	public Position getPosSelStein() {
		return posSelStein;
	}


	public void setPosSelStein(Position posSelStein) {
		this.posSelStein = posSelStein;
	}


	public int getGreenState() {
		return greenState;
	}


	public void setGreenState(int greenState) {
		this.greenState = greenState;
	}
	
}

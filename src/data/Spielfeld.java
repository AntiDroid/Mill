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
	
	private int state;
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
		
		state = 0;

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
            	
            	//Defintion des jeweiligen Teams und seiner Daten
            	GridPane gridSel = null;
            	List<Stein> sel = null;
            	
            	if(isRedTurn){
            		gridSel = redGridSel;
            		sel = redSel;
            	}
            	else{
            		gridSel = greenGridSel;
            		sel = greenSel;
            	}
            	
            	//Statusdefinition
        		if(!gridSel.getChildren().isEmpty())
        			state = 0;
        		else
        			state = 1;
            	
        		
        		//Abhandlung je nach Status
            	switch(state){
            	
            	case 0: 
            		anfangsphase(gridSel, sel, t);
            		break;
            		
            	case 1:
            		//1.1 auswahl des steins
            		if(posSelStein == null){
	            		for(int i = 0; i < pos.length; i++){
	            			
	            			//wenn das Feld belegt ist und der Klick auf das Feld war
	            			if(pos[i].getBelegung() != null && pos[i].getBelegung().isRed() == isRedTurn && pos[i].isInRange(t.getX(), t.getY())){
	                			posSelStein = pos[i];
	                    		System.out.println("selected "+posSelStein.toString());
	                  
	            				break;
	            			}
	            		}
            		}
            		
            		//1.2 Verschieben auf ausgewählte Position
            		else{
            			System.out.println("go");
            			verschieben(t);
            		}
            		break;
            		
            	}
            }
        });
    }
	
    public void verschieben(MouseEvent t) {
		
    	outerloop:
		for(int i = 0; i < pos.length; i++){
			
			//wenn das Feld leer ist und ausgewählt wurde
			if(pos[i].getBelegung() == null && pos[i].isInRange(t.getX(), t.getY())){
				for(Position p: movePos()){
					
					if(p.getEbene() == pos[i].getEbene() && p.getX() == pos[i].getX() && p.getY() == pos[i].getY())	
						
						posSelStein.getBelegung().setLayoutX(pos[i].getKoordX());
						move(pos[i]);
					
						System.out.println("gangen");
						break outerloop;
				}
				
			}
			
		}
    
    	isRedTurn = !isRedTurn;
	}

	public ArrayList<Position> movePos(){
		ArrayList<Position> movePoss = new ArrayList<Position>();
		
		int pEbene = posSelStein.getEbene();
		int pX = posSelStein.getX();
		int pY = posSelStein.getY();
		
		int pSum = pEbene + pX + pY;
		
		for(int ebene = 0; ebene < 3; ebene++){
			for(int x = 0; x < 3; x++){
				for(int y = 0; y < 3; y++){
					Position n = positionen[ebene][x][y];
					int nSum = n.getEbene()+n.getX()+n.getY();
					
					//Summe darf sich nur um eins �ndern
					if(Math.abs(pSum - nSum) == 1){
					
						// Eckstein
						if((pX+pY)%2 == 0){
							//Muss sich auf der gleichen Ebene Bewegen
							if(pEbene == n.getEbene()){
								movePoss.add(n);
							}
						}
						// Mittelstein
						if((pX+pY)%2 == 1){
							// Gleiche Ebene
							if(pEbene == n.getEbene()){
								movePoss.add(n);
							}
							// Ebenenwechsel
							if(pEbene != n.getEbene()){
								// Darf sich nur die Ebene �ndern
								if(pX == n.getX() && pY == n.getY()){
									movePoss.add(n);
								}
							}
						}	
					}
					
				}
			}
		}
		
		for(int i = movePoss.size(); i == 0; i--){
			if(movePoss.get(i-1).getBelegung() != null)
				movePoss.remove(i-1);
		}
		
		return movePoss;
	}
	
	public void move(Position p){
		
		posSelStein.getBelegung().setLayoutX(p.getKoordX());
		posSelStein.getBelegung().setLayoutY(p.getKoordY());
		
		Stein cur = posSelStein.getBelegung();
		posSelStein.setBelegung(null);
		p.setBelegung(cur);
	
		posSelStein = null;
	}

	public void anfangsphase(GridPane gridSel, List<Stein> sel, MouseEvent t){
		
		for(int i = 0; i < pos.length; i++){

			if(pos[i].getBelegung() == null && pos[i].isInRange(t.getX(), t.getY())){
    			
    			isRedTurn = !isRedTurn;
				int index = gridSel.getChildren().size()-1;
					
				pos[i].setBelegung(sel.get(index));
					
				gridSel.getChildren().remove(sel.get(index));
				mainPane.getChildren().add(sel.get(index));
				sel.get(index).setLayoutX(pos[i].getKoordX());
				sel.get(index).setLayoutY(pos[i].getKoordY());

				break;
				
			}
		}
	}

	public Position getPosSelStein() {
		return posSelStein;
	}

	public void setPosSelStein(Position posSelStein) {
		this.posSelStein = posSelStein;
	}
	
	public boolean isMuehle(boolean isRedM){
		//Muehles in gleicher Ebene
		for(int ebene = 0; ebene < 3; ebene++){
			if(positionen[0][0][0].getBelegung().isRed() == isRedM && positionen[0][0][1].getBelegung().isRed() == isRedM && positionen[0][0][2].getBelegung().isRed() == isRedM) return true;
			if(positionen[0][0][0].getBelegung().isRed() == isRedM && positionen[0][1][0].getBelegung().isRed() == isRedM && positionen[0][2][0].getBelegung().isRed() == isRedM) return true;
			if(positionen[0][2][0].getBelegung().isRed() == isRedM && positionen[0][2][1].getBelegung().isRed() == isRedM && positionen[0][2][2].getBelegung().isRed() == isRedM) return true;
			if(positionen[0][0][2].getBelegung().isRed() == isRedM && positionen[0][1][2].getBelegung().isRed() == isRedM && positionen[0][2][2].getBelegung().isRed() == isRedM) return true;
		}
		// Muehles auf dem Fadenkre
		if(positionen[0][1][0].getBelegung().isRed() == isRedM && positionen[1][1][0].getBelegung().isRed() == isRedM && positionen[2][1][0].getBelegung().isRed() == isRedM) return true;
		if(positionen[0][0][1].getBelegung().isRed() == isRedM && positionen[1][0][1].getBelegung().isRed() == isRedM && positionen[2][0][1].getBelegung().isRed() == isRedM) return true;
		if(positionen[0][1][2].getBelegung().isRed() == isRedM && positionen[1][1][2].getBelegung().isRed() == isRedM && positionen[2][1][2].getBelegung().isRed() == isRedM) return true;
		if(positionen[0][2][1].getBelegung().isRed() == isRedM && positionen[1][2][1].getBelegung().isRed() == isRedM && positionen[2][2][1].getBelegung().isRed() == isRedM) return true;
		
		return false;
	}
	
}

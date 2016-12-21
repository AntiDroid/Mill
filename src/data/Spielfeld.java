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
	private static ArrayList<Integer> redMuehleList = new ArrayList<Integer>();
	private static ArrayList<Integer> greenMuehleList = new ArrayList<Integer>();
	
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
	            				
	            				if(movePos().size() == 0)
	            					posSelStein = null;
	            				else{
	            					System.out.println("\nselected "+posSelStein.toString());
	            					for(Position p: movePos()){
	            						System.out.println(p);
	            					}
	            				}
	            				break;
	            			}
	            		}
            		}
            		
            		//1.2 Verschieben auf ausgewählte Position
            		else{
            			System.out.println("mögliche Auswahl");
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
					
					if(p.equals(pos[i])){	
						move(pos[i]);
					
						System.out.println("Position geändert!");
						break outerloop;
					}
				}
				
			}
			
		}
    
    	isRedTurn = !isRedTurn;
	}

	public ArrayList<Position> movePos(){
		
		ArrayList<Position> movePoss = new ArrayList<Position>();
		
		int curEbene = posSelStein.getEbene();
		int curX = posSelStein.getX();
		int curY = posSelStein.getY();
		
		int pSum = curEbene + curX + curY;
		
		for(int ebene = 0; ebene < 3; ebene++){
			
			for(int x = 0; x < 3; x++){
				
				for(int y = 0; y < 3; y++){
					
					Position n = positionen[ebene][x][y];
					
					//Wenn die zu prüfende Position NICHT belegt ist
					if(n.getBelegung() == null && !(x == 1 && y == 1)){
					
						// Eckstein
						if((curX+curY)%2 == 0){
							//Muss sich auf der gleichen Ebene Bewegen
							if(curEbene == n.getEbene()){
								if(Math.abs(curX - x) == 1 && (curY - y) == 0)
									movePoss.add(n);
								else if(Math.abs(curY - y) == 1 && (curX - x) == 0)
									movePoss.add(n);
							}
						}
						// Mittelstein
						else {
							// Gleiche Ebene
							if(curEbene == n.getEbene()){
								if(Math.abs(curX - x) == 1 && (curY - y) == 0)
									movePoss.add(n);
								else if(Math.abs(curY - y) == 1 && (curX - x) == 0)
									movePoss.add(n);
							}
							// Ebenenwechsel
							else {
								// Darf sich nur die Ebene �ndern
								if(curX == n.getX() && curY == n.getY() && Math.abs(curEbene-ebene) == 1)
									movePoss.add(n);
							}
						}
						
						
					}
				}
			}
		}
		
		return movePoss;
	}
	
	public void move(Position p){
		
		int curX = (int) posSelStein.getBelegung().getLayoutX();
		int curY = (int) posSelStein.getBelegung().getLayoutY();
		
		int destX = (int) p.getKoordX();
		int destY = (int) p.getKoordY();
	    
		posSelStein.getBelegung().setLayoutX(destX);
		posSelStein.getBelegung().setLayoutY(destY);
		
		p.setBelegung(posSelStein.getBelegung());
		posSelStein.setBelegung(null);
	
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
		int muehleID = 0;
		ArrayList<Integer> muehleList;
		if(isRedM) muehleList = redMuehleList;
		else 		muehleList = greenMuehleList;
		
		ArrayList<Integer> lastMuehleList = muehleList;
		muehleList.clear();
		
		//Muehlen in gleicher Ebene
		for(int e = 0; e < 3; e++){
			if(positionen[e][0][0].getBelegung() != null && positionen[e][0][1].getBelegung() != null && positionen[e][0][2].getBelegung() != null ){
				if(positionen[e][0][0].getBelegung().isRed() == isRedM && positionen[e][0][1].getBelegung().isRed() == isRedM && positionen[e][0][2].getBelegung().isRed() == isRedM){
					muehleID = 10+e;
					muehleList.add(muehleID);}	
			}
			if(positionen[e][0][0].getBelegung() != null && positionen[e][1][0].getBelegung() != null && positionen[e][2][0].getBelegung() != null ){
				if(positionen[e][0][0].getBelegung().isRed() == isRedM && positionen[e][1][0].getBelegung().isRed() == isRedM && positionen[e][2][0].getBelegung().isRed() == isRedM){
					muehleID = 20+e;
					muehleList.add(muehleID);}	
			}
			if(positionen[e][2][0].getBelegung() != null && positionen[e][2][1].getBelegung() != null && positionen[e][2][2].getBelegung() != null ){
				if(positionen[e][2][0].getBelegung().isRed() == isRedM && positionen[e][2][1].getBelegung().isRed() == isRedM && positionen[e][2][2].getBelegung().isRed() == isRedM){
					muehleID = 30+e;
					muehleList.add(muehleID);}	
			}
			if(positionen[e][0][2].getBelegung() != null && positionen[e][1][2].getBelegung() != null && positionen[e][2][2].getBelegung() != null ){
				if(positionen[e][0][2].getBelegung().isRed() == isRedM && positionen[e][1][2].getBelegung().isRed() == isRedM && positionen[e][2][2].getBelegung().isRed() == isRedM){
					muehleID = 40+e;
					muehleList.add(muehleID);}		
			}
		}
		// Muehlen auf dem Fadenkreuz
		if(positionen[0][1][0].getBelegung() != null && positionen[1][1][0].getBelegung() != null && positionen[2][1][0].getBelegung() != null ){
			if(positionen[0][1][0].getBelegung().isRed() == isRedM && positionen[1][1][0].getBelegung().isRed() == isRedM && positionen[2][1][0].getBelegung().isRed() == isRedM){
				muehleID = 1;
				muehleList.add(muehleID);}		
		}
		if(positionen[0][0][1].getBelegung() != null && positionen[1][0][1].getBelegung() != null && positionen[2][0][1].getBelegung() != null ){
			if(positionen[0][0][1].getBelegung().isRed() == isRedM && positionen[1][0][1].getBelegung().isRed() == isRedM && positionen[2][0][1].getBelegung().isRed() == isRedM){
				muehleID = 2;
				muehleList.add(muehleID);}
		}
		if(positionen[0][1][2].getBelegung() != null && positionen[1][1][2].getBelegung() != null && positionen[2][1][2].getBelegung() != null ){
			if(positionen[0][1][2].getBelegung().isRed() == isRedM && positionen[1][1][2].getBelegung().isRed() == isRedM && positionen[2][1][2].getBelegung().isRed() == isRedM){
				muehleID = 3;
				muehleList.add(muehleID);}
		}
		if(positionen[0][2][1].getBelegung() != null && positionen[1][2][1].getBelegung() != null && positionen[2][2][1].getBelegung() != null ){
			if(positionen[0][2][1].getBelegung().isRed() == isRedM && positionen[1][2][1].getBelegung().isRed() == isRedM && positionen[2][2][1].getBelegung().isRed() == isRedM){
				muehleID = 4; 
				muehleList.add(muehleID);}		
		}
		
		if(isRedM) redMuehleList = muehleList;
		else 		greenMuehleList = muehleList;
		
		for (int i = 0; i < muehleList.size(); i++) {
			if(!lastMuehleList.contains(muehleList.get(i))){
				System.out.println("MUEHLE!");
				return true;
			}
		}
		
		return false;
	}
	
	public boolean isFree(Position p){
		
		
		return false;
	}
	
}

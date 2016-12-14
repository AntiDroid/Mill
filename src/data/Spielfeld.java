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
	
	            			if(pos[i].getBelegung() != null && pos[i].isInRange(t.getX(), t.getY())){
	                			posSelStein = pos[i];
	            				break;
	            			}
	            		}
            		}
            		
            		//1.2 auswahl der pos
            		else{
            			for(Position p: movePos(posSelStein)){
            				System.out.println(p.getEbene()+","+p.getX()+","+p.getY());
            			}
            			posSelStein = null;
            		}
            		break;
            		
            	case 2:
            		
            		break;
            	
            	default:
            		System.out.println("Fehler");
            		
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
					
					// nicht in die mitte
					if(!(n.getX() == 1 && n.getY() == 1)){
						//Summe darf sich nur um eins ï¿½ndern
						if(Math.abs(pSum - nSum) == 1){
							// x oder y darf sich nicht mehr wie 1 ändern
							if(Math.abs(p.getX() - n.getX()) <= 1 && Math.abs(p.getY() - n.getY()) <= 1){
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
										// Darf sich nur die Ebene ï¿½ndern
										if(p.getX() == n.getX() && p.getY() == n.getY()){
											movePoss.add(n);
										}
									}
								}
							}
						}
					}
				}
			}
		}
		
		return movePoss;
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
		for(int ebene = 0; ebene < 3; ebene++){
			if(positionen[0][0][0].getBelegung().isRed() == isRedM && positionen[0][0][1].getBelegung().isRed() == isRedM && positionen[0][0][2].getBelegung().isRed() == isRedM){
				muehleID = 111+ebene;
				muehleList.add(muehleID);}			
			if(positionen[0][0][0].getBelegung().isRed() == isRedM && positionen[0][1][0].getBelegung().isRed() == isRedM && positionen[0][2][0].getBelegung().isRed() == isRedM){
				muehleID = 222+ebene;
				muehleList.add(muehleID);}			
			if(positionen[0][2][0].getBelegung().isRed() == isRedM && positionen[0][2][1].getBelegung().isRed() == isRedM && positionen[0][2][2].getBelegung().isRed() == isRedM){
				muehleID = 333+ebene;
				muehleList.add(muehleID);}				
			if(positionen[0][0][2].getBelegung().isRed() == isRedM && positionen[0][1][2].getBelegung().isRed() == isRedM && positionen[0][2][2].getBelegung().isRed() == isRedM){
				muehleID = 444+ebene;
				muehleList.add(muehleID);}		
		}
		// Muehlen auf dem Fadenkreuz
		if(positionen[0][1][0].getBelegung().isRed() == isRedM && positionen[1][1][0].getBelegung().isRed() == isRedM && positionen[2][1][0].getBelegung().isRed() == isRedM){
			muehleID = 1;
			muehleList.add(muehleID);}		
		if(positionen[0][0][1].getBelegung().isRed() == isRedM && positionen[1][0][1].getBelegung().isRed() == isRedM && positionen[2][0][1].getBelegung().isRed() == isRedM){
			muehleID = 2;
			muehleList.add(muehleID);}		
		if(positionen[0][1][2].getBelegung().isRed() == isRedM && positionen[1][1][2].getBelegung().isRed() == isRedM && positionen[2][1][2].getBelegung().isRed() == isRedM){
			muehleID = 3;
			muehleList.add(muehleID);}		
		if(positionen[0][2][1].getBelegung().isRed() == isRedM && positionen[1][2][1].getBelegung().isRed() == isRedM && positionen[2][2][1].getBelegung().isRed() == isRedM){
			muehleID = 4; 
			muehleList.add(muehleID);}		
		
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

package data;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.animation.TranslateTransition;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class Spielfeld implements Initializable {

	private int state;
	private Position posSelStein = null;
	private static ArrayList<Integer> redMuehleList = new ArrayList<Integer>();
	private static ArrayList<Integer> greenMuehleList = new ArrayList<Integer>();

	static Image imageF = new Image("file:res/img/mill.png");

	@FXML
	private Pane mainPane;
	@FXML
	private Label name1;
	@FXML
	private Label name2;
	@FXML
	private ImageView feld;
	@FXML
	private GridPane greenGridSel;
	@FXML
	private GridPane redGridSel;

	private boolean muehle = false;

	List<Stein> greenSel = new ArrayList<Stein>();
	List<Stein> redSel = new ArrayList<Stein>();

	private boolean isRedTurn = true;

	Position[] pos = { new Position(0, 0, 0, 44, 70.5), new Position(0, 0, 1, 44, 249), new Position(0, 0, 2, 44, 428),
			new Position(0, 1, 0, 222.5, 70.5), new Position(0, 2, 0, 401, 70.5), new Position(0, 2, 1, 401, 249),
			new Position(0, 2, 2, 401, 428), new Position(0, 1, 2, 222.5, 428), new Position(1, 0, 0, 98, 125),
			new Position(1, 1, 0, 222.5, 125), new Position(1, 2, 0, 346, 125), new Position(1, 0, 1, 98, 249),
			new Position(1, 0, 2, 98, 372), new Position(1, 1, 2, 222.5, 372), new Position(1, 2, 2, 346, 372),
			new Position(1, 2, 1, 346, 249), new Position(2, 0, 0, 153, 179.5), new Position(2, 0, 1, 153, 249),
			new Position(2, 0, 2, 153, 316), new Position(2, 1, 0, 222.5, 179.5), new Position(2, 2, 0, 291.5, 179.5),
			new Position(2, 2, 1, 291.5, 249), new Position(2, 2, 2, 291.5, 316), new Position(2, 1, 2, 222.5, 316) };

	@Override // This method is called by the FXMLLoader when initialization is complete
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {

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
        name2.setText("SPIELER 2");
        mainPane.setOnMouseClicked(new EventHandler<MouseEvent>(){

            @Override
            public void handle(MouseEvent t) {

            	//Defintion des jeweiligen Teams und seiner Daten
            	GridPane gridSel;
            	List<Stein> sel;

            	if(isRedTurn){
            		gridSel = redGridSel;
            		sel = redSel;
            	}
            	else{
            		gridSel = greenGridSel;
            		sel = greenSel;
            	}

            	//Statusdefinition
        		if (muehle)
        			state = 2;
            	else if(!gridSel.getChildren().isEmpty())
        			state = 0;
        		else
        			state = 1;
        		
        		if(sel.size() < 4)
        			state = 10;

        		//Abhandlung je nach Status
            	switch(state){

            	case 0:
            		anfangsphase(gridSel, sel, t);

            		muehle = isMuehle(!isRedTurn);

            		break;

            	case 1:
            		//1.1 auswahl des steins
            		if(posSelStein == null){
	            		for(int i = 0; i < pos.length; i++){

	            			//wenn das Feld belegt ist und der Klick auf das Feld war
	            			if(pos[i].getBelegung() != null && pos[i].getBelegung().isRed() == isRedTurn && pos[i].isInRange(t.getX(), t.getY())){

	            				posSelStein = pos[i];

	            				if(movePoss().size() == 0)
	            					posSelStein = null;
	            				else
	            					posSelStein.getBelegung().setOpacity(0.8);
	            				
	            				break;
	            			}
	            		}
            		}

            		//1.2 Verschieben auf ausgewählte Position
            		else{
            			
            			if(sel.size() < 5)
            				springen(t);
            			else
            				verschieben(t);
            			
            			for(Position p: pos){
            				if(p.getBelegung() != null){
            					p.getBelegung().setThree(false);
            				}
            			}
            			muehle = isMuehle(!isRedTurn);
            		}

            		break;

            	case 2:

	            	for(int i = 0; i < pos.length; i++){

	            		//wenn das Feld belegt ist und der Klick auf das Feld war
	            		if(pos[i].getBelegung() != null && pos[i].getBelegung().isRed() == isRedTurn
	            				&& pos[i].isInRange(t.getX(), t.getY())
	            				&& !pos[i].getBelegung().isThree()){

	            			sel.remove(pos[i].getBelegung());
	            			mainPane.getChildren().remove(pos[i].getBelegung());
	            			pos[i].setBelegung(null);
	            			muehle = false;

	            			break;
	            		}
	            	}

            		break;
            		
            	case 10:
            		
            		System.out.println("Spiel vorbei");
            		System.out.println(isRedTurn+" hat verloren!");
            	}

            	if(!muehle && posSelStein == null){
            		if(isRedTurn){
            			name1.setText("Spieler 1");
            			name2.setText("SPIELER 2");
            		}
            		else{
            			name1.setText("SPIELER 1");
            			name2.setText("Spieler 2");
            		}
            	}
            }

        });
    }

	public void verschieben(MouseEvent t) {

		outerloop: for (int i = 0; i < pos.length; i++) {

			// wenn das Feld ausgewählt wurde
			if (pos[i].isInRange(t.getX(), t.getY())) {
				for (Position p : movePoss()) {

					if (p.equals(pos[i])) {
						move(pos[i], true);

						break outerloop;
					}
				}
			}
		}
	}
	
	public void springen(MouseEvent t) {

		outerloop: for (int i = 0; i < pos.length; i++) {

			// wenn das Feld ausgewählt wurde
			if (pos[i].isInRange(t.getX(), t.getY())) {
					
					if (pos[i].getBelegung() == null) {
						move(pos[i], false);

						break outerloop;
					}	
			}
		}
	}

	public ArrayList<Position> movePoss() {

		ArrayList<Position> movePoss = new ArrayList<Position>();

		int curEbene = posSelStein.getEbene();
		int curX = posSelStein.getX();
		int curY = posSelStein.getY();

		for (int ebene = 0; ebene < 3; ebene++) {

			for (int x = 0; x < 3; x++) {

				for (int y = 0; y < 3; y++) {

					if (x == 1 && y == 1)
						continue;

					Position n = getPosition(ebene, x, y);

					// Wenn die zu prüfende Position NICHT belegt ist und KEINE
					// Mitte
					if (n.getBelegung() == null) {

						// Eckstein
						if ((curX + curY) % 2 == 0) {
							// Muss sich auf der gleichen Ebene Bewegen
							if (curEbene == n.getEbene()) {
								if (Math.abs(curX - x) == 1 && (curY - y) == 0)
									movePoss.add(n);
								else if (Math.abs(curY - y) == 1 && (curX - x) == 0)
									movePoss.add(n);
							}
						}
						// Mittelstein
						else {
							// Gleiche Ebene
							if (curEbene == n.getEbene()) {
								if (Math.abs(curX - x) == 1 && (curY - y) == 0)
									movePoss.add(n);
								else if (Math.abs(curY - y) == 1 && (curX - x) == 0)
									movePoss.add(n);
							}
							// Ebenenwechsel
							else {
								// Darf sich nur die Ebene �ndern
								if (curX == n.getX() && curY == n.getY() && Math.abs(curEbene - ebene) == 1)
									movePoss.add(n);
							}
						}
					}
				}
			}
		}

		return movePoss;
	}

	public void move(Position p, boolean animation) {

		int curX = (int) posSelStein.getBelegung().getLayoutX();
		int curY = (int) posSelStein.getBelegung().getLayoutY();

		int destX = (int) p.getKoordX();
		int destY = (int) p.getKoordY();
		
		if(animation){
		    TranslateTransition tt = new TranslateTransition(Duration.millis(300), posSelStein.getBelegung());
		    
		    if(curY == destY){
		    	//tt.setByX(destX - curX);
		    	tt.setToX(destX-curX);
		    }
		    else if(curX == destX){
		    	//tt.setByY(destY - curY);
		    	tt.setToY(destY-curY);
		    }
		   
		    System.out.println(tt.getFromX());
		    System.out.println(tt.getFromY());
		    tt.play();
		}
		else{
			posSelStein.getBelegung().setLayoutX(destX);
			posSelStein.getBelegung().setLayoutY(destY);
		}

		p.setBelegung(posSelStein.getBelegung());
		posSelStein.getBelegung().setOpacity(1);
		posSelStein.setBelegung(null);
		posSelStein = null;
		
		isRedTurn = !isRedTurn;
	}

	public void anfangsphase(GridPane gridSel, List<Stein> sel, MouseEvent t) {

		for (int i = 0; i < pos.length; i++) {
			if (pos[i].getBelegung() == null && pos[i].isInRange(t.getX(), t.getY())) {

				int index = gridSel.getChildren().size() - 1;

				pos[i].setBelegung(sel.get(index));

				gridSel.getChildren().remove(sel.get(index));
				mainPane.getChildren().add(sel.get(index));
				sel.get(index).setLayoutX(pos[i].getKoordX());
				sel.get(index).setLayoutY(pos[i].getKoordY());

				isRedTurn = !isRedTurn;
				break;

			}
		}
	}

	public boolean isMuehle(boolean isRedM) {

		int muehleID = 0;
		ArrayList<Integer> muehleList;
		if (isRedM)
			muehleList = redMuehleList;
		else
			muehleList = greenMuehleList;

		ArrayList<Integer> lastMuehleList = (ArrayList<Integer>) muehleList.clone();
		muehleList.clear();

		// Muehlen in gleicher Ebene
		for (int e = 0; e < 3; e++) {
			if(isThree(e, 0, 0, 0, 0, 1, isRedM)){
				muehleID = 10 + e;
				muehleList.add(muehleID);
			}
			if(isThree(e, 0, 0, 0, 1, 0, isRedM)){
				muehleID = 20 + e;
				muehleList.add(muehleID);
			}
			if(isThree(e, 2, 0, 0, 0, 1, isRedM)){
				muehleID = 30 + e;
				muehleList.add(muehleID);
			}
			if(isThree(e, 0, 2, 0, 1, 0, isRedM)){
				muehleID = 40 + e;
				muehleList.add(muehleID);
			}
		}
		// Muehlen auf dem Fadenkreuz
		if(isThree(0, 1, 0, 1, 0, 0, isRedM)){
			muehleID = 1;
			muehleList.add(muehleID);
		}
		if(isThree(0, 0, 1, 1, 0, 0, isRedM)){
			muehleID = 2;
			muehleList.add(muehleID);
		}
		if(isThree(0, 1, 2, 1, 0, 0, isRedM)){
			muehleID = 3;
			muehleList.add(muehleID);
		}
		if(isThree(0, 2, 1, 1, 0, 0, isRedM)){
			muehleID = 4;
			muehleList.add(muehleID);
		}

		if (isRedM)
			redMuehleList = muehleList;
		else
			greenMuehleList = muehleList;

		for (int i = 0; i < muehleList.size(); i++) {
			if (!lastMuehleList.contains(muehleList.get(i))) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Vereinfachung des isMuehle-Codes
	 * @param ebene Ebenenstart-Wert
	 * @param x X-Startwerte
	 * @param y Y- Startwert
	 * @param inkrEbene Schritterhoehung der Ebene
	 * @param inkrX Schritterhoehung der X
	 * @param inkrY Schritterhoehung der Y
	 * @param isRed Spieler
	 * @return ob ein Spieler hier eine dreier Reihe hat
	 */
	public boolean isThree(int ebene, int x, int y, int inkrEbene, int inkrX, int inkrY, boolean isRed){

		// ist es eine dreier Reihe - Schleife
		for(int i = 0; i < 3; i++){
			if(getPosition(ebene, x, y).getBelegung() == null){
				return false;
			}
			if(getPosition(ebene, x, y).getBelegung().isRed() != isRed){
				return false;
			}

			ebene += inkrEbene;
			x += inkrX;
			y += inkrY;
		}

		ebene -= inkrEbene;
		x -= inkrX;
		y -= inkrY;

		// dreier Reihe mit Three markieren
		for(int i = 0; i < 3; i++){

			getPosition(ebene, x, y).getBelegung().setThree(true);

			ebene -= inkrEbene;
			x -= inkrX;
			y -= inkrY;
		}

		return true;
	}

	public Position getPosition(int ebene, int x, int y) {

		for (Position p : pos) {
			if (p.getEbene() == ebene && p.getX() == x && p.getY() == y)
				return p;
		}

		return null;
	}

}

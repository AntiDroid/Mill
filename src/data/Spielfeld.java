package data;

import java.util.ArrayList;

public class Spielfeld {

	private Position[][][] positionen;
	private Position posSelStein = null;
	
	public Spielfeld(){
		
		positionen = new Position[3][3][3];
		
		for(int ebene = 0; ebene < 3; ebene++){
			for(int x = 0; x < 3; x++){
				for(int y = 0; y < 3; y++){
					positionen[ebene][x][y] = new Position(ebene, x, y, 5, 5);
				}
			}
		}
		for(int ebene = 0; ebene < 3; ebene++){
			for(int y = 0; y < 3; y++){
				for(int x = 0; x < 3; x++){
					System.out.print(positionen[ebene][x][y].toString()+"\t");
				}
				System.out.println();
			}
		}
	}
	
	
	public ArrayList<Position> movePoss(Position p){
		ArrayList<Position> movePoss = new ArrayList<Position>();
		int pSum = p.getEbene()+p.getX()+p.getY();
		
		for(int ebene = 0; ebene < 3; ebene++){
			for(int x = 0; x < 3; x++){
				for(int y = 0; y < 3; y++){
					Position n = positionen[ebene][x][y];
					int nSum = n.getEbene()+n.getX()+n.getY();
					
					//Summe darf sich nur um eins ändern
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
								// Darf sich nur die Ebene Ändern
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
	
}

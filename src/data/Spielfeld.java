package data;

public class Spielfeld {

	private Position[][][] positionen;
	
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
	
}

package Helper;

import GameObjects.Field;

public class consolePrinter {

	//Prints all Fields after everything is set
	public void printAllFields(int yDimension, int xDimension, Field[][] fields){
		for(int i = 0; i < yDimension; i++){
			for(int j = 0; j < xDimension; j++){
				switch(fields[j][i].getName()){
					case "GameObjects.Field":
						System.out.print("[]");
						break;
					case "GameObjects.Player":
						System.out.print("[P]");
						break;
					case "GameObjects.Target":
						System.out.print("[T]");
						break;
					case "GameObjects.Obstacle":
						System.out.println("[O]");
						break;
				}
			}
			System.out.println(" ");
		}
	}
}

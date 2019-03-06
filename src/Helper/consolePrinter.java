package Helper;

import GameObjects.Field;

public class consolePrinter {

	//Prints all Fields after everything is set
	public void printAllFields(int yDimension, int xDimension, Field[][] fields){
		for(int i = 0; i < yDimension; i++){
			for(int j = 0; j < xDimension; j++){
				switch(fields[j][i].getName()){
					case "GameObjects.Field":
						System.out.print("[Field]");
						break;
					case "GameObjects.Player":
						System.out.print("[Player]");
						break;
					case "GameObjects.Target":
						System.out.print("[Target]");
						break;
					case "GameObjects.Obstacle":
						System.out.println("[Obstacle]");
						break;
					case "GameObjects.Hole":
						System.out.println("[Hole]");
						break;
					case "GameObjects.Skull":
						System.out.println("[Skull]");
						break;
					case "GameObjects.Coin":
						System.out.println("[Coin]");
						break;
					case "GameObjects.Heart":
						System.out.println("[Heart]");
						break;
					case "GameObjects.Chest":
						System.out.println("[Chest]");
						break;
					case "GameObjects.FilledHole":
						System.out.println("[FilledHole]");
						break;
				}
			}
			System.out.println(" ");
		}
	}
}

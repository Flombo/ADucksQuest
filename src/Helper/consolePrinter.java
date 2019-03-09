package Helper;

import GameObjects.Field_like_Objects.Field;

public class consolePrinter {

	//Prints all Fields after everything is set
	public void printAllFields(int yDimension, int xDimension, Field[][] fields){
		for(int i = 0; i < yDimension; i++){
			for(int j = 0; j < xDimension; j++){
				switch(fields[j][i].getName()){
					case "GameObjects.Field_like_Objects.Field":
						System.out.print("[Field]");
						break;
					case "GameObjects.Player.Player":
						System.out.print("[Player]");
						break;
					case "GameObjects.Target.Target":
						System.out.print("[Target]");
						break;
					case "GameObjects.Obstacles.Obstacle":
						System.out.println("[Obstacle]");
						break;
					case "GameObjects.Obstacles.Hole":
						System.out.println("[Hole]");
						break;
					case "GameObjects.Enemies.Skull":
						System.out.println("[Skull]");
						break;
					case "GameObjects.Collectibles.Coin":
						System.out.println("[Coin]");
						break;
					case "GameObjects.Collectibles.Heart":
						System.out.println("[Heart]");
						break;
					case "GameObjects.Obstacles.Chest":
						System.out.println("[Chest]");
						break;
					case "GameObjects.Field_like_Objects.FilledHole":
						System.out.println("[FilledHole]");
						break;
				}
			}
			System.out.println(" ");
		}
	}
}

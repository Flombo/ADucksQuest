package GameLogic.Movement.MovementHelper;

import GameObjects.Field;
import GameObjects.Player;

public class playerCollisionChecker {

	private int newPosX;
	private int newPosY;
	private Field[][] fields;
	private Player player;

	public playerCollisionChecker(int newPosX, int newPosY, Field[][] fields, Player player){
		this.newPosX = newPosX;
		this.newPosY = newPosY;
		this.fields = fields;
		this.player = player;
	}

	//checks the next GameObject that would colide with player
	public boolean checkNextGameObject(){
		boolean canMove = false;
		switch (this.fields[this.newPosX][this.newPosY].getName()){
			case "GameObjects.Obstacle":
				break;
			case "GameObjects.Field":
				canMove = true;
				break;
			case  "GameObjects.Hole":
				this.player.setLives(-1);
				break;
			case "GameObjects.Skull":
				this.player.setLives(-1);
				break;
		}
		return canMove;
	}

}

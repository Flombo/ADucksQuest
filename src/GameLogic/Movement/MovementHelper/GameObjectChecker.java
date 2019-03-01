package GameLogic.Movement.MovementHelper;

import GameObjects.Field;
import GameObjects.Player;

public class GameObjectChecker {

	private int newPosX;
	private int newPosY;
	private int oldPosX;
	private int oldPosY;
	private Field[][] fields;
	private Player player;

	public GameObjectChecker(int oldPosX, int oldPosY, int newPosX, int newPosY, Field[][] fields, Player player){
		this.oldPosX = oldPosX;
		this.oldPosY = oldPosY;
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
				this.fields[this.oldPosX][this.oldPosY] =
						new Field(this.oldPosX * 30, this.oldPosY * 30, "GameObjects.Field");
				break;
			case  "GameObjects.Hole":
				this.player.setLives(-1);
				break;
		}
		return canMove;
	}

}

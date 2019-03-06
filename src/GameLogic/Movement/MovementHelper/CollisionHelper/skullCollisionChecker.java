package GameLogic.Movement.MovementHelper.CollisionHelper;

import GameObjects.Field;
import GameObjects.Player;
import GameObjects.Skull;
import Rendering.View;

public class skullCollisionChecker  {

	private Skull skull;
	private int newPosX;
	private int newPosY;
	private Field[][] fields;
	private Player player;
	private View view;

	public skullCollisionChecker(int newPosX, int newPosY, Field[][] fields, Skull skull, Player player, View view){
		this.fields = fields;
		this.newPosX = newPosX;
		this.newPosY = newPosY;
		this.skull = skull;
		this.player = player;
		this.view = view;
	}

	//checks the next GameObject that would collide with player
	public boolean checkNextGameObject(){
		boolean canMove = false;
		switch (this.fields[this.newPosX][this.newPosY].getName()){
			case "GameObjects.Obstacle":
				skull.changePosition();
				break;
			case "GameObjects.Field":
				canMove = true;
				break;
			case "GameObjects.Hole":
				skull.changePosition();
				break;
			case "GameObjects.Player":
				this.player.setLives(-1);
				this.player.attacked(this.view);
				skull.changePosition();
				break;
			case "GameObjects.Skull":
				skull.changePosition();
				break;
			case "GameObjects.Target":
				skull.changePosition();
				break;
			case "GameObjects.Coin":
				skull.changePosition();
				break;
			case "GameObjects.Heart":
				skull.changePosition();
				break;
			case "GameObjects.Chest":
				skull.changePosition();
				break;
			case "GameObjects.FilledHole":
				canMove = true;
				break;
		}
		return canMove;
	}

}

package GameLogic.Movement.MovementHelper.CollisionHelper;

import GameObjects.Field;
import GameObjects.Player;
import Rendering.View;

public class playerCollisionChecker {

	private int newPosX;
	private int newPosY;
	private Field[][] fields;
	private Player player;
	private View view;

	public playerCollisionChecker(int newPosX, int newPosY, Field[][] fields, Player player, View view){
		this.newPosX = newPosX;
		this.newPosY = newPosY;
		this.fields = fields;
		this.player = player;
		this.view = view;
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
				this.player.attacked(view);
				break;
			case "GameObjects.Skull":
				this.player.setLives(-1);
				this.player.attacked(view);
				break;
			case "GameObjects.Coin":
				this.player.setCoins(1);
				canMove = true;
				break;
		}
		return canMove;
	}

}

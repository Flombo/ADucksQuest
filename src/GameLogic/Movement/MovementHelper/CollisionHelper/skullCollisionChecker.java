package GameLogic.Movement.MovementHelper.CollisionHelper;

import GameObjects.Field_like_Objects.Field;
import GameObjects.Player.Player;
import GameObjects.Enemies.Skull;
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
			case "GameObjects.Obstacles.Obstacle":
				skull.changePosition();
				break;
			case "GameObjects.Field_like_Objects.Field":
				canMove = true;
				break;
			case "GameObjects.Obstacles.Hole":
				skull.changePosition();
				break;
			case "GameObjects.Player.Player":
				this.skull.attack();
				this.player.setLives(-1);
				this.player.attacked(this.view);
				skull.changePosition();
				break;
			case "GameObjects.Enemies.Skull":
				skull.changePosition();
				break;
			case "GameObjects.Target.Target":
				skull.changePosition();
				break;
			case "GameObjects.Collectibles.Coin":
				skull.changePosition();
				break;
			case "GameObjects.Collectibles.Heart":
				skull.changePosition();
				break;
			case "GameObjects.Obstacles.Chest":
				skull.changePosition();
				break;
			case "GameObjects.Field_like_Objects.FilledHole":
				canMove = true;
				break;
			case "GameObjects.Enemies.Zombie":
				skull.changePosition();
				break;
		}
		return canMove;
	}

}

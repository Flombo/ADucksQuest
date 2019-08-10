package GameLogic.Movement.MovementHelper.CollisionHelper;

import GameObjects.Enemies.Zombie;
import GameObjects.Field_like_Objects.Field;
import GameObjects.Player.Player;
import Rendering.View;

public class zombieCollisionChecker {

	private Zombie zombie;
	private int newPosX;
	private int newPosY;
	private Field[][] fields;
	private Player player;
	private View view;

	public zombieCollisionChecker(int newPosX, int newPosY, Field[][] fields, Zombie zombie, Player player, View view){
		this.fields = fields;
		this.newPosX = newPosX;
		this.newPosY = newPosY;
		this.zombie = zombie;
		this.player = player;
		this.view = view;
	}

	//checks the next GameObject that would collide with player
	public boolean checkNextGameObject(){
		boolean canMove = false;
		switch (this.fields[this.newPosX][this.newPosY].getName()){
			case "GameObjects.Obstacles.Obstacle":
				zombie.changePostion();
				break;
			case "GameObjects.Field_like_Objects.Field":
				canMove = true;
				break;
			case "GameObjects.Obstacles.Hole":
				zombie.changePostion();
				break;
			case "GameObjects.Player.Player":
				this.zombie.attack();
				zombie.changePostion();
				int livesTaken = Integer.parseInt(this.player.getLives().getValue()) >= 2 ? -2 : -1;
				this.view.setPlayerLives(this.player, livesTaken);
				this.player.attacked(this.view);
				break;
			case "GameObjects.Enemies.Skull":
				zombie.changePostion();
				break;
			case "GameObjects.Target.Target":
				zombie.changePostion();
				break;
			case "GameObjects.Collectibles.Coin":
				zombie.changePostion();
				break;
			case "GameObjects.Collectibles.Heart":
				zombie.changePostion();
				break;
			case "GameObjects.Obstacles.Chest":
				zombie.changePostion();
				break;
			case "GameObjects.Field_like_Objects.FilledHole":
				canMove = true;
				break;
			case "GameObjects.Enemies.Zombie":
				zombie.changePostion();
				break;
		}
		return canMove;
	}

}

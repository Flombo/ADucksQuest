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
			case "GameObjects.Obstacles.Hole":
			case "GameObjects.Enemies.Skull":
			case "GameObjects.Target.Target":
			case "GameObjects.Collectibles.Coin":
			case "GameObjects.Collectibles.Heart":
			case "GameObjects.Obstacles.Chest":
			case "GameObjects.Enemies.Zombie":
				zombie.changePostion();
				break;
			case "GameObjects.Field_like_Objects.Field":
			case "GameObjects.Field_like_Objects.FilledHole":
				canMove = true;
				break;
			case "GameObjects.Player.Player":
				this.zombie.attack();
				zombie.changePostion();
				int livesTaken = Integer.parseInt(this.player.getLives().getValue()) >= 2 ? -2 : -1;
				this.player.attacked(this.view);
				this.view.setPlayerLives(this.player, livesTaken);
				break;
		}
		return canMove;
	}

}

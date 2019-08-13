package GameLogic.Movement.MovementHelper.CollisionHelper;

import GameObjects.Obstacles.Chest;
import GameObjects.Field_like_Objects.Field;
import GameObjects.Obstacles.Hole;
import GameObjects.Player.Player;
import Rendering.View;

public class playerCollisionChecker {

	private int newPosX;
	private int newPosY;
	private Field[][] fields;
	private Player player;
	private View view;
	private chestCollisionChecker chestCollisionChecker;
	private int newPos;

	public playerCollisionChecker(
			int newPosX,
			int newPosY,
			Field[][] fields,
			Player player,
			View view,
			boolean upDown,
			int xDimension,
			int yDimension,
			int newPos
	){
		this.newPosX = newPosX;
		this.newPosY = newPosY;
		this.fields = fields;
		this.player = player;
		this.view = view;
		this.newPos = newPos;
		this.chestCollisionChecker = new chestCollisionChecker(this.fields, upDown,xDimension, yDimension);
	}

	//checks the next GameObject that would colide with player
	public boolean checkNextGameObject(){
		boolean canMove = false;
		switch (this.fields[this.newPosX][this.newPosY].getName()){
			case "GameObjects.Obstacles.Obstacle":
				break;
			case "GameObjects.Field_like_Objects.Field":
				canMove = true;
				break;
			case  "GameObjects.Obstacles.Hole":
				this.player.setAllowedToMove(false);
				this.player.setCurrentImage(this.player.getFieldImage());
				((Hole)this.fields[this.newPosX][this.newPosY]).animatePlayerFall();
				this.view.setPlayerLives(this.player, -1);
				this.player.attacked(view);
				this.player.setAllowedToMove(true);
				break;
			case "GameObjects.Enemies.Skull":
				this.player.setAllowedToMove(false);
				this.view.setPlayerLives(this.player, -1);
				this.player.attacked(view);
				this.player.setAllowedToMove(true);
				break;
			case "GameObjects.Collectibles.Coin":
				this.player.setCoins(1);
				this.player.itemPicked();
				canMove = true;
				break;
			case "GameObjects.Collectibles.Heart":
				this.player.setAllowedToMove(false);
				this.view.setPlayerLives(this.player, 1);
				this.player.itemPicked();
				this.player.setAllowedToMove(true);
				canMove = true;
				break;
			case "GameObjects.Target.Target":
				canMove = true;
				this.view.showSuccessMenu();
				break;
			case "GameObjects.Obstacles.Chest":
				canMove = this.chestCollisionChecker.checkNextPos((Chest) this.fields[this.newPosX][this.newPosY], this.newPos);
				break;
			case "GameObjects.Field_like_Objects.FilledHole":
				canMove = true;
				break;
			case "GameObjects.Enemies.Zombie":
				this.player.setAllowedToMove(false);
				this.view.setPlayerLives(this.player, -2);
				this.player.attacked(this.view);
				this.player.setAllowedToMove(true);
				break;
		}
		return canMove;
	}

}

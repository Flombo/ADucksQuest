package GameLogic.Movement.MovementHelper.CollisionHelper;

import GameObjects.Chest;
import GameObjects.Field;
import GameObjects.Hole;
import GameObjects.Player;
import Rendering.View;

import java.awt.event.WindowEvent;

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
			case "GameObjects.Obstacle":
				break;
			case "GameObjects.Field":
				canMove = true;
				break;
			case  "GameObjects.Hole":
				this.player.setCurrentImage(this.player.getFieldImage());
				((Hole)this.fields[this.newPosX][this.newPosY]).animatePlayerFall();
				this.player.setLives(-1);
				this.player.attacked(view);
				break;
			case "GameObjects.Skull":
				this.player.setLives(-1);
				this.player.attacked(view);
				break;
			case "GameObjects.Coin":
				this.player.setCoins(1);
				this.player.itemPicked();
				canMove = true;
				break;
			case "GameObjects.Heart":
				this.player.setLives(1);
				this.player.itemPicked();
				canMove = true;
				break;
			case "GameObjects.Target":
				canMove = true;
				this.view.setDialog("You won ^^ your score:" + this.player.getScore() + " your Moves :" + this.player.getMoves());
				this.view.dispatchEvent(new WindowEvent(this.view, WindowEvent.WINDOW_CLOSING));
				break;
			case "GameObjects.Chest":
				canMove = this.chestCollisionChecker.checkNextPos((Chest) this.fields[this.newPosX][this.newPosY], this.newPos);
				break;
			case "GameObjects.FilledHole":
				canMove = true;
				break;
		}
		return canMove;
	}

}
package GameLogic.Movement.MovementHelper.CollisionHelper;

import GameObjects.Field_like_Objects.Field;
import GameObjects.Player.Player;
import GameObjects.Enemies.Skull;
import Rendering.View;

public class skullCollisionChecker  {

	private Skull skull;
	private int newPosX;
	private Field[][] fields;
	private Player player;
	private View view;

	public skullCollisionChecker(int newPosX, Field[][] fields, Skull skull, Player player, View view){
		this.fields = fields;
		this.newPosX = newPosX;
		this.skull = skull;
		this.player = player;
		this.view = view;
	}

	//checks the next GameObject that would collide with player
	public boolean checkNextGameObject(){
		boolean canMove = false;
		switch (this.fields[this.newPosX][this.skull.getYPos()].getName()){
			case "GameObjects.Obstacles.Obstacle":
			case "GameObjects.Obstacles.Hole":
			case "GameObjects.Enemies.Skull":
			case "GameObjects.Target.Target":
			case "GameObjects.Collectibles.Coin":
			case "GameObjects.Collectibles.Heart":
			case "GameObjects.Obstacles.Chest":
			case "GameObjects.Enemies.Zombie":
				skull.changePosition();
				break;
			case "GameObjects.Field_like_Objects.Field":
			case "GameObjects.Field_like_Objects.FilledHole":
				canMove = true;
				break;
			case "GameObjects.Player.Player":
				this.skull.attack();
				this.player.attacked(this.view);
				this.view.setPlayerLives(this.player, -1);
				skull.changePosition();
				break;
		}
		return canMove;
	}

	public void setNewPosX(int newPosX){
		this.newPosX = newPosX;
	}

	public void setSkull(Skull skull) {
		this.skull = skull;
	}

	public void setFields(Field[][] fields) {
		this.fields = fields;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public void setView(View view) {
		this.view = view;
	}

}

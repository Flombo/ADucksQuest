package GameLogic.Movement.MovementHelper;

import GameObjects.Field;
import GameObjects.Player;
import GameObjects.Skull;

public class skullGameObjectChecker {

	private Skull skull;
	private int newPosX;
	private int newPosY;
	private Field[][] fields;
	private Player player;

	public skullGameObjectChecker(int newPosX, int newPosY, Field[][] fields, Skull skull, Player player){
		this.fields = fields;
		this.newPosX = newPosX;
		this.newPosY = newPosY;
		this.skull = skull;
		this.player = player;
	}

	//checks the next GameObject that would collide with player
	public boolean checkNextGameObject(){
		boolean canMove = false;
		System.out.println(this.fields[this.newPosX][this.newPosY].getName());
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
				this.player.attacked();
				skull.changePosition();
				break;
			case "GameObjects.Skull":
				skull.changePosition();
				break;
		}
		System.out.println(canMove);
		return canMove;
	}

}

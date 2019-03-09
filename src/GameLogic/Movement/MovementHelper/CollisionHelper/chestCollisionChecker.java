package GameLogic.Movement.MovementHelper.CollisionHelper;

import GameObjects.Obstacles.Chest;
import GameObjects.Field_like_Objects.Field;
import GameObjects.Field_like_Objects.FilledHole;

class chestCollisionChecker {

	private Field[][] fields;
	private boolean upDown;
	private int xDimension;
	private int yDimension;

	chestCollisionChecker(
			Field[][] fields,
			boolean upDown,
			int xDimension,
			int yDimension
	){
		this.fields = fields;
		this.upDown = upDown;
		this.xDimension = xDimension;
		this.yDimension = yDimension;
	}

	//checks if newPos is between the gamebounderies
	private boolean checkRightLeft(Chest chest, int newPosX){
		boolean canMove = false;
		int newPos = newPosX + chest.getXPos();
		if(newPos < xDimension && newPos >= 0){
			canMove = this.checkNextGameObject(chest, newPos, false);
		}
		return canMove;
	}

	//checks if newPos is between the gamebounderies
	private boolean checkUpDown(Chest chest, int newPosY){
		boolean canMove = false;
		int newPos = newPosY + chest.getYPos();
		if(newPos < yDimension && newPos >= 0){
			canMove = this.checkNextGameObject(chest, newPos, true);
		}
		return canMove;
	}

	//checks if nextPos is up/down or left/right
	boolean checkNextPos(Chest chest, int newPos){
		boolean canMove;
		if(this.upDown){
			canMove = this.checkUpDown(chest, newPos);
		} else {
			canMove = this.checkRightLeft(chest, newPos);
		}
		return  canMove;
	}

	//checks the next GameObject that would colide with Chest
	private boolean checkNextGameObject(Chest chest, int newPos, boolean upDown){
		boolean canMove = false;
		int newPosX = chest.getXPos();
		int newPosY = chest.getYPos();
		if(upDown){
			newPosY = newPos;
		} else {
			newPosX = newPos;
		}
		switch (this.fields[newPosX][newPosY].getName()){
			case "GameObjects.Obstacles.Obstacle":
				break;
			case "GameObjects.Field_like_Objects.Field":
				canMove = true;
				this.moveChest(chest, newPosX, newPosY);
				break;
			case  "GameObjects.Obstacles.Hole":
				canMove = true;
				this.fillHole(chest, newPosX, newPosY);
				break;
			case "GameObjects.Enemies.Skull":
				break;
			case "GameObjects.Collectibles.Coin":
				break;
			case "GameObjects.Collectibles.Heart":
				break;
			case "GameObjects.Target.Target":
				break;
		}
		return canMove;
	}

	//changes hole into filledhole
	private void fillHole(Chest chest, int newPosX, int newPosY){
		this.fields[chest.getXPos()][chest.getYPos()] = new Field(chest.getX(), chest.getY(), "GameObjects.Field_like_Objects.Field");
		FilledHole filledHole = new FilledHole();
		filledHole.setY(newPosY * 30);
		filledHole.setX(newPosX * 30);
		this.fields[newPosX][newPosY] = filledHole;
	}

	//moves chest
	private void moveChest(Chest chest, int newPosX, int newPosY){
		this.fields[chest.getXPos()][chest.getYPos()] = new Field(chest.getX(), chest.getY(), "GameObjects.Field_like_Objects.Field");
		chest.setX(newPosX * 30);
		chest.setY(newPosY * 30);
		this.fields[chest.getXPos()][chest.getYPos()] = chest;
	}

}

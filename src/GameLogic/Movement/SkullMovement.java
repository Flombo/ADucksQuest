package GameLogic.Movement;

import GameLogic.Movement.MovementHelper.CollisionHelper.skullCollisionChecker;
import GameObjects.Field_like_Objects.Field;
import GameObjects.GameObjectEnums.SkullPosition;
import GameObjects.Player.Player;
import GameObjects.Enemies.Skull;
import Rendering.View;

public class SkullMovement implements Runnable{

	private View view;
	private Field[][] fields;
	private Thread thread;
	private boolean isRunning = false;
	private Skull skull;
	private int xDimension;
	private Player player;

	public SkullMovement(Field[][] fields, Skull skull, int xDimension, Player player, View view){
		this.fields = fields;
		this.skull = skull;
		this.xDimension = xDimension;
		this.player = player;
		this.view = view;
	}

	public void initMovement(){
		this.start();
	}

	// changes skulls position
	private void changeSkullPos(int newPos){
		this.skull.setX((this.skull.getXPos() + newPos) * 30);
		this.skull.setY(this.skull.getYPos() * 30);
		this.fields[this.skull.getXPos()][this.skull.getYPos()] = this.skull;
	}

	// checks if next field is an obstacle
	private void checkNextField(int newPos){
		skullCollisionChecker skullCollisionChecker = new skullCollisionChecker(
				this.skull.getXPos() + newPos,
				this.skull.getYPos(),
				this.fields,
				this.skull,
				this.player,
				this.view
		);
		if(skullCollisionChecker.checkNextGameObject()) {
			this.fields[this.skull.getXPos()][this.skull.getYPos()] = new Field(this.skull.getXPos() * 30, this.skull.getYPos() * 30, "GameObjects.Field_like_Objects.Field");
			this.changeSkullPos(newPos);
			this.skull.walk();
		}
	}

	// moves Skull in y-direction
	private void moveSkullLeft(){
		if (this.skull.getXPos() - 1 >= 0){
			this.checkNextField(-1);
		} else {
			this.skull.changePosition();
		}
	}

	// moves Skull in x-direction
	private void moveSkullRight(){
		if (this.skull.getXPos() + 1 < xDimension) {
			this.checkNextField(1);
		} else {
			this.skull.changePosition();
		}
	}

	//calls vor every Skull the moveSkull method
	private void controllSkulls(){
		if (this.skull.getPosition().equals(SkullPosition.SKULL_RIGHT)) {
			this.moveSkullRight();
		} else {
			this.moveSkullLeft();
		}
	}

	//start Thread
	private synchronized void start(){
		isRunning = true;
		thread = new Thread(this, "SkullMovement");
		thread.start();
	}

	//stop Thread
	private synchronized void stop(){
		isRunning = false;
		try{
			thread.join();
		} catch (InterruptedException e){
			e.printStackTrace();
		}
	}

	//calls the movement method every second
	@Override
	public void run() {
		long timer = System.currentTimeMillis();
		while(isRunning) {
			if (System.currentTimeMillis() - timer >= 1000 / 60) {
				timer += 4000 / 60;
				try {
					Thread.sleep(4500 / 60);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				this.controllSkulls();
			}
		}
		this.stop();
	}

}

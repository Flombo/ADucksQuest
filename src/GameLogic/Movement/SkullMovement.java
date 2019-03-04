package GameLogic.Movement;

import GameLogic.Movement.MovementHelper.CollisionHelper.skullCollisionChecker;
import GameObjects.Field;
import GameObjects.GameObjectEnums.SkullPosition;
import GameObjects.Player;
import GameObjects.Skull;
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

	private int getSkullX(Skull skull){
		return skull.getX() / 30;
	}

	private int getSkullY(Skull skull){
		return skull.getY() / 30;
	}

	public void initMovement(){
		this.start();
	}

	private void moveSkulls(Skull skull, int newPos){
		if(newPos == 1) {
			this.moveSkullRight(skull, newPos);
		} else {
			this.moveSkullLeft(skull, newPos);
		}
	}

	// changes skulls position
	private void changeSkullPos(Skull skull, int newPos){
		skull.setX((this.getSkullX(skull) + newPos) * 30);
		skull.setY(this.getSkullY(skull) * 30);
		this.fields[this.getSkullX(skull)][this.getSkullY(skull)] = skull;
	}

	// checks if next field is an obstacle
	private void checkNextField(Skull skull, int newPos){
		skullCollisionChecker skullCollisionChecker = new skullCollisionChecker(
				this.getSkullX(skull) + newPos,
				this.getSkullY(skull),
				this.fields,
				skull,
				this.player,
				this.view
		);
		if(skullCollisionChecker.checkNextGameObject()) {
			this.fields[this.getSkullX(skull)][this.getSkullY(skull)] = new Field(this.getSkullX(skull) * 30, this.getSkullY(skull) * 30, "GameObjects.Field");
			this.changeSkullPos(skull, newPos);
			skull.walk();
		}
	}

	// moves Skull in y-direction
	private void moveSkullLeft(Skull skull, int newPos){
		if (this.getSkullX(skull) + newPos >= 0){
			this.checkNextField(skull, newPos);
		} else {
			skull.changePosition();
		}
	}

	// moves Skull in x-direction
	private void moveSkullRight(Skull skull, int newPos){
		if (this.getSkullX(skull) + newPos < xDimension) {
			this.checkNextField(skull, newPos);
		} else {
			skull.changePosition();
		}
	}

	//calls vor every Skull the moveSkull method
	private void controllSkulls(){
		if (this.skull.getPosition().equals(SkullPosition.SKULL_RIGHT)) {
			this.moveSkulls(this.skull, 1);
		} else {
			this.moveSkulls(this.skull, -1);
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

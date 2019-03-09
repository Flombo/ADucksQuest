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

	private void moveSkulls(Skull skull, int newPos){
		if(newPos == 1) {
			this.moveSkullRight(skull, newPos);
		} else {
			this.moveSkullLeft(skull, newPos);
		}
	}

	// changes skulls position
	private void changeSkullPos(Skull skull, int newPos){
		skull.setX((skull.getXPos() + newPos) * 30);
		skull.setY(skull.getYPos() * 30);
		this.fields[skull.getXPos()][skull.getYPos()] = skull;
	}

	// checks if next field is an obstacle
	private void checkNextField(Skull skull, int newPos){
		skullCollisionChecker skullCollisionChecker = new skullCollisionChecker(
				skull.getXPos() + newPos,
				skull.getYPos(),
				this.fields,
				skull,
				this.player,
				this.view
		);
		if(skullCollisionChecker.checkNextGameObject()) {
			this.fields[skull.getXPos()][skull.getYPos()] = new Field(skull.getXPos() * 30, skull.getYPos() * 30, "GameObjects.Field_like_Objects.Field");
			this.changeSkullPos(skull, newPos);
			skull.walk();
		}
	}

	// moves Skull in y-direction
	private void moveSkullLeft(Skull skull, int newPos){
		if (skull.getXPos() + newPos >= 0){
			this.checkNextField(skull, newPos);
		} else {
			skull.changePosition();
		}
	}

	// moves Skull in x-direction
	private void moveSkullRight(Skull skull, int newPos){
		if (skull.getXPos() + newPos < xDimension) {
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

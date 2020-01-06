package GameLogic.Movement;

import GameLogic.Movement.MovementHelper.CollisionHelper.skullCollisionChecker;
import GameLogic.ThreadHelper.ThreadWaitManager;
import GameObjects.Field_like_Objects.Field;
import GameObjects.GameObjectEnums.PositionEnums.SkullPosition;
import GameObjects.Player.Player;
import GameObjects.Enemies.Skull;
import Rendering.View;
import javafx.application.Platform;

public class SkullMovement implements Runnable{

	private View view;
	private Field[][] fields;
	private Thread thread;
	private boolean isRunning = false;
	private ThreadWaitManager threadWaitManager;
	private Skull skull;
	private int xDimension;
	private Player player;
	private skullCollisionChecker skullCollisionChecker;

	public SkullMovement(Field[][] fields, Skull skull, int xDimension, Player player, View view){
		this.fields = fields;
		this.skull = skull;
		this.xDimension = xDimension;
		this.player = player;
		this.view = view;
		this.threadWaitManager = new ThreadWaitManager();
	}

	public synchronized void setIsRunning(boolean isRunning){
		this.isRunning = isRunning;
	}

	public void initMovement(){
		Platform.runLater(this.start());
	}

	// changes skulls position
	private void changeSkullPos(int newPos){
		this.skull.setX((this.skull.getXPos() + newPos) * 30);
		this.skull.setY(this.skull.getY());
		this.fields[this.skull.getXPos()][this.skull.getYPos()] = this.skull;
	}

	// checks if next field is an obstacle
	private void checkNextField(int newPos){
		this.initCollisionChecker(newPos);
		if(skullCollisionChecker.checkNextGameObject()) {
			this.fields[this.skull.getXPos()][this.skull.getYPos()] = new Field("GameObjects.Field_like_Objects.Field");
			this.fields[this.skull.getXPos()][this.skull.getYPos()].setY(this.skull.getYPos() * 30);
			this.fields[this.skull.getXPos()][this.skull.getYPos()].setX(this.skull.getXPos() * 30);
			this.changeSkullPos(newPos);
			this.skull.walk();
		}
	}

	private void initCollisionChecker(int newPos){
		if(this.skullCollisionChecker == null) {
			this.skullCollisionChecker = new skullCollisionChecker(
					this.skull.getXPos() + newPos,
					this.fields,
					this.skull,
					this.player,
					this.view
			);
		} else {
			this.skullCollisionChecker.setNewPosX(this.skull.getXPos() + newPos);
			this.skullCollisionChecker.setPlayer(this.player);
			this.skullCollisionChecker.setFields(this.fields);
			this.skullCollisionChecker.setView(this.view);
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
	private synchronized Runnable start(){
		return (()-> {
			isRunning = true;
			thread = new Thread(this, "SkullMovement");
			thread.setDaemon(true);
			thread.start();
		});
	}

	//stop Thread
	private synchronized void stop(){
		isRunning = false;
		this.thread.interrupt();
	}

	//calls the movement method every second
	@Override
	public void run() {
		long timer = System.currentTimeMillis();
		while(isRunning) {
			if (System.currentTimeMillis() - timer >= 7000 / 60) {
				timer += 4000 / 60;
				this.threadWaitManager.pauseThread(7000 / 60);
				this.controllSkulls();
			}
		}
		this.stop();
	}

}

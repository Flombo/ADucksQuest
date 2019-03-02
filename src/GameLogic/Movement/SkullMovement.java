package GameLogic.Movement;

import GameLogic.Movement.MovementHelper.skullGameObjectChecker;
import GameObjects.Field;
import GameObjects.GameObjectEnums.SkullPosition;
import GameObjects.Player;
import GameObjects.Skull;
import Helper.consolePrinter;

public class SkullMovement implements Runnable{

	private Field[][] fields;
	private Thread thread;
	private boolean isRunning = false;
	private Skull[] skulls;
	private int xDimension;
	private Player player;
	private Helper.consolePrinter consolePrinter;

	public SkullMovement(Field[][] fields, Skull[] skulls, int xDimension, Player player){
		this.fields = fields;
		this.skulls = skulls;
		this.xDimension = xDimension;
		this.consolePrinter = new consolePrinter();
		this.player = player;
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
		GameLogic.Movement.MovementHelper.skullGameObjectChecker skullGameObjectChecker = new skullGameObjectChecker(this.getSkullX(skull) + newPos, this.getSkullY(skull), this.fields, skull, this.player);
		if(skullGameObjectChecker.checkNextGameObject()) {
			this.fields[this.getSkullX(skull)][this.getSkullY(skull)] = new Field(this.getSkullX(skull) * 30, this.getSkullY(skull) * 30, "GameObjects.Field");
			this.changeSkullPos(skull, newPos);
		}
	}

	// moves Skull in y-direction
	private void moveSkullLeft(Skull skull, int newPos){
		if (this.getSkullX(skull) + newPos >= 0){
			this.checkNextField(skull, newPos);
		} else {
			skull.changePosition();
		}
//		this.consolePrinter.printAllFields(this.yDimension, this.xDimension, this.fields);
	}

	// moves Skull in x-direction
	private void moveSkullRight(Skull skull, int newPos){
		if (this.getSkullX(skull) + newPos < xDimension) {
			System.out.println("Checking");
			this.checkNextField(skull, newPos);
		} else {
			skull.changePosition();
		}
//		this.consolePrinter.printAllFields(this.yDimension, this.xDimension, this.fields);
	}

	//calls vor every Skull the moveSkull method
	private void controllSkulls(){
		for (Skull skull : this.skulls) {
			if (skull.getPosition().equals(SkullPosition.SKULL_RIGHT)) {
				this.moveSkulls(skull, 1);
			} else {
				this.moveSkulls(skull, -1);
			}
		}
	}

	//start Thread
	private synchronized void start(){
		isRunning = true;
		thread = new Thread("SkullMovement");
		thread.start();
		this.run();
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
			if (System.currentTimeMillis() - timer >= 1000) {
				timer += 500;
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				this.controllSkulls();
			}
		}
		this.stop();
	}

}

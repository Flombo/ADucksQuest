package GameLogic.Movement;

import GameLogic.Movement.MovementHelper.CollisionHelper.zombieCollisionChecker;
import GameLogic.ThreadHelper.ThreadWaitManager;
import GameObjects.Enemies.Zombie;
import GameObjects.Field_like_Objects.Field;
import GameObjects.GameObjectEnums.PositionEnums.ZombiePostion;
import GameObjects.Player.Player;
import Rendering.View;
import javafx.application.Platform;

public class ZombieMovement implements Runnable{

		private View view;
		private Field[][] fields;
		private Thread thread;
		private boolean isRunning = false;
		private Zombie zombie;
		private int xDimension;
		private int yDimension;
		private Player player;
		private ThreadWaitManager threadWaitManager;
		private zombieCollisionChecker zombieCollisionChecker;

		public ZombieMovement(Field[][] fields, Zombie zombie, int xDimension, int yDimension, Player player, View view){
			this.fields = fields;
			this.zombie = zombie;
			this.xDimension = xDimension;
			this.yDimension = yDimension;
			this.player = player;
			this.view = view;
			this.threadWaitManager = new ThreadWaitManager();
		}

		public void setIsRunning(boolean isRunning){
			this.isRunning = isRunning;
		}

		public void initMovement(){
			Platform.runLater(this.start());
		}

		// changes skulls position
		private synchronized void changeZombiePos(int newPos){
			if(this.zombie.getPostion().equals(ZombiePostion.Zombie_Right) || this.zombie.getPostion().equals(ZombiePostion.Zombie_Left)) {
				this.zombie.setX((this.zombie.getXPos() + newPos) * 30);
				this.zombie.setY(this.zombie.getYPos() * 30);
				this.fields[this.zombie.getXPos()][this.zombie.getYPos()] = this.zombie;
			} else {
				this.zombie.setX(this.zombie.getXPos() * 30);
				this.zombie.setY((this.zombie.getYPos() + newPos) * 30);
				this.fields[this.zombie.getXPos()][this.zombie.getYPos()] = this.zombie;
			}
		}

		private synchronized void initZombieCollision(int newPos){
			switch (this.zombie.getPostion()) {
				case Zombie_Right:
				case Zombie_Left:
					zombieCollisionChecker = new zombieCollisionChecker();
					this.setZombieCollisionCheckerParameters(this.zombie.getXPos() + newPos, this.zombie.getYPos());
					break;
				case Zombie_Down:
				case Zombie_Up:
					zombieCollisionChecker = new zombieCollisionChecker();
					this.setZombieCollisionCheckerParameters(this.zombie.getXPos(), this.zombie.getYPos() + newPos);
					break;
			}
		}

		private synchronized void setZombieCollisionCheckerParameters(int x, int y){
			zombieCollisionChecker.setPlayer(this.player);
			zombieCollisionChecker.setFields(this.fields);
			zombieCollisionChecker.setView(this.view);
			zombieCollisionChecker.setZombie(this.zombie);
			zombieCollisionChecker.setNewPosX(x);
			zombieCollisionChecker.setNewPosY(y);
		}

		// checks if next field is an obstacle
		private synchronized void checkNextField(int newPos){
			this.initZombieCollision(newPos);
			if(zombieCollisionChecker != null) {
				if (zombieCollisionChecker.checkNextGameObject()) {
					this.fields[this.zombie.getXPos()][this.zombie.getYPos()] =
							new Field("GameObjects.Field_like_Objects.Field");
					this.fields[this.zombie.getXPos()][this.zombie.getYPos()].setX(this.zombie.getXPos() * 30);
					this.fields[this.zombie.getXPos()][this.zombie.getYPos()].setY(this.zombie.getYPos() * 30);
					this.changeZombiePos(newPos);
					this.zombie.walk();
				}
			}
		}

		// moves Skull in y-direction
		private synchronized void moveZombieLeft(){
			if (this.zombie.getXPos() - 1 >= 0){
				this.checkNextField(-1);
			} else {
				this.zombie.changePostion();
			}
		}

		// moves Skull in x-direction
		private synchronized void moveZombieRight(){
			if (this.zombie.getXPos() + 1 < xDimension) {
				this.checkNextField(1);
			} else {
				this.zombie.changePostion();
			}
		}

		private synchronized void moveZombieUp(){
			if (this.zombie.getYPos() -1 >= 0){
				this.checkNextField( - 1);
			} else {
				this.zombie.changePostion();
			}
		}

		private synchronized void moveZombieDown(){
			if (this.zombie.getYPos() + 1 < yDimension) {
				this.checkNextField(1);
			} else {
				this.zombie.changePostion();
			}
		}

		//calls vor every Skull the moveSkull method
		private synchronized void controllZombies(){
			switch (this.zombie.getPostion()){
				case Zombie_Right:
					this.moveZombieRight();
					break;
				case Zombie_Left:
					this.moveZombieLeft();
					break;
				case Zombie_Up:
					this.moveZombieUp();
					break;
				case Zombie_Down:
					this.moveZombieDown();
					break;
			}
		}

		//start Thread
		private  Runnable start(){
			return (()->{
				isRunning = true;
				thread = new Thread(this, "ZombieMovement");
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
		public synchronized void run() {
			long timer = System.currentTimeMillis();
			while(isRunning) {
				if (System.currentTimeMillis() - timer >= 7000 / 60) {
					timer += 4000 / 60;
					this.threadWaitManager.pauseThread(7000 / 60);
					this.controllZombies();
				}
			}
			this.stop();
		}

	}
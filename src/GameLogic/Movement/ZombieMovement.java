package GameLogic.Movement;

import GameLogic.Movement.MovementHelper.CollisionHelper.zombieCollisionChecker;
import GameLogic.ThreadHelper.ThreadWaitManager;
import GameObjects.Enemies.Zombie;
import GameObjects.Field_like_Objects.Field;
import GameObjects.GameObjectEnums.ZombiePostion;
import GameObjects.Player.Player;
import Rendering.View;

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
			this.start();
		}

		// changes skulls position
		private void changeZombiePos(int newPos){
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

		private zombieCollisionChecker initZombieCollision(int newPos){
			zombieCollisionChecker zombieCollisionChecker = null;
			switch (this.zombie.getPostion()) {
				case Zombie_Right:
				case Zombie_Left:
					zombieCollisionChecker = new zombieCollisionChecker(
							this.zombie.getXPos() + newPos,
							this.zombie.getYPos(),
							this.fields,
							this.zombie,
							this.player,
							this.view
					);
					break;
				case Zombie_Down:
				case Zombie_Up:
					zombieCollisionChecker = new zombieCollisionChecker(
							this.zombie.getXPos(),
							this.zombie.getYPos() + newPos,
							this.fields,
							this.zombie,
							this.player,
							this.view
					);
					break;
			}
			return zombieCollisionChecker;
		}

		// checks if next field is an obstacle
		private void checkNextField(int newPos){
			zombieCollisionChecker zombieCollisionChecker = this.initZombieCollision(newPos);
			if(zombieCollisionChecker != null) {
				if (zombieCollisionChecker.checkNextGameObject()) {
					this.fields[this.zombie.getXPos()][this.zombie.getYPos()] =
							new Field(
									this.zombie.getXPos() * 30,
									this.zombie.getYPos() * 30,
									"GameObjects.Field_like_Objects.Field"
							);
					this.changeZombiePos(newPos);
					this.zombie.walk();
				}
			}
		}

		// moves Skull in y-direction
		private void moveZombieLeft(){
			if (this.zombie.getXPos() - 1 >= 0){
				this.checkNextField(-1);
			} else {
				this.zombie.changePostion();
			}
		}

		// moves Skull in x-direction
		private void moveZombieRight(){
			if (this.zombie.getXPos() + 1 < xDimension) {
				this.checkNextField(1);
			} else {
				this.zombie.changePostion();
			}
		}

		private void moveZombieUp(){
			if (this.zombie.getYPos() -1 >= 0){
				this.checkNextField( - 1);
			} else {
				this.zombie.changePostion();
			}
		}

		private void moveZombieDown(){
			if (this.zombie.getYPos() + 1 < yDimension) {
				this.checkNextField(1);
			} else {
				this.zombie.changePostion();
			}
		}

		//calls vor every Skull the moveSkull method
		private void controllZombies(){
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
		private synchronized void start(){
			isRunning = true;
			thread = new Thread(this, "ZombieMovement");
			thread.setDaemon(true);
			thread.start();
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
				if (System.currentTimeMillis() - timer >= 6000 / 60) {
					timer += 3000 / 60;
					this.threadWaitManager.pauseThread(6000 / 60);
					this.controllZombies();
				}
			}
			this.stop();
		}

	}
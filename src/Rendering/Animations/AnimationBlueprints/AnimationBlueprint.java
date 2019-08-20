package Rendering.Animations.AnimationBlueprints;

import GameLogic.ThreadHelper.ThreadWaitManager;
import GameObjects.Field_like_Objects.Field;
import GameObjects.Player.Player;
import Rendering.View;
import javafx.scene.image.Image;

public class AnimationBlueprint implements Runnable{

	private Thread thread;
	private Field field;
	private Image[] frames;
	private boolean isRunning = false;
	private ThreadWaitManager threadWaitManager;
	private Player player;
	private View view;
	private long timer;
	private int counter;

	protected AnimationBlueprint(){
		this.threadWaitManager = new ThreadWaitManager();
	}


	private synchronized void start(){
		this.thread = new Thread(this, "AnimationBlueprint");
		this.thread.setDaemon(true);
		this.thread.start();
		this.isRunning = true;
	}

	private synchronized void stop(){
		if(this.player != null){
			this.player.attacked(view);
			this.view.setPlayerLives(this.player, -1);
			this.player.setAllowedToMove(true);
		}
		this.thread.interrupt();
	}

	//sets player image to field image if != null
	private void setPlayerImage(){
		if(player != null){
			this.player.setCurrentImageToFieldImage();
			this.player.setAllowedToMove(false);
		}
	}

	@Override
	public void run(){
		this.timer = System.currentTimeMillis();
		this.counter = 0;
		while (this.isRunning) {
			this.frameSetterLoop();
			field.setImageToDefault();
			this.isRunning = false;
		}
		this.stop();
	}

	//loops through frame images
	private void frameSetterLoop(){
		while (counter < frames.length) {
			this.setPlayerImage();
			if (System.currentTimeMillis() - timer > 7000 / 60) {
				field.setCurrentImage(frames[counter]);
				this.threadWaitManager.pauseThread(7000 / 60);
				counter++;
				timer += 3500 / 60;
			}
		}
		this.isRunning = false;
	}

	protected void animate(Field field, Image[] frames, Player player, View view){
		this.field = field;
		this.frames = frames;
		this.player = player;
		this.view = view;
		this.start();
	}
}

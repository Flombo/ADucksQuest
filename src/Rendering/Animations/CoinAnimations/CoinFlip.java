package Rendering.Animations.CoinAnimations;

import GameLogic.ThreadHelper.ThreadWaitManager;
import GameObjects.Collectibles.Coin;
import GameObjects.GameObjectEnums.CoinFlipFrames;
import javafx.scene.image.Image;

public class CoinFlip implements Runnable{

	private Image flip1;
	private Image flip2;
	private Image flip3;
	private Coin coin;
	private Thread thread;
	private boolean isRunning = false;
	private ThreadWaitManager threadWaitManager;

	public CoinFlip(Coin coin){
		this.flip1 = coin.loadImage("/textures/CoinAnimation/Coin_flip.png");
		this.flip2 = coin.loadImage("/textures/CoinAnimation/Coin_flip1.png");
		this.flip3 = coin.loadImage("/textures/CoinAnimation/Coin_flip2.png");
		this.coin = coin;
		this.threadWaitManager = new ThreadWaitManager();
	}

	private synchronized void start(){
		this.isRunning = true;
		this.thread = new Thread(this, "CoinFlip");
		this.thread.setDaemon(true);
		this.thread.start();
	}

	private synchronized void stop(){
		isRunning = false;
		this.thread.interrupt();
	}

	@Override
	public void run(){
		long timer = System.currentTimeMillis();
		while (isRunning){
			if(this.coin.isAllowedToFlip()) {
				if (System.currentTimeMillis() - timer > 7000 / 60) {
					this.changeCurrentCoinImage(this.coin);
					this.threadWaitManager.pauseThread(7000 / 60);
					timer += 3500 / 60;
				}
				this.coin.setCurrentImageToDefault();
			} else {
				isRunning = false;
			}
		}
		this.stop();
	}

	//changes currentCoinImage to next frame
	private void changeCurrentCoinImage(Coin coin){
		switch (coin.getFlipFrame()){
			case Coin_Default:
				coin.setCurrentImage(this.flip1);
				coin.setFlipFrame(CoinFlipFrames.Coin_Flip);
				break;
			case Coin_Flip:
				coin.setCurrentImage(this.flip2);
				coin.setFlipFrame(CoinFlipFrames.Coin_Flip1);
				break;
			case Coin_Flip1:
				coin.setCurrentImage(this.flip3);
				coin.setFlipFrame(CoinFlipFrames.Coin_Default);
				break;
		}
	}

	//inits thread
	public void initFlip(){
		this.start();
	}

}

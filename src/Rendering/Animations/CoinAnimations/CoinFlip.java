package Rendering.Animations.CoinAnimations;

import GameObjects.Collectibles.Coin;
import GameObjects.GameObjectEnums.CoinFlipFrames;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class CoinFlip implements Runnable{

	private BufferedImage flip1;
	private BufferedImage flip2;
	private BufferedImage flip3;
	private Coin coin;
	private Thread thread;
	private boolean isRunning = false;

	public CoinFlip(Coin coin){
		try {
			this.flip1 = ImageIO.read(getClass().getResource("/textures/CoinAnimation/Coin_flip.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			this.flip2 = ImageIO.read(getClass().getResource("/textures/CoinAnimation/Coin_flip1.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			this.flip3 = ImageIO.read(getClass().getResource("/textures/CoinAnimation/Coin_flip2.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.coin = coin;
	}

	private synchronized void start(){
		this.isRunning = true;
		this.thread = new Thread(this, "CoinFlip");
		this.thread.start();
	}

	private synchronized void stop(){
		isRunning = false;
		try{
			thread.join();
		} catch (InterruptedException e){
			e.printStackTrace();
		}
	}

	@Override
	public void run(){
		long timer = System.currentTimeMillis();
		while (isRunning){
			if (System.currentTimeMillis() - timer > 7000 / 60) {
				this.changeCurrentCoinImage(this.coin);
				try {
					Thread.sleep(7000 / 60);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				timer += 3500 /60;
			}
			this.coin.setCurrentImageToDefault();
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

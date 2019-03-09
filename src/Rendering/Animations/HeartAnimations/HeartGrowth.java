package Rendering.Animations.HeartAnimations;

import GameObjects.Heart;
import GameObjects.GameObjectEnums.HeartGrowthFrames;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class HeartGrowth implements Runnable {

	private BufferedImage growth1;
	private BufferedImage growth2;
	private Heart heart;
	private Thread thread;
	private boolean isRunning = false;

	public HeartGrowth(Heart heart){
		try {
			this.growth1 = ImageIO.read(getClass().getResource("/textures/HeartAnimation/Heart1.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			this.growth2 = ImageIO.read(getClass().getResource("/textures/HeartAnimation/Heart2.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.heart = heart;
	}

	private synchronized void start(){
		this.isRunning = true;
		this.thread = new Thread(this, "HeartGrowth");
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
			if (System.currentTimeMillis() - timer > 10000 / 60) {
				this.changeCurrentHeartImage(this.heart);
				try {
					Thread.sleep(10000 / 60);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				timer += 3500 /60;
			}
			this.heart.setCurrentImageToDefault();
		}
		this.stop();
	}

	//changes currentCoinImage to next frame
	private void changeCurrentHeartImage(Heart heart){
		switch (heart.getGrowthFrame()){
			case Heart_Default:
				heart.setCurrentImage(this.growth1);
				heart.setGrowthFrame(HeartGrowthFrames.Heart_1);
				break;
			case Heart_1:
				heart.setCurrentImage(this.growth2);
				heart.setGrowthFrame(HeartGrowthFrames.Heart_2);
				break;
			case Heart_2:
				heart.setImageToDefault();
				heart.setGrowthFrame(HeartGrowthFrames.Heart_Default);
				break;
		}
	}

	//inits thread
	public void initGrowth(){
		this.start();
	}
}
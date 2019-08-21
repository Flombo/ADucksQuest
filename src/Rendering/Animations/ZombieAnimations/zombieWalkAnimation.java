package Rendering.Animations.ZombieAnimations;

import GameObjects.Enemies.Zombie;
import GameObjects.GameObjectEnums.Frames.ZombieWalkFrames;
import Helper.ImageLoader;
import javafx.scene.image.Image;


public class zombieWalkAnimation {

	private Image leftFrame1;
	private Image leftFrame2;
	private Image rightFrame1;
	private Image rightFrame2;
	private Image downFrame1;
	private Image downFrame2;

	public zombieWalkAnimation() {
		ImageLoader imageLoader = new ImageLoader();
		this.leftFrame1 = imageLoader.loadImage("/textures/zombieAnimation/left/zombie_walk_left1.png");
		this.leftFrame2 = imageLoader.loadImage("/textures/zombieAnimation/left/zombie_walk_left2.png");
		this.rightFrame1 = imageLoader.loadImage("/textures/zombieAnimation/right/zombie_walk_right1.png");
		this.rightFrame2 = imageLoader.loadImage("/textures/zombieAnimation/right/zombie_walk_right2.png");
		this.downFrame1 = imageLoader.loadImage("/textures/zombieAnimation/down/zombie_walk_down1.png");
		this.downFrame2 = imageLoader.loadImage("/textures/zombieAnimation/down/zombie_walk_down2.png");
	}

	private void walkRight(Zombie zombie){
		switch (zombie.getZombieWalkFrame()){
			case Zombie_Right_Default:
				zombie.setCurrentImage(this.rightFrame1);
				zombie.setZombieWalkFrame(ZombieWalkFrames.Zombie_Right_1);
				break;
			case Zombie_Right_1:
				zombie.setCurrentImage(this.rightFrame2);
				zombie.setZombieWalkFrame(ZombieWalkFrames.Zombie_Right_2);
				break;
			case Zombie_Right_2:
				zombie.setImageToDefault();
				zombie.setZombieWalkFrame(ZombieWalkFrames.Zombie_Right_Default);
				break;
		}
	}

	private void walkDown(Zombie zombie){
		switch (zombie.getZombieWalkFrame()){
			case Zombie_Down_Default:
				zombie.setCurrentImage(this.downFrame1);
				zombie.setZombieWalkFrame(ZombieWalkFrames.Zombie_Down_1);
				break;
			case Zombie_Down_1:
				zombie.setCurrentImage(this.downFrame2);
				zombie.setZombieWalkFrame(ZombieWalkFrames.Zombie_Down_2);
				break;
			case Zombie_Down_2:
				zombie.setImageToDefault();
				zombie.setZombieWalkFrame(ZombieWalkFrames.Zombie_Down_Default);
				break;
		}
	}

	private void walkLeft(Zombie zombie){
		switch (zombie.getZombieWalkFrame()){
			case Zombie_Left_Default:
				zombie.setCurrentImage(this.leftFrame1);
				zombie.setZombieWalkFrame(ZombieWalkFrames.Zombie_Left_1);
				break;
			case Zombie_Left_1:
				zombie.setCurrentImage(this.leftFrame2);
				zombie.setZombieWalkFrame(ZombieWalkFrames.Zombie_Left_2);
				break;
			case Zombie_Left_2:
				zombie.setImageToDefault();
				zombie.setZombieWalkFrame(ZombieWalkFrames.Zombie_Left_Default);
				break;
		}
	}

	public void walk(Zombie zombie){
		switch (zombie.getPostion()){
			case Zombie_Left:
				this.walkLeft(zombie);
				break;
			case Zombie_Down:
				this.walkDown(zombie);
				break;
			case Zombie_Right:
				this.walkRight(zombie);
				break;
		}
	}

}

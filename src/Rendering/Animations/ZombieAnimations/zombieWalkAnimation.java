package Rendering.Animations.ZombieAnimations;

import GameObjects.Enemies.Zombie;
import GameObjects.GameObjectEnums.ZombiePostion;
import GameObjects.GameObjectEnums.ZombieWalkFrames;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class zombieWalkAnimation {

	private BufferedImage leftFrame1;
	private BufferedImage leftFrame2;
	private BufferedImage rightFrame1;
	private BufferedImage rightFrame2;
	private BufferedImage downFrame1;
	private BufferedImage downFrame2;

	public zombieWalkAnimation() {
		try {
			this.leftFrame1 = ImageIO.read(getClass().getResource("/textures/zombieAnimation/left/zombie_walk_left1.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			this.leftFrame2 = ImageIO.read(getClass().getResource("/textures/zombieAnimation/left/zombie_walk_left2.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			this.rightFrame1 = ImageIO.read(getClass().getResource("/textures/zombieAnimation/right/zombie_walk_right1.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			this.rightFrame2 = ImageIO.read(getClass().getResource("/textures/zombieAnimation/right/zombie_walk_right2.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			this.downFrame1 = ImageIO.read(getClass().getResource("/textures/zombieAnimation/down/zombie_walk_down1.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			this.downFrame2 = ImageIO.read(getClass().getResource("/textures/zombieAnimation/down/zombie_walk_down2.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
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

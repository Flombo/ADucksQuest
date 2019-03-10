package Rendering.Animations.ZombieAnimations;

import GameObjects.Enemies.Zombie;
import Rendering.Animations.AnimationBlueprints.AnimationBlueprint;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class zombieAttackAnimation extends AnimationBlueprint {

	private BufferedImage[] frames;
	private BufferedImage frame1;
	private BufferedImage frame2;
	private BufferedImage frame3;
	private Zombie zombie;

	public zombieAttackAnimation(Zombie zombie){
		this.zombie = zombie;
	}

	//plays attack animation
	public void animateAttack(){
		this.initFrames();
		this.animation(this.zombie, this.frames);
	}

	//inits attackframes equal to skull postion
	private void initFrames(){
		switch (this.zombie.getPostion()) {
			case Zombie_Right:
				try {
					this.frame1 = ImageIO.read(getClass().getResource("/textures/zombieTextures/right/zombie_right.png"));
				} catch (IOException e) {
					e.printStackTrace();
				}
				try {
					this.frame2 = ImageIO.read(getClass().getResource("/textures/zombieAnimation/attackAnimation/right/zombie_right_1.png"));
				} catch (IOException e) {
					e.printStackTrace();
				}
				try {
					this.frame3 = ImageIO.read(getClass().getResource("/textures/zombieAnimation/attackAnimation/right/zombie_right_2.png"));
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case Zombie_Down:
				try {
					this.frame1 = ImageIO.read(getClass().getResource("/textures/zombieTextures/down/zombie_down.png"));
				} catch (IOException e) {
					e.printStackTrace();
				}
				try {
					this.frame2 = ImageIO.read(getClass().getResource("/textures/zombieAnimation/attackAnimation/down/zombie_down1.png"));
				} catch (IOException e) {
					e.printStackTrace();
				}
				try {
					this.frame3 = ImageIO.read(getClass().getResource("/textures/zombieAnimation/attackAnimation/down/zombie_down2.png"));
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case Zombie_Left:
				try {
					this.frame1 = ImageIO.read(getClass().getResource("/textures/zombieTextures/left/zombie_left.png"));
				} catch (IOException e) {
					e.printStackTrace();
				}
				try {
					this.frame2 = ImageIO.read(getClass().getResource("/textures/zombieAnimation/attackAnimation/left/zombie_left_1.png"));
				} catch (IOException e) {
					e.printStackTrace();
				}
				try {
					this.frame3 = ImageIO.read(getClass().getResource("/textures/zombieAnimation/attackAnimation/left/zombie_left_2.png"));
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case Zombie_Up:
				try {
					this.frame1 = ImageIO.read(getClass().getResource("/textures/zombieTextures/up/zombie_up.png"));
				} catch (IOException e) {
					e.printStackTrace();
				}
				this.frame2 = this.frame3 = this.frame1;
				break;
		}
		this.frames = new BufferedImage[60];
		for(int i = 0; i < this.frames.length - 3; i += 3){
			this.frames[i] = this.frame1;
			this.frames[i + 1] = this.frame2;
			this.frames[i + 2] = this.frame3;
		}
	}
}

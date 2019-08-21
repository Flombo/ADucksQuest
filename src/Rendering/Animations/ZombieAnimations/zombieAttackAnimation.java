package Rendering.Animations.ZombieAnimations;

import GameObjects.Enemies.Zombie;
import Rendering.Animations.AnimationBlueprints.AnimationBlueprint;
import javafx.scene.image.Image;

public class zombieAttackAnimation extends AnimationBlueprint {

	private Image[] frames;
	private Image frame1;
	private Image frame2;
	private Image frame3;
	private Image attackRight1;
	private Image attackRight2;
	private Image attackRight3;
	private Image attackLeft1;
	private Image attackLeft2;
	private Image attackLeft3;
	private Image attackUp1;
	private Image attackBottom1;
	private Image attackBottom2;
	private Image attackBottom3;
	private Zombie zombie;

	public zombieAttackAnimation(Zombie zombie){
		this.zombie = zombie;
		this.intitAttackFrames();
	}

	private void intitAttackFrames(){
		this.attackBottom1 = this.zombie.loadImage("/textures/zombieTextures/down/zombie_down.png");
		this.attackBottom2 = this.zombie.loadImage("/textures/zombieAnimation/attackAnimation/down/zombie_down1.png");
		this.attackBottom3 = this.zombie.loadImage("/textures/zombieAnimation/attackAnimation/down/zombie_down2.png");

		this.attackLeft1 = this.zombie.loadImage("/textures/zombieTextures/left/zombie_left.png");
		this.attackLeft2 = this.zombie.loadImage("/textures/zombieAnimation/attackAnimation/left/zombie_left_1.png");
		this.attackLeft3 = this.zombie.loadImage("/textures/zombieAnimation/attackAnimation/left/zombie_left_2.png");

		this.attackRight1 = this.zombie.loadImage("/textures/zombieTextures/right/zombie_right.png");
		this.attackRight2 = this.zombie.loadImage("/textures/zombieAnimation/attackAnimation/right/zombie_right_1.png");
		this.attackRight3 = this.zombie.loadImage("/textures/zombieAnimation/attackAnimation/right/zombie_right_2.png");

		this.attackUp1 = this.zombie.loadImage("/textures/zombieTextures/up/zombie_up.png");
	}

	//plays attack animation
	public void animateAttack(){
		this.initFrames();
		this.animate(this.zombie, this.frames, null, null);
	}

	//inits attackframes equal to zombie postion
	private void initFrames(){
		switch (this.zombie.getPostion()) {
			case Zombie_Right:
				this.frame1 = this.attackRight1;
				this.frame2 = this.attackRight2;
				this.frame3 = this.attackRight3;
				break;
			case Zombie_Down:
				this.frame1 = this.attackBottom1;
				this.frame2 = this.attackBottom2;
				this.frame3 = this.attackBottom3;
				break;
			case Zombie_Left:
				this.frame1 = this.attackLeft1;
				this.frame2 = this.attackLeft2;
				this.frame3 = this.attackLeft3;
				break;
			case Zombie_Up:
				this.frame1 = this.attackUp1;
				this.frame2 = this.frame3 = this.frame1;
				break;
		}
		this.frames = new Image[9];
		for(int i = 0; i < this.frames.length - 3; i += 3){
			this.frames[i] = this.frame1;
			this.frames[i + 1] = this.frame2;
			this.frames[i + 2] = this.frame3;
		}
	}
}

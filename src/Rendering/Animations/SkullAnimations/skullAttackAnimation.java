package Rendering.Animations.SkullAnimations;

import GameObjects.GameObjectEnums.SkullPosition;
import GameObjects.Skull;
import Rendering.Animations.AnimationBlueprints.AnimationBlueprint;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class skullAttackAnimation extends AnimationBlueprint {

	private BufferedImage[] frames;
	private BufferedImage frame1;
	private BufferedImage frame2;
	private Skull skull;

	public skullAttackAnimation(Skull skull){
		this.skull = skull;
		this.initFrames();
	}

	//plays attack animation
	public void animateAttack(){
		this.animation(this.skull, this.frames);
	}

	//inits attackframes equal to skull postion
	private void initFrames(){
		if(this.skull.getPosition().equals(SkullPosition.SKULL_RIGHT)) {
			try {
				this.frame1 = ImageIO.read(getClass().getResource("/textures/skullTextureRight.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				this.frame2 = ImageIO.read(getClass().getResource("/textures/skullAnimation/skullTextureRightWalk1.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else {
			try {
				this.frame1 = ImageIO.read(getClass().getResource("/textures/skullTextureLeft.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				this.frame2 = ImageIO.read(getClass().getResource("/textures/skullAnimation/skullTextureLeftWalk1.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		this.frames = new BufferedImage[60];
		for(int i = 0; i < this.frames.length - 2; i += 2){
			this.frames[i] = this.frame1;
			this.frames[i + 1] = this.frame2;
		}
	}

}

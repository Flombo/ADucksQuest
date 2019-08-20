package Rendering.Animations.SkullAnimations;

import GameObjects.GameObjectEnums.SkullPosition;
import GameObjects.Enemies.Skull;
import Rendering.Animations.AnimationBlueprints.AnimationBlueprint;
import javafx.scene.image.Image;

public class skullAttackAnimation extends AnimationBlueprint {

	private Image[] frames;
	private Skull skull;

	public skullAttackAnimation(Skull skull){
		this.skull = skull;
		this.initFrames();
	}

	//plays attack animate
	public void animateAttack(){
		this.animate(this.skull, this.frames, null, null);
	}

	//inits attackframes equal to skull postion
	private void initFrames(){
		Image frame1;
		Image frame2;
		if(this.skull.getPosition().equals(SkullPosition.SKULL_RIGHT)) {
			frame1 = this.skull.loadImage("/textures/skullTextureRight.png");
			frame2 = this.skull.loadImage("/textures/skullAnimation/skullTextureRightWalk1.png");
		}else {
			frame1 = this.skull.loadImage("/textures/skullTextureLeft.png");
			frame2 = this.skull.loadImage("/textures/skullAnimation/skullTextureLeftWalk1.png");
		}
		this.frames = new Image[9];
		for(int i = 0; i < this.frames.length - 2; i += 2){
			this.frames[i] = frame1;
			this.frames[i + 1] = frame2;
		}
	}

}

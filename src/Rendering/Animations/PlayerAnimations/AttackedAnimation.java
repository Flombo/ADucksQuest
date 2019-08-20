package Rendering.Animations.PlayerAnimations;

import GameObjects.Player.Player;
import Helper.ImageLoader;
import Rendering.Animations.AnimationBlueprints.AnimationBlueprint;
import javafx.scene.image.Image;

public class AttackedAnimation extends AnimationBlueprint {

	private Image[] frames;

	public AttackedAnimation(){
		ImageLoader imageLoader = new ImageLoader();
		Image frame1 = imageLoader.loadImage("/textures/playerAnimation/playerDownTextureAttacked1.png");
		Image frame2 = imageLoader.loadImage("/textures/playerAnimation/playerDownTextureAttacked2.png");
		Image frame3 = imageLoader.loadImage("/textures/playerAnimation/playerDownTextureAttacked3.png");
		Image frame4 = imageLoader.loadImage("/textures/playerDownTexture.png");
		this.frames = new Image[9];
		for(int i = 0; i < 9 - 3; i += 3){
			this.frames[i] = frame1;
			this.frames[i + 1] = frame2;
			this.frames[i + 2] = frame3;
			this.frames[i + 3] = frame4;
		}
	}

	public void attacked(Player player){
		this.animate(player, this.frames, null, null);
	}
}

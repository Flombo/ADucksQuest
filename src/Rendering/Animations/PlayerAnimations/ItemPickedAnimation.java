package Rendering.Animations.PlayerAnimations;

import GameObjects.Player.Player;
import Helper.ImageLoader;
import Rendering.Animations.AnimationBlueprints.AnimationBlueprint;
import javafx.scene.image.Image;

public class ItemPickedAnimation extends AnimationBlueprint {

	private Image[] frames;

	public ItemPickedAnimation() {
		ImageLoader imageLoader = new ImageLoader();
		Image frame1 = imageLoader.loadImage("/textures/playerAnimation/itemPickAnimation/playerItemPick1.png");
		Image frame2 = imageLoader.loadImage("/textures/playerAnimation/itemPickAnimation/playerItemPick2.png");
		Image frame3 = imageLoader.loadImage("/textures/playerAnimation/itemPickAnimation/playerItemPick3.png");

		this.frames = new Image[60];

		for (int i = 0; i < 60 - 2; i += 2) {
			this.frames[i] = frame1;
			this.frames[i + 1] = frame2;
			this.frames[i + 2] = frame3;
		}
	}

	public void animateItemPicked(Player player){
		this.animation(player, this.frames);
	}

}

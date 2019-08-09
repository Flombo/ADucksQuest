package Rendering.Animations.HoleAnimations;

import GameObjects.Obstacles.Hole;
import Helper.ImageLoader;
import Rendering.Animations.AnimationBlueprints.AnimationBlueprint;
import javafx.scene.image.Image;

public class PlayerFallsAnimation extends AnimationBlueprint {

	private Image[] frames;

	public PlayerFallsAnimation(){
		ImageLoader imageLoader = new ImageLoader();
		Image frame1 = imageLoader.loadImage("/textures/playerFallsAnimation/playerFalls1.png");
		Image frame2 = imageLoader.loadImage("/textures/playerFallsAnimation/playerFalls2.png");
		Image frame3 = imageLoader.loadImage("/textures/playerFallsAnimation/playerFalls3.png");
		Image frame4 = imageLoader.loadImage("/textures/playerFallsAnimation/playerFalls4.png");
		this.frames = new Image[60];
		for(int i = 0; i < 60 - 3; i += 3){
			this.frames[i] = frame1;
			this.frames[i + 1] = frame2;
			this.frames[i + 2] = frame3;
			this.frames[i + 3] = frame4;
		}
	}

	public void animatePlayerFalls(Hole hole){
		this.animation(hole, this.frames);
	}
}

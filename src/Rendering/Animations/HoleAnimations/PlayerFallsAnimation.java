package Rendering.Animations.HoleAnimations;

import GameObjects.Obstacles.Hole;
import Rendering.Animations.AnimationBlueprints.AnimationBlueprint;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class PlayerFallsAnimation extends AnimationBlueprint {

	private BufferedImage[] frames;
	private BufferedImage frame1;
	private BufferedImage frame2;
	private BufferedImage frame3;
	private BufferedImage frame4;

	public PlayerFallsAnimation(){
		try {
			this.frame1 = ImageIO.read(getClass().getResource("/textures/playerFallsAnimation/playerFalls1.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			this.frame2 = ImageIO.read(getClass().getResource("/textures/playerFallsAnimation/playerFalls2.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			this.frame3 = ImageIO.read(getClass().getResource("/textures/playerFallsAnimation/playerFalls3.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			this.frame4 = ImageIO.read(getClass().getResource("/textures/playerFallsAnimation/playerFalls4.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.frames = new BufferedImage[60];
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

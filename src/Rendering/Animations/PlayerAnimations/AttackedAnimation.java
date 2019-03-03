package Rendering.Animations.PlayerAnimations;

import GameObjects.Player;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class AttackedAnimation {

	private BufferedImage[] frames;
	private BufferedImage frame1;
	private BufferedImage frame2;
	private BufferedImage frame3;
	private BufferedImage frame4;

	public AttackedAnimation(){
		try {
			this.frame1 = ImageIO.read(getClass().getResource("/textures/playerAnimation/playerDownTextureAttacked1.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			this.frame2 = ImageIO.read(getClass().getResource("/textures/playerAnimation/playerDownTextureAttacked2.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			this.frame3 = ImageIO.read(getClass().getResource("/textures/playerAnimation/playerDownTextureAttacked3.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			this.frame4 = ImageIO.read(getClass().getResource("/textures/playerDownTexture.png"));
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

	public void attacked(Player player){
		long timer = System.currentTimeMillis();
		int counter = 0;
		while (counter < this.frames.length) {
			if (System.currentTimeMillis() - timer > 1000 / 60) {
				player.setCurrentImage(this.frames[counter]);
				counter++;
				timer += 1000 / 60 / 2;
			}
		}
		player.setImageToDefault();
	}
}

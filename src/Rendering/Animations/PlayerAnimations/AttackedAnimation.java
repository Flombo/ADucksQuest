package Rendering.Animations.PlayerAnimations;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class AttackedAnimation {

	private BufferedImage[] frames;
	private BufferedImage frame1;
	private BufferedImage frame2;
	private BufferedImage frame3;

	public AttackedAnimation(){
		try {
			this.frame1 = ImageIO.read(getClass().getResource("/textures/playerDownTextureAttacked1.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			this.frame2 = ImageIO.read(getClass().getResource("/textures/playerDownTextureAttacked2.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			this.frame3 = ImageIO.read(getClass().getResource("/textures/playerDownTextureAttacked3.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.frames = new BufferedImage[]{frame1, frame2, frame3};
	}

	public BufferedImage[] getFrames(){
		return this.frames;
	}
}

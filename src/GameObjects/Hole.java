package GameObjects;

import Rendering.Animations.HoleAnimations.PlayerFallsAnimation;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Hole extends Field {
	private BufferedImage currentImage;
	private BufferedImage defaultImage;
	private PlayerFallsAnimation playerFallsAnimation;

	public Hole() {
		super(0, 0, "GameObjects.Hole");
		try {
			this.defaultImage = ImageIO.read(getClass().getResource("/textures/holeTexture.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.currentImage = defaultImage;
		this.playerFallsAnimation = new PlayerFallsAnimation();
	}

	@Override
	public BufferedImage getCurrentImage() {
		return currentImage;
	}

	@Override
	public void setImageToDefault() {
		this.currentImage = defaultImage;
	}

	public void animatePlayerFall(){
		this.playerFallsAnimation.animatePlayerFalls(this);
	}

	@Override
	public void setCurrentImage(BufferedImage currentImage) {
		this.currentImage = currentImage;
	}
}

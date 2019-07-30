package GameObjects.Collectibles;

import GameObjects.Field_like_Objects.Field;
import GameObjects.GameObjectEnums.HeartGrowthFrames;
import Rendering.Animations.HeartAnimations.HeartGrowth;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Heart extends Field {
	private BufferedImage currentImage;
	private BufferedImage defaultImage;
	private HeartGrowthFrames growthFrame;
	private HeartGrowth heartGrowth;
	private boolean allowedToGrow = true;

	public Heart() {
		super(0, 0, "GameObjects.Collectibles.Heart");
		try {
			this.defaultImage = ImageIO.read(getClass().getResource("/textures/HeartAnimation/Heart.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.currentImage = defaultImage;
		this.growthFrame = HeartGrowthFrames.Heart_Default;
		this.startAnimation();
	}

	//starts animation
	public void startAnimation(){
		this.allowedToGrow = true;
		this.heartGrowth = new HeartGrowth(this);
		this.growth();
	}

	@Override
	public BufferedImage getCurrentImage() {
		return currentImage;
	}

	public void setCurrentImage(BufferedImage currentImage) {
		this.currentImage = currentImage;
	}

	public void setCurrentImageToDefault(){
		this.currentImage = defaultImage;
	}

	public HeartGrowthFrames getGrowthFrame() {
		return growthFrame;
	}

	public void setGrowthFrame(HeartGrowthFrames growthFrame) {
		this.growthFrame = growthFrame;
	}

	private void growth(){
		this.heartGrowth.initGrowth();
	}

	public boolean isAllowedToGrow() {
		return allowedToGrow;
	}

	public void setAllowedToGrow(boolean allowedToGrow) {
		this.allowedToGrow = allowedToGrow;
	}
}

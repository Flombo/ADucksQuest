package GameObjects.Collectibles;

import GameObjects.Field_like_Objects.Field;
import GameObjects.GameObjectEnums.Frames.HeartGrowthFrames;
import Rendering.Animations.HeartAnimations.HeartGrowth;
import Sound.InteractionSounds.ItemPickedSound;
import javafx.scene.image.Image;

public class Heart extends Field {
	private Image currentImage;
	private Image defaultImage;
	private HeartGrowthFrames growthFrame;
	private HeartGrowth heartGrowth;
	private ItemPickedSound itemPickedSound;

	public Heart() {
		super("GameObjects.Collectibles.Heart");
		this.defaultImage = this.loadImage("/textures/HeartAnimation/Heart.png");
		//this.itemPickedSound = new ItemPickedSound();
		this.currentImage = defaultImage;
		this.growthFrame = HeartGrowthFrames.Heart_Default;
		this.startAnimation();
	}

	public void playHeartPicked(){
		this.itemPickedSound.playItemPicked();
	}

	//starts animation
	public void startAnimation(){
		this.heartGrowth = new HeartGrowth(this);
		this.growth();
	}

	@Override
	public Image getCurrentImage() {
		return currentImage;
	}

	public void setCurrentImage(Image currentImage) {
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

	public void setAllowedToGrow(boolean allowedToGrow) {
		this.heartGrowth.setIsRunning(allowedToGrow);
	}
}

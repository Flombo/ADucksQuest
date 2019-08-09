package GameObjects.Obstacles;

import GameObjects.Field_like_Objects.Field;
import Rendering.Animations.HoleAnimations.PlayerFallsAnimation;
import javafx.scene.image.Image;

public class Hole extends Field {
	private Image currentImage;
	private Image defaultImage;
	private PlayerFallsAnimation playerFallsAnimation;

	public Hole() {
		super(0, 0, "GameObjects.Obstacles.Hole");
		this.defaultImage = this.loadImage("/textures/holeTexture.png");
		this.currentImage = defaultImage;
		this.playerFallsAnimation = new PlayerFallsAnimation();
	}

	@Override
	public Image getCurrentImage() {
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
	public void setCurrentImage(Image currentImage) {
		this.currentImage = currentImage;
	}
}

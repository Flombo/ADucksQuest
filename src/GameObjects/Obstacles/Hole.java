package GameObjects.Obstacles;

import GameObjects.Field_like_Objects.Field;
import GameObjects.Player.Player;
import Rendering.Animations.HoleAnimations.PlayerFallsAnimation;
import Rendering.View;
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

	public void animatePlayerFall(Player player, View view){
		this.playerFallsAnimation.animatePlayerFalls(this, player, view);
	}

	@Override
	public void setCurrentImage(Image currentImage) {
		this.currentImage = currentImage;
	}
}

package GameObjects.Obstacles;

import GameObjects.Field_like_Objects.Field;
import javafx.scene.image.Image;

public class Obstacle extends Field {

	private Image image;

	public Obstacle(){
		super(0, 0, "GameObjects.Obstacles.Obstacle");
		this.image = this.loadImage("/textures/obstacleTexture.png");
	}

	@Override
	public Image getCurrentImage() {
		return image;
	}
}

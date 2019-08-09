package GameObjects.Obstacles;

import GameObjects.Field_like_Objects.Field;
import javafx.scene.image.Image;

public class Chest extends Field {

	private Image image;

	public Chest() {
		super(0, 0, "GameObjects.Obstacles.Chest");
		this.image = this.loadImage("/textures/chest.png");
	}

	@Override
	public Image getCurrentImage() {
		return image;
	}
}

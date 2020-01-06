package GameObjects.Field_like_Objects;

import javafx.scene.image.Image;

public class FilledHole extends Field {

	private Image image;

	public FilledHole() {
		super("GameObjects.Field_like_Objects.FilledHole");
		this.image = this.loadImage("/textures/filledHole.png");
	}

	@Override
	public Image getCurrentImage() {
		return this.image;
	}
}

package GameObjects.Target;

import GameObjects.Field_like_Objects.Field;
import javafx.scene.image.Image;

public class Target extends Field {

	private Image image;

	public Target(){
		super(0, 0, "GameObjects.Target.Target");
		this.image = this.loadImage("/textures/targetTexture.png");
	}

	@Override
	public Image getCurrentImage() {
		return image;
	}
}

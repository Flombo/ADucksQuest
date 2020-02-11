package GameObjects.Target;

import GameObjects.Field_like_Objects.Field;
import Sound.InteractionSounds.VictorySound;
import javafx.scene.image.Image;

public class Target extends Field {

	private Image image;
	private VictorySound victorySound;

	public Target(){
		super("GameObjects.Target.Target");
		this.image = this.loadImage("/textures/targetTexture.png");
		this.victorySound = new VictorySound();
	}

	public void playVictorySound(){
		this.victorySound.playVictorySound();
	}

	@Override
	public Image getCurrentImage() {
		return image;
	}
}

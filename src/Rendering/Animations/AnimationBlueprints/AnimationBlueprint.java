package Rendering.Animations.AnimationBlueprints;

import java.awt.image.BufferedImage;
import GameObjects.Field;

public class AnimationBlueprint {

	protected void animation(Field field, BufferedImage[] frames){
		long timer = System.currentTimeMillis();
		int counter = 0;
		while (counter < frames.length) {
			if (System.currentTimeMillis() - timer > 1000 / 60) {
				field.setCurrentImage(frames[counter]);
				counter++;
				timer += 1000 / 60 / 2;
			}
		}
		field.setImageToDefault();
	}
}

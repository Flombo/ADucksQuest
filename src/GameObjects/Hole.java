package GameObjects;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Hole extends Field {
	private  BufferedImage image;

	public Hole() {
		super(0, 0, "GameObjects.Hole");
		try {
			this.image = ImageIO.read(getClass().getResource("/textures/holeTexture.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public BufferedImage getCurrentImage() {
		return image;
	}
}

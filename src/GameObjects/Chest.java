package GameObjects;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Chest extends Field {

	private BufferedImage image;

	public Chest() {
		super(0, 0, "GameObjects.Chest");
		try {
			this.image = ImageIO.read(getClass().getResource("/textures/chest.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public BufferedImage getCurrentImage() {
		return image;
	}
}

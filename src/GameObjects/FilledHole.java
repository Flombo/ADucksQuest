package GameObjects;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class FilledHole extends Field {

	private BufferedImage image;

	public FilledHole() {
		super(0, 0, "GameObjects.FilledHole");
		try {
			this.image = ImageIO.read(getClass().getResource("/textures/filledHole.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public BufferedImage getCurrentImage() {
		return this.image;
	}
}

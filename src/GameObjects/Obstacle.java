package GameObjects;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Obstacle extends Field {
	private BufferedImage image;
	public Obstacle(){
		super(0, 0, "GameObjects.Obstacle");
		try {
			this.image = ImageIO.read(getClass().getResource("/textures/obstacleTexture.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public BufferedImage getImage() {
		return image;
	}
}

package GameObjects;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Target extends Field {
	private BufferedImage image;
	public Target(){
		super(0, 0, "GameObjects.Target");
		try {
			this.image = ImageIO.read(getClass().getResource("/textures/targetTexture.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public BufferedImage getCurrentImage() {
		return image;
	}
}

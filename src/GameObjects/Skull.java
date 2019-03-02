package GameObjects;

import GameObjects.GameObjectEnums.SkullPosition;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Skull extends Field {
	private SkullPosition position;
	private BufferedImage image;

	public Skull() {
		super(0, 0, "GameObjects.Skull");
		try {
			this.image = ImageIO.read(getClass().getResource("/textures/skullTextureRight.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.position = SkullPosition.SKULL_RIGHT;
	}


	public SkullPosition getPosition() {
		return position;
	}

	public void changePosition() {
		if(this.getPosition().equals(SkullPosition.SKULL_RIGHT)){
			this.setPosition(SkullPosition.SKULL_LEFT);
		} else {
			this.setPosition(SkullPosition.SKULL_RIGHT);
		}
	}

	private void setPosition(SkullPosition position) {
		this.position = position;
		this.setImage();
	}

	@Override
	public BufferedImage getImage() {
		return image;
	}

	private void setImage() {
		if(this.getPosition().equals(SkullPosition.SKULL_RIGHT)) {
			try {
				this.image = ImageIO.read(getClass().getResource("/textures/skullTextureRight.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			try {
				this.image = ImageIO.read(getClass().getResource("/textures/skullTextureLeft.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}

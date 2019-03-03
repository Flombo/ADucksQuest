package GameObjects;

import GameObjects.GameObjectEnums.SkullPosition;
import GameObjects.GameObjectEnums.SkullWalkFrames;
import Rendering.Animations.SkullAnimations.skullWalkAnimation;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Skull extends Field {
	private SkullPosition position;
	private SkullWalkFrames walkFrame;
	private BufferedImage currentImage;
	private BufferedImage imageRight;
	private BufferedImage imageLeft;
	private skullWalkAnimation skullWalkAnimation;

	public Skull() {
		super(0, 0, "GameObjects.Skull");
		try {
			this.imageRight = ImageIO.read(getClass().getResource("/textures/skullTextureRight.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			this.imageLeft = ImageIO.read(getClass().getResource("/textures/skullTextureLeft.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.currentImage = imageRight;
		this.walkFrame = SkullWalkFrames.Skull_Right_Default;
		this.position = SkullPosition.SKULL_RIGHT;
		this.skullWalkAnimation = new skullWalkAnimation();
	}

	public SkullWalkFrames getWalkFrame() {
		return walkFrame;
	}

	public void setImageToDefault(Skull skull){
		switch (skull.getPosition()) {
			case SKULL_LEFT:
				this.currentImage = imageRight;
				this.walkFrame = SkullWalkFrames.Skull_Right_Default;
				break;
			case SKULL_RIGHT:
				this.currentImage = imageLeft;
				this.walkFrame = SkullWalkFrames.Skull_Left_Default;
				break;
		}
	}

	public SkullPosition getPosition() {
		return position;
	}

	public void changePosition() {
		if(this.getPosition().equals(SkullPosition.SKULL_RIGHT)){
			this.setPosition(SkullPosition.SKULL_LEFT);
			this.walkFrame = SkullWalkFrames.Skull_Left_Default;
		} else {
			this.setPosition(SkullPosition.SKULL_RIGHT);
			this.walkFrame = SkullWalkFrames.Skull_Right_Default;
		}
	}

	private void setPosition(SkullPosition position) {
		this.position = position;
		this.setImageToDefault(this);
	}

	@Override
	public BufferedImage getCurrentImage() {
		return currentImage;
	}

	public void walk(){
		this.skullWalkAnimation.walk(this);
	}

	public void setImage(BufferedImage image){
		this.currentImage = image;
	}

	public void setWalkFrame(SkullWalkFrames skullWalkFrame) {
		this.walkFrame = skullWalkFrame;
	}
}

package GameObjects.Enemies;

import GameObjects.Field_like_Objects.Field;
import GameObjects.GameObjectEnums.ZombiePostion;
import GameObjects.GameObjectEnums.ZombieWalkFrames;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Zombie extends Field {

	private BufferedImage currentImage;
	private BufferedImage upImage;
	private BufferedImage downImage;
	private BufferedImage leftImage;
	private BufferedImage rightImage;
	private ZombiePostion zombiePostion;
	private ZombieWalkFrames zombieWalkFrame;

	public Zombie() {
		super(0, 0, "GameObjects.Enemies.Zombie");
		try {
			this.downImage = ImageIO.read(getClass().getResource("/textures/zombieTextures/zombie_down.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			this.upImage = ImageIO.read(getClass().getResource("/textures/zombieTextures/zombie_up.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			this.leftImage = ImageIO.read(getClass().getResource("/textures/zombieTextures/zombie_left.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			this.rightImage = ImageIO.read(getClass().getResource("/textures/zombieTextures/zombie_right.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.currentImage = this.downImage;
		this.zombiePostion = ZombiePostion.Zombie_Down;
		this.zombieWalkFrame = ZombieWalkFrames.Zombie_Down_Default;
	}

	@Override
	public void setCurrentImage(BufferedImage currentImage) {
		this.currentImage = currentImage;
	}

	@Override
	public void setImageToDefault() {
		switch (this.zombiePostion){
			case Zombie_Up:
				this.currentImage = this.upImage;
				break;
			case Zombie_Down:
				this.currentImage = this.downImage;
				break;
			case Zombie_Left:
				this.currentImage = this.leftImage;
				break;
			case Zombie_Right:
				this.currentImage = this.rightImage;
				break;
		}
	}

	@Override
	public BufferedImage getCurrentImage() {
		return currentImage;
	}

	public void changePostion() {
		ZombiePostion[] zombiePostions = new ZombiePostion[]{
				ZombiePostion.Zombie_Down,
				ZombiePostion.Zombie_Up,
				ZombiePostion.Zombie_Right,
				ZombiePostion.Zombie_Left
		};
		int randomIndex = (int) Math.floor(Math.random() * zombiePostions.length);
		this.zombiePostion = zombiePostions[randomIndex];
		this.changeZombieWalkFrame();
	}

	private void changeZombieWalkFrame() {
		switch (this.zombiePostion){
			case Zombie_Up:
				this.zombieWalkFrame = ZombieWalkFrames.Zombie_Up_Default;
				this.setImageToDefault();
				break;
			case Zombie_Down:
				this.zombieWalkFrame = ZombieWalkFrames.Zombie_Down_Default;
				this.setImageToDefault();
				break;
			case Zombie_Left:
				this.zombieWalkFrame = ZombieWalkFrames.Zombie_Left_Default;
				this.setImageToDefault();
				break;
			case Zombie_Right:
				this.zombieWalkFrame = ZombieWalkFrames.Zombie_Right_Default;
				this.setImageToDefault();
				break;
		}
	}

	public ZombiePostion getPostion() {
		return zombiePostion;
	}

	public void setZombieWalkFrame(ZombieWalkFrames zombieWalkFrame) {
		this.zombieWalkFrame = zombieWalkFrame;
	}

	public ZombieWalkFrames getZombieWalkFrame() {
		return zombieWalkFrame;
	}

	public void walk(){

	}

	public void attack(){

	}
}

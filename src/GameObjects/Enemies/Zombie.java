package GameObjects.Enemies;

import GameObjects.Field_like_Objects.Field;
import GameObjects.GameObjectEnums.PositionEnums.ZombiePostion;
import GameObjects.GameObjectEnums.Frames.ZombieWalkFrames;
import Rendering.Animations.ZombieAnimations.zombieAttackAnimation;
import Rendering.Animations.ZombieAnimations.zombieWalkAnimation;
import Sound.EnemieSounds.ZombieSound;
import javafx.scene.image.Image;

public class Zombie extends Field {

	private Image currentImage;
	private Image upImage;
	private Image downImage;
	private Image leftImage;
	private Image rightImage;
	private ZombiePostion zombiePostion;
	private ZombieWalkFrames zombieWalkFrame;
	private zombieWalkAnimation zombieWalkAnimation;
	private zombieAttackAnimation zombieAttackAnimation;
	private ZombieSound zombieSound;

	public Zombie() {
		super("GameObjects.Enemies.Zombie");
		this.downImage = this.loadImage("/textures/zombieTextures/down/zombie_down.png");
		this.upImage = this.loadImage("/textures/zombieTextures/up/zombie_up.png");
		this.leftImage = this.loadImage("/textures/zombieTextures/left/zombie_left.png");
		this.rightImage = this.loadImage("/textures/zombieTextures/right/zombie_right.png");
		this.currentImage = this.downImage;
		this.zombiePostion = ZombiePostion.Zombie_Down;
		this.zombieWalkFrame = ZombieWalkFrames.Zombie_Down_Default;
		this.zombieWalkAnimation = new zombieWalkAnimation();
		this.zombieAttackAnimation = new zombieAttackAnimation(this);
		//this.zombieSound = new ZombieSound();
	}

	public void playAttackSound(){
		this.zombieSound.playAttackSound();
	}

	@Override
	public void setCurrentImage(Image currentImage) {
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
	public Image getCurrentImage() {
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

	public synchronized ZombiePostion getPostion() {
		return zombiePostion;
	}

	public void setZombieWalkFrame(ZombieWalkFrames zombieWalkFrame) {
		this.zombieWalkFrame = zombieWalkFrame;
	}

	public ZombieWalkFrames getZombieWalkFrame() {
		return zombieWalkFrame;
	}

	public void walk(){
		this.zombieWalkAnimation.walk(this);
	}

	public void attack(){
		this.zombieAttackAnimation.animateAttack();
	}
}

package Rendering.Animations.SkullAnimations;

import GameObjects.GameObjectEnums.SkullPosition;
import GameObjects.GameObjectEnums.SkullWalkFrames;
import GameObjects.Enemies.Skull;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class skullWalkAnimation {

	private BufferedImage leftFrame1;
	private BufferedImage leftFrame2;
	private BufferedImage leftFrame3;
	private BufferedImage rightFrame1;
	private BufferedImage rightFrame2;
	private BufferedImage rightFrame3;

	public skullWalkAnimation() {
		try {
			this.leftFrame1 = ImageIO.read(getClass().getResource("/textures/skullAnimation/skullTextureLeftWalk1.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			this.leftFrame2 = ImageIO.read(getClass().getResource("/textures/skullAnimation/skullTextureLeftWalk2.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			this.leftFrame3 = ImageIO.read(getClass().getResource("/textures/skullAnimation/skullTextureLeftWalk3.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			this.rightFrame1 = ImageIO.read(getClass().getResource("/textures/skullAnimation/skullTextureRightWalk1.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			this.rightFrame2 = ImageIO.read(getClass().getResource("/textures/skullAnimation/skullTextureRightWalk2.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			this.rightFrame3 = ImageIO.read(getClass().getResource("/textures/skullAnimation/skullTextureRightWalk3.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void walkRight(Skull skull){
		switch (skull.getWalkFrame()){
			case Skull_Right_Default:
				skull.setImage(this.rightFrame1);
				skull.setWalkFrame(SkullWalkFrames.Skull_Right1);
				break;
			case Skull_Right1:
				skull.setImage(this.rightFrame2);
				skull.setWalkFrame(SkullWalkFrames.Skull_Right2);
				break;
			case Skull_Right2:
				skull.setImage(this.rightFrame3);
				skull.setWalkFrame(SkullWalkFrames.Skull_Right3);
				break;
			case Skull_Right3:
				skull.setImageToDefault(skull);
				skull.setWalkFrame(SkullWalkFrames.Skull_Right_Default);
				break;
		}
	}

	private void walkLeft(Skull skull){
		switch (skull.getWalkFrame()){
			case Skull_Left_Default:
				skull.setImage(this.leftFrame1);
				skull.setWalkFrame(SkullWalkFrames.Skull_Left1);
				break;
			case Skull_Left1:
				skull.setImage(this.leftFrame2);
				skull.setWalkFrame(SkullWalkFrames.Skull_Left2);
				break;
			case Skull_Left2:
				skull.setImage(this.leftFrame3);
				skull.setWalkFrame(SkullWalkFrames.Skull_Left3);
				break;
			case Skull_Left3:
				skull.setImageToDefault(skull);
				skull.setWalkFrame(SkullWalkFrames.Skull_Left_Default);
				break;
		}
	}

	public void walk(Skull skull){
		if(skull.getPosition().equals(SkullPosition.SKULL_RIGHT)){
			this.walkRight(skull);
		} else {
			this.walkLeft(skull);
		}
	}
}

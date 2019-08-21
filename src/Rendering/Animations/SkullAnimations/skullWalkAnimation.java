package Rendering.Animations.SkullAnimations;

import GameObjects.GameObjectEnums.PositionEnums.SkullPosition;
import GameObjects.GameObjectEnums.Frames.SkullWalkFrames;
import GameObjects.Enemies.Skull;
import Helper.ImageLoader;
import javafx.scene.image.Image;

public class skullWalkAnimation {

	private Image leftFrame1;
	private Image leftFrame2;
	private Image leftFrame3;
	private Image rightFrame1;
	private Image rightFrame2;
	private Image rightFrame3;

	public skullWalkAnimation() {
		ImageLoader imageLoader = new ImageLoader();
		this.leftFrame1 = imageLoader.loadImage("/textures/skullAnimation/skullTextureLeftWalk1.png");
		this.leftFrame2 = imageLoader.loadImage("/textures/skullAnimation/skullTextureLeftWalk2.png");
		this.leftFrame3 = imageLoader.loadImage("/textures/skullAnimation/skullTextureLeftWalk3.png");
		this.rightFrame1 = imageLoader.loadImage("/textures/skullAnimation/skullTextureRightWalk1.png");
		this.rightFrame2 = imageLoader.loadImage("/textures/skullAnimation/skullTextureRightWalk2.png");
		this.rightFrame3 = imageLoader.loadImage("/textures/skullAnimation/skullTextureRightWalk3.png");
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

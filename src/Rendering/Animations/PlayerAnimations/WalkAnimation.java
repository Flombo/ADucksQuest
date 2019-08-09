package Rendering.Animations.PlayerAnimations;

import GameObjects.GameObjectEnums.PlayerWalkFrames;
import GameObjects.Player.Player;
import Helper.ImageLoader;
import javafx.scene.image.Image;

public class WalkAnimation {

	private Image leftFrame1;
	private Image leftFrame2;
	private Image rightFrame1;
	private Image rightFrame2;
	private Image upFrame1;
	private Image upFrame2;
	private Image downFrame1;
	private Image downFrame2;

	public WalkAnimation() {
		ImageLoader imageLoader = new ImageLoader();
		this.leftFrame1 = imageLoader.loadImage("/textures/playerAnimation/walkAnimation/Left/playerWalkLeftTexture1.png");
		this.leftFrame2 = imageLoader.loadImage("/textures/playerAnimation/walkAnimation/Left/playerWalkLeftTexture2.png");
		this.rightFrame1 = imageLoader.loadImage("/textures/playerAnimation/walkAnimation/Right/playerWalkRightTexture1.png");
		this.rightFrame2 = imageLoader.loadImage("/textures/playerAnimation/walkAnimation/Right/playerWalkRightTexture2.png");
		this.upFrame1 = imageLoader.loadImage("/textures/playerAnimation/walkAnimation/Up/playerWalkUpTexture1.png");
		this.upFrame2 = imageLoader.loadImage("/textures/playerAnimation/walkAnimation/Up/playerWalkUpTexture2.png");
		this.downFrame1 = imageLoader.loadImage("/textures/playerAnimation/walkAnimation/Down/playerWalkDownTexture1.png");
		this.downFrame2 = imageLoader.loadImage("/textures/playerAnimation/walkAnimation/Down/playerWalkDownTexture2.png");
	}

	private void walkUp(Player player){
		switch (player.getWalkFrame()){
			case Player_Default_Up:
				player.setCurrentImage(this.upFrame1);
				player.setWalkFrame(PlayerWalkFrames.Player_Up1);
				break;
			case Player_Up1:
				player.setCurrentImage(this.upFrame2);
				player.setWalkFrame(PlayerWalkFrames.Player_Up2);
				break;
			case Player_Up2:
				player.setImageToDefault();
				player.setWalkFrame(PlayerWalkFrames.Player_Default_Up);
				break;
		}
	}

	private void walkDown(Player player){
		switch (player.getWalkFrame()){
			case Player_Default_Down:
				player.setCurrentImage(this.downFrame1);
				player.setWalkFrame(PlayerWalkFrames.Player_Down1);
				break;
			case Player_Down1:
				player.setCurrentImage(this.downFrame2);
				player.setWalkFrame(PlayerWalkFrames.Player_Down2);
				break;
			case Player_Down2:
				player.setImageToDefault();
				player.setWalkFrame(PlayerWalkFrames.Player_Default_Down);
				break;
		}
	}

	private void walkRight(Player player){
		switch (player.getWalkFrame()){
			case Player_Default_Right:
				player.setCurrentImage(this.rightFrame1);
				player.setWalkFrame(PlayerWalkFrames.Player_Right1);
				break;
			case Player_Right1:
				player.setCurrentImage(this.rightFrame2);
				player.setWalkFrame(PlayerWalkFrames.Player_Right2);
				break;
			case Player_Right2:
				player.setImageToDefault();
				player.setWalkFrame(PlayerWalkFrames.Player_Default_Right);
				break;
		}
	}

	private void walkLeft(Player player){
		switch (player.getWalkFrame()){
			case Player_Default_Left:
				player.setCurrentImage(this.leftFrame1);
				player.setWalkFrame(PlayerWalkFrames.Player_Left1);
				break;
			case Player_Left1:
				player.setCurrentImage(this.leftFrame2);
				player.setWalkFrame(PlayerWalkFrames.Player_Left2);
				break;
			case Player_Left2:
				player.setImageToDefault();
				player.setWalkFrame(PlayerWalkFrames.Player_Default_Left);
				break;
		}
	}

	public void walk(Player player){
		switch (player.getPosition()){
			case PLAYER_RIGHT:
				this.walkRight(player);
				break;
			case PLAYER_LEFT:
				this.walkLeft(player);
				break;
			case PLAYER_UP:
				this.walkUp(player);
				break;
			case PLAYER_DOWN:
				this.walkDown(player);
				break;
		}
	}
}

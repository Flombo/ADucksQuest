package Rendering.Animations.PlayerAnimations;

import GameObjects.GameObjectEnums.PlayerWalkFrames;
import GameObjects.Player;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class WalkAnimation {

	private BufferedImage leftFrame1;
	private BufferedImage leftFrame2;
	private BufferedImage rightFrame1;
	private BufferedImage rightFrame2;
	private BufferedImage upFrame1;
	private BufferedImage upFrame2;
	private BufferedImage downFrame1;
	private BufferedImage downFrame2;

	public WalkAnimation() {
		try {
			this.leftFrame1 = ImageIO.read(getClass().getResource("/textures/playerAnimation/walkAnimation/Left/playerWalkLeftTexture1.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			this.leftFrame2 = ImageIO.read(getClass().getResource("/textures/playerAnimation/walkAnimation/Left/playerWalkLeftTexture2.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			this.rightFrame1 = ImageIO.read(getClass().getResource("/textures/playerAnimation/walkAnimation/Right/playerWalkRightTexture1.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			this.rightFrame2 = ImageIO.read(getClass().getResource("/textures/playerAnimation/walkAnimation/Right/playerWalkRightTexture2.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			this.upFrame1 = ImageIO.read(getClass().getResource("/textures/playerAnimation/walkAnimation/Up/playerWalkUpTexture1.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			this.upFrame2 = ImageIO.read(getClass().getResource("/textures/playerAnimation/walkAnimation/Up/playerWalkUpTexture2.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			this.downFrame1 = ImageIO.read(getClass().getResource("/textures/playerAnimation/walkAnimation/Down/playerWalkDownTexture1.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			this.downFrame2 = ImageIO.read(getClass().getResource("/textures/playerAnimation/walkAnimation/Down/playerWalkDownTexture2.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
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

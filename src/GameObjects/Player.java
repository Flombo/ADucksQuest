package GameObjects;

import GameObjects.GameObjectEnums.PlayerPosition;
import GameObjects.GameObjectEnums.PlayerWalkFrames;
import Rendering.Animations.PlayerAnimations.AttackedAnimation;
import Rendering.Animations.PlayerAnimations.WalkAnimation;
import Rendering.View;

import javax.imageio.ImageIO;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Field {

	private String moves = "0";
	private String score = "100";
	private String lives = "3";
	private BufferedImage currentImage;
	private BufferedImage downImage;
	private BufferedImage upImage;
	private BufferedImage leftImage;
	private BufferedImage rightImage;
	private PlayerPosition position;
	private PlayerWalkFrames walkFrame;
	private WalkAnimation walkAnimation;

	public Player(){
		super(0, 0, "GameObjects.Player");
		this.initDefaultFrames();
		this.position = PlayerPosition.PLAYER_DOWN;
		this.currentImage = this.downImage;
		this.walkFrame = PlayerWalkFrames.Player_Default_Down;
		this.walkAnimation = new WalkAnimation();
	}

	public void setImageToDefault() {
		switch (this.position) {
			case PLAYER_DOWN:
				this.currentImage = downImage;
				this.walkFrame = PlayerWalkFrames.Player_Default_Down;
				break;
			case PLAYER_UP:
				this.currentImage = upImage;
				this.walkFrame = PlayerWalkFrames.Player_Default_Up;
				break;
			case PLAYER_LEFT:
				this.currentImage = leftImage;
				this.walkFrame = PlayerWalkFrames.Player_Default_Left;
				break;
			case PLAYER_RIGHT:
				this.currentImage = rightImage;
				this.walkFrame = PlayerWalkFrames.Player_Default_Right;
				break;
		}
	}

	//init the defaultFrames for every Up,Down,Left,Right
	private void initDefaultFrames(){
		try {
			this.downImage = ImageIO.read(getClass().getResource("/textures/playerDownTexture.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			this.upImage = ImageIO.read(getClass().getResource("/textures/playerUpTexture.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			this.leftImage = ImageIO.read(getClass().getResource("/textures/playerLeftTexture.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			this.rightImage = ImageIO.read(getClass().getResource("/textures/playerRightTexture.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getMoves() {
		return moves;
	}

	public void setMoves(int moves) {
		this.moves = Integer.toString(Integer.parseInt(this.getMoves()) + moves);
		this.setScore();
	}

	public String getScore() {
		return score;
	}

	private void setScore() {
		this.score = Integer.toString(Integer.parseInt(this.score) - Integer.parseInt(this.getMoves()) / 2);
	}

	public String getLives(){
		return lives;
	}

	public void setLives(int lives) {
		this.lives = Integer.toString(Integer.parseInt(this.getLives()) + lives);
	}

	public BufferedImage getCurrentImage() {
		return currentImage;
	}

	public void setCurrentImage(BufferedImage currentImage) {
		this.currentImage = currentImage;
	}

	public PlayerPosition getPosition() {
		return position;
	}

	//gets Texture By Postion
	private BufferedImage getPlayerTextureByPosition(PlayerPosition position){
		switch (position){
			case PLAYER_DOWN:
				this.currentImage = this.downImage;
				break;
			case PLAYER_UP:
				this.currentImage = this.upImage;
				break;
			case PLAYER_LEFT:
				this.currentImage = this.leftImage;
				break;
			case PLAYER_RIGHT:
				this.currentImage = this.rightImage;
				break;
		}
		return this.currentImage;
	}

	public void attacked(View view){
		AttackedAnimation attackedAnimation = new AttackedAnimation();
		attackedAnimation.attacked(this);
		this.checkPlayersLives(view);
	}

	public void setPosition(PlayerPosition position) {
		this.position = position;
		this.setCurrentImage(this.getPlayerTextureByPosition(this.position));
	}

	public PlayerWalkFrames getWalkFrame() {
		return walkFrame;
	}

	public void setWalkFrame(PlayerWalkFrames walkFrame) {
		this.walkFrame = walkFrame;
	}

	public void walk(){
		this.walkAnimation.walk(this);
	}

	//checks if player has enough lives
	private void checkPlayersLives(View view){
		if(Integer.parseInt(this.getLives()) <= 0) {
			view.setDialog("You lose ... your moves :" + this.getMoves() + " your Score :" + this.getScore());
			view.dispatchEvent(new WindowEvent(view, WindowEvent.WINDOW_CLOSING));
		}
	}
}

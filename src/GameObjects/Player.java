package GameObjects;

import GameObjects.GameObjectEnums.PlayerPosition;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Field {

	private String moves = "0";
	private String score = "100";
	private String lives = "3";
	private BufferedImage image;
	private PlayerPosition position;

	public Player(){
		super(0, 0, "GameObjects.Player");
		try {
			this.image = ImageIO.read(getClass().getResource("/textures/playerDownTexture.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.position = PlayerPosition.PLAYER_DOWN;
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
		this.lives = Integer.toString(Integer.parseInt(this.getLives() + lives));
	}

	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}

	public PlayerPosition getPosition() {
		return position;
	}

	public void setPosition(PlayerPosition position) {
		this.position = position;
	}
}

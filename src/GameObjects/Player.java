package GameObjects;

import GameObjects.GameObjectEnums.PlayerPosition;
import Rendering.Animations.PlayerAnimations.AttackedAnimation;

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
		this.setImageToDefault();
		this.position = PlayerPosition.PLAYER_DOWN;
	}

	private void setImageToDefault(){
		try {
			this.image = ImageIO.read(getClass().getResource("/textures/playerDownTexture.png"));
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

	public BufferedImage getImage() {
		return image;
	}

	public void attacked(){
		AttackedAnimation attackedAnimation = new AttackedAnimation();
		BufferedImage[] attackedFrames = attackedAnimation.getFrames();
		long timer = System.currentTimeMillis();
		int counter = 0;
		while (counter < attackedFrames.length) {
			if (System.currentTimeMillis() - timer > 500) {
				this.setImage(attackedFrames[counter]);
				counter++;
				timer += 250;
			}
		}
		this.setImageToDefault();
	}

	private void setImage(BufferedImage image) {
		this.image = image;
	}

	public PlayerPosition getPosition() {
		return position;
	}

	private BufferedImage getPlayerTextureByPosition(PlayerPosition position){
		BufferedImage playerTexture = null;
		switch (position){
			case PLAYER_DOWN:
				try {
					playerTexture = ImageIO.read(getClass().getResource("/textures/playerDownTexture.png"));
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case PLAYER_UP:
				try {
					playerTexture = ImageIO.read(getClass().getResource("/textures/playerUpTexture.png"));
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case PLAYER_LEFT:
				try {
					playerTexture = ImageIO.read(getClass().getResource("/textures/playerLeftTexture.png"));
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case PLAYER_RIGHT:
				try {
					playerTexture = ImageIO.read(getClass().getResource("/textures/playerRightTexture.png"));
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
		}
		return playerTexture;
	}

	public void setPosition(PlayerPosition position) {
		this.position = position;
		this.setImage(this.getPlayerTextureByPosition(this.position));
	}
}

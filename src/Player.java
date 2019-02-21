import java.awt.*;

class Player extends Field {

	private String moves = "0";
	private String score = "100";
	private String lives = "3";

	Player(){
		super(0, 0, "Player", Color.BLUE, Color.WHITE);
	}

	String getMoves() {
		return moves;
	}

	void setMoves(int moves) {
		this.moves = Integer.toString(Integer.parseInt(this.getMoves()) + moves);
		this.setScore();
	}

	String getScore() {
		return score;
	}

	private void setScore() {
		this.score = Integer.toString(Integer.parseInt(this.score) - Integer.parseInt(this.getMoves()) / 2);
	}

	String getLives(){
		return lives;
	}

	public void setLives(int lives) {
		this.lives = Integer.toString(Integer.parseInt(this.getLives() + lives));
	}
}

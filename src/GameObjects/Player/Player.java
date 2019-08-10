package GameObjects.Player;

import GameObjects.Field_like_Objects.Field;
import GameObjects.GameObjectEnums.PlayerPosition;
import GameObjects.GameObjectEnums.PlayerWalkFrames;
import Rendering.Animations.PlayerAnimations.AttackedAnimation;
import Rendering.Animations.PlayerAnimations.ItemPickedAnimation;
import Rendering.Animations.PlayerAnimations.WalkAnimation;
import Rendering.View;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.image.Image;

public class Player extends Field {

	private SimpleStringProperty moves;
	private SimpleStringProperty score;
	private SimpleStringProperty lives;
	private SimpleStringProperty coins;
	private Image currentImage;
	private Image downImage;
	private Image upImage;
	private Image leftImage;
	private Image rightImage;
	private PlayerPosition position;
	private PlayerWalkFrames walkFrame;
	private WalkAnimation walkAnimation;
	private Image fieldImage;
	private AttackedAnimation attackedAnimation;
	private ItemPickedAnimation itemPickedAnimation;
	private boolean allowedToMove;

	public Player(){
		super(0, 0, "GameObjects.Player.Player");
		this.initDefaultFrames();
		this.moves = new SimpleStringProperty("0");
		this.lives = new SimpleStringProperty("3");
		this.coins = new SimpleStringProperty("0");
		this.score = new SimpleStringProperty("100");
		this.allowedToMove = true;
		this.position = PlayerPosition.PLAYER_DOWN;
		this.currentImage = this.downImage;
		this.walkFrame = PlayerWalkFrames.Player_Default_Down;
		this.walkAnimation = new WalkAnimation();
		this.attackedAnimation = new AttackedAnimation();
		this.itemPickedAnimation = new ItemPickedAnimation();
		this.fieldImage = this.loadImage("/textures/fieldTexture.png");
	}

	public boolean getAllowedToMove(){
		return this.allowedToMove;
	}

	public void setAllowedToMove(boolean allowedToMove){
		this.allowedToMove = allowedToMove;
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
		this.downImage = this.loadImage("/textures/playerDownTexture.png");
		this.upImage = this.loadImage("/textures/playerUpTexture.png");
		this.leftImage = this.loadImage("/textures/playerLeftTexture.png");
		this.rightImage = this.loadImage("/textures/playerRightTexture.png");
	}

	public SimpleStringProperty getMoves() {
		return moves;
	}

	public void setMoves(int moves) {
		this.moves.setValue(Integer.toString(Integer.parseInt(this.getMoves().getValue()) + moves));
		this.setScore();
	}

	public SimpleStringProperty getScore() {
		return score;
	}

	private void setScore() {
		this.score.setValue(Integer.toString(Integer.parseInt(this.score.getValue()) - Integer.parseInt(this.getMoves().getValue()) / 2));
	}

	public SimpleStringProperty getLives(){
		return lives;
	}

	public Runnable setLives(int lives) {
		return (()-> this.lives.setValue(Integer.toString(Integer.parseInt(this.getLives().getValue()) + lives)));
	}

	public Image getCurrentImage() {
		return currentImage;
	}

	public void setCurrentImage(Image currentImage) {
		this.currentImage = currentImage;
	}

	public PlayerPosition getPosition() {
		return position;
	}

	//gets Texture By Postion
	private Image getPlayerTextureByPosition(PlayerPosition position){
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
		this.attackedAnimation.attacked(this);
		this.checkPlayersLives(view);
	}

	public void itemPicked(){
		this.itemPickedAnimation.animateItemPicked(this);
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
	public void checkPlayersLives(View view){
		if(Integer.parseInt(this.getLives().getValue()) <= 0) {
			view.setIsRunning(false);
			view.showDeathMenu();
		}
	}

	public SimpleStringProperty getCoins() {
		return coins;
	}

	public void setCoins(int coins) {
		this.coins.setValue(Integer.toString(Integer.parseInt(this.coins.getValue()) + coins));
	}

	public Image getFieldImage() {
		return fieldImage;
	}
}

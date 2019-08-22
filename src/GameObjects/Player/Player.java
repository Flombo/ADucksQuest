package GameObjects.Player;

import GameObjects.Field_like_Objects.Field;
import GameObjects.GameObjectEnums.PositionEnums.PlayerPosition;
import GameObjects.GameObjectEnums.Frames.PlayerWalkFrames;
import Rendering.Animations.PlayerAnimations.AttackedAnimation;
import Rendering.Animations.PlayerAnimations.ItemPickedAnimation;
import Rendering.Animations.PlayerAnimations.WalkAnimation;
import Rendering.View;
import Sound.PlayerSounds.PlayerSounds;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.image.Image;

public class Player extends Field {

	private SimpleStringProperty moves;
	private SimpleStringProperty score;
	private SimpleStringProperty lives;
	private SimpleStringProperty coins;
	private SimpleStringProperty earnedCoins;
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
	private PlayerSounds playerSounds;

	public Player() {
		super(0, 0, "GameObjects.Player.Player");
		this.initDefaultFrames();
		this.moves = new SimpleStringProperty("0");
		this.lives = new SimpleStringProperty("3");
		this.coins = new SimpleStringProperty("0");
		this.score = new SimpleStringProperty("100");
		this.earnedCoins = new SimpleStringProperty("0");
		this.allowedToMove = true;
		this.position = PlayerPosition.PLAYER_DOWN;
		this.currentImage = this.downImage;
		this.walkFrame = PlayerWalkFrames.Player_Default_Down;
		this.walkAnimation = new WalkAnimation();
		this.attackedAnimation = new AttackedAnimation();
		this.itemPickedAnimation = new ItemPickedAnimation();
		this.playerSounds = new PlayerSounds();
		this.fieldImage = this.loadImage("/textures/fieldTexture.png");
	}

	//init the defaultFrames for every Up,Down,Left,Right
	private void initDefaultFrames(){
		this.downImage = this.loadImage("/textures/playerDownTexture.png");
		this.upImage = this.loadImage("/textures/playerUpTexture.png");
		this.leftImage = this.loadImage("/textures/playerLeftTexture.png");
		this.rightImage = this.loadImage("/textures/playerRightTexture.png");
	}

	//shows death menu, stops renderloop and plays death sound
	private void showDeathMenu(View view){
		this.playerSounds.playDamageSound();
		view.setIsRunningFalse();
		view.showDeathMenu();
	}

	//checks if lives are equal to zero
	private Runnable checkIfLivesAreZero(int lives){
		Runnable runnable = null;
		if(Integer.parseInt(this.getLives().getValue()) + lives == 0) {
			runnable = setLivesInCheckPlayerLivesForView(lives);
		}
		return runnable;
	}

	//checks lives for view
	private Runnable checkPlayerLivesForView(View view, int lives){
		Runnable runnable;
		if(Integer.parseInt(this.getLives().getValue()) + lives > 0) {
			runnable = setLivesInCheckPlayerLivesForView(lives);
		} else {
			runnable = this.checkIfLivesAreZero(lives);
			this.showDeathMenu(view);
		}
		return runnable;
	}

	//plays sound when player is falling down a hole
	public void playFallingSound(){
		this.playerSounds.playFallingSound();
	}

	//quak!
	public void playQuak(){
		this.playerSounds.playQuak();
	}

	//plays animation when player is attacked
	public void attacked(View view){
		this.attackedAnimation.attacked(this);
		this.checkPlayersLives(view);
	}

	//play item picked animation
	public void itemPicked(){
		this.itemPickedAnimation.animateItemPicked(this);
	}

	//plays walk animation
	public void walk(){
		this.walkAnimation.walk(this);
	}

	//checks if player has enough lives
	public void checkPlayersLives(View view){
		if(Integer.parseInt(this.getLives().getValue()) == 0) {
			view.setIsRunningFalse();
			view.showDeathMenu();
		}
	}

	//sets postion
	public void setPosition(PlayerPosition position) {
		this.position = position;
		this.setCurrentImage(this.getPlayerTextureByPosition(this.position));
	}

	//gets coins
	public SimpleStringProperty getCoins() {
		return coins;
	}

	//sets coins
	public void setCoins(int coins) {
		this.coins.setValue(Integer.toString(Integer.parseInt(this.coins.getValue()) + coins));
	}

	//gets earned coins
	public SimpleStringProperty getEarnedCoins() {
		return earnedCoins;
	}

	//calculates the earned coins
	public void setEarnedCoins() {
		this.earnedCoins.setValue(Integer.toString(Integer.parseInt(this.getScore().getValue())/15));
	}

	//sets current image to field image
	public void setCurrentImageToFieldImage() {
		this.currentImage = this.fieldImage;
	}

	//gets walkframe
	public PlayerWalkFrames getWalkFrame() {
		return walkFrame;
	}

	//sets walkframe
	public void setWalkFrame(PlayerWalkFrames walkFrame) {
		this.walkFrame = walkFrame;
	}

	//gets current image
	public Image getCurrentImage() {
		return currentImage;
	}

	//sets current image
	public void setCurrentImage(Image currentImage) {
		this.currentImage = currentImage;
	}

	//gets current position
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

	public SimpleStringProperty getMoves() {
		return moves;
	}

	public void setMoves(int moves) {
		this.moves.setValue(Integer.toString(Integer.parseInt(this.getMoves().getValue()) + moves));
	}

	public SimpleStringProperty getScore() {
		return score;
	}

	//sets score
	private void setScore() {
		this.score.setValue(this.calculateScore());
	}

	//parses score to int
	private int parseScoreToInt(){
		return Integer.parseInt(this.score.getValue());
	}

	//parses lives to int
	private int parseLivesToInt(){
		return Integer.parseInt(this.getLives().getValue());
	}

	//parses moves to int
	private int parseMovesToInt(){
		return Integer.parseInt(this.getMoves().getValue());
	}

	//parses coins to int
	private int parseCoinsToInt(){
		return Integer.parseInt(this.getCoins().getValue());
	}

	//calculates score
	private String calculateScore(){
		return Integer.toString(
				this.parseScoreToInt()
						- this.parseMovesToInt()
						+ this.parseLivesToInt() * 5
						+ this.parseCoinsToInt() * 10
		);
	}

	//returns lives
	public SimpleStringProperty getLives(){
		return lives;
	}

	//sets lives
	public Runnable setLives(int lives, View view) {
		Runnable runnable;
		runnable = this.checkPlayerLivesForView(view, lives);
		this.setScore();
		return runnable;
	}

	//sets lives for View
	private Runnable setLivesInCheckPlayerLivesForView(int lives){
		return (() -> this.lives.setValue(Integer.toString(Integer.parseInt(this.getLives().getValue()) + lives)));
	}

	public boolean getAllowedToMove(){
		return this.allowedToMove;
	}

	public void setAllowedToMove(boolean allowedToMove){
		this.allowedToMove = allowedToMove;
	}

	//set image to default image
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
}

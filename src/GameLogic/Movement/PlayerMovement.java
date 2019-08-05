package GameLogic.Movement;

import GameLogic.Movement.MovementHelper.CollisionHelper.playerCollisionChecker;
import GameLogic.Movement.MovementHelper.playerFrameChecker;
import GameObjects.Field_like_Objects.Field;
import GameObjects.GameObjectEnums.PlayerPosition;
import GameObjects.Player.Player;
import Helper.consolePrinter;
import Rendering.View;
import java.awt.event.KeyEvent;

public class PlayerMovement {

	private View view;
	private consolePrinter consolePrinter;
	private int yDimension;
	private int xDimension;
	private Field[][] fields;
	private Player player;
	private playerFrameChecker playerFrameChecker;

	public PlayerMovement(consolePrinter consolePrinter , int xDimension, int yDimension, View view, Field[][] fields, Player player){
		this.consolePrinter = consolePrinter;
		this.xDimension = xDimension;
		this.yDimension = yDimension;
		this.view = view;
		this.fields = fields;
		this.player = player;
		this.playerFrameChecker = new playerFrameChecker();
	}

	//keylistener for player-movement
	public void changePlayerPos(KeyEvent event){
		int keyCode = event.getKeyCode();

		if (this.player.getAllowedToMove()) {
			switch ( keyCode ) {
				case KeyEvent.VK_DOWN:
				case KeyEvent.VK_S:
					this.movePlayer(true, 1);
					this.player.setPosition(PlayerPosition.PLAYER_DOWN);
					this.playerFrameChecker.checkWalkFrameDown(this.player);
					break;
				case KeyEvent.VK_UP:
				case KeyEvent.VK_W:
					this.movePlayer(true, -1);
					this.player.setPosition(PlayerPosition.PLAYER_UP);
					this.playerFrameChecker.checkWalkFrameUp(this.player);
					break;
				case KeyEvent.VK_RIGHT:
				case KeyEvent.VK_D:
					this.movePlayer(false, 1);
					this.player.setPosition(PlayerPosition.PLAYER_RIGHT);
					this.playerFrameChecker.checkWalkFrameRight(this.player);
					break;
				case KeyEvent.VK_LEFT:
				case KeyEvent.VK_A:
					this.movePlayer(false, -1);
					this.player.setPosition(PlayerPosition.PLAYER_LEFT);
					this.playerFrameChecker.checkWalkFrameLeft(this.player);
					break;
				case KeyEvent.VK_ESCAPE:
					this.view.showGameMenu();
					break;

			}
		}

	}

	//move player pos
	private void movePlayerPos() {
		this.player.setMoves(1);
		this.fields[this.player.getXPos()][this.player.getYPos()] = this.player;
	}

	//inits playerCollisionChecker
	private playerCollisionChecker initPlayerCollisionChecker(boolean upDown, int newPos){
		playerCollisionChecker playerCollisionChecker;
		if (upDown) {
			playerCollisionChecker = new playerCollisionChecker(
					this.player.getXPos(),
					this.player.getYPos() + newPos,
					fields,
					player,
					this.view,
					true,
					xDimension,
					yDimension,
					newPos
			);
		} else {
			playerCollisionChecker = new playerCollisionChecker(
					this.player.getXPos() + newPos,
					this.player.getYPos(),
					fields,
					player,
					this.view,
					false,
					xDimension,
					yDimension,
					newPos
			);
		}
		return playerCollisionChecker;
	}

	//method that checks if down/up is pressed and if player reached the end of field
	private void movePlayer(boolean upDown,int newPos) {
		if(upDown) {
			if (this.player.getYPos() + newPos >= 0 && this.player.getYPos() + newPos < yDimension) {
				this.movePlayerYDirection(newPos);
			}
		}else {
			if (this.player.getXPos() + newPos >= 0 && this.player.getXPos() + newPos < xDimension) {
				this.movePlayerXDirection(newPos);
			}
		}
//		consolePrinter.printAllFields(yDimension, xDimension, fields);
	}

	//moves player in x direction when its allowed
	private void movePlayerXDirection(int newPos){
		playerCollisionChecker playerCollisionChecker = this.initPlayerCollisionChecker(false, newPos);
		if(playerCollisionChecker.checkNextGameObject()) {
			this.fields[this.player.getXPos()][this.player.getYPos()] = this.createFieldOnOldPlayerPos();
			this.player.setX((this.player.getXPos() + newPos) * 30);
			this.movePlayerPos();
		}
		this.player.checkPlayersLives(this.view);
	}

	//moves player in y direction when its allowed
	private void movePlayerYDirection(int newPos){
		playerCollisionChecker playerCollisionChecker = this.initPlayerCollisionChecker(true, newPos);
		if(playerCollisionChecker.checkNextGameObject()) {
			this.fields[this.player.getXPos()][this.player.getYPos()] = this.createFieldOnOldPlayerPos();
			this.player.setY((this.player.getYPos() + newPos) * 30);
			this.movePlayerPos();
		}
		this.player.checkPlayersLives(this.view);
	}

	//creates field on old player postion
	private Field createFieldOnOldPlayerPos() {
		return new Field(this.player.getXPos() * 30, this.player.getYPos() * 30, "GameObjects.Field_like_Objects.Field");
	}
}
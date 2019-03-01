package GameLogic;

import GameObjects.Field;
import GameObjects.GameObjectEnums.PlayerPosition;
import GameObjects.Obstacle;
import GameObjects.Player;
import GameObjects.Target;
import Helper.consolePrinter;
import Rendering.View;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;

public class GameInit {

    private Field[][] fields;
    private int xDimension;
    private int yDimension;
    private int playerPosX;
    private int playerPosY;
    private Player player;
    private View view;
    private consolePrinter printer;
    private int amount;

    //Constructor takes height and width for JFrame
    public GameInit(int xDimension, int yDimension, int amount){
        this.xDimension = xDimension;
        this.yDimension = yDimension;
        this.fields = new Field[xDimension][yDimension];
        this.playerPosX = this.randomPos();
        this.playerPosY = this.randomPos();
        this.printer = new consolePrinter();
        this.amount = amount;
    }

    private void setPlayerPosY(int playerPosY) {
        this.playerPosY = playerPosY;
    }

    private void setPlayerPosX(int playerPosX) {
        this.playerPosX = playerPosX;
    }

    public void init(){
        this.initFields();
        this.initTarget();
        this.initPlayer();
        this.initObstacle(this.amount);
        printer.printAllFields(yDimension, xDimension, fields);
        this.initView();
        this.initMovement();
    }

    private void initView(){
        this.view = new View(this.fields);
    }

    private void initMovement(){
        this.view.start();
        this.view.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                changePlayerPos(e);
            }
        });
    }

    //Create Fields
    private void initFields(){
        for(int i = 0; i < this.yDimension; i++){
            for(int j = 0; j < this.xDimension; j++){
                this.fields[j][i] = new Field(j * 30, i * 30, "GameObjects.Field");
            }
        }
    }

    //Returns random Position
    private int randomPos(){
        return (int) ( 1 * Math.random() * this.fields.length );
    }

    //Initialize GameObjects.Target
    private void initTarget(){
        int targetPosX = this.randomPos();
        int targetPosY = this.randomPos();

        Target target = new Target();
        target.setX(targetPosX * 30);
        target.setY(targetPosY * 30);
        this.fields[targetPosX][targetPosY] = target;
    }

    //Initialize GameObjects.Player
    private void initPlayer() {

        //generate new position until it isn´t target´s position
        while(this.fields[this.playerPosX][this.playerPosY] instanceof Target){
            this.playerPosX = this.randomPos();
            this.playerPosY = this.randomPos();
        }
        this.controllPlayer();
    }

    private void initObstacle(int amount){
        int randomX;
        int randomY;
        for(int i = 0; i <= amount; i++){
            randomX = this.randomPos();
            randomY = this.randomPos();

            while(this.fields[randomX][randomY] instanceof Target || this.fields[randomX][randomY] instanceof Player){
                randomX = this.randomPos();
                randomY = this.randomPos();
            }
            Obstacle obstacle = new Obstacle();
            obstacle.setX(randomX * 30);
            obstacle.setY(randomY * 30);

            this.fields[randomX][randomY] = obstacle;
        }
    }

    private void controllPlayer(){
        if(this.player != null) {
            if(this.fields[this.playerPosX][this.playerPosY] instanceof Target){
                this.view.setDialog(
                        "Gewonnen deine Züge :" + " "
                                + this.player.getMoves()
                                + " Score : "  + " "
                                + this.player.getScore()
                                + " Leben : " + this.player.getLives()
                );
                this.view.dispatchEvent(new WindowEvent(this.view, WindowEvent.WINDOW_CLOSING));
                this.player.setMoves(0);
            }

            this.player.setY(this.playerPosY * 30);
            this.player.setX(this.playerPosX * 30);

        }else {
            this.player = new Player();
            this.player.setX(this.playerPosX * 30);
            this.player.setY(this.playerPosY * 30);
        }
        this.fields[this.playerPosX][this.playerPosY] = this.player;
    }

    //keylistener for player-movement
    private void changePlayerPos(KeyEvent event){
        int keyCode = event.getKeyCode();

        switch ( keyCode ){
            case KeyEvent.VK_DOWN:
                this.movePlayer(true, 1);
                this.player.setPosition(PlayerPosition.PLAYER_DOWN);
                break;
            case KeyEvent.VK_S:
                this.movePlayer(true, 1);
                this.player.setPosition(PlayerPosition.PLAYER_DOWN);
                break;
            case KeyEvent.VK_UP:
                this.movePlayer(true, -1);
                this.player.setPosition(PlayerPosition.PLAYER_UP);
                break;
            case KeyEvent.VK_W:
                this.movePlayer(true, -1);
                this.player.setPosition(PlayerPosition.PLAYER_UP);
                break;
            case KeyEvent.VK_RIGHT:
                this.movePlayer(false, 1);
                this.player.setPosition(PlayerPosition.PLAYER_RIGHT);
                break;
            case KeyEvent.VK_D:
                this.movePlayer(false, 1);
                this.player.setPosition(PlayerPosition.PLAYER_RIGHT);
                break;
            case KeyEvent.VK_LEFT:
                this.movePlayer(false, -1);
                this.player.setPosition(PlayerPosition.PLAYER_LEFT);
                break;
            case KeyEvent.VK_A:
                this.movePlayer(false, -1);
                this.player.setPosition(PlayerPosition.PLAYER_LEFT);
                break;
            default:
                this.view.setDialog("You have to press the arrow keys or WASD!");
                break;

        }

    }

    //move player pos
    private void movePlayerPos() {
        this.player.setMoves(1);
        this.controllPlayer();
    }

    //method that checks if down/up is pressed and if player reached the end of field
    private void movePlayer(boolean upDown,int newPos) {
        if(upDown) {
            if (this.playerPosY + newPos >= 0 && this.playerPosY + newPos < yDimension) {
                if(!(this.fields[this.playerPosX][this.playerPosY + newPos] instanceof Obstacle)) {
                    this.fields[this.playerPosX][this.playerPosY] = new Field(this.playerPosX * 30, this.playerPosY * 30, "GameObjects.Field");
                    this.setPlayerPosY(this.playerPosY + newPos);
                    this.movePlayerPos();
                }
            }
        }else {
            if (this.playerPosX + newPos >= 0 && this.playerPosX + newPos < xDimension) {
                if(!(this.fields[this.playerPosX + newPos][this.playerPosY] instanceof Obstacle)) {
                    this.fields[this.playerPosX][this.playerPosY] = new Field(this.playerPosX * 30, this.playerPosY * 30, "GameObjects.Field");
                    this.setPlayerPosX(this.playerPosX + newPos);
                    this.movePlayerPos();
                }
            }
        }
        printer.printAllFields(yDimension, xDimension, fields);
    }
}
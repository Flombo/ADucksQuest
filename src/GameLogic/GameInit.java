package GameLogic;

import GameLogic.Movement.PlayerMovement;
import GameLogic.Movement.SkullMovement;
import GameObjects.*;
import Helper.consolePrinter;
import Rendering.Animations.CoinAnimations.CoinFlip;
import Rendering.View;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GameInit {

    private Field[][] fields;
    private int xDimension;
    private int yDimension;
    private int playerPosX;
    private int playerPosY;
    private Player player;
    private Skull[] skulls;
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

    public void init(){
        this.initFields();
        this.initTarget();
        this.initPlayer();
        this.initObstacle(this.amount);
        this.initHoles();
        this.initSkulls(2);
        this.initCoins();

        printer.printAllFields(yDimension, xDimension, fields);
        this.initView();
		PlayerMovement playerMovement = new PlayerMovement(
				this.printer,
				this.xDimension,
				this.yDimension,
				this.view,
				this.fields,
				this.playerPosY,
				this.playerPosX,
				this.player
		);
        this.initMovement(playerMovement, this.player);
		this.initSkullMovement();
		this.initFlipCoins();
    }

    private void initView(){
        this.view = new View(this.fields);
		this.view.start();
    }

    private void initMovement(PlayerMovement playerMovement, Player player){
        this.view.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                playerMovement.changePlayerPos(e);
                player.walk();
            }
        });
    }

    private void initFlipCoins(){
		for (Field[] fields: this.fields) {
			for (Field field: fields) {
				if(field.getName().equals("GameObjects.Coin")){
					((Coin) field).flip();
				}
			}
		}
	}

    private void initCoins(){
    	for(int i = 0; i < 3; i++) {
			Coin coin = new Coin();
			this.setGameObjectPosition(coin);
		}
	}

    private void initSkullMovement(){
    	for (Skull skull : this.skulls) {
			SkullMovement skullMovement = new SkullMovement(this.fields, skull, this.xDimension, this.player, this.view);
			skullMovement.initMovement();
		}
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
		this.player = new Player();
		this.player.setX(this.playerPosX * 30);
		this.player.setY(this.playerPosY * 30);
		this.fields[this.playerPosX][this.playerPosY] = this.player;
    }

    //set random GameObjectPos
    private void setGameObjectPosition(Field field){
		Map<String, Integer> map = this.checkFieldPostions();
		int randomX = map.get("randomX");
		int randomY = map.get("randomY");
		field.setX(randomX * 30);
		field.setY(randomY * 30);

		this.fields[randomX][randomY] = field;
	}

	// init Obstacle
    private void initObstacle(int amount){
        for(int i = 0; i <= amount; i++){
            Obstacle obstacle = new Obstacle();
			this.setGameObjectPosition(obstacle);
        }
    }

    //init Hole
    private void initHoles(){
		for(int i = 0; i <= 3; i++){
			Hole hole = new Hole();
			this.setGameObjectPosition(hole);
		}
	}

	private void initSkulls(int amount){
		this.skulls = new Skull[amount];
    	for (int i = 0; i < amount; i++) {
			Skull skull = new Skull();
			this.setGameObjectPosition(skull);
			this.skulls[i] = skull;
		}
	}

//	private Skull[] getSkull(){
//    	Skull skull = null;
//		ArrayList<Skull> skulls = new ArrayList<>();
//    	for(int y = 0; y < this.yDimension; y++){
//    		for(int x = 0; x < this.xDimension; x++){
//				if(this.fields[x][y] instanceof Skull){
//					skulls.add((Skull) this.fields[x][y]);
//				}
//			}
//		}
//    	skulls.toArray();
//    	return skulls;
//	}

	//checks if randomPos of Object isn´t taken by target or Player
	private Map<String, Integer> checkFieldPostions(){
		Map<String, Integer> map = new HashMap<>();
		int randomX = this.randomPos();
		int randomY = this.randomPos();

		while(this.fields[randomX][randomY] instanceof Target || this.fields[randomX][randomY] instanceof Player){
			randomX = this.randomPos();
			randomY = this.randomPos();
		}
		map.put("randomX", randomX);
		map.put( "randomY", randomY);
		return map;
	}

}
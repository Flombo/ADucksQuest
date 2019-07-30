package GameLogic;

import GameLogic.Movement.PlayerMovement;
import GameLogic.Movement.SkullMovement;
import GameLogic.Movement.ZombieMovement;
import GameObjects.Collectibles.Coin;
import GameObjects.Collectibles.Heart;
import GameObjects.Enemies.Skull;
import GameObjects.Enemies.Zombie;
import GameObjects.Field_like_Objects.Field;
import GameObjects.Obstacles.Chest;
import GameObjects.Obstacles.Hole;
import GameObjects.Obstacles.Obstacle;
import GameObjects.Player.Player;
import GameObjects.Target.Target;
import Helper.consolePrinter;
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
    private Player player;
    private Skull[] skulls;
    private Zombie[] zombies;
    private PlayerMovement playerMovement;
    private ArrayList <ZombieMovement> zombieMovements;
    private ArrayList <SkullMovement> skullMovements;
    private ArrayList <Coin> coins;
    private ArrayList <Heart> hearts;
    private KeyAdapter keyAdapter;
    private View view;
    private consolePrinter printer;
    private int amount;

	//Constructor takes height and width for JFrame
    public GameInit(int xDimension, int yDimension, int amount){
        this.xDimension = xDimension;
        this.yDimension = yDimension;
        this.printer = new consolePrinter();
        this.amount = amount;
        this.initKeyAdapter();
    }

    //resumes collectible animations
    public void resumeCollectibleAnimations(){
		this.resumeCoinAnimation();
		this.resumeHeartAnimation();
	}

	private void resumeHeartAnimation(){
    	for (Heart heart : this.hearts){
			heart.startAnimation();
		}
	}

	private void resumeCoinAnimation(){
		for (Coin coin : this.coins) {
			coin.startAnimation();
		}
	}

    //pauses collectible animation
    public void switchCollectiblesAnimation(boolean allowedToAnimate){
		this.switchHeartAnimation(allowedToAnimate);
		this.switchCoinAnimation(allowedToAnimate);
	}

	private void switchCoinAnimation(boolean allowedToAnimate){
		for (Coin coin : this.coins){
			coin.setAllowedToFlip(allowedToAnimate);
		}
	}

	private void switchHeartAnimation(boolean allowedToAnimate){
    	for (Heart heart : this.hearts){
    		heart.setAllowedToGrow(allowedToAnimate);
		}
	}

    //pauses Enemy movement
    public void switchEnemyMovement(boolean allowedToMove){
		this.switchSkullMovement(allowedToMove);
		this.switchZombieMovement(allowedToMove);
	}

	//pauses zombie movement
	private void switchZombieMovement(boolean allowedToMove){
		for (ZombieMovement zombieMovement : this.zombieMovements){
			zombieMovement.setAllowedToMove(allowedToMove);
		}
	}

	//pauses skull movement
	private void switchSkullMovement(boolean allowedToMove){
    	for (SkullMovement skullMovement : this.skullMovements){
    		skullMovement.setAllowedToMove(allowedToMove);
		}
	}

    //removes KeyListener from view
    private void removeKeyAdapter(){
    	this.view.removeKeyListener(this.keyAdapter);
	}

	//inits KeyAdapter
    private void initKeyAdapter(){
		this.keyAdapter = new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				super.keyReleased(e);
				playerMovement.changePlayerPos(e);
				player.walk();
			}
		};
	}

    //initialize Level
    public Field[][] initLevel(){
		this.initFields();
		this.initTarget();
		this.initPlayer();
		this.initObstacle(this.amount);
		this.initHoles();
		this.initSkulls();
		this.initZombies();
		this.initCoins();
		this.initHearts();
		this.initChest();
		printer.printAllFields(yDimension, xDimension, fields);
		return this.fields;
	}

	//inits view and shows mainMenu
    public void initView(){
        this.view = new View(this);
    }

    //inits enemyMovement
    public void initEnemyMovement(){
		this.initSkullMovement();
		this.initZombieMovement();
    }

    public void intitPlayerMovement(){
		this.playerMovement = new PlayerMovement(
				this.printer,
				this.xDimension,
				this.yDimension,
				this.view,
				this.fields,
				this.player
		);
		if(this.keyAdapter != null){
			this.removeKeyAdapter();
			this.initKeyAdapter();
		}
		view.addKeyListener(this.keyAdapter);
		view.requestFocusInWindow();
	}

	//gets certain gameObject by name
	private ArrayList<Field> getCertainGameObjectType(String name){
		ArrayList<Field> gameObjects = new ArrayList<>();
    	for (Field[] fields : this.fields) {
    		for ( Field field : fields){
    			if(field.getName().equals(name)){
					gameObjects.add(field);
				}
			}
		}
    	return gameObjects;
	}

	private void initChest(){
    	Chest chest = new Chest();
    	this.setGameObjectPosition(chest);
	}

	private void initHearts(){
    	this.hearts = new ArrayList<>();
    	for(int i = 0; i < 3; i++){
    		Heart heart = new Heart();
    		this.setGameObjectPosition(heart);
    		this.hearts.add(heart);
		}
	}

    private void initCoins(){
    	this.coins = new ArrayList<>();
    	for(int i = 0; i < 3; i++) {
			Coin coin = new Coin();
			this.setGameObjectPosition(coin);
			this.coins.add(coin);
		}
	}

	private void initZombieMovement(){
    	this.zombieMovements = new ArrayList<>();
    	for (Zombie zombie : this.zombies) {
			ZombieMovement zombieMovement = new ZombieMovement(
					this.fields,
					zombie,
					this.xDimension,
					this.yDimension,
					this.player,
					this.view
			);
			zombieMovement.initMovement();
			this.zombieMovements.add(zombieMovement);
		}
	}

	//Initialize SkullMovement
    private void initSkullMovement(){
    	this.skullMovements = new ArrayList<>();
    	for (Skull skull : this.skulls) {
			SkullMovement skullMovement = new SkullMovement(this.fields, skull, this.xDimension, this.player, this.view);
			skullMovement.initMovement();
			this.skullMovements.add(skullMovement);
		}
	}

    //Create Fields
    private void initFields(){
		this.fields = new Field[xDimension][yDimension];
        for(int i = 0; i < this.yDimension; i++){
            for(int j = 0; j < this.xDimension; j++){
                this.fields[j][i] = new Field(j * 30, i * 30, "GameObjects.Field_like_Objects.Field");
            }
        }
    }

    //Returns random Position
    private int randomPos(){
        return (int) ( 1 * Math.random() * this.fields.length );
    }

    //Initialize GameObjects.Target.Target
    private void initTarget(){
        int targetPosX = this.randomPos();
        int targetPosY = this.randomPos();

        Target target = new Target();
        target.setX(targetPosX * 30);
        target.setY(targetPosY * 30);
        this.fields[targetPosX][targetPosY] = target;
    }

    //Initialize GameObjects.Player.Player
    private void initPlayer() {
		this.player = new Player();
		this.player.setX(this.randomPos() * 30);
		this.player.setY(this.randomPos() * 30);
		this.fields[this.player.getXPos()][this.player.getYPos()] = this.player;

        //generate new position until it isn´t target´s position
        while(this.fields[this.player.getXPos()][this.player.getYPos()] instanceof Target){
            this.player.setX(this.randomPos() * 30);
            this.player.setY(this.randomPos() * 30);
        }
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

	private void initZombies(){
    	this.zombies = new Zombie[2];
    	for (int i = 0; i < 2; i++){
    		Zombie zombie = new Zombie();
    		this.setGameObjectPosition(zombie);
    		this.zombies[i] = zombie;
		}
	}

	private void initSkulls(){
		this.skulls = new Skull[2];
    	for (int i = 0; i < 2; i++) {
			Skull skull = new Skull();
			this.setGameObjectPosition(skull);
			this.skulls[i] = skull;
		}
	}

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
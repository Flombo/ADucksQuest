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
import javafx.scene.input.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class GameInit {

    private Field[][] fields;
    private int xDimension;
    private int yDimension;
    private Player player;
    private ArrayList<Skull> skulls = new ArrayList<>();
    private ArrayList<Zombie> zombies = new ArrayList<>();
    private PlayerMovement playerMovement;
    private ArrayList <ZombieMovement> zombieMovements = new ArrayList<>();
    private ArrayList <SkullMovement> skullMovements = new ArrayList<>();
    private ArrayList <Coin> coins = new ArrayList<>();
    private ArrayList <Heart> hearts = new ArrayList<>();
    private View view;
    private consolePrinter printer;

	//Constructor takes height and width for JFrame
    public GameInit(View view){
        this.printer = new consolePrinter();
        this.view = view;
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
    public Runnable switchCollectiblesAnimation(boolean allowedToAnimate){
    	return (()->{
			this.switchHeartAnimation(allowedToAnimate);
			this.switchCoinAnimation(allowedToAnimate);
		});
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
    public synchronized Runnable switchEnemyMovement(boolean allowedToMove){
    	return (()->{
    		if(!this.zombieMovements.isEmpty()) {
				this.switchZombieMovement(allowedToMove);
			}
    		if(!this.skullMovements.isEmpty()){
				this.switchSkullMovement(allowedToMove);
			}
		});
	}

	//pauses zombie movement
	private synchronized void switchZombieMovement(boolean allowedToMove){
		for (ZombieMovement zombieMovement : this.zombieMovements){
			zombieMovement.setIsRunning(allowedToMove);
		}
	}

	//pauses skull movement
	private synchronized void switchSkullMovement(boolean allowedToMove){
    	for (SkullMovement skullMovement : this.skullMovements){
    		skullMovement.setIsRunning(allowedToMove);
		}
    	this.skullMovements = new ArrayList<>();
    	this.skulls = new ArrayList<>();
	}

	//inits KeyHandler
	public void handle(KeyEvent e) {
		playerMovement.changePlayerPos(e);
		player.walk();
	}

    //initialize Level
    public Field[][] initLevel(String levelcode, int xDimension, int yDimension){
    	this.xDimension = xDimension;
    	this.yDimension = yDimension;
    	this.decodeLevelCode(levelcode);
		//printer.printAllFields(yDimension, xDimension, fields);
		return this.fields;
	}

	private void decodeLevelCode(String levelCode){
		ArrayList<Field> tmp = new ArrayList<>();
		StringTokenizer tokenizer = new StringTokenizer(levelCode, "/");
		while (tokenizer.hasMoreTokens()){
			switch (tokenizer.nextToken()){
				case "p":
					this.player = new Player();
					tmp.add(this.player);
					break;
				case "f":
					tmp.add(new Field("GameObjects.Field_like_Objects.Field"));
					break;
				case "h":
					tmp.add(new Hole());
					break;
				case "o":
					tmp.add(new Obstacle());
					break;
				case "t":
					tmp.add(new Target());
					break;
				case "sk":
					Skull skull = new Skull();
					this.skulls.add(skull);
					tmp.add(skull);
					break;
				case "c":
					Coin coin = new Coin();
					this.coins.add(coin);
					tmp.add(coin);
					break;
				case "ch":
					tmp.add(new Chest());
					break;
				case "ht":
					Heart heart = new Heart();
					this.hearts.add(heart);
					tmp.add(heart);
					break;
				case "z":
					Zombie zombie = new Zombie();
					this.zombies.add(zombie);
					tmp.add(zombie);
					break;
			}
		}
		this.buildLevel(tmp);
	}

	private void buildLevel(ArrayList<Field> tmp){
    	int index = 0;
    	this.fields = new Field[xDimension][yDimension];
    	for(int y = 0; y < yDimension; y++){
    		for(int x = 0; x < xDimension; x++){
				this.fields[x][y] = tmp.get(index);
				this.fields[x][y].setY(y * 30);
				this.fields[x][y].setX(x * 30);
				index++;
			}
		}
	}

	public void resumeEnemyMovement(){
    	this.resumeSkullMovement();
    	this.resumeZombieMovement();
	}

	private void resumeSkullMovement(){
		for (Skull skull : this.skulls) {
			SkullMovement skullMovement = new SkullMovement(this.fields, skull, this.xDimension, this.player, this.view);
			skullMovement.initMovement();
			this.skullMovements.add(skullMovement);
		}
	}

	private void resumeZombieMovement(){
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

    //inits enemyMovement
    public synchronized void initEnemyMovement(){
		this.initSkullMovement();
		this.initZombieMovement();
    }

    public void initPlayerMovement(){
    	if(this.playerMovement == null) {
			this.playerMovement = new PlayerMovement(
					this.printer,
					this.xDimension,
					this.yDimension,
					this.view,
					this.fields,
					this.player
			);
		}
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

	private void initZombieMovement(){
        if(!this.zombies.isEmpty() && this.zombieMovements.isEmpty()) {
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
	}

	//Initialize SkullMovement
    private void initSkullMovement(){
        if(!this.skulls.isEmpty() && this.skullMovements.isEmpty()) {
            for (Skull skull : this.skulls) {
                SkullMovement skullMovement = new SkullMovement(this.fields, skull, this.xDimension, this.player, this.view);
                skullMovement.initMovement();
                this.skullMovements.add(skullMovement);
            }
        }
	}

	public void resetRessources() {
    	if(!this.zombies.isEmpty() && !this.zombieMovements.isEmpty()) {
			this.zombieMovements = new ArrayList<>();
			this.zombies = new ArrayList<>();
		}
    	if(!this.skulls.isEmpty() && !this.skullMovements.isEmpty()){
    		this.skulls = new ArrayList<>();
    		this.skullMovements = new ArrayList<>();
		}
		this.playerMovement = null;
    	this.fields = null;
    	this.player = null;
    	this.hearts = new ArrayList<>();
    	this.coins = new ArrayList<>();
	}
}
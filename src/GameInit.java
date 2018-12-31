import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

class GameInit {

    private Field[][] fields;
    private int xDimension;
    private int yDimension;
    private Target target;
    private int playerPosX;
    private int playerPosY;
    private static final Color fillColorField = Color.lightGray;
    private static final Color strokeColorField = Color.white;
    private View view;
    private consolePrinter printer;

    //Constructor takes height and width for JFrame
    GameInit(int xDimension, int yDimension){
        this.xDimension = xDimension;
        this.yDimension = yDimension;
        this.fields = new Field[xDimension][yDimension];
        this.playerPosX = this.randomPos();
        this.playerPosY = this.randomPos();
        this.printer = new consolePrinter();
    }

    private void setPlayerPosY(int playerPosY) {
        this.playerPosY = playerPosY;
    }

    private void setPlayerPosX(int playerPosX) {
        this.playerPosX = playerPosX;
    }

    void init(){
        this.initFields();
        this.initTarget();
        this.initPlayer();
        printer.printAllFields(yDimension, xDimension, fields);
        this.initView();
        this.initMovement();
    }

    private void initView(){
        this.view = new View(this.fields);
    }

    private void initMovement(){
        this.view.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                changePlayerPos(e);
                view.repaint();
            }
        });
    }

    //Create Fields
    private void initFields(){
        for(int i = 0; i < this.yDimension; i++){
            for(int j = 0; j < this.xDimension; j++){
                this.fields[j][i] = new Field(j * 30, i * 30, "Field", fillColorField, strokeColorField);
            }
        }
    }

    //Returns random Position
    private int randomPos(){
        return (int) ( 1 * Math.random() * this.fields.length );
    }

    //Initialize Target
    private void initTarget(){
        int targetPosX = this.randomPos();
        int targetPosY = this.randomPos();

        this.target = new Target();
        this.target.setX(targetPosX * 30);
        this.target.setY(targetPosY * 30);
        this.fields[targetPosX][targetPosY] = this.target;
    }

    //Initialize Player
    private void initPlayer() {

        //generate new position until it isn´t target´s position
        while(this.fields[this.playerPosX][this.playerPosY].equals(this.target)){
            this.playerPosX = this.randomPos();
            this.playerPosY = this.randomPos();
        }
        this.createPlayer();
    }

    private void createPlayer(){
        Player player = new Player();
        player.setX(this.playerPosX * 30);
        player.setY(this.playerPosY * 30);

        this.fields[this.playerPosX][this.playerPosY] = player;
    }

    //move player pos
    private void movePlayerPos() {
        this.createPlayer();
    }

    //keylistener for player-movement
    private void changePlayerPos(KeyEvent event){
        int keyCode = event.getKeyCode();

        switch ( keyCode ){
            case KeyEvent.VK_DOWN:
                this.movePlayer(true, -1);
                System.out.println("down");
                printer.printAllFields(yDimension, xDimension, fields);
                break;
            case KeyEvent.VK_UP:
                this.movePlayer(true, 1);
                System.out.println("up");
                printer.printAllFields(yDimension, xDimension, fields);
                break;
            case KeyEvent.VK_RIGHT:
                this.movePlayer(false, 1);
                System.out.println("right");
                printer.printAllFields(yDimension, xDimension, fields);
                break;
            case KeyEvent.VK_LEFT:
                this.movePlayer(false, -1);
                System.out.println("left");
                printer.printAllFields(yDimension, xDimension, fields);
                break;
            default:
                System.out.println("You have to press the arrow keys!");
                printer.printAllFields(yDimension, xDimension, fields);
                break;

        }

    }

    //method that checks if down/up is pressed and if player reached the end of field
    private void movePlayer(boolean upDown,int newPos) {
        if(upDown) {
            if (this.playerPosY + newPos >= 0 && this.playerPosY + newPos < yDimension) {
                this.fields[this.playerPosX][this.playerPosY] = new Field(this.playerPosX * 30, this.playerPosY * 30, "Field", fillColorField, strokeColorField);
                this.setPlayerPosY(this.playerPosY + newPos);
                this.movePlayerPos();
            } else {
                System.out.println("You reached the end.");
            }
        }else {
            if (this.playerPosX + newPos >= 0 && this.playerPosX + newPos < xDimension) {
                this.fields[this.playerPosX][this.playerPosY] = new Field(this.playerPosX * 30, this.playerPosY * 30, "Field", fillColorField, strokeColorField);
                this.setPlayerPosX(this.playerPosX + newPos);
                this.movePlayerPos();
            } else {
                System.out.println("You reached the end.");
            }
        }
        printer.printAllFields(yDimension, xDimension, fields);
    }
}
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.event.KeyEvent;

public class GameInit {

    private int height;
    private int width;
    private Field[][] fields;
    private int xDimension;
    private int yDimension;
    private Target target;
    private int playerPosX;
    private int playerPosY;

    //Constructor takes height and width for JFrame
    GameInit(int height, int width, int xDimension, int yDimension){
        this.height = height;
        this.width = width;
        this.xDimension = xDimension;
        this.yDimension = yDimension;
        this.fields = new Field[xDimension][yDimension];
        this.playerPosX = this.randomPos();
        this.playerPosY = this.randomPos();
    }

    public void setPlayerPosX(int x) {
        this.playerPosX = x;

    }

    public void setPlayerPosY(int y)
    {
        this.playerPosY = y;
    }

    public int getHeight()
    {
        return height;
    }

    public void setHeight(int height)
    {
        this.height = height;
    }

    public int getWidth() {

        return width;
    }

    public void setWidth(int width) {

        this.width = width;
    }

    //Create Fields
    void initFields(){
        for(int i = 0; i < this.yDimension; i++){
            for(int j = 0; j < this.xDimension; j++){
                this.fields[j][i] = new Field(j * 30, i * 30, "Field");
            }
        }
    }

    //Returns random Position
    private int randomPos(){
        return (int) ( 1 * Math.random() * this.fields.length );
    }

    //Initialize Target
    void initTarget(){
        int targetPosX = this.randomPos();
        int targetPosY = this.randomPos();

        this.target = new Target();
        this.target.setX(targetPosX * 30);
        this.target.setY(targetPosY * 30);
        this.fields[targetPosX][targetPosY] = this.target;

        this.initPlayer();
    }

    //Initialize Player
    private void initPlayer() {

        //generate new position until it isn´t target´s position
        while(this.fields[this.playerPosX][this.playerPosY].equals(this.target)){
            System.out.println("möp möp");
            this.playerPosX = this.randomPos();
            this.playerPosY = this.randomPos();
        }

        Player player = new Player();
        player.setX(this.playerPosX * 30);
        player.setY(this.playerPosY * 30);
        this.fields[this.playerPosX][this.playerPosY] = player;
    }

    //movew player pos
    private void movePlayerPos() {
        Player player = new Player();
        player.setX(this.playerPosX);
        player.setY(this.playerPosY);
        this.fields[this.playerPosX][this.playerPosY] = player;
    }

//    //keylistener for player-movement
//    void changePlayerPos(KeyEvent event){
//        int keyCode = event.getKeyCode();
//        switch ( keyCode ){
//            case KeyEvent.VK_DOWN:
//                this.movePlayer(true, -1);
//                System.out.println("down");
//                this.printAllFields();
//                break;
//            case KeyEvent.VK_UP:
//                this.movePlayer(true, 1);
//                System.out.println("up");
//                this.printAllFields();
//                break;
//            case KeyEvent.VK_RIGHT:
//                this.movePlayer(false, 1);
//                System.out.println("right");
//                this.printAllFields();
//                break;
//            case KeyEvent.VK_LEFT:
//                this.movePlayer(false, -1);
//                System.out.println("left");
//                this.printAllFields();
//                break;
//            default:
//                System.out.println("You have to press the arrow keys!");
//                this.printAllFields();
//                break;
//        }
//
//    }
//
//    //method that checks if down/up is pressed and if player reached the end of field
//    private void movePlayer(boolean upDown,int newPos) {
//        if(upDown) {
//            if (!(this.fields[this.playerPosX][this.playerPosY + newPos].getY() < 0) || !(this.fields[this.playerPosX][this.playerPosY + newPos].getY() > 0)) {
//                this.fields[this.playerPosX][this.playerPosY] = new Field(this.playerPosX, this.playerPosY, "Field");
//                this.setPlayerPosY(this.playerPosY + newPos);
//                this.movePlayerPos();
//            } else {
//                System.out.println("You reached the end.");
//            }
//        }else {
//            if (!(this.fields[this.playerPosX + newPos][this.playerPosY].getX() < 0) || !(this.fields[this.playerPosX + newPos][this.playerPosY].getX() > 0)) {
//                this.fields[this.playerPosX][this.playerPosY] = new Field(this.playerPosX, this.playerPosY, "Field");
//                this.setPlayerPosX(this.playerPosX + newPos);
//                this.movePlayerPos();
//            } else {
//                System.out.println("You reached the end.");
//            }
//        }
//    }

    //Prints all Fields after everything is set
    void printAllFields(){
        for(int i = 0; i < this.yDimension; i++){
            for(int j = 0; j < this.xDimension; j++){
                System.out.println(this.fields[j][i].getName() + "x" + this.fields[j][i].getX());
                System.out.println(this.fields[j][i].getName() + "y:" + this.fields[j][i].getY());
            }
        }
    }
}
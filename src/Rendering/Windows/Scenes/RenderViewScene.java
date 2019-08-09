package Rendering.Windows.Scenes;

import GameLogic.GameInit;
import GameObjects.Field_like_Objects.Field;
import GameObjects.Player.Player;
import Rendering.Windows.Controller.RenderViewController;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class RenderViewScene extends Scene implements Runnable{

    private boolean isRunning = false;
    private Thread currentThread;
    private Field[][] fields;
    private Stage stage;
    private GameInit gameInit;
    private RenderViewController renderViewController;

    public RenderViewScene(
            Field[][] fields,
            Stage stage,
            Parent root,
            float height,
            float width
    ) {
        super(root, height, width);
        this.fields = fields;
        this.stage = stage;
        this.setKeyHandler();
    }

    private void setKeyHandler(){
        this.setOnKeyReleased(keyEvent -> gameInit.handle(keyEvent));
    }

    public void setIsRunning(boolean isRunning){
        this.isRunning = isRunning;
    }

    public Player getPlayer() {
        Player player = null;
        for(Field[] fields : this.fields){
            for(Field field : fields){
                if(field.getName().equals("GameObjects.Player.Player")){
                    player = (Player) field;
                }
            }
        }
        return player;
    }

    public boolean isRunning(){
        return this.isRunning;
    }

    //initLevel Thread
    public synchronized void initLevel(
            Field[][] fields,
            RenderViewController renderViewController,
            GameInit gameInit
    ){
        isRunning = true;
        this.fields = fields;
        this.gameInit = gameInit;
        this.gameInit.intitPlayerMovement();
        this.gameInit.initEnemyMovement();
        currentThread = new Thread(this, "Renderthread");
        currentThread.start();
        this.renderViewController = renderViewController;
        //this.renderViewController.setLabelBindings(this.getPlayer());
    }

    // stop currentThread by closing frame
    private synchronized void stop(){
        currentThread.interrupt();
    }

    /** if deltaTime is less than 0.5 seconds render the gamefield
     * and prints the framerate into title
     * */
    @Override
    public void run() {
        int frames = 0;
        long timer = System.currentTimeMillis();
        final double framerate =  60.0;
        long frameStart = System.nanoTime();
        double nanoSeconds = 1000000000 / framerate;
        double delta = 0;

        while (isRunning){
            long now = System.nanoTime();
            delta += (now - frameStart) / nanoSeconds;
            frameStart = now;
            if (delta < 0.5) {
                try {
                    Thread.sleep((long) (1000 / framerate));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            this.renderViewController.initRendering(this.fields);
            frames++;
            delta--;
        }
        stop();
    }

}

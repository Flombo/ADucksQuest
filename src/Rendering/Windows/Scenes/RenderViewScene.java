package Rendering.Windows.Scenes;

import GameLogic.GameInit;
import GameObjects.Field_like_Objects.Field;
import GameObjects.Player.Player;
import Rendering.Windows.Controller.RenderViewController;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class RenderViewScene extends Scene{

    private boolean isRunning = false;
    private Field[][] fields;
    private GameInit gameInit;

    public RenderViewScene(
            Field[][] fields,
            Parent root,
            float height,
            float width
    ) {
        super(root, height, width);
        this.fields = fields;
        this.setKeyHandler();
    }

    private void setKeyHandler(){
        this.setOnKeyReleased(keyEvent -> gameInit.handle(keyEvent));
    }

    public Runnable setIsRunning(boolean isRunning){
        return (()-> this.isRunning = isRunning);
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

    //init renderloop
    public void initLevel(
            Field[][] fields,
            RenderViewController renderViewController,
            GameInit gameInit
    ){
        this.fields = fields;
        Player player = this.getPlayer();
        this.gameInit = gameInit;
        this.isRunning = true;

        this.gameInit.intitPlayerMovement();
        this.gameInit.initEnemyMovement();
        this.initRenderLoop(renderViewController);
        this.initLabelBinding(player, renderViewController);
    }

    private void initLabelBinding(Player player, RenderViewController renderViewController){
        if(player != null){
            Platform.runLater(renderViewController.setLabelBindings(player));
        }
    }

    private void initRenderLoop(RenderViewController renderViewController){
        AnimationTimer animationTimer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                renderViewController.initRendering(fields);
            }
        };
        animationTimer.start();
        if(!this.isRunning){
            animationTimer.stop();
        }
    }

}

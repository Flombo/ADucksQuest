package Rendering.Windows.Scenes;

import GameLogic.GameInit;
import GameObjects.Field_like_Objects.Field;
import GameObjects.Player.Player;
import Rendering.View;
import Rendering.Windows.Controller.RenderViewController;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class RenderViewScene extends Scene{

    private boolean isRunning = false;
    private Field[][] fields;
    private GameInit gameInit;
    private View view;

    public RenderViewScene(
            Parent root,
            float height,
            float width,
            View view
    ) {
        super(root, height, width);
        this.view = view;
        this.setKeyHandler();
        this.getStylesheets().add("/Rendering/Windows/Style/RenderView.css");
    }

    private void setKeyHandler(){
        this.setOnKeyReleased(keyEvent -> gameInit.handle(keyEvent));
    }

    public Runnable setIsRunning(boolean isRunning){
        return (()-> this.isRunning = isRunning);
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
        Player player = this.view.getPlayer();
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
                renderViewController.initRendering(fields, view);
            }
        };
        animationTimer.start();
        if(!this.isRunning){
            animationTimer.stop();
        }
    }

}

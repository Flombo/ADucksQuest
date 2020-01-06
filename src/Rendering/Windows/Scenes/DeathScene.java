package Rendering.Windows.Scenes;

import GameObjects.Player.Player;
import Rendering.View;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class DeathScene extends Scene {

    private View view;

    public DeathScene(Parent parent, double height, double width, View view) {
        super(parent, width, height);
        this.view = view;
        this.getStylesheets().add("/Rendering/Windows/Style/DeathMenu.css");
    }

    public Player getPlayer(){
        return this.view.getPlayer();
    }

    public void retry(){
        this.view.resetRessources();
        this.view.initLevel(this.view.getCurrentLevelCode(), this.view.getLevelXAxis(), this.view.getLevelYAxis());
    }

    public void goToMenu(){
        this.view.resetRessources();
        this.view.showMainMenu();
    }

}

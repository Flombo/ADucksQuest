package Rendering.Windows.Scenes;

import Rendering.View;
import Rendering.Windows.Controller.IngameMenuController;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class IngameMenuScene extends Scene {

    private View view;
    private IngameMenuController ingameMenuController;

    public IngameMenuScene(Parent parent, double width, double height, View view, IngameMenuController ingameMenuController) {
        super(parent, width, height);
        this.view = view;
        this.getStylesheets().add("/Rendering/Windows/Style/IngameMenu.css");
        this.ingameMenuController = ingameMenuController;
    }

    public void resumeGame(){
        this.view.resumeGame();
    }

    public void goToMenu(){
        this.view.showMainMenu();
        this.view.resetRessources();
    }

    public void options() {
        this.view.showOptions();
        this.view.resetRessources();
    }

    public void setSize() {
        this.view.setSize(this.getHeight(), this.getWidth());
    }

    public void resize() {
        this.ingameMenuController.initHandling();
    }
}

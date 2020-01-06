package Rendering.Windows.Scenes;

import Rendering.View;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class IngameMenuScene extends Scene {

    private View view;

    public IngameMenuScene(Parent parent, double width, double height, View view) {
        super(parent, width, height);
        this.view = view;
        this.getStylesheets().add("/Rendering/Windows/Style/IngameMenu.css");
    }

    public void resumeGame(){
        this.view.resumeGame();
    }

    public void goToMenu(){
        this.view.showMainMenu();
        this.view.resetRessources();
    }

}

package Rendering.Windows.Scenes;

import Rendering.View;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class SuccessMenuScene extends Scene {

    private View view;

    public SuccessMenuScene(Parent parent, double width, double height, View view) {
        super(parent, width, height);
        this.view = view;
    }

    public void startNewGame(){
        this.view.initLevel();
    }

    public void goToMenu(){
        this.view.showMainMenu();
    }
}

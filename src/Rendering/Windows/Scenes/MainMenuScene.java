package Rendering.Windows.Scenes;

import Rendering.View;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class MainMenuScene extends Scene{

    private View view;

    public MainMenuScene(Parent parent, double width, double height, View view) {
        super(parent, width, height);
        this.view = view;
    }

    public void initGame(){
        this.view.initLevel();
    }

    public void closeGame(){
        this.view.closeGame();
    }
}

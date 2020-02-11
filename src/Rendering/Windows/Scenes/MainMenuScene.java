package Rendering.Windows.Scenes;

import Rendering.View;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class MainMenuScene extends Scene{

    private View view;

    public MainMenuScene(Parent parent, double width, double height, View view) {
        super(parent, width, height);
        this.view = view;
        this.getStylesheets().add("/Rendering/Windows/Style/MainMenu.css");
    }

    public void showLevelSelection(){
        this.view.showLevelSelection();
    }

    public void closeGame(){
        this.view.closeGame();
    }

    public void showLeveleditor(){
        this.view.showLeveleditor();
    }

    public void showOptions() {
        this.view.showOptions();
    }
}

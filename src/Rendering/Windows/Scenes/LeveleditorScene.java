package Rendering.Windows.Scenes;

import Rendering.View;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class LeveleditorScene extends Scene {

    private View view;

    public LeveleditorScene(Parent parent, double height, double width, View view) {
        super(parent, width, height);
        this.view = view;
        this.getStylesheets().add("/Rendering/Windows/Style/Leveleditor.css");
    }

    public void goToMenu(){
        this.view.showMainMenu();
    }

    public void save(String levelStructure){
        System.out.println(levelStructure);
    }
}

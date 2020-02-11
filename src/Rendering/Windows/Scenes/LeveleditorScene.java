package Rendering.Windows.Scenes;

import Rendering.View;
import Rendering.Windows.Controller.LeveleditorController;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class LeveleditorScene extends Scene {

    private View view;
    private LeveleditorController leveleditorController;

    public LeveleditorScene(Parent parent, double height, double width, View view, LeveleditorController leveleditorController) {
        super(parent, width, height);
        this.view = view;
        this.getStylesheets().add("/Rendering/Windows/Style/Leveleditor.css");
        this.leveleditorController = leveleditorController;
    }

    public void goToMenu(){
        this.view.showMainMenu();
    }

    public void resize() {
        this.leveleditorController.reLayout();
    }
}

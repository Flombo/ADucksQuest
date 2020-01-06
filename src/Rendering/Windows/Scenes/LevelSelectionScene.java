package Rendering.Windows.Scenes;

import Database.DatabaseHelper;
import GameObjects.Entities.Level;
import Rendering.View;
import Rendering.Windows.Controller.LevelSelectionController;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class LevelSelectionScene extends Scene {

    private View view;
    private DatabaseHelper databaseHelper;
    private LevelSelectionController levelSelectionController;

    public LevelSelectionScene(Parent parent, double width, double height, View view, LevelSelectionController levelSelectionController) {
        super(parent, width, height);
        this.view = view;
        this.getStylesheets().add("/Rendering/Windows/Style/LevelSelection.css");
        if(this.databaseHelper == null) {
            this.databaseHelper = new DatabaseHelper();
        }
        this.levelSelectionController = levelSelectionController;
    }

    public void buildLevels(){
        levelSelectionController.buildLevels(this.databaseHelper);
    }

    public void initGame(String levelCode, int x, int y){
        this.view.initLevel(levelCode, x, y);
    }

    public void goToMenu(){
        this.view.showMainMenu();
    }

}

package Rendering.Windows.Controller;

import Rendering.Windows.ControllerHelper.HoverHelper;
import Rendering.Windows.Scenes.MainMenuScene;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;

public class MainMenuController extends HoverHelper {

    @FXML
    private void onButtonPressed(Event event){
        Node source = (Node) event.getSource();
        MainMenuScene scene = (MainMenuScene) source.getScene();
        switch (((Node) event.getSource()).getId()){
            case ("startGame"):
                scene.showLevelSelection();
                break;
            case ("quitGame"):
                scene.closeGame();
                break;
            case ("Leveleditor"):
                scene.showLeveleditor();
                break;
        }
    }

}

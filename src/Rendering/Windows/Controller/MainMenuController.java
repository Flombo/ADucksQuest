package Rendering.Windows.Controller;

import Rendering.Windows.Scenes.MainMenuScene;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;

public class MainMenuController{

    @FXML
    private void onButtonPressed(Event event){
        Node source = (Node) event.getSource();
        MainMenuScene scene = (MainMenuScene) source.getScene();
        switch (((Node) event.getSource()).getId()){
            case ("startGame"):
                scene.initGame();
                break;
            case ("quitGame"):
                scene.closeGame();
                break;
        }
    }

}

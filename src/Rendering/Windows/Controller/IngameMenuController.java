package Rendering.Windows.Controller;

import Rendering.Windows.ControllerHelper.HoverHelper;
import Rendering.Windows.Scenes.IngameMenuScene;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

public class IngameMenuController extends HoverHelper {

    public Button options;
    public Button resume;
    public Button goToMenu;

    public void onClick(MouseEvent mouseEvent) {
        Node source = (Node) mouseEvent.getSource();
        IngameMenuScene ingameMenuScene = (IngameMenuScene) source.getScene();
        switch (source.getId()){
            case "resume":
                ingameMenuScene.resumeGame();
                break;
            case "goToMenu":
                ingameMenuScene.goToMenu();
                break;
        }
    }
}

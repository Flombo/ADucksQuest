package Rendering.Windows.Controller;

import Rendering.Windows.ControllerHelper.HoverHelper;
import Rendering.Windows.Scenes.IngameMenuScene;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.Observable;
import java.util.ResourceBundle;

public class IngameMenuController extends HoverHelper {

    public Button options;
    public Button resume;
    public Button goToMenu;
    public AnchorPane anchor;
    public VBox buttonContainer;
    public Label resumeLabel;

    public void initHandling(){
        this.anchor.widthProperty().addListener((Observable -> this.resize()));
        this.anchor.heightProperty().addListener((Observable -> this.resize()));
    }

    private void resize() {
        this.resumeLabel.setLayoutX((this.anchor.widthProperty().getValue() - this.resumeLabel.widthProperty().getValue()) / 2);
        this.resumeLabel.setLayoutY((this.anchor.heightProperty().getValue() - this.resumeLabel.heightProperty().getValue()) / 3);
        this.buttonContainer.setLayoutX((this.anchor.widthProperty().getValue() - this.buttonContainer.widthProperty().getValue()) / 2);
        this.buttonContainer.setLayoutY(this.anchor.heightProperty().getValue() - (this.resumeLabel.heightProperty().getValue() + this.resumeLabel.getLayoutY() + 20));
    }

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
            case "options":
                ingameMenuScene.options();
                break;
        }
    }
}

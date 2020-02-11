package Rendering.Windows.Controller;

import Rendering.Windows.ControllerHelper.HoverHelper;
import Rendering.Windows.Scenes.MainMenuScene;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuController extends HoverHelper implements Initializable {

    public AnchorPane anchor;
    public Label mainMenuLabel;
    public VBox buttonContainer;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.anchor.heightProperty().addListener((Observable -> this.resize()));
        this.anchor.widthProperty().addListener((Observable -> this.resize()));
    }

    private void resize() {
        this.mainMenuLabel.setLayoutX((this.anchor.widthProperty().getValue() - this.mainMenuLabel.widthProperty().getValue()) / 2);
        this.mainMenuLabel.setLayoutY(this.anchor.heightProperty().getValue() / 3);
        this.buttonContainer.setLayoutX((this.anchor.widthProperty().getValue() - this.buttonContainer.widthProperty().getValue()) / 2);
    }

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
            case ("options"):
                scene.showOptions();
                break;
        }
    }
}

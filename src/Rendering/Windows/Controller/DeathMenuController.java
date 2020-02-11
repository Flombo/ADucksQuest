package Rendering.Windows.Controller;

import GameObjects.Player.Player;
import Rendering.Windows.ControllerHelper.HoverHelper;
import Rendering.Windows.Scenes.DeathScene;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.Observable;
import java.util.ResourceBundle;

public class DeathMenuController extends HoverHelper {

    public Button startButton;
    public Button mainMenuButton;
    public Label moveAmount;
    public Label liveAmount;
    public Label coinAmount;
    public Label scoreAmount;
    public AnchorPane anchor;
    public VBox buttonContainer;
    public Pane scoreContainer;

    private DeathScene deathScene;

    public void initHandling(){
        anchor.widthProperty().addListener((Observable -> this.resize()));
        anchor.heightProperty().addListener((Observable -> this.resize()));
    }

    private void resize() {
        double scoreContainerLayoutX = (this.anchor.widthProperty().getValue() - this.scoreContainer.widthProperty().getValue()) / 2;
        double scoreContainerLayoutY = (this.anchor.heightProperty().getValue() - this.scoreContainer.heightProperty().getValue()) / 3;
        if(scoreContainerLayoutX < scoreContainerLayoutX + this.scoreContainer.widthProperty().getValue()) {
            this.scoreContainer.setLayoutX(scoreContainerLayoutX);
        }
        if(scoreContainerLayoutY < this.startButton.getLayoutY()) {
            this.scoreContainer.setLayoutY(scoreContainerLayoutY);
        }
        double buttonContainerLayoutX = (this.anchor.widthProperty().getValue() - this.buttonContainer.widthProperty().getValue()) / 2;
        double buttonContainerLayoutY = (this.scoreContainer.getLayoutY() + this.scoreContainer.heightProperty().getValue()) + 50;
        this.buttonContainer.setLayoutX(buttonContainerLayoutX);
        if(buttonContainerLayoutY > (this.scoreContainer.getLayoutY() + this.scoreContainer.heightProperty().getValue())) {
            this.buttonContainer.setLayoutY(buttonContainerLayoutY);
        }
    }

    public void setLabels(){
        this.deathScene = (DeathScene) this.coinAmount.getScene();
        Player player = this.deathScene.getPlayer();
        this.coinAmount.textProperty().bind(player.getCoins());
        this.liveAmount.textProperty().bind(player.getLives());
        this.moveAmount.textProperty().bind(player.getMoves());
        this.scoreAmount.textProperty().bind(player.getScore());
    }

    public void onButtonClicked(MouseEvent mouseEvent) {
        Node source = (Node) mouseEvent.getSource();
        if(source.getId().equals("mainMenuButton")){
            deathScene.goToMenu();
        } else {
            deathScene.retry();
        }
    }
}

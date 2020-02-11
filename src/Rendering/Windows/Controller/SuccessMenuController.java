package Rendering.Windows.Controller;

import GameObjects.Player.Player;
import Rendering.Windows.ControllerHelper.HoverHelper;
import Rendering.Windows.Scenes.SuccessMenuScene;
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

public class SuccessMenuController extends HoverHelper {

    public Button mainMenuButton;
    public Button startButton;
    public Label moves;
    public Label lives;
    public Label coins;
    public Label score;
    public Label earnedCoins;
    public AnchorPane anchor;
    public VBox buttonContainer;
    public Pane scoreContainer;

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

    public void onButtonClicked(MouseEvent mouseEvent) {
        Node source = (Node) mouseEvent.getSource();
        SuccessMenuScene successMenuScene = (SuccessMenuScene) source.getScene();

        if(source.getId().equals("startButton")){
            successMenuScene.startNewGame();
        } else {
            successMenuScene.goToMenu();
        }
    }

    public void setLabelBindings(Player player){
        this.coins.textProperty().bind(player.getCoins());
        this.lives.textProperty().bind(player.getLives());
        this.moves.textProperty().bind(player.getMoves());
        this.score.textProperty().bind(player.getScore());
        this.earnedCoins.textProperty().bind(player.getEarnedCoins());
    }
}

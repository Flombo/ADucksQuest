package Rendering.Windows.Controller;

import GameObjects.Player.Player;
import Rendering.Windows.ControllerHelper.HoverHelper;
import Rendering.Windows.Scenes.SuccessMenuScene;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class SuccessMenuController extends HoverHelper {

    public Button mainMenuButton;
    public Button startButton;
    public Label moves;
    public Label lives;
    public Label coins;
    public Label score;
    public Label earnedCoins;

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
        this.earnedCoins.textProperty().bind(player.getCoins());
    }

}

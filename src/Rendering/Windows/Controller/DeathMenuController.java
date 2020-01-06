package Rendering.Windows.Controller;

import GameObjects.Player.Player;
import Rendering.Windows.ControllerHelper.HoverHelper;
import Rendering.Windows.Scenes.DeathScene;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class DeathMenuController extends HoverHelper {

    public Button startButton;
    public Button mainMenuButton;
    public Label moveAmount;
    public Label liveAmount;
    public Label coinAmount;
    public Label scoreAmount;

    private DeathScene deathScene;

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

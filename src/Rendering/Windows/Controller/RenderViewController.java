package Rendering.Windows.Controller;

import GameObjects.Field_like_Objects.Field;
import GameObjects.Player.Player;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Label;

public class RenderViewController {

    public Label coinLabel;
    @FXML
    private Label moveLabel;
    @FXML
    private Label liveLabel;
    @FXML
    private Button menuButton;
    @FXML
    private Button punchSkillButton;
    @FXML
    private Button doubleCoinSkillButton;
    @FXML
    private Button rageSkillButton;
    @FXML
    private Canvas renderCanvas;
    private Field[][] fields;

    public void menuButtonClicked(MouseEvent mouseEvent) {
        System.out.println("BabediBUbedi");
    }

    //render Gamefield and Counterpanel
    public void initRendering(Field[][] fields){
        this.fields = fields;
        this.renderGamefield(this.renderCanvas);
    }

//    public void setLabelBindings(Player player){
//        this.moveLabel.textProperty().bind(player.getMoves());
//        this.liveLabel.textProperty().bind(player.getLives());
//        this.coinLabel.textProperty().bind(player.getCoins());
//    }

    private void updateSkillbar(Player player){
    }

    //render Gamefield
    private void renderGamefield(Canvas renderCanvas){
        GraphicsContext g = renderCanvas.getGraphicsContext2D();
        for (Field[] fields : this.fields) {
            for(Field field : fields){
                if(field instanceof Player) {
                    this.updateSkillbar((Player) field);
                }
                g.drawImage(field.getCurrentImage(), field.getX() + 75, field.getY() + 90, field.getHeight(), field.getWidth());
            }
        }
    }

}

package Rendering.Windows.Controller;

import GameObjects.Field_like_Objects.Field;
import GameObjects.Player.Player;
import Rendering.View;
import Rendering.Windows.Config.ConfigManager;
import Rendering.Windows.ControllerHelper.HoverHelper;
import Rendering.Windows.Scenes.RenderViewScene;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import java.net.URL;
import java.util.ResourceBundle;

public class RenderViewController extends HoverHelper {

    public Label coinLabel;
    public AnchorPane anchorPane;
    public Pane counterbarpane;
    public HBox counterBar;
    public Button menuButton;
    public HBox coinBar;
    public HBox liveBar;
    public HBox moveBar;
    @FXML
    private Label moveLabel;
    @FXML
    private Label liveLabel;
    @FXML
    private Canvas renderCanvas;
    private Field[][] fields;
    private View view;

    public void menuButtonClicked() {
        if(view != null){
            this.view.showIngameMenu();
        }
    }

    //render Gamefield and Counterpanel
    public void initRendering(Field[][] fields, View view, int x, int y){
        this.view = view;
        this.fields = fields;
        this.setCanvasLayout(x, y);
        this.setCounterLayout();
        this.renderGamefield(this.renderCanvas, x, y);
    }

    private void setCounterLayout() {
        this.counterbarpane.setPrefWidth(this.anchorPane.getWidth());
        this.setCounterBarLayout();
    }

    private void setCounterBarLayout() {
        this.counterBar.setPrefWidth((this.counterbarpane.widthProperty().getValue() / 3)*2);
        double layoutX = this.counterbarpane.widthProperty().getValue() - this.counterBar.widthProperty().getValue() - 50;
        if(layoutX > this.menuButton.getLayoutX() + this.menuButton.widthProperty().getValue() + 20) {
            this.counterBar.setLayoutX(layoutX);
        }
    }

    private void setCanvasLayout(int x, int y) {
        this.setRenderCanvasSize(x, y);
        double layoutX = (this.anchorPane.getWidth() - this.renderCanvas.getWidth()) / 2;
        double layoutY = (this.anchorPane.getHeight() - this.renderCanvas.getHeight()) / 2;
        if(layoutY > 125) {
            this.renderCanvas.setLayoutY(layoutY);
        }
        this.renderCanvas.setLayoutX(layoutX);
        this.clearCanvas();
    }

    private void setRenderCanvasSize(int x, int y){
        double anchorPaneHeight = this.anchorPane.getHeight() - this.counterbarpane.getHeight();
        double anchorPaneWidth = this.anchorPane.getWidth();
        if(anchorPaneHeight > anchorPaneWidth && anchorPaneWidth - anchorPaneWidth - 50 >= 50){
            this.renderCanvas.setWidth(anchorPaneWidth - 50);
            this.renderCanvas.setHeight(anchorPaneWidth - 50);
        } else if(anchorPaneHeight < anchorPaneWidth && anchorPaneHeight - anchorPaneHeight - 50 >= 50) {
            this.renderCanvas.setHeight(anchorPaneHeight - 50);
            this.renderCanvas.setWidth(anchorPaneHeight - 50);
        } else {
            this.renderCanvas.setHeight(anchorPaneHeight - 50);
            this.renderCanvas.setWidth(anchorPaneHeight - 50);
        }
    }

    public Runnable setLabelBindings(Player player){
        return (() -> {
            moveLabel.textProperty().bind(player.getMoves());
            liveLabel.textProperty().bind(player.getLives());
            coinLabel.textProperty().bind(player.getCoins());
        });
    }

    public void clearCanvas(){
        GraphicsContext g = renderCanvas.getGraphicsContext2D();
        g.clearRect(0, 0, this.anchorPane.widthProperty().doubleValue(), this.anchorPane.heightProperty().doubleValue());
    }

    //render Gamefield
    private void renderGamefield(Canvas renderCanvas, int x, int y){
        GraphicsContext g = renderCanvas.getGraphicsContext2D();
        for (Field[] fields : this.fields) {
            for(Field field : fields){
                if(x != y) {
                    g.drawImage(
                            field.getCurrentImage(),
                            field.getXPos() * this.renderCanvas.getWidth() / x,
                            field.getYPos() * this.renderCanvas.getHeight() / x,
                            this.renderCanvas.getWidth() / x,
                            this.renderCanvas.getHeight() / x
                    );
                } else {
                    g.drawImage(
                            field.getCurrentImage(),
                            field.getXPos() * this.renderCanvas.getWidth() / x,
                            field.getYPos() * this.renderCanvas.getHeight() / y,
                            this.renderCanvas.getWidth() / x,
                            this.renderCanvas.getHeight() / y
                    );
                }
            }
        }
    }
}

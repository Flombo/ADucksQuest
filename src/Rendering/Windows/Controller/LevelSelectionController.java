package Rendering.Windows.Controller;

import Database.DatabaseHelper;
import GameObjects.Entities.Level;
import Rendering.Windows.ControllerHelper.DragHelper;
import Rendering.Windows.ControllerHelper.HoverHelper;
import Rendering.Windows.Scenes.LevelSelectionScene;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.input.MouseEvent;
import java.util.ArrayList;

public class LevelSelectionController extends HoverHelper {

    public Button backButton;
    public AnchorPane anchor;
    @FXML
    private AnchorPane scrollAnchor;
    @FXML
    private ScrollPane scrollPane;
    private DatabaseHelper databaseHelper;
    private ArrayList<Level> levels;
    private Pane levelDialog;
    private DragHelper dragHelper;

    public void buildLevels(DatabaseHelper databaseHelper){
        this.databaseHelper = databaseHelper;
        this.dragHelper = new DragHelper();
        this.scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        levels = this.databaseHelper.selectAll();
        TilePane tilePane = new TilePane();
        tilePane.setLayoutX(10);
        for (Level level: levels) {
            tilePane.getChildren().add(this.buildLevelPane(level, levels.indexOf(level)));
        }
        this.scrollAnchor.getChildren().add(tilePane);
    }

    private Pane buildLevelPane(Level level, int index){
        Label levelname = new Label(level.getName());
        levelname.setId("levelLabel");
        Image img = level.getThumbnail();
        ImageView view = new ImageView(img);
        VBox vBox = new VBox();
        vBox.setLayoutY(5);
        vBox.setLayoutX(5);
        vBox.getChildren().add(levelname);
        vBox.getChildren().add(view);
        return this.buildPane(vBox, index);
    }

    private Pane buildPane(VBox vBox, int index){
        Pane levelPane = new Pane(vBox);
        levelPane.setId(Integer.toString(index));
        levelPane.addEventFilter(MouseEvent.MOUSE_ENTERED_TARGET, this::onHoverInElement);
        levelPane.addEventFilter(MouseEvent.MOUSE_EXITED_TARGET, this::onHoverOutElement);
        levelPane.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
            if(this.levelDialog != null){
                this.anchor.getChildren().remove(this.levelDialog);
                this.levelDialog = null;
            }
            this.buildLevelDialog(index);
        });
        return levelPane;
    }

    private void buildLevelDialog(int index){
        Level level = this.levels.get(index);
        int x = level.getX();
        int y = level.getY();
        Label levelname = new Label("Levelname : " + level.getName());
        Label tags = new Label("Tags : " + level.getTags());
        Image img = level.getThumbnail();
        ImageView view = new ImageView(img);
        Button start = buildLevelDialogButton("Start level", "startButton", level.getLevelcode(), x, y);
        Button close = buildLevelDialogButton("close", "closeButton", level.getLevelcode(), x, y);
        VBox vbox = new VBox();
        vbox.getChildren().addAll(levelname, tags, start, close);
        TilePane tile = new TilePane();
        tile.getChildren().addAll(view, vbox);
        tile.setId("levelDialogTile");
        tile.setLayoutY(25);
        tile.setLayoutX(10);
        levelDialog = new Pane(tile);
        levelDialog.setId("levelDialog");
        levelDialog.setLayoutX(200);
        levelDialog.setLayoutY(300);
        levelDialog.setMinSize(400, 200);
        levelDialog.setMaxHeight(200);
        this.dragHelper.addHandler(this.levelDialog);
        this.anchor.getChildren().add(levelDialog);
    }

    private Button buildLevelDialogButton(String label, String id, String levelCode, int x, int y){
        Button btn = new Button(label);
        btn.setId(id);
        btn.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> this.levelDialogButtonClickHandler(event, levelCode, x, y));
        btn.addEventHandler(MouseEvent.MOUSE_ENTERED_TARGET, this::onHoverIn);
        btn.addEventHandler(MouseEvent.MOUSE_EXITED_TARGET, this::onHoverOut);
        return btn;
    }

    private void levelDialogButtonClickHandler(MouseEvent event, String levelCode, int x, int y){
        Button btn = (Button) event.getSource();
        if(btn.getId().equals("startButton")){
            ((LevelSelectionScene)btn.getScene()).initGame(levelCode, x, y);
        }
        this.anchor.getChildren().remove(this.levelDialog);
        this.levelDialog = null;
    }

    public void onClick(MouseEvent mouseEvent) {
        ((LevelSelectionScene)((Button) mouseEvent.getSource()).getScene()).goToMenu();
        this.scrollAnchor.getChildren().removeAll();
    }
}

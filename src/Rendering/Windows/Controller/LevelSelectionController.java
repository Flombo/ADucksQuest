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
    private TilePane levelTile;

    public void initHandling(){
        this.anchor.widthProperty().addListener((Observable -> this.resizeElements()));
        this.anchor.heightProperty().addListener((Observable -> this.resizeElements()));
    }

    public void buildLevels(DatabaseHelper databaseHelper){
        this.databaseHelper = databaseHelper;
        this.dragHelper = new DragHelper();
        this.scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        levels = this.databaseHelper.selectAll();
        this.levelTile = new TilePane();
        this.addLevelsToTilePane();
        this.scrollAnchor.getChildren().add(this.levelTile);
        this.initHandling();
    }

    private void addLevelsToTilePane() {
        if(!levels.isEmpty()) {
            for (Level level : levels) {
                this.levelTile.getChildren().add(this.buildLevelPane(level, levels.indexOf(level)));
            }
        } else {
            Label label = new Label("There were no levels found!");
            label.setStyle("-fx-font-size: 20; -fx-text-fill: darkorange; -fx-font-weight: bold;");
            this.levelTile.getChildren().add(label);
        }
    }

    private void resizeElements() {
        this.scrollPane.setPrefHeight(this.anchor.heightProperty().getValue()/1.2);
        this.scrollPane.setPrefWidth(this.anchor.widthProperty().getValue()/1.2);
        this.setScrollPaneLayouts();
        this.scrollAnchor.setPrefHeight(this.scrollPane.heightProperty().getValue() - 5);
        this.scrollAnchor.setPrefWidth(this.scrollPane.widthProperty().getValue() - 5);
        this.levelTile.setPrefWidth(this.scrollPane.widthProperty().getValue());
        this.levelTile.setPrefHeight(this.scrollPane.heightProperty().getValue());
        System.out.println(this.levelTile.widthProperty().getValue());
    }

    private void setScrollPaneLayouts() {
        double scrollPaneLayoutX = (this.anchor.widthProperty().getValue() - this.scrollPane.widthProperty().getValue()) / 2;
        double scrollPaneLayoutY = (this.anchor.heightProperty().getValue()
                + this.backButton.getLayoutY()
                + this.backButton.heightProperty().getValue()
                - this.scrollPane.heightProperty().getValue()
        ) / 2;
        if(scrollPaneLayoutX < 300 && scrollPaneLayoutX > this.backButton.getLayoutX()) {
            this.scrollPane.setLayoutX(scrollPaneLayoutX);
        }
        if(scrollPaneLayoutY > this.backButton.getLayoutY() + this.backButton.heightProperty().getValue() + 10 && scrollPaneLayoutY < 590){
            this.scrollPane.setLayoutY(scrollPaneLayoutY);
        }
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
            this.buildLevelDialog(index, levelPane.getLayoutX(), levelPane.getLayoutY());
        });
        return levelPane;
    }

    private void buildLevelDialog(int index, double xLayout, double yLayout){
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
        double dialogLayoutX = xLayout + this.scrollPane.getLayoutX() - 10;
        double dialogLayoutY = yLayout + this.scrollPane.getLayoutY() - 10;
        this.buildLevelDialogPane(this.buildLevelDialogTile(vbox, view), dialogLayoutX, dialogLayoutY);
        this.anchor.getChildren().add(levelDialog);
    }

    private TilePane buildLevelDialogTile(VBox vbox, ImageView view){
        TilePane tile = new TilePane();
        tile.getChildren().addAll(view, vbox);
        tile.setId("levelDialogTile");
        tile.setLayoutY(25);
        tile.setLayoutX(10);
        return tile;
    }

    private void buildLevelDialogPane(TilePane tile, double layoutX, double layoutY){
        levelDialog = new Pane(tile);
        levelDialog.setId("levelDialog");
        levelDialog.setLayoutX(layoutX);
        levelDialog.setLayoutY(layoutY);
        levelDialog.setMinSize(400, 200);
        levelDialog.setMaxHeight(200);
        this.dragHelper.addHandler(this.levelDialog);
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

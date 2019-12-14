package Rendering.Windows.Controller;

import Database.DatabaseHelper;
import Rendering.Windows.ControllerHelper.HoverHelper;
import Rendering.Windows.ControllerHelper.LeveleditorHelper;
import Rendering.Windows.Scenes.LeveleditorScene;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Paint;

import java.net.URL;
import java.util.ResourceBundle;

public class LeveleditorController extends HoverHelper implements Initializable{

    @FXML
    Pane levelPane;
    @FXML
    Slider ySlider;
    @FXML
    Slider xSlider;
    private Pane elementsDialog;
    private Pane backDialog;
    private Pane saveDialog;
    private LeveleditorHelper leveleditorHelper;
    private DatabaseHelper databaseHelper;
    private Pane currentElementPick;
    private boolean elementButtonActive = false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        leveleditorHelper = new LeveleditorHelper();
        databaseHelper = new DatabaseHelper();
        addSliderListener(ySlider);
        addSliderListener(xSlider);
    }

    private void addSliderListener(Slider slider){
        leveleditorHelper.onDrag(levelPane, xSlider, ySlider);
        slider.valueProperty().addListener((observable -> {
            leveleditorHelper.onDrag(levelPane, xSlider,ySlider);
            elementsDialog = null;
        }));
    }

    @FXML
    public void onButtonClicked(MouseEvent mouseEvent) {
        Node source = (Node) mouseEvent.getSource();
        LeveleditorScene leveleditorScene = (LeveleditorScene) source.getScene();
        switch (source.getId()){
            case "backButton":
                this.showBackDialog(leveleditorScene);
                break;
            case "clearButton":
                this.clear();
                break;
            case "saveButton":
                this.showSaveDialog();
                break;
            case "elementsButton":
                if(!elementButtonActive){
                    source.setStyle("-fx-border-color: lightgreen; -fx-border-width: 2px");
                    elementButtonActive = true;
                } else {
                    source.setStyle("-fx-border: none");
                    elementButtonActive = false;
                }
                this.showElementsDialog();
        }
    }

    private void elementEventHandler(Pane field) {
        this.elementClickHandler(field);
        this.elementHoverHandler(field);
    }

    private void elementClickHandler(Pane field){
        field.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
            if(event.getButton().equals(MouseButton.PRIMARY)) {
                if(currentElementPick != null){
                    currentElementPick.setStyle("-fx-border: none");
                }
                leveleditorHelper.setCurrentEditorElement(field.getId());
                currentElementPick = field;
                currentElementPick.setStyle("-fx-border-color: lightgreen; -fx-border-width: 2px");
            }
        });
    }

    private void elementHoverHandler(Pane field){
        field.addEventFilter(MouseEvent.MOUSE_ENTERED_TARGET, this::onHoverInElement);
        field.addEventHandler(MouseEvent.MOUSE_EXITED_TARGET, this::onHoverOutElement);
    }

    private void backDialogHandler(Button button, LeveleditorScene leveleditorScene){
        button.addEventFilter(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
             Node source = (Node) mouseEvent.getSource();
             switch (source.getId()){
                 case "saveButton":
                     this.showSaveDialog();
                     levelPane.getChildren().remove(backDialog);
                     break;
                 case "discardButton":
                     clear();
                     leveleditorScene.goToMenu();
                     break;
                 case "cancelButton":
                     levelPane.getChildren().remove(backDialog);
                     break;
             }
        });
        button.addEventFilter(MouseEvent.MOUSE_ENTERED_TARGET, this::onHoverIn);
        button.addEventFilter(MouseEvent.MOUSE_EXITED_TARGET, this::onHoverOut);
    }

    private void saveEventHandler(Button button, TextField name, TextField tags){
        this.addClickHandler(button, name, tags);
        button.addEventFilter(MouseEvent.MOUSE_ENTERED_TARGET, this::onHoverIn);
        button.addEventFilter(MouseEvent.MOUSE_EXITED_TARGET, this::onHoverOut);
    }

    private void addClickHandler(Button button, TextField name, TextField tags){
        button.addEventFilter(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            Node source = (Node) mouseEvent.getSource();
            String levelCode = null;
            try {
                levelCode = leveleditorHelper.getLevelStructure();
            } catch (Exception e) {
                this.displayErrorMsg(e.getLocalizedMessage());
            }
            if(source.getId().equals("saveButton")){
                if(levelCode != null) {
                    if (name.getText().length() < 50 && tags.getText().length() < 100 && levelCode.length() < 1000 && name.getText().length() >= 5 && tags.getText().length() >= 5) {
                        databaseHelper.insertLevel(name.getText(), tags.getText(), levelCode);
                        databaseHelper.selectAll();
                        levelPane.getChildren().remove(saveDialog);
                    } else {
                        try {
                            throw new Exception("Levelname has to be around 5 and 50 characters. Tags have to be around 5 and 100 characters");
                        } catch (Exception e) {
                            this.displayErrorMsg(e.getLocalizedMessage());
                        }
                    }
                }
            } else {
                levelPane.getChildren().remove(saveDialog);
            }
        });
    }

    private void displayErrorMsg(String localizedMessage) {
        Label errorMsg = new Label(localizedMessage);
        errorMsg.setId("errorMsg");
        errorMsg.setLayoutY(200);
        errorMsg.setLayoutX(25);
        saveDialog.getChildren().add(errorMsg);
    }

    private void showSaveDialog(){
        if(saveDialog == null) {
            this.buildSaveDialog();
            this.levelPane.getChildren().add(saveDialog);
        } else {
            this.levelPane.getChildren().remove(saveDialog);
            saveDialog = null;
        }
    }

    private void showBackDialog(LeveleditorScene leveleditorScene){
        if(backDialog == null) {
            this.buildBackDialog(leveleditorScene);
            this.levelPane.getChildren().add(backDialog);
        } else {
            this.levelPane.getChildren().remove(backDialog);
            backDialog = null;
        }
    }

    private void showElementsDialog(){
        if(elementsDialog == null) {
            this.buildElementsDialog();
            this.levelPane.getChildren().add(elementsDialog);
        } else {
            this.levelPane.getChildren().remove(elementsDialog);
            elementsDialog = null;
        }
    }

    private void buildSaveDialog(){
        Label txt = new Label("Save and upload level");
        txt.setId("saveText");
        txt.setLayoutX(125);
        txt.setLayoutY(25);
        Label name = new Label("Levelname:");
        name.setId("levelNameLabel");
        name.setLayoutX(50);
        name.setLayoutY(75);
        Label tags = new Label("Tags:");
        tags.setLayoutX(50);
        tags.setLayoutY(150);
        tags.setId("levelTagsLabel");
        TextField nameInput = new TextField();
        nameInput.setLayoutX(150);
        nameInput.setLayoutY(75);
        TextField tagInput = new TextField();
        tagInput.setLayoutX(150);
        tagInput.setLayoutY(150);
        Button saveButton = this.buildDialogButton("save", "saveButton", 50, 250);
        this.saveEventHandler(saveButton, nameInput, tagInput);
        Button cancelButton = this.buildDialogButton("cancel", "cancelButton", 150, 250);
        this.saveEventHandler(cancelButton, nameInput, tagInput);
        Pane pane = new Pane(txt, name, tags, nameInput, tagInput, saveButton, cancelButton);
        pane.setId("saveDialog");
        pane.setLayoutY(60);
        pane.setLayoutX(60);
        saveDialog = pane;
    }

    private void buildBackDialog(LeveleditorScene leveleditorScene) {
        Label txt = new Label("Want to quit?");
        txt.setId("backText");
        txt.setLayoutX(125);
        txt.setLayoutY(25);
        Button saveButton = this.buildDialogButton("save", "saveButton", 50, 100);
        this.backDialogHandler(saveButton, leveleditorScene);
        Button discardButton = this.buildDialogButton("discard", "discardButton", 150, 100);
        this.backDialogHandler(discardButton, leveleditorScene);
        Button cancelButton = this.buildDialogButton("cancel", "cancelButton", 250, 100);
        this.backDialogHandler(cancelButton, leveleditorScene);
        Pane pane = new Pane(txt, saveButton, discardButton, cancelButton);
        pane.setLayoutY(60);
        pane.setLayoutX(60);
        pane.setId("backDialog");
        backDialog = pane;
    }

    private Button buildDialogButton(String text, String id, int x, int y){
        Button btn = new Button(text);
        btn.setId(id);
        btn.setLayoutY(y);
        btn.setLayoutX(x);
        return btn;
    }

    private void buildElementsDialog() {
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setStyle("-fx-background-color: rgb(48,75,100);");
        anchorPane.setMinSize(400, 400);
        this.buildPlayerElementsPanel(anchorPane);
        ScrollPane scrollPane = new ScrollPane(anchorPane);
        scrollPane.setStyle("-fx-border: none;");
        scrollPane.setMinSize(405,400);
        scrollPane.setMaxSize(405,400);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setLayoutX(20);
        scrollPane.setLayoutY(20);
        Pane pane = new Pane(scrollPane);
        pane.setLayoutY(60);
        pane.setLayoutX(60);
        pane.setId("elementsDialog");
        elementsDialog = pane;
    }

    private void buildPlayerElementsPanel(AnchorPane anchorPane){
        Label playerLabel = this.buildElementsLabel("player elements", this.buildFieldElementsPanel(anchorPane));
        anchorPane.getChildren().add(playerLabel);
        TilePane playerPane = buildElementsTile(this.buildPlayerElements(), playerLabel.getLayoutY());
        anchorPane.getChildren().add(playerPane);
    }

    private double buildFieldElementsPanel(AnchorPane anchorPane){
        Label fieldLabel = this.buildElementsLabel("field elements", this.buildInteractionElementsPanel(anchorPane));
        anchorPane.getChildren().add(fieldLabel);
        TilePane fieldPane = this.buildElementsTile(this.buildFieldElements(), fieldLabel.getLayoutY());
        anchorPane.getChildren().add(fieldPane);
        return fieldPane.getLayoutY();
    }

    private double buildInteractionElementsPanel(AnchorPane anchorPane){
        Label interactionLabel = this.buildElementsLabel("interaction elements", this.buildEnemyElementsPanel(anchorPane));
        anchorPane.getChildren().add(interactionLabel);
        TilePane interactionPane = this.buildElementsTile(this.buildInteractionElements(), interactionLabel.getLayoutY());
        anchorPane.getChildren().add(interactionPane);
        return interactionPane.getLayoutY();
    }

    private double buildEnemyElementsPanel(AnchorPane anchorPane){
        Label enemieLabel = this.buildElementsLabel("enemy elements", this.buildItemElementsPanel(anchorPane));
        anchorPane.getChildren().add(enemieLabel);
        TilePane enemyPane = this.buildElementsTile(this.buildEnemyElements(), enemieLabel.getLayoutY());
        anchorPane.getChildren().add(enemyPane);
        return enemyPane.getLayoutY();
    }

    private double buildItemElementsPanel(AnchorPane anchorPane){
        Label itemLabel = this.buildElementsLabel("item elements", 0);
        anchorPane.getChildren().add(itemLabel);
        TilePane itemPane = this.buildElementsTile(this.buildItemElements(), itemLabel.getLayoutY());
        anchorPane.getChildren().add(itemPane);
        return itemPane.getLayoutY();
    }

    private Label buildElementsLabel(String txt, double y){
        Label label = new Label(txt);
        label.setLayoutY(y + 55);
        label.setTextFill(Paint.valueOf("white"));
        label.setLayoutX(100);
        return label;
    }

    private TilePane buildElementsTile(Pane[] elements, double y){
        TilePane tilePane = new TilePane(elements);
        tilePane.setLayoutY(y + 25);
        tilePane.setMinSize(400, 30);
        tilePane.setStyle("-fx-background-color: rgb(72,147,217);");
        return tilePane;
    }

    private Pane[] buildItemElements(){
        Pane coin = this.buildElement("coin");
        Pane heart = this.buildElement("heart");
        return new Pane[]{coin, heart};
    }

    private Pane[] buildEnemyElements(){
        Pane zombie = this.buildElement("zombie");
        Pane skull = this.buildElement("skull");
        return new Pane[]{zombie, skull};
    }

    private Pane[] buildInteractionElements(){
        Pane target = this.buildElement("target");
        Pane hole = this.buildElement("hole");
        Pane obstacle = this.buildElement("obstacle");
        Pane chest = this.buildElement("chest");
        return new Pane[]{
                target, hole, obstacle, chest
        };
    }

    private Pane[] buildFieldElements(){
        Pane field = this.buildElement("field");
        return new Pane[]{field};
    }

    private Pane[] buildPlayerElements() {
        Pane player = this.buildElement("player");

        return new Pane[]{
                player
        };
    }

    private Pane buildElement(String id){
        Pane field = new Pane();
        field.setId(id);
        this.elementEventHandler(field);
        return field;
    }

    private void clear(){
        ySlider.setValue(5);
        xSlider.setValue(5);
        elementsDialog = null;
        backDialog = null;
        levelPane.getChildren().clear();
        leveleditorHelper.setCurrentEditorElement("field");
        leveleditorHelper.onDrag(levelPane, xSlider, ySlider);
    }

}

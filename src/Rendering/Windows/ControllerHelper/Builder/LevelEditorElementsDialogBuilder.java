package Rendering.Windows.ControllerHelper.Builder;

import Rendering.Windows.ControllerHelper.DragHelper;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.Glow;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Paint;

public class LevelEditorElementsDialogBuilder {

    private LevelEditorMapBuilder mapBuilder;
    private Pane currentElementPick;
    private DragHelper dragHelper;

    public LevelEditorElementsDialogBuilder(LevelEditorMapBuilder mapBuilder){
        this.mapBuilder = mapBuilder;
        this.dragHelper = new DragHelper();
    }

    public Pane buildElementsDialog() {
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
        this.addEventlisteners(pane);
        return pane;
    }

    private void addEventlisteners(Pane elementsDialog){
        this.onHoverIn(elementsDialog);
        this.onHoverOut(elementsDialog);
        this.dragHelper.addHandler(elementsDialog);
    }

    private void onHoverIn(Pane elementsDialog){
        elementsDialog.addEventFilter(MouseEvent.MOUSE_ENTERED_TARGET, mouseEvent -> elementsDialog.getScene().setCursor(Cursor.HAND));
    }

    private void onHoverOut(Pane elementsDialog) {
        elementsDialog.addEventFilter(MouseEvent.MOUSE_EXITED_TARGET, mouseEvent -> elementsDialog.getScene().setCursor(Cursor.DEFAULT));
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
        Tooltip tooltip = this.buildElementTooltip(id);
        Pane field = new Pane();
        field.setId(id);
        Tooltip.install(field, tooltip);
        this.elementEventHandler(field);
        return field;
    }

    private Tooltip buildElementTooltip(String id){
        Tooltip tooltip = new Tooltip();
        switch (id){
            case "player":
                tooltip.setText("This is the playerelement. You have to place exact one on the map");
                break;
            case "field":
                tooltip.setText("This is a normal Field. It hasn´t any special function");
                break;
            case "coin":
                tooltip.setText("This is a coin. The player can use these coins to buy Themes, Skins, Leveleditorelements in the shop");
                break;
            case "heart":
                tooltip.setText("This is a heart. The player gets one extra live for collecting these");
                break;
            case "chest":
                tooltip.setText("This is a chest. The player can push it to blockade enemies and push it into holes to walk over them");
                break;
            case "target":
                tooltip.setText("These are stairs. The player has to reach these to complete a level. You have to place exact one on the map");
                break;
            case "obstacle":
                tooltip.setText("This is an obstacle. The player and enemies can´t move through it");
                break;
            case "zombie":
                tooltip.setText("This is a zombie. This enemy type can walk random in 4 directions(up,down, left, right)");
                break;
            case "skull":
                tooltip.setText("This is a skull. It can only walk left and right");
                break;
            case "hole":
                tooltip.setText("This is a hole, The player gets damaged by falling in it. Enemies can´t walk through it");
                break;
        }
        return tooltip;
    }

    private void elementEventHandler(Pane field){
        this.elementClickHandler(field);
        this.elementHoverHandler(field);
    }

    private void elementClickHandler(Pane field){
        field.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
            if(event.getButton().equals(MouseButton.PRIMARY)) {
                if(currentElementPick != null){
                    currentElementPick.setStyle("-fx-border: none");
                }
                mapBuilder.setCurrentEditorElement(field.getId());
                currentElementPick = field;
                currentElementPick.setStyle("-fx-border-color: lightgreen; -fx-border-width: 2px");
            }
        });
    }

    private void elementHoverHandler(Pane field){
        this.elementHoverIn(field);
        this.elementHoverOut(field);
    }

    private void elementHoverOut(Pane field){
        field.addEventFilter(MouseEvent.MOUSE_EXITED_TARGET, mouseEvent -> {
            field.getScene().setCursor(Cursor.DEFAULT);
            field.setEffect(null);
            field.setOpacity(1);
        });
    }

    private void elementHoverIn(Pane field){
        field.addEventFilter(MouseEvent.MOUSE_ENTERED_TARGET, mouseEvent -> {
            long start = System.nanoTime();
            field.getScene().setCursor(Cursor.HAND);
            Glow glow = new Glow();
            glow.setLevel(0.7);
            field.setEffect(glow);
            field.setOpacity(0.5);
        });
    }

}

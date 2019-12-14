package Rendering.Windows.ControllerHelper;

import javafx.scene.control.Slider;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.util.ArrayList;

public class LeveleditorHelper {

    private ArrayList<Pane> fields = new ArrayList<>();
    private boolean playerPlaced = false;
    private boolean targetPlaced = false;
    private String currentEditorElement = "field";

    public void onDrag(Pane levelPane, Slider xSlider, Slider ySlider){
        int xMax = (int) xSlider.getValue();
        int yMax = (int) ySlider.getValue();
        fields.clear();
        playerPlaced = false;
        targetPlaced = false;
        this.buildLevelFields(levelPane, xMax, yMax);
    }

    private void buildLevelFields(Pane levelPane, int xMax, int yMax){
        levelPane.getChildren().clear();
        for(int y = 0; y < yMax; y++){
            for(int x = 0; x < xMax; x++){
                Pane field = new Pane();
                this.addClickHandler(field);
                this.styleFieldPane(field, x, y);
                fields.add(field);
                levelPane.getChildren().add(field);
            }
        }
    }

    private void addClickHandler(Pane field){
        field.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
            if(event.getButton().equals(MouseButton.PRIMARY)) {
                setFieldID(currentEditorElement, field);
            } else {
                setFieldID("field", field);
            }
        });
    }

    private void styleFieldPane(Pane field, int x, int y){
        this.setFieldID("field", field);
        field.setMaxSize(30,30);
        field.setPrefSize(30,30);
        field.setMinSize(30,30);
        field.setLayoutX(30 * x);
        field.setLayoutY(30 * y);
        field.setVisible(true);
    }

    private void setFieldID(String fieldID, Pane field){
        switch (fieldID){
            case "field":
                field.setId("field");
                break;
            case "hole":
                field.setId("hole");
                break;
            case "obstacle":
                field.setId("obstacle");
                break;
            case "player":
                if(!playerPlaced) {
                    field.setId("player");
                    playerPlaced = true;
                }
                break;
            case "target":
                if(!targetPlaced) {
                    field.setId("target");
                    targetPlaced = true;
                }
                break;
            case "skull":
                field.setId("skull");
                break;
            case "coin":
                field.setId("coin");
                break;
            case "chest":
                field.setId("chest");
                break;
            case "heart":
                field.setId("heart");
                break;
            case "zombie":
                field.setId("zombie");
                break;

        }
    }

    public void setCurrentEditorElement(String newElement){
        this.currentEditorElement = newElement;
    }

    public String getLevelStructure() throws Exception{
        StringBuilder levelStructure = new StringBuilder();
        int playerAmount = 0;
        int targetAmount = 0;
        for (Pane field: fields) {
            if(field.getId().equals("player")){
                playerAmount++;
            }
            if (field.getId().equals("target")){
                targetAmount++;
            }
            levelStructure.append(getFieldCode(field.getId())).append("/");
        }
        if(playerAmount < 1 || targetAmount < 1|| playerAmount > 1 || targetAmount > 1){
            throw new Exception("You have to place exact 1 player and target!");
        }
        return levelStructure.toString();
    }

    private String getFieldCode(String id){
        String code = null;
        switch (id){
            case "field":
                code = "f";
                break;
            case "hole":
                code = "h";
                break;
            case "obstacle":
                code = "o";
                break;
            case "player":
                code = "p";
                break;
            case "target":
                code = "t";
                break;
            case "skull":
                code = "sk";
                break;
            case "coin":
                code = "c";
                break;
            case "chest":
                code = "ch";
                break;
            case "heart":
                code = "ht";
                break;
            case "zombie":
                code = "z";
                break;
        }
        return code;
    }

}

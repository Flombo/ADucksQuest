package Rendering.Windows.ControllerHelper.Builder;

import Database.DatabaseHelper;
import Rendering.Windows.Controller.LeveleditorController;
import Rendering.Windows.ControllerHelper.DragHelper;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class LeveleditorSaveDialogBuilder {

    private LevelEditorMapBuilder mapBuilder;
    private LeveleditorController controller;
    private DatabaseHelper databaseHelper;
    private DragHelper dragHelper;

    public LeveleditorSaveDialogBuilder(LeveleditorController controller, LevelEditorMapBuilder mapBuilder){
        this.mapBuilder = mapBuilder;
        this.controller = controller;
        this.databaseHelper = new DatabaseHelper();
        this.dragHelper = new DragHelper();
    }

    public Pane buildSaveDialog(byte[] thumbnail, int x, int y){
        Label txt = this.buildLabel("Save and upload level", "saveText", 125, 25);
        Label name = this.buildLabel("Levelname:", "levelNameLabel", 50, 75);
        Label tags = this.buildLabel("Tags:", "levelTagsLabel", 50, 150);
        TextField nameInput = this.buildTextField(150, 75);
        TextField tagInput = this.buildTextField(150, 150);
        Button saveButton = this.buildDialogButton("save", "saveButton", 50, 250);
        this.saveEventHandler(saveButton, nameInput, tagInput, thumbnail, x, y);
        Button cancelButton = this.buildDialogButton("cancel", "cancelButton", 150, 250);
        this.saveEventHandler(cancelButton, nameInput, tagInput, null, x, y);
        Pane pane = new Pane(txt, name, tags, nameInput, tagInput, saveButton, cancelButton);
        pane.setId("saveDialog");
        pane.setLayoutY(60);
        pane.setLayoutX(60);
        this.dragHelper.addHandler(pane);
        return pane;
    }

    private TextField buildTextField(int x, int y){
        TextField txt = new TextField();
        txt.setLayoutX(x);
        txt.setLayoutY(y);
        return txt;
    }

    private Label buildLabel(String txt, String id, int x, int y){
        Label label = new Label(txt);
        label.setId(id);
        label.setLayoutX(x);
        label.setLayoutY(y);
        return label;
    }

    private Button buildDialogButton(String text, String id, int x, int y){
        Button btn = new Button(text);
        btn.setId(id);
        btn.setLayoutY(y);
        btn.setLayoutX(x);
        return btn;
    }

    private void saveEventHandler(Button button, TextField name, TextField tags, byte[] thumbnail, int x, int y){
        this.addClickHandler(button, name, tags, thumbnail, x, y);
        button.addEventFilter(MouseEvent.MOUSE_ENTERED_TARGET, controller::onHoverIn);
        button.addEventFilter(MouseEvent.MOUSE_EXITED_TARGET, controller::onHoverOut);
    }

    private void addClickHandler(Button button, TextField name, TextField tags, byte[] thumbnail, int x, int y){
        button.addEventFilter(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            Node source = (Node) mouseEvent.getSource();
            String levelCode = null;
            try {
                levelCode = mapBuilder.getLevelStructure();
            } catch (Exception e) {
                controller.displayErrorMsg(e.getLocalizedMessage());
            }
            if(source.getId().equals("saveButton")){
                this.saveLevel(levelCode, name.getText(), tags.getText(), thumbnail, x, y);
                controller.setLevelIsSaved(true);
            } else {
                controller.removeSaveDialog();
            }
        });
    }

    private void saveLevel(String levelCode, String name, String tags, byte[] thumbnail, int x, int y){
        if(levelCode != null) {
            if (name.length() < 50 && tags.length() < 100 && levelCode.length() < 1000 && name.length() >= 5 && tags.length() >= 5) {
                if (thumbnail.length < 60000) {
                    databaseHelper.insertLevel(name, tags, levelCode, thumbnail, x, y);
                    controller.removeSaveDialog();
                    controller.showSuccessDialog();
                } else {
                    try {
                        throw new Exception("Thumbnail too big!");
                    } catch (Exception e){
                        controller.displayErrorMsg(e.getLocalizedMessage());
                    }
                }
            } else {
                try {
                    throw new Exception("Levelname has to be around 5 and 50 characters. Tags have to be around 5 and 100 characters");
                } catch (Exception e) {
                    controller.displayErrorMsg(e.getLocalizedMessage());
                }
            }
        }
    }

}

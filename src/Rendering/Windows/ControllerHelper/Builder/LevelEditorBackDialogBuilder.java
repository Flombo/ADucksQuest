package Rendering.Windows.ControllerHelper.Builder;

import Rendering.Windows.Controller.LeveleditorController;
import Rendering.Windows.ControllerHelper.DragHelper;
import Rendering.Windows.Scenes.LeveleditorScene;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class LevelEditorBackDialogBuilder {

    private LeveleditorController controller;
    private DragHelper dragHelper;

    public LevelEditorBackDialogBuilder(LeveleditorController controller){
        this.controller = controller;
        this.dragHelper = new DragHelper();
    }

    public Pane buildBackDialog(LeveleditorScene leveleditorScene) {
        Label txt = new Label("Want to quit?");
        txt.setId("backText");
        txt.setLayoutX(125);
        txt.setLayoutY(25);
        Button saveButton = this.buildDialogButton("save", "saveButton", 50);
        this.backDialogHandler(saveButton, leveleditorScene);
        Button discardButton = this.buildDialogButton("discard", "discardButton", 150);
        this.backDialogHandler(discardButton, leveleditorScene);
        Button cancelButton = this.buildDialogButton("cancel", "cancelButton", 250);
        this.backDialogHandler(cancelButton, leveleditorScene);
        Pane pane = new Pane(txt, saveButton, discardButton, cancelButton);
        pane.setLayoutY(60);
        pane.setLayoutX(60);
        pane.setId("backDialog");
        this.dragHelper.addHandler(pane);
        return pane;
    }

    private Button buildDialogButton(String text, String id, int x){
        Button btn = new Button(text);
        btn.setId(id);
        btn.setLayoutY(100);
        btn.setLayoutX(x);
        return btn;
    }

    private void backDialogHandler(Button button, LeveleditorScene leveleditorScene){
        button.addEventFilter(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            Node source = (Node) mouseEvent.getSource();
            switch (source.getId()){
                case "saveButton":
                    controller.showSaveDialog();
                    break;
                case "discardButton":
                    leveleditorScene.goToMenu();
                    break;
            }
            controller.removeBackDialog();
        });
        button.addEventFilter(MouseEvent.MOUSE_ENTERED_TARGET, controller::onHoverIn);
        button.addEventFilter(MouseEvent.MOUSE_EXITED_TARGET, controller::onHoverOut);
    }

}

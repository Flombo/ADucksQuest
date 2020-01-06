package Rendering.Windows.ControllerHelper;

import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class DragHelper {

    private double deltaX;
    private double deltaY;

    public void addHandler(Pane dialog){
        this.addMousePressHandler(dialog);
        this.addDragHandler(dialog);
    }

    private void addMousePressHandler(Pane dialog){
        dialog.addEventFilter(MouseEvent.MOUSE_PRESSED, mouseEvent -> {
            if(mouseEvent.isPrimaryButtonDown()){
                deltaX = dialog.getLayoutX() - mouseEvent.getSceneX();
                deltaY = dialog.getLayoutY() - mouseEvent.getSceneY();
            }
        });
    }

    private void addDragHandler(Pane dialog) {
        dialog.addEventFilter(MouseEvent.MOUSE_DRAGGED, mouseEvent -> {
            if (mouseEvent.isPrimaryButtonDown()) {
                dialog.getScene().setCursor(Cursor.CLOSED_HAND);
                dialog.setLayoutX(mouseEvent.getSceneX() + deltaX);
                dialog.setLayoutY(mouseEvent.getSceneY() + deltaY);
            }
        });
    }

}

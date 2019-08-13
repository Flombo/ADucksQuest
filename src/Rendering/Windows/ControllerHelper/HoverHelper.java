package Rendering.Windows.ControllerHelper;

import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

public class HoverHelper {

    private double defaultHeight;

    public void onHoverIn(MouseEvent mouseEvent) {
        Button button = (Button) mouseEvent.getSource();
        if(!button.getId().equals("menuButton")) {
            this.defaultHeight = button.getHeight();
            button.setMinHeight(60);
        }
        button.setOpacity(0.5);
    }

    public void onHoverOut(MouseEvent mouseEvent) {
        Button button = (Button) mouseEvent.getSource();
        if(!button.getId().equals("menuButton")) {
            button.setMinHeight(this.defaultHeight);
        }
        button.setOpacity(1);
    }

}

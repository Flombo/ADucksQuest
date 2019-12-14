package Rendering.Windows.ControllerHelper;

import javafx.scene.control.Button;
import javafx.scene.effect.Glow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class HoverHelper {

    public void onHoverIn(MouseEvent mouseEvent) {
        Button button = (Button) mouseEvent.getSource();
        if(!button.getId().equals("menuButton")) {
            Glow glow = new Glow();
            glow.setLevel(0.7);
            button.setEffect(glow);
        }
        button.setOpacity(0.5);
    }

    public void onHoverOut(MouseEvent mouseEvent) {
        Button button = (Button) mouseEvent.getSource();
        if(!button.getId().equals("menuButton")) {
            button.setEffect(null);
        }
        button.setOpacity(1);
    }

    protected void onHoverInElement(MouseEvent mouseEvent) {
        Pane pane = (Pane) mouseEvent.getSource();
        Glow glow = new Glow();
        glow.setLevel(0.7);
        pane.setEffect(glow);
        pane.setOpacity(0.5);
    }

    protected void onHoverOutElement(MouseEvent mouseEvent) {
        Pane pane = (Pane) mouseEvent.getSource();
        pane.setEffect(null);
        pane.setOpacity(1);
    }

}

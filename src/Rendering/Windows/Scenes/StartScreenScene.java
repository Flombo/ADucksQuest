package Rendering.Windows.Scenes;

import Rendering.View;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class StartScreenScene extends Scene {

    private View view;

    public StartScreenScene(Parent parent, double v, double v1, View view) {
        super(parent, v, v1);
        this.view = view;
        this.setEventHandler();
    }

    private void setEventHandler(){
        this.setOnKeyReleased(keyEvent -> loadMainMenu());
    }

    private void loadMainMenu(){
        this.view.setMainMenu();
    }

}

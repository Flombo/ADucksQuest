package Rendering.Windows.Scenes;

import Rendering.View;
import Rendering.Windows.Controller.OptionsController;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class OptionsScene extends Scene {

    private View view;
    private OptionsController optionsController;

    public OptionsScene(Parent parent, double width, double height, View view, OptionsController optionsController) {
        super(parent, width, height);
        this.view = view;
        this.getStylesheets().add("/Rendering/Windows/Style/Options.css");
        this.optionsController = optionsController;
    }

    public void goToMenu() {
        this.view.showMainMenu();
    }

    public void setSize(double heigth, double width){
        this.view.setSize(heigth, width);
    }

    public void setFullScreen() {
        this.view.setFullScreen(true);
        this.view.setSize(this.getHeight(), this.getWidth());
    }

    public void setWindowMode(){
        this.view.setFullScreen(false);
        this.view.setSize(this.getHeight(), this.getWidth());
    }

    public void resize(){
        this.optionsController.resize(this.getHeight(), this.getWidth());
    }
}

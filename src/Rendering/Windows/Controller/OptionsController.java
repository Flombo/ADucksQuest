package Rendering.Windows.Controller;

import Rendering.Windows.Config.ConfigManager;
import Rendering.Windows.ControllerHelper.HoverHelper;
import Rendering.Windows.Scenes.OptionsScene;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import java.net.URL;
import java.util.ResourceBundle;

public class OptionsController extends HoverHelper implements Initializable {

    @FXML
    private AnchorPane anchor;
    @FXML
    private Button back;
    @FXML
    private Button save;
    @FXML
    private Button defaultButton;
    @FXML
    private Label options;
    @FXML
    private TextField widthField;
    @FXML
    private TextField heightField;
    @FXML
    private CheckBox muteCheck;
    @FXML
    private CheckBox windowmodeCheck;
    @FXML
    private CheckBox fullscreenCheck;
    private ConfigManager configManager;
    private Label width;
    private Label height;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.configManager = new ConfigManager();
        this.muteCheck.setSelected(this.configManager.getMuted());
        this.windowmodeCheck.setSelected(this.configManager.getWindowMode());
        this.fullscreenCheck.setSelected(this.configManager.getFullscreen());
        this.width = new Label("Width");
        this.height = new Label("Height");
        this.anchor.getChildren().add(width);
        this.anchor.getChildren().add(height);
        this.options.setPrefWidth(this.options.getText().length() * 20);
        this.anchor.widthProperty().addListener((observable -> reLayout()));
        this.anchor.heightProperty().addListener((observable -> reLayout()));
        this.widthField.textProperty().addListener((observable -> {
            if(!heightField.textProperty().getValue().equals("") && !widthField.textProperty().getValue().equals("")) {
                resize(Double.parseDouble(heightField.textProperty().getValue()), Double.parseDouble(widthField.textProperty().getValue()));
            }
        }));
        this.heightField.textProperty().addListener((observable -> {
            if(!heightField.textProperty().getValue().equals("") && !widthField.textProperty().getValue().equals("")) {
                resize(Double.parseDouble(heightField.textProperty().getValue()), Double.parseDouble(widthField.textProperty().getValue()));
            }
        }));
    }

    public void resize(double height, double width) {
        OptionsScene optionsScene = (OptionsScene) this.anchor.getScene();
        if(height >= 300 && width >= 300) {
            optionsScene.setSize(height, width);
        }
        this.fullscreenCheck.setSelected(false);
        this.windowmodeCheck.setSelected(true);
    }

    private void reLayout() {
        double optionsLayoutX = (this.anchor.widthProperty().getValue() - this.options.widthProperty().getValue()) / 2;
        this.options.setLayoutY(this.anchor.heightProperty().getValue() / 8);
        if(optionsLayoutX >= 25) {
            this.options.setLayoutX(optionsLayoutX);
        }
        this.setCheckboxLayout();
        this.setFieldLayout();
        this.setButtonLayout();
    }

    private void setButtonLayout(){
        double width = 150;
        this.back.setPrefWidth(width);
        this.save.setPrefWidth(width);
        this.defaultButton.setPrefWidth(width);
        this.back.setLayoutY(this.widthField.getLayoutY() +  200);
        this.back.setLayoutX(this.windowmodeCheck.getLayoutX());
        this.defaultButton.setLayoutY(this.widthField.getLayoutY() + 200);
        this.defaultButton.setLayoutX(this.back.getLayoutX() + this.back.getWidth() +  50);
        this.save.setLayoutY(this.widthField.getLayoutY() + 200);
        this.save.setLayoutX(this.defaultButton.getLayoutX() + this.defaultButton.getWidth() + 50);
    }

    private void setCheckboxLayout(){
        double layoutX = this.options.getLayoutX() - 200;
        this.windowmodeCheck.setLayoutY(this.options.getLayoutY() + 75);
        if(layoutX >= 25) {
            this.windowmodeCheck.setLayoutX(this.options.getLayoutX() - 200);
        }
        this.fullscreenCheck.setLayoutY(this.windowmodeCheck.getLayoutY() + 75);
        this.fullscreenCheck.setLayoutX(this.windowmodeCheck.getLayoutX());
        this.muteCheck.setLayoutY(this.fullscreenCheck.getLayoutY() + 75);
        this.muteCheck.setLayoutX(this.fullscreenCheck.getLayoutX());
    }

    private void setFieldLayout(){
        this.heightField.setLayoutY(this.muteCheck.getLayoutY() +  50);
        this.heightField.setLayoutX(this.muteCheck.getLayoutX());
        this.widthField.setLayoutY(this.heightField.getLayoutY() + 50);
        this.widthField.setLayoutX(this.heightField.getLayoutX());
        this.widthField.promptTextProperty().setValue(this.anchor.widthProperty().getValue().toString());
        this.heightField.promptTextProperty().setValue(this.anchor.heightProperty().getValue().toString());
        this.setFieldLabelLayout();
    }

    private void setFieldLabelLayout(){
        this.width.setLayoutX(this.widthField.getLayoutX() + this.widthField.widthProperty().getValue() + 5);
        this.width.setLayoutY(this.widthField.getLayoutY());
        this.height.setLayoutX(this.heightField.getLayoutX() + this.heightField.widthProperty().getValue() + 5);
        this.height.setLayoutY(this.heightField.getLayoutY());
    }

    @FXML
    private void onClick(MouseEvent event){
        Button btn = (Button) event.getSource();
        OptionsScene optionsScene = (OptionsScene) btn.getScene();
        switch (btn.getId()){
            case "back":
                optionsScene.goToMenu();
                break;
            case "defaultButton":
                optionsScene.setSize(800, 700);
                this.fullscreenCheck.setSelected(false);
                this.windowmodeCheck.setSelected(true);
                break;
            case "save":
                this.saveConfig();
                break;
        }
    }

    private void saveConfig() {
        saveOptions();
    }

    public void onCheck(MouseEvent mouseEvent) {
        CheckBox checkBox = (CheckBox)mouseEvent.getSource();
        OptionsScene optionsScene = (OptionsScene) checkBox.getScene();
        switch (checkBox.getId()){
            case "windowmodeCheck":
                if(fullscreenCheck.isSelected()){
                    fullscreenCheck.setSelected(false);
                }
                if(windowmodeCheck.isSelected()){
                    optionsScene.setFullScreen();
                }
                optionsScene.setWindowMode();
                break;
            case "fullscreenCheck":
                if(windowmodeCheck.isSelected()){
                    windowmodeCheck.setSelected(false);
                }
                if(fullscreenCheck.isSelected()){
                    optionsScene.setWindowMode();
                }
                optionsScene.setFullScreen();
                break;
            case "muteCheck":
                System.out.println("muted");
                break;
        }
    }

    private void saveOptions() {
        Rectangle2D rect = Screen.getScreens().get(0).getBounds();
        OptionsScene optionsScene = (OptionsScene) this.fullscreenCheck.getScene();
        configManager.writeValue("fullScreen", String.valueOf(this.fullscreenCheck.isSelected()));
        configManager.writeValue("windowMode", String.valueOf(this.windowmodeCheck.isSelected()));
        if(this.fullscreenCheck.isSelected()) {
            configManager.writeValue("width", String.valueOf(rect.getWidth()));
            configManager.writeValue("height", String.valueOf(rect.getHeight()));
        } else {
            configManager.writeValue("width", this.widthField.textProperty().getValue());
            configManager.writeValue("height", this.heightField.textProperty().getValue());
        }
        configManager.writeValue("muted", String.valueOf(this.muteCheck.isSelected()));
        optionsScene.setSize(Double.parseDouble(this.heightField.textProperty().getValue()), Double.parseDouble(this.widthField.textProperty().getValue()));
    }
}

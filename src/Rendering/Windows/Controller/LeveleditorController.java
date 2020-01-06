package Rendering.Windows.Controller;

import Rendering.Windows.ControllerHelper.Builder.LevelEditorBackDialogBuilder;
import Rendering.Windows.ControllerHelper.Builder.LeveleditorSaveDialogBuilder;
import Rendering.Windows.ControllerHelper.HoverHelper;
import Rendering.Windows.ControllerHelper.Builder.LevelEditorElementsDialogBuilder;
import Rendering.Windows.ControllerHelper.Builder.LevelEditorMapBuilder;
import Rendering.Windows.Scenes.LeveleditorScene;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.ResourceBundle;

public class LeveleditorController extends HoverHelper implements Initializable{

    @FXML
    Button elementsButton;
    @FXML
    Pane levelPane;
    @FXML
    Slider ySlider;
    @FXML
    Slider xSlider;
    private Pane elementsDialog;
    private Pane backDialog;
    private Pane saveDialog;
    private LevelEditorMapBuilder levelEditorMapBuilder;
    private boolean elementButtonActive = false;
    private boolean levelIsSaved = false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        levelEditorMapBuilder = new LevelEditorMapBuilder(this);
        addSliderListener(ySlider);
        addSliderListener(xSlider);
    }

    private void addSliderListener(Slider slider){
        levelEditorMapBuilder.onDrag(levelPane, xSlider, ySlider);
        slider.valueProperty().addListener((observable -> {
            levelEditorMapBuilder.onDrag(levelPane, xSlider,ySlider);
            elementsDialog = null;
            this.resetElementsButton();
        }));
    }

    public boolean isLevelIsSaved() {
        return levelIsSaved;
    }

    public void setLevelIsSaved(boolean levelIsSaved) {
        this.levelIsSaved = levelIsSaved;
    }

    @FXML
    public void onButtonClicked(MouseEvent mouseEvent) {
        Node source = (Node) mouseEvent.getSource();
        LeveleditorScene leveleditorScene = (LeveleditorScene) source.getScene();
        if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
            switch (source.getId()) {
                case "backButton":
                    this.clear();
                    if(!levelIsSaved) {
                        this.showBackDialog(leveleditorScene);
                    } else {
                        leveleditorScene.goToMenu();
                    }
                    break;
                case "clearButton":
                    this.clear();
                    break;
                case "saveButton":
                    this.removeElementsDialog();
                    this.removeBackDialog();
                    this.showSaveDialog();
                    break;
                case "elementsButton":
                    if (!elementButtonActive) {
                        source.setStyle("-fx-border-color: lightgreen; -fx-border-width: 2px");
                        elementButtonActive = true;
                    } else {
                       this.resetElementsButton();
                    }
                    this.showElementsDialog();
            }
        }
    }

    public void displayErrorMsg(String localizedMessage) {
        Label errorMsg = new Label(localizedMessage);
        errorMsg.setId("errorMsg");
        errorMsg.setLayoutY(200);
        errorMsg.setLayoutX(25);
        saveDialog.getChildren().add(errorMsg);
    }

    public void showSaveDialog(){
        if(saveDialog == null) {
            LeveleditorSaveDialogBuilder saveDialogBuilder = new LeveleditorSaveDialogBuilder(this, levelEditorMapBuilder);
            saveDialog = saveDialogBuilder.buildSaveDialog(this.getSnapshot(), (int)xSlider.getValue(), (int)ySlider.getValue());
            this.levelPane.getChildren().add(saveDialog);
        } else {
            this.levelPane.getChildren().remove(saveDialog);
            saveDialog = null;
        }
    }

    private byte[] getSnapshot(){
        WritableImage img = new WritableImage((int)this.levelPane.widthProperty().get(), (int)this.levelPane.heightProperty().get());
        this.levelPane.snapshot(new SnapshotParameters(), img);
        File file = new File("snapshot.png");
        byte[] fileContent = null;
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(img, null), "png", file);
            BufferedImage image = ImageIO.read(file);
            BufferedImage resized = resize(image);
            ImageIO.write(resized, "png", file);
            System.out.println("snapshot saved: " + file.getAbsolutePath());
            fileContent = Files.readAllBytes(file.toPath());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return fileContent;
    }

    private BufferedImage resize(BufferedImage img) {
        Image tmp = img.getScaledInstance(150, 150, Image.SCALE_SMOOTH);
        BufferedImage resized = new BufferedImage(150, 150, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resized.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
        return resized;
    }

    private void showBackDialog(LeveleditorScene leveleditorScene){
        if(backDialog == null) {
            LevelEditorBackDialogBuilder backDialogBuilder = new LevelEditorBackDialogBuilder(this);
            backDialog = backDialogBuilder.buildBackDialog(leveleditorScene);
            this.levelPane.getChildren().add(backDialog);
        } else {
            this.levelPane.getChildren().remove(backDialog);
            backDialog = null;
        }
    }

    private void showElementsDialog(){
        if(elementsDialog == null) {
            LevelEditorElementsDialogBuilder dialogBuilder = new LevelEditorElementsDialogBuilder(levelEditorMapBuilder);
            elementsDialog = dialogBuilder.buildElementsDialog();
            this.levelPane.getChildren().add(elementsDialog);
        } else {
            this.removeElementsDialog();
        }
    }

    public void removeElementsDialog(){
        if(elementsDialog != null){
            this.levelPane.getChildren().remove(elementsDialog);
            elementsDialog = null;
            this.resetElementsButton();
        }
    }

    private void resetElementsButton(){
        elementsButton.setStyle("-fx-border: none");
        elementButtonActive = false;
    }

    public void removeSaveDialog(){
        levelPane.getChildren().remove(saveDialog);
    }

    public void removeBackDialog(){
        levelPane.getChildren().remove(backDialog);
    }

    private void clear(){
        ySlider.setValue(5);
        xSlider.setValue(5);
        elementsDialog = null;
        this.resetElementsButton();
        backDialog = null;
        levelPane.getChildren().clear();
        levelEditorMapBuilder.setCurrentEditorElement("field");
        levelEditorMapBuilder.onDrag(levelPane, xSlider, ySlider);
    }

}

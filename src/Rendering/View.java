package Rendering;

import GameLogic.GameInit;
import GameObjects.Field_like_Objects.Field;
import GameObjects.Player.Player;
import Rendering.Windows.Controller.RenderViewController;
import Rendering.Windows.Scenes.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

import java.io.IOException;

public class View extends Application {

	private Stage stage;
	private GameInit gameInit;
	private RenderViewScene renderViewScene;
	private RenderViewController renderViewController;
	private MainMenuScene mainMenuScene;
	private IngameMenuScene ingameMenuScene;
	private StartScreenScene startScreenScene;
	private DeathScene deathScene;
	private Field[][] fields;
	private double height;
	private double width;

    public View(){
        this.height = 750;
        this.width = 600;
    }

	public void setIsRunning(boolean isRunning){
        Platform.runLater(this.gameInit.switchEnemyMovement(isRunning));
        Platform.runLater(this.gameInit.switchCollectiblesAnimation(isRunning));
        Platform.runLater(this.renderViewScene.setIsRunning(isRunning));
	}

	private FXMLLoader getFxmlloader(String path){
			return new FXMLLoader(getClass().getResource(path));
	}

    public void showGameMenu(){
        this.stage.setScene(this.ingameMenuScene);
    }

    private void loadScenes(){
	    this.initStartScene();
	    this.initMainMenuScene();
	    this.initIngameMenuScene();
        this.initRenderScene();
        this.initDeathScene();
    }

    private void initDeathScene(){
        FXMLLoader loader = this.getFxmlloader("Windows/Fxml/DeathMenu.fxml");
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (root != null) {
            this.deathScene = new DeathScene(root, this.height, this.width);
        }
    }

    private void initIngameMenuScene(){
        FXMLLoader loader = this.getFxmlloader("Windows/Fxml/IngameMenu.fxml");
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (root != null) {
            this.ingameMenuScene = new IngameMenuScene(root, this.width, this.height);
        }
    }

    private void initMainMenuScene(){
        FXMLLoader loader = this.getFxmlloader("Windows/Fxml/MainMenu.fxml");
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (root != null) {
            this.mainMenuScene = new MainMenuScene(root, this.width, this.height, this);
        }
    }

    private void initStartScene(){
        FXMLLoader loader = this.getFxmlloader("Windows/Fxml/StartScreen.fxml");
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (root != null) {
            this.startScreenScene = new StartScreenScene(root, 600, 750, this);
        }
    }

    public void setMainMenu(){
	    this.stage.setScene(this.mainMenuScene);
    }

    private void initRenderScene(){
        this.fields = this.gameInit.initLevel();
        FXMLLoader loader = this.getFxmlloader("Windows/Fxml/RenderView.fxml");
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (root != null) {
            this.renderViewController = loader.getController();
            this.renderViewScene = new RenderViewScene(fields, this.stage, root, 600, 750);
        }
    }

    public void closeGame(){
        this.setIsRunning(false);
        Platform.exit();
    }

    public void showDeathMenu(){
        System.out.println("Death");
        this.setIsRunning(false);
        Platform.runLater(this.setDeathMenu());
    }

    private Runnable setDeathMenu(){
        return (()-> this.stage.setScene(this.deathScene));
    }

    public void initLevel(){
        Platform.runLater(this.renderViewScene.initLevel(fields, this.renderViewController, this.gameInit));
        this.stage.setScene(this.renderViewScene);
    }

	@Override
	public void start(Stage stage) {
		this.stage = stage;
		this.gameInit = new GameInit(15, 15, 20, this);
        this.loadScenes();
        this.stage.setScene(this.startScreenScene);
        this.stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

    public void setPlayerLives(Player player, int i) {
        Platform.runLater(player.setLives(i));
    }
}

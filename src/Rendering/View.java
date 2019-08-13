package Rendering;

import GameLogic.GameInit;
import GameObjects.Field_like_Objects.Field;
import GameObjects.Player.Player;
import Rendering.Windows.Controller.DeathMenuController;
import Rendering.Windows.Controller.RenderViewController;
import Rendering.Windows.Controller.SuccessMenuController;
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
	private SuccessMenuScene successMenuScene;
	private StartScreenScene startScreenScene;
	private DeathScene deathScene;
	private Field[][] fields;
	private double height;
	private double width;
    private DeathMenuController deathMenuController;
    private SuccessMenuController successMenuController;

    public View(){
        this.height = 750;
        this.width = 600;
    }

	public void setIsRunningFalse(){
        Platform.runLater(this.gameInit.switchEnemyMovement(false));
        Platform.runLater(this.gameInit.switchCollectiblesAnimation(false));
        Platform.runLater(this.renderViewScene.setIsRunning(false));
	}

	private FXMLLoader getFxmlloader(String path){
			return new FXMLLoader(getClass().getResource(path));
	}

    //loads all the scenes
    private void loadScenes(){
	    this.initStartScene();
	    this.initMainMenuScene();
	    this.initIngameMenuScene();
        this.initRenderScene();
        this.initDeathScene();
        this.initSuccessMenuScene();
    }

    //loads deathscene
    private void initDeathScene(){
        FXMLLoader loader = this.getFxmlloader("Windows/Fxml/DeathMenu.fxml");
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (root != null) {
            this.deathMenuController = loader.getController();
            this.deathScene = new DeathScene(root, this.height, this.width, this);
        }
    }

    //gets player out of fields
    public Player getPlayer(){
        Player player = null;
        for(Field[] fields : this.fields){
            for(Field field : fields){
                if(field.getName().equals("GameObjects.Player.Player")){
                    player = (Player) field;
                }
            }
        }
        return player;
    }

    //loads ingamemenu
    private void initSuccessMenuScene(){
        FXMLLoader loader = this.getFxmlloader("Windows/Fxml/SuccessMenu.fxml");
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (root != null) {
            this.successMenuController = loader.getController();
            this.successMenuScene = new SuccessMenuScene(root, this.width, this.height, this);
        }
    }

    //loads ingamemenu
    private void initIngameMenuScene(){
        FXMLLoader loader = this.getFxmlloader("Windows/Fxml/IngameMenu.fxml");
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (root != null) {
            this.ingameMenuScene = new IngameMenuScene(root, this.width, this.height, this);
        }
    }

    //loads mainmenu
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

    //loads startscreen
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

    //loads renderscene
    private void initRenderScene(){
        FXMLLoader loader = this.getFxmlloader("Windows/Fxml/RenderView.fxml");
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (root != null) {
            this.renderViewController = loader.getController();
            this.renderViewScene = new RenderViewScene(root, 600, 750, this);
        }
    }

    //closes game
    public void closeGame(){
        this.setIsRunningFalse();
        Platform.exit();
    }

    public void showSuccessMenu(){
        this.setIsRunningFalse();
        Platform.runLater(this.setSuccessMenu());
    }

    private Runnable setSuccessMenu(){
        return (() -> {
            stage.setScene(successMenuScene);
            successMenuController.setLabelBindings(this.getPlayer());
        });
    }

    //shows Ingamemenu
    public void showIngameMenu(){
        this.setIsRunningFalse();
        this.stage.setScene(this.ingameMenuScene);
    }

    //shows mainmenu
    public void showMainMenu(){
        this.stage.setScene(this.mainMenuScene);
    }

    //shows deathmenu
    public void showDeathMenu(){
        this.setIsRunningFalse();
        Platform.runLater(this.setDeathMenu());
    }

    //sets deathmenu to stage
    private Runnable setDeathMenu(){
        return (() -> {
            stage.setScene(deathScene);
            deathMenuController.setLabels();
        });
    }

    //resumes game
    public void resumeGame(){
        this.stage.setScene(this.renderViewScene);
        this.gameInit.resumeCollectibleAnimations();
        this.renderViewScene.initLevel(this.fields, this.renderViewController, this.gameInit);
    }

    //inits level
    public void initLevel(){
        this.fields = this.gameInit.initLevel();
        this.renderViewScene.initLevel(fields, this.renderViewController, this.gameInit);
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

	//has to be here because changes to gui must be in javafx thread
    public void setPlayerLives(Player player, int lives) {
        Platform.runLater(player.setLives(lives, this));
    }
}

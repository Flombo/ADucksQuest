package Rendering;

import GameLogic.GameInit;
import GameObjects.Field_like_Objects.Field;
import GameObjects.Player.Player;
import Rendering.Windows.Config.ConfigManager;
import Rendering.Windows.Controller.*;
import Rendering.Windows.Scenes.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.IOException;

public class View extends Application {

	private Stage stage;
	private GameInit gameInit;
	private RenderViewScene renderViewScene;
	private RenderViewController renderViewController;
	private MainMenuScene mainMenuScene;
	private IngameMenuScene ingameMenuScene;
	private SuccessMenuScene successMenuScene;
    private SuccessMenuController successMenuController;
	private StartScreenScene startScreenScene;
	private DeathScene deathScene;
    private DeathMenuController deathMenuController;
	private LeveleditorScene leveleditorScene;
	private LevelSelectionScene levelSelectionScene;
	private LevelSelectionController levelSelectionController;
	private OptionsScene optionsScene;
    private OptionsController optionsController;
    private Field[][] fields;
	private double height;
	private double width;
    private String currentLevelCode;
    private int levelXAxis;
    private int levelYAxis;

    public View(){
        ConfigManager configManager = new ConfigManager();
        this.height = configManager.getHeight();
        this.width = configManager.getWidth();
    }

	public synchronized void setIsRunningFalse(){
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
        this.initLeveleditorScene();
        this.initLevelSelectionScene();
        this.initOptionsScene();
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

    private void initLevelSelectionScene(){
        FXMLLoader loader = this.getFxmlloader("Windows/Fxml/Levelselection.fxml");
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (root != null) {
            if(this.levelSelectionController == null){
                this.levelSelectionController = loader.getController();
            }
            this.levelSelectionScene = new LevelSelectionScene(root, this.width, this.height, this, this.levelSelectionController);
        }
    }

    private void initLeveleditorScene(){
        FXMLLoader loader = this.getFxmlloader("Windows/Fxml/Leveleditor.fxml");
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (root != null) {
            LeveleditorController leveleditorController = loader.getController();
            this.leveleditorScene = new LeveleditorScene(root, this.height, this.width, this, leveleditorController);
        }
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
            this.deathScene = new DeathScene(root, this.width, this.height, this);
        }
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
            IngameMenuController ingameMenuController = loader.getController();
            this.ingameMenuScene = new IngameMenuScene(root, this.width, this.height, this, ingameMenuController);
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
            this.startScreenScene = new StartScreenScene(root, this.width, this.height, this);
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
            this.renderViewScene = new RenderViewScene(root, this.width, this.height, this);
        }
    }

    private void initOptionsScene(){
        FXMLLoader loader = this.getFxmlloader("Windows/Fxml/Options.fxml");
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (root != null) {
            this.optionsController = loader.getController();
            this.optionsScene = new OptionsScene(root, this.width, this.height, this, this.optionsController);
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
            Player player = this.getPlayer();
            player.setEarnedCoins();
            stage.setScene(successMenuScene);
            successMenuController.setLabelBindings(player);
            successMenuController.initHandling();
        });
    }

    //shows Ingamemenu
    public void showIngameMenu(){
        this.setIsRunningFalse();
        this.stage.setScene(this.ingameMenuScene);
        this.ingameMenuScene.resize();
    }

    //shows mainmenu
    public void showMainMenu(){
        this.stage.setScene(this.mainMenuScene);
    }

    public void showLeveleditor(){
        this.stage.setScene(this.leveleditorScene);
        this.leveleditorScene.resize();
    }

    public void showLevelSelection(){
        this.fields = null;
        this.levelSelectionScene.buildLevels();
        this.stage.setScene(this.levelSelectionScene);
    }

    //shows deathmenu
    public void showDeathMenu(){
        Platform.runLater(this.setDeathMenu());
    }

    public void showOptions() {
        this.stage.setScene(this.optionsScene);
        this.optionsScene.resize();
    }

    //sets deathmenu to stage
    private Runnable setDeathMenu(){
        return (() -> {
            stage.setScene(deathScene);
            deathMenuController.setLabels();
            deathMenuController.initHandling();
        });
    }

    //resumes game
    public void resumeGame(){
        this.stage.setScene(this.renderViewScene);
        this.renderViewScene.resumeLevel(this.fields, this.renderViewController, this.gameInit, this.levelXAxis, this.levelYAxis);
    }

    //inits level
    public void initLevel(String levelCode, int x, int y){
        this.currentLevelCode = levelCode;
        this.levelXAxis = x;
        this.levelYAxis = y;
        this.fields = this.gameInit.initLevel(levelCode, x, y);
        this.renderViewScene.initLevel(fields, this.renderViewController, this.gameInit, x, y);
        this.stage.setScene(this.renderViewScene);
    }

	@Override
	public void start(Stage stage) {
		this.stage = stage;
		this.gameInit = new GameInit(this);
        this.loadScenes();
        this.stage.setScene(this.startScreenScene);
        this.stage.initStyle(StageStyle.UTILITY);
        this.stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

	//has to be here because changes to gui must be in javafx thread
    public synchronized void setPlayerLives(Player player, int lives) {
        Platform.runLater(player.setLives(lives, this));
    }

    public void resetRessources() {
        this.gameInit.resetRessources();
    }

    public String getCurrentLevelCode() {
        return currentLevelCode;
    }

    public int getLevelXAxis() {
        return levelXAxis;
    }

    public int getLevelYAxis() {
        return levelYAxis;
    }

    public void setFullScreen(boolean scale) {
        this.stage.setFullScreen(scale);
    }

    public void setSize(double height, double width) {
        this.height = height;
        this.width = width;
        this.stage.setWidth(width);
        this.stage.setHeight(height);
    }
}

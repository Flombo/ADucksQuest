package Rendering.Menus;

import GameLogic.GameInit;
import GameObjects.Player.Player;
import Rendering.View;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;

public class MainMenu extends Menu implements clickHandlerInterface{

	private JButton[] mainMenuButtons;

	public MainMenu(
			View view,
			int frameHeight,
			int frameWidth,
			GameInit gameInit,
			Color backgoundColor,
			Player player
	) {
		super(view, frameHeight, frameWidth, gameInit, backgoundColor, player);
		this.buildButtons();
	}

	private void buildButtons(){
		JButton startGameButton = new JButton("Start Run");
		JButton shopButton = new JButton("Shop");
		JButton levelEditorButton = new JButton("Leveleditor");
		JButton customizeButton = new JButton("Customize Character");
		JButton quitButton = new JButton("Quit Game");
		this.mainMenuButtons = new JButton[]{
				startGameButton,
				shopButton,
				levelEditorButton,
				customizeButton,
				quitButton
		};
	}

	//builds main menu components
	public void showMainMenu(){
		this.showMenu(this.mainMenuButtons);
		this.addEventListener();
	}

	//add mouselistener to buttons
	@Override
	public void addEventListener() {
		for(JButton jButton : this.mainMenuButtons){
			jButton.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					super.mouseClicked(e);
					handleMouseClick(e);
				}
			});
		}
	}

	//checks which button was clicked and process function
	@Override
	public void handleMouseClick(MouseEvent e) {
		switch (((JButton)e.getSource()).getText()){
			case "Start Run":
				this.setVisible(false);
				this.removeButtons(this.mainMenuButtons, this);
				this.fields = this.gameInit.initLevel();
				this.view.initLevel(this.fields);
				this.gameInit.intitPlayerMovement();
				this.gameInit.initEnemyMovement();
				break;
			case "Quit Game":
				this.view.dispatchEvent(new WindowEvent(view, WindowEvent.WINDOW_CLOSING));
				break;
		}
	}
}
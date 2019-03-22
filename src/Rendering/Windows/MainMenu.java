package Rendering.Windows;

import GameLogic.GameInit;
import GameObjects.Field_like_Objects.Field;
import Rendering.View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;

public class MainMenu extends JPanel {

	private int frameWidth;
	private View view;
	private GameInit gameInit;
	private Field[][] fields;
	private JButton[] mainMenuButtons;
	private JButton[] gameMenuButtons;

	public MainMenu (View view, int frameHeight, int frameWidth, GameInit gameInit){

		this.gameInit = gameInit;
		this.view = view;
		this.frameWidth = frameWidth;
		this.setSize(new Dimension(this.frameWidth, frameHeight));
		this.setVisible(true);

	}

	public void showGameMenu(){
		this.removeButtons(this.mainMenuButtons);
		JButton resumeGame = new JButton("Resume Game");
		JButton goToMenu = new JButton("Go to Menu");
		this.gameMenuButtons = new JButton[]{resumeGame, goToMenu};
		this.styleButtons(this.gameMenuButtons);
		this.setLayout(this.gameMenuButtons);
		this.addEventlistener(this.gameMenuButtons);
		this.setVisible(true);
	}

	//builds main menu components
	public void showMainMenu(){
		this.removeButtons(this.gameMenuButtons);
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
		this.styleButtons(this.mainMenuButtons);
		this.setLayout(this.mainMenuButtons);
		this.addEventlistener(this.mainMenuButtons);
	}

	//removes jbuttons from panel
	private void removeButtons(JButton[] jButtons){
		if(jButtons != null) {
			for (JButton jButton : jButtons) {
				this.remove(jButton);
			}
		}
	}

	//checks which button was clicked and process function
	private void handleMouseClick(MouseEvent e){
		switch (((JButton)e.getSource()).getText()){
			case "Resume Game":
				this.view.initLevel(this.fields);
				this.view.requestFocusInWindow();
				break;
			case "Go to Menu":
				this.showMainMenu();
				break;
			case "Start Run":
				this.setVisible(false);
				this.fields = this.gameInit.initLevel();
				this.view.initLevel(this.fields);
				if(this.view.isRunning()) {
					this.gameInit.intitPlayerMovement();
					this.gameInit.initEnemyMovement();
				}
				break;
			case "Quit Game":
				this.view.dispatchEvent(new WindowEvent(view, WindowEvent.WINDOW_CLOSING));
				break;
		}
	}

	//add mouselistener to buttons
	private void addEventlistener(JButton[] jButtons){
		for(JButton jButton : jButtons){
			jButton.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					super.mouseClicked(e);
					handleMouseClick(e);
				}
			});
		}
	}

	//sets constraints to default
	private void setConstraintsToDefault(GridBagConstraints constraints){
		constraints.gridx = 0;
		constraints.gridy = 0;
	}

	//sets Layout
	private void setLayout(JButton[] jButtons){
		GridBagLayout gridBagLayout = new GridBagLayout();
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.VERTICAL;
		int counter = 100;
		this.setLayout(gridBagLayout);
		for(JButton jButton : jButtons){
			this.setConstraintsToDefault(constraints);
			constraints.ipady = 20;
			constraints.ipadx = 200;
			constraints.gridx = this.frameWidth / 2;
			constraints.gridy = counter;
			this.add(jButton, constraints);
			counter += 150;
		}
	}

	//style buttons
	private void styleButtons(JButton[] jButtons){
		for(JButton jButton : jButtons){
			jButton.setBackground(Color.DARK_GRAY);
			jButton.setForeground(Color.white);
			jButton.setSize(100, 100);
		}
	}
}
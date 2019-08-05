package Rendering.Menus;

import GameLogic.GameInit;
import GameObjects.Field_like_Objects.Field;
import GameObjects.Player.Player;
import Rendering.View;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class IngameMenu extends Menu implements clickHandlerInterface{

    private JButton[] gameMenuButtons;
    private Field[][] fields;

    public IngameMenu(
            View view,
            int frameHeight,
            int frameWidth,
            GameInit gameInit,
            Color backgoundColor,
            Player player,
            Field[][] fields
    ) {
        super(view, frameHeight, frameWidth, gameInit, backgoundColor, player);
        this.fields = fields;
    }

    public void showGameMenu(){
        if(this.gameMenuButtons == null) {
            JButton resumeGame = new JButton("Resume Game");
            JButton goToMenu = new JButton("Go to Menu");
            this.gameMenuButtons = new JButton[]{resumeGame, goToMenu};
            this.addEventListener();
        }
        this.showMenu(this.gameMenuButtons);
        this.setOpaque(true);
        this.setVisible(true);
        this.revalidate();
    }

    @Override
    public void addEventListener() {
        for(JButton jButton : this.gameMenuButtons){
            jButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e);
                    handleMouseClick(e);
                }
            });
        }
    }

    @Override
    public void handleMouseClick(MouseEvent e) {
        switch (((JButton)e.getSource()).getText()){
            case "Resume Game":
                this.removeButtons(this.gameMenuButtons, this);
                this.setVisible(false);
                this.view.run();
                this.view.initLevel(this.fields);
                this.player.setAllowedToMove(true);
                this.gameInit.initEnemyMovement();
                this.gameInit.resumeCollectibleAnimations();
                this.view.requestFocusInWindow();
                break;
            case "Go to Menu":
                if (!this.view.isRunning()){
                    this.view.dispose();
                }
                this.gameInit.initView();
                break;
        }
    }
}
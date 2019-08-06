package Rendering.Menus;

import GameLogic.GameInit;
import GameObjects.Player.Player;
import Rendering.View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DeathMenu extends Menu implements clickHandlerInterface {

    private JButton[] deathmenubuttons;
    private Player player;
    private JLabel[] labels;
    private JPanel panel;
    private Color backgroundColor;

    public DeathMenu(
            View view,
            int frameHeight,
            int frameWidth,
            GameInit gameInit,
            Color backgoundColor,
            Player player
    ) {
        super(view, frameHeight, frameWidth, gameInit, backgoundColor, player);
        this.backgroundColor = backgoundColor;
    }

    public void showDeathMenu(){
        this.player = this.view.getPlayer();
        if(this.deathmenubuttons == null){
            JButton startRun = new JButton("Start new run");
            JButton goToMenu = new JButton("Go to Menu");
            this.deathmenubuttons = new JButton[]{startRun, goToMenu};
            this.addEventListener();
        }
        this.showMenu(this.deathmenubuttons);
        this.setOpaque(true);
        this.setVisible(true);
        this.revalidate();
    }

    @Override
    public void addEventListener() {
        for(JButton jButton : this.deathmenubuttons){
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
            case "Start new run":
                this.removeButtons(this.deathmenubuttons, this);
                this.setVisible(false);
                this.fields = this.gameInit.initLevel();
                this.view.initLevel(this.fields);
                this.gameInit.intitPlayerMovement();
                this.gameInit.initEnemyMovement();
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

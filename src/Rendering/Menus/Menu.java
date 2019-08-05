package Rendering.Menus;

import GameLogic.GameInit;
import GameObjects.Field_like_Objects.Field;
import GameObjects.Player.Player;
import Rendering.View;
import javax.swing.*;
import java.awt.*;

class Menu extends JPanel {

    private int frameWidth;
    protected View view;
    GameInit gameInit;
    protected Field[][] fields;
    protected Player player;

    private Color backgoundColor;

    Menu(
            View view,
            int frameHeight,
            int frameWidth,
            GameInit gameInit,
            Color backgoundColor,
            Player player
    ){

        this.gameInit = gameInit;
        this.view = view;
        this.player = player;
        this.backgoundColor = backgoundColor;
        this.frameWidth = frameWidth;
        this.setSize(new Dimension(this.frameWidth, frameHeight));
        this.setVisible(true);
    }

    //builds main menu components
    void showMenu(JButton[] buttons){
        this.styleButtons(buttons);
        this.setLayout(buttons);
        this.view.requestFocusInWindow();
    }

    //removes jbuttons from panel
    void removeButtons(JButton[] jButtons, Menu menu){
        if(jButtons != null) {
            for (JButton jButton : jButtons) {
                menu.remove(jButton);
            }
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
        constraints.insets = new Insets(5,5,5,5);
        this.setLayout(gridBagLayout);
        int counter = 0;
        for(JButton jButton : jButtons){
            this.setConstraintsToDefault(constraints);
            constraints.ipady = 20;
            constraints.ipadx = 200;
            constraints.gridx = this.frameWidth / 2;
            constraints.gridy = counter;
            counter += 50;
            this.add(jButton, constraints);
        }
    }

    //style buttons
    private void styleButtons(JButton[] jButtons){
        for(JButton jButton : jButtons){
            jButton.setBackground(this.backgoundColor);
            jButton.setForeground(Color.white);
            Dimension dimension = new Dimension(75,40);
            jButton.setPreferredSize(dimension);
        }
    }
}
package Rendering.Menus;

import GameObjects.Player.Player;

import javax.swing.*;
import java.awt.*;

public class PlayerValuePanel extends JPanel {

    private Player player;
    private Color backgroundColor;
    private JLabel[] labels;

    public PlayerValuePanel(Player player, Color backgroundColor){
        this.player = player;
        this.backgroundColor = backgroundColor;
        this.createPanel();
    }

    private void createPanel(){
        GridBagLayout gl = new GridBagLayout();
        this.setLayout(gl);
        this.setBackground(this.backgroundColor);
        this.setPreferredSize(new Dimension(300, 600));
        this.createLabels();
        this.setVisible(true);
    }

    private void createLabels(){
        JLabel dieText = new JLabel("You died!");
        dieText.setName("die");
        JLabel moveText = new JLabel("Züge:" + this.player.getMoves());
        moveText.setName("moves");
        JLabel scoreText = new JLabel("Score:" + this.player.getScore());
        scoreText.setName("score");
        JLabel livesText = new JLabel("Lives:" + this.player.getLives());
        livesText.setName("lives");
        JLabel coinsText = new JLabel("Coins:" + this.player.getCoins());
        coinsText.setName("coins");
        this.labels = new JLabel[]{dieText, moveText, scoreText, livesText, coinsText};
        this.styleLabels(this.labels);
    }

    private void styleLabels(JLabel[] texts){
        int counter = 0;
        for(JLabel text : texts){
            switch (text.getName()){
                case("die"):
                    text.setForeground(Color.PINK);
                    text.setFont(text.getFont().deriveFont(Font.BOLD, 30));
                    counter = this.setLabelPostion(text, counter);
                    break;
                case("move"):
                    text.setForeground(Color.ORANGE);
                    text.setFont(text.getFont().deriveFont(Font.PLAIN, 15));
                    counter = this.setLabelPostion(text, counter);
                    break;
                case("lives"):
                    text.setForeground(Color.RED);
                    text.setFont(text.getFont().deriveFont(Font.PLAIN, 15));
                    counter = this.setLabelPostion(text, counter);
                    break;
                case("coins"):
                    text.setForeground(Color.YELLOW);
                    text.setFont(text.getFont().deriveFont(Font.PLAIN, 15));
                    counter = this.setLabelPostion(text, counter);
                    break;
                case("score"):
                    text.setForeground(Color.BLACK);
                    text.setFont(text.getFont().deriveFont(Font.PLAIN, 15));
                    counter = this.setLabelPostion(text, counter);
                    break;
            }
        }
    }

    private int setLabelPostion(JLabel label, int counter){
        GridBagLayout gl = new GridBagLayout();
        this.setLayout(gl);
        GridBagConstraints gc = new GridBagConstraints();
        gc.gridx = counter;
        gc.gridy = counter;
        gc.insets = new Insets(5,0,0,0);
        this.add(label, gc);
        return counter + 50;
    }
}

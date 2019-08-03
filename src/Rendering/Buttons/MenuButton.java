package Rendering.Buttons;

import GameObjects.Player.Player;
import Rendering.View;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class MenuButton extends JPanel {

    private JButton menuButton;
    private ImageIcon menuButtonIcon;
    private BufferedImage menuButtonBG;
    private View view;

    public MenuButton(View view){
        this.view = view;
        this.setVisible(true);
        GridBagLayout gridBagLayout = new GridBagLayout();
        this.setLayout(gridBagLayout);
        this.initButton();
    }

    private void initButton(){
        this.menuButton = new JButton();
        this.styleButton();

    }

    //sets menubutton clickhandler
    public void setMenuButtonClickhandler(){
        View view = this.view;
        Player player = this.view.getPlayer();
        this.menuButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                view.showGameMenu(player);
            }
        });
    }

    private void styleButton(){
        Dimension buttonDimension = new Dimension(70, 40);
        this.menuButton.setOpaque(true);
        this.menuButton.setPreferredSize(buttonDimension);
        this.loadButtonIcon();
        this.setVisible(false);
        this.add(this.menuButton);
    }

    private void loadButtonIcon(){
        try {
            this.menuButtonBG = ImageIO.read(getClass().getResource("/Rendering/Buttons/MenuButton.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(menuButtonBG != null) {
            ImageIcon icon = new ImageIcon(this.menuButtonBG);
            Image img = icon.getImage();
            Image scaledInstance = img.getScaledInstance( 70, 40,  java.awt.Image.SCALE_SMOOTH );
            this.menuButtonIcon = new ImageIcon(scaledInstance);
        }
        this.menuButton.setIcon(this.menuButtonIcon);
    }

}

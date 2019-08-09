//package Rendering.Buttons;
//
//import Rendering.Colors.GameUIColors;
//import Rendering.View;
//
//import javax.imageio.ImageIO;
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.MouseAdapter;
//import java.awt.event.MouseEvent;
//import java.awt.image.BufferedImage;
//import java.io.IOException;
//
//public class MenuButton extends JPanel {
//
//    private JButton menuButton;
//    private ImageIcon menuButtonIcon;
//    private BufferedImage menuButtonBG;
//    private View view;
//    private Color backgroundcolor;
//
//    public MenuButton(View view){
//        this.view = view;
//        this.setVisible(true);
//        GridBagLayout gridBagLayout = new GridBagLayout();
//        this.setLayout(gridBagLayout);
//        this.initButton();
//        GameUIColors gameUIColors = new GameUIColors();
//        this.backgroundcolor = gameUIColors.getBackgroundbarColor();
//    }
//
//    private void initButton(){
//        this.menuButton = new JButton();
//        this.styleButton();
//
//    }
//
//    //sets menubutton clickhandler
//    public void setMenuButtonClickhandler(){
//        View view = this.view;
//        this.menuButton.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                super.mouseClicked(e);
//                view.showGameMenu();
//            }
//        });
//    }
//
//    private void styleButton(){
//        this.menuButton.setOpaque(true);
//        this.loadButtonIcon();
//        this.setVisible(false);
//        this.add(this.menuButton);
//    }
//
//    public void resetButtonSize(){
//        this.menuButton.setPreferredSize(new Dimension(70,40));
//        this.setBackground(this.backgroundcolor);
//    }
//
//    private void loadButtonIcon(){
//        try {
//            this.menuButtonBG = ImageIO.read(getClass().getResource("/Rendering/Buttons/MenuButton.png"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        if(menuButtonBG != null) {
//            ImageIcon icon = new ImageIcon(this.menuButtonBG);
//            Image img = icon.getImage();
//            Image scaledInstance = img.getScaledInstance( 70, 40,  java.awt.Image.SCALE_SMOOTH );
//            this.menuButtonIcon = new ImageIcon(scaledInstance);
//            this.resetButtonSize();
//        }
//        this.menuButton.setIcon(this.menuButtonIcon);
//    }
//
//}

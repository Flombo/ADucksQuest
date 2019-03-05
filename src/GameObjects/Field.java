package GameObjects;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Field {

    private int x;
    private int y;
    private int height;
    private int width;
    private String name;
    private BufferedImage currentImage;
    private BufferedImage defaultImage;

    public Field(int x, int y, String name){
        this.x = x;
        this.y = y;
        this.height = 30;
        this.width = 30;
        this.name = name;
        try {
            this.defaultImage = ImageIO.read(getClass().getResource("/textures/fieldTexture.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.currentImage = this.defaultImage;
    }
    public void setX(int x){
        this.x = x;
    }

    public void setY(int y){
        this.y = y;
    }

    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }

    public int getHeight(){
        return this.height;
    }

    public int getWidth(){
        return  this.width;
    }

    public String getName(){
        return this.name;
    }

    public BufferedImage getCurrentImage() {
        return currentImage;
    }

    public void setImageToDefault(){
        this.currentImage = this.defaultImage;
    }

    public void setCurrentImage(BufferedImage image){
        this.currentImage = image;
    }
}

package GameObjects.Field_like_Objects;

import Helper.ImageLoader;
import javafx.scene.image.Image;

public class Field {

    private int x;
    private int y;
    private int height;
    private int width;
    private String name;
    private Image currentImage;
    private Image defaultImage;

    public Field(int x, int y, String name){
        this.x = x;
        this.y = y;
        this.height = 30;
        this.width = 30;
        this.name = name;
        this.defaultImage = this.loadImage("/textures/fieldTexture.png");
        this.currentImage = this.defaultImage;
    }

    public Image loadImage(String path){
        ImageLoader imageLoader = new ImageLoader();
        return imageLoader.loadImage(path);
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

    public Image getCurrentImage() {
        return currentImage;
    }

    public void setImageToDefault(){
        this.currentImage = this.defaultImage;
    }

    public void setCurrentImage(Image image){
        this.currentImage = image;
    }

    public int getXPos(){
        return this.getX() / 30;
    }

    public int getYPos(){
        return this.getY() / 30;
    }
}

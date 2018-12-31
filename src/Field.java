import java.awt.*;

public class Field {

    private int x;
    private int y;
    private int height;
    private int width;
    private String name;
    private Color fillColor;
    private Color strokeColor;

    Field(int x, int y, String name, Color fillColor, Color strokeColor){
        this.x = x;
        this.y = y;
        this.height = 30;
        this.width = 30;
        this.name = name;
        this.fillColor = fillColor;
        this.strokeColor = strokeColor;
    }
    void setX(int x){
        this.x = x;
    }

    void setY(int y){
        this.y = y;
    }

    int getX(){
        return this.x;
    }

    int getY(){
        return this.y;
    }

    int getHeight(){
        return this.height;
    }

    int getWidth(){
        return  this.width;
    }

    String getName(){
        return this.name;
    }

    Color getFillColor(){
        return this.fillColor;
    }

    Color getStrokeColor(){
        return this.strokeColor;
    }

    public void setFillColor(Color fillColor){
        this.fillColor = fillColor;
    }

    public void setStrokeColor(Color strokeColor){
        this.strokeColor = strokeColor;
    }
}

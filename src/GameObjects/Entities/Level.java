package GameObjects.Entities;

import javafx.scene.image.Image;

public class Level {

    private String name;
    private String tags;
    private String levelcode;
    private Image thumbnail;
    private int x;
    private int y;

    public Level(String name, String tags, String levelcode, Image thumbnail, int x, int y){
        this.name = name;
        this.tags = tags;
        this.levelcode = levelcode;
        this.thumbnail = thumbnail;
        this.x = x;
        this.y = y;
    }

    public String getName() {
        return name;
    }

    public String getTags() {
        return tags;
    }

    public String getLevelcode() {
        return levelcode;
    }

    public Image getThumbnail() {
        return thumbnail;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}

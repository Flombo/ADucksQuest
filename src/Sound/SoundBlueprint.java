package Sound;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class SoundBlueprint {

    protected void playSound(Media media){
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
    }

}
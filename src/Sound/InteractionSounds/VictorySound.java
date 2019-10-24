package Sound.InteractionSounds;

import Sound.SoundBlueprint;
import javafx.scene.media.Media;

import java.io.File;

public class VictorySound extends SoundBlueprint {

    private Media victorySound;

    public VictorySound(){
        this.victorySound = new Media(
                getClass().getResource("/Sound/soundeffects/interactions/stairsachieved.wav").toExternalForm()
        );
    }

    public void playVictorySound(){
        this.playSound(this.victorySound);
    }

}
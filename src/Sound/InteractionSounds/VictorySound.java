package Sound.InteractionSounds;

import Sound.SoundBlueprint;
import javafx.scene.media.Media;

public class VictorySound extends SoundBlueprint {

    private Media victorySound;

    public VictorySound(){
        this.victorySound = new Media(
                "file:///C:/Users/flori/IdeaProjects/PuzzleGame_proto/src/Sound/soundeffects/interactions/stairsachieved.wav"
        );
    }

    public void playVictorySound(){
        this.playSound(this.victorySound);
    }

}
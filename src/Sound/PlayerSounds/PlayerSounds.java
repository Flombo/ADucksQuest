package Sound.PlayerSounds;

import Sound.SoundBlueprint;
import javafx.scene.media.Media;

public class PlayerSounds extends SoundBlueprint {

    private Media damagedSound;
    private Media quak;

    public PlayerSounds(){
        this.damagedSound = new Media(
                "file:///C:/Users/flori/IdeaProjects/PuzzleGame_proto/src/Sound/soundeffects/player/damagedSound.wav"
        );
        this.quak = new Media(
                "file:///C:/Users/flori/IdeaProjects/PuzzleGame_proto/src/Sound/soundeffects/player/quak.wav"
        );
    }

    public void playDamageSound(){
        this.playSound(this.damagedSound);
    }

    public void playQuak(){
        this.playSound(this.quak);
    }

}

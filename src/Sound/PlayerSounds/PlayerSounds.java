package Sound.PlayerSounds;

import Sound.SoundBlueprint;
import javafx.scene.media.Media;

public class PlayerSounds extends SoundBlueprint {

    private Media damagedSound;
    private Media quak;
    private Media fallingSound;

    public PlayerSounds(){
        this.damagedSound = new Media(
                "file:///C:/Users/flori/IdeaProjects/PuzzleGame_proto/src/Sound/soundeffects/player/damagedSound.wav"
        );
        this.quak = new Media(
                "file:///C:/Users/flori/IdeaProjects/PuzzleGame_proto/src/Sound/soundeffects/player/quak.wav"
        );
        this.fallingSound = new Media(
                "file:///C:/Users/flori/IdeaProjects/PuzzleGame_proto/src/Sound/soundeffects/player/fallingSound.wav"
        );
    }

    public void playDamageSound(){
        this.playSound(this.damagedSound);
    }

    public void playQuak(){
        this.playSound(this.quak);
    }

    public void playFallingSound(){
        this.playSound(this.fallingSound);
    }

}

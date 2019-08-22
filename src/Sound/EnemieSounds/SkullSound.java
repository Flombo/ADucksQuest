package Sound.EnemieSounds;

import Sound.SoundBlueprint;
import javafx.scene.media.Media;


public class SkullSound extends SoundBlueprint {

    private Media attackSound;
    private Media changeDirectionSound;

    public SkullSound(){
        this.attackSound = new Media(
                "file:///C:/Users/flori/IdeaProjects/PuzzleGame_proto/src/Sound/soundeffects/enemies/Skull/skullAttack.wav"
        );
        this.changeDirectionSound = new Media(
                "file:///C:/Users/flori/IdeaProjects/PuzzleGame_proto/src/Sound/soundeffects/enemies/Skull/skullChangeDirection.mp3"
        );
    }

    public void playAttackSound(){
        this.playSound(this.attackSound);
    }

    public void playChangeDirectionSound(){
        this.playSound(this.changeDirectionSound);
    }
}

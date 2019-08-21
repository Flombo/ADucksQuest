package Sound.EnemieSounds;

import Sound.SoundBlueprint;
import javafx.scene.media.Media;


public class SkullSound extends SoundBlueprint {

    private Media attackSound;

    public SkullSound(){
        this.attackSound = new Media(
                "file:///C:/Users/flori/IdeaProjects/PuzzleGame_proto/src/Sound/soundeffects/enemies/Skull/skullAttack.wav"
        );
    }

    public void playAttackSound(){
        this.playSound(this.attackSound);
    }
}

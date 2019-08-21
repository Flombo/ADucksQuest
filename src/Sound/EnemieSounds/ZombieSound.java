package Sound.EnemieSounds;

import Sound.SoundBlueprint;
import javafx.scene.media.Media;

public class ZombieSound extends SoundBlueprint {

    private Media attackSound;

    public ZombieSound(){
        this.attackSound = new Media(
                "file:///C:/Users/flori/IdeaProjects/PuzzleGame_proto/src/Sound/soundeffects/enemies/Zombie/zombieAttack.wav"
        );
    }

    public void playAttackSound(){
        this.playSound(this.attackSound);
    }

}
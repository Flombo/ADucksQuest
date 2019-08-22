package Sound.EnemieSounds;

import Sound.SoundBlueprint;
import javafx.scene.media.Media;

public class ZombieSound extends SoundBlueprint {

    private Media attackSound;
    private Media changeDirectionSound;

    public ZombieSound(){
        this.attackSound = new Media(
                "file:///C:/Users/flori/IdeaProjects/PuzzleGame_proto/src/Sound/soundeffects/enemies/Zombie/zombieAttack.wav"
        );
        this.changeDirectionSound = new Media(
                "file:///C:/Users/flori/IdeaProjects/PuzzleGame_proto/src/Sound/soundeffects/enemies/Zombie/zombieChangeDirection.wav"
        );
    }

    public void playAttackSound(){
        this.playSound(this.attackSound);
    }

    public void playChangeDirectionSound(){
        this.playSound(this.changeDirectionSound);
    }

}
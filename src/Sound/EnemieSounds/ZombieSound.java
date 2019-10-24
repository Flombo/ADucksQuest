package Sound.EnemieSounds;

import Sound.SoundBlueprint;
import javafx.scene.media.Media;

public class ZombieSound extends SoundBlueprint {

    private Media attackSound;
    private Media changeDirectionSound;

    public ZombieSound(){
        this.attackSound = new Media(
                getClass().getResource("/Sound/soundeffects/enemies/Zombie/zombieAttack.wav").toExternalForm()
        );
        this.changeDirectionSound = new Media(
                getClass().getResource("/Sound/soundeffects/enemies/Zombie/zombieChangeDirection.wav").toExternalForm()
        );
    }

    public void playAttackSound(){
        this.playSound(this.attackSound);
    }

    public void playChangeDirectionSound(){
        this.playSound(this.changeDirectionSound);
    }

}
package Sound.EnemieSounds;

import Sound.SoundBlueprint;
import javafx.scene.media.Media;


public class SkullSound extends SoundBlueprint {

    private Media attackSound;
    private Media changeDirectionSound;

    public SkullSound(){
        this.attackSound = new Media(
                getClass().getResource("/Sound/soundeffects/enemies/Skull/skullAttack.wav").toExternalForm()
        );
        this.changeDirectionSound = new Media(
                getClass().getResource("/Sound/soundeffects/enemies/Skull/skullChangeDirection.mp3").toExternalForm()
        );
    }

    public void playAttackSound(){
        this.playSound(this.attackSound);
    }

    public void playChangeDirectionSound(){
        this.playSound(this.changeDirectionSound);
    }
}

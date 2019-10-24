package Sound.PlayerSounds;

import Sound.SoundBlueprint;
import javafx.scene.media.Media;

import java.io.File;

public class PlayerSounds extends SoundBlueprint {

    private Media damagedSound;
    private Media quak;
    private Media fallingSound;

    public PlayerSounds(){
        this.damagedSound = new Media(
                getClass().getResource("/Sound/soundeffects/player/damagedSound.wav").toExternalForm()
        );
        this.quak = new Media(
                getClass().getResource("/Sound/soundeffects/player/quak.wav").toExternalForm()
        );
        this.fallingSound = new Media(
                getClass().getResource("/Sound/soundeffects/player/fallingSound.wav").toExternalForm()
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

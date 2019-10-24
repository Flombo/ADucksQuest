package Sound.InteractionSounds;

import Sound.SoundBlueprint;
import javafx.scene.media.Media;

public class ItemPickedSound extends SoundBlueprint {

    private Media itemPicked;

    public ItemPickedSound(){
        this.itemPicked = new Media(
                getClass().getResource("/Sound/soundeffects/interactions/itemPickup.wav").toExternalForm()
        );
    }

    public void playItemPicked(){
        this.playSound(this.itemPicked);
    }

}

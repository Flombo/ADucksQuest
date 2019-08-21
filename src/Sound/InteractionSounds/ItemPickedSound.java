package Sound.InteractionSounds;

import Sound.SoundBlueprint;
import javafx.scene.media.Media;

public class ItemPickedSound extends SoundBlueprint {

    private Media itemPicked;

    public ItemPickedSound(){
        this.itemPicked = new Media(
        "file:///C:/Users/flori/IdeaProjects/PuzzleGame_proto/src/Sound/soundeffects/interactions/itemPickup.wav"
        );
    }

    public void playItemPicked(){
        this.playSound(this.itemPicked);
    }

}

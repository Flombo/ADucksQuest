package GameObjects.Collectibles;

import GameObjects.Field_like_Objects.Field;
import GameObjects.GameObjectEnums.CoinFlipFrames;
import Rendering.Animations.CoinAnimations.CoinFlip;
import javafx.scene.image.Image;

public class Coin extends Field {

	private Image currentImage;
	private Image defaultImage;
	private CoinFlipFrames flipFrame;
	private CoinFlip coinFlip;
	private boolean allowedToFlip = true;

	public Coin() {
		super(0, 0, "GameObjects.Collectibles.Coin");
		this.defaultImage = this.loadImage("/textures/CoinAnimation/Coin.png");
		this.currentImage = defaultImage;
		this.flipFrame = CoinFlipFrames.Coin_Default;
		this.startAnimation();
	}

	public void startAnimation(){
		this.allowedToFlip = true;
		this.coinFlip = new CoinFlip(this);
		this.flip();
	}

	@Override
	public Image getCurrentImage() {
		return currentImage;
	}

	public void setCurrentImage(Image currentImage) {
		this.currentImage = currentImage;
	}

	public void setCurrentImageToDefault(){
		this.currentImage = defaultImage;
	}

	public CoinFlipFrames getFlipFrame() {
		return flipFrame;
	}

	public void setFlipFrame(CoinFlipFrames flipFrame) {
		this.flipFrame = flipFrame;
	}

	private void flip(){
		this.coinFlip.initFlip();
	}

	public boolean isAllowedToFlip() {
		return allowedToFlip;
	}

	public void setAllowedToFlip(boolean allowedToFlip) {
		this.allowedToFlip = allowedToFlip;
	}
}

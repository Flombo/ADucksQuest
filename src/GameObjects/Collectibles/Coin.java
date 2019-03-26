package GameObjects.Collectibles;

import GameObjects.Field_like_Objects.Field;
import GameObjects.GameObjectEnums.CoinFlipFrames;
import Rendering.Animations.CoinAnimations.CoinFlip;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Coin extends Field {

	private BufferedImage currentImage;
	private BufferedImage defaultImage;
	private CoinFlipFrames flipFrame;
	private CoinFlip coinFlip;
	private boolean allowedToFlip = true;

	public Coin() {
		super(0, 0, "GameObjects.Collectibles.Coin");
		try {
			this.defaultImage = ImageIO.read(getClass().getResource("/textures/CoinAnimation/Coin.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.currentImage = defaultImage;
		this.flipFrame = CoinFlipFrames.Coin_Default;
		this.coinFlip = new CoinFlip(this);
		this.flip();
	}

	@Override
	public BufferedImage getCurrentImage() {
		return currentImage;
	}

	public void setCurrentImage(BufferedImage currentImage) {
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

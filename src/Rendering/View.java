package Rendering;

import GameLogic.GameInit;
import GameObjects.Field_like_Objects.Field;
import GameObjects.Player.Player;
import Rendering.Buttons.MenuButton;
import Rendering.Colors.GameUIColors;
import Rendering.Menus.IngameMenu;
import Rendering.Menus.MainMenu;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;

public class View extends JFrame implements Runnable{

	private boolean isRunning = false;
	private Thread currentThread;
	private GameInit gameInit;
	private MainMenu mainMenu;
	private IngameMenu gameMenu;
	private Field[][] fields;
	private Color backgroundbarColor;
	private Color innerbarColor;
	private Color backgroundColor;
	private MenuButton menuButton;

	public View(GameInit gameInit){
		this.gameInit = gameInit;
		this.menuButton = new MenuButton(this);
		this.setLayout(new GridBagLayout());
		this.setMenuButton();
		GameUIColors gameUIColors = new GameUIColors();
		this.backgroundbarColor = gameUIColors.getBackgroundbarColor();
		this.innerbarColor = gameUIColors.getInnerBarColor();
		this.backgroundColor = gameUIColors.getBackgroundColor();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.mainMenu = new MainMenu(
				this,
				this.getHeight(),
				this.getWidth(),
				this.gameInit,
				this.backgroundbarColor,
				null
		);
		this.add(this.mainMenu);
		this.showMainMenu();
		this.setVisible(true);
		this.pack();
	}

	public void setIsRunning(boolean isRunning){
		this.isRunning = isRunning;
	}

	//needed for pack() to get the preferd size
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(15 * 40, 15 * 50);
	}

	//sets postion of menubutton and adds it to view
	private void setMenuButton() {
		GridBagConstraints gc = new GridBagConstraints();
		gc.insets = new Insets(0, 0,648,460);
		this.add(this.menuButton, gc);
	}

	public Player getPlayer() {
		Player player = null;
		for(Field[] fields : this.fields){
			for(Field field : fields){
				if(field.getName().equals("GameObjects.Player.Player")){
					player = (Player) field;
				}
			}
		}
		return player;
	}

	public void showGameMenu(){
		Player player = this.getPlayer();
		if(this.gameMenu == null) {
			this.gameMenu = new IngameMenu(
					this,
					getHeight(),
					getWidth(),
					this.gameInit,
					this.backgroundbarColor,
					player,
					this.fields
			);
			this.add(this.gameMenu);
		}
		this.gameMenu.showGameMenu();
		while (this.currentThread.isAlive()){
			this.setIsRunning(false);
		}
		this.gameInit.switchEnemyMovement(false);
		this.gameInit.switchCollectiblesAnimation(false);
		player.setAllowedToMove(false);
		this.menuButton.setVisible(false);
		repaint();
	}

	public boolean isRunning(){
		return this.isRunning;
	}

	//shows Mainmenu
	private void showMainMenu(){
		this.mainMenu.showMainMenu();
	}

	//shows Dialogwindow
	public void setDialog(String txt){
		JOptionPane.showMessageDialog(this, txt);
	}

	//initLevel Thread
	public synchronized void initLevel(Field[][] fields){
		isRunning = true;
		this.fields = fields;
		currentThread = new Thread(this, "Thread1");
		currentThread.start();
		this.menuButton.setMenuButtonClickhandler();
		this.menuButton.setVisible(true);
		this.pack();
	}

	// stop currentThread by closing frame
	private synchronized void stop(){
		currentThread.interrupt();
	}

	/** if deltaTime is less than 0.5 seconds render the gamefield
	 * and prints the framerate into title
	 * */
	@Override
	public void run() {
		int frames = 0;
		long timer = System.currentTimeMillis();
		final double framerate =  60.0;
		long frameStart = System.nanoTime();
		double nanoSeconds = 1000000000 / framerate;
		double delta = 0;

		while (isRunning){
			long now = System.nanoTime();
			delta += (now - frameStart) / nanoSeconds;
			frameStart = now;
			if (delta < 0.5) {
				try {
					Thread.sleep((long) (1000 / framerate));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			initRendering();
			frames++;
			delta--;
			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				this.setTitle("FPS:" + frames);
				frames = 0;
			}
		}
		stop();
	}

	//render Gamefield and Counterpanel
	private void initRendering(){
		BufferStrategy bufferStrategy = getBufferStrategy();
		if(bufferStrategy == null){
			createBufferStrategy(3);
			return;
		}
		this.renderBackgroundAndCounterbarbackground();
		this.renderGamefield(bufferStrategy);
		Graphics graphics = bufferStrategy.getDrawGraphics();
		graphics.dispose();
		this.menuButton.repaint();
		bufferStrategy.show();
	}

	//render Gamefield
	private void renderGamefield(BufferStrategy bufferStrategy){
		Graphics g = bufferStrategy.getDrawGraphics();
		this.renderCounterbarBox(bufferStrategy);
		for (Field[] fields : this.fields) {
			for(Field field : fields){
				if(field instanceof Player) {
					this.renderCounterText(bufferStrategy, (Player) field);
					this.renderSkillbar(bufferStrategy);
				}
				g.drawImage(field.getCurrentImage(), field.getX() +  getWidth() / 8, field.getY() + 90, field.getHeight(), field.getWidth(), null);
			}
		}
	}

	//renders objects that need to render only one time
	private void renderBackgroundAndCounterbarbackground(){
		BufferStrategy bufferStrategy = getBufferStrategy();
		if(bufferStrategy == null){
			createBufferStrategy(3);
			return;
		}
		this.renderBackground(bufferStrategy);
		this.renderCounterbarBackground(bufferStrategy);
	}

	//renders background
	private void renderBackground(BufferStrategy bufferStrategy) {
		Graphics g = bufferStrategy.getDrawGraphics();
		g.setColor(this.backgroundColor);
		g.fillRect(0,0,getWidth(), getHeight());
	}

	//render skillbar
	private void renderSkillbar(BufferStrategy bufferStrategy){
		Graphics g = bufferStrategy.getDrawGraphics();
		g.setColor(this.backgroundbarColor);
		g.fillRect(0, 15 * 36, this.getWidth(), this.getHeight() / 3);
		g.setColor(this.innerbarColor);
		g.fillRect(getWidth() / 30, getHeight() - 200, getWidth() - getWidth() / 14,  getHeight() - 570);
	}

	//renders counterbarbackground one time
	private void renderCounterbarBackground(BufferStrategy bufferStrategy){
		Graphics g = bufferStrategy.getDrawGraphics();
		g.setColor(this.backgroundbarColor);
		g.fillRect(0, 31, getWidth(),59);
	}

	//render Counterbar
	private void renderCounterbarBox(BufferStrategy bufferStrategy){
		Graphics g = bufferStrategy.getDrawGraphics();
		g.setColor(this.innerbarColor);
		g.fillRect(getWidth() - 465, 41, getWidth() - getWidth() / 4, 40);
	}

	//render Counterbar text
	private void renderCounterText(BufferStrategy bufferStrategy, Player player){
		Graphics g = bufferStrategy.getDrawGraphics();
		g.setColor(Color.BLACK);
		g.drawString("Züge :" + " " + player.getMoves(), 175, 65);
		g.setColor(Color.BLUE);
		g.drawString("Coins :" + " " + player.getCoins(), 325, 65);
		g.setColor(Color.RED);
		g.drawString("Lives :" + " " + player.getLives(), 500, 65);
	}

}

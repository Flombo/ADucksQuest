package Rendering;

import GameLogic.GameInit;
import GameObjects.Field_like_Objects.Field;
import GameObjects.Player.Player;
import Rendering.Windows.MainMenu;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;

public class View extends JFrame implements Runnable{

	private boolean isRunning = false;
	private boolean isPaused = false;
	private Thread currentThread;
	private GameInit gameInit;
	private MainMenu mainMenu;
	private Field[][] fields;

	public View(GameInit gameInit){
		this.gameInit = gameInit;
		Dimension dimension = new Dimension(15 * 40, 15 * 50);
		this.setSize(dimension);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setLayout(new GridBagLayout());
		this.mainMenu = new MainMenu(this, this.getHeight(), this.getWidth(), this.gameInit);
		this.add(this.mainMenu);
		this.showMainMenu();
		this.setVisible(true);
	}

	public void showGameMenu(Player player){
		this.isPaused = true;
		this.gameInit.switchEnemyMovement(false);
		this.gameInit.switchCollectiblesAnimation(false);
		player.setAllowedToMove(false);
		this.mainMenu.showGameMenu(player);
	}

	public boolean isRunning(){
		return this.isRunning;
	}

	public void setIsPaused(boolean isPaused){
		this.isPaused = isPaused;
	}

	//shows Mainmenu
	public void showMainMenu(){
		this.mainMenu.showMainMenu();
	}

	//shows Dialogwindow
	public void setDialog(String txt){
		JOptionPane.showMessageDialog(this, txt);
	}

	//initLevel Thread
	public synchronized void initLevel(Field[][] fields){
		isRunning = true;
		isPaused = false;
		this.fields = fields;
		currentThread = new Thread(this, "Thread1");
		currentThread.start();
	}

	// stop currentThread by closing frame
	private synchronized void stop(){
		isRunning = false;
		try{
			currentThread.join();
		} catch (InterruptedException e){
			e.printStackTrace();
		}
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
			while (!isPaused) {
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
		this.renderGamefield(bufferStrategy);
		Graphics graphics = bufferStrategy.getDrawGraphics();
		graphics.dispose();
		bufferStrategy.show();
	}

	//render Gamefield
	private void renderGamefield(BufferStrategy bufferStrategy){
		Graphics g = bufferStrategy.getDrawGraphics();
		this.renderCounterPanel(bufferStrategy);
		for (Field[] fields : this.fields) {
			for(Field field : fields){
				if(field instanceof Player) {
					this.renderCounterText(bufferStrategy, (Player) field);
				}
				g.drawImage(field.getCurrentImage(), field.getX() +  getWidth() / 8, field.getY() + 90, field.getHeight(), field.getWidth(), null);
			}
		}
	}

	//render CounterPanel Boxes
	private void renderCounterPanel(BufferStrategy bufferStrategy){
		Graphics g = bufferStrategy.getDrawGraphics();
		g.setColor(Color.BLACK);
		g.fillRect(0,0,getWidth(),getHeight());
		g.setColor(Color.white);
		g.fillRect(0, 33, getWidth(),50);
		g.setColor(Color.BLACK);
		g.fillRect(getWidth() / 10, 36, getWidth() - getWidth() / 5, 45);
		g.setColor(Color.lightGray);
		g.fillRect(getWidth() / 8, 38,getWidth() - getWidth() / 4, 40);
	}

	//render CounterPanel Text
	private void renderCounterText(BufferStrategy bufferStrategy, Player player){
		Graphics g = bufferStrategy.getDrawGraphics();
		g.setColor(Color.BLACK);
		g.drawString("Züge :" + " " + player.getMoves(), getWidth() / 6, 65);
		g.setColor(Color.BLUE);
		g.drawString("Coins :" + " " + player.getCoins(), getWidth() - getHeight() / 2, 65);
		g.setColor(Color.RED);
		g.drawString("Lives :" + " " + player.getLives(), getWidth() - getWidth() / 3, 65);
	}

}

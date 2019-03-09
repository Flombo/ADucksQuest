package Rendering;

import GameObjects.Field_like_Objects.Field;
import GameObjects.Player.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;

public class View extends JFrame implements Runnable{

	private Field[][] fields;
	private boolean isRunning = false;
	private Thread thread;

	public View(Field[][] fields){
		this.fields = fields;
		Dimension dimension = new Dimension(fields.length * 40, fields.length * 50);
		this.setSize(dimension);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setLayout(new GridBagLayout());
		this.setVisible(true);
	}

	//shows Dialogwindow
	public void setDialog(String txt){
		JOptionPane.showMessageDialog(this, txt);
	}

	//start Thread
	public synchronized void start(){
		isRunning = true;
		thread = new Thread(this, "Thread1");
		thread.start();
	}

	// stop thread by closing frame
	private synchronized void stop(){
		isRunning = false;
		try{
			thread.join();
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
			long now = System.nanoTime();
			delta += (now - frameStart) / nanoSeconds;
			frameStart = now;
			if(delta < 0.5){
				try {
					Thread.sleep((long) (1000 / framerate));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			initRendering();
			frames++;
			delta--;
			if(System.currentTimeMillis() - timer > 1000){
				timer += 1000;
				this.setTitle("FPS:" + frames );
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

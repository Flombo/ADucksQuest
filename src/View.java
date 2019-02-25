import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;

class View extends JFrame implements Runnable{

	private Field[][] fields;
	private boolean isRunning = false;
	private Thread thread;

	View(Field[][] fields, int xDimension, int yDimension){
		this.fields = fields;
		Dimension dimension = new Dimension(xDimension * 35, yDimension * 60);
		this.setSize(dimension);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setLayout(new GridBagLayout());
		this.setVisible(true);
	}

	//shows Dialogwindow
	void setDialog(String txt){
		JOptionPane.showMessageDialog(this, txt);
	}

	//start Thread
	synchronized void start(){
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
				g.setColor(field.getStrokeColor());
				g.fillRect(field.getX() + 25, field.getY() + 90, field.getWidth(), field.getHeight());
				g.setColor(field.getFillColor());
				g.fillRect(field.getX() + 1 + 25, field.getY() + 1 + 90, field.getWidth() - 2, field.getHeight() - 2);
			}
		}
	}

	//render CounterPanel Boxes
	private void renderCounterPanel(BufferStrategy bufferStrategy){
		Graphics g = bufferStrategy.getDrawGraphics();
		g.setColor(Color.BLACK);
		g.fillRect(0,0,350,600);
		g.setColor(Color.white);
		g.fillRect(25, 33, 300,50);
		g.setColor(Color.lightGray);
		g.fillRect(30, 40,90, 40);
		g.fillRect(130, 40, 90, 40);
		g.fillRect(230, 40, 90, 40);
	}

	//render CounterPanel Text
	private void renderCounterText(BufferStrategy bufferStrategy, Player player){
		Graphics g = bufferStrategy.getDrawGraphics();
		g.setColor(Color.BLACK);
		g.drawString("Züge :" + " " + player.getMoves(), 45, 65);
		g.setColor(Color.BLUE);
		g.drawString("Score :" + " " + player.getScore(), 145, 65);
		g.setColor(Color.RED);
		g.drawString("Lives :" + " " + player.getLives(), 245, 65);
	}

}

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;

class View extends JFrame implements Runnable{

	private Field[][] fields;
	private boolean isRunning = false;
	private Thread thread;

	View(Field[][] fields, int xDimension, int yDimension){
		this.fields = fields;
		Dimension dimension = new Dimension(xDimension * 40, yDimension * 40);
		this.setSize(dimension);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setLayout(new GridBagLayout());
		this.setVisible(true);
	}

	void setDialog(String txt){
		JOptionPane.showMessageDialog(this, txt);
	}

	synchronized void start(){
		isRunning = true;
		thread = new Thread(this, "Thread1");
		thread.start();
	}

	private synchronized void stop(){
		isRunning = false;
		try{
			thread.join();
		} catch (InterruptedException e){
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		final double nanoSeconds = 10000000.0 / 60.0;
		double delta = 0;
		int frames = 0;
		int updates = 0;
		while (isRunning){
			long now = System.nanoTime();
			delta += (now - lastTime) / nanoSeconds;
			while (delta >= 1){
				updates++;
				delta--;
			}
			initRendering();
			frames++;

			if(System.currentTimeMillis() - timer > 1000){
				timer += 1000;
				this.setTitle("PuzzleGame |" + updates + " UPS, " + frames + "fps");
				updates = 0;
				frames = 0;
			}
		}
		stop();
	}

	private void initRendering(){
		BufferStrategy bufferStrategy = getBufferStrategy();
		if(bufferStrategy == null){
			createBufferStrategy(3);
			return;
		}
		this.render(bufferStrategy);

		Graphics graphics = bufferStrategy.getDrawGraphics();
		graphics.dispose();
		bufferStrategy.show();
	}

	private void render(BufferStrategy bufferStrategy){
		Graphics g = bufferStrategy.getDrawGraphics();
		for (Field[] fields : this.fields) {
			for(Field field : fields){
				g.setColor(field.getStrokeColor());
				g.fillRect(field.getX() + 40, field.getY() + 85, field.getWidth(), field.getHeight());
				g.setColor(field.getFillColor());
				g.fillRect(field.getX() + 1 + 40, field.getY() + 1 + 85, field.getWidth() - 2, field.getHeight() - 2);
			}
		}
	}

}

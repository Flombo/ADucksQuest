import javax.swing.*;
import java.awt.*;

public class DrawFields extends JLabel {

	private Field[][] fields;

	DrawFields(Field[][] fields){
		this.setSize(new Dimension(300, 300));
		this.fields = fields;
	}

	protected void paintComponent(Graphics g) {
		for (Field[] fields : this.fields) {
			for(Field field : fields){
				super.paintComponent(g);
				g.setColor(field.getStrokeColor());
				g.fillRect(field.getX(), field.getY(), field.getWidth(), field.getHeight());
				g.setColor(field.getFillColor());
				g.fillRect(field.getX() + 1, field.getY() + 1, field.getWidth() - 2, field.getHeight() -2);
			}
		}
	}

}

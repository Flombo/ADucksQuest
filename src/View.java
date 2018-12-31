import javax.swing.*;
import java.awt.*;

class View extends JFrame {

	View(Field[][] fields){
		this.setTitle("PuzzleGame");
		this.setSize(new Dimension(400,400));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setVisible(true);
		DrawFields drawFields = new DrawFields(fields);
		drawFields.setVisible(true);
		this.add(drawFields, BorderLayout.CENTER);
	}

}

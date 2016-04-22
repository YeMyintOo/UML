package Canvas;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class UC_ActionLine extends Line {
	private Color color;

	public UC_ActionLine() {
		super(0, 0, 0, 0);
	}

	public UC_ActionLine(double startx, double starty, double endx, double endy) {
		super(startx, starty, endx, endy);
		setColor(Color.BLACK);
	}

	public UC_ActionLine(double startx, double starty, double endx, double endy, Color color) {
		super(startx, starty, endx, endy);
		setColor(color);

	}
	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
		setStroke(color);
	}
}

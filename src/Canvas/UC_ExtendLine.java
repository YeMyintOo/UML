package Canvas;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class UC_ExtendLine extends Line{
	private Color color;

	public UC_ExtendLine(double startx, double starty, double endx, double endy, Color color) {
		super(startx, starty, endx, endy);
		setStroke(color);
		getStrokeDashArray().addAll(10d, 10d);
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
		setFill(color);
	}



}

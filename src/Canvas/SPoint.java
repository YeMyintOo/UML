package Canvas;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class SPoint extends Circle {
	private Color color;

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public SPoint() {
		this(0, 0, 2);
	}

	public SPoint(double x, double y, double radius) {
		this(x, y, radius, Color.GREEN);
	}

	public SPoint(double x, double y, double radius, Color color) {
		super(x, y, radius);
		setFill(color);
	}
}

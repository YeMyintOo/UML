package Canvas;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class SE_DestroyActivation extends Line {
	private Color color;

	public SE_DestroyActivation(double startx, double starty, double endx, double endy, Color color) {
		super(startx, starty, endx, endy);
		setStroke(color);

	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
		setFill(color);
	}
}

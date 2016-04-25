package Canvas;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

public class SE_Activation extends Line {
	private Color color;

	private DoubleProperty life;
	private Rectangle rect;

	public SE_Activation(double startx, double starty, double endx, double endy, Color color) {
		super(startx, starty, endx, endy);
		setStroke(color);
		//getStrokeDashArray().addAll(10d, 10d);
		life = new SimpleDoubleProperty(100);
		rect = new Rectangle();
	}

	public final DoubleProperty lifeProperty() {
		return life;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
		setFill(color);
	}

	public Rectangle getRect() {
		return rect;
	}

	public void setRect(Rectangle rect) {
		this.rect = rect;
	}
}

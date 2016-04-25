package Canvas;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

public class SE_NewActivation extends Line {
	private Color color;

	private DoubleProperty life;
	private Rectangle rect;
	private Rectangle newOb;
	private DoubleProperty newlife;
	private StringProperty label;

	public SE_NewActivation(double startx, double starty, double endx, double endy, Color color) {
		super(startx, starty, endx, endy);
		setStroke(color);
		life = new SimpleDoubleProperty(100);
		newlife = new SimpleDoubleProperty(150);
		rect = new Rectangle();
		rect.setFill(Color.LIGHTGRAY);
		rect.setStroke(Color.BLACK);

		newOb = new Rectangle();
		newOb.setStroke(Color.BLACK);
		newOb.setFill(color);

		label = new SimpleStringProperty("Role");
	}

	public final StringProperty labelProperty() {
		return label;
	}

	public final DoubleProperty lifeProperty() {
		return life;
	}

	public final DoubleProperty newlifeProperty() {
		return newlife;
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

	public Rectangle getNewOb() {
		return newOb;
	}

	public void setNewOb(Rectangle newOb) {
		this.newOb = newOb;
	}
}

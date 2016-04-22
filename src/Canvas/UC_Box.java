package Canvas;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class UC_Box extends Rectangle {
	private Color bgcolor; // Background Color

	private StringProperty label;

	public UC_Box(double x, double y, double width, double height, Color bgcolor, Color scolor) {
		super(x,y, width, height);
		setX(x);
		setY(y);
		setFill(bgcolor);
		setOpacity(0.3);
		setStroke(scolor);
		setArcWidth(10);
		setArcHeight(10);
		label = new SimpleStringProperty("Box Label");
		
	}

	public final StringProperty labelProperty() {
		return label;
	}

	public Color getBgcolor() {
		return bgcolor;
	}

	public void setBgcolor(Color bgcolor) {
		this.bgcolor = bgcolor;

	}

}

package Canvas;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class S_State extends Rectangle {

	private StringProperty label;

	public S_State(double x, double y, Color color) {
		super(x,y,100,40);
		setArcWidth(10);
		setArcHeight(10);
		label = new SimpleStringProperty("State");
		setFill(color);
		setStroke(Color.LIGHTGRAY);
	}

	public final StringProperty labelProperty() {
		return label;
	}
}

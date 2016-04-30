package Canvas;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class S_SubState extends Rectangle{

	private StringProperty label;

	public S_SubState(double x, double y, Color color) {
		super(x,y,200,300);
		setArcWidth(10);
		setArcHeight(10);
		label = new SimpleStringProperty("Sub State");
		setFill(color);
		setStroke(Color.LIGHTGRAY);
	}

	public final StringProperty labelProperty() {
		return label;
	}
}

package Canvas;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class UC_Actor extends Circle {

	private StringProperty label;

	public UC_Actor() {
		super(0, 0, 0);
	}

	public UC_Actor(double centerX, double centerY, double radius) {
		this(centerX, centerY, radius,Color.WHEAT,Color.GRAY);
	}

	public UC_Actor(double centerX, double centerY, double radius, Color bgcolor, Color scolor) {
		super(centerX, centerY, radius);
		setFill(bgcolor);
		setStroke(scolor);
		label= new SimpleStringProperty("Actor");
	}
	
	public final StringProperty labelProperty() {
		return label;
		}

	
}

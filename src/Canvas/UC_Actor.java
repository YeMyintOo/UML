package Canvas;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class UC_Actor extends Circle {

	private StringProperty data;
	private Text label;
	private Line body;
	private Line leg;

	public UC_Actor() {
		super(0, 0, 0);
	}

	public UC_Actor(double centerX, double centerY, double radius) {
		this(centerX, centerY, radius, Color.WHEAT, Color.GRAY);
	}

	public UC_Actor(double centerX, double centerY, double radius, Color bgcolor, Color scolor) {
		super(centerX, centerY, radius);
		setFill(bgcolor);
		setStroke(scolor);
		data = new SimpleStringProperty("Actor");

		label = new Text(data.get());
		label.setFont(Font.font("Arial", FontWeight.BLACK, 16));

		body = new Line();
		body.startXProperty().bind(centerXProperty());
		body.startYProperty().bind(centerYProperty().add(20));
		body.endXProperty().bind(centerXProperty());
		body.endYProperty().bind(centerYProperty().add(40));
		
		
	}

	public final StringProperty labelProperty() {
		return data;
	}

	public Text getLabel() {
		return label;
	}

	public Line getBody() {
		return body;
	}
}

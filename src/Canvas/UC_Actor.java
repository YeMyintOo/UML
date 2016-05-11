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
	private Line leg2;
	private Line leg3;
	private Line leg4;

	public UC_Actor(double centerX, double centerY, double radius) {
		this(centerX, centerY, radius, Color.WHEAT, Color.GRAY);
	}

	public UC_Actor(double centerX, double centerY, double radius, Color bgcolor, Color scolor) {
		super(centerX, centerY, radius);
		setFill(bgcolor);
		setStroke(scolor);
		data = new SimpleStringProperty("Actor");

		label = new Text(data.get());
		label.setFont(Font.font("Arial", FontWeight.BLACK, 14));
		label.textProperty().bind(labelProperty());
		label.layoutXProperty().bind(centerXProperty().subtract(label.getLayoutBounds().getWidth() / 2));
		label.layoutYProperty().bind(centerYProperty().add(80));

		body = new Line();
		body.startXProperty().bind(centerXProperty());
		body.startYProperty().bind(centerYProperty().add(20));
		body.endXProperty().bind(centerXProperty());
		body.endYProperty().bind(centerYProperty().add(40));

		leg = new Line();
		leg.startXProperty().bind(centerXProperty());
		leg.startYProperty().bind(centerYProperty().add(20));
		leg.endXProperty().bind(centerXProperty().add(10));
		leg.endYProperty().bind(centerYProperty().add(30));

		leg2 = new Line();
		leg2.startXProperty().bind(centerXProperty());
		leg2.startYProperty().bind(centerYProperty().add(20));
		leg2.endXProperty().bind(centerXProperty().subtract(10));
		leg2.endYProperty().bind(centerYProperty().add(30));

		leg3 = new Line();
		leg3.startXProperty().bind(centerXProperty());
		leg3.startYProperty().bind(centerYProperty().add(40));
		leg3.endXProperty().bind(centerXProperty().add(10));
		leg3.endYProperty().bind(centerYProperty().add(50));

		leg4 = new Line();
		leg4.startXProperty().bind(centerXProperty());
		leg4.startYProperty().bind(centerYProperty().add(40));
		leg4.endXProperty().bind(centerXProperty().subtract(10));
		leg4.endYProperty().bind(centerYProperty().add(50));
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

	public Line getLeg() {
		return leg;
	}

	public Line getLeg2() {
		return leg2;
	}

	public Line getLeg3() {
		return leg3;
	}

	public Line getLeg4() {
		return leg4;
	}
}

package Canvas;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class CO_Library extends Rectangle {

	private Color bgcolor; // Background Color
	private StringProperty data;
	private Text label;
	private Rectangle node1;
	private Rectangle node2;

	public CO_Library(double x, double y, double width, double height, Color bgcolor, Color scolor) {
		super(x, y, width, height);
		setX(x);
		setY(y);
		setFill(bgcolor);
		setStroke(scolor);
		data = new SimpleStringProperty("Library.jar");

		label = new Text();
		label.textProperty().bind(data);
		label.setFont(Font.font("Arial", FontWeight.BLACK, 16));
		label.xProperty().bind(xProperty().add(widthProperty().getValue() / 2)
				.subtract(label.layoutBoundsProperty().getValue().getWidth() / 2));
		label.yProperty().bind(yProperty().add(20));

		node1 = new Rectangle();
		node1.setStroke(Color.LIGHTGRAY);
		node1.setFill(Color.WHITE);
		node1.setWidth(30);
		node1.setHeight(15);
		node1.xProperty().bind(xProperty().subtract(15));
		node1.yProperty().bind(yProperty().add(heightProperty().divide(2).subtract(15)));
		node1.toFront();

		node2 = new Rectangle();
		node2.setStroke(Color.LIGHTGRAY);
		node2.setFill(Color.WHITE);
		node2.setWidth(30);
		node2.setHeight(15);
		node2.xProperty().bind(xProperty().subtract(15));
		node2.yProperty().bind(yProperty().add(heightProperty().divide(2).add(15)));
		node2.toFront();

	}

	public final StringProperty labelProperty() {
		return data;
	}

	public Text getLabel() {
		return label;
	}

	public Rectangle getNode1() {
		return node1;
	}

	public Rectangle getNode2() {
		return node2;
	}

}

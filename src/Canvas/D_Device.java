package Canvas;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class D_Device extends Rectangle {

	private Text label;
	private Text data;
	private Rectangle shape;

	public D_Device(double x, double y, Color color) {
		super(x, y, 100, 50);
		setTranslateZ(10);
		setStroke(Color.LIGHTGRAY);
		setFill(color);

		label = new Text("<<Device>>");
		label.xProperty().bind(xProperty().add(widthProperty().getValue() / 2)
				.subtract(label.layoutBoundsProperty().getValue().getWidth() / 2));
		label.yProperty().bind(yProperty().add(20));

		data = new Text("PC");
		data.xProperty().bind(xProperty().add(widthProperty().getValue() / 2)
				.subtract(data.layoutBoundsProperty().getValue().getWidth() / 2));
		data.yProperty().bind(yProperty().add(35));

		DoubleProperty width = new SimpleDoubleProperty();
		width.set(label.layoutBoundsProperty().getValue().getWidth() * 1.5);
		widthProperty().bind(width);

		shape = new Rectangle(x + 10, y - 10, 100, 50);
		shape.setStroke(Color.LIGHTGRAY);
		shape.setFill(Color.LIGHTGRAY);
		shape.xProperty().bind(xProperty().add(10));
		shape.yProperty().bind(yProperty().subtract(10));

	}

	public Text getLabel() {
		return label;
	}

	public Text getData() {
		return data;
	}

	public Rectangle getShape() {
		return shape;
	}

}

package Canvas;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class CO_Package extends Rectangle {

	private Text label;
	private Rectangle node1;

	public CO_Package(double x, double y, Color color) {
		super(x, y, 110, 45);
		setStroke(Color.LIGHTGRAY);
		setFill(color);

		label = new Text("Package Name");
		label.xProperty().bind(xProperty().add(label.layoutBoundsProperty().getValue().getWidth() / 3));
		label.yProperty().bind(yProperty().add(22));

		node1 = new Rectangle();
		node1.setFill(color);
		node1.setStroke(Color.LIGHTGRAY);
		node1.setHeight(20);
		node1.setWidth(40);
		node1.xProperty().bind(xProperty());
		node1.yProperty().bind(yProperty().subtract(20));
		
		DoubleProperty width=new SimpleDoubleProperty();
		width.set(label.layoutBoundsProperty().getValue().getWidth()*1.6);
		widthProperty().bind(width);
		
	}

	public Text getLabel() {
		return label;
	}

	public Rectangle getNode1() {
		return node1;
	}

}

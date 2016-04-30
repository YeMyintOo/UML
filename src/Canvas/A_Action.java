package Canvas;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class A_Action extends Rectangle {

	private StringProperty data;
	private Text label;

	public A_Action(double x, double y, Color color) {
		super(x, y, 100, 40);
		setArcWidth(10);
		setArcHeight(10);
		data = new SimpleStringProperty("Action");
		setFill(color);
		setStroke(Color.LIGHTGRAY);

		label = new Text(data.get());
		label.setFont(Font.font("Arial", FontWeight.BLACK, 14));
		label.textProperty().bind(labelProperty());
		label.xProperty().bind(this.xProperty().add(label.layoutBoundsProperty().getValue().getWidth() / 2));
		label.yProperty().bind(this.yProperty().add(this.heightProperty().divide(2)));
	}

	public Text getLabel() {
		return label;
	}

	public final StringProperty labelProperty() {
		return data;
	}

}

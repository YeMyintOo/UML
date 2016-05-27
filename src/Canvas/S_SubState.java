package Canvas;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class S_SubState extends Rectangle {

	private StringProperty data;
	private Text label;
	private Line br;
	private TextField field;

	public S_SubState(double x, double y, Color color) {
		super(x, y, 200, 300);
		setArcWidth(10);
		setArcHeight(10);
		data = new SimpleStringProperty("Sub State");
		setFill(color);
		setStroke(Color.LIGHTGRAY);

		label = new Text(labelProperty().get());
		label.setFont(Font.font("Arial", FontWeight.BLACK, 14));
		label.textProperty().bind(labelProperty());

		label.xProperty().bind(xProperty().add(10));
		label.yProperty().bind(yProperty().add(20));

		br = new Line();
		br.setStroke(Color.LIGHTGRAY);
		br.startXProperty().bind(xProperty());
		br.startYProperty().bind(yProperty().add(30));
		br.endXProperty().bind(xProperty().add(getWidth()));
		br.endYProperty().bind(yProperty().add(30));
		
		field = new TextField(labelProperty().get());
		field.layoutXProperty().bind(xProperty().subtract(20));
		field.layoutYProperty().bind(yProperty().add(10));
		field.textProperty().bindBidirectional(labelProperty());
	}

	public TextField getText(boolean isShow) {
		field.setText(labelProperty().get());
		if (isShow) {
			field.setVisible(isShow);
		} else {
			field.setVisible(false);
		}
		return field;
	}

	public void setTextInVisible() {
		field.setVisible(false);
	}

	public Text getLabel() {
		return label;
	}

	public final StringProperty labelProperty() {
		return data;
	}

	public Line getBr() {
		return br;
	}
}

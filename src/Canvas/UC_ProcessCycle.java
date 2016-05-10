package Canvas;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class UC_ProcessCycle extends Ellipse {

	private StringProperty data;
	private Text label;
	private TextField text;

	public UC_ProcessCycle(double centerX, double centerY, double radiusX, double radiusY, Color bgcolor,
			Color scolor) {
		super(centerX, centerY, radiusX, radiusY);
		setFill(bgcolor);
		setStroke(Color.GRAY);
		data = new SimpleStringProperty("Process");

		label = new Text();
		label.textProperty().bindBidirectional(labelProperty());
		label.setFont(Font.font("Arial", FontWeight.NORMAL, 12));
		label.layoutXProperty().bind(centerXProperty().subtract(label.layoutBoundsProperty().getValue().getWidth()/2));
		label.layoutYProperty().bind(centerYProperty());

		text = new TextField(data.get());
		text.layoutXProperty().bind(centerXProperty().subtract(70));
		text.layoutYProperty().bind(centerYProperty().subtract(15));

		labelProperty().bindBidirectional(getTextData().textProperty());

	}

	public final StringProperty labelProperty() {
		return data;
	}

	public Text getLabel() {
		return label;
	}

	public TextField getTextData() {
		return text;
	}

	public TextField getText(boolean isShow) {
		text.setText(labelProperty().get());
		if (isShow) {
			text.setVisible(isShow);
		} else {
			text.setVisible(false);
		}
		return text;
	}

	public void setTextInVisible() {
		text.setVisible(false);
	}
}

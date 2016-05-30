package Canvas;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class D_System extends Rectangle {
	private Text label;
	private StringProperty dataV;
	private TextField field;

	public D_System(double x, double y, Color bgcolor) {
		super(x, y, 300, 400);
		setX(x);
		setY(y);
		setFill(bgcolor);
		setStroke(Color.LIGHTGRAY);

		dataV = new SimpleStringProperty("System Design ");
		label = new Text();
		label.textProperty().bind(labelProperty());
		label.setFont(Font.font("Arial", FontWeight.BLACK, 16));
		label.xProperty().bind(xProperty().add(20));
		label.yProperty().bind(yProperty().add(25));

		field = new TextField(labelProperty().get());
		field.layoutXProperty().bind(xProperty().subtract(20));
		field.layoutYProperty().bind(yProperty().add(10));
		field.textProperty().bindBidirectional(labelProperty());

		addEventFilter(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				setX(e.getX());
				setY(e.getY());
			}
		});

		label.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				field.setVisible(true);
			}
		});

		DoubleProperty min = new SimpleDoubleProperty();
		field.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent e) {
				min.set(label.layoutBoundsProperty().getValue().getWidth());
				if (min.get() > widthProperty().get()) {
					label.xProperty().bind(xProperty().add(20));
					widthProperty().bind(min.add(40));
				}
				if (e.getCode() == KeyCode.ENTER) {
					field.setVisible(false);
				}
			}
		});
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

	public StringProperty labelProperty() {
		return dataV;
	}

	public void setLabelProperty(String label) {
		dataV.set(label);
	}

	public Text getLabel() {
		return label;
	}

}

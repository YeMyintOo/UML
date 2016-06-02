package Canvas;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class UC_Process extends Ellipse {

	private StringProperty data;
	private Text label;
	private TextField text;
	private DropShadow shape;

	public UC_Process(double centerX, double centerY, double radiusX, double radiusY, Color bgcolor,
			Color scolor) {
		super(centerX, centerY, radiusX, radiusY);
		setFill(bgcolor);
		setStroke(Color.GRAY);
		data = new SimpleStringProperty("Process");

		label = new Text();
		label.textProperty().bindBidirectional(labelProperty());
		label.setFont(Font.font("Arial", FontWeight.NORMAL, 12));
		label.layoutXProperty()
				.bind(centerXProperty().subtract(label.layoutBoundsProperty().getValue().getWidth() / 2));
		label.layoutYProperty().bind(centerYProperty());

		text = new TextField(data.get());
		text.layoutXProperty().bind(centerXProperty().subtract(70));
		text.layoutYProperty().bind(centerYProperty().subtract(15));

		labelProperty().bindBidirectional(getTextData().textProperty());

		addEventFilter(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent key) {
				setCenterX(key.getX());
				setCenterY(key.getY());
			}
		});
		addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent key) {
				if (shape == null) {
					shape = new DropShadow();
				}
				setEffect(shape);
			}
		});
		addEventFilter(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent key) {
				setEffect(null);
			}
		});

		DoubleProperty w = new SimpleDoubleProperty();
		text.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent e) {
				w.set(label.layoutBoundsProperty().getValue().getWidth());
				label.layoutXProperty().bind(centerXProperty().subtract(label.getLayoutBounds().getWidth() / 2));
				if (w.get() > radiusXProperty().get()) {					
					radiusXProperty().bind(w);
				}
				if (e.getCode() == KeyCode.ENTER) {
					text.setVisible(false);
				}
			}
		});

		label.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				System.out.println("Click on Process Label");
				text.setVisible(true);
			}
		});

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

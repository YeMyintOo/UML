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

public class CO_Library extends Rectangle {

	private StringProperty data;
	private Text label;
	private Rectangle node1;
	private Rectangle node2;
	private TextField field;

	public CO_Library(double x, double y, double w, double h, Color bgcolor, Color scolor) {
		super(x, y, w, h);
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
		
		field = new TextField(labelProperty().get());
		field.layoutXProperty().bind(xProperty().subtract(25));
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
		
		DoubleProperty width = new SimpleDoubleProperty();
		field.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent e) {
				width.set(label.layoutBoundsProperty().getValue().getWidth());
				label.xProperty().bind(xProperty().add(widthProperty().getValue() / 2)
						.subtract(label.layoutBoundsProperty().getValue().getWidth() / 2));
				widthProperty().bind(width.add(20));
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

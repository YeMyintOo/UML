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

public class CO_SComponent extends Rectangle {

	private Rectangle node1;
	private Rectangle node2;
	private Text head;
	
	private Text label;
	private StringProperty data;
	private TextField field;

	public CO_SComponent(double x, double y, Color color) {
		super(x, y, 120, 45);
		setStroke(Color.LIGHTGRAY);
		setFill(color);

		node1 = new Rectangle();
		node1.setStroke(Color.LIGHTGRAY);
		node1.setFill(Color.WHITE);
		node1.setWidth(30);
		node1.setHeight(15);
		node1.xProperty().bind(xProperty().subtract(15));
		node1.yProperty().bind(yProperty().add(5));
		node1.toFront();

		node2 = new Rectangle();
		node2.setStroke(Color.LIGHTGRAY);
		node2.setFill(Color.WHITE);
		node2.setWidth(30);
		node2.setHeight(15);
		node2.xProperty().bind(xProperty().subtract(15));
		node2.yProperty().bind(yProperty().add(25));
		node2.toFront();

		head = new Text("<<source>>");
		head.xProperty().bind(xProperty().add(widthProperty().getValue() / 2)
				.subtract(head.layoutBoundsProperty().getValue().getWidth() / 2));
		head.yProperty().bind(yProperty().add(15));
		
		data = new SimpleStringProperty("Component");
		
		label = new Text(labelProperty().getValue());
		label.setFont(Font.font("Arial", FontWeight.BLACK, 14));
		label.textProperty().bind(labelProperty());
		label.xProperty().bind(xProperty().add(widthProperty().getValue() / 2)
				.subtract(label.layoutBoundsProperty().getValue().getWidth() / 2).add(10));
		label.yProperty().bind(yProperty().add(30));

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
						.subtract(label.layoutBoundsProperty().getValue().getWidth() / 2).add(5));
				head.xProperty().bind(xProperty().add(widthProperty().getValue() / 2)
						.subtract(head.layoutBoundsProperty().getValue().getWidth() / 2));
				widthProperty().bind(width.add(40));
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

	public void setTextInVisible() {
		field.setVisible(false);
	}

	public final StringProperty labelProperty() {
		return data;
	}

	public Text getLabel() {
		return label;
	}
	
	public Text getHead() {
		return head;
	}

	public Rectangle getNode1() {
		return node1;
	}

	public Rectangle getNode2() {
		return node2;
	}

}

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

public class CO_Package extends Rectangle {

	private Text label;
	private Rectangle node1;
	private StringProperty data;
	private TextField field;

	public CO_Package(double x, double y, Color color) {
		super(x, y, 110, 45);
		setStroke(Color.LIGHTGRAY);
		setFill(color);

		data = new SimpleStringProperty("Package Name");
		
		label = new Text();
		label.setFont(Font.font("Arial", FontWeight.BLACK, 14));
		label.textProperty().bind(labelProperty());
		label.xProperty().bind(xProperty().add(widthProperty().getValue() / 2).subtract(label.layoutBoundsProperty().getValue().getWidth()/2));
		label.yProperty().bind(yProperty().add(22));

		node1 = new Rectangle();
		node1.setFill(color);
		node1.setStroke(Color.LIGHTGRAY);
		node1.setHeight(10);
		node1.setWidth(40);
		node1.xProperty().bind(xProperty());
		node1.yProperty().bind(yProperty().subtract(10));
			
		
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

}

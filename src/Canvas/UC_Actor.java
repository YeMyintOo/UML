package Canvas;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class UC_Actor extends Circle {
	
	private StringProperty data;
	private Text label;
	private TextField field;
	private Line body;
	private Line leg;
	private Line leg2;
	private Line leg3;
	private Line leg4;
	private DropShadow shape;

	public UC_Actor(){
		this(0,0,0,Color.LIGHTGREEN,Color.BLUE);
	}
	
	public UC_Actor(double centerX, double centerY, double radius, Color bgcolor, Color scolor) {
		super(centerX, centerY, radius);
		setFill(bgcolor);
		setStroke(scolor);
		data = new SimpleStringProperty("Actor");

		label = new Text(data.get());
		label.setFont(Font.font("Arial", FontWeight.BLACK, 14));
		label.textProperty().bind(labelProperty());
		label.layoutXProperty().bind(centerXProperty().subtract(label.getLayoutBounds().getWidth() / 2));
		label.layoutYProperty().bind(centerYProperty().add(80));

		body = new Line();
		body.startXProperty().bind(centerXProperty());
		body.startYProperty().bind(centerYProperty().add(20));
		body.endXProperty().bind(centerXProperty());
		body.endYProperty().bind(centerYProperty().add(40));

		leg = new Line();
		leg.startXProperty().bind(centerXProperty());
		leg.startYProperty().bind(centerYProperty().add(20));
		leg.endXProperty().bind(centerXProperty().add(10));
		leg.endYProperty().bind(centerYProperty().add(30));

		leg2 = new Line();
		leg2.startXProperty().bind(centerXProperty());
		leg2.startYProperty().bind(centerYProperty().add(20));
		leg2.endXProperty().bind(centerXProperty().subtract(10));
		leg2.endYProperty().bind(centerYProperty().add(30));

		leg3 = new Line();
		leg3.startXProperty().bind(centerXProperty());
		leg3.startYProperty().bind(centerYProperty().add(40));
		leg3.endXProperty().bind(centerXProperty().add(10));
		leg3.endYProperty().bind(centerYProperty().add(50));

		leg4 = new Line();
		leg4.startXProperty().bind(centerXProperty());
		leg4.startYProperty().bind(centerYProperty().add(40));
		leg4.endXProperty().bind(centerXProperty().subtract(10));
		leg4.endYProperty().bind(centerYProperty().add(50));

		field = new TextField(labelProperty().get());
		field.layoutXProperty().bind(centerXProperty().subtract(60));
		field.layoutYProperty().bind(centerYProperty().add(60));
		field.textProperty().bindBidirectional(labelProperty());

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

		label.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				field.setVisible(true);
			}
		});

		DoubleProperty w = new SimpleDoubleProperty();
		field.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent e) {
				w.set(label.layoutBoundsProperty().getValue().getWidth());
				label.layoutXProperty().bind(centerXProperty().subtract(label.getLayoutBounds().getWidth() / 2));
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

	public Line getBody() {
		return body;
	}

	public Line getLeg() {
		return leg;
	}

	public Line getLeg2() {
		return leg2;
	}

	public Line getLeg3() {
		return leg3;
	}

	public Line getLeg4() {
		return leg4;
	}

}

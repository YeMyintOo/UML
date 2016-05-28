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
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class S_HistoryState extends Rectangle {

	private StringProperty data;
	private StringProperty history;
	private Text label;
	private Text hlabel;
	private Line br1;
	private Line br2;
	private TextField field;
	private TextField hfield;

	public S_HistoryState(double x, double y, Color color) {
		super(x, y, 200, 300);
		setArcWidth(10);
		setArcHeight(10);
		data = new SimpleStringProperty("History State");
		history = new SimpleStringProperty("entry/");
		setFill(color);
		setStroke(Color.LIGHTGRAY);

		label = new Text(labelProperty().get());
		label.setFont(Font.font("Arial", FontWeight.BLACK, 14));
		label.textProperty().bind(labelProperty());
		label.xProperty().bind(xProperty().add(10));
		label.yProperty().bind(yProperty().add(20));

		hlabel = new Text(historyProperty().get());
		hlabel.textProperty().bind(historyProperty());
		hlabel.xProperty().bind(xProperty().add(10));
		hlabel.yProperty().bind(yProperty().add(50));

		br1 = new Line();
		br1.setStroke(Color.LIGHTGRAY);
		br1.startXProperty().bind(xProperty());
		br1.startYProperty().bind(yProperty().add(30));
		br1.endXProperty().bind(xProperty().add(getWidth()));
		br1.endYProperty().bind(yProperty().add(30));

		br2 = new Line();
		br2.setStroke(Color.LIGHTGRAY);
		br2.startXProperty().bind(xProperty());
		br2.startYProperty().bind(yProperty().add(60));
		br2.endXProperty().bind(xProperty().add(getWidth()));
		br2.endYProperty().bind(yProperty().add(60));
		
		field = new TextField(labelProperty().get());
		field.layoutXProperty().bind(xProperty().subtract(20));
		field.layoutYProperty().bind(yProperty().add(5));
		field.textProperty().bindBidirectional(labelProperty());
		
		hfield = new TextField(historyProperty().get());
		hfield.layoutXProperty().bind(xProperty().subtract(20));
		hfield.layoutYProperty().bind(yProperty().add(40));
		hfield.textProperty().bindBidirectional(historyProperty());
		
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
				field.setText(labelProperty().get());
				field.setVisible(true);
			}
		});
		
		hlabel.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				hfield.setText(historyProperty().get());
				hfield.setVisible(true);
			}
		});
		
		DoubleProperty width = new SimpleDoubleProperty();
		field.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent e) {
				if (e.getCode() == KeyCode.ENTER) {
					field.setVisible(false);
				}
			}
		});
		
		hfield.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent e) {
				if (e.getCode() == KeyCode.ENTER) {
					hfield.setVisible(false);
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


	public TextField getHText(boolean isShow) {
		hfield.setText(labelProperty().get());
		if (isShow) {
			hfield.setVisible(isShow);
		} else {
			hfield.setVisible(false);
		}
		return hfield;
	}

	public void setHTextInVisible() {
		hfield.setVisible(false);
	}
	
	public Line getBr1() {
		return br1;
	}

	public Line getBr2() {
		return br2;
	}

	public Text getLabel() {
		return label;
	}

	public Text gethLabel() {
		return hlabel;
	}

	public final StringProperty labelProperty() {
		return data;
	}

	public final StringProperty historyProperty() {
		return history;
	}

}

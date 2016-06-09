package Canvas;

import java.util.ArrayList;

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
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class O_Object extends Rectangle {

	private Rectangle dataBox; // Data area box
	private StringProperty oname; // Object name
	private Text label;
	private TextField field;
	private DropShadow shape;
	private ArrayList<StringProperty> datas;

	public O_Object(double x, double y, double width, double height, Color bgcolor, Color scolor) {
		super(x, y, width, height);
		setFill(bgcolor);
		setStroke(scolor);

		datas = new ArrayList<StringProperty>();
		oname = new SimpleStringProperty("Oject :Class ");

		dataBox = new Rectangle(x, y + getHeight(), 100, 40);
		dataBox.setFill(Color.WHITE);
		dataBox.setStroke(scolor);
		dataBox.xProperty().bind(this.xProperty());
		dataBox.yProperty().bind(this.yProperty().add(getHeight()));

		label = new Text(labelProperty().get());
		label.textProperty().bind(labelProperty());
		label.xProperty().bind(xProperty().add(widthProperty().getValue() / 2)
				.subtract(label.layoutBoundsProperty().getValue().getWidth() / 2));
		label.yProperty().bind(yProperty().add(20));

		field = new TextField(oname.get());
		field.layoutXProperty().bind(xProperty().subtract(25));
		field.layoutYProperty().bind(yProperty().add(10));
		field.textProperty().bindBidirectional(labelProperty());

		widthProperty().bindBidirectional(dataBox.widthProperty());
		labelProperty().bindBidirectional(getTextData().textProperty());

		addEventFilter(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				setX(e.getX());
				setY(e.getY());
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
				label.xProperty().bind(xProperty().add(widthProperty().getValue() / 2)
						.subtract(label.layoutBoundsProperty().getValue().getWidth() / 2));
				if (e.getCode() == KeyCode.ENTER) {
					field.setVisible(false);
				}
			}
		});
		

	}

	public Text getLabel() {
		return label;
	}

	public TextField getTextData() {
		return field;
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
		return oname;
	}

	public void addData(String data) {
		StringProperty d = new SimpleStringProperty("var=value");
		datas.add(d);
	}

	public ArrayList<StringProperty> getDatas() {
		return datas;
	}

	public Rectangle getdataBox() {
		return dataBox;
	}
}

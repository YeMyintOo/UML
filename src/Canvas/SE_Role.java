package Canvas;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class SE_Role extends Rectangle {

	private Color bgColor;
	private StringProperty data;
	private DoubleProperty life;
	private Text label;
	private Line line;
	private TextField field;

	public SE_Role(double x, double y, Color bgcolor) {
		super(x, y, 100, 40);
		setFill(bgcolor);
		data = new SimpleStringProperty(":Role");
		life = new SimpleDoubleProperty(100);

		label = new Text(labelProperty().getValue());
		label.setFont(Font.font("Arial", FontWeight.BLACK, 14));
		label.textProperty().bind(labelProperty());
		label.xProperty().bind(xProperty().add(widthProperty().getValue() / 2)
				.subtract(label.layoutBoundsProperty().getValue().getWidth() / 2));
		label.yProperty().bind(yProperty().add(20));

		line = new Line(getX() + (getWidth() / 2), getY() + getHeight(), getX() + (getWidth() / 2),
				getY() + lifeProperty().add(10).doubleValue());
		line.setStroke(bgcolor);
		line.getStrokeDashArray().addAll(10d, 10d);
		line.startXProperty().bind(xProperty().add(getWidth() / 2));
		line.startYProperty().bind(yProperty().add(getHeight()));
		line.endXProperty().bind(xProperty().add(getWidth() / 2));
		line.endYProperty().bind(lifeProperty().add(10).add(yProperty()));

		field = new TextField(labelProperty().get());
		field.layoutXProperty().bind(xProperty().subtract(25));
		field.layoutYProperty().bind(yProperty().add(10));
		field.textProperty().bindBidirectional(labelProperty());

		widthProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
				line.startXProperty().bind(xProperty().add(getWidth() / 2));
				line.startYProperty().bind(yProperty().add(getHeight()));
				line.endXProperty().bind(xProperty().add(getWidth() / 2));
				line.endYProperty().bind(lifeProperty().add(10).add(yProperty()));
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

	public Line getLife() {
		return line;
	}

	public Text getLabel() {
		return label;
	}

	public final StringProperty labelProperty() {
		return data;
	}

	public final DoubleProperty lifeProperty() {
		return life;
	}
}

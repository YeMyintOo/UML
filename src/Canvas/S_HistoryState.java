package Canvas;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
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

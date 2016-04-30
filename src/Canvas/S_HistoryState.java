package Canvas;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class S_HistoryState extends Rectangle{

	private StringProperty label;
	private StringProperty history;

	public S_HistoryState(double x, double y, Color color) {
		super(x,y,200,300);
		setArcWidth(10);
		setArcHeight(10);
		label = new SimpleStringProperty("History State");
		history= new SimpleStringProperty("entry/");
		setFill(color);
		setStroke(Color.LIGHTGRAY);
	}

	public final StringProperty labelProperty() {
		return label;
	}
	
	public final StringProperty historyProperty() {
		return history;
	}


}

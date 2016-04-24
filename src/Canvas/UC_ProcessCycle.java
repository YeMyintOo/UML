package Canvas;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.stage.StageStyle;

public class UC_ProcessCycle extends Ellipse {

	private Color bgcolor; // Background Color;
	private StringProperty label;

	public UC_ProcessCycle(double centerX, double centerY, double radiusX, double radiusY, Color bgcolor,
			Color scolor) {
		super(centerX, centerY, radiusX, radiusY);
		setFill(bgcolor);
		setStroke(Color.GRAY);
		label = new SimpleStringProperty("Process");
		
	}

	public Color getBgcolor() {
		return bgcolor;
	}

	public void setBgcolor(Color bgcolor) {
		this.bgcolor = bgcolor;

	}

	public final StringProperty labelProperty() {
		return label;
	}

}

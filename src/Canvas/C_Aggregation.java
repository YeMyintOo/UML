package Canvas;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class C_Aggregation extends Line{

	private StringProperty data1; // Start Point Multiplicity
	private StringProperty data2; // End Point Multiplicity

	
	
	public C_Aggregation(double startx, double starty, double endx, double endy, Color color) {
		super(startx, starty, endx, endy);
		setStroke(color);

		data1 = new SimpleStringProperty("1");
		data2 = new SimpleStringProperty("1");
	}

	public StringProperty startMultiplicity() {
		return data1;
	}

	public StringProperty endMultiplicity() {
		return data2;
	}



}

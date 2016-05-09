package Library;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class MyGridLine extends Line {

	public MyGridLine(double x1, double y1, double x2, double y2) {
		super(x1, y1, x2, y2);
		setStroke(Color.rgb(230,230,250));
	}
}

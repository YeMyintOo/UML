package Canvas;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class S_FinalState extends Circle {

	public S_FinalState(double x, double y, double r, Color color) {
		super(x,y,r);
		setFill(color);
		setStroke(Color.LIGHTGRAY);
	}

}

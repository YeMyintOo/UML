package Canvas;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class A_InitNode extends Circle {

	public A_InitNode(double x, double y, double r, Color color) {
		super(x, y, r);
		setFill(color);
		setStroke(Color.LIGHTGRAY);
	}

}

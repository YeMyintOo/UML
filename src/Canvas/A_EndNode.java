package Canvas;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class A_EndNode extends Circle {
	
	public A_EndNode(double x, double y, double r, Color color) {
		super(x, y, r);
		setFill(color);
		setStroke(Color.LIGHTGRAY);
	}

}

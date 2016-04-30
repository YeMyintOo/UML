package Canvas;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class A_Merge extends Rectangle {

	public A_Merge(double x, double y, Color color) {
		super(x, y, 30, 30);
		setFill(color);
		setRotate(45);
		setStroke(Color.LIGHTGRAY);
	}
}
